package com.wfms.service.system.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.dao.system.IZwJsglDao;
import com.wfms.model.system.RolePart;
import com.wfms.service.system.IZwJsglService;

@Service
public class ZwJsglServiceImpl implements IZwJsglService {

	@Autowired
	private IZwJsglDao zwjsDao;

	public IZwJsglDao getZwjsDao() {
		return zwjsDao;
	}

	public void setZwjsDao(IZwJsglDao zwjsDao) {
		this.zwjsDao = zwjsDao;
	}

	public int addZwJs(RolePart zwjs) {
		return zwjsDao.insertZwJs(zwjs);
	}

	public int delZwJs(int jsId,String zwId) {
		return zwjsDao.delZwJsByYhAndZw(jsId, zwId);
	}
	
	


}
