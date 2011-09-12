package com.wfms.common.util;

import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wfms.common.web.views.AbstractMimeView;
import com.wfms.common.web.views.JSONView1;
import com.wfms.common.web.views.MimeFileView;

public class MvcUtil {
	private static final JSONView1 jsonLibView = new JSONView1();
	private static final MimeFileView mimeFileView = new MimeFileView();

	public static ModelAndView jsonObjectModelAndView(Object jsonObject) {
		return new ModelAndView(jsonLibView, "jsonObject", jsonObject);
	}

	public static ModelAndView jsonArrayModelAndView(Object jsonArray) {
		return new ModelAndView(jsonLibView, "jsonArray", jsonArray);
	}

	public static ModelAndView mimeModelAndView(String path) {
		return new ModelAndView(mimeFileView, AbstractMimeView.FILE_PATH, path);
	}
}
