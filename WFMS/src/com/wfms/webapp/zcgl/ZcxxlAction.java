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
import com.wfms.model.zcgl.ZcxxEntity;
import com.wfms.service.zcgl.IZcxxService;

/**
 *
 */
@Controller
@Lazy(true)
@RequestMapping("/zcgl/zcxxAction.do")
public class ZcxxlAction  {
	@Autowired
	private IZcxxService zcxxService;

	/**
	 * <b>方法名:initZcxx</b>
	 * <dd>方法作用：初始化
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	 * <dd>
	 */
	@RequestMapping(params="method=initZcxx")
	public String initZcxx(
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
		return "/zcgl/zcxx_list";
	}

	/**
	 * <dl>
	 * <b>方法名:getZcxxByPage</b>
	 * <dd>方法作用：分页查询
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	 * <dd>
	 */
	 @RequestMapping(params="method=getZcxxByPage")
	public ModelAndView getZcxxByPage(HttpServletRequest request, HttpServletResponse response)
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

		zcxxService.getZcxxByPage(page);
		JSONObject obj = JSONUtil.formatObject(page, new String[] { "rys" });
		response.setCharacterEncoding("UTF-8");
		return  MvcUtil.jsonObjectModelAndView(obj);
	}

	/**
	 * <dl>
	 * <b>方法名:addZcxx</b>
	 * <dd>方法作用：
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	 * <dd>
	 */
	 @RequestMapping(params="method=addZcxx")
	public ModelAndView addZcxx(HttpServletRequest request, HttpServletResponse response,
			ZcxxEntity entity )
			throws Exception {
		AjaxMsg msg = new AjaxMsg();
		int id = this.zcxxService.addZcxx(entity);
		if (id < 0) {
			msg.setSuccess(false);
			msg.setMessage("增加消防记录信息失败，数据有误，请验证后重试！");
		} else {
			msg.setSuccess(true);
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	/**
	 * <dl>
	 * <b>方法名:updateZcxx</b>
	 * <dd>方法作用：
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	 */
	 @RequestMapping(params="method=updateZcxx")
	public ModelAndView updateZcxx(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String data = request.getParameter("data");
		data = java.net.URLDecoder.decode(data, "UTF-8");
		JSONArray ja = JSONArray.fromObject(data);

		List<ZcxxEntity> list = (List<ZcxxEntity>) BeanConvert.boundToArray(
				ZcxxEntity.class, ja);
		AjaxMsg msg = new AjaxMsg();
		if (list == null) {
			msg.setSuccess(false);
			msg.setMessage("更新数据失败，数据有误，请验证后重试!");
		} else {
			int row = this.zcxxService.updateZcxx(list);
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
	 * <b>方法名:deleteZcxx</b>
	 * <dd>方法作用：
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	 */
	 @RequestMapping(params="method=deleteZcxx")
	public ModelAndView deleteZcxx(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strId = request.getParameter("id");
		int id = NumberUtil.format(strId);
		AjaxMsg msg = new AjaxMsg();
		if (id == 0) {
			msg.setSuccess(false);
			msg.setMessage("删除资产信息失败，数据有误，请验证后重试！");
		} else {
			int row = this.zcxxService.deleteZcxx(id);
			if (row == -1) {
				msg.setSuccess(false);
				msg.setMessage("删除资产信息失败！");
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
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(params = "method=getZcxxList")
	public ModelAndView getZcxxList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	String zclx = request.getParameter("zclx");
    	String type = request.getParameter("ty");
		String column = request.getParameter("column");
    	List<ZcxxEntity> zcxxList = zcxxService.getZcxxList(column,zclx, type);
		JSONArray ary = JSONUtil.formatArray(zcxxList, new String[] {});
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonArrayModelAndView(ary);
	}
    
    /**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params = "method=getZcxxByZclx")
	public ModelAndView getZcxxByZclx(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String zclx = request.getParameter("zclx");
		String column = request.getParameter("column");
		List<ZcxxEntity> zcxxList = null;
		if (zclx != null && !"".equals(zclx) && column !=null && !"".equals(column) ) {
			zcxxList = zcxxService.getZcxxByZclx(column,zclx);
		}
		JSONArray ary = JSONUtil.formatArray(zcxxList, new String[] {});
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonArrayModelAndView(ary);
	}
}
