package com.wfms.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.wfms.dao.system.ICompanyDao;
import com.wfms.model.system.Company;
import com.wfms.service.system.ICompanyService;

@Service
@Lazy(true)
public class CompanyService implements ICompanyService {

	@Autowired
	private ICompanyDao companyDao;

	public boolean addCompany(Company company) {
		companyDao.saveCompany(company);
		return true;
	}

	public Company loadCompany(String companyId) {
		return companyDao.getCompany(companyId);
	}

	public List<Company> loadAllCompany() {
		return companyDao.getAllCompany();
	}

}
