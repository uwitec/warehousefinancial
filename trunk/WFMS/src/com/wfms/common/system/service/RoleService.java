package com.wfms.common.system.service;


import org.springframework.stereotype.Service;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.dao.BaseService;
import com.wfms.common.system.entity.RoleGenInfo;

@Service
public class RoleService extends BaseService<RoleGenInfo>{

	@Override
	public void setBaseDao(BaseDao<RoleGenInfo> paramBaseDao) {
		
	}

}
