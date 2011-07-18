package com.wfms.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.wfms.common.system.constant.Constant;
import com.wfms.common.system.entity.User;
import com.wfms.common.system.web.SessionManager;

public class SessionFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		User user = SessionManager.getUser();
		if (user == null) {

			response.getWriter().println(
					"<script>alert('您还未登录系统,请先登录系统...');window.location.href='"
							+ Constant.LOGIN_URL + "'</script>");
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {

		ServletContext sc = arg0.getServletContext();
		String path = sc.getContextPath();

		Constant.INDEX_URL = path
				+ arg0.getInitParameter(Constant.INDEX_URL_NAME);
		Constant.LOGIN_URL = path
				+ arg0.getInitParameter(Constant.LOGIN_URL_NAME);

		sc.setAttribute("loginURL", Constant.LOGIN_URL);
		sc.setAttribute("indexURL", Constant.INDEX_URL);
		sc.setAttribute("rootPath", sc.getContextPath());

		SessionManager.run();
	}
}
