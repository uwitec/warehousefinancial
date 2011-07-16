/**
 *******************************************************************************
 * 文件名：RyjtcyServiceImpl.java
 *
 * 描述�?
 * 
 * 创建日期：Apr 29, 2010 9:14:40 AM
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
import com.wfms.dao.rsgl.IRyjtcyDao;
import com.wfms.dto.rsgl.RyjtcyDto;
import com.wfms.model.rsgl.RyjtcyEntity;
import com.wfms.service.rsgl.IRyjtcyService;

/**
 * <dl>
 * Description
 * <dd> 项目名称：数字化校园信息平台ECMS
 * <dd> 类名称：RyjtcyServiceImpl
 * <dd> 类描述：
 * <dd> 创建人：Administrator
 * <dd> 创建时间：Apr 29, 2010 9:14:40 AM
 * <dd> 修改人：�?
 * <dd> 修改时间：无
 * <dd> 修改备注：无
 * </dl>
 * 
 * @author CYC
 * @see RyjtcyServiceImpl
 * @version 1.0
 * 
 */
@Service
public class RyjtcyServiceImpl implements IRyjtcyService {

	@Autowired
	private IRyjtcyDao ryjtcyDao;

	public void setRyjtcyDao(IRyjtcyDao ryjtcyDao) {
		this.ryjtcyDao = ryjtcyDao;
	}

	public int addRyjtcy(RyjtcyEntity ryjtcy) {
		return ryjtcyDao.insertRyjtcy(ryjtcy);
	}

	public int deleteRyjtcy(int ryjtcyId) {
		return ryjtcyDao.deleteRyjtcy(ryjtcyId);
	}

	public RyjtcyDto loadRyjtcyByRy(int ryId) {
		RyjtcyDto ryjtcyDto = new RyjtcyDto();
		ryjtcyDto.setRyjtcyList(ryjtcyDao.getRyjtcyByRy(ryId));
		return ryjtcyDto;
	}

	public int updateRyjtcyList(List<RyjtcyEntity> ryjtcyList) {
		return ryjtcyDao.batchUpdateRyjtcy(ryjtcyList);
	}

	public Page getRyjtcyByPage(Page page) {
		if (page == null) {
			page = new Page();
		}
		page.setDataList(this.ryjtcyDao.getRyjtcyByPage(page));
		page.setTotalCount(this.ryjtcyDao.getRyjtcyCount(page
				.getConditionList()));
		return page;
	}

}
