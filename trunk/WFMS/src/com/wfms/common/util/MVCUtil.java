package com.wfms.common.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.wfms.common.web.views.JSONView;

public class MVCUtil {
	private static final JSONView jsonLibView = new JSONView();
	public static ModelAndView jsonObjectModelAndView(JSONObject jsonObject) {
		return new ModelAndView(jsonLibView, "jsonObject", jsonObject);
	}

	public static ModelAndView jsonArrayModelAndView(JSONArray jsonArray) {
		return new ModelAndView(jsonLibView, "jsonArray", jsonArray);
	}
}
