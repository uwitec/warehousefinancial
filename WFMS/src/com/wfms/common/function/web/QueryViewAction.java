/**
 * 
 */
package com.wfms.common.function.web;

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

import com.wfms.common.attribute.AjaxMsg;
import com.wfms.common.function.entity.QueryView;
import com.wfms.common.function.service.IQueryViewService;
import com.wfms.common.util.JSONUtil;
import com.wfms.common.util.NumberUtil;

/**
 * @author Administrator
 * 
 */
@Controller
@Lazy(true)
@RequestMapping("/common/queryViewAction.do")
public class QueryViewAction {

	private IQueryViewService queryViewService;

	@Autowired
	public void setQueryViewService(IQueryViewService queryViewService) {
		this.queryViewService = queryViewService;
	}

	@RequestMapping(params = "method=getQueryView")
	public ModelAndView getQueryView(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Object strYhId = request.getSession().getAttribute("userId");
		String strMkId = request.getParameter("mkId");
		if (strYhId == null || strMkId == null) {
			return null;
		}
		int yhId = NumberUtil.format(String.valueOf(strYhId));
		int mkId = NumberUtil.format(strMkId);
		if (yhId == 0 || mkId == 0) {
			return null;
		}
		List<QueryView> list = queryViewService.loadYhMkQueryView(yhId, mkId);
		// JSONArray obj = JSONUtil.formatArray(list,new
		// String[]{"yhid","mkid"});
		JSONArray obj = JSONArray.fromObject(list);
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonArrayModelAndView(obj);
	}

	@RequestMapping(params = "method=addQueryView")
	public ModelAndView addQueryView(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String data = request.getParameter("data");
		data = java.net.URLDecoder.decode(data, "UTF-8");
		Object strYhId = request.getSession().getAttribute("userId");
		String strMkId = request.getParameter("mkId");
		String name = request.getParameter("name");
		AjaxMsg msg = new AjaxMsg();
		if (strYhId == null || strMkId == null) {
			msg.setSuccess(false);
			msg.setMessage("增加查询视图失败，数据有误，请验证后重试�?");
		} else {
			int yhId = NumberUtil.format(String.valueOf(strYhId));
			int mkId = NumberUtil.format(strMkId);
			if (yhId == 0 || mkId == 0) {
				msg.setSuccess(false);
				msg.setMessage("增加查询视图失败，数据有误，请验证后重试�?");
			} else {
				QueryView q = new QueryView();
				q.setConditions(data);
				q.setMkid(mkId);
				q.setStmc(name);
				q.setYhid(yhId);
				int id = queryViewService.addQueryView(q);
				if (id < 0) {
					msg.setSuccess(false);
					msg.setMessage("增加查询视图失败，数据有误，请验证后重试�?");
				} else {
					msg.setSuccess(true);
				}
			}
		}

		JSONObject obj = JSONObject.fromObject(msg);
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	@RequestMapping(params = "method=delQueryView")
	public ModelAndView delQueryView(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strId = request.getParameter("id");
		int id = NumberUtil.format(strId);
		AjaxMsg msg = new AjaxMsg();
		if (id == 0) {
			msg.setSuccess(false);
			msg.setMessage("删除视图失败，数据有误，请验证后重试�?");
		} else {
			int row = queryViewService.deleteQueryView(id);
			if (row < 0) {
				msg.setSuccess(false);
				msg.setMessage("删除视图失败，服务器繁忙，请稍后重试�?");
			} else {
				msg.setSuccess(true);
			}
		}
		JSONObject obj = JSONObject.fromObject(msg);
		response.setCharacterEncoding("utf-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
}
