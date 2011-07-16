/**
 *******************************************************************************
 * 文件名：ResourceFilter.java
 *
 * 描述�?
 * 
 * 创建日期：Mar 3, 2010 4:36:56 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2009 足下科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.Filter;    
import javax.servlet.FilterConfig;    
import javax.servlet.ServletRequest;    
import javax.servlet.ServletResponse;    
import javax.servlet.FilterChain;    
import javax.servlet.ServletException;    
import javax.servlet.http.HttpServletRequest;    
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;    
import java.util.Iterator;    
import java.util.Set;    
import java.util.HashSet;    
//    
import org.apache.commons.logging.Log;    
import org.apache.commons.logging.LogFactory;    
   
/** 
* This Filter class handle the security of the application. 
* 
* It should be configured inside the web.xml. 
* 
* @author Derek Y. Shen 
*/   
public class ResourceFilter implements Filter {    
	//the login page uri    
	private static final String LOGIN_PAGE_URI = "login.html";    
   
	//the logger object    
	private Log logger = LogFactory.getLog(this.getClass());    
	   
	public static final String INDEX_PAGE = "/yhglAction.do?method=login"; 
	public static final String ADMIN_PAGE = "/login.jsp"; 
    public static final String[] EXCEPT_PAGE = {"test.jsp"}; 

	//a set of restricted resources    
	private Set restrictedResources;    
	private Set invalidateResources;   
	/** 
	   * Initializes the Filter. 
	   */   
	public void init(FilterConfig filterConfig) throws ServletException {    
	  this.restrictedResources = new HashSet();    
	  this.invalidateResources = new HashSet();
	  this.restrictedResources.add(".do");    
	  this.restrictedResources.add(".jsp");    
	  this.restrictedResources.add(".html");    
	  this.restrictedResources.add(".htm");
	  
	  this.invalidateResources.add(".js");
	  this.invalidateResources.add(".css");
	  this.invalidateResources.add(".jpg");
	  this.invalidateResources.add(".gif");
	  this.invalidateResources.add(".swf");
	}    
	   
	/** 
	   * Standard doFilter object. 
	   */   
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)    
	   throws IOException, ServletException {    
	  this.logger.warn("doFilter");    
	      
	   String contextPath = ((HttpServletRequest)req).getContextPath();    
	   String requestUri = ((HttpServletRequest)req).getRequestURI();    
	   String refer = ((HttpServletRequest)req).getHeader("referer");
	  this.logger.warn("contextPath = " + contextPath);    
	  this.logger.warn("requestUri = " + requestUri);    
	  this.logger.warn("referer ="+refer);    
	  if ((this.containInvalidateResource(requestUri) && !validateAccess(refer)) || !this.authorize((HttpServletRequest)req)) {    
	   this.logger.warn("authorization failed");    
	    ((HttpServletRequest)req).getRequestDispatcher(LOGIN_PAGE_URI).forward(req, res);    
	   }    
	  else {    
	   this.logger.warn("authorization succeeded");    
	    chain.doFilter(req, res);    
	   }    
	}    
	   
	public void destroy() {}    
	 
	/**
	 * 
	 * <dl>
	 * <b>方法�?:contains</b>
	 * <dd>方法作用：是否包含指定访问路�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param value
	 * <dd>@param contextPath
	 * <dd>@return
	 * </dl>
	 */
	private boolean contains(String value, String contextPath) {    
	   Iterator ite = this.restrictedResources.iterator();    
	      
	  while (ite.hasNext()) {    
	    String restrictedResource = (String)ite.next();    
	       
	   if ((contextPath + restrictedResource).equalsIgnoreCase(value)) {    
	    return true;    
	    }    
	   }    
	      
	  return false;    
	}    
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:containInvalidateResource</b>
	 * <dd>方法作用：是否包含不能直接访问的资源
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param value
	 * <dd>@return
	 * </dl>
	 */
	private boolean containInvalidateResource(String value) {    
		Iterator ite = this.invalidateResources.iterator();    
		while (ite.hasNext()) {    
			String invalidateResource = (String)ite.next();    
			logger.warn("invalidateResource Index="+invalidateResource.indexOf(value));
			if ((invalidateResource).indexOf(value)!=-1) {    
				return true;    
			}    
		}    
		return false;    
	}
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:validateAccess</b>
	 * <dd>方法作用：验证访问来源是否合�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param value
	 * <dd>@return
	 * </dl>
	 */
	private boolean validateAccess(String value) {    
		Iterator ite = this.restrictedResources.iterator();    
		while (ite.hasNext()) {    
			String invalidateResource = (String)ite.next();    
			logger.warn("restrictedResources Index="+invalidateResource.indexOf(value));
			if ((invalidateResource).indexOf(value)!=-1) {    
				return true;    
			}    
		}    
		return false;    
	}    
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:authorize</b>
	 * <dd>方法作用：验证授权信�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param req
	 * <dd>@return
	 * </dl>
	 */
	private boolean authorize(HttpServletRequest req) {    
	   return true;
	}   
	
	private boolean validateAccessPath(HttpServletRequest request,HttpServletResponse response,String vlaue)
	{
		HttpSession session=request.getSession();
		boolean falg = false; 
        for (int i = 0; i < EXCEPT_PAGE.length; i++){ 
            if (vlaue.indexOf(EXCEPT_PAGE[i]) >= 0){ 
                falg = true; 
                break; 
            } 
        } 
        if ( vlaue.indexOf(INDEX_PAGE) == -1 && 
    		vlaue.indexOf(ADMIN_PAGE) == -1 && 
    		vlaue.indexOf(".jsp") > -1 && !falg || 
        (vlaue.indexOf(".do") == 28 && 
        		vlaue.indexOf(".gif") == -1 ) || 
        (vlaue.indexOf(".do") == -1 && 
        		vlaue.indexOf(".gif") == 36)|| 
        (vlaue.indexOf(".do") == 27 && 
        		vlaue.indexOf(".gif") == -1)|| 
        (vlaue.indexOf(".do") == 29 && 
        		vlaue.indexOf(".gif") == -1)|| 
        (vlaue.indexOf(".do") == 33 && 
        		vlaue.indexOf(".gif") == -1)) { 
            // 判断当前页是否是重定向以后的登录页面，如果是就不做session的判断，防止出现死循�? 
            String ref = request.getHeader("REFERER");  //是否是从地址栏直接输入的地址吗？ 
            if (session == null || (ref == null) || (ref.equals(""))) { 
                return false;
            } 
            return true;
        }
        return true;
	}
	
}   
