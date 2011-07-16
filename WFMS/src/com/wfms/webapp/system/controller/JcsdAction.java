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
import com.wfms.common.util.JSONUtil;
import com.wfms.common.util.NumberUtil;
import com.wfms.common.util.QxUtil;
import com.wfms.dto.system.JcsdForm;
import com.wfms.dto.system.QtdmDto;
import com.wfms.model.system.QtdmEntity;
import com.wfms.service.system.IQtdmglService;

@Controller
@Lazy(true)
@RequestMapping("/system/jcsdAction.do")
public class JcsdAction {
	@Autowired
	private IQtdmglService qtdmglService;

	// 初始化其他代码维护页面
	@RequestMapping(params = "method=initQtdmwh")
	public String initQtdmwh(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if (obj == null) {
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		request.setAttribute("qxlx", qxlx);
		request.setAttribute("mkid", id);
		return "/system/jcsd_qtdm";
	}

	/**
	 * 
	 * <dl>
	 * <b>方法名:getAllJs</b>
	 * <dd>方法作用：
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	 * <dd>
	 * 
	 * @param mapping
	 *            <dd>
	 * @param form
	 *            <dd>
	 * @param request
	 *            <dd>
	 * @param response
	 *            <dd>
	 * @return
	 *            <dd>
	 * @throws Exception
	 *             </dl>
	 */
	@RequestMapping(params = "method=getQtdm")
	public ModelAndView getQtdm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String dmlb = request.getParameter("dmlb");
		if ("".equals(dmlb) || dmlb == null) {
			return null;
		}
		QtdmDto dto = qtdmglService.getQtdm(dmlb);
		JSONArray jay = JSONUtil.formatArray(dto.getQtdmList());
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonArrayModelAndView(jay);
	}

	/**
	 * 
	 * <dl>
	 * <b>方法名:addQtdm</b>
	 * <dd>方法作用：增加其他代码
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	 * <dd>
	 * 
	 * @param mapping
	 *            <dd>
	 * @param form
	 *            <dd>
	 * @param request
	 *            <dd>
	 * @param response
	 *            <dd>
	 * @return
	 *            <dd>
	 * @throws Exception
	 *             </dl>
	 */
	@RequestMapping(params = "method=addQtdm")
	public ModelAndView addQtdm(HttpServletRequest request,
			HttpServletResponse response, JcsdForm jcsdForm) throws Exception {
		int id = qtdmglService.addQtdm(jcsdForm.getQtdm());
		AjaxMsg msg = new AjaxMsg();
		if (id > 0) {
			msg.setSuccess(true);
		} else {
			msg.setSuccess(false);
			msg.setMessage("添加失败，代码简称不能重复，请检查后重试!");
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	@RequestMapping(params = "method=delQtdm")
	public ModelAndView delQtdm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strId = request.getParameter("id");
		int id = NumberUtil.format(strId);
		if (id == 0) {
			return new ModelAndView("error");
		}
		int row = qtdmglService.delQtdm(id);
		AjaxMsg msg = new AjaxMsg();
		if (row <= 0) {
			msg.setSuccess(false);
			msg.setMessage("删除失败，服务器繁忙，请稍后重试!");
		} else {
			msg.setSuccess(true);
		}
		response.setCharacterEncoding("utf-8");
		JSONObject obj = JSONUtil.formatObject(msg);
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	@RequestMapping(params = "method=updQtdm")
	public ModelAndView updQtdm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String data = request.getParameter("data");
		data = java.net.URLDecoder.decode(data, "UTF-8");
		JSONArray ja = JSONArray.fromObject(data);
		List<QtdmEntity> list = new ArrayList<QtdmEntity>();
		for (int i = 0; i < ja.size(); i++) {
			JSONObject obj = (JSONObject) ja.get(i);
			QtdmEntity qtdm = new QtdmEntity();
			try {
				qtdm.setId(obj.getInt("id"));

			} catch (Exception e) {
				break;
			}
			qtdm.setBz(obj.getString("bz"));
			qtdm.setDmjc(obj.getString("dmjc"));
			qtdm.setDmmc(obj.getString("dmmc"));
			qtdm.setDmlb(obj.getString("dmlb"));
			list.add(qtdm);
		}
		int row = qtdmglService.updQtdm(list);
		AjaxMsg msg = new AjaxMsg();
		if (row <= 0) {
			msg.setSuccess(false);
			msg.setMessage("更新失败,服务器繁忙,请稍后重试!");
		} else {
			msg.setSuccess(true);
		}
		response.setCharacterEncoding("utf-8");
		JSONObject obj = JSONUtil.formatObject(msg);
		return MvcUtil.jsonObjectModelAndView(obj);
	}
}
