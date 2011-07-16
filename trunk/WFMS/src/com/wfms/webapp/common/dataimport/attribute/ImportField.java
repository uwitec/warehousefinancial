package com.wfms.webapp.common.dataimport.attribute;

/**
 * 
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：ImportField
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 4, 2010 1:58:50 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see ImportField
 * @version 1.0
 *
 */
public class ImportField{

	private String DisplayName="";
	private String DBName="";
	private int index=-1;
	
	public ImportField(String displayName,String dbName){
		this.DisplayName = displayName;
		this.DBName = dbName;
	}
	public String getDisplayName() {
		return DisplayName;
	}
	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}
	public String getDBName() {
		return DBName;
	}
	public void setDBName(String name) {
		DBName = name;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	
}
