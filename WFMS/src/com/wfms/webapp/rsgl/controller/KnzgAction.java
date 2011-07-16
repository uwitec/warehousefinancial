/**
 *******************************************************************************
 * 文件名：KnzgAction.java
 *
 * 描述：
 * 
 * 创建日期：Apr 23, 2010 6:59:07 PM
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
import com.wfms.dto.rsgl.KnzgDto;
import com.wfms.model.rsgl.KnzgEntity;
import com.wfms.service.rsgl.IKnzgService;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：KnzgAction
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Apr 23, 2010 6:59:07 PM
 *  <dd> 修改人：无
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author ZHANGYONG
 * @see KnzgAction
 * @version 1.0
 *
 */

@Controller
@Lazy(true)
@RequestMapping("/rsgl/knzgAction.do")
public class KnzgAction{
	@Autowired
	private IKnzgService knzgService;

	
	@RequestMapping(params="method=initKnzg")
	public String initKnzg(HttpServletRequest request,HttpServletResponse response)
			throws Exception{
		
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if (obj == null) {
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		request.setAttribute("qxlx", qxlx);
		request.setAttribute("mkid", id);
		return "/rsgl/knzg_list";
	}
	
	@RequestMapping(params="method=getKnzgByPage")
	public ModelAndView getKnzgByPage(HttpServletRequest request,HttpServletResponse response)
			throws Exception{

			String start = request.getParameter("start");
			String limit = request.getParameter("limit");
			String conditions = request.getParameter("conditions");
			Page page = PageUtil.format(start, limit);
			JSONObject conObj = null;
			if (conditions != null && !"".equals(conditions)) {
				conditions = java.net.URLDecoder.decode(conditions, "UTF-8");
				conObj = JSONObject.fromObject(conditions);
			}
			
			List<ConditionBean> conList =JSONUtil.formatConditionList(conObj);
			page.setConditionList(conList);
			page = this.knzgService.getKnzgByPage(page);
			JSONObject obj = JSONUtil.formatObject(page,new String[]{"rys","bzrs","lss"});
			response.setCharacterEncoding("UTF-8");
			return  MvcUtil.jsonObjectModelAndView(obj);
	}
	
	@RequestMapping(params="method=addKnzg")
	public ModelAndView addKnzg(HttpServletRequest request,HttpServletResponse response,
			KnzgEntity entity)
		throws Exception{
		AjaxMsg msg = new AjaxMsg();
		int id = this.knzgService.addOrUpdateKnzg(entity);
		if (id < 0) {
			msg.setSuccess(false);
			msg.setMessage("添加困难职工信息失败，数据有误，请验证后重试！");
		}else{
			msg.setSuccess(true);
		}
		JSONObject obj =JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return  MvcUtil.jsonObjectModelAndView(obj);
	}
	
	@RequestMapping(params="method=deleteKnzg")
	public ModelAndView deleteKnzg(HttpServletRequest request,HttpServletResponse response)
		throws Exception{
		String strId =request.getParameter("id");
		int id = NumberUtil.format(strId);
		AjaxMsg msg = new AjaxMsg();
		if (id == 0) {
			msg.setSuccess(false);
			msg.setMessage("删除困难职工信息失败，数据有误，请验证后重试！");
		}else{
			int row = this.knzgService.deleteKnzg(id);
			if (row == -1) {
				msg.setSuccess(false);
				msg.setMessage("删除困难职工信息失败！");
			}else if (row < 0){
				msg.setSuccess(false);
				msg.setMessage("删除困难职工信息失败，服务器繁忙，请稍后重试！");
			}else{
				msg.setSuccess(true);
			}
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return  MvcUtil.jsonObjectModelAndView(obj);
	}
	
	@RequestMapping(params="method=updateKnzg")
	public ModelAndView updateKnzg(HttpServletRequest request,HttpServletResponse response)
			throws Exception{
			String data = request.getParameter("data");
			data = java.net.URLDecoder.decode(data,"UTF-8");
			JSONArray ja = JSONArray.fromObject(data);
			List<KnzgEntity> list =(List<KnzgEntity>)BeanConvert.boundToArray(KnzgEntity.class, ja);
			AjaxMsg msg = new AjaxMsg();
			if (list == null) {
				msg.setSuccess(false);
				msg.setMessage("更新困难职工信息失败，数据有误，请验证后重试！");
		    }else{
		    	int row = this.knzgService.updateKnzg(list);
		    	if (row <= 0) {
					msg.setSuccess(false);
					 msg.setMessage("更新困难职工信息失败,服务器繁忙，请稍后重试！");
				}else{
					msg.setSuccess(true);
				}
		    }
			JSONObject obj = JSONUtil.formatObject(msg);
			response.setCharacterEncoding("UTF-8");
			return  MvcUtil.jsonObjectModelAndView(obj);
	}	
	
	//根据人员ID查询困难职工
	@RequestMapping(params="method=getKnzgByRyId")
	public ModelAndView getKnzgByRyId(HttpServletRequest request,HttpServletResponse response)
	throws Exception{
		String idStr = request.getParameter("id");
		int id = NumberUtil.format(idStr);
		
		KnzgDto knzgDto = knzgService.loadKnzgByRyId(id);
		JSONObject obj = JSONUtil.formatObject(knzgDto.getKnzg(),new String[] {"ry"});
		response.setCharacterEncoding("UTF-8");
		return  MvcUtil.jsonObjectModelAndView(obj);
	}	
	
	@RequestMapping(params="method=getKnzgById")
	public ModelAndView getKnzgById(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String strId = request.getParameter("id");
		int id = NumberUtil.format(strId);
		KnzgEntity knzg = this.knzgService.getKnzgById(id);
		JSONArray obj = JSONUtil.formatArray(knzg,new String[]{"lss","bzrs","bm"});
		response.setCharacterEncoding("utf-8");
		return  MvcUtil.jsonArrayModelAndView(obj);
	}
	/**
	 * <dl>
	 * <b>方法名:toUpdateKnzg</b>
	 * <dd>方法作用：	跳转到修改页面
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
	
	@RequestMapping(params="method=toUpdateKnzg")
	public String toUpdateKnzg(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		return "/rsgl/knzg_update";
	}
}
