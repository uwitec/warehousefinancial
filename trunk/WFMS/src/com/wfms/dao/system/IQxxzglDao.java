/**
 *******************************************************************************
 * 文件名：IQxxzglDao.java
 *
 * 描述�?
 * 
 * 创建日期：Jan 19, 2010 2:52:57 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dao.system;

import com.wfms.model.system.MemberRight;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：IQxxzglDao
 *  <dd> 接口描述：权限限制管理接�?
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 19, 2010 2:52:57 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see IQxxzglDao
 * @version 1.0
 *
 */
public interface IQxxzglDao {
	
	public int deleteQxxzByYh(int yhId);
	
	public int addQxxz(MemberRight qxxz);
	
	public int updateQxxz(MemberRight qxxz);
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:addUpdateForQxxz</b>
	 * <dd>方法作用：根据权限限制的有无,自动选择更新或增加权限限制记�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@return int
	 * </dl>
	 */
	public int  addUpdateForQxxz(MemberRight qxxz,String yhId,int qxId);
}
