package com.wfms.service.rsgl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.dao.rsgl.IBmglDao;
import com.wfms.dto.rsgl.BmDto;
import com.wfms.model.system.DepartGenInfo;
import com.wfms.service.rsgl.IBmglService;

@Service
public class BmglServiceImpl implements IBmglService {

	@Autowired
	private IBmglDao bmglDao;

	public void setBmglDao(IBmglDao bmglDao) {
		this.bmglDao = bmglDao;
	}

	public int addBm(DepartGenInfo bm) {
		return bmglDao.addBm(bm);
	}

	public int deleteBm(String depid) {
		DepartGenInfo bm = bmglDao.getBmById(depid);
		List roleList = bm.getRoles();
		if (roleList != null && roleList.size() != 0) {
			return -1;
		}
		return bmglDao.deleteBm(depid);
	}

	public BmDto getAllBm() {
		List<DepartGenInfo> list = bmglDao.getAllBm();
		BmDto dto = new BmDto();
		dto.setBmList(list);
		return dto;
	}

	public BmDto getBmById(String depid) {
		DepartGenInfo bm = bmglDao.getBmById(depid);
		BmDto dto = new BmDto();
		dto.setBm(bm);
		return dto;
	}

	public int updateBm(List<DepartGenInfo> bmList) {
		int i = 0;
		for (; i < bmList.size(); i++) {
			bmglDao.updateBm(bmList.get(i));
		}
		return i;
	}
}
