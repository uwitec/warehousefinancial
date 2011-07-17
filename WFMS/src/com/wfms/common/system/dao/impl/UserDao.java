package com.wfms.common.system.dao.impl;


import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.system.dao.IUserDao;
import com.wfms.common.system.entity.User;

@Repository
public class UserDao extends BaseDao<User> implements IUserDao {

}
