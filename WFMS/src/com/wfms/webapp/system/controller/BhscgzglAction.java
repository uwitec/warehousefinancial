/**
 *******************************************************************************
 * 文件名：XhscgzglAction.java
 *
 * 描述：学号生成规则管理
 * 
 * 创建日期：Jan 30, 2010 10:41:44 AM
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import xuner.web.mvc.MvcUtil;

import com.wfms.common.entity.AjaxMsg;
import com.wfms.common.util.JSONUtil;
import com.wfms.common.util.NumberUtil;
import com.wfms.common.util.QxUtil;
import com.wfms.dto.system.BhscgzDto;
import com.wfms.model.system.BhscgzszEntity;
import com.wfms.service.system.IBhscgzglService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <dl>
 * Description
 * <dd> 项目名称：数字化校园信息平台ECMS
 * <dd> 类名称：XhscgzglAction
 * <dd> 类描述：
 * <dd> 创建人：Administrator
 * <dd> 创建时间：Jan 30, 2010 10:41:44 AM
 * <dd> 修改人：无
 * <dd> 修改时间：无
 * <dd> 修改备注：无
 * </dl>
 * 
 * @author CYC
 * @see BhscgzglAction
 * @version 1.0
 * 
 */

@Controller
@Lazy(true)
@RequestMapping("/system/bhscgzglAction.do")
public class BhscgzglAction {
	@Autowired
	private IBhscgzglService bhscgzglService;

	@RequestMapping(params = "method=initXhscgzgl")
	public String initXhscgzgl(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if (obj == null) {
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		request.setAttribute("qxlx", qxlx);
		request.setAttribute("mkid", id);
		return "/system/jcsd_xhscgz";
	}

	@RequestMapping(params = "method=initBjdmscgzgl")
	public String initBjdmscgzgl(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if (obj == null) {
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		request.setAttribute("qxlx", qxlx);
		request.setAttribute("mkid", id);
		return "/system/jcsd_bjdmscgz";
	}

	@RequestMapping(params = "method=initBjmcscgzgl")
	public String initBjmcscgzgl(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if (obj == null) {
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		request.setAttribute("qxlx", qxlx);
		return "/system/jcsd_bjmcscgz";
	}

	@RequestMapping(params = "method=getAllBhscgz")
	public ModelAndView getAllBhscgz(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String name = request.getParameter("name");
		BhscgzDto dto = bhscgzglService.loadBhscgzByGzmc(name);
		JSONArray jay = JSONUtil.formatArray(dto.getBhscgzList());
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonArrayModelAndView(jay);
	}

	@RequestMapping(params = "method=updateXhscgz")
	public ModelAndView updateXhscgz(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String data = request.getParameter("data");
		data = java.net.URLDecoder.decode(data, "UTF-8");
		JSONArray ja = JSONArray.fromObject(data);
		List<BhscgzszEntity> list = new ArrayList<BhscgzszEntity>();
		for (int i = 0; i < ja.size(); i++) {
			JSONObject obj = (JSONObject) ja.get(i);
			BhscgzszEntity entity = new BhscgzszEntity();
			try {
				int id = "".equals(obj.getString("id")) ? 0 : obj.getInt("id");
				entity.setId(id);
				entity.setTjid(obj.getInt("tjid"));
				entity.setScsx(obj.getInt("scsx"));
			} catch (Exception e) {
				break;
			}
			entity.setQsxh(obj.getString("qsxh"));
			entity.setClz(obj.getString("clz"));
			entity.setScgzmc(obj.getString("scgzmc"));
			entity.setZdws(NumberUtil.format(obj.getString("zdws")));
			entity.setQsw(NumberUtil.format(obj.getString("qsw")));
			entity.setWs(NumberUtil.format(obj.getString("ws")));
			list.add(entity);
		}
		int row = bhscgzglService.updateBhscgz(list);
		AjaxMsg msg = new AjaxMsg();
		if (row != 0) {
			msg.setSuccess(true);
			msg.setMessage("更新成功");
			msg.setOther(row);
		} else {
			msg.setSuccess(false);
			msg.setMessage("添加失败,服务器繁忙!");
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	@RequestMapping(params = "method=delBhgz")
	public ModelAndView delBhgz(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strId = request.getParameter("id");
		int id = 0;
		try {
			id = Integer.parseInt(strId);
		} catch (Exception e) {
			id = 0;
		}
		int row = bhscgzglService.deleteBhscgz(id);
		AjaxMsg msg = new AjaxMsg();
		if (row > 0) {
			msg.setSuccess(true);
		} else {
			msg.setSuccess(false);
			msg.setMessage("删除失败，服务器繁忙，请稍后重试!");
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
}
