package com.wfms.service.system.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.dao.system.IQxglDao;
import com.wfms.dto.system.QxDto;
import com.wfms.model.system.PartGenInfo;
import com.wfms.model.system.RightGenInfo;
import com.wfms.service.system.IQxglService;

@Service
public class QxglServiceImpl implements IQxglService {

	@Autowired
	private IQxglDao qxglDao;
	
	public void setQxglDao(IQxglDao qxglDao) {
		this.qxglDao = qxglDao;
	}
	
	public int addQx(RightGenInfo qx) {
		return qxglDao.addQx(qx);
	}

	public int deleteQx(int id) {
		RightGenInfo qx = qxglDao.getQxById(id);
		List jsqxList = qx.getPartRights();
		List qxxzList=qx.getMemberRights();
		if((jsqxList != null && jsqxList.size()>0) || (qxxzList != null && qxxzList.size() != 0)){
			return -1;
		}
		return qxglDao.deleteQx(id);
	}

	public QxDto getAllQx() {
		List<RightGenInfo> list  = qxglDao.getAllQx();
		QxDto dto = new QxDto();
		dto.setQxList(list);
		return dto;
	}

	public QxDto getQxById(int id) {
		RightGenInfo Qx = qxglDao.getQxById(id);
		QxDto dto = new QxDto();
		dto.setQx(Qx);
		return dto;
	}

	public int updateQx(List<RightGenInfo> qxList) {
		int i=0;
		for(;i<qxList.size();i++){
			if(qxList.get(i)!=null)
				qxglDao.updateQx(qxList.get(i));
		}
		return i;
	}
}
