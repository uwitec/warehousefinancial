package com.wfms.common.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.dao.BaseService;
import com.wfms.common.system.dao.IUserDao;
import com.wfms.common.system.entity.User;

@Service
public class UserService extends BaseService<User> {

	@Override
	@Autowired
	public void setBaseDao(@Qualifier("userDao") BaseDao<User> paramBaseDao) {
		this.baseDao = paramBaseDao;
	}

	public List<User> loadLoginUser(String loginId, String loginPwd) {
		return ((IUserDao) baseDao).getLoginUser(loginId, loginPwd);
	}

	public boolean updateUserLoginStatus(String userId, String loginStatus) {
		((IUserDao) baseDao).updateUserLoginStatus(userId, loginStatus);
		return true;
	}

	public boolean updateAllUserLoginStatus(String status) {
		((IUserDao) baseDao).updateAllUserLoginStatus(status);
		return true;
	}
}
