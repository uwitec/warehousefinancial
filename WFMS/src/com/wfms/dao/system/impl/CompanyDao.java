package com.wfms.dao.system.impl;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.dao.system.ICompanyDao;
import com.wfms.model.system.Company;



@Repository
@Lazy(true)
public class CompanyDao extends BaseDao implements ICompanyDao {

	public Company getCompany(String companyId) {
		return (Company)super.getByPk(Company.class,companyId);
	}

	public int saveCompany(Company company) {
		return super.add(company);
	}

	public List<Company> getAllCompany() {
		return super.executeQuery(Company.class);
	}

}
