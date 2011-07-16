/**
 *******************************************************************************
 * 文件名：LxdmglAction.java
 *
 * 描述：
 * 
 * 创建日期：Jan 26, 2010 1:37:24 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
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
import com.wfms.common.entity.OtherTreeEntity;
import com.wfms.common.entity.Page;
import com.wfms.common.util.BeanConvert;
import com.wfms.common.util.ConditionBean;
import com.wfms.common.util.JSONUtil;
import com.wfms.common.util.PageUtil;
import com.wfms.common.util.Rule;
import com.wfms.common.util.StringUtil;
import com.wfms.dto.system.LxdmDto;
import com.wfms.model.system.LxdmEntity;
import com.wfms.service.system.ILxdmglService;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：LxdmglAction
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 26, 2010 1:37:24 PM
 *  <dd> 修改人：无
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author GHT
 * @see LxdmglAction
 * @version 1.0
 *
 */
@Controller
@Lazy(true)
@RequestMapping("/system/lxdmglAction.do")
public class LxdmglAction{
	
	@Autowired
	private ILxdmglService lxdmglService;

	/**
	 * 
	 * <dl>
	 * <b>方法名:getLxdmBySjjc</b>
	 * <dd>方法作用：根据简称获取代码
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
	@RequestMapping(params="method=getLxdmBySjjc")
	public ModelAndView getLxdmBySjjc(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String dmjc = request.getParameter("dmjc");
		LxdmDto dto = lxdmglService.loadSubLxdmBySupJc(dmjc);
		JSONArray ary = JSONUtil.formatArray(dto.getLxdmList());
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonArrayModelAndView(ary);
	}
	
	@RequestMapping(params="method=initLxdm")
	public String initLxdm()
			throws Exception {
		return "/system/sjzd";
	}
	/**
	 * 
	 * <dl>
	 * <b>方法名:getLxdmBySjdm</b>
	 * <dd>方法作用：根据上级代码获取代码
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
	@RequestMapping(params="method=getLxdmBySjdm")
	public String getLxdmBySjdm()
			throws Exception {
		return null;
	}
	
	//查询所有的类型代码模块(名称)
	@RequestMapping(params="method=getAllLxdmMk")
	public ModelAndView getAllLxdmMk(HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		String nodeId = request.getParameter("node"); //隐式参数
		JSONArray jay =  null;
		if(!"0".equals(nodeId)) //判段是否是树的根，如果是就读取根节点，不是就读取叶子节点
		{
			String dmmkObj = (String)request.getParameter("nodeText");
			JSONObject nodeTextObj = JSONUtil.formatObject(dmmkObj);
			String dmmk = nodeTextObj.getString(nodeId);
			dmmk = StringUtil.unescape(dmmk);
			List<LxdmEntity> lxdmList = lxdmglService.loadLxdmByMkmc(dmmk);
			List<OtherTreeEntity> treeList = new ArrayList<OtherTreeEntity>(lxdmList.size());
			for(LxdmEntity lxdm:lxdmList)
			{
				OtherTreeEntity otherTree = new OtherTreeEntity();
				otherTree.setLeaf(true);
				otherTree.setQtip(lxdm.getDmmc());
				otherTree.setSingleClickExpand(true);
				otherTree.setText(lxdm.getDmmc());
				otherTree.setId(lxdm.getLxdm());
				treeList.add(otherTree);
			}
			jay = JSONUtil.formatArray(treeList);
		}
		else
		{
			List<String> dmmkList = lxdmglService.loadAllLxdmMk();
			jay = JSONUtil.formatArray(dmmkList);
		}
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonArrayModelAndView(jay);
	}
	
	@RequestMapping(params="method=getDmmcByLxdm")
	public ModelAndView getDmmcByLxdm(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String nodeText = request.getParameter("nodeText");
		String  id = request.getParameter("node");
		List<LxdmEntity> lxdmList = lxdmglService.loadLxdmByMkmc(nodeText);
		List<OtherTreeEntity> treeList = new ArrayList<OtherTreeEntity>(lxdmList.size());
		for(LxdmEntity lxdm:lxdmList)
		{
			OtherTreeEntity otherTree = new OtherTreeEntity();
			otherTree.setLeaf(true);
			otherTree.setQtip(lxdm.getDmmc());
			otherTree.setSingleClickExpand(true);
			otherTree.setText(lxdm.getDmmc());
			otherTree.setId(lxdm.getLxdm());
			treeList.add(otherTree);
		}
		JSONArray jay = JSONUtil.formatArray(treeList);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonArrayModelAndView(jay);
	}
	
	@RequestMapping(params="method=getLxdmBySuperdm")
	public ModelAndView getLxdmBySuperdm(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String superDm = request.getParameter("superDm");
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		String conditions = request.getParameter("conditions");
		Page page = null;
		if (start != null && limit != null && superDm != null) {
			 page = PageUtil.format(start, limit);
		
		//查询条件
		JSONObject conObj = null;
		if(conditions != null && !"".equals(conditions)){
			conditions = java.net.URLDecoder.decode(conditions,"UTF-8");
			conObj = JSONObject.fromObject(conditions);
		}
		List<ConditionBean> conList = JSONUtil.formatConditionList(conObj);
		conList.add(new ConditionBean("sjlxdm",superDm,Rule.EQUAL));
		conList.add(new ConditionBean("lxdm",Rule.ORDERBYASC));
		page.setConditionList(conList);
		
		page = lxdmglService.loadLxdmBySuperDm(page);
		JSONObject jsonObj = JSONUtil.formatObject(page ,"");
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(jsonObj);
		}
		return null;
	}
	
	//添加类型代码
	@RequestMapping(params="method=addLxdm")
	public ModelAndView addLxdm(HttpServletRequest request,
			HttpServletResponse response,
			LxdmEntity entity)
			throws Exception {
		AjaxMsg msg = new AjaxMsg();
		int id = this.lxdmglService.addLxdm(entity);
		if (id < 0) {
			msg.setSuccess(false);
			msg.setMessage("增加类型代码信息失败，数据有误，请验证后重试!");
		} else {
			msg.setSuccess(true);
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(obj);
		return null;
	}
	
	@RequestMapping(params="method=updateLxdm")
	public ModelAndView updateLxdm(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			String data = request.getParameter("data");
			data = java.net.URLDecoder.decode(data,"UTF-8");
			JSONArray ja = JSONArray.fromObject(data);
			
			List<LxdmEntity> list=(List<LxdmEntity>)BeanConvert.boundToArray(LxdmEntity.class, ja);
			AjaxMsg msg = new AjaxMsg();
			if(list == null){
				msg.setSuccess(false);
				msg.setMessage("更新数据失败，数据有误，请验证后重试!");
			}else{
				int row  = lxdmglService.updateLxdm(list);
				if(row <= 0){
					msg.setSuccess(false);
					msg.setMessage("更新数据失败，服务器繁忙，请稍后重试!");
				}else{
					msg.setSuccess(true);
				}
			}
			JSONObject obj = JSONUtil.formatObject(msg);
			response.setCharacterEncoding("utf-8");
			return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	@RequestMapping(params="method=delLxdm")
	public ModelAndView delLxdm(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String dmId = request.getParameter("id");
		int res = lxdmglService.deleteLxdm(Integer.valueOf(dmId));
		AjaxMsg msg = new AjaxMsg();
		if(res>0)
		{
			msg.setSuccess(true);
		}
		else
		{
			msg.setSuccess(false);
			msg.setMessage("删除失败");
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	@RequestMapping(params="method=loadDmmcByLxdm")
	public  String loadDmmcByLxdm(String lxdm)
	throws Exception{
		if(lxdm == null || lxdm == ""){
			return "";
		}
		String dmmc = null;
		try{
			dmmc = lxdmglService.getDmmcByLxdm(lxdm);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if(dmmc != null && dmmc != ""){
			return dmmc;
		}else{
			return "";
		}
	}
}
