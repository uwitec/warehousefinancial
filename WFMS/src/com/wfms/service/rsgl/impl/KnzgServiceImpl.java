/**
 *******************************************************************************
 * 文件名：KnzgService.java
 *
 * 描述�?
 * 
 * 创建日期：Apr 23, 2010 6:53:22 PM
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
import com.wfms.dao.rsgl.IKnzgDao;
import com.wfms.dto.rsgl.KnzgDto;
import com.wfms.model.rsgl.KnzgEntity;
import com.wfms.service.rsgl.IKnzgService;

/**
 * <dl>
 * Description
 * <dd> 项目名称：数字化校园信息平台ECMS
 * <dd> 类名称：KnzgService
 * <dd> 类描述：
 * <dd> 创建人：Administrator
 * <dd> 创建时间：Apr 23, 2010 6:53:22 PM
 * <dd> 修改人：�?
 * <dd> 修改时间：无
 * <dd> 修改备注：无
 * </dl>
 * 
 * @author ZHANGYONG
 * @see KnzgServiceImpl
 * @version 1.0
 * 
 */
@Service
public class KnzgServiceImpl implements IKnzgService {
	@Autowired
	private IKnzgDao knzgDao;

	public void setKnzgDao(IKnzgDao knzgDao) {
		this.knzgDao = knzgDao;
	}

	public int addKnzg(KnzgEntity knzg) {
		if (knzg == null) {
			return -1;
		}
		return this.knzgDao.addKnzg(knzg);
	}

	public int deleteKnzg(int id) {
		KnzgEntity knzg = this.knzgDao.getKnzg(id);
		if (knzg == null) {
			return -1;
		}
		return this.knzgDao.deleteKnzg(id);
	}

	public Page getKnzgByPage(Page page) {
		if (page == null) {
			page = new Page();
		}
		page.setDataList(this.knzgDao.getKnzgByPage(page));
		page.setTotalCount(this.knzgDao.getKnzgCount(page.getConditionList()));
		return page;
	}

	public int updateKnzg(List<KnzgEntity> knzgList) {
		return this.knzgDao.updateKnzg(knzgList);
	}

	public KnzgEntity getKnzgById(int id) {
		KnzgEntity knzg = this.knzgDao.getKnzg(id);
		return knzg;
	}

	public KnzgDto loadKnzgByRyId(int ryId) {
		KnzgDto knzgDto = new KnzgDto();
		knzgDto.setKnzg(knzgDao.getKnzgByRyId(ryId));
		return knzgDto;
	}

	public int addOrUpdateKnzg(KnzgEntity knzg) {
		return knzgDao.saveOrUpdateKnzg(knzg);
	}

}
