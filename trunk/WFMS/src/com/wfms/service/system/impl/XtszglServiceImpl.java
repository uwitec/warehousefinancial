/**
 *******************************************************************************
 * 文件名：XtszService.java
 *
 * 描述�?
 * 
 * 创建日期：Feb 4, 2010 11:10:17 AM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.dao.system.IXtszglDao;
import com.wfms.dto.system.XtszDto;
import com.wfms.model.system.XtszEntity;
import com.wfms.service.system.IXtszglService;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：XtszService
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Feb 4, 2010 11:10:17 AM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see XtszglServiceImpl
 * @version 1.0
 *
 */
@Service
public class XtszglServiceImpl implements IXtszglService {

	@Autowired
	private IXtszglDao xtszglDao=null;
	
	public void setXtszglDao(IXtszglDao xtszglDao) {
		this.xtszglDao = xtszglDao;
	}

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
	 * <dd>@see com.is.system.service.IXtszService#deleteXtszByPk(int)
	 * </dl>
	 */
	public int deleteXtszByPk(int xtszId) {
		return xtszglDao.deleteXtszByPk(xtszId);
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
	 * <dd>@see com.is.system.service.IXtszService#getAllXtsz()
	 * </dl>
	 */
	public List<XtszEntity> getAllXtsz() {
		return xtszglDao.getAllXtsz();
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
	 * <dd>@see com.is.system.service.IXtszService#getXtszByKey(java.lang.String)
	 * </dl>
	 */
	public XtszEntity getXtszByKey(String key) {
		return xtszglDao.getXtszByKey(key);
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
	 * <dd>@see com.is.system.service.IXtszService#getXtszByKeyName(java.lang.String)
	 * </dl>
	 */
	public XtszEntity getXtszByKeyName(String keyName) {
		return xtszglDao.getXtszByKeyName(keyName);
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
	 * <dd>@see com.is.system.service.IXtszService#getXtszByPage(com.is.common.entity.Page)
	 * </dl>
	 */
	public XtszDto getKdXtsz(){
		XtszDto dto = new XtszDto();
		dto.setXtszList(xtszglDao.getKdXtsz());
		return dto;
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
	 * <dd>@see com.is.system.service.IXtszService#insertXtsz(com.is.system.entity.XtszEntity)
	 * </dl>
	 */
	public int insertXtsz(XtszEntity xtsz) {
		return xtszglDao.insertXtsz(xtsz);
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
	 * <dd>@see com.is.system.service.IXtszService#updateXtsz(com.is.system.entity.XtszEntity)
	 * </dl>
	 */
	public int updateXtsz(List<XtszEntity> xtszList) {
		int count = 0;
		for(XtszEntity xtsz : xtszList){
			try{
				count += xtszglDao.updateXtsz(xtsz);
			}catch (Exception e) {
				return 0;
			}
		}
		return count;
	}

}
