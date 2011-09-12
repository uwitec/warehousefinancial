package com.wfms.common.web.views;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONView1 extends AbstractView {
	public JSONView1() {
		setContentType("text/json");
	}

	protected void renderMergedOutputModel(Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Object jsonArray = model.get("jsonArray");

		if (jsonArray != null) {
			out.println(JSONArray.toJSONString(jsonArray));
		} else {
			Object jsonObject = model.get("jsonObject");

			if (jsonObject != null) {
				out.println(JSONObject.toJSONString(jsonObject));
			} else {
				jsonArray = new JSONArray();
				out.println(JSONArray.toJSONString(jsonArray));
			}
		}
	}
}