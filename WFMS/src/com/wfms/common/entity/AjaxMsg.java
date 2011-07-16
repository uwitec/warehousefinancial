package com.wfms.common.entity;

import java.util.List;

public class AjaxMsg{
	private boolean success;
	private String message = "";
	private Object other = "";
	private List errorList = null;
	
	public AjaxMsg(){
		
	}
	public AjaxMsg(boolean success,String message)
	{
		this.success = success;
		this.message = message;
	}
	public AjaxMsg(boolean success,String message,List errorList){
		this.success = success;
		this.message = message;
		this.errorList = errorList;
	}
	
	public List getErrorList() {
		return errorList;
	}
	public void setErrorList(List errorList) {
		this.errorList = errorList;
	}
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getOther() {
		return other;
	}
	public void setOther(Object other) {
		this.other = other;
	}
}
