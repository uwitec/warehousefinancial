package com.wfms.common.function.attribute;

/**
 * @author CYC
 * @see ActionReport
 * @version 1.0
 *
 */
public class ActionReport { 
	
	public static String SUCCESS = "SUCCESS";
	public static String ERROR = "ERROR";
	public static String DB_ERROR = "DB_ERROR";
	public static String UNKNOW = "UNKNOW";
	
	private String type="";
	private String msg="";
	public ActionReport(String type,String msg){
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
	public boolean isSuccess(){
		if(this.type.equalsIgnoreCase("SUCCESS")){
			return true;
		}else return false;
	}

}
