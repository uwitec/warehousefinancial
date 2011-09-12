package com.wfms.common.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.dao.BaseService;
import com.wfms.common.system.entity.UserRole;

@Service
public class UserRoleService extends BaseService<UserRole> {

	@Override
	@Autowired
	public void setBaseDao(@Qualifier("userRoleDao")
	BaseDao<UserRole> paramBaseDao) {
		this.baseDao = paramBaseDao;
	}

}

