package com.wfms.webapp.common.datastat.attribute.v3;

public class Data {

    /**
     * 显示标签
     */
    private String label;
    
	/**
	 * 值
	 */
	private String value;

	/**
	 * 颜色
	 */
	private String color;
	
	/**
	 * 切开数据集 1.切开,0默认不切开(饼图和漏斗图用)
	 */
	private String issliced;
	
	/**
	 * 单击javascript事件处理定义
	 */
	private String link;
	
	/**
	 * 行号
	 */
	private String rownum;
	
	/**
	 * 列号
	 */
	private String columnnum;
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIssliced() {
		return issliced;
	}

	public void setIssliced(String issliced) {
		this.issliced = issliced;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getLink() {
		return "javascript:alert('row:"+rownum+" column:"+columnnum+"');";
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getRownum() {
		return rownum;
	}

	public void setRownum(String rownum) {
		this.rownum = rownum;
	}

	public String getColumnnum() {
		return columnnum;
	}

	public void setColumnnum(String columnnum) {
		this.columnnum = columnnum;
	}
	
	
}
