package com.wfms.webapp.jxc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import xuner.web.mvc.MvcUtil;
import xuner.web.util.DateUtil;

import com.wfms.common.entity.AjaxMsg;
import com.wfms.common.entity.Page;
import com.wfms.common.util.BeanConvert;
import com.wfms.common.util.ConditionBean;
import com.wfms.common.util.JSONUtil;
import com.wfms.common.util.NumberUtil;
import com.wfms.common.util.PageUtil;
import com.wfms.common.util.QxUtil;
import com.wfms.constant.SystemConstant;
import com.wfms.model.jxc.fygl.FywhEntity;
import com.wfms.service.jxc.fywh.IFywhService;

@Controller
@Lazy(true)
@RequestMapping("/jxc/FywhAction.do")
public class FywhAction {
	@Autowired
	private IFywhService IFywhService;

	@RequestMapping(params = "method=initFywh")
	public String initFywh(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if (obj == null) {
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		if(qxlx < 1){
			return "error";
		}
		request.setAttribute("qxlx", qxlx);
		request.setAttribute("mkid", id);
		return "/jxc/fywh/fywh_list";
	}

	/**
	 * 
	 * @param 数据初始化
	 * @param response
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(params = "method=getAllFywh")
	public ModelAndView getAllFywh(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		String conditions = request.getParameter("conditions");
		Page page = PageUtil.format(start, limit);
		JSONObject conObj = null;
		if (conditions != null && !"".equals(conditions)) {
			conditions = java.net.URLDecoder.decode(conditions, "UTF-8");
			conObj = JSONObject.fromObject(conditions);
		}
		List<ConditionBean> conList = JSONUtil.formatConditionList(conObj);
		page.setConditionList(conList);
		page = this.IFywhService.getFywhEntity(page);
		JSONObject obj = JSONUtil.formatObject(page, new String[] {});
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	/**
	 * 添加信息
	 */
	@RequestMapping(params = "method=addFywh")
	public ModelAndView addFywh(HttpServletRequest request,
			HttpServletResponse response, FywhEntity FywhEntity,
			HttpSession session) throws Exception {
		AjaxMsg msg = new AjaxMsg();
		String xf = FywhEntity.getXfzmc();
		FywhEntity.setXfzid(xf.split("[|]")[0]);
		FywhEntity.setXfzmc(xf.split("[|]")[1]);
		FywhEntity.setJlrid(String.valueOf(session.getAttribute(SystemConstant.YHDL_USERID)));
		FywhEntity.setJlrmc(String.valueOf(session.getAttribute(SystemConstant.YHDL_YHXM)));
		FywhEntity.setJlsj(String.valueOf(DateUtil.getCurrentTimeString()));
		int id = this.IFywhService.addFywh(FywhEntity);
		if (id < 0) {
			msg.setSuccess(false);
			msg.setMessage("增加费用失败!");
		} else {
			msg.setSuccess(true);
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	@RequestMapping(params = "method=updateFywh")
	public ModelAndView updateFywh(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String data = request.getParameter("data");
		data = java.net.URLDecoder.decode(data, "UTF-8");
		JSONArray ja = JSONArray.fromObject(data); // 把字符串转换成JSON对象
		List<FywhEntity> list = (List<FywhEntity>) BeanConvert.boundToArray(
				FywhEntity.class, ja);// 把JSON对象转换成java对象
		AjaxMsg msg = new AjaxMsg();
		if (list == null) {
			msg.setSuccess(false);
			msg.setMessage("更新数据失败!");
		} else {
			int row = IFywhService.updateFywh(list);
			if (row <= 0) {
				msg.setSuccess(false);
				msg.setMessage("更新数据失败!");
			} else {
				msg.setSuccess(true);
			}
		}
		JSONObject obj = JSONUtil.formatObject(msg); // msg转换成JSON对象
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	@RequestMapping(params = "method=deleteFywh")
	public ModelAndView deleteFywh(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strId = request.getParameter("id");
		int id = NumberUtil.format(strId);
		AjaxMsg msg = new AjaxMsg();
		if (id == 0) {
			msg.setSuccess(false);
			msg.setMessage("删除费用失败，数据有误，请验证后重试！");
		} else {
			int row = IFywhService.deleteFywh(id);

			if (row == -1) {
				msg.setSuccess(false);
				msg.setMessage("删除费用信息失败！");
			} else if (row < 0) {
				msg.setSuccess(false);
				msg.setMessage("删除失败，服务器繁忙，请稍后重试！");
			} else {
				msg.setSuccess(true);
			}
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	
	/**
	 * 判断页面需要查询人或车
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="method=getAllXfdxByJoin")
	public ModelAndView getAllXfdxByJoin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String type =request.getParameter("sid");
		JSONArray obj = new JSONArray();
		
		if(type != null && !type.equals("")){
			List<FywhEntity> page  = IFywhService.getAllXfz(type);
			obj = JSONUtil.formatArray(page,new String[]{});
		}
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonArrayModelAndView(obj);
	}
}
