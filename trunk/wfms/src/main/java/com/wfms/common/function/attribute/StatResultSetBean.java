package com.wfms.common.function.attribute;

import java.util.List;

/**
 * @author CYC
 * @see StatResultSetBean
 * @version 1.0
 *
 */
public class StatResultSetBean {
	private List rs; 
	private int rowNum;
	private int columnNum;
	private List rowKeys;
	private List columnKeys;
	public List<List<String>> getRs() {
		return rs;
	}
	public void setRs(List rs) {
		this.rs = rs;
	}
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public int getColumnNum() {
		return columnNum;
	}
	public void setColumnNum(int columnNum) {
		this.columnNum = columnNum;
	}
	public List getRowKeys() {
		return rowKeys;
	}
	public void setRowKeys(List rowKeys) {
		this.rowKeys = rowKeys;
	}
	public List getColumnKeys() {
		return columnKeys;
	}
	public void setColumnKeys(List columnKeys) {
		this.columnKeys = columnKeys;
	}
	 
}
