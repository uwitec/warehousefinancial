package com.wfms.common.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wfms.common.system.constant.Constant;
import com.wfms.common.system.web.SessionManager;

public class SessionFilter implements Filter {

	private Log logger = LogFactory.getLog(SessionFilter.class);

	private static final String LOGIN_PAGE_URI = "login.html";
	public static final String INDEX_PAGE = "/yhglAction.do?method=login";
	public static final String ADMIN_PAGE = "/login.jsp";
	public static final String[] EXCEPT_PAGE = { "test.jsp" };

	private Set<String> restrictedResources;
	private Set<String> invalidateResources;

	public void destroy() {
		SessionManager.stop();
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		this.logger.warn("doFilter");

		String contextPath = ((HttpServletRequest) req).getContextPath();
		String requestUri = ((HttpServletRequest) req).getRequestURI();
		String refer = ((HttpServletRequest) req).getHeader("referer");
		this.logger.warn("contextPath = " + contextPath);
		this.logger.warn("requestUri = " + requestUri);
		this.logger.warn("referer =" + refer);
		if ((this.containInvalidateResource(requestUri)
				&& !refer.endsWith(LOGIN_PAGE_URI) && !validateAccess(refer))
				|| !this.authorize()) {
			this.logger.warn("authorization failed");
			((HttpServletRequest) req).getRequestDispatcher(LOGIN_PAGE_URI)
					.forward(req, res);
		} else {
			this.logger.warn("authorization succeeded");
			chain.doFilter(req, res);
		}
	}

	public void init(FilterConfig config) throws ServletException {

		this.restrictedResources = new HashSet<String>();
		this.invalidateResources = new HashSet<String>();
		this.restrictedResources.add(".do");
		this.restrictedResources.add(".jsp");
		this.restrictedResources.add(".html");
		this.restrictedResources.add(".htm");

		this.invalidateResources.add(".js");
		this.invalidateResources.add(".css");
		this.invalidateResources.add(".jpg");
		this.invalidateResources.add(".gif");
		this.invalidateResources.add(".swf");

		ServletContext sc = config.getServletContext();
		String path = sc.getContextPath();

		Constant.INDEX_URL = path
				+ config.getInitParameter(Constant.INDEX_URL_NAME);
		Constant.LOGIN_URL = path
				+ config.getInitParameter(Constant.LOGIN_URL_NAME);

		sc.setAttribute("loginURL", Constant.LOGIN_URL);
		sc.setAttribute("indexURL", Constant.INDEX_URL);
		sc.setAttribute("rootPath", sc.getContextPath());
		/**
		 * 启动SessionManager
		 */
		SessionManager.run();
		logger.info("SessionFilter startup:"
				+ new Date(System.currentTimeMillis()).toString());
	}

	/*
	 * private boolean contains(String value, String contextPath) {
	 * Iterator<String> ite = this.restrictedResources.iterator(); while
	 * (ite.hasNext()) { String restrictedResource = (String) ite.next(); if
	 * ((contextPath + restrictedResource).equalsIgnoreCase(value)) { return
	 * true; } } return false; }
	 */

	/**
	 * 含有不需要验证的访问资源
	 * 
	 * @param value
	 * @return
	 */
	private boolean containInvalidateResource(String value) {
		Iterator<String> ite = this.invalidateResources.iterator();
		while (ite.hasNext()) {
			String invalidateResource = (String) ite.next();
			logger.warn("invalidateResource Index="
					+ invalidateResource.indexOf(value));
			if (invalidateResource.indexOf(value) != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证访问源是否为合法的（refer）
	 * 
	 * @param value
	 * @return
	 */
	private boolean validateAccess(String value) {
		Iterator<String> iter = this.restrictedResources.iterator();
		if (value == null) {
			return false;
		}
		while (iter.hasNext()) {
			String invalidateResource = (String) iter.next();
			logger.warn("restrictedResources Index="
					+ value.indexOf(invalidateResource));
			if (value.indexOf(invalidateResource) != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否已经登录并获得授权
	 * 
	 * @return true获得 false未获得
	 */
	private boolean authorize() {
		return SessionManager.getUser() != null;
	}

	private boolean validateAccessPath(HttpServletRequest request,
			HttpServletResponse response, String vlaue) {
		HttpSession session = ServletContextHandler.getSession();
		boolean falg = false;
		for (int i = 0; i < EXCEPT_PAGE.length; i++) {
			if (vlaue.indexOf(EXCEPT_PAGE[i]) >= 0) {
				falg = true;
				break;
			}
		}
		if (vlaue.indexOf(INDEX_PAGE) == -1 && vlaue.indexOf(ADMIN_PAGE) == -1
				&& vlaue.indexOf(".jsp") > -1 && !falg
				|| (vlaue.indexOf(".do") == 28 && vlaue.indexOf(".gif") == -1)
				|| (vlaue.indexOf(".do") == -1 && vlaue.indexOf(".gif") == 36)
				|| (vlaue.indexOf(".do") == 27 && vlaue.indexOf(".gif") == -1)
				|| (vlaue.indexOf(".do") == 29 && vlaue.indexOf(".gif") == -1)
				|| (vlaue.indexOf(".do") == 33 && vlaue.indexOf(".gif") == -1)) {
			// 判断当前页是否是重定向以后的登录页面，如果是就不做session的判断，防止出现死循�?
			String ref = request.getHeader("REFERER"); // 是否是从地址栏直接输入的地址吗？
			if (session == null || (ref == null) || (ref.equals(""))) {
				return false;
			}
			return true;
		}
		return true;
	}
}
