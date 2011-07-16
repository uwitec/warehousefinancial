package com.wfms.service.system.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.dao.system.IYhZwglDao;
import com.wfms.model.system.RoleMember;
import com.wfms.service.system.IYhZwglService;

@Service
public class YhZwglServiceImpl implements IYhZwglService {

	@Autowired
	private IYhZwglDao yhzwDao=null;
	
	public IYhZwglDao getYhzwDao() {
		return yhzwDao;
	}

	public void setYhzwDao(IYhZwglDao yhzwDao) {
		this.yhzwDao = yhzwDao;
	}

	public int addYhZw(RoleMember yhzw) {
		return yhzwDao.insertYhZw(yhzw);
	}

	public int delYhZw(RoleMember yhzw) {
		return yhzwDao.delYhZwByYhAndZw(yhzw);
	}


}
