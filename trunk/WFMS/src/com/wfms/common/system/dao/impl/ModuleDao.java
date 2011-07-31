package com.wfms.common.system.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.system.dao.IModuleDao;
import com.wfms.common.system.entity.ModuleGenInfo;

@Repository
public class ModuleDao extends BaseDao<ModuleGenInfo> implements IModuleDao {

	@Override
	public List<ModuleGenInfo> getModuleByUser(String userID) {
		String sql = "select * from MODULE_GENINF where id in(select MODULE_ID from ROLE_MODULE where ROLE_ID in(select MEM_GENINF.role_id from MEM_ROLE inner join MEM_GENINF on MEM_ROLE.MEM_ID = MEM_GENINF.ID where mem_id=?) union select module_id from MEM_MODULE where mem_id=? and GRANTTYPE='1' except select module_id from MEM_MODULE where mem_id=? and GRANTTYPE='0')";
		return (List<ModuleGenInfo>) super.executeSQLQuery(sql, new String[]{userID,userID,userID},
				ModuleGenInfo.class);
	}

	@Override
	public List<ModuleGenInfo> getModuleByParent(String parentId) {
		return (List<ModuleGenInfo>) super.executeQuery(new String[]{"parentid=?"}, parentId);
	}
}
