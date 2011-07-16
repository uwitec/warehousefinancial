package com.wfms.webapp.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import xuner.web.mvc.MvcUtil;

import com.wfms.common.entity.AjaxMsg;
import com.wfms.common.util.BeanConvort;
import com.wfms.common.util.DateUtil;
import com.wfms.common.util.JSONUtil;
import com.wfms.common.util.JacksonUtil;
import com.wfms.common.util.NumberUtil;
import com.wfms.common.util.QxUtil;
import com.wfms.common.util.StringUtil;
import com.wfms.dto.system.JsDto;
import com.wfms.model.system.PartGenInfo;
import com.wfms.model.system.PartRight;
import com.wfms.model.system.RightGenInfo;
import com.wfms.model.system.RoleGenInfo;
import com.wfms.model.system.RolePart;
import com.wfms.service.system.IJsglService;
import com.wfms.service.system.IZwJsglService;

@Controller
@Lazy(true)
@RequestMapping("/system/jsglAction.do")
public class JsglAction {
	@Autowired
	private IJsglService jsglService;
	@Autowired
	private IZwJsglService zwjsService;

	@RequestMapping(params = "method=initJsgl")
	public String initJsgl(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if (obj == null) {
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		request.setAttribute("qxlx", qxlx);
		request.setAttribute("mkid", id);
		return "/system/jsgl_list";
	}

	@RequestMapping(params = "method=getAllJs")
	public ModelAndView getAllJs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsDto dto = jsglService.getAllJs();
		JSONObject jay = JSONUtil.formatObject(dto, new String[] { "partRights",
				"parent", "memberParts","role","roles" });
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(jay);
	}

	@RequestMapping(params = "method=getJs")
	public String getJs(HttpServletRequest request, 
			HttpServletResponse response,@RequestParam("id")int partId)
			throws Exception {
		if (partId == 0) {
			return "error";
		}
		JsDto dto = jsglService.getJs(partId);
		request.setAttribute("js", dto.getJs());
		return "/system/jsgl_sq";
	}

	@RequestMapping(params = "method=addJs")
	public ModelAndView addJs(HttpServletRequest request,
			HttpServletResponse response, PartGenInfo js) throws Exception {
		js.setCreateTime(DateUtil.formatDate(new Date()));
		js.setParentId(0);
		int row = jsglService.addJs(js);
		AjaxMsg msg = new AjaxMsg();
		if (row > 0) {
			msg.setSuccess(true);
		} else {
			msg.setSuccess(false);
			msg.setMessage("添加失败,服务器繁忙,请稍后重试!");
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	@RequestMapping(params = "method=delJs")
	public ModelAndView delJs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strId = request.getParameter("id");
		int id = NumberUtil.format(strId);
		AjaxMsg msg = new AjaxMsg();
		if (id == 0) {
			msg.setSuccess(false);
			msg.setMessage("删除失败,数据有误!");
		} else {
			int row = jsglService.delJs(id);
			if (row > 0) {
				msg.setSuccess(true);
			} else if (row == -1) {
				msg.setSuccess(false);
				msg.setMessage("删除失败,该角色还拥有权限,请先删除其权限!");
			} else if (row == -2) {
				msg.setSuccess(false);
				msg.setMessage("删除失败,还有用户属于该角色,请删除该用户关联!");
			} else if (row == -3) {
				msg.setSuccess(false);
				msg.setMessage("删除失败,该角色为不可删除角色，删除将会影响系统正常使用");
			} else {
				msg.setSuccess(false);
				msg.setMessage("删除失败,服务器繁忙!");
			}
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	@RequestMapping(params = "method=updJs")
	public ModelAndView updJs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String data = request.getParameter("data");
		data = java.net.URLDecoder.decode(data, "UTF-8");
		JSONArray ja = JSONArray.fromObject(data);
		List<PartGenInfo> list = BeanConvort.boundToArray(PartGenInfo.class,ja);
		
		/*for (int i = 0; i < ja.size(); i++) {
			JSONObject obj = (JSONObject) ja.get(i);
			PartGenInfo js = new PartGenInfo();
			try {
				js.setPartId(Integer.parseInt(obj.getString("id")));
				js.setParentId(0);
			} catch (Exception e) {
				break;
			}
			js.setCreateTime(DateUtil.formatDate(new Date()));
			js.setName(obj.getString("jsmc"));
			js.setDescription(obj.getString("jsms"));
			list.add(js);
		}*/
		int row = jsglService.updJs(list);
		AjaxMsg msg = new AjaxMsg();
		if (row != 0) {
			msg.setSuccess(true);
			msg.setMessage("更新成功");
			msg.setOther(row);
		} else {
			msg.setSuccess(false);
			msg.setMessage("添加失败,服务器繁忙!");
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	// 增加角色权限
	@RequestMapping(params = "method=addJxqx")
	public ModelAndView addJxqx(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strId = request.getParameter("id");
		String strGnmkId = request.getParameter("gnmkId");
		int id = NumberUtil.format(strId);
		//int gnmkId = NumberUtil.format(strGnmkId);

		AjaxMsg msg = new AjaxMsg();
		if (id == 0 || StringUtil.isNullOrEmpty(strGnmkId)) {
			msg.setSuccess(false);
			msg.setMessage("添加失败,数据有误!");
		} else {
			int row = jsglService.addJsqx(id, strGnmkId);
			if (row > 0) {
				msg.setSuccess(true);
			} else {
				msg.setSuccess(false);
				msg.setMessage("添加失败,服务器繁忙,请稍后重试!");
			}
		}

		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	// 删除角色权限
	@RequestMapping(params = "method=delJxqx")
	public ModelAndView delJxqx(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strId = request.getParameter("id");
		String strGnmkId = request.getParameter("gnmkId");
		int id = NumberUtil.format(strId);

		AjaxMsg msg = new AjaxMsg();
		if (id == 0 || StringUtil.isNullOrEmpty(strGnmkId)) {
			msg.setSuccess(false);
			msg.setMessage("删除失败,数据有误!");
		} else {
			int row = jsglService.delJsqx(id, strGnmkId);
			if (row > 0) {
				msg.setSuccess(true);
			} else {
				msg.setSuccess(false);
				msg.setMessage("删除失败,服务器繁忙,请稍后重试!");
			}
		}

		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	// 获取角色所有权限
	@RequestMapping(params = "method=getAllJsqx")
	@ResponseBody
	public JsDto getAllJsqx(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strId = request.getParameter("id");
		int id = NumberUtil.format(strId);
		if (id == 0) {
			return null;
		}
		JsDto dto = jsglService.getAllJsqx(id);
		return dto;
	}

	// 批量修改角色权限
	@RequestMapping(params = "method=updJsqx")
	public ModelAndView updJsqx(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strId = request.getParameter("id");
		int jsid = NumberUtil.format(strId);
		PartGenInfo js = new PartGenInfo();
		js.setPartId(jsid);

		String data = request.getParameter("data");
		data = java.net.URLDecoder.decode(data, "UTF-8");
		JSONArray ja = JSONArray.fromObject(data);
		List<PartRight> list = BeanConvort.boundToArray(PartRight.class, ja);
		for(PartRight partRight:list)
		{
			partRight.setPart(js);
		}
		/*for (int i = 0; i < ja.size(); i++) {
			JSONObject obj = (JSONObject) ja.get(i);
			PartRight jsqx = new PartRight();
			RightGenInfo qx = new RightGenInfo();
			try {
				jsqx.setId(obj.getInt("id"));
				qx.setRightId(obj.getInt("qx.id"));
				jsqx.setRightType(obj.getInt("qxlx"));
				jsqx.setRightStatus(obj.getString("qxzt"));
			} catch (Exception e) {
				break;
			}
			jsqx.setPart(js);
			jsqx.setRight(qx);
			list.add(jsqx);
		}*/
		int row = jsglService.updJsqx(list);
		AjaxMsg msg = new AjaxMsg();
		if (row != 0) {
			msg.setSuccess(true);
			msg.setMessage("更新成功");
			msg.setOther(row);
		} else {
			msg.setSuccess(false);
			msg.setMessage("更新失败,服务器繁忙!");
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	// 获取用户没有的角色
	@RequestMapping(params = "method=getYhwyJs")
	public ModelAndView getYhwyJs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strId = request.getParameter("yhId");
		if (StringUtil.isNullOrEmpty(strId)) {
			return new ModelAndView("error");
		}
		JsDto dto = jsglService.getYhwyJs(strId);
		JSONArray jay = JSONUtil.formatArray(dto.getJsList(), new String[] {
				"jsqxs", "zwjss", "yhjss" });
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonArrayModelAndView(jay);
	}

	// 增加用户角色
	@RequestMapping(params = "method=addYhjs")
	public ModelAndView addYhjs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strYhId = request.getParameter("yhId");
		String strJsId = request.getParameter("jsId");
		int jsId = NumberUtil.format(strJsId);
		AjaxMsg msg = new AjaxMsg();
		if (StringUtil.isNullOrEmpty(strYhId) || jsId == 0) {
			msg.setSuccess(false);
			msg.setMessage("更新失败,数据有误!");
			return null;
		} else {
			int row = jsglService.addYhjs(strYhId, jsId);

			if (row != 0) {
				msg.setSuccess(true);
				msg.setMessage("更新成功");
				msg.setOther(row);
			} else {
				msg.setSuccess(false);
				msg.setMessage("更新失败,服务器繁忙!");
			}
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	// 删除用户角色
	@RequestMapping(params = "method=delYhjs")
	public ModelAndView delYhjs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strYhId = request.getParameter("yhId");
		String strJsId = request.getParameter("jsId");
		int jsId = NumberUtil.format(strJsId);
		AjaxMsg msg = new AjaxMsg();
		if (StringUtil.isNullOrEmpty(strYhId) || jsId == 0) {
			msg.setSuccess(false);
			msg.setMessage("更新失败,数据有误!");
			return null;
		} else {
			int row = jsglService.delYhjs(strYhId, jsId);

			if (row != 0) {
				msg.setSuccess(true);
				msg.setMessage("更新成功");
				msg.setOther(row);
			} else {
				msg.setSuccess(false);
				msg.setMessage("更新失败,服务器繁忙!");
			}
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	// 获取职务以用角色
	@RequestMapping(params = "method=getZwyyJs")
	public ModelAndView getZwyyJs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strId = request.getParameter("id");
		if (StringUtil.isNullOrEmpty(strId)) {
			return null;
		}
		JsDto dto = jsglService.getJsForZw(strId);
		JSONArray jay = JSONUtil.formatArray(dto.getJsList(), new String[] {
				"partRights", "memberParts","parent"});
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonArrayModelAndView(jay);
	}

	// 获取职务未有角色
	@RequestMapping(params = "method=getZwwyJs")
	public ModelAndView getZwwyJs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strId = request.getParameter("id");
		if (StringUtil.isNullOrEmpty(strId)) {
			return null;
		}
		JsDto dto = jsglService.getJsForZwNot(strId);
		JSONArray jay = JSONUtil.formatArray(dto.getJsList(), new String[] {
				"js","jsqxList","parent","memberParts","partRights" });
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonArrayModelAndView(jay);
	}

	// 增加职务角色
	@RequestMapping(params = "method=addZwjs")
	public ModelAndView addZwjs(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strJsId = request.getParameter("jsId");
		String strZwId = request.getParameter("zwId");
		int jsId = NumberUtil.format(strJsId);
		if (jsId == 0 || StringUtil.isNullOrEmpty(strZwId)) {
			return new ModelAndView("error");
		}
		PartGenInfo js = new PartGenInfo(jsId);
		RoleGenInfo zw = new RoleGenInfo(strZwId);
		RolePart zwjs = new RolePart();
		zwjs.setPart(js);
		zwjs.setRole(zw);
		int row = zwjsService.addZwJs(zwjs);
		AjaxMsg msg = new AjaxMsg();
		if (row > 0) {
			msg.setSuccess(true);
		} else {
			msg.setSuccess(false);
			msg.setMessage("增加职务角色失败，服务器繁忙，请稍后重试!");
		}
		JSONObject jay = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(jay);
	}

	// 增加职务角色
	@RequestMapping(params = "method=delZwjs")
	public ModelAndView delZwjs(@RequestParam("jsId")int partId,
			@RequestParam("zwId")String zwId,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (partId == 0 || StringUtil.isNullOrEmpty(zwId)) {
			return new ModelAndView("error");
		}
		int row = zwjsService.delZwJs(partId, zwId);
		AjaxMsg msg = new AjaxMsg();
		if (row > 0) {
			msg.setSuccess(true);
		} else {
			msg.setSuccess(false);
			msg.setMessage("增加职务角色失败，服务器繁忙，请稍后重试!");
		}
		JSONObject jay = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(jay);
	}
}
