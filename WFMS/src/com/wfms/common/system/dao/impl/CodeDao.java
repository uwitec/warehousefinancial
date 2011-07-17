/**
 *******************************************************************************
 * 文件名：LxdmglDaoImpl.java
 *
 * 描述�?
 * 
 * 创建日期：Jan 19, 2010 1:31:53 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.common.system.dao.impl;


import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.system.dao.ICodeDao;
import com.wfms.common.system.entity.Code;

@Repository
public class CodeDao extends BaseDao<Code> implements ICodeDao {

	@Override
	public Code getLxdmBySuperDmAndDmmc(String str, String str1) {
		return null;
	}

	@Override
	public String getDmByCkbCkzd(String ckb,String column,String nbckzd, String ckzd) {
		return null;
	}

}
