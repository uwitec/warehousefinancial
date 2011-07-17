package com.wfms.common.attribute;
public class ActionReportEntity {
	public static String SUCCESS = "SUCCESS";
	public static String ERROR = "ERROR";
	public static String DB_ERROR = "DB_ERROR";
	public static String UNKNOW = "UNKNOW";
	
	private String type = "";
	private String msg = "";
	private boolean isSuccess = false;
	
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public ActionReportEntity(String type,String msg){
		this.type = type;
		this.msg = msg;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
