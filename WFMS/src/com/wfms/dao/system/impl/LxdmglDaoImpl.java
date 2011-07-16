/**
 *******************************************************************************
 * 文件名：LxdmglDaoImpl.java
 *
 * 描述�?
 * 
 * 创建日期：Jan 19, 2010 1:31:53 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dao.system.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.dao.system.ILxdmglDao;
import com.wfms.model.system.LxdmEntity;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：LxdmglDaoImpl
 *  <dd> 类描述：类型代码Dao实现�?
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 19, 2010 1:31:53 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see LxdmglDaoImpl
 * @version 1.0
 *
 */
@Repository
public class LxdmglDaoImpl extends BaseDao implements ILxdmglDao {

	/**
	 * <dl>
	 * <b>方法�?:deleteLxdm</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param lxdm
	 * <dd>@return
	 * <dd>@see com.is.system.dao.ILxdmglDao#deleteLxdm(com.is.system.entity.LxdmEntity)
	 * </dl>
	 */
	public int deleteLxdm(int lxdmId) {
		String sql="delete from lxdmb where sfkbj='1' and id=?";
		try {
			return super.executeSQLUpdate(sql,new String[]{String.valueOf(lxdmId)});
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * <dl>
	 * <b>方法�?:getAllLxdm</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@return
	 * <dd>@see com.is.system.dao.ILxdmglDao#getAllLxdm()
	 * </dl>
	 */
	public List<LxdmEntity> getAllLxdm() {
		return super.findAll(LxdmEntity.class);
	}

	/**
	 * <dl>
	 * <b>方法�?:getAllLxdmBySuper</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param superDm
	 * <dd>@return
	 * <dd>@see com.is.system.dao.ILxdmglDao#getAllLxdmBySuper(java.lang.String)
	 * </dl>
	 */
	public List<LxdmEntity> getSubLxdmBySupDm(String superDm) {
		String hql="FROM LxdmEntity lxdm where lxdm.sjlxdm=?";
		Object obj = super.executeQuery(hql, superDm);
		return obj == null ? null : (List<LxdmEntity>)obj;
	}
	
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:getSubLxdmByDmjc</b>
	 * <dd>方法作用：根据代码简称查询所有子级类型代�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param dmjc			代码�?�?
	 * <dd>@return
	 * </dl>
	 */
	public List<LxdmEntity> getSubLxdmBySuperJc(String superJc) {
		String hql="SELECT lx FROM LxdmEntity lx WHERE lx.sjlxdm in (SELECT l.lxdm FROM LxdmEntity l WHERE l.dmjc=? and l.sjlxdm='0') order by lx.id";
		Object obj = super.executeQuery(hql, superJc);
		return obj == null ? null : (List<LxdmEntity>)obj;
	}

	
	/**
	 * <dl>
	 * <b>方法�?:getAllLxdmCategory</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@return
	 * <dd>@see com.is.system.dao.ILxdmglDao#getAllLxdmCategory()
	 * </dl>
	 */
	public List<LxdmEntity> getAllLxdmCategory() {
		return getSubLxdmBySupDm("0");
	}

	/**
	 * <dl>
	 * <b>方法�?:insertLxdm</b>
	 * <dd>方法作用：增加类型代�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param lxdm LxdmEntity
	 * <dd>@return
	 * <dd>@see com.is.system.dao.ILxdmglDao#insertLxdm(com.is.system.entity.LxdmEntity)
	 * </dl>
	 */
	public int insertLxdm(LxdmEntity lxdm) {
		String maxLxdm = String.valueOf(super.executeSQLScalar("select max(lxdm) from lxdmb where sjlxdm=?",lxdm.getSjlxdm()));
		if(maxLxdm == null || "".equals(maxLxdm))
		{
			lxdm.setLxdm(lxdm.getSjlxdm()+"01");
		}
		else
		{
			lxdm.setLxdm(String.valueOf(Integer.valueOf(maxLxdm)+1));
		}
		Serializable sid=super.insert(lxdm);
		int fetchCount=Integer.valueOf(String.valueOf(sid));
		return fetchCount;
	}

	/**
	 * <dl>
	 * <b>方法�?:updLxdm</b>
	 * <dd>方法作用：更新类型代�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param lxdm LxdmEntity
	 * <dd>@return fetchCount
	 * <dd>@see com.is.system.dao.ILxdmglDao#updLxdm(com.is.system.entity.LxdmEntity)
	 * </dl>
	 */
	public int updLxdm(LxdmEntity lxdm) {
		int fetchCount=0;
		fetchCount = super.update(lxdm);
		return fetchCount;
	}

	public LxdmEntity getLxdmBySuperDmAndDmmc(String superDm, String dmmc) {
		String queryHql="FROM LxdmEntity where sjlxdm=:sjlxdm and dmmc=:dmmc";
		Map<String,String> queryParams=new HashMap<String, String>(2);
		queryParams.put("sjlxdm", superDm);
		queryParams.put("dmmc", dmmc);
		Object obj = super.executeScalar(queryHql, queryParams);
		if(obj!=null)
		{
			return (LxdmEntity)obj;
		}
		return null;
	}

	public String getDmByCkbCkzd(String wbzd, String wbz, String nbzd,
			String ckb) {
		String querySql="select "+nbzd+" from "+ckb+" where "+wbzd+"='"+wbz+"'";
		Object obj=super.executeSQLScalar(querySql, null);
		if(obj!=null)
		{
			return obj.toString();
		}
		return null;
	}

	public String getDmmcByLxdm(String lxdm) {
		String sql="select dmmc from lxdmb where lxdm=?";
		return String.valueOf(super.executeSQLScalar(sql, lxdm));
	}

	public List<String> getAllDmmk() {
		String querySql="select mkmc from lxdmb group by mkmc,sjlxdm having mkmc is not null and sjlxdm='0'";
		List<String> mkmcList = super.executeSQLQuery(querySql);
		return mkmcList;
	}

	public List<LxdmEntity> getLxdmByMkmc(String mkmc) {
		String queryHql="FROM LxdmEntity where mkmc=? and sjlxdm='0'";
		return super.executeQuery(queryHql, mkmc);
	}

	public List<LxdmEntity> getLxdmBySuperDmCond(Page page) {
		 return super.executeQuery(LxdmEntity.class, page);
	}


	public int getLxdmCountByPage(List<ConditionBean> cons) {
		return super.getTotalCount(LxdmEntity.class,cons);
	}

	public int updateLxdm(List<LxdmEntity> lxdm) {
		return super.batchUpdate(lxdm,15);
	}

}
