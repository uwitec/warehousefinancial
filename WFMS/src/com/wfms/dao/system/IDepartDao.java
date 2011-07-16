package com.wfms.dao.system;

import java.util.List;

import com.wfms.model.system.DepartGenInfo;

public interface IDepartDao {

public DepartGenInfo getDepartment(String departmentId);
	
	public List<DepartGenInfo> getAllDepartment();
	
	public String saveDepartment(DepartGenInfo department);
	
	public int deleteDepartment(String departmentId);
	
	public DepartGenInfo getDepartById(String id);
	
	public int UpdateDepartment(List<DepartGenInfo> department);
}
