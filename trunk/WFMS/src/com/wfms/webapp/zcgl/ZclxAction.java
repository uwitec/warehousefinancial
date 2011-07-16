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
import com.wfms.model.zcgl.ZclxEntity;
import com.wfms.service.zcgl.ICkxxService;
import com.wfms.service.zcgl.IZclxService;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：XfjcAction
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Apr 15, 2010 1:15:44 PM
 *  <dd> 修改人：无
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author ZHANGYONG
 * @see ZclxAction
 * @version 1.0
 *
 */
@Controller
@Lazy(true)
@RequestMapping("/zcgl/zclxAction.do")
public class ZclxAction  {
	
	@Autowired
	private IZclxService zclxService;
	
	@Autowired
	private ICkxxService ckxxService;

	/**
	 * <b>方法名:initZclx</b>
	 * <dd>方法作用：初始化
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	 * <dd>
	 */
	@RequestMapping(params="method=initZclx")
	public String initZclx( HttpServletRequest request, 
			HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if (obj == null) {
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		request.setAttribute("qxlx", qxlx);
		request.setAttribute("mkid", id);
		return "/zcgl/zclx_list";
	}

	/**
	 * <dl>
	 * <b>方法名:getZclxByPage</b>
	 * <dd>方法作用：分页查询
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	 * <dd>
	 *             </dl>
	 */
	 @RequestMapping(params="method=getZclxByPage")
	public ModelAndView getZclxByPage(HttpServletRequest request, HttpServletResponse response)
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

		zclxService.getZclxByPage(page);
		JSONObject obj = JSONUtil.formatObject(page, new String[] { });
		response.setCharacterEncoding("UTF-8");
		return  MvcUtil.jsonObjectModelAndView(obj);
	}

	/**
	 * <dl>
	 * <b>方法名:addZclx</b>
	 * <dd>方法作用：
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	 * <dd>
	 */
	 @RequestMapping(params="method=addZclx")
	public ModelAndView addZclx(HttpServletRequest request, HttpServletResponse response,
			ZclxEntity entity )
			throws Exception {
		AjaxMsg msg = new AjaxMsg();
		int id = zclxService.addZclx(entity);
		if (id < 0) {
			msg.setSuccess(false);
			msg.setMessage("增加资产类型失败，数据有误，请验证后重试！");
		} else {
			msg.setSuccess(true);
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	/**
	 * <dl>
	 * <b>方法名:updateZclx</b>
	 * <dd>方法作用：
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	 * <dd>
	 */
	 @RequestMapping(params="method=updateZclx")
	public ModelAndView updateZclx(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String data = request.getParameter("data");
		data = java.net.URLDecoder.decode(data, "UTF-8");
		JSONArray ja = JSONArray.fromObject(data);

		List<ZclxEntity> list = (List<ZclxEntity>) BeanConvert.boundToArray(
				ZclxEntity.class, ja);
		AjaxMsg msg = new AjaxMsg();
		if (list == null) {
			msg.setSuccess(false);
			msg.setMessage("更新数据失败，数据有误，请验证后重试!");
		} else {
			int row = zclxService.updateZclx(list);
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
	 * <b>方法名:deleteZclx</b>
	 * <dd>方法作用：
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	 * <dd>
	 *             </dl>
	 */
	 @RequestMapping(params="method=deleteZclx")
	public ModelAndView deleteZclx(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strId = request.getParameter("id");
		int id = NumberUtil.format(strId);
		AjaxMsg msg = new AjaxMsg();
		if (id == 0) {
			msg.setSuccess(false);
			msg.setMessage("删除资产类型失败，数据有误，请验证后重试！");
		} else {
			int row = zclxService.deleteZclx(id);
			if (row == -1) {
				msg.setSuccess(false);
				msg.setMessage("删除资产类型失败！");
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
	 
	 /**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=getZclxList")
	public ModelAndView getZclxList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<ZclxEntity> zclxList = zclxService.getZclxList();
		JSONArray ary = JSONUtil.formatArray(zclxList, new String[] {});
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonArrayModelAndView(ary);
	}
	
	
	@RequestMapping(params = "method=getZclxListByCkId")
	public ModelAndView getZclxListByCkId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ckId = request.getParameter("ckId");
		List<ZclxEntity> zclxList = ckxxService.getZclxListByCkId(ckId);
		JSONArray ary = JSONUtil.formatArray(zclxList, new String[] {});
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonArrayModelAndView(ary);
	}
}
