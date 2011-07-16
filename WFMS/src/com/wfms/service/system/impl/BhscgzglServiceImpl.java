/**
 *******************************************************************************
 * 文件名：BhscgzglService.java
 *
 * 描述�?
 * 
 * 创建日期：Jan 20, 2010 8:21:07 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.service.system.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.dao.system.IBhscgzglDao;
import com.wfms.dto.system.BhscgzDto;
import com.wfms.model.system.BhscgzszEntity;
import com.wfms.service.system.IBhscgzglService;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：BhscgzglService
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 20, 2010 8:21:07 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see BhscgzglServiceImpl
 * @version 1.0
 *
 */
@Service
public class BhscgzglServiceImpl implements IBhscgzglService {

	@Autowired
	private IBhscgzglDao bhscgzglDao=null;

	public void setBhscgzglDao(IBhscgzglDao bhscgzglDao) {
		this.bhscgzglDao = bhscgzglDao;
	}

	public int deleteBhscgz(int bhscgzId) {
		return bhscgzglDao.deleteBhscgz(bhscgzId);
	}

	public BhscgzDto loadBhscgzByGzmc(String gzmc) {
		BhscgzDto dto = new BhscgzDto();
		dto.setBhscgzList(bhscgzglDao.getBhscgzByGzmc(gzmc));
		return dto;
	}

	public int updateBhscgz(List<BhscgzszEntity> list) {
		int row = 0;
		List<BhscgzszEntity> addList = new ArrayList<BhscgzszEntity>();
		List<BhscgzszEntity> updList = new ArrayList<BhscgzszEntity>();;
		for(BhscgzszEntity entity : list){
			if(entity.getId() == 0){
				addList.add(entity);
			}else{
				updList.add(entity);
			}
		}
		if(addList != null && addList.size() != 0){
			for(BhscgzszEntity entity : addList){
				row += bhscgzglDao.insertBhscgz(entity);
			}
		}
		if(updList != null && updList.size() != 0){
			for(BhscgzszEntity entity : updList){
				row += bhscgzglDao.updateBhscgz(entity);
			}
		}
		return row;
	}

}
