package com.wfms.webapp.common.dataimport.attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：ImportFields
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 4, 2010 1:59:04 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see ImportFields
 * @version 1.0
 *
 */
public class ImportFields {

	private List<ImportField> importObjList;
	
	public ImportFields(){
		importObjList=new ArrayList<ImportField>();
	}
	
	
	
	public List<ImportField> getImportObjList() {
		return importObjList;
	}



	public void setImportObjList(List<ImportField> importObjList) {
		this.importObjList = importObjList;
	}



	public void addField(ImportField field){
		importObjList.add(field);
	}
	
	public ImportField getFieldByDislayName(String displayName){
		for(ImportField o:importObjList){
			if (o.getDisplayName().equalsIgnoreCase(displayName))
				return o;
		}
		return null;
	}
	
	public ImportField getFieldByDBName(String DBName){
		for(ImportField o:importObjList){
			if (o.getDBName().equalsIgnoreCase(DBName))
				return o;
		}
		return null;
	}
	
	public ImportField getFieldByIndex(int index){
		if(index<0) return null;
		for(ImportField o:importObjList){
			if (o.getIndex()==index)
				return o;
		}
		return null;
	}
	
	/**
	 * ��֤�����ֶ��Ƿ�����
	 * @return
	 */
	public String validate(){
		for(ImportField o:importObjList){
			if (o.getIndex()<0)
				return o.getDisplayName();
		}
		return null;
	}
	
	public String[] getColumns(){
		String[] s = new String[importObjList.size()];
		for(int i=0;i<importObjList.size();i++){
			s[i]=importObjList.get(i).getDBName();
		}
		return s;
	}
	
	
	
}
