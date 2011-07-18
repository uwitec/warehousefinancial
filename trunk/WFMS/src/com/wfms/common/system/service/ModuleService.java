package com.wfms.common.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.dao.BaseService;
import com.wfms.common.system.entity.ModuleGenInfo;

@Service
public class ModuleService extends BaseService<ModuleGenInfo> {

	@Override
	@Autowired
	public void setBaseDao(@Qualifier("moduleDao")
	BaseDao<ModuleGenInfo> paramBaseDao) {
		this.baseDao = paramBaseDao;
	}

}
