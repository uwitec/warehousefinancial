package com.wfms.common.system.dao.impl;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.system.dao.IModuleDao;
import com.wfms.common.system.entity.ModuleGenInfo;

@Repository
public class ModuleDao extends BaseDao<ModuleGenInfo> implements IModuleDao {
}
