/**
 *******************************************************************************
 * 文件名：RyydDaoImpl.java
 *
 * 描述�?
 * 
 * 创建日期：Apr 16, 2010 1:56:12 PM
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
import com.wfms.dao.rsgl.IRyydDao;
import com.wfms.model.rsgl.RyydEntity;

/**
 * <dl>  Description
 *  <dd> 项目名称：迅尔数字化校园信息平台ECMS
 *  <dd> 类名称：RyydDaoImpl
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Apr 16, 2010 1:56:12 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author ECMS
 * @see RyydDaoImpl
 * @version 1.0
 *
 */
@Repository
public class RyydDaoImpl extends BaseDao implements IRyydDao {

	/**
	 * <dl>
	 * <b>方法�?:addRyyd</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param ryyd
	 * <dd>@return
	 * <dd>@see com.is.rsgl.dao.IRyydDao#addRyyd(com.is.rsgl.entity.RyydEntity)
	 * </dl>
	 */
	public int addRyyd(RyydEntity ryyd) {
		return super.add(ryyd);
	}

	/**
	 * <dl>
	 * <b>方法�?:deleteRyydById</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param id
	 * <dd>@return
	 * <dd>@see com.is.rsgl.dao.IRyydDao#deleteRyydById(int)
	 * </dl>
	 */
	public int deleteRyydById(int id) {
		return super.delete(RyydEntity.class,id);
	}

	/**
	 * <dl>
	 * <b>方法�?:getRyydById</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param id
	 * <dd>@return
	 * <dd>@see com.is.rsgl.dao.IRyydDao#getRyydById(int)
	 * </dl>
	 */
	public RyydEntity getRyydById(int id) {
		return (RyydEntity)super.getByPk(RyydEntity.class, id);
	}

	/**
	 * <dl>
	 * <b>方法�?:getRyydByPage</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param page
	 * <dd>@return
	 * <dd>@see com.is.rsgl.dao.IRyydDao#getRyydByPage(com.is.common.entity.Page)
	 * </dl>
	 */
	public List<RyydEntity> getRyydByPage(Page page) {
		return super.executeQuery(RyydEntity.class, page);
	}

	/**
	 * <dl>
	 * <b>方法�?:getRyydCount</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param list
	 * <dd>@return
	 * <dd>@see com.is.rsgl.dao.IRyydDao#getRyydCount(java.util.List)
	 * </dl>
	 */
	public int getRyydCount(List<ConditionBean> list) {
		return super.getTotalCount(RyydEntity.class,list);
	}

}
