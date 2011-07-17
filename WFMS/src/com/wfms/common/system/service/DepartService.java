package com.wfms.common.system.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.dao.BaseService;
import com.wfms.common.system.entity.DepartGenInfo;

@Service
@Lazy(true)
public class DepartService extends BaseService<DepartGenInfo> {

	@Override
	public void setBaseDao(BaseDao<DepartGenInfo> paramBaseDao) {
		
	}

}
