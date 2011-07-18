package com.wfms.common.system.dao.impl;


import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.system.dao.IUserModuleDao;
import com.wfms.common.system.entity.UserModule;

@Repository
public class UserModuleDao extends BaseDao<UserModule> implements
		IUserModuleDao {

}
