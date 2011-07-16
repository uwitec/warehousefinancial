/**
 *******************************************************************************
 * 文件名：RyhjAction.java
 *
 * 描述：
 * 
 * 创建日期：Mar 16, 2010 2:23:04 PM
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

import net.sf.json.JSONArray;
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
import com.wfms.common.util.BeanConvert;
import com.wfms.common.util.ConditionBean;
import com.wfms.common.util.JSONUtil;
import com.wfms.common.util.NumberUtil;
import com.wfms.common.util.PageUtil;
import com.wfms.common.util.QxUtil;
import com.wfms.model.rsgl.RyhjEntity;
import com.wfms.service.rsgl.IRyhjService;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：RyhjAction
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 16, 2010 2:23:04 PM
 *  <dd> 修改人：无
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see RyhjAction
 * @version 1.0
 *
 */

@Controller
@Lazy(true)
@RequestMapping("/rsgl/ryhjAction.do")
public class RyhjAction {
	@Autowired
	private IRyhjService ryhjService;

	@RequestMapping(params="method=initRyhj")
	public String initRyhj(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if(obj == null){
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		request.setAttribute("qxlx", qxlx);
		request.setAttribute("mkid", id);
		return "/rsgl/ryhj_list";
	}
	
	@RequestMapping(params="method=getRyhjByPage")
	public ModelAndView getRyhjByPage(HttpServletRequest request, HttpServletResponse response)
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
		
		page = ryhjService.loadRyhjByPage(page);
		JSONObject obj = JSONUtil.formatObject(page,new String[]{"part","right",
				"ls","bzr","memberRights","memberParts","roleMembers",
				"company","parts","members","roleMembers","roleParts","parent","children",
				"chargeRole","roles","partRights","memberParts","departs"});
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	@RequestMapping(params="method=addRyhj")
	public ModelAndView addRyhj(HttpServletRequest request, HttpServletResponse response,
			RyhjEntity ryhj)
			throws Exception {
		AjaxMsg msg = new AjaxMsg();
		if(ryhj == null){
			msg.setSuccess(false);
			msg.setMessage("增加异动信息失败，数据有误，请验证后重试！");
		}else{
			int row = ryhjService.addRyhj(ryhj);
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
	
	@RequestMapping(params="method=updRyhj")
	public ModelAndView updRyhj(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String data = request.getParameter("data");
		data = java.net.URLDecoder.decode(data,"UTF-8");
		JSONArray ja = JSONArray.fromObject(data);
		
		List<RyhjEntity> list=(List<RyhjEntity>) BeanConvert.boundToArray(RyhjEntity.class, ja);
		AjaxMsg msg = new AjaxMsg();
		if(list == null){
			msg.setSuccess(false);
			msg.setMessage("更新数据失败，数据有误，请验证后重试");
		}else{
			int row  = ryhjService.updateRyhjList(list);
			if(row <= 0){
				msg.setSuccess(false);
				msg.setMessage("更新数据失败，数据有误，请验证后重试");
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
			int row = ryhjService.deleteRyhj(id);
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
