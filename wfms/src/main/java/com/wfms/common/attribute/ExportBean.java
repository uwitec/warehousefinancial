package com.wfms.common.attribute;

import java.util.List;

public class ExportBean {
	private String sheetName;
	private String title;
	private String tableTitle;
	private String topContent;
	private String bottomContent;
	private List<String> header;
	private List<String[]> tableContent;
	private ExportBean childElement = null;

	public String getSheetName() {
		return this.sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTopContent() {
		return this.topContent;
	}

	public void setTopContent(String topContent) {
		this.topContent = topContent;
	}

	public String getBottomContent() {
		return this.bottomContent;
	}

	public void setBottomContent(String bottomContent) {
		this.bottomContent = bottomContent;
	}

	public List<String> getHeader() {
		return this.header;
	}

	public void setHeader(List<String> header) {
		this.header = header;
	}

	public List<String[]> getTableContent() {
		return this.tableContent;
	}

	public void setTableContent(List<String[]> tableContent) {
		this.tableContent = tableContent;
	}

	public ExportBean getChildElement() {
		return this.childElement;
	}

	public void setChildElement(ExportBean childElement) {
		this.childElement = childElement;
	}

	public boolean hasChildElement() {
		return this.childElement != null;
	}

	public String getTableTitle() {
		return this.tableTitle;
	}

	public void setTableTitle(String tableTitle) {
		this.tableTitle = tableTitle;
	}
}