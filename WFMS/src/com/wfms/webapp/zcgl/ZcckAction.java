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
import com.wfms.model.zcgl.CkdEntity;
import com.wfms.model.zcgl.CkdmxEntity;
import com.wfms.service.zcgl.IZcckService;

@Controller
@Lazy(true)
@RequestMapping("/zcgl/zcckAction.do")
public class ZcckAction {
	
	@Autowired
	private IZcckService zcckService;
	
	@RequestMapping(params="method=initZcck")
	public String initZcck(
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
		return "/zcgl/zcck_list";
	}
	
	
	/**
	 * <dl>
	 * <b>方法名:getCkdmxByPage</b>
	 * <dd>方法作用：分页查询
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	 * <dd>
	 *             </dl>
	 */
	 @RequestMapping(params="method=getCkdmxByPage")
	public ModelAndView getRkdmxByPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		String conditions = request.getParameter("conditions");
		String ckdId = request.getParameter("ckdId");
		Page page = PageUtil.format(start, limit);
		// 查询条件
		JSONObject conObj = null;
		if (conditions != null && !"".equals(conditions)) {
			conditions = java.net.URLDecoder.decode(conditions, "UTF-8");
			conObj = JSONObject.fromObject(conditions);
		}
		List<ConditionBean> conList = JSONUtil.formatConditionList(conObj);
		if (ckdId != null && !"".equals(ckdId)) {//202.202.160.27
			 //sms sanxia sms 2009 
			ConditionBean con = new ConditionBean("ckd.id",ckdId,Rule.EQUAL);
			conList.add(con);
		}
		
		page.setConditionList(conList);

		zcckService.getCkdmxByPage(page);
		JSONObject obj = JSONUtil.formatObject(page, new String[] { });
		response.setCharacterEncoding("UTF-8");
		return  MvcUtil.jsonObjectModelAndView(obj);
	}
	 /**
	  * <dl>
	  * <b>方法名:getZcckdByPage</b>
	  * <dd>方法作用：分页查询
	  * <dd>重写备注：(这里描述重写原因、结果或备注)
	  * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	  * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	  * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	  * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	  * <dd>
	  *             </dl>
	  */
	 @RequestMapping(params="method=getZcckdByPage")
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
		 
		 zcckService.getZcckdByPage(page);
		 JSONObject obj = JSONUtil.formatObject(page, new String[] { "ckdmxs"});
		 response.setCharacterEncoding("UTF-8");
		 return  MvcUtil.jsonObjectModelAndView(obj);
	 }
	
	 
	 /**
		 * <dl>
		 * <b>方法名:addZcckd</b>
		 * <dd>方法作用：
		 * <dd>重写备注：(这里描述重写原因、结果或备注)
		 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
		 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
		 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
		 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
		 * <dd>
		 */
		 @RequestMapping(params="method=addZcckd")
		public ModelAndView addZcrkd(HttpServletRequest request, HttpServletResponse response,
				CkdEntity entity)
				throws Exception {
			AjaxMsg msg = new AjaxMsg();
			String data = request.getParameter("data");
			data = java.net.URLDecoder.decode(data, "UTF-8");
			JSONArray ja = JSONArray.fromObject(data);
			
			List<CkdmxEntity> list = (List<CkdmxEntity>) BeanConvert.boundToArray(CkdmxEntity.class, ja);
			
			int id = zcckService.addCkd(entity,list);
			if (id < 0) {
				msg.setSuccess(false);
				msg.setMessage("增加出库单失败，数据有误，请验证后重试！");
			} else {
				msg.setSuccess(true);
			}
			
			JSONObject obj = JSONUtil.formatObject(msg);
			response.setCharacterEncoding("UTF-8");
			return MvcUtil.jsonObjectModelAndView(obj);
		}
		 
		 
		 /**
			 * <dl>
			 * <b>方法名:deleteCkd</b>
			 * <dd>方法作用：
			 * <dd>重写备注：(这里描述重写原因、结果或备注)
			 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
			 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
			 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
			 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
			 * <dd>
		 */
		@RequestMapping(params="method=deleteCkd")
		public ModelAndView deleteCkd(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strId = request.getParameter("id");
		int id = NumberUtil.format(strId);
		AjaxMsg msg = new AjaxMsg();
		if (id == 0) {
			msg.setSuccess(false);
			msg.setMessage("删除出库单失败，数据有误，请验证后重试！");
		} else {
			int row = zcckService.deleteCkd(id);
			if (row == -1) {
				msg.setSuccess(false);
				msg.setMessage("删除出库单失败，该出库单下还有资产！");
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
		
}
