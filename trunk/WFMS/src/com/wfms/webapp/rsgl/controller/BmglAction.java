package com.wfms.webapp.rsgl.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import xuner.web.mvc.MvcUtil;

import com.wfms.common.entity.AjaxMsg;
import com.wfms.common.util.BeanConvort;
import com.wfms.common.util.JSONUtil;
import com.wfms.common.util.QxUtil;
import com.wfms.common.util.StringUtil;
import com.wfms.dto.rsgl.BmDto;
import com.wfms.model.system.DepartGenInfo;
import com.wfms.service.system.IDepartService;

@Controller
@Lazy(true)
@RequestMapping("/rsgl/bmglAction.do")
public class BmglAction {
	@Autowired
//	private IdeptService deptService;
	private IDepartService deptService;

	@RequestMapping(params = "method=initBmgl")
	public String initBmgl(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if (obj == null) {
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		request.setAttribute("qxlx", qxlx);
		request.setAttribute("mkid", id);
		return "/rsgl/bmgl_list";
	}

	@RequestMapping(params = "method=getAllBm")
	@ResponseBody
	public BmDto getAllBm() throws Exception {
//		BmDto dto = deptService.getAllBm();
//		return dto;
		BmDto bd = new BmDto();
		bd.setBmList(deptService.loadAllDepartment());
		return bd;
	}

	@RequestMapping(params = "method=getAllBmForList")
	@ResponseBody
	public List<DepartGenInfo> getAllBmForList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//		BmDto dto = deptService.getAllBm();
//		return dto.getBmList();
		return deptService.loadAllDepartment();
	}

	@RequestMapping(params = "method=addBm")
	public ModelAndView addBm(HttpServletRequest request,
			HttpServletResponse response, DepartGenInfo bm) throws Exception {
		bm.setParent(new DepartGenInfo("0"));
//		int row = deptService.addBm(bm);
		String row = deptService.addDepartment(bm);
		AjaxMsg msg = new AjaxMsg();
		if (row != null && !row.equals("")) {
//		if (row > 0) {
			msg.setSuccess(true);
		} else {
			msg.setSuccess(false);
			msg.setMessage("添加失败,服务器繁忙,请稍后重试!");
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	@RequestMapping(params = "method=delBm")
	public ModelAndView delBm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strId = request.getParameter("id");
		AjaxMsg msg = new AjaxMsg();
		if (StringUtil.isNullOrEmpty(strId)) {
			msg.setSuccess(false);
			msg.setMessage("删除失败,数据有误!");
		} else {
//			int row = deptService.deleteBm(strId);
			int row = deptService.deleteDepartment(strId);
			if (row > 0) {
				msg.setSuccess(true);
			} else if (row == -1) {
				msg.setSuccess(false);
				msg.setMessage("删除失败,该部门下还有用户,请先删除部门下的用户!");
			} else {
				msg.setSuccess(false);
				msg.setMessage("删除失败,服务器繁忙!");
			}
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	@RequestMapping(params = "method=updBm")
	public ModelAndView updBm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String data = request.getParameter("data");
		data = java.net.URLDecoder.decode(data, "UTF-8");
		JSONArray ja = JSONArray.fromObject(data);
		List<DepartGenInfo> bmList = new ArrayList<DepartGenInfo>();
		bmList = BeanConvort.boundToArray(DepartGenInfo.class, ja);
//		int row = deptService.updateBm(bmList);
		int row = deptService.UpdateDepartment(bmList);
		AjaxMsg msg = new AjaxMsg();
		if (row > 0) {
			msg.setSuccess(true);
		} else {
			msg.setSuccess(false);
			msg.setMessage("修改失败,服务器繁忙,请稍后重试!");
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
}
