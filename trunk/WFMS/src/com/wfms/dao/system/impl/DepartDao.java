package com.wfms.dao.system.impl;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.dao.system.IDepartDao;
import com.wfms.model.system.DepartGenInfo;



@Repository
@Lazy(true)
public class DepartDao extends BaseDao implements IDepartDao {

	public int deleteDepartment(String departmentId) {
		return super.delete(DepartGenInfo.class, departmentId);
	}

	public List<DepartGenInfo> getAllDepartment() {
		return super.executeQuery(DepartGenInfo.class);
	}

	public DepartGenInfo getDepartment(String departmentId) {
		return (DepartGenInfo)super.getByPk(DepartGenInfo.class, departmentId);
	}

	public String saveDepartment(DepartGenInfo department) {
		return (String)super.insert(department);
	}

	public DepartGenInfo getDepartById(String id) {
		return (DepartGenInfo)super.getByPk(DepartGenInfo.class, id);
	}
	
	public int UpdateDepartment(List<DepartGenInfo> department){
		return super.batchUpdate(department, department.size());
	}
}
