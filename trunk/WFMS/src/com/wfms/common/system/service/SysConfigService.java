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
package com.wfms.common.system.service;


import org.springframework.stereotype.Service;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.dao.BaseService;
import com.wfms.common.system.entity.SysConfig;


@Service
public class SysConfigService extends BaseService<SysConfig> {

	@Override
	public void setBaseDao(BaseDao<SysConfig> paramBaseDao) {
		// TODO Auto-generated method stub
		
	}
	

}
