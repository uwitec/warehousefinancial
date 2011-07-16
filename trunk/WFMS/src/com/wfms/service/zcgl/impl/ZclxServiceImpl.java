package com.wfms.service.zcgl.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.common.entity.Page;
import com.wfms.dao.zcgl.IZclxDao;
import com.wfms.model.zcgl.ZclxEntity;
import com.wfms.service.zcgl.IZclxService;

@Service
public class ZclxServiceImpl implements IZclxService{

	@Autowired
	private IZclxDao zclxDao;
	
	public int addZclx(ZclxEntity zclxEntity) {
		return zclxDao.addZclx(zclxEntity);
	}

	public int deleteZclx(int id) {
		return zclxDao.deleteZclx(id);
	}

	public Page getZclxByPage(Page page) {
		if (page == null) {
			page = new Page();
		}
		page.setDataList(zclxDao.getZclxList(page));
		page.setTotalCount(zclxDao.getZclxTotalCount(page));
		return page;
	}

	public int updateZclx(List<ZclxEntity> zclxList) {
		return zclxDao.updateZclx(zclxList);
	}

	public List<ZclxEntity> getZclxList() {
		return zclxDao.getZclxList();
	}

}
