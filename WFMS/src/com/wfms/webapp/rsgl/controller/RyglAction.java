/**
 *******************************************************************************
 * 文件名：RyAction.java
 *
 * 描述：
 * 
 * 创建日期：Jan 23, 2010 5:41:14 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.webapp.rsgl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import xuner.web.mvc.MvcUtil;

import com.wfms.common.entity.AjaxMsg;
import com.wfms.common.entity.OtherTreeEntity;
import com.wfms.common.entity.Page;
import com.wfms.common.util.BeanConvort;
import com.wfms.common.util.ConditionBean;
import com.wfms.common.util.JSONUtil;
import com.wfms.common.util.NumberUtil;
import com.wfms.common.util.PageUtil;
import com.wfms.common.util.QxUtil;
import com.wfms.common.util.Rule;
import com.wfms.common.util.StringUtil;
import com.wfms.common.util.TreeUtil;
import com.wfms.dto.rsgl.BmDto;
import com.wfms.dto.rsgl.RyDto;
import com.wfms.model.rsgl.RyEntity;
import com.wfms.service.rsgl.IBmglService;
import com.wfms.service.rsgl.IRyglService;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：RyAction
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 23, 2010 5:41:14 PM
 *  <dd> 修改人：无
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see RyglAction
 * @version 1.0
 *
 */

@Controller
@Lazy(true)
@RequestMapping("/rsgl/ryglAction.do")
public class RyglAction  {
	@Autowired
	private IRyglService ryglService;
	@Autowired
	private IBmglService bmglService;
	/**
	 * <dl>
	 * <b>方法名:initRygl</b>
	 * <dd>方法作用：初始化人员管理页面
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选) 
	 * <dd>@param mapping
	 * <dd>@param form
	 * <dd>@param request
	 * <dd>@param response
	 * <dd>@return
	 * <dd>@throws Exception
	 * </dl>
	 */
	@RequestMapping(params="method=initRygl")
	public String initRygl(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if(obj == null){
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		request.setAttribute("qxlx", qxlx);
		request.setAttribute("mkid", id);
		return "/rsgl/rygl_list";
	}
	
	@RequestMapping(params="method=getAllRyxx")
	@ResponseBody
	public Page getAllRyxx(HttpServletRequest request, HttpServletResponse response)
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
		Map<String,Rule> configMap = new HashMap<String,Rule>();
		configMap.put("bm.id", Rule.EQUAL);
		List<ConditionBean> conList = JSONUtil.formatConditionList(conObj,configMap);
		conList.add((new ConditionBean("rzsj",Rule.ORDERBYDESC)));
		page.setConditionList(conList);
		page = ryglService.loadRyForPage(page);
		return page;
		//JSONObject obj = JSONUtil.formatObject(page,true,new String[]{"rys","lss","bzrs"});
		//response.setCharacterEncoding("utf-8");
		//return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	@RequestMapping(params="method=addRy")
	public ModelAndView addRy(HttpServletRequest request, HttpServletResponse response,
			RyEntity ry)
			throws Exception {
		AjaxMsg msg = new AjaxMsg();
		if(ry == null){
			msg.setSuccess(false);
			msg.setMessage("增加人员失败，数据有误，请验证后重试！");
		}else{
			if(ry.getMember()!=null &&
					!StringUtil.isNullOrEmpty(ry.getMember().getLoginid())
					&&!StringUtil.isNullOrEmpty(ry.getMember().getPassword()))
			{
				ry.getMember().setUsername(ry.getRyxm());
			}
			else
			{
				ry.setMember(null);
			}
			int row = ryglService.addRy(ry);
			if(row <0 ){
				msg.setSuccess(false);
				msg.setMessage("增加人员失败，数据有误，请验证后重试！");
			}else{
				msg.setSuccess(true);
			}
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	@RequestMapping(params="method=toUdataRy")
	public String toUdataRy(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ids = request.getParameter("ids");
		request.setAttribute("ids", ids);
		return "/rsgl/rygl_upd";
	}
	
	@RequestMapping(params="method=getRyxxByIds")
	@ResponseBody
	public RyDto getRyxxByIds(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ids = request.getParameter("ids");
		int[] id = NumberUtil.format(ids.split(","));
		RyDto dto = ryglService.getRyByIds(id);
		return dto;
	}
	
	@RequestMapping(params="method=delRyxx")
	public ModelAndView delRyxx(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strId = request.getParameter("ids");
		int[] ids = NumberUtil.format(strId.split(","));
		AjaxMsg msg = new AjaxMsg();
		if(ids == null || ids.length  == 0){
			msg.setSuccess(false);
			msg.setMessage("删除职工失败，数据有误，请验证后重试！");
		}else{
			int row  = ryglService.deleteRy(ids);
			if(row <0 ){
				msg.setSuccess(false);
				msg.setMessage("删除职工失败，职工下又对应教师或班主任关联信息，请删除关联信息重试！");
			}else{
				msg.setSuccess(true);
			}
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	@RequestMapping(params="method=updRyxx")
	public ModelAndView updRyxx(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String data = request.getParameter("data");
		data = java.net.URLDecoder.decode(data,"UTF-8");
		JSONArray ja = JSONArray.fromObject(data);
		
		List<RyEntity> list=(List<RyEntity>) BeanConvort.boundToArray(RyEntity.class, ja);
		AjaxMsg msg = new AjaxMsg();
		if(list == null){
			msg.setSuccess(false);
			msg.setMessage("更新数据失败，数据有误，请验证后重试");
		}else{
			int row  = ryglService.updateRyList(list);
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
	
	//获取部门树节点
	@RequestMapping(params="method=getRyTree")
	public ModelAndView getRyTree(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("node");
		JSONArray obj = null;
		String contextPath = request.getContextPath();
		if("0".equals(id) || "".equals(id)){
			BmDto dto = bmglService.getAllBm();
			List<OtherTreeEntity> list  = TreeUtil.getTreeByBm(dto.getBmList());
			obj = JSONUtil.formatArray(list);
		}else{
			RyDto dto = ryglService.loadRybyBm(id);
			List<OtherTreeEntity> list  = TreeUtil.getTreeByRy(contextPath,dto.getRyList());
			obj = JSONUtil.formatArray(list);
		}
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonArrayModelAndView(obj);
	}
	
	@RequestMapping(params="method=addYhForRy")
	public ModelAndView addYhForRy(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strId = request.getParameter("ryId");
		String dlzh = request.getParameter("dlzh");
		String dlmm = request.getParameter("dlmm");
		int id = NumberUtil.format(strId);
		AjaxMsg msg = new AjaxMsg();
		if(id == 0){
			msg.setSuccess(false);
			msg.setMessage("为职工添加用户失败失败，数据有误，请验证后重试！");
		}else{
			String row  = ryglService.addYhForRy(id, dlzh, dlmm);
			if("-2".equals(row)){
				msg.setSuccess(false);
				msg.setMessage("为职工添加用户失败失败，您输入的用户名已被占用，请重新输入！");
			}else if(StringUtil.isNullOrEmpty(row) ){
				msg.setSuccess(false);
				msg.setMessage("为职工添加用户失败失败，服务器繁忙，稍后重试！");
			}else{
				msg.setSuccess(true);
			}
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	@RequestMapping(params="method=initRygrxx")
	public String initRygrxx(HttpServletRequest request,
			HttpServletResponse response,
			HttpSession session)
			throws Exception {
		Integer ryId = (Integer)session.getAttribute("ryId");
		RyDto dto = ryglService.getRyById(ryId);
		request.setAttribute("ry", dto.getRy());
		return "/bgzdh/rygrxx";
	}
	
	@RequestMapping(params="method=getAllRy")
	public ModelAndView getAllRy(HttpServletRequest request,HttpServletResponse response)
		throws Exception{
		
		List<RyEntity>  list =this.ryglService.getAllRy();
		JSONArray jay = JSONUtil.formatArray(list , new String[]{"bm","rys","lss","bzrs"});
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonArrayModelAndView(jay);
	}
	
	@RequestMapping(params="method=ryImport")
	public String ryImport(HttpServletRequest request,HttpSession session){
		String id = request.getParameter("id");
		Object obj = session.getAttribute("gnmkList");
		if (obj == null) {
			return "error";
		}
		int qxlx = QxUtil.getQx(id,obj);
		request.setAttribute("qxlx", qxlx);
		request.setAttribute("mkid", id);
		return "redirect:/rsgl/ryxx_import.html";
	}
}
