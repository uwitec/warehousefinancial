package com.wfms.common.system.service;

import org.springframework.stereotype.Service;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.dao.BaseService;
import com.wfms.common.system.entity.User;

@Service
public class UserService extends BaseService<User> {

	@Override
	public void setBaseDao(BaseDao<User> paramBaseDao) {
		// TODO Auto-generated method stub
		
	}
	
}
