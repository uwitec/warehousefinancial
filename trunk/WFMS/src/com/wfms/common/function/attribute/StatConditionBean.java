package com.wfms.common.function.attribute;

public class StatConditionBean {
	private String rowKeyType;
	private Object rowObjects;
	private String columnKeyType;
	private Object columnObjects;
	private String statViewName;
	private String tableName;
	private String condition;
	private String[] refColumnKeys = new String[2];
	private String[] refRowKeys = new String[2];
	private String[] refTabs = new String[2];
	private String[] refSql = new String[2];
	
	private String statTarget;
	private String statCategory;
	
	public String getStatTarget() {
		return statTarget;
	}
	public void setStatTarget(String statTarget) {
		this.statTarget = statTarget;
	}
	public String getStatCategory() {
		return statCategory;
	}
	public void setStatCategory(String statCategory) {
		this.statCategory = statCategory;
	}
	public String getRowKeyType() {
		return rowKeyType;
	}
	public void setRowKeyType(String rowKeyType) {
		this.rowKeyType = rowKeyType;
	}
	public Object getRowObjects() {
		return rowObjects;
	}
	public void setRowObjects(Object rowObjects) {
		this.rowObjects = rowObjects;
	}
	public String getColumnKeyType() {
		return columnKeyType;
	}
	public void setColumnKeyType(String columnKeyType) {
		this.columnKeyType = columnKeyType;
	}
	public Object getColumnObjects() {
		return columnObjects;
	}
	public void setColumnObjects(Object columnObjects) {
		this.columnObjects = columnObjects;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String[] getRefTabs() {
		return refTabs;
	}
	public void setRefTabs(String[] refTabs) {
		this.refTabs = refTabs;
	}
	public String[] getRefSql() {
		return refSql;
	}
	public void setRefSql(String[] refSql) {
		this.refSql = refSql;
	}
	public String[] getRefColumnKeys() {
		return refColumnKeys;
	}
	public void setRefColumnKeys(String[] refColumnKeys) {
		this.refColumnKeys = refColumnKeys;
	}
	public String[] getRefRowKeys() {
		return refRowKeys;
	}
	public void setRefRowKeys(String[] refRowKeys) {
		this.refRowKeys = refRowKeys;
	}
	public String getStatViewName() {
		return statViewName;
	}
	public void setStatViewName(String statViewName) {
		this.statViewName = statViewName;
	}
}
