package com.wfms.common.system.dao;

import com.wfms.common.dao.GeneralDao;
import com.wfms.common.system.entity.Code;

public interface ICodeDao extends GeneralDao<Code> {
	public Code getLxdmBySuperDmAndDmmc(String str, String str1);
	
	public String getDmByCkbCkzd(String ckb,String column,String nbckzd,String ckzd);
}
