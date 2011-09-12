package com.wfms.common.web.views;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.wfms.common.attribute.ExportBean;

public class ExcelView extends AbstractExcelView {
	public void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<ExportBean> exportBeanList = (List<ExportBean>) model
				.get("exportBeanList");
		for (ExportBean exportBean : exportBeanList) {
			buildExcelSheet(workbook, exportBean.getSheetName(),
					exportBean.getTableTitle(), exportBean.getHeader(),
					exportBean.getTableContent());
		}
		String paramName = request.getParameter("fileName");
		String fileName = new String(new String(
				(paramName + ".xls").getBytes(), "ISO-8859-1"));
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ fileName);
	}

	public void buildExcelSheet(HSSFWorkbook workBook, String sheetName,
			String title, List<String> header, List<String[]> sheetContent) {
		if ((sheetName == null) || ("".equals(sheetName))) {
			int sheetCount = workBook.getNumberOfSheets();
			sheetName = "sheet" + sheetCount;
		}
		HSSFSheet hssfSheet = workBook.createSheet(sheetName);
		HSSFPalette hssfPalette = workBook.getCustomPalette();

		setDefaultHighWidth(hssfSheet);

		int rowCount = sheetContent.size();
		int columnCount = header.size();

		HSSFRow titleRow = hssfSheet.createRow(0);

		hssfSheet
				.addMergedRegion(new CellRangeAddress(0, 0, 0, columnCount - 1));
		titleRow.createCell(0).setCellValue(title);
		titleRow.getCell(0).setCellStyle(
				getAnyCellStyle(workBook, getHdrFont(workBook), (short) 2,
						(short) 1, (short) 1, false));

		Row headerRow = hssfSheet.createRow(1);
		for (int i = 0; i < columnCount; ++i) {
			headerRow.createCell(i).setCellValue((String) header.get(i));
			headerRow.getCell(i)
					.setCellStyle(
							getBorderCellStyle(
									getAnyCellStyle(workBook,
											getFtrFont(workBook), (short) 1,
											(short) 1, (short) 1, false),
									hssfPalette.findSimilarColor(-1, -1, -1)
											.getIndex(), (short) 10));
			hssfSheet.autoSizeColumn(i);
		}

		for (int i = 0; i < rowCount; ++i) {
			Row row = hssfSheet.createRow(i + 2);
			for (int j = 0; j < ((String[]) sheetContent.get(i)).length; ++j) {
				row.createCell(j).setCellValue(
						((String[]) sheetContent.get(i))[j]);
			}
		}
	}

	public static HSSFFont getHdrFont(HSSFWorkbook wb) {
		HSSFFont fontStyle = wb.createFont();
		fontStyle.setFontName("宋体");
		fontStyle.setFontHeightInPoints((short) 16);
		fontStyle.setBoldweight((short) 700);
		return fontStyle;
	}

	public static HSSFFont getFtrFont(HSSFWorkbook wb) {
		HSSFFont fontStyle = wb.createFont();
		fontStyle.setFontName("宋体");
		fontStyle.setFontHeightInPoints((short) 12);
		fontStyle.setBoldweight((short) 700);
		fontStyle.setColor(wb.getCustomPalette().findSimilarColor(0, -128, 0)
				.getIndex());
		return fontStyle;
	}

	public static HSSFFont getContentFont(HSSFWorkbook wb) {
		HSSFFont fontStyle = wb.createFont();
		fontStyle.setFontName("宋体");
		fontStyle.setFontHeightInPoints((short) 12);
		fontStyle.setBoldweight((short) 400);
		return fontStyle;
	}

	public static HSSFFont getMergeConflictFont(HSSFWorkbook wb) {
		HSSFFont fontStyle = wb.createFont();
		fontStyle.setFontName("Arial");
		fontStyle.setFontHeightInPoints((short) 12);
		fontStyle.setBoldweight((short) 400);
		fontStyle.setBoldweight((short) 700);
		return fontStyle;
	}

	public static HSSFCellStyle getAnyCellStyle(HSSFWorkbook wb, HSSFFont font,
			short align, short valign, short indent, boolean wrapText) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		if (font != null)
			cellStyle.setFont(font);
		if (align > 0)
			cellStyle.setAlignment(align);
		if (valign > 0)
			cellStyle.setVerticalAlignment(valign);
		if (indent > 0)
			cellStyle.setIndention(indent);
		cellStyle.setWrapText(wrapText);
		return cellStyle;
	}

	public static HSSFCellStyle getBorderCellStyle(HSSFCellStyle cellStyle,
			short borderColor, short borderWidth) {
		cellStyle.setTopBorderColor(borderColor);
		cellStyle.setRightBorderColor(borderColor);
		cellStyle.setBottomBorderColor(borderColor);
		cellStyle.setLeftBorderColor(borderColor);

		cellStyle.setBorderTop(borderWidth);
		cellStyle.setBorderRight(borderWidth);
		cellStyle.setBorderBottom(borderWidth);
		cellStyle.setBorderLeft(borderWidth);
		return cellStyle;
	}

	public static void setDefaultHighWidth(HSSFSheet sheet) {
		sheet.setDefaultRowHeightInPoints(10.0F);
		sheet.setDefaultColumnWidth(20);
	}
}