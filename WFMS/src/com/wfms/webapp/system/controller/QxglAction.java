package com.wfms.webapp.system.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import xuner.web.mvc.MvcUtil;

import com.wfms.common.entity.AjaxMsg;
import com.wfms.common.util.BeanConvort;
import com.wfms.common.util.JSONUtil;
import com.wfms.common.util.NumberUtil;
import com.wfms.dto.system.QxDto;
import com.wfms.model.system.ModuleGenInfo;
import com.wfms.model.system.RightGenInfo;
import com.wfms.service.system.IQxglService;

@Controller
@Lazy(true)
@RequestMapping("/system/qxglAction.do")
public class QxglAction{
	
	@Autowired
	private IQxglService qxglService;
	
	@RequestMapping(params="method=initQxgl")
	public String initQxgl(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "/system/qxgl_list";
	}
	
	@RequestMapping(params="method=getAllQx")
	public ModelAndView getAllQx(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QxDto dto = qxglService.getAllQx();
		JSONObject jay =JSONUtil.formatObject(dto, new String[]{"partRights","memberRights","parent","right"});
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(jay);
	}
	
	@RequestMapping(params="method=addQx")
	public ModelAndView addQx(HttpServletRequest request,
			HttpServletResponse response,
			RightGenInfo qx)
	throws Exception {
		qx.setParentId(0);
		int row = qxglService.addQx(qx);
		AjaxMsg msg = new AjaxMsg();
		if(row>0){
			msg.setSuccess(true);
		}else{
			msg.setSuccess(false);
			msg.setMessage("添加失败,服务器繁忙,请稍后重试!");
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	@RequestMapping(params="method=delBm")
	public ModelAndView delBm(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String strId = request.getParameter("id");
		int id = NumberUtil.format(strId);
		AjaxMsg msg = new AjaxMsg();
		if(id == 0){
			msg.setSuccess(false);
			msg.setMessage("删除失败,数据有误!");
		}else{
			int row = qxglService.deleteQx(id);
			if(row>0){
				msg.setSuccess(true);
			}else if(row == -1){
				msg.setSuccess(false);
				msg.setMessage("删除失败,该部门下还有用户,请先删除部门下的用户!");
			}else{
				msg.setSuccess(false);
				msg.setMessage("删除失败,服务器繁忙!");
			}
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	@RequestMapping(params="method=updQx")
	public ModelAndView updQx(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String data = request.getParameter("data");
		data = java.net.URLDecoder.decode(data,"UTF-8");
		JSONArray ja = JSONArray.fromObject(data);
		List<RightGenInfo> qxList = new ArrayList<RightGenInfo>();
		qxList = BeanConvort.boundToArray(RightGenInfo.class, ja);
		/*ModuleGenInfo gnmk=null;
		for(int i=0;i<ja.size();i++){
			JSONObject obj = (JSONObject)ja.get(i);
			RightGenInfo qx = new RightGenInfo();
			try {
				qx.setRightId(obj.getInt("rightId"));
				qx.setParent(new RightGenInfo(obj.getInt("parentId")));
			} catch (Exception e) {
				System.out.println("数字格式化失败!");
				break;
			}
			gnmk=new ModuleGenInfo();
			JSONObject gnmkJson=null;
			if(obj.getJSONObject("module")!=null)
			{
				gnmkJson=obj.getJSONObject("module");
			}
			gnmk.setModuleId(gnmkJson.getInt("rightId"));
			qx.setModule(gnmk);
			qx.setName(obj.getString("name"));
			qx.setDescription(obj.getString("description"));
			qx.setRightStatus(obj.getString("rightStatus"));
			qxList.add(qx);
		}*/
		int row = qxglService.updateQx(qxList);
		AjaxMsg msg = new AjaxMsg();
		if(row>0){
			msg.setSuccess(true);
		}else{
			msg.setSuccess(false);
			msg.setMessage("修改失败,服务器繁忙,请稍后重试!");
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
}
