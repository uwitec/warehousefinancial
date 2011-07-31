package com.wfms.common.system.dao;

import java.util.List;

import com.wfms.common.dao.GeneralDao;
import com.wfms.common.system.entity.ModuleGenInfo;

public interface IModuleDao extends GeneralDao<ModuleGenInfo> {
	
	/**
	 * 查询用户所有的功能模块
	 * @param userID
	 * @return
	 */
	public List<ModuleGenInfo> getModuleByUser(String userID);
	
	/**
	 * 根据父级模块查询下一级模块
	 * @param parentId
	 * @return
	 */
	public List<ModuleGenInfo> getModuleByParent(String parentId);
}
