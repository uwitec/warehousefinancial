package com.wfms.common.filter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletContextHandler {
	
	private static final String Request_Key = "request";
	
	private static final String Response_Key = "response";
	
	private static ThreadLocal<Map<String, Object>> local = new ThreadLocal<Map<String,Object>>();
	
	private static Map<String, Object> getLocalMap(){
		Map<String, Object> map = local.get();
		if(map == null){
			map = new HashMap<String,Object>();
			local.set(map);
		}
		return map;
	}
	
	public static HttpServletRequest getRequest(){
		return (HttpServletRequest)getLocalMap().get(Request_Key);
	}
	
	public static HttpServletResponse getResponse(){
		return (HttpServletResponse)getLocalMap().get(Response_Key);
	}
	
	public static HttpSession getSession(){
		return getRequest().getSession();
	}
	
	public static String getPath(){
		return getSession().getServletContext().getRealPath("") + File.separator;
	}
	
	public static String getUrl(){
		return getRequest().getContextPath() + "/";
	}
	
	protected static void setRequest(HttpServletRequest request){
		getLocalMap().put(Request_Key, request);
	}
	
	protected static void setResponse(HttpServletResponse response){
		getLocalMap().put(Response_Key, response);
	}
	
	protected static void clear(){
		local.set(null);
	}
}
