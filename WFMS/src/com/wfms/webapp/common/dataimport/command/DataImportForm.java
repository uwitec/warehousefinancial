/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.wfms.webapp.common.dataimport.command;


import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;


/**
 * 
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：DataImportForm
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 4, 2010 1:59:13 PM
 *  <dd> 修改人：无
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see DataImportForm
 * @version 1.0
 *
 */
public class DataImportForm{
	/*
	 * Generated fields
	 */

	private File  file;
	private MultipartFile multiFile;
	private boolean isReserve;
	private Object matchData;
	private boolean isPreview;
	private HttpServletRequest request;
	private String tableName;


	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public boolean isReserve() {
		return isReserve;
	}

	public void setIsReserve(boolean isReserve) {
		this.isReserve = isReserve;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Object getMatchData() {
		return matchData;
	}

	public void setMatchData(Object matchData) {
		this.matchData = matchData;
	}
	
	public void setReserve(boolean isReserve) {
		this.isReserve = isReserve;
	}
	public boolean isPreview() {
		return isPreview;
	}

	public void setIsPreview(boolean isPreview) {
		this.isPreview = isPreview;
	}

	public MultipartFile getMultiFile() {
		return multiFile;
	}

	public void setMultiFile(MultipartFile multiFile) {
		this.multiFile = multiFile;
	}
}