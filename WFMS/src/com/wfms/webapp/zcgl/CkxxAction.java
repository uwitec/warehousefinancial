/**
 *******************************************************************************
 * 文件名：XfjcAction.java
 *
 * 描述：
 * 
 * 创建日期：Apr 15, 2010 1:15:44 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.webapp.zcgl;

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
import com.wfms.common.entity.Page;
import com.wfms.common.util.BeanConvert;
import com.wfms.common.util.ConditionBean;
import com.wfms.common.util.JSONUtil;
import com.wfms.common.util.NumberUtil;
import com.wfms.common.util.PageUtil;
import com.wfms.common.util.QxUtil;
import com.wfms.model.zcgl.CkxxEntity;
import com.wfms.model.zcgl.CkzcEntity;
import com.wfms.service.zcgl.ICkxxService;


@Controller
@Lazy(true)
@RequestMapping("/zcgl/ckxxAction.do")
public class CkxxAction  {
	@Autowired
	private ICkxxService ckxxService;

	/**
	 * 仓库信息
	 */
	@RequestMapping(params="method=initCkxx")
	public String initCkxx(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if (obj == null) {
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		request.setAttribute("qxlx", qxlx);
		request.setAttribute("mkid", id);
		return "/zcgl/ckxx_list";
	}

	/**
	 * 仓库信息列表
	 */
	 @RequestMapping(params="method=getCkxxByPage")
	public ModelAndView getCkxxByPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		String conditions = request.getParameter("conditions");
		Page page = PageUtil.format(start, limit);
		// 查询条件
		JSONObject conObj = null;
		if (conditions != null && !"".equals(conditions)) {
			conditions = java.net.URLDecoder.decode(conditions, "UTF-8");
			conObj = JSONObject.fromObject(conditions);
		}
		List<ConditionBean> conList = JSONUtil.formatConditionList(conObj);
		page.setConditionList(conList);

		ckxxService.getCkxxByPage(page);
		JSONObject obj = JSONUtil.formatObject(page, new String[] { "rys","ckzcList" });
		response.setCharacterEncoding("UTF-8");
		return  MvcUtil.jsonObjectModelAndView(obj);
	}

	/**
	 * <dl>
	 * <b>方法名:addCkxx</b>
	 * <dd>方法作用：
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	 * <dd>
	 *             </dl>
	 */
	 @RequestMapping(params="method=addCkxx")
	public ModelAndView addCkxx(HttpServletRequest request, HttpServletResponse response,
			CkxxEntity entity )
			throws Exception {
		String data = request.getParameter("data");
		data = java.net.URLDecoder.decode(data, "UTF-8");
		JSONArray ja = JSONArray.fromObject(data);
		entity.setGlyid(entity.getGlyxm());
		
		List<CkzcEntity> list = (List<CkzcEntity>) BeanConvert.boundToArray(
				CkzcEntity.class, ja);
		
		
		AjaxMsg msg = new AjaxMsg();
		int id = ckxxService.addCkxx(entity,list);
		if (id < 0) {
			msg.setSuccess(false);
			msg.setMessage("增加仓库信息失败，数据有误，请验证后重试！");
		} else {
			msg.setSuccess(true);
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	/**
	 * <dl>
	 * <b>方法名:updateCkxx</b>
	 * <dd>方法作用：
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	 * <dd>
	 */
	 @RequestMapping(params="method=updateCkxx")
	public ModelAndView updateCkxx(HttpServletRequest request, HttpServletResponse response,
			CkxxEntity ckxx)
			throws Exception {
		AjaxMsg msg = new AjaxMsg();
		if (ckxx == null) {
			msg.setSuccess(false);
			msg.setMessage("更新数据失败，数据有误，请验证后重试!");
		} else {
			int row = ckxxService.updateCkxx(ckxx);
			if (row <= 0) {
				msg.setSuccess(false);
				msg.setMessage("更新数据失败，服务器繁忙，请稍后重试!");
			} else {
				msg.setSuccess(true);
			}
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	/**
	 * <dl>
	 * <b>方法名:deleteCkxx</b>
	 * <dd>方法作用：
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	 * <dd>
	 */
	 @RequestMapping(params="method=deleteCkxx")
	public ModelAndView deleteCkxx(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strId = request.getParameter("id");
		int id = NumberUtil.format(strId);
		AjaxMsg msg = new AjaxMsg();
		if (id == 0) {
			msg.setSuccess(false);
			msg.setMessage("删除仓库信息失败，数据有误，请验证后重试！");
		} else {
			int row = ckxxService.deleteCkxx(id);
			if (row == -1) {
				msg.setSuccess(false);
				msg.setMessage("删除仓库信息失败！");
			} else if (row < 0) {
				msg.setSuccess(false);
				msg.setMessage("删除失败，服务器繁忙，请稍后重试！");
			} else {
				msg.setSuccess(true);
			}
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	 
	@RequestMapping(params="method=getCkxxList")
	public ModelAndView getCkxxList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = String.valueOf(request.getSession().getAttribute("userId"));
		List<CkxxEntity> ckxxList = ckxxService.getCkxxList(userId);
		JSONArray ary = JSONUtil.formatArray(ckxxList, new String[] {"ckzcList"});
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonArrayModelAndView(ary);
	}
	
	
}
