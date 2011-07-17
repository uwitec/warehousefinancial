package com.wfms.common.web.views;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MimeBlobView extends AbstractMimeView {

	@SuppressWarnings("unchecked")
	protected void renderMergedOutputModel(Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fileName = (String) model.get(FILE_NAME);
		Blob fileData = (Blob) model.get(FILE_DATA);

		if (fileName != null && fileData != null) {
			buildHeader(response, fileName, fileData.length());
			BufferedInputStream in = new BufferedInputStream(fileData
					.getBinaryStream());
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