/**
 *******************************************************************************
 * 文件名：RyhjServiceImpl.java
 *
 * 描述�?
 * 
 * 创建日期：Mar 16, 2010 2:05:10 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.service.rsgl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.common.entity.Page;
import com.wfms.dao.rsgl.IRyhjDao;
import com.wfms.model.rsgl.RyhjEntity;
import com.wfms.service.rsgl.IRyhjService;

/**
 * <dl>
 * Description
 * <dd> 项目名称：数字化校园信息平台ECMS
 * <dd> 类名称：RyhjServiceImpl
 * <dd> 类描述：
 * <dd> 创建人：Administrator
 * <dd> 创建时间：Mar 16, 2010 2:05:10 PM
 * <dd> 修改人：�?
 * <dd> 修改时间：无
 * <dd> 修改备注：无
 * </dl>
 * 
 * @author CYC
 * @see RyhjServiceImpl
 * @version 1.0
 * 
 */
@Service
public class RyhjServiceImpl implements IRyhjService {
	@Autowired
	private IRyhjDao ryhjDao;

	public void setRyhjDao(IRyhjDao ryhjDao) {
		this.ryhjDao = ryhjDao;
	}

	/**
	 * <dl>
	 * <b>方法�?:addRyhj</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??)
	 * <dd>
	 * 
	 * @param ryhj
	 *            <dd>
	 * @return
	 *            <dd>
	 * @see com.is.rsgl.service.IRyhjService#addRyhj(com.is.rsgl.entity.RyhjEntity)
	 *      </dl>
	 */
	public int addRyhj(RyhjEntity ryhj) {
		return ryhjDao.insertRyhj(ryhj);
	}

	/**
	 * <dl>
	 * <b>方法�?:deleteRyhj</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??)
	 * <dd>
	 * 
	 * @param ryhjId
	 *            <dd>
	 * @return
	 *            <dd>
	 * @see com.is.rsgl.service.IRyhjService#deleteRyhj(int)
	 *      </dl>
	 */
	public int deleteRyhj(int ryhjId) {
		return ryhjDao.deleteRyhj(ryhjId);
	}

	/**
	 * <dl>
	 * <b>方法�?:loadRyhjByPage</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??)
	 * <dd>
	 * 
	 * @param page
	 *            <dd>
	 * @return
	 *            <dd>
	 * @see com.is.rsgl.service.IRyhjService#loadRyhjByPage(com.is.common.entity.Page)
	 *      </dl>
	 */
	public Page loadRyhjByPage(Page page) {
		if (page == null) {
			page = new Page();
		}
		page.setDataList(ryhjDao.getRyhjByPage(page));
		page.setTotalCount(ryhjDao.getRyhjCountByCond(page.getConditionList()));
		return page;
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
	 * <dd>
	 * 
	 * @param ryhj
	 *            <dd>
	 * @return
	 *            <dd>
	 * @see com.is.rsgl.service.IRyhjService#updateRyhj(com.is.rsgl.entity.RyhjEntity)
	 *      </dl>
	 */
	public int updateRyhjList(List<RyhjEntity> ryhjList) {
		return ryhjDao.batchUpdateRyhj(ryhjList);
	}

}
