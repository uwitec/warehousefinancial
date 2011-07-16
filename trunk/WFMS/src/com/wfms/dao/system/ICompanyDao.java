package com.wfms.dao.system;

import java.util.List;

import com.wfms.model.system.Company;

public interface ICompanyDao {
	
	public Company getCompany(String companyId);

	public int saveCompany(Company company);
	
	public List<Company> getAllCompany();
}
