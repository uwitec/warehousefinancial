package com.wfms.common.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.system.dao.IUserDao;
import com.wfms.common.system.entity.User;

@Repository
public class UserDao extends BaseDao<User> implements IUserDao {

	public List<User> getLoginUser(String loginId, String loginPwd) {
		return (List<User>) super.executeQuery(new String[] { "loginid=?",
				"loginpwd=?" }, new String[] { loginId, loginPwd });
	}

	public void updateUserLoginStatus(String userId, String status) {
		User user = super.fetch(userId);
		user.setLoginStatus(status);
		super.update(user);
	}

	public void updateAllUserLoginStatus(String status) {
		String hql = "update " + User.class.getName()
				+ " set loginStatus=? where loginStatus<>?";
		executeHQLUpdate(hql, new String[] { status,
				"1".equals(status) ? "0" : "1" });
	}

}
