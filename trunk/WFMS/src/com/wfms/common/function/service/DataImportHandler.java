package com.wfms.common.function.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.wfms.common.function.attribute.ActionReport;
import com.wfms.common.function.attribute.DataImportReport;
import com.wfms.common.function.attribute.ImportField;
import com.wfms.common.function.attribute.ImportFields;
import com.wfms.common.function.attribute.LxdmType;
import com.wfms.common.function.command.DataImportForm;
import com.wfms.common.function.dao.IImportDao;
import com.wfms.common.function.entity.Column;
import com.wfms.common.function.entity.ImportRule;

/**
 * 
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：DataImportDAO
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 4, 2010 1:57:47 PM
 *  <dd> 修改人：无
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see DataImportHandler
 * @version 1.0
 *
 */
public abstract class DataImportHandler {
	protected Workbook book;
	protected Sheet sheet;
	protected IImportDao importDao = null;
	
	protected DataImportForm form;
	protected ImportFields importFields; 
	protected Map<String,String> fieldValueMap;
	protected boolean defaultInsert = true;
	protected boolean insertPermit = true; 		
	protected LxdmType lxdmType = LxdmType.LXDM;
	protected String tableName = null;
	protected String[] columns = null;
	protected String[] pkColumns = null;
	protected List<String[]> pkColsList = null;
	protected DataImportReport dataImportReport = new DataImportReport();
	protected List<Column> colEntList = null;
	protected List<ImportRule> drgzList = null;
	
	public DataImportHandler(){
		importFields = new ImportFields();
		dataImportReport = new DataImportReport();
		fieldValueMap = new HashMap<String,String>();
	}
	
	public DataImportHandler(InputStream is,DataImportForm form) throws Exception{
		try {
			this.book = Workbook.getWorkbook(is);
			this.sheet = book.getSheet(0);
			this.form = form;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		importFields = new ImportFields();
		dataImportReport = new DataImportReport();
		fieldValueMap = new HashMap<String,String>();
	}
	
	
	public abstract ActionReport validateRows(Map<String,String> fieldValueMap,LxdmType lxdmType);
	
	
	public abstract ActionReport dataImport(boolean insertPermit,boolean defaultInsert);
	
	
	protected ActionReport beforeDataImport(){
		ActionReport report;
		report = validateFormat();
		if(!report.isSuccess()) return report;
		if(sheet!=null)
		{
			int rowcount = sheet.getRows(); 
			dataImportReport.setRecordCount(rowcount-2);
			return new ActionReport(ActionReport.SUCCESS,"");
		}
		return new ActionReport(ActionReport.ERROR,"外部数据文件无法读取");
	}
	

	protected ActionReport afterDataImport(){
		return new ActionReport(ActionReport.SUCCESS,"");
	}
	
	public DataImportReport getDataImportReport() {
		return dataImportReport;
	}

	
	public ActionReport validateFormat(){
		if(this.sheet!=null)
		{
			Cell[] fields = sheet.getRow(1);
			if (fields.length > 50) 
				return new ActionReport(ActionReport.ERROR,"数据列大于50列");
			for (int i = 0; i < fields.length; i++) {
				String field = fields[i].getContents().trim();
				ImportField importField = importFields.getFieldByDislayName(field);
				if(importField!=null){
					importField.setIndex(i);
				}		
			}
			if(importFields.validate()!=null){
				return new ActionReport(ActionReport.ERROR,"不允许为空:"+importFields.validate());
			}
		}
		return new ActionReport(ActionReport.SUCCESS,"");
	}
	
	public static String[] getColumnValues(
			String[] dbNames,
			Map<String,String> fieldValueMap){
		if(dbNames==null||dbNames.length==0) return null;
		String[] result = new String[dbNames.length];
		for(int i=0;i<dbNames.length;i++){
			result[i] = fieldValueMap.get(dbNames[i]);
		}
		return result;
		
	}

	public ImportFields getImportFields() {
		return importFields;
	}


	public void setImportFields(ImportFields importFields) {
		this.importFields = importFields;
	}


	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public String[] getPkColumns() {
		return pkColumns;
	}

	public void setPkColumns(String[] pkColumns) {
		this.pkColumns = pkColumns;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}
	
	public DataImportForm getForm() {
		return form;
	}

	public void setForm(DataImportForm form) {
		this.form = form;
	}
	
	/**
	 * 
	 * <dl>
	 * <b>方法名:initSheetForm</b>
	 * <dd>方法作用：由Spring实例化对象时，手动设置DataImportForm和InputStream
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选) 
	 * <dd>@param is
	 * <dd>@param form
	 * </dl>
	 */
	public void initDataImportForm(DataImportForm form){
		if(form!=null && form.getMultiFile()!=null)
		{
			try {
				this.book = Workbook.getWorkbook(form.getMultiFile().getInputStream());
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.form = form;
			this.sheet = book!=null?book.getSheet(0):null;
		}
	}

	public List<Column> getColEntList() {
		return colEntList;
	}

	public void setColEntList(List<Column> colEntList) {
		this.colEntList = colEntList;
	}

	public List<ImportRule> getDrgzList() {
		return drgzList;
	}

	public void setDrgzList(List<ImportRule> drgzList) {
		this.drgzList = drgzList;
	}

	public List<String[]> getPkColsList() {
		return pkColsList;
	}

	public void setPkColsList(List<String[]> pkColsList) {
		this.pkColsList = pkColsList;
	}

}
