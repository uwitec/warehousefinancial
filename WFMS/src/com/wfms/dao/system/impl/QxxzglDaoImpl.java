/**
 *******************************************************************************
 * 文件名：QxxzglDaoImpl.java
 *
 * 描述�?
 * 
 * 创建日期：Jan 19, 2010 2:57:31 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dao.system.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.dao.system.IQxxzglDao;
import com.wfms.model.system.MemberRight;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：QxxzglDaoImpl
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 19, 2010 2:57:31 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see QxxzglDaoImpl
 * @version 1.0
 *
 */
@Repository
public class QxxzglDaoImpl extends BaseDao implements IQxxzglDao {

	/**
	 * <dl>
	 * <b>方法�?:addQxxz</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param qxxz
	 * <dd>@return
	 * <dd>@see com.is.system.dao.IQxxzglDao#addQxxz(com.is.system.entity.QxxzEntity)
	 * </dl>
	 */
	public int addQxxz(MemberRight qxxz) {
		// TODO Auto-generated method stub
		return super.add(qxxz);
	}

	/**
	 * <dl>
	 * <b>方法�?:deleteQxxzByYh</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param yhId
	 * <dd>@return
	 * <dd>@see com.is.system.dao.IQxxzglDao#deleteQxxzByYh(int)
	 * </dl>
	 */
	public int deleteQxxzByYh(int yhId) {
		// TODO Auto-generated method stub
		Map<String,Integer> attVals=new HashMap<String, Integer>();
		attVals.put("yh.id",yhId);
		return super.executeDelete(MemberRight.class, attVals);
	}

	/**
	 * <dl>
	 * <b>方法�?:updateQxxz</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param qxxz
	 * <dd>@return
	 * <dd>@see com.is.system.dao.IQxxzglDao#updateQxxz(com.is.system.entity.QxxzEntity)
	 * </dl>
	 */
	public int updateQxxz(MemberRight qxxz) {
		// TODO Auto-generated method stub
		return super.update(qxxz);
	}

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
	public int  addUpdateForQxxz(MemberRight qxxz,String yhId,int qxId)
	{
		Map params=new HashMap(2);
		params.put("yh.id", yhId);
		params.put("qx.id", qxId);
		return super.addUpdByQueryExt(qxxz, params);
	}
}
