/**
 *******************************************************************************
 * 文件名：XtszDaoImpl.java
 *
 * 描述�?
 * 
 * 创建日期：Feb 2, 2010 12:28:10 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dao.system.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.dao.system.IXtszglDao;
import com.wfms.model.system.XtszEntity;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：XtszDaoImpl
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Feb 2, 2010 12:28:10 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see XtszglDaoImpl
 * @version 1.0
 *
 */
@Repository
public class XtszglDaoImpl extends BaseDao implements IXtszglDao {

	/**
	 * <dl>
	 * <b>方法�?:deleteXtszByPk</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param xtszId
	 * <dd>@return
	 * <dd>@see com.is.system.dao.IXtszDao#deleteXtszByPk(int)
	 * </dl>
	 */
	public int deleteXtszByPk(int xtszId) {
		// TODO Auto-generated method stub
		return super.delete(XtszEntity.class,xtszId);
	}

	/**
	 * <dl>
	 * <b>方法�?:getAllXtsz</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@return
	 * <dd>@see com.is.system.dao.IXtszDao#getAllXtsz()
	 * </dl>
	 */
	public List<XtszEntity> getAllXtsz() {
		// TODO Auto-generated method stub
		return super.findAll(XtszEntity.class);
	}

	/**
	 * <dl>
	 * <b>方法�?:getXtszByKey</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param key
	 * <dd>@return
	 * <dd>@see com.is.system.dao.IXtszDao#getXtszByKey(java.lang.String)
	 * </dl>
	 */
	public XtszEntity getXtszByKey(String key) {
		// TODO Auto-generated method stub
		String queryHql="FROM XtszEntity where key='"+key+"'";
		return (XtszEntity)super.getEntityByConditions(queryHql);
	}

	/**
	 * <dl>
	 * <b>方法�?:getXtszByKeyName</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param keyName
	 * <dd>@return
	 * <dd>@see com.is.system.dao.IXtszDao#getXtszByKeyName(java.lang.String)
	 * </dl>
	 */
	public XtszEntity getXtszByKeyName(String keyName) {
		// TODO Auto-generated method stub
		String queryHql="FROM XtszEntity where keyName=:keyName";
		Map<String,String> queryParam=new HashMap<String, String>(1);
		queryParam.put("keyName",keyName);
		return (XtszEntity)super.executeScalar(queryHql, queryParam);
	}

	/**
	 * <dl>
	 * <b>方法�?:getXtszByPage</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param page
	 * <dd>@return
	 * <dd>@see com.is.system.dao.IXtszDao#getXtszByPage(com.is.common.entity.Page)
	 * </dl>
	 */
	public List<XtszEntity> getKdXtsz(){
		return super.getList("FROM XtszEntity where sfkd='1' order by sx");
	}

	/**
	 * <dl>
	 * <b>方法�?:insertXtsz</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param xtsz
	 * <dd>@return
	 * <dd>@see com.is.system.dao.IXtszDao#insertXtsz(com.is.system.entity.XtszEntity)
	 * </dl>
	 */
	public int insertXtsz(XtszEntity xtsz) {
		// TODO Auto-generated method stub
		return super.add(xtsz);
	}

	/**
	 * <dl>
	 * <b>方法�?:updateXtsz</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param xtsz
	 * <dd>@return
	 * <dd>@see com.is.system.dao.IXtszDao#updateXtsz(com.is.system.entity.XtszEntity)
	 * </dl>
	 */
	public int updateXtsz(XtszEntity xtsz) {
		// TODO Auto-generated method stub
		return super.update(xtsz);
	}

	public String getXtszValByKey(String key) {
		// TODO Auto-generated method stub
		String querySql="select value from xtszb where key=?";
		Object obj=super.executeSQLScalar(querySql, key);
		return String.valueOf(obj);
	}

}
