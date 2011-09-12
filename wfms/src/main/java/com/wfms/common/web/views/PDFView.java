package com.wfms.common.web.views;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.wfms.common.attribute.ExportBean;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.document.AbstractPdfView;

public class PDFView extends AbstractPdfView {
	public void buildPdfDocument(Map model, Document document,
			PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<ExportBean> list = (List<ExportBean>) model.get("exportBeanList");
		for (ExportBean exportBean : list) {
			document.add(new Paragraph(exportBean.getTopContent(),
					chineseFont()));
			if (exportBean.getTableContent() != null)
				generatePdfTable(document, exportBean.getTitle(),
						exportBean.getHeader(), exportBean.getTableContent());
			document.add(new Paragraph(exportBean.getBottomContent(),
					chineseFont()));
		}
		String paramName = request.getParameter("fileName");
		String fileName = new String(new String(
				(paramName + ".pdf").getBytes(), "ISO-8859-1"));
		response.setContentType("application/pdf;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ fileName);
	}

	private void generatePdfTable(Document doc, String title,
			List<String> header, List<String[]> tableContent) throws Exception {
		if (doc == null) {
			doc = new Document(PageSize.A4, 50.0F, 50.0F, 50.0F, 50.0F);
		} else {
			doc.setPageSize(PageSize.A4);
		}

		doc.addCreationDate();

		HeaderFooter footer = new HeaderFooter(new Phrase(), true);

		footer.setBorder(1);

		footer.setAlignment(1);
		doc.setFooter(footer);

		Paragraph paragraph = new Paragraph(title, chineseFont());
		paragraph.setAlignment(1);
		doc.add(paragraph);

		Table table = new Table(header.size());
		table.setWidth(100.0F);
		table.setBorder(1);
		for (int i = 0; i < header.size(); ++i) {
			Cell cell = new Cell(new Phrase((String) header.get(i),
					chineseFont()));
			cell.setHorizontalAlignment(1);
			cell.setVerticalAlignment(1);
			cell.setHeader(true);
			cell.setBackgroundColor(new Color(231, 231, 231));
			table.addCell(cell);
		}
		table.endHeaders();

		for (int i = 0; i < tableContent.size(); ++i) {
			Cell cell = null;

			String[] value = (String[]) tableContent.get(i);
			for (int j = 0; j < value.length; ++j) {
				cell = new Cell(new Phrase(value[j], chineseFont()));
				cell.setHorizontalAlignment(1);
				cell.setVerticalAlignment(1);
				table.addCell(cell);
			}
		}
		doc.add(table);
	}

	public static Font chineseFont() {
		BaseFont baseFont = null;
		try {
			baseFont = BaseFont
					.createFont("STSong-Light", "UniGB-UCS2-H", true);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Font chineseFont = new Font(baseFont, 12.0F, 0, Color.BLACK);
		return chineseFont;
	}
}