package com.wfms.common.web.views;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;

public abstract class AbstractJsonView extends AbstractView {
	public AbstractJsonView() {
		setContentType("text/json");
	}

	protected void renderMergedOutputModel(Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONArray jsonArray = new JSONArray();
		buildJsonDocument(model, jsonArray, request, response);
		response.getWriter().println(jsonArray.toJSONString());
	}

	protected abstract void buildJsonDocument(Map paramMap,
			JSONArray paramJSONArray,
			HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse) throws JSONException;
}