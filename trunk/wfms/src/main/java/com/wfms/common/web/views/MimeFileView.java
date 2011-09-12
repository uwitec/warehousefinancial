package com.wfms.common.web.views;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MimeFileView extends AbstractMimeView {

	@SuppressWarnings("unchecked")
	protected void renderMergedOutputModel(Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String filePath = (String) model.get(FILE_PATH);
		if (filePath != null) {
			File file = new File(filePath);

			buildHeader(response, file.getName(), file.length());

			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(file));

			try {
				byte[] buffer = new byte[4096];
				int len;
				OutputStream out = response.getOutputStream();
				while ((len = in.read(buffer, 0, buffer.length)) > 0) {
					out.write(buffer, 0, len);
				}
				out.flush();
			} finally {
				in.close();
			}
		}
	}
}