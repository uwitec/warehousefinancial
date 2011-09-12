package com.wfms.common.system.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.dao.BaseService;
import com.wfms.common.system.entity.SysConfig;


@Service
public class SysConfigService extends BaseService<SysConfig> {

	@Override
	@Autowired
	public void setBaseDao(@Qualifier("sysConfigDao")BaseDao<SysConfig> paramBaseDao) {
		this.baseDao = paramBaseDao;
	}
	

}
