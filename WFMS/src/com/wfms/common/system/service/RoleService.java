package com.wfms.common.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.dao.BaseService;
import com.wfms.common.system.entity.RoleGenInfo;

@Service
public class RoleService extends BaseService<RoleGenInfo> {

	@Override
	@Autowired
	public void setBaseDao(@Qualifier("roleDao")
	BaseDao<RoleGenInfo> paramBaseDao) {
		this.baseDao = paramBaseDao;
	}

}
