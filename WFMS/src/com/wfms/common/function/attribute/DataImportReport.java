package com.wfms.common.function.attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：DataImportReport
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 4, 2010 1:59:33 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see DataImportReport
 * @version 1.0
 *
 */
public class DataImportReport {
	private int recordCount=0;
	private int successCount=0;
	private int failedCount=0;
	private int mergeCount = 0;
	private List<DataImportError> errorList=new ArrayList<DataImportError>();
	
	public DataImportReport(){
		
	}
	
	public void addError(int row,String msg){
		failedCount++;
		errorList.add(new DataImportError(row,msg));		
	}
	
	public void addSuccess(){
		successCount++;
	}
	
	public boolean hasError(){
		if (failedCount==0) return false;
		else return true;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}

	public List<DataImportError> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<DataImportError> errorList) {
		this.errorList = errorList;
	}

	public int getMergeCount() {
		return mergeCount;
	}

	public void setMergeCount(int mergeCount) {
		this.mergeCount = mergeCount;
	}
	
	
}
