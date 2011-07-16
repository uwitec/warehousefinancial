/**
 *******************************************************************************
 * 文件名：IBhscgzglDao.java
 *
 * 描述�?
 * 
 * 创建日期：Jan 20, 2010 7:45:22 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dao.system;

import java.util.List;

import com.wfms.model.system.BhscgzszEntity;
import com.wfms.model.system.BhscgztjEntity;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：IBhscgzglDao
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 20, 2010 7:45:22 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see IBhscgzglDao
 * @version 1.0
 *
 */
public interface IBhscgzglDao {
	
	public int insertBhscgz(BhscgzszEntity bhscgz);
	
	public int updateBhscgz(BhscgzszEntity bhscgz);
	
	public int deleteBhscgz(int bhscgzId);
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:getBhscgzByGzmc</b>
	 * <dd>方法作用�? 查询某个编号规则名称的所有生成信�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param gzmc
	 * <dd>@return
	 * </dl>
	 */
	public List<BhscgzszEntity> getBhscgzByGzmc(String gzmc);
	
	public BhscgztjEntity getBhtjByFjid(int id);
	
	public BhscgztjEntity getBhtjByid(int id);
	
	public String getTjValue(String sql);
}
