/**
 *******************************************************************************
 * 文件名：RyydAction.java
 *
 * 描述：
 * 
 * 创建日期：Apr 16, 2010 2:17:34 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.webapp.rsgl.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import xuner.web.mvc.MvcUtil;

import com.wfms.common.entity.AjaxMsg;
import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.common.util.JSONUtil;
import com.wfms.common.util.NumberUtil;
import com.wfms.common.util.PageUtil;
import com.wfms.common.util.QxUtil;
import com.wfms.model.rsgl.RyydEntity;
import com.wfms.service.rsgl.IRyydService;

/**
 * <dl>  Description
 *  <dd> 项目名称：迅尔数字化校园信息平台ECMS
 *  <dd> 类名称：RyydAction
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Apr 16, 2010 2:17:34 PM
 *  <dd> 修改人：无
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author ECMS
 * @see RyydAction
 * @version 1.0
 *
 */

@Controller
@Lazy(true)
@RequestMapping("/rsgl/ryydAction.do")
public class RyydAction  {
	@Autowired
	private IRyydService ryydService;

	@RequestMapping(params="method=initRyyd")
	public String initRyyd(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if(obj == null){
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		request.setAttribute("qxlx", qxlx);
		request.setAttribute("mkid", id);
		return  "/rsgl/ryyd_list";
	}
	
	@RequestMapping(params="method=getRyydByPage")
	public ModelAndView getRyydByPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		String conditions = request.getParameter("conditions");
		Page page = PageUtil.format(start, limit);
		//查询条件
		JSONObject conObj = null;
		if(conditions != null && !"".equals(conditions)){
			conditions = java.net.URLDecoder.decode(conditions,"UTF-8");
			conObj = JSONObject.fromObject(conditions);
		}
		List<ConditionBean> conList = JSONUtil.formatConditionList(conObj);
		page.setConditionList(conList);
		
		page = ryydService.getRyydByPage(page);
		JSONObject obj = JSONUtil.formatObject(page,new String[]{"rys","lss","bzrs"});
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	@RequestMapping(params="method=addRyyd")
	public ModelAndView addRyyd(HttpServletRequest request, HttpServletResponse response,
			RyydEntity ryyd)
			throws Exception {
		AjaxMsg msg = new AjaxMsg();
		if(ryyd == null){
			msg.setSuccess(false);
			msg.setMessage("增加异动信息失败，数据有误，请验证后重试！");
		}else{
			int row = ryydService.addRyyd(ryyd);
			if(row <0 ){
				msg.setSuccess(false);
				msg.setMessage("增加异动信息失败，数据有误，请验证后重试！");
			}else{
				msg.setSuccess(true);
			}
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	@RequestMapping(params="method=deleteRyyd")
	public ModelAndView deleteRyyd(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strId = request.getParameter("id");
		int id = NumberUtil.format(strId);
		AjaxMsg msg = new AjaxMsg();
		if(id == 0){
			msg.setSuccess(false);
			msg.setMessage("删除人员异动信息失败，数据有误，请验证后重试！");
		}else{
			int row = ryydService.deleteRyydById(id);
			if(row <= 0){
				msg.setSuccess(false);
				msg.setMessage("删除人员异动信息失败，服务器繁忙，请稍后重试！");
			}else{
				msg.setSuccess(true);
			}
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
}
