/**
 *******************************************************************************
 * 文件名：RyjtcyDaoImpl.java
 *
 * 描述�?
 * 
 * 创建日期：Apr 29, 2010 9:01:34 AM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dao.rsgl.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.dao.rsgl.IRyjtcyDao;
import com.wfms.model.rsgl.RyjtcyEntity;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：RyjtcyDaoImpl
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Apr 29, 2010 9:01:34 AM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see RyjtcyDaoImpl
 * @version 1.0
 *
 */
@Repository
public class RyjtcyDaoImpl extends BaseDao implements IRyjtcyDao {

	public int batchUpdateRyjtcy(List<RyjtcyEntity> ryjtcyList) {
		return super.batchUpdate(ryjtcyList, ryjtcyList!=null?ryjtcyList.size():0);
	}

	public int deleteRyjtcy(int ryjtcyId) {
		return super.delete(RyjtcyEntity.class,ryjtcyId);
	}

	public List<RyjtcyEntity> getRyjtcyByRy(int ryId) {
		return super.executeQuery("FROM RyjtcyEntity where ry.id",ryId);
	}

	public int insertRyjtcy(RyjtcyEntity ryjtcy) {
		return super.add(ryjtcy);
	}

	public int updateRyjtcy(RyjtcyEntity ryjtcy) {
		return super.update(ryjtcy);
	}

	public List<RyjtcyEntity> getRyjtcyByPage(Page page) {
		return super.executeQuery(RyjtcyEntity.class, page);
	}

	public int getRyjtcyCount(List<ConditionBean> cons) {
		return super.getTotalCount(RyjtcyEntity.class, cons);
	}

}
