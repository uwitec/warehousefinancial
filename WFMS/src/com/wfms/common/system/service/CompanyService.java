package com.wfms.common.system.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.dao.BaseService;
import com.wfms.common.system.entity.Company;

@Service
@Lazy(true)
public class CompanyService extends BaseService<Company>{

	@Override
	public void setBaseDao(BaseDao<Company> paramBaseDao) {
		
	}

}
