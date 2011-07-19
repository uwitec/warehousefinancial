package com.wfms.common.system.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.system.dao.IUserDao;
import com.wfms.common.system.entity.User;

@Repository
public class UserDao extends BaseDao<User> implements IUserDao {

	@Override
	public List<User> getLoginUser(String loginId, String loginPwd) {
		return (List<User>)super.executeQuery(new String[]{"loginid=?","loginpwd=?"}, new String[]{loginId,loginPwd});
	}

}
