package com.wfms.common.system.dao;

import java.util.List;

import com.wfms.common.dao.GeneralDao;
import com.wfms.common.system.entity.User;


public interface IUserDao extends GeneralDao<User>{
	
	public List<User> getLoginUser(String loginId,String loginPwd);
}
