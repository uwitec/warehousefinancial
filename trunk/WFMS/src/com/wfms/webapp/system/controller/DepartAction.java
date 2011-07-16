package com.wfms.webapp.system.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Location;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import xuner.web.mvc.MvcUtil;

import com.wfms.common.util.BeanConvort;
import com.wfms.common.util.JSONUtil;
import com.wfms.model.system.DepartGenInfo;
import com.wfms.service.system.IDepartService;

@Controller
@Lazy(true)
@RequestMapping("/system/departAction.do")
public class DepartAction {

	@Autowired
	private IDepartService departService;

	@RequestMapping(params = "method=getAllDeparts")
	protected ModelAndView getLocationAsJson() {

		List<DepartGenInfo> departList = departService.loadAllDepartment();
		JSONArray jay = JSONUtil.formatArray(departList, new String[] {
				"parent", "children", "company", "manager", "roles" });
		return MvcUtil.jsonArrayModelAndView(jay);
	}

	// value = "{p_LocationId}.json")
	// @PathVariable("p_LocationId") Integer p_LocationId
	/*
	 * MappingJacksonHttpMessageConverter jsonConverter = new
	 * MappingJacksonHttpMessageConverter(); Location requestedLocation = new
	 * Location(p_LocationId); MediaType jsonMimeType =
	 * MediaType.APPLICATION_JSON; if
	 * (jsonConverter.canWrite(requestedLocation.getClass(), jsonMimeType)) {
	 * try { jsonConverter.write(requestedLocation, jsonMimeType, new
	 * ServletServerHttpResponse(response)); } catch (IOException m_Ioe) { }
	 * catch (HttpMessageNotWritableException p_Nwe) { } }
	 */
}
