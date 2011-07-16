/**
 *******************************************************************************
 * 文件名：IRyjtcyDao.java
 *
 * 描述�?
 * 
 * 创建日期：Apr 29, 2010 8:59:03 AM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dao.rsgl;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.model.rsgl.RyjtcyEntity;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：IRyjtcyDao
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Apr 29, 2010 8:59:03 AM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see IRyjtcyDao
 * @version 1.0
 *
 */
public interface IRyjtcyDao {

	public int insertRyjtcy(RyjtcyEntity ryjtcy);
	
	public int updateRyjtcy(RyjtcyEntity ryjtcy);
	
	public int batchUpdateRyjtcy(List<RyjtcyEntity> ryjtcyList);
	
	public int deleteRyjtcy(int ryjtcyId);
	
	public List<RyjtcyEntity> getRyjtcyByRy(int ryId);
	
	public List<RyjtcyEntity> getRyjtcyByPage(Page page);
	
	public int getRyjtcyCount(List<ConditionBean> cons);
}
