/**
 *******************************************************************************
 * 文件名：BhscgzglDaoImpl.java
 *
 * 描述�?
 * 
 * 创建日期：Jan 20, 2010 7:56:57 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dao.system.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.dao.system.IBhscgzglDao;
import com.wfms.model.system.BhscgzszEntity;
import com.wfms.model.system.BhscgztjEntity;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：BhscgzglDaoImpl
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 20, 2010 7:56:57 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see BhscgzglDaoImpl
 * @version 1.0
 *
 */
@Repository
public class BhscgzglDaoImpl extends BaseDao implements IBhscgzglDao {

	/**
	 * <dl>
	 * <b>方法�?:deleteBhscgz</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param bhscgzId
	 * <dd>@return
	 * <dd>@see com.is.system.dao.IBhscgzglDao#deleteBhscgz(int)
	 * </dl>
	 */
	public int deleteBhscgz(int bhscgzId) {
		// TODO Auto-generated method stub
		return super.delete(BhscgzszEntity.class,bhscgzId);
	}

	/**
	 * <dl>
	 * <b>方法�?:getBhscgzByGzmc</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param gzmc
	 * <dd>@return
	 * <dd>@see com.is.system.dao.IBhscgzglDao#getBhscgzByGzmc(java.lang.String)
	 * </dl>
	 */
	public List<BhscgzszEntity> getBhscgzByGzmc(String gzmc) {
		// TODO Auto-generated method stub
		String hql="FROM BhscgzszEntity where scgzmc='"+gzmc+"' order by scsx asc";
		Object obj = super.getList(hql);
		return obj == null ? null : (List<BhscgzszEntity>)obj;
	}

	/**
	 * <dl>
	 * <b>方法�?:insertBhscgz</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param bhscgz
	 * <dd>@return
	 * <dd>@see com.is.system.dao.IBhscgzglDao#insertBhscgz(com.is.system.entity.BhscgzEntity)
	 * </dl>
	 */
	public int insertBhscgz(BhscgzszEntity bhscgz) {
		// TODO Auto-generated method stub
		return super.add(bhscgz);
	}

	/**
	 * <dl>
	 * <b>方法�?:updateBhscgz</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param bhscgz
	 * <dd>@return
	 * <dd>@see com.is.system.dao.IBhscgzglDao#updateBhscgz(com.is.system.entity.BhscgzEntity)
	 * </dl>
	 */
	public int updateBhscgz(BhscgzszEntity bhscgz) {
		// TODO Auto-generated method stub
		return super.update(bhscgz);
	}
	public BhscgztjEntity getBhtjByFjid(int id){
		String hql = "FROM BhscgztjEntity where fjid="+id;
		Object obj = super.getEntityByConditions(hql);
		return obj == null ? null : (BhscgztjEntity)obj;
	}
	
	public BhscgztjEntity getBhtjByid(int id){
		Object obj = super.getByPk(BhscgztjEntity.class, id);
		return obj == null ? null : (BhscgztjEntity)obj;
	}
	public String getTjValue(String sql){
		return String.valueOf(super.executeSQLScalar(sql));
	}
}
