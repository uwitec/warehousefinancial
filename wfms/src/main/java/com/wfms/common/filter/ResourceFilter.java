package com.wfms.common.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 资源访问控制过滤器
 * 
 * @author CYC
 * 
 */
public class ResourceFilter implements Filter {
	private static final String LOGIN_PAGE_URI = "login.html";

	private Log logger = LogFactory.getLog(this.getClass());

	public static final String INDEX_PAGE = "/yhglAction.do?method=login";
	public static final String ADMIN_PAGE = "/login.jsp";
	public static final String[] EXCEPT_PAGE = { "test.jsp" };

	private Set<String> restrictedResources;
	private Set<String> invalidateResources;

	public void init(FilterConfig filterConfig) throws ServletException {
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
		if ((this.containInvalidateResource(requestUri) && !validateAccess(refer))
				|| !this.authorize((HttpServletRequest) req)) {
			this.logger.warn("authorization failed");
			((HttpServletRequest) req).getRequestDispatcher(LOGIN_PAGE_URI)
					.forward(req, res);
		} else {
			this.logger.warn("authorization succeeded");
			chain.doFilter(req, res);
		}
	}

	public void destroy() {
	}

	private boolean contains(String value, String contextPath) {
		Iterator<String> ite = this.restrictedResources.iterator();
		while (ite.hasNext()) {
			String restrictedResource = (String) ite.next();
			if ((contextPath + restrictedResource).equalsIgnoreCase(value)) {
				return true;
			}
		}
		return false;
	}

	private boolean containInvalidateResource(String value) {
		Iterator ite = this.invalidateResources.iterator();
		while (ite.hasNext()) {
			String invalidateResource = (String) ite.next();
			logger.warn("invalidateResource Index="
					+ invalidateResource.indexOf(value));
			if ((invalidateResource).indexOf(value) != -1) {
				return true;
			}
		}
		return false;
	}

	private boolean validateAccess(String value) {
		Iterator ite = this.restrictedResources.iterator();
		while (ite.hasNext()) {
			String invalidateResource = (String) ite.next();
			logger.warn("restrictedResources Index="
					+ invalidateResource.indexOf(value));
			if ((invalidateResource).indexOf(value) != -1) {
				return true;
			}
		}
		return false;
	}

	private boolean authorize(HttpServletRequest req) {
		return req.getSession().getAttribute("userId") != null;
	}

	private boolean validateAccessPath(HttpServletRequest request,
			HttpServletResponse response, String vlaue) {
		HttpSession session = request.getSession();
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
