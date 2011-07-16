package com.wfms.webapp.jxc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.wfms.model.jxc.jcsz.JxcRyglEntity;
import com.wfms.service.jxc.jcsz.IJxcRyglService;

@Controller
@Lazy(true)
@RequestMapping("/jxc/JxcRyglAction.do")
public class JxcRyglAction {
	@Autowired
	private IJxcRyglService RyglService;


	/**
	 * 调用页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(params = "method=initRygl")
	public String initRygl(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if (obj == null) {
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		if(qxlx < 1){
			return "error";
		}
		request.setAttribute("qxlx", qxlx);
		request.setAttribute("mkid", id);
		return "/jxc/jcsz/jxc_rygl";
	}
	/**
	 * 
	 * @param 数据初始化
	 * @param response
	 * @return
	 * @throws Exception
	 *  */
	
	@RequestMapping(params="method=getAllRygl")
	public ModelAndView getAllRygl(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		String conditions =request.getParameter("conditions");
		Page page = PageUtil.format(start, limit);
		JSONObject conObj = null;
		if (conditions != null && !"".equals(conditions)) {
			conditions = java.net.URLDecoder.decode(conditions, "UTF-8");
			conObj = JSONObject.fromObject(conditions);
		}
		List<ConditionBean> conList = JSONUtil.formatConditionList(conObj);
		page.setConditionList(conList);
		page  = this.RyglService.getAllRygl(page);
		JSONObject obj = JSONUtil.formatObject(page,new String[]{});
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	/**
	 * 根据id删除信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="method=deleteRygl")
	public ModelAndView deleteRygl(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strId = request.getParameter("id");
		int id = NumberUtil.format(strId);
		AjaxMsg msg = new AjaxMsg();
		if(id == 0){
			msg.setSuccess(false);
			msg.setMessage("删除信息失败，数据有误，请验证后重试！");
		}else{
			int row = RyglService.deleteRygl(id);
			
			if(row == -1){
				msg.setSuccess(false);
				msg.setMessage("删除信息失败！");
			}else if( row < 0 ){
				msg.setSuccess(false);
				msg.setMessage("删除失败，服务器繁忙，请稍后重试！");
			}else{
				msg.setSuccess(true);
			}
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	/**
	 * 添加基地信息
	 */
	@RequestMapping(params="method=addRygl")
	public ModelAndView addRygl(HttpServletRequest request,HttpServletResponse response,
		JxcRyglEntity Rygl,HttpSession session) throws Exception{
		AjaxMsg msg = new AjaxMsg();
		int id = this.RyglService.addRygl(Rygl);
		if (id < 0) {
			msg.setSuccess(false);
			msg.setMessage("添加车间信息失败，数据有误，请验证后重试!");
		}else{
			msg.setSuccess(true);
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	} 
	/**
	 * 修改基地信息
	 */
	@RequestMapping(params="method=updateRygl")
	public ModelAndView updateRygl(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String data = request.getParameter("data");
		data = java.net.URLDecoder.decode(data,"UTF-8"); 
		JSONArray ja = JSONArray.fromObject(data); //把字符串转换成JSON对象
		List<JxcRyglEntity> list = (List<JxcRyglEntity>) BeanConvert.boundToArray(JxcRyglEntity.class, ja);//把JSON对象转换成java对象
		AjaxMsg msg = new AjaxMsg();
		if(list == null){
			msg.setSuccess(false);
			msg.setMessage("更新数据失败，数据有误，请验证后重试!");
		}else{
			int row  = RyglService.updateRygl(list);
			if(row <= 0){
				msg.setSuccess(false);
				msg.setMessage("更新数据失败，服务器繁忙，请稍后重试!");
			}else{
				msg.setSuccess(true);
			}
		}
		JSONObject obj = JSONUtil.formatObject(msg); //msg转换成JSON对象
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	/**
	 * 查询客户信息  包括供货商和经销商
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="method=getAllRyglByJoin")
	public ModelAndView getAllRyglByJoin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String khlb =request.getParameter("khlb");
		String sqlwhere = "供货商(厂家)";
		if(khlb.equals("1")){
			sqlwhere = "经销商(客户)";
		}
		List<JxcRyglEntity> page  = this.RyglService.getAllRyglByJoin(sqlwhere);
		JSONArray obj = JSONUtil.formatArray(page,new String[]{});
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonArrayModelAndView(obj);
	}
}
