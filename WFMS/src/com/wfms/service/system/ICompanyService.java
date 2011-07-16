package com.wfms.service.system;

import java.util.List;

import com.wfms.model.system.Company;

public interface ICompanyService {

	public boolean addCompany(Company company);

	public Company loadCompany(String companyId);
	
	public List<Company> loadAllCompany();
}
