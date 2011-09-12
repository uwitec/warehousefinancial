package com.wfms.common.web.views;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.servlet.view.AbstractView;

public class JsonView extends AbstractView {
	public static final String DEFAULT_CONTENT_TYPE = "application/json";
	private ObjectMapper objectMapper = new ObjectMapper();
	private JsonEncoding encoding = JsonEncoding.UTF8;

	@Override
	protected void renderMergedOutputModel(Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> value = new HashMap<String, Object>();// filterModel(model);
		Object renderValue = null;
		if (value.size() == 1) {
			renderValue = value.values().iterator().next();
		} else {
			renderValue = value;
		}
		JsonGenerator generator = objectMapper.getJsonFactory()
				.createJsonGenerator(response.getOutputStream(), encoding);
		objectMapper.writeValue(generator, renderValue);
	}
}
// //Controller中返回jsonView数据
// @RequestMapping(value = "gen", method = RequestMethod.POST)
// public ModelAndView generate(HttpServletRequest req) {
// return new ModelAndView("json").addObject(token);//这里返回的view名称是json
// }
// }