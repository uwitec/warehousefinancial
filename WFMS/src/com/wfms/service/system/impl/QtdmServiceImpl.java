/**
 *******************************************************************************
 * 文件名：QtdmServiceImpl.java
 *
 * 描述�?
 * 
 * 创建日期：Jan 21, 2010 3:06:14 PM
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

import com.wfms.dao.system.IQtdmglDao;
import com.wfms.dto.system.QtdmDto;
import com.wfms.model.system.QtdmEntity;
import com.wfms.service.system.IQtdmglService;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：QtdmServiceImpl
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 21, 2010 3:06:14 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author GHT
 * @see QtdmServiceImpl
 * @version 1.0
 *
 */
@Service
public class QtdmServiceImpl implements IQtdmglService {
	@Autowired
	private IQtdmglDao qtdmglDao;
	
	public void setQtdmglDao(IQtdmglDao qtdmglDao) {
		this.qtdmglDao = qtdmglDao;
	}

	/**
	 * <dl>
	 * <b>方法�?:addQtdm</b>
	 * <dd>方法作用：添加其他代�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param qtdm
	 * <dd>@return
	 * <dd>@see com.is.system.service.IQtdmglService#addQtdm(com.is.system.entity.QtdmEntity)
	 * </dl>
	 */
	public int addQtdm(QtdmEntity qtdm) {
		return qtdmglDao.addQtdm(qtdm);
	}

	/**
	 * <dl>
	 * <b>方法�?:delQtdm</b>
	 * <dd>方法作用：删除其他代�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param id
	 * <dd>@return
	 * <dd>@see com.is.system.service.IQtdmglService#delQtdm(int)
	 * </dl>
	 */
	public int delQtdm(int id) {
		return qtdmglDao.delQtdm(id);
	}

	/**
	 * <dl>
	 * <b>方法�?:getQtdm</b>
	 * <dd>方法作用：获取其他代�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param dmlb
	 * <dd>@return
	 * <dd>@see com.is.system.service.IQtdmglService#getQtdm(java.lang.String)
	 * </dl>
	 */
	public QtdmDto getQtdm(String dmlb) {
		List<QtdmEntity> list = qtdmglDao.getQtdm(dmlb);
		QtdmDto dto = new QtdmDto();
		dto.setQtdmList(list);
		return dto;
	}

	/**
	 * <dl>
	 * <b>方法�?:updQtdm</b>
	 * <dd>方法作用：修改其他代�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param list
	 * <dd>@return
	 * <dd>@see com.is.system.service.IQtdmglService#updQtdm(java.util.List)
	 * </dl>
	 */
	public int updQtdm(List<QtdmEntity> list) {
		int row = 0;
		for(QtdmEntity qtdm : list ){
			try {
				row += qtdmglDao.updQtdm(qtdm);
			} catch (Exception e) {
				return 0;
			}
		}
		return row;
	}

}
