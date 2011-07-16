package com.wfms.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.wfms.dao.system.IDepartDao;
import com.wfms.model.system.DepartGenInfo;
import com.wfms.service.system.IDepartService;

@Service
@Lazy(true)
public class DepartService implements IDepartService {

	@Autowired
	private IDepartDao departDao;
	
	public String addDepartment(DepartGenInfo department) {
		return departDao.saveDepartment(department);
	}

	public int deleteDepartment(String departmentId) {
		return departDao.deleteDepartment(departmentId);
	}

	public List<DepartGenInfo> loadAllDepartment() {
		List<DepartGenInfo> departmentList = departDao.getAllDepartment();
		return departmentList;
	}

	public DepartGenInfo loadDepartment(String departmentId) {
		return departDao.getDepartment(departmentId);
	}

	public int UpdateDepartment(List<DepartGenInfo> department){
		return departDao.UpdateDepartment(department);
	}
}
