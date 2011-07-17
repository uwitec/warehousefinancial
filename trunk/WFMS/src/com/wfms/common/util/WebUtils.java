package com.wfms.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.wfms.common.orm.Condition;
import com.wfms.common.orm.Page;


/**
 * Web层参数工具类
 * @author CYC
 * 
 */
public class WebUtils {

	/**
	 * 获取参数列表
	 * 
	 * @param request
	 * @param prefix
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map getParametersStartingWith(HttpServletRequest request,
			String prefix) {
		Enumeration paramNames = request.getParameterNames();
		Map params = new TreeMap();
		if (prefix == null) {
			prefix = "";
		}
		while ((paramNames != null) && (paramNames.hasMoreElements())) {
			String paramName = (String) paramNames.nextElement();
			if ((!"".equals(prefix)) && (!paramName.startsWith(prefix)))
				continue;
			String unprefixed = paramName.substring(prefix.length());
			String[] values = request.getParameterValues(paramName);
			if ((values == null) || (values.length == 0)) {
				continue;
			}
			if (values.length > 1) {
				params.put(unprefixed, values);
			} else {
				params.put(unprefixed, values[0]);
			}
		}

		return params;
	}

	/**
	 * 创建ConditionList
	 * 
	 * @param request
	 * @param filterPrefix
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Condition> creatCondtionList(HttpServletRequest request,
			String filterPrefix) {
		List<Condition> filtrationList = new ArrayList<Condition>();

		Map<String, String> filterParamMap = getParametersStartingWith(request,
				filterPrefix);
		for (Map.Entry<String, String> entry : filterParamMap.entrySet()) {
			String filterName = (String) entry.getKey();
			String value = "";
			if("POST".equals(request.getMethod()))
			{
				value = (String) entry.getValue();
			}
			else if("GET".equals(request.getMethod()))
			{//转码?
				value = StringUtil.iso2utf(entry.getKey());
			}

			if (!StringUtils.isNotBlank(value))
				continue;
			Condition filter = new Condition(filterName, value);
			filtrationList.add(filter);
		}

		return filtrationList;
	}

	/**
	 * 设置Page conditions
	 * 
	 * @param request
	 * @param page
	 */
	public static void setPageParameter(HttpServletRequest request, Page page) {
		if(page == null)
		{
			page = new Page();
		}
		List<Condition> conditionList = creatCondtionList(request, "filter_");
		page.getConditions().addAll(conditionList);

		String fieldName = request.getParameter("fieldName");
		String orderType = request.getParameter("orderType");
		if ((StringUtils.isNotBlank(fieldName))
				&& (StringUtils.isNotBlank(orderType))) {
			page.setOrderByColumn(fieldName);
			page.setOrderByRule(orderType);
		}

		String start = request.getParameter("start");
		if (!StringUtils.isNotBlank(start)) {
			page.setStart(0);
		} else {
			Integer startNo = Integer.valueOf(Integer.parseInt(start));
			page.setStart(startNo);
		}

		String limit = request.getParameter("limit");
		if (!StringUtils.isNotBlank(limit)) {
			//默认
			page.setLimit(20);
		} else {
			Integer limittNo = Integer.valueOf(Integer.parseInt(limit));
			page.setLimit(limittNo);
		}
		
		//向前兼容
		String data = request.getParameter("data");
		if (!StringUtils.isEmpty(data)) {
			try {
				data = URLDecoder.decode(data, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			List<Condition> list = ConditionUtil.format(data,page.getRuleConfig());
			page.getConditions().addAll(list);
		}
	}
	
}