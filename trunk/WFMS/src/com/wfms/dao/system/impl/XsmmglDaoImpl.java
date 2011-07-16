/**
 *******************************************************************************
 * 文件名：XsmmglDaoImpl.java
 *
 * 描述�?
 * 
 * 创建日期：Mar 24, 2010 2:47:55 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dao.system.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.dao.system.IXsmmglDao;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：XsmmglDaoImpl
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 24, 2010 2:47:55 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see XsmmglDaoImpl
 * @version 1.0
 *
 */
@Repository
public class XsmmglDaoImpl extends BaseDao implements IXsmmglDao {

	/**
	 * <dl>
	 * <b>方法�?:getXsmmByXhMm</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param xh
	 * <dd>@param mm
	 * <dd>@return
	 * <dd>@see com.is.system.dao.IXsmmglDao#getXsmmByXhMm(java.lang.String, java.lang.String)
	 * </dl>
	 */
	public String getXsmmByXhMm(String xh, String mm) {
		// TODO Auto-generated method stub
		String querySql="select mm from XSMMB where xh=:xh and mm=:mm";
		Map<String, String> queryParams = new HashMap<String, String>(2);
		queryParams.put("xh", xh);
		queryParams.put("mm", mm);
		return (String)super.executeSQLScalar(querySql, queryParams);
	}

	public int batchInserXsmm(String xh, String mm, String type) {
		// TODO Auto-generated method stub
		String insertSql="";
		try {
			boolean flag = true;
			if(xh != null && !xh.trim().equals("")){//删除学生密码
				flag = false;
				super.executeSQLUpdate("delete from xsmmb where xh in('"+xh+"')");
			}
			//更改单独学生密码
				insertSql = "insert into xsmmb(xh,mm) select" ;
				switch(Integer.parseInt(type)){
				case 0://学号
					insertSql += " xh,xh from xsjbxxb xsxx where SFBY = '0'" ;//未毕业
					break;
				case 1://身份证后6位
					insertSql += " xh,case when SFZH is null or length(sfzh) < 18" +
							" then '0' else substr(SFZH,13,18) end mm" +
							" from xsjbxxb xsxx where SFBY = '0'" ;//未毕业
					break;
				case 2://管理员设置统一密码
					insertSql += " xh,'"+mm+"' from xsjbxxb xsxx where SFBY = '0'" ;//未毕业
					break;
				}

				if(flag){
					insertSql += " and xh is not null and not exists(" +
						"select null from xsmmb xsmm where xsmm.xh=xsxx.xh)";
				}else{
					insertSql += " and xh in('" +xh + "')";
				}
			return super.executeSQLUpdate(insertSql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public String getXsmmByXh(String xh) {
		// TODO Auto-generated method stub
		String querySql="select mm from xsmmb where xh=?";
		return (String)super.executeSQLScalar(querySql, xh);
	}
	
	/**
	 * 修改学生密码
	 */
	public int updateXsmm(String xh, String mm){
		int re = 0;
		String sql = "update XSMMB set mm='"+mm+"' where xh='"+xh+"'";
		try {
			re = super.executeSQLUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re;
	}
}
