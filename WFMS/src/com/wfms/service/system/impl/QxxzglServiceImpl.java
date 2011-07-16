/**
 *******************************************************************************
 * 文件名：QxxzglService.java
 *
 * 描述�?
 * 
 * 创建日期：Jan 19, 2010 5:17:37 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.dao.system.IQxxzglDao;
import com.wfms.model.system.MemberRight;
import com.wfms.service.system.IQxxzglService;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：QxxzglService
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 19, 2010 5:17:37 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see QxxzglServiceImpl
 * @version 1.0
 *
 */
@Service
public class QxxzglServiceImpl implements IQxxzglService {

	@Autowired
	private IQxxzglDao qxxzglDao=null;
	
	public IQxxzglDao getQxxzglDao() {
		return qxxzglDao;
	}

	public void setQxxzglDao(IQxxzglDao qxxzglDao) {
		this.qxxzglDao = qxxzglDao;
	}

	/**
	 * <dl>
	 * <b>方法�?:grantYhQxxz</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@return
	 * <dd>@see com.is.system.service.IQxxzglService#grantYhQxxz()
	 * </dl>
	 */
	public int addYhQxxz(MemberRight qxxz) {
		// TODO Auto-generated method stub
		int res= qxxzglDao.addUpdateForQxxz(qxxz, qxxz.getMember().getMemid(), qxxz.getRight().getRightId());
		return res;
	}

	/**
	 * <dl>
	 * <b>方法�?:revokeYhQxxz</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@return
	 * <dd>@see com.is.system.service.IQxxzglService#revokeYhQxxz()
	 * </dl>
	 */
	public boolean delYhQxxz(MemberRight qxxz) {
		// TODO Auto-generated method stub
		int res= qxxzglDao.addUpdateForQxxz(qxxz, qxxz.getMember().getMemid(), qxxz.getRight().getRightId());
		return res>0?true:false;
	}

	/**
	 * 
	 * <dl>
	 * <b>方法�?:delAllYhQxxz</b>
	 * <dd>方法作用：根据用户编�?,删除该用户的�?有权限限制记�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param yhId int用户编号
	 * <dd>@return boolean
	 * <dd>@see com.is.system.service.IQxxzglService#delAllYhQxxz(int)
	 * </dl>
	 */
	public boolean delAllYhQxxz(int yhId) {
		// TODO Auto-generated method stub
		int res = qxxzglDao.deleteQxxzByYh(yhId);
		return res>0?true:false;
	}

}
