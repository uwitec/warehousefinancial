package com.wfms.common.web.views;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

public abstract class AbstractMimeView extends AbstractView {
	public final static String FILE_PATH = "filePath";

	public final static String FILE_NAME = "fileName";

	public final static String FILE_DATA = "fileData";

	public AbstractMimeView() {
		setContentType("application/download");
	}
    
	protected void buildHeader(HttpServletResponse response, String mimeName,
			long mimeLength) {
		response.setContentType(getContentType());
		if (mimeLength > 0)
			response.setHeader("Content-Length", String.valueOf(mimeLength));
		try {
			mimeName = URLEncoder.encode(mimeName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		
		response.setHeader("Content-disposition", "attachment; filename=\""
				+ mimeName + "\"");
	}
}