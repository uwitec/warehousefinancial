package com.wfms.common.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServletContextFilter implements Filter {

	private Log logger = LogFactory
			.getLog(ServletContextFilter.class);

	public void destroy() {
		ServletContextHandler.clear();
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		initRequestResponse(request, response);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		logger.info("ServletContextFilter startup:"
				+ new Date(System.currentTimeMillis()).toString());
	}

	private void initRequestResponse(ServletRequest request,
			ServletResponse response) {
			ServletContextHandler.setRequest((HttpServletRequest) request);
			ServletContextHandler.setResponse((HttpServletResponse) response);
	}

}
