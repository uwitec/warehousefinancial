package com.wfms.common.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.dao.BaseService;
import com.wfms.common.system.entity.User;

@Service
public class UserService extends BaseService<User> {

	@Override
	@Autowired
	public void setBaseDao(@Qualifier("userDao")
	BaseDao<User> paramBaseDao) {
		this.baseDao = paramBaseDao;
	}

}
