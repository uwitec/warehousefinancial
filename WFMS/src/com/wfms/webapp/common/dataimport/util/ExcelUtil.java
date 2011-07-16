/**
 *******************************************************************************
 * 文件名：ExcelUtil.java
 *
 * 描述�?
 * 
 * 创建日期：Feb 20, 2010 10:39:42 AM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.webapp.common.dataimport.util;

import java.io.IOException;
import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：ExcelUtil
 *  <dd> 类描述：Excel操作�?
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Feb 20, 2010 10:39:42 AM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see ExcelUtil
 * @version 1.0
 *
 */
public class ExcelUtil {
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:getDataByRowIndex</b>
	 * <dd>方法作用：读取指定行数据
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param inputStream		Excel文件�?
	 * <dd>@param rowCount			读取数据行号
	 * <dd>@return		String[]	数据数组
	 * </dl>
	 */
	public static String[] getDataByRowIndex(InputStream inputStream,int rowCount)
	{
		String[] datas=null;
		Workbook book=null;
		try {
			book = Workbook.getWorkbook(inputStream);
		} catch (BiffException e) {
			e.printStackTrace();
			return new String[0];
		} catch (IOException e) {
			e.printStackTrace();
			return new String[0];
		}
		Sheet sheet = book.getSheet(0);
		if(sheet!=null)
		{
			Cell[] cells=sheet.getRow(rowCount);
			if(cells!=null && cells.length>0)
			{
				datas=new String[cells.length];
				for(int i=0;i<cells.length;i++)
				{
					datas[i] = cells[i].getContents().trim();
				}
			}
		}
		return datas;
	}
	
	public static String[] getDataByRowIndex(Sheet sheet,int rowCount)
	{
		String[] datas=null;
		if(sheet!=null)
		{
			Cell[] cells=sheet.getRow(rowCount);
			if(cells!=null && cells.length>0)
			{
				datas=new String[cells.length];
				for(int i=0;i<cells.length;i++)
				{
					datas[i] = cells[i].getContents().trim();
				}
			}
		}
		else
		{
			datas=new String[0];
		}
		return datas;
	}
	
	public static String getDataByRowAndIndex(Sheet sheet,int _row,int _index)
	{
		String data = "";
		if(sheet != null && _row >= 0 && _index >= 0){
			data = sheet.getRow(_row)[_index].getContents().trim();
		}else{
			return "";
		}
		return data;
	}
	
}
