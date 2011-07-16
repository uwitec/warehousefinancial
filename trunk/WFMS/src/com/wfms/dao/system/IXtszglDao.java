/**
 *******************************************************************************
 * 文件名：IXtszDao.java
 *
 * 描述�?
 * 
 * 创建日期：Feb 2, 2010 12:24:41 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dao.system;

import java.util.List;

import com.wfms.model.system.XtszEntity;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：IXtszDao
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Feb 2, 2010 12:24:41 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see IXtszglDao
 * @version 1.0
 *
 */
public interface IXtszglDao {
	
	public int insertXtsz(XtszEntity xtsz);
	
	public int updateXtsz(XtszEntity xtsz);
	
	public int deleteXtszByPk(int xtszId);
	
	public XtszEntity getXtszByKey(String key);
	
	public List<XtszEntity> getAllXtsz();
	
	public List<XtszEntity> getKdXtsz();
	
	public XtszEntity getXtszByKeyName(String keyName);
	
	public String getXtszValByKey(String key);
}
