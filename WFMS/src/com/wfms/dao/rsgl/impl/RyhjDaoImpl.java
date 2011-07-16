/**
 *******************************************************************************
 * 文件名：RyhjDaoImpl.java
 *
 * 描述�?
 * 
 * 创建日期：Mar 16, 2010 2:01:19 PM
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
import com.wfms.dao.rsgl.IRyhjDao;
import com.wfms.model.rsgl.RyhjEntity;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：RyhjDaoImpl
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 16, 2010 2:01:19 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see RyhjDaoImpl
 * @version 1.0
 *
 */
@Repository
public class RyhjDaoImpl extends BaseDao implements IRyhjDao {

	/**
	 * <dl>
	 * <b>方法�?:deleteRyhj</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param ryhjId
	 * <dd>@return
	 * <dd>@see com.is.rsgl.dao.IRyhjDao#deleteRyhj(int)
	 * </dl>
	 */
	public int deleteRyhj(int ryhjId) {
		return super.delete(RyhjEntity.class,ryhjId);
	}

	/**
	 * <dl>
	 * <b>方法�?:getRyhjByPage</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param page
	 * <dd>@return
	 * <dd>@see com.is.rsgl.dao.IRyhjDao#getRyhjByPage(com.is.common.entity.Page)
	 * </dl>
	 */
	public List<RyhjEntity> getRyhjByPage(Page page) {
		return super.executeQuery(RyhjEntity.class, page);
	}

	/**
	 * <dl>
	 * <b>方法�?:getRyhjCountByCond</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param condList
	 * <dd>@return
	 * <dd>@see com.is.rsgl.dao.IRyhjDao#getRyhjCountByCond(java.util.List)
	 * </dl>
	 */
	public int getRyhjCountByCond(List<ConditionBean> condList) {
		return super.getTotalCount(RyhjEntity.class,condList);
	}

	/**
	 * <dl>
	 * <b>方法�?:insertRyhj</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param ryhj
	 * <dd>@return
	 * <dd>@see com.is.rsgl.dao.IRyhjDao#insertRyhj(com.is.rsgl.entity.RyhjEntity)
	 * </dl>
	 */
	public int insertRyhj(RyhjEntity ryhj) {
		return super.add(ryhj);
	}

	/**
	 * <dl>
	 * <b>方法�?:updateRyhj</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param ryhj
	 * <dd>@return
	 * <dd>@see com.is.rsgl.dao.IRyhjDao#updateRyhj(com.is.rsgl.entity.RyhjEntity)
	 * </dl>
	 */
	public int batchUpdateRyhj(List<RyhjEntity> ryhjList) {
		return super.batchUpdate(ryhjList, ryhjList!=null?ryhjList.size():0);
	}

}
