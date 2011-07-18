package com.wfms.common.function.attribute;

/**
 * @author CYC
 * @see DataImportError
 * @version 1.0
 *
 */
public class DataImportError {
	private int row;
	private String msg;
	public DataImportError(int row,String msg){
		this.row=row;
		this.msg=msg;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
