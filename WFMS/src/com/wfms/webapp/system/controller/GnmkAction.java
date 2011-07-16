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
import com.wfms.common.entity.TreeEntity;
import com.wfms.common.util.BeanConvort;
import com.wfms.common.util.JSONUtil;
import com.wfms.common.util.NumberUtil;
import com.wfms.common.util.QxUtil;
import com.wfms.common.util.TreeUtil;
import com.wfms.dto.system.GnmkDto;
import com.wfms.model.system.ModuleGenInfo;
import com.wfms.service.system.IGnmkService;

@Controller
@Lazy(true)
@RequestMapping("/system/gnmkAction.do")
public class GnmkAction {
	@Autowired
	private IGnmkService gnmkService;

	@RequestMapping(params = "method=getTree")
	public ModelAndView getTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String contextPath = request.getContextPath();
		String text = request.getParameter("text");
		Object obj = request.getSession().getAttribute("gnmkList");
		if (obj != null) {
			List<ModuleGenInfo> list;
			if (!"".equals(text) && text != null) {
				list = TreeUtil.filterGnmk((List<ModuleGenInfo>) obj, text);
			} else {
				list = (List<ModuleGenInfo>) obj;
			}
			GnmkDto dto = gnmkService.getAllGnmk(list);
			List<TreeEntity> treeList = TreeUtil.initTree(contextPath,dto.getGnmkList());
			JSONArray jay = JSONUtil.formatArray(treeList,true);
			response.setCharacterEncoding("UTF-8");
			return MvcUtil.jsonArrayModelAndView(jay);
		}
		return null;
	}

	@RequestMapping(params = "method=getAllTree")
	public ModelAndView getAllTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String contextPath = request.getContextPath();
		GnmkDto dto = gnmkService.getAllGnmk();
		List<TreeEntity> treeList = TreeUtil.initTree(contextPath,dto.getGnmkList());
		JSONArray jay = JSONUtil.formatArray(treeList,true);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonArrayModelAndView(jay);
	}

	// 初始化功能模块管理页面
	@RequestMapping(params = "method=initGnmkgl")
	public String initGnmkgl(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if (obj == null) {
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		request.setAttribute("qxlx", qxlx);
		return "/system/gnmk_edit";
		
	}

	// 得到子级功能模块
	@RequestMapping(params = "method=getSonGnmk")
	public ModelAndView getSonGnmk(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strId = request.getParameter("id");
		int id = 0;
		try {
			id = Integer.parseInt(strId);
		} catch (NumberFormatException e) {
			id = 0;
		}
		GnmkDto dto = gnmkService.getSonGnmk(id);
		JSONObject job = JSONUtil.formatObject(dto, "right");
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(job);
	}

	// 添加功能模块
	@RequestMapping(params = "method=addGnmk")
	public ModelAndView addGnmk(HttpServletRequest request,
			HttpServletResponse response, ModuleGenInfo gnmk) throws Exception {
		int id = gnmkService.addGnmk(gnmk);
		AjaxMsg msg = new AjaxMsg();
		if (id != -1) {
			msg.setSuccess(true);
			msg.setMessage("添加成功");
			msg.setOther(id);
		} else {
			msg.setSuccess(false);
			msg.setMessage("添加失败,服务器繁忙!");
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	// 添加功能模块
	@RequestMapping(params = "method=delGnmk")
	public ModelAndView delGnmk(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strId = request.getParameter("id");
		int id = 0;
		try {
			id = Integer.parseInt(strId);
		} catch (Exception e) {
			id = 0;
		}
		int row = gnmkService.delGnmk(id);
		AjaxMsg msg = new AjaxMsg();
		if (row > 0) {
			msg.setSuccess(true);
			msg.setMessage("删除成功!您删除了" + row + "条记录!");
		} else if (row == -1) {
			msg.setSuccess(false);
			msg.setMessage("删除失败,该节点含有子节点,请先删除子节点!");
		} else {
			msg.setSuccess(false);
			msg.setMessage("删除失败,该模块已被授权,请先删除权限!");
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	@RequestMapping(params = "method=updGnmk")
	public ModelAndView updGnmk(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String data = request.getParameter("data");
		data = java.net.URLDecoder.decode(data, "UTF-8");
		JSONArray ja = JSONArray.fromObject(data);
		List<ModuleGenInfo> list = BeanConvort.boundToArray(ModuleGenInfo.class, ja);
		int row = gnmkService.updGnmkList(list);
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

	// 获取角色已有功能模块,返回树
	@RequestMapping(params = "method=getJsyyGnmk")
	public ModelAndView getJsyyGnmk(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String contextPath = request.getContextPath();
		String strId = request.getParameter("id");
		int jsId = NumberUtil.format(strId);
		if (jsId == 0) {
			return null;
		}
		GnmkDto dto = gnmkService.getJsyyGnmk(jsId);
		List<TreeEntity> treeList = TreeUtil.initTree(contextPath,dto.getGnmkList());
		//JSONArray jay = JSONUtil.formatArray(treeList);
		JSONArray jay = JSONUtil.formatArray(treeList);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonArrayModelAndView(jay);
	}

	// 获取角色未有功能模块,返回树
	@RequestMapping(params = "method=getJswyGnmk")
	public ModelAndView getJswyGnmk(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String contextPath = request.getContextPath();
		String strId = request.getParameter("id");
		int jsId = NumberUtil.format(strId);
		if (jsId == 0) {
			return null;
		}
		GnmkDto dto = gnmkService.getJswyGnmk(jsId);
		List<TreeEntity> treeList = TreeUtil.initTree(contextPath,dto.getGnmkList());
//		JSONArray jay = JSONUtil.formatArray(treeList);
		JSONArray jay = JSONUtil.formatArray(treeList);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonArrayModelAndView(jay);
	}

	// 获取所有权限未有的功能模块,返回下拉列表数据
	@RequestMapping(params = "method=getAllForCmb")
	public ModelAndView getAllForCmb(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GnmkDto dto = gnmkService.getAllGnmk();
		JSONArray jay = JSONUtil.formatArray(dto.getGnmkList(), "right");
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonArrayModelAndView(jay);
	}
}
