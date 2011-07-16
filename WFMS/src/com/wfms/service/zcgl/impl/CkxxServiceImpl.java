package com.wfms.service.zcgl.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.common.entity.Page;
import com.wfms.dao.zcgl.ICkxxDao;
import com.wfms.model.zcgl.CkxxEntity;
import com.wfms.model.zcgl.CkzcEntity;
import com.wfms.model.zcgl.ZclxEntity;
import com.wfms.service.zcgl.ICkxxService;

@Service
public class CkxxServiceImpl implements ICkxxService{

	@Autowired
	private ICkxxDao ckxxDao;
	
	public int addCkxx(CkxxEntity ckxx,List<CkzcEntity> list) {
		if (list != null && !"".equals(list)) {
			for (int i = 0; i < list.size(); i++) {
				ckxx.addCkzc(list.get(i));
			}
		}
		return ckxxDao.addCkxx(ckxx);
	}

	public int deleteCkxx(int id) {
		return ckxxDao.deleteCkxx(id);
	}

	public Page getCkxxByPage(Page page) {
		if (page == null) {
			page  = new Page();
		}
		page.setDataList(ckxxDao.getCkxxList(page));
		page.setTotalCount(ckxxDao.getCkxxTotalCount(page));
		return page;
	}

	public int updateCkxx(List<CkxxEntity> ckxxList) {
		return ckxxDao.updateCkxx(ckxxList);
	}

	public int updateCkxx(CkxxEntity ckxx) {
		return ckxxDao.updateCkxx(ckxx);
	}

	public List<CkxxEntity> getCkxxList(String userId) {
		return ckxxDao.getCkxxList(userId);
	}

	public List<ZclxEntity> getZclxListByCkId(String ckId) {
		List<CkzcEntity> ckzcList = ckxxDao.getCkzcListByCkId(ckId);
		List<ZclxEntity> zclxlList = new ArrayList<ZclxEntity>();
		if (ckzcList != null && !"".equals(ckzcList)) {
			for (int i = 0; i < ckzcList.size(); i++) {
				zclxlList.add(ckzcList.get(i).getZclx());
			}
		}
		return zclxlList;
	}
}
