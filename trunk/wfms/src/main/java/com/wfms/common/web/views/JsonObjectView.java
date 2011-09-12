package com.wfms.common.web.views;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.AbstractView;

import com.alibaba.fastjson.JSONObject;


public class JsonObjectView extends AbstractView {
	public JsonObjectView() {
		setContentType("text/json");
	}

	protected void renderMergedOutputModel(Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");

		response.getWriter().println(JSONObject.toJSONString(model));
	}
}