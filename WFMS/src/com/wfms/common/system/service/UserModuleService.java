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
package com.wfms.common.system.service;

import org.springframework.stereotype.Service;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.dao.BaseService;
import com.wfms.common.system.entity.UserModule;

@Service
public class UserModuleService extends BaseService<UserModule> {

	@Override
	public void setBaseDao(BaseDao<UserModule> paramBaseDao) {
		// TODO Auto-generated method stub

	}

}
