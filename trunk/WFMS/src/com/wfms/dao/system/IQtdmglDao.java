/**
 *******************************************************************************
 * 文件名：IQtdmDao.java
 *
 * 描述�?
 * 
 * 创建日期：Jan 21, 2010 3:07:23 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dao.system;

import java.util.List;

import com.wfms.model.system.QtdmEntity;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：IQtdmDao
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 21, 2010 3:07:23 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author GHT
 * @see IQtdmglDao
 * @version 1.0
 *
 */
public interface IQtdmglDao {
	/**
	 * 读取其他代码
	 * <dl>
	 * <b>方法�?:getQtdm</b>
	 * <dd>方法作用：根据代码类型获取集�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param dmlb
	 * <dd>@return
	 * </dl>
	 */
	public List<QtdmEntity> getQtdm(String dmlb);
	/**
	 * 增加其他代码
	 * <dl>
	 * <b>方法�?:addQtdm</b>
	 * <dd>方法作用：增加其他代�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param qtdm
	 * <dd>@return
	 * </dl>
	 */
	public int addQtdm(QtdmEntity qtdm);
	/**
	 * 根据主键删除类型代码
	 * <dl>
	 * <b>方法�?:delQtdm</b>
	 * <dd>方法作用：根据主键删除类型代�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param id
	 * <dd>@return
	 * </dl>
	 */
	public int delQtdm(int id);
	
	/**
	 * <dl>
	 * <b>方法�?:updQtdm</b>
	 * <dd>方法作用：批量修改类型代�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param list
	 * <dd>@return
	 * </dl>
	 */
	public int updQtdm(QtdmEntity qtdm);
}
