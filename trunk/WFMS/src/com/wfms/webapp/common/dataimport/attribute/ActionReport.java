package com.wfms.webapp.common.dataimport.attribute;

/**
 * 
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：ActionReport
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 4, 2010 1:59:20 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
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
