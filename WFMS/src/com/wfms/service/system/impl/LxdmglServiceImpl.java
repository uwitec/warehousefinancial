/**
 *******************************************************************************
 * 文件名：LxdmglServiceImpl.java
 *
 * 描述�?
 * 
 * 创建日期：Jan 26, 2010 1:00:37 PM
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

import com.wfms.common.entity.Page;
import com.wfms.dao.system.ILxdmglDao;
import com.wfms.dto.system.LxdmDto;
import com.wfms.model.system.LxdmEntity;
import com.wfms.service.system.ILxdmglService;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：LxdmglServiceImpl
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 26, 2010 1:00:37 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see LxdmglServiceImpl
 * @version 1.0
 *
 */
@Service
public class LxdmglServiceImpl implements ILxdmglService {

	@Autowired
	private ILxdmglDao lxdmDao=null;
	
	public ILxdmglDao getLxdmDao() {
		return lxdmDao;
	}

	public void setLxdmDao(ILxdmglDao lxdmDao) {
		this.lxdmDao = lxdmDao;
	}

	/**
	 * <dl>
	 * <b>方法�?:getSubLxdmBySuperJc</b>
	 * <dd>方法作用�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param superJc
	 * <dd>@return
	 * <dd>@see com.is.system.service.ILxdmService#getSubLxdmBySuperJc(java.lang.String)
	 * </dl>
	 */
	public LxdmDto loadSubLxdmBySupJc(String superJc) {
		// TODO Auto-generated method stub
		LxdmDto lxdmDto=new LxdmDto();
		List<LxdmEntity> lxdmList = lxdmDao.getSubLxdmBySuperJc(superJc);
		lxdmDto.setLxdmList(lxdmList);
		return lxdmDto;
	}

	public LxdmDto loadSubLxdmBySupDm(String superDm) {
		// TODO Auto-generated method stub
		LxdmDto lxdmDto=new LxdmDto();
		List<LxdmEntity> lxdmList = lxdmDao.getSubLxdmBySupDm(superDm);
		lxdmDto.setLxdmList(lxdmList);
		return lxdmDto;
	}

	public List<String> loadAllLxdmMk() {
		return lxdmDao.getAllDmmk();
	}

	public List<LxdmEntity> loadLxdmByMkmc(String mkmc) {
		return lxdmDao.getLxdmByMkmc(mkmc);
	}

	public Page loadLxdmBySuperDm(Page page) {
		if(page == null)
		{
			page = new Page();
		}
		page.setDataList(lxdmDao.getLxdmBySuperDmCond(page));
		page.setTotalCount(lxdmDao.getLxdmCountByPage(page.getConditionList()));
		return page;
	}

	public int addLxdm(LxdmEntity lxdm) {
		return lxdmDao.insertLxdm(lxdm);
	}

	public int updateLxdm(List<LxdmEntity> lxdm) {
		return lxdmDao.updateLxdm(lxdm);
	}

	public int deleteLxdm(int lxdmId) {
		return lxdmDao.deleteLxdm(lxdmId);
	}

	public String getDmmcByLxdm(String lxdm) {
		return lxdmDao.getDmmcByLxdm(lxdm);
	}

}
