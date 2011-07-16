/**
 *******************************************************************************
 * 文件名：RyjtcyAction.java
 *
 * 描述：
 * 
 * 创建日期：Apr 29, 2010 9:17:40 AM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.webapp.rsgl.controller;

import java.util.ArrayList;
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
import com.wfms.common.util.Rule;
import com.wfms.model.rsgl.RyjtcyEntity;
import com.wfms.service.rsgl.IRyjtcyService;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：RyjtcyAction
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Apr 29, 2010 9:17:40 AM
 *  <dd> 修改人：无
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see RyjtcyAction
 * @version 1.0
 *
 */

@Controller
@Lazy(true)
@RequestMapping("/rsgl/ryjtcyAction.do")
public class RyjtcyAction  {
	@Autowired
	private IRyjtcyService ryjtcyService;
	

	/*public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		return super.execute(mapping, form, request, response);
	}
*/
	@RequestMapping(params="method=initRyjtcy")
	public String initRyjtcy(HttpServletRequest request,HttpServletResponse response)
		throws Exception{
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if (obj == null ) {
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		request.setAttribute("qxlx",qxlx);
		request.setAttribute("mkid", id);
		return "/rsgl/knzg_list";
	}

	@RequestMapping(params="method=getRyjtcyByPage")
	public ModelAndView getRyjtcyByPage(HttpServletRequest request,HttpServletResponse response)
		throws Exception{
		String start =request.getParameter("start");
		String limit = request.getParameter("limit");
		String idStr = request.getParameter("id");
		Page page = PageUtil.format(start, limit);
		int id = NumberUtil.format(idStr);
		List<ConditionBean> condList= new ArrayList<ConditionBean>(1);
		condList.add(new ConditionBean("ry.id",String.valueOf(id),Rule.EQUAL));
		page.setConditionList(condList);
		page = this.ryjtcyService.getRyjtcyByPage(page);
		JSONObject obj = JSONUtil.formatObject(page,new String[] {"rys","bzrs","lss"});
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	//添加人员的家庭成员信息
	@RequestMapping(params="method=addRyjtcy")
	public ModelAndView addRyjtcy(HttpServletRequest request,HttpServletResponse response,
			RyjtcyEntity entity )
		throws Exception{
		AjaxMsg msg = new AjaxMsg();
		int id = this.ryjtcyService.addRyjtcy(entity);
		if (id < 0) {
			msg.setSuccess(false);
			msg.setMessage("添加人员家庭成员失败，数据有误，请验证后重试！");
		}else{
			msg.setSuccess(true);
		}
		JSONObject obj =JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	//删除人员的家庭成员信息
	@RequestMapping(params="method=deleteRyjtcy")
	public ModelAndView deleteRyjtcy(HttpServletRequest request,HttpServletResponse response)
	throws Exception{
		String strId =request.getParameter("id");
		int id = NumberUtil.format(strId);
		AjaxMsg msg = new AjaxMsg();
		if (id == 0) {
			msg.setSuccess(false);
			msg.setMessage("删除人员家庭成员信息失败，数据有误，请验证后重试！");
		}else{
			int row = this.ryjtcyService.deleteRyjtcy(id);
			if (row == -1) {
				msg.setSuccess(false);
				msg.setMessage("删除人员家庭成员信息失败！");
			}else if (row < 0){
				msg.setSuccess(false);
				msg.setMessage("删除人员家庭成员信息失败，服务器繁忙，请稍后重试！");
			}else{
				msg.setSuccess(true);
			}
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	//更新人员的家庭成员信息
	@RequestMapping(params="method=updateRyjtcy")
	public ModelAndView updateRyjtcy(HttpServletRequest request,HttpServletResponse response)
	throws Exception{
		String data = request.getParameter("data");
		data = java.net.URLDecoder.decode(data,"UTF-8");
		JSONArray ja = JSONArray.fromObject(data);
		List<RyjtcyEntity> list =(List<RyjtcyEntity>)BeanConvert.boundToArray(RyjtcyEntity.class, ja);
		AjaxMsg msg = new AjaxMsg();
		if (list == null) {
			msg.setSuccess(false);
			msg.setMessage("更新人员家庭成员信息失败，数据有误，请验证后重试！");
	    }else{
	    	int row = this.ryjtcyService.updateRyjtcyList(list);
	    	if (row <= 0) {
				msg.setSuccess(false);
				 msg.setMessage("更新人员家庭成员信息失败,服务器繁忙，请稍后重试！");
			}else{
				msg.setSuccess(true);
			}
	    }
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
}
