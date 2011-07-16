package com.wfms.webapp.system.controller;

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
import com.wfms.common.util.BeanConvert;
import com.wfms.common.util.JSONUtil;
import com.wfms.common.util.QxUtil;
import com.wfms.dto.system.XtszDto;
import com.wfms.model.system.XtszEntity;
import com.wfms.service.system.IXtszglService;

@Controller
@Lazy(true)
@RequestMapping("/system/xtszglAction.do")
public class XtszglAction{
	
	@Autowired
	private IXtszglService xtszglService;

	//初始化学校基本信息设置
	@RequestMapping(params="method=initXxjbxxSz")
	public String initXxjbxxSz(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if(obj == null){
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		request.setAttribute("qxlx", qxlx);
		request.setAttribute("mkid", id);
		return "/system/jcsd_xtsz";
	}
	
	@RequestMapping(params="method=getKdXtsz")
	public ModelAndView getKdXtsz(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		XtszDto dto = xtszglService.getKdXtsz();
		JSONArray jay = JSONUtil.formatArray(dto.getXtszList());
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonArrayModelAndView(jay);
	}
	
	@RequestMapping(params="method=updateKdXtsz")
	public ModelAndView updateKdXtsz(HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String data = request.getParameter("data");
		data = java.net.URLDecoder.decode(data,"UTF-8");
		JSONArray ja = JSONArray.fromObject(data);
		
		List<XtszEntity> list=(List<XtszEntity>) BeanConvert.boundToArray(XtszEntity.class, ja);
		AjaxMsg msg = new AjaxMsg();
		if(list == null){
			msg.setSuccess(false);
			msg.setMessage("更新数据失败，数据有误，请验证后重试");
		}else{
			int row  = xtszglService.updateXtsz(list);
			if(row <= 0){
				msg.setSuccess(false);
				msg.setMessage("更新数据失败，服务器繁忙，请稍后重试");
			}else{
				msg.setSuccess(true);
			}
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
}
