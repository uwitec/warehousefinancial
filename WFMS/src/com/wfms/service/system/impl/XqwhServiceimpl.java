package com.wfms.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.dao.system.IXqwhDao;
import com.wfms.model.system.XqwhEntity;
import com.wfms.service.system.IXqwhService;
@Service
public class XqwhServiceimpl implements IXqwhService {
	@Autowired 
	private IXqwhDao iXqwhDao;
	public int addXqwh(XqwhEntity xqwhEntity) {
		return iXqwhDao.addXqwh(xqwhEntity);
	}

	public int deleteXqwh(int id) {
		return iXqwhDao.deleteXqwh(id);
	}

	public List<XqwhEntity> getXqwhEntity() {
		return iXqwhDao.getXqwhEntity();
	}

	public Page getXqwhEntity(Page page) {
		if(page==null){
			page=new Page();
		}
		page.setDataList(iXqwhDao.getXqwhEntity(page));
		page.setTotalCount(iXqwhDao.getXqwhEntityCount(page.getConditionList()));
		return page;
	}

	public int getXqwhEntityCount(List<ConditionBean> list) {
		return iXqwhDao.getXqwhEntityCount(list);
	}

	public int updateXqwh(List<XqwhEntity> xqwhEntity) {
		return iXqwhDao.updateXqwh(xqwhEntity);
	}

}
