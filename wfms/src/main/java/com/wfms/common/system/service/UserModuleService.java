package com.wfms.common.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.dao.BaseService;
import com.wfms.common.system.entity.UserModule;

@Service
public class UserModuleService extends BaseService<UserModule> {

	@Override
	@Autowired
	public void setBaseDao(@Qualifier("moduleDa")
	BaseDao<UserModule> paramBaseDao) {
		this.baseDao = paramBaseDao;
	}

}
