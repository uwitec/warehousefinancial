package com.wfms.common.web.views;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.wfms.common.attribute.ExportBean;

public class TextExtView extends AbstractView {
	public TextExtView() {
		setContentType("text/html");
	}

	protected void renderMergedOutputModel(Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fileName = request.getParameter("fileName");
		List exportBeanList = (List) model.get("exportBeanList");

		response.reset();
		fileName = fileName + ".txt";
		response.addHeader("Content-Disposition ", "attachment; filename=\""
				+ fileName + "\"");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Cache-Control",
				"must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		String rootPath = request.getSession().getServletContext()
				.getRealPath("/");
		String filePath = rootPath + "\\TextExtView.txt";

		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		if ((exportBeanList == null) || (exportBeanList.size() == 0)) {
			return;
		}
		for (int i = 0; i < exportBeanList.size(); ++i) {
			ExportBean exportBean = (ExportBean) exportBeanList.get(i);
			List<String[]> tabContent = exportBean.getTableContent();
			try {
				if (exportBean.getTopContent() != null) {
					writer.write(exportBean.getTopContent());
					writer.write("\r\n");
				}
				if (exportBean.getTitle() != null) {
					writer.write(exportBean.getTitle());
					writer.write("\r\n");
				}
				for (String head : exportBean.getHeader()) {
					if (head != null)
						writer.write(head + "\t");
				}
				writer.write("\r\n");
				for (String[] values : tabContent) {
					for (String value : values) {
						if (value != null)
							writer.write(value + "\t");
					}
					writer.write("\r\n");
				}
				if (exportBean.getBottomContent() != null) {
					writer.write(exportBean.getBottomContent());
					writer.write("\r\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		writer.write("\r\n");
		writer.flush();
		writer.close();
		response.reset();
		File f = new File(filePath);
		response.setContentType("application/x-download");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ URLEncoder.encode(fileName, "UTF8"));
		response.setContentLength((int) f.length());
		if ((fileName == null) || (filePath == null))
			return;
		try {
			if ((f.exists()) && (f.canRead())) {
				byte[] buffer = new byte[4096];
				BufferedOutputStream output = null;
				BufferedInputStream input = null;
				try {
					output = new BufferedOutputStream(
							response.getOutputStream());
					input = new BufferedInputStream(new FileInputStream(f));
					int i = -1;
					while ((i = input.read(buffer, 0, 4096)) > -1) {
						output.write(buffer, 0, i);
					}
					response.flushBuffer();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (input != null)
						input.close();
					if (output != null)
						output.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}