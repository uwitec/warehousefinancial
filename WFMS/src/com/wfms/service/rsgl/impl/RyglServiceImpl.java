/**
 *******************************************************************************
 * 文件名：RyglServiceImpl.java
 *
 * 描述�?
 * 
 * 创建日期：Jan 22, 2010 3:53:45 PM
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
import com.wfms.dao.rsgl.IRyglDao;
import com.wfms.dao.system.IYhglDao;
import com.wfms.dto.rsgl.RyDto;
import com.wfms.model.rsgl.RyEntity;
import com.wfms.model.system.MemberGenInfo;
import com.wfms.service.rsgl.IRyglService;

/**
 * <dl>
 * Description
 * <dd> 项目名称：数字化校园信息平台ECMS
 * <dd> 类名称：RyglServiceImpl
 * <dd> 类描述：
 * <dd> 创建人：Administrator
 * <dd> 创建时间：Jan 22, 2010 3:53:45 PM
 * <dd> 修改人：�?
 * <dd> 修改时间：无
 * <dd> 修改备注：无
 * </dl>
 * 
 * @author CYC
 * @see RyglServiceImpl
 * @version 1.0
 * 
 */
@Service
public class RyglServiceImpl implements IRyglService {
	@Autowired
	private IRyglDao ryglDao;
	@Autowired
	private IYhglDao yhglDao;

	public void setYhglDao(IYhglDao yhglDao) {
		this.yhglDao = yhglDao;
	}

	public void setRyglDao(IRyglDao ryglDao) {
		this.ryglDao = ryglDao;
	}

	public Page loadRyForPage(Page page) {
		List<RyEntity> ryList = ryglDao.getRyByPage(page);
		int ryCount = ryglDao.getRyCountByParams(page.getConditionList());
		page.setDataList(ryList);
		page.setTotalCount(ryCount);
		return page;
	}

	public int addRy(RyEntity ry) {
		return ryglDao.insertRy(ry);
	}

	public int deleteRy(int[] ryId) {
		int count = 0;
		for (int id : ryId) {
			count += ryglDao.deleteRy(id);
		}
		return count;
	}

	public int updateRy(RyEntity ry) {
		return ryglDao.updateRy(ry);
	}

	public List<RyEntity> loadRyByBmAndExistsYh(String bmId, boolean existsAble) {
		return null;//ryglDao.getRyByBmAndExsitsUser(bmId, existsAble);
	}

	public RyDto getRyByIds(int[] ids) {
		RyDto dto = new RyDto();
		List<RyEntity> ryList = ryglDao.getRyByIds(ids);
		dto.setRyList(ryList);
		return dto;
	}

	public int updateRyList(List<RyEntity> ryList) {
		int count = 0;
		for (RyEntity ry : ryList) {
			try {
				count += ryglDao.updateRy(ry);
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		}
		return count;
	}

	public RyDto loadRybyBm(String bmId) {
		RyDto ryDto = new RyDto();
//		List<RyEntity> ryList = ryglDao.getRyByBmId(bmId);
//		ryDto.setRyList(ryList);
		ryDto.setRyList(null);
		return ryDto;
	}

	public String addYhForRy(int ryId, String dlzh, String dlmm) {
		RyEntity ry = ryglDao.getRyById(ryId);
		if (ry == null || ry.getId() == 0) {
			return "-1";
		}
		MemberGenInfo y = yhglDao.getYhByDlzh(dlzh);
		if (y != null && !"".equals(y.getMemid())) {
			return "-2";
		}
		MemberGenInfo yh = new MemberGenInfo();
		yh.setDlcs(0);
		yh.setPassword(dlmm);
		yh.setLoginid(dlzh);
		yh.setMobilephone(ry.getSjhm());
		yh.setRy(ry);
		yh.setResign("true");
		yh.setUsername(ry.getRyxm());
		String row = yhglDao.addYh(yh);
		return row;
	}

	public RyDto loadRyByNotLsAnBm(String bmId) {
		RyDto ryDto = new RyDto();
//		List<RyEntity> ryList = ryglDao.getRyByIfLsAndBm(bmId, false);
//		ryDto.setRyList(ryList);
		ryDto.setRyList(null);
		return ryDto;
	}

	public RyDto getRyById(int id) {
		RyEntity ry = ryglDao.getRyById(id);
		RyDto dto = new RyDto();
		dto.setRy(ry);
		return dto;
	}

	public List<RyEntity> getAllRy() {
		return this.ryglDao.getAllRy();
	}
}
