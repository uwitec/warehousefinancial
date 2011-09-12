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
package com.wfms.common.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.dao.BaseService;
import com.wfms.common.system.entity.Code;

@Service
public class CodeService extends BaseService<Code> {

	@Override
	@Autowired
	public void setBaseDao(@Qualifier("codeDao")
	BaseDao<Code> paramBaseDao) {
		this.baseDao = paramBaseDao;
	}

}
