package com.wfms.common.system.dao.impl;


import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.system.dao.ICodeDao;
import com.wfms.common.system.entity.Code;

@Repository
public class CodeDao extends BaseDao<Code> implements ICodeDao {

	public Code getLxdmBySuperDmAndDmmc(String str, String str1) {
		return null;
	}

	public String getDmByCkbCkzd(String ckb,String column,String nbckzd, String ckzd) {
		return null;
	}

}
