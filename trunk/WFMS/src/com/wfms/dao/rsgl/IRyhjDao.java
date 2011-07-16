/**
 *******************************************************************************
 * 文件名：IRyhjDao.java
 *
 * 描述�?
 * 
 * 创建日期：Mar 16, 2010 1:56:50 PM
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
import com.wfms.model.rsgl.RyhjEntity;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：IRyhjDao
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 16, 2010 1:56:50 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see IRyhjDao
 * @version 1.0
 *
 */
public interface IRyhjDao {
	
	public int insertRyhj(RyhjEntity ryhj);
	
	public int batchUpdateRyhj(List<RyhjEntity> ryhjList);
	
	public int deleteRyhj(int ryhjId);

	public List<RyhjEntity> getRyhjByPage(Page page);
	
	public int getRyhjCountByCond(List<ConditionBean> condList);
}
