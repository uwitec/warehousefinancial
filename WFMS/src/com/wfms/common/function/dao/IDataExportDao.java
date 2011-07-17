package com.wfms.common.function.dao;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import xuner.web.mvc.bean.ExportBean;

public interface IDataExportDao {
	
	public boolean exportData(String title, String condition,
			String tableName, String[] colList, HttpServletResponse response);
	
	public boolean exportXsbdData(String title, String condition,
			String tableName, String[] colList, HttpServletResponse response);
	
	public ExportBean getExportBean(String title,String sql,String[] tables,Map matchMap);

}
