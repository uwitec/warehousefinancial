package com.wfms.common.function.attribute;

/**
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
