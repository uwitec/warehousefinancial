package com.wfms.common.system.dao.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.system.dao.ICompanyDao;
import com.wfms.common.system.entity.Company;

@Repository
@Lazy(true)
public class CompanyDao extends BaseDao<Company> implements ICompanyDao {

}
