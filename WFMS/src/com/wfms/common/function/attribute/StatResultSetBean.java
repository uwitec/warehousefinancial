package com.wfms.common.function.attribute;

import java.util.List;

/**
 * 
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：StatResultSetBean
 *  <dd> 类描述：通用统计实体
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 4, 2010 2:01:56 PM
 *  <dd> 修改人：无
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
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
