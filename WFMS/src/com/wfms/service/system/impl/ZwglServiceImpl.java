package com.wfms.service.system.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.dao.system.IZwglDao;
import com.wfms.dto.system.ZwDto;
import com.wfms.model.system.RoleGenInfo;
import com.wfms.service.system.IZwglService;

@Service
public class ZwglServiceImpl implements IZwglService {

	@Autowired
	private IZwglDao zwglDao;
	public void setZwglDao(IZwglDao zwglDao) {
		this.zwglDao = zwglDao;
	}

	public int delZw(String id) {
		int res=0;
		RoleGenInfo zw=zwglDao.getZwById(id);
		//该职务存在用户信�?
		if(zw.getMembers()!=null && zw.getMembers().size()>0)
		{
			res = -1;
		}
		//该职务下存在角色信息
		else if(zw.getRoleParts()!=null && zw.getRoleParts().size()>0)
		{
			res = -2;
		}
		else
		{
			res=zwglDao.delZw(id);
		}
		return res;
	}

	public ZwDto getAllZw() {
		ZwDto dto = new ZwDto();
		dto.setZwList(zwglDao.getAllZw());
		return dto;
	}

	public ZwDto getZwById(String id) {
		ZwDto dto = new ZwDto();
		dto.setZw(zwglDao.getZwById(id));
		return dto;
	}

	public int updZw(List<RoleGenInfo> zwList) {
		/*int row = 0;
		for(int i=0;i<zwList.size();i++){
			row+=zwglDao.updZw(zwList.get(i));
		}
		return row;*/
		int fetchCount = 0;
		fetchCount = zwglDao.updateRoleList(zwList);
		return fetchCount;
	}

	public String addZw(RoleGenInfo zw) {
		return zwglDao.insertZw(zw);
	}

	public List<RoleGenInfo> getZwForYhNot(String yhId) {
		return zwglDao.getZwForYhNot(yhId);
	}

	public List<RoleGenInfo> loadRoleByDepart(String depid) {
		return zwglDao.getRolesByDepart(depid);
	}

}
