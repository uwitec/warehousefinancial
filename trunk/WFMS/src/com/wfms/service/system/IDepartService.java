package com.wfms.service.system;

import java.util.List;

import com.wfms.model.system.DepartGenInfo;

public interface IDepartService {

public String addDepartment(DepartGenInfo department);
	
	public DepartGenInfo loadDepartment(String departmentId);
	
	public List<DepartGenInfo> loadAllDepartment();
	
	public int deleteDepartment(String departmentId);
	
	public int UpdateDepartment(List<DepartGenInfo> department);
}
