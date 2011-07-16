package com.wfms.service.jxc.fywh.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.dao.jxc.fywh.IFywhDao;
import com.wfms.model.jxc.fygl.FywhEntity;
import com.wfms.service.jxc.fywh.IFywhService;

@Service
public class FywhServiceimpl implements IFywhService {
	@Autowired 
	private IFywhDao iFywhDao;
	public int addFywh(FywhEntity FywhEntity) {
		return iFywhDao.addFywh(FywhEntity);
	}

	public int deleteFywh(int id) {
		return iFywhDao.deleteFywh(id);
	}

	public List<FywhEntity> getFywhEntity() {
		return iFywhDao.getFywhEntity();
	}

	public Page getFywhEntity(Page page) {
		if(page==null){
			page=new Page();
		}
		page.setDataList(iFywhDao.getFywhEntity(page));
		page.setTotalCount(iFywhDao.getFywhEntityCount(page.getConditionList()));
		return page;
	}

	public int getFywhEntityCount(List<ConditionBean> list) {
		return iFywhDao.getFywhEntityCount(list);
	}

	public int updateFywh(List<FywhEntity> FywhEntity) {
		return iFywhDao.updateFywh(FywhEntity);
	}
	
	public List<FywhEntity> getAllXfz(String type){
		return iFywhDao.getAllXfz(type);
	}
}
