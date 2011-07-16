package com.wfms.webapp.system.controller;

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

import com.wfms.common.entity.AjaxMsg;
import com.wfms.common.entity.Page;
import com.wfms.common.util.BeanConvert;
import com.wfms.common.util.ConditionBean;
import com.wfms.common.util.JSONUtil;
import com.wfms.common.util.NumberUtil;
import com.wfms.common.util.PageUtil;
import com.wfms.common.util.QxUtil;
import com.wfms.model.system.XqwhEntity;
import com.wfms.service.system.IXqwhService;

@Controller
@Lazy(true)
@RequestMapping("/system/XqwhAction.do")
public class XqwhAction {
	@Autowired
	private IXqwhService iXqwhService;

	@RequestMapping(params = "method=initXqwh")
	public String initXqwh(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if (obj == null) {
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		request.setAttribute("qxlx", qxlx);
		request.setAttribute("mkid", id);
		return "/system/xqwh_list";
	}

	/**
	 * 
	 * @param 数据初始化
	 * @param response
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(params = "method=getAllXqwh")
	public ModelAndView getAllXqwh(HttpServletRequest request,
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
		page = this.iXqwhService.getXqwhEntity(page);
		JSONObject obj = JSONUtil.formatObject(page, new String[] {});
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	/**
	 * 添加信息
	 */
	@RequestMapping(params = "method=addXqwh")
	public ModelAndView addXqwh(HttpServletRequest request,
			HttpServletResponse response, XqwhEntity xqwhEntity,
			HttpSession session) throws Exception {
		AjaxMsg msg = new AjaxMsg();
		int id = this.iXqwhService.addXqwh(xqwhEntity);
		if (id < 0) {
			msg.setSuccess(false);
			msg.setMessage("校区代码信息失败，数据有误，请验证后重试!");
		} else {
			msg.setSuccess(true);
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	@RequestMapping(params = "method=updateXqwh")
	public ModelAndView updateXqwh(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String data = request.getParameter("data");
		data = java.net.URLDecoder.decode(data, "UTF-8");
		JSONArray ja = JSONArray.fromObject(data); // 把字符串转换成JSON对象
		List<XqwhEntity> list = (List<XqwhEntity>) BeanConvert.boundToArray(
				XqwhEntity.class, ja);// 把JSON对象转换成java对象
		AjaxMsg msg = new AjaxMsg();
		if (list == null) {
			msg.setSuccess(false);
			msg.setMessage("更新数据失败，数据有误，请验证后重试!");
		} else {
			int row = iXqwhService.updateXqwh(list);
			if (row <= 0) {
				msg.setSuccess(false);
				msg.setMessage("更新数据失败，服务器繁忙，请稍后重试!");
			} else {
				msg.setSuccess(true);
			}
		}
		JSONObject obj = JSONUtil.formatObject(msg); // msg转换成JSON对象
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	@RequestMapping(params = "method=deleteXqwh")
	public ModelAndView deleteXqwh(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strId = request.getParameter("id");
		int id = NumberUtil.format(strId);
		AjaxMsg msg = new AjaxMsg();
		if (id == 0) {
			msg.setSuccess(false);
			msg.setMessage("删除校区代码信息失败，数据有误，请验证后重试！");
		} else {
			int row = iXqwhService.deleteXqwh(id);

			if (row == -1) {
				msg.setSuccess(false);
				msg.setMessage("删除校区代码信息失败，数据有误，请验证后重试");
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

}
