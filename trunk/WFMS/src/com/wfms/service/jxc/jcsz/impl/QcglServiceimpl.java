package com.wfms.service.jxc.jcsz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.dao.jxc.jcsz.IQcglDao;
import com.wfms.model.jxc.jcsz.QCglEntity;
import com.wfms.service.jxc.jcsz.IQcglService;
@Service
public class QcglServiceimpl implements IQcglService {
	@Autowired 
	private IQcglDao iQcglDao;
	public int addQcgl(QCglEntity QCglEntity) {
		return iQcglDao.addQcgl(QCglEntity);
	}

	public int deleteQcgl(int id) {
		return iQcglDao.deleteQcgl(id);
	}

	public List<QCglEntity> getQCglEntity() {
		return iQcglDao.getQCglEntity();
	}

	public Page getQCglEntity(Page page) {
		if(page==null){
			page=new Page();
		}
		page.setDataList(iQcglDao.getQCglEntity(page));
		page.setTotalCount(iQcglDao.getQCglEntityCount(page.getConditionList()));
		return page;
	}

	public int getQCglEntityCount(List<ConditionBean> list) {
		return iQcglDao.getQCglEntityCount(list);
	}

	public int updateQcgl(List<QCglEntity> QCglEntity) {
		return iQcglDao.updateQcgl(QCglEntity);
	}

}
