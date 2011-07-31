package com.wfms.common.system.service;

import java.util.List;

import org.logicalcobwebs.concurrent.FJTask.Par;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.dao.BaseService;
import com.wfms.common.system.dao.IModuleDao;
import com.wfms.common.system.entity.ModuleGenInfo;

@Service
public class ModuleService extends BaseService<ModuleGenInfo> {

	@Override
	@Autowired
	public void setBaseDao(@Qualifier("moduleDao")
	BaseDao<ModuleGenInfo> paramBaseDao) {
		this.baseDao = paramBaseDao;
	}

	public List<ModuleGenInfo> loadUserModules(String userId) {
		return ((IModuleDao) baseDao).getModuleByUser(userId);
	}
	
	public List<ModuleGenInfo> loadSubModules(String moduleId)
	{
		return ((IModuleDao)baseDao).getModuleByParent(moduleId);
	}
}
