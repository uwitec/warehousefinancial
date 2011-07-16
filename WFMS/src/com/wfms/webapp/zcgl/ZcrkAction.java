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
import com.wfms.common.util.Rule;
import com.wfms.model.zcgl.RkdEntity;
import com.wfms.model.zcgl.RkdmxEntity;
import com.wfms.service.zcgl.IZcrkService;


@Controller
@Lazy(true)
@RequestMapping("/zcgl/zcrkAction.do")
public class ZcrkAction {
	
	@Autowired
	private IZcrkService zcrkService;
	
	@RequestMapping(params="method=initZcrk")
	public String initZcrk(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
		return "/zcgl/zcrk_list";
	}
	
	@RequestMapping(params="method=initKcll")
	public String initKcll(
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
		return "/zcgl/kcll_list";
	}
	
	@RequestMapping(params="method=initCxtj")
	public String initCxtj(
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
		return "/zcgl/cxtj_list";
	}
	
	
	/**
	 * <dl>
	 * <b>方法名:getRkdmxByPage</b>
	 */
	 @RequestMapping(params="method=getRkdmxByPage")
	public ModelAndView getRkdmxByPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		String conditions = request.getParameter("conditions");
		String rkdId = request.getParameter("rkdId");
		Page page = PageUtil.format(start, limit);
		// 查询条件
		JSONObject conObj = null;
		if (conditions != null && !"".equals(conditions)) {
			conditions = java.net.URLDecoder.decode(conditions, "UTF-8");
			conObj = JSONObject.fromObject(conditions);
		}
		List<ConditionBean> conList = JSONUtil.formatConditionList(conObj);
		if (rkdId != null && !"".equals(rkdId)) {
			ConditionBean con = new ConditionBean("rkd.id",rkdId,Rule.EQUAL);
			conList.add(con);
		}
		page.setConditionList(conList);

		zcrkService.getRkdmxByPage(page);
		JSONObject obj = JSONUtil.formatObject(page, new String[] { });
		response.setCharacterEncoding("UTF-8");
		return  MvcUtil.jsonObjectModelAndView(obj);
	}
	 /**
	  * <dl>
	  * <b>方法名:getZcrkdByPage</b>
	  */
	 @RequestMapping(params="method=getZcrkdByPage")
	 public ModelAndView getZcrkdByPage(HttpServletRequest request, HttpServletResponse response)
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
		 
		 zcrkService.getZcrkdByPage(page);
		 JSONObject obj = JSONUtil.formatObject(page, new String[] { "rkdmxs"});
		 response.setCharacterEncoding("UTF-8");
		 return  MvcUtil.jsonObjectModelAndView(obj);
	 }
	
	 
	 /**
		 * <dl>
		 * <b>方法名:addZcrkd</b>
		 */
		 @RequestMapping(params="method=addZcrkd")
		public ModelAndView addZcrkd(HttpServletRequest request, HttpServletResponse response,
				RkdEntity entity )
				throws Exception {
			AjaxMsg msg = new AjaxMsg();
			String data = request.getParameter("data");
			data = java.net.URLDecoder.decode(data, "UTF-8");
			JSONArray ja = JSONArray.fromObject(data);
			
			List<RkdmxEntity> list = (List<RkdmxEntity>) BeanConvert.boundToArray(
					RkdmxEntity.class, ja);
			
				int id = zcrkService.addRkd(entity,list);
				if (id < 0) {
					msg.setSuccess(false);
					msg.setMessage("增加入库单失败，数据有误，请验证后重试！");
				} else {
					msg.setSuccess(true);
				}
			
			JSONObject obj = JSONUtil.formatObject(msg);
			response.setCharacterEncoding("UTF-8");
			return MvcUtil.jsonObjectModelAndView(obj);
		}
		 
		 
		 /**
		 */
		@RequestMapping(params="method=deleteRkd")
		public ModelAndView deleteRkd(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strId = request.getParameter("id");
		int id = NumberUtil.format(strId);
		AjaxMsg msg = new AjaxMsg();
		if (id == 0) {
			msg.setSuccess(false);
			msg.setMessage("删除入库单失败，数据有误，请验证后重试！");
		} else {
			int row = zcrkService.deleteRkd(id);
			if (row == -1) {
				msg.setSuccess(false);
				msg.setMessage("删除入库单失败，该入库单下还有资产！");
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
		@RequestMapping(params = "method=getRkdmxList")
		public ModelAndView getRkdmxList(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			String zclx = request.getParameter("zclx");
			String column = request.getParameter("column");
			List<RkdmxEntity> rkdmxList =  zcrkService.getRkdmxByZclx(column,zclx);
			JSONArray ary = JSONUtil.formatArray(rkdmxList, new String[] {});
			response.setCharacterEncoding("utf-8");
			return MvcUtil.jsonArrayModelAndView(ary);
	  }	
		
	
		
		/**
		  */
		 @RequestMapping(params="method=getCkzcByPage")
		 public ModelAndView getCkzcByPage(HttpServletRequest request, HttpServletResponse response)
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
			 
			 zcrkService.getCkzcByPage(page);
			 JSONObject obj = JSONUtil.formatObject(page, new String[] { });
			 response.setCharacterEncoding("UTF-8");
			 return  MvcUtil.jsonObjectModelAndView(obj);
		 }	
}
