package com.wfms.service.jxc.jcsz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.common.entity.Page;
import com.wfms.dao.jxc.jcsz.IJxcRyglDao;
import com.wfms.model.jxc.jcsz.JxcRyglEntity;
import com.wfms.service.jxc.jcsz.IJxcRyglService;

@Service
public class JxcRyglService implements IJxcRyglService {
	@Autowired
	private IJxcRyglDao JxcRyglDao;

	public Page getAllRygl(Page page) {
		if (page == null) {
			page = new Page();
		}
		page.setDataList(this.JxcRyglDao.getJxcRyglEntity(page));
		page.setTotalCount(JxcRyglDao.getJxcRyglEntityCount(page.getConditionList()));
		return page;
	}

	public int deleteRygl(int id) {
		JxcRyglEntity cjxx = this.JxcRyglDao.getJxcRyglEntityPk(id);
		if (cjxx == null) {
			return -1;
		}
		return this.JxcRyglDao.deleteRygl(id);
	}

	public int addRygl(JxcRyglEntity JxcRyglEntity) {
		if (JxcRyglEntity == null) {
			return -1;
		}
		return this.JxcRyglDao.addRygl(JxcRyglEntity);
	}

	public int updateRygl(List<JxcRyglEntity> RyglList) {
		return JxcRyglDao.updateRygl(RyglList);
	}
	
	public List<JxcRyglEntity> getAllRyglByJoin(String khlb){
		return this.JxcRyglDao.getAllRyglByJoin(khlb);
	}
}
