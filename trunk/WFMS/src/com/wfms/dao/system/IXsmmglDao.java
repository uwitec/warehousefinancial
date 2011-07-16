/**
 *******************************************************************************
 * 文件名：IXsmmglDao.java
 *
 * 描述�?
 * 
 * 创建日期：Mar 24, 2010 2:46:15 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dao.system;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：IXsmmglDao
 *  <dd> 类描述：学生密码DAO接口
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 24, 2010 2:46:15 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see IXsmmglDao
 * @version 1.0
 *
 */
public interface IXsmmglDao {

	/**
	 * 
	 * <dl>
	 * <b>方法�?:getXsmmByXhMm</b>
	 * <dd>方法作用：根据学生编号和密码查询学生密码
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param xh
	 * <dd>@param mm
	 * <dd>@return
	 * </dl>
	 */
	public String getXsmmByXhMm(String xh,String mm);
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:batchInserXsmm</b>
	 * <dd>方法作用：初始化系统�?有学生密码信�?(不初始化已存在学生密�?)
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@return
	 * </dl>
	 */
	public int batchInserXsmm(String xh, String mm, String type);
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:getXsmmByXh</b>
	 * <dd>方法作用：根据学号查询学生密�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param xh
	 * <dd>@return
	 * </dl>
	 */
	public String getXsmmByXh(String xh);
	
	/**
	 * 更新学生密码
	 * @param xh
	 * @param mm
	 * @return
	 */
	public int updateXsmm(String xh, String mm);
}
