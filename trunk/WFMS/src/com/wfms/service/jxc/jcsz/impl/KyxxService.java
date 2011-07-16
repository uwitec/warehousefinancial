package com.wfms.service.jxc.jcsz.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.common.entity.Page;
import com.wfms.dao.jxc.jcsz.IKyxxDao;
import com.wfms.model.jxc.jcsz.KyxxEntity;
import com.wfms.service.jxc.jcsz.IKyxxService;

@Service
public class KyxxService implements IKyxxService {
	@Autowired
	private IKyxxDao KyxxDao;

	public Page getAllKyxx(Page page) {
		if (page == null) {
			page = new Page();
		}
		page.setDataList(this.KyxxDao.getKyxxEntity(page));
		page.setTotalCount(KyxxDao.getKyxxEntityCount(page.getConditionList()));
		return page;
	}

	/**
	 * <dl>
	 * <b>方法名:deleteKyxx</b>
	 * <dd>方法作用：删除信息
	 */
	public int deleteKyxx(int id) {
		KyxxEntity cjxx = this.KyxxDao.getKyxxEntityPk(id);
		if (cjxx == null) {
			return -1;
		}
		return this.KyxxDao.deleteKyxx(id);
	}

	/**
	 * 添加基地信息
	 */
	public int addKyxx(KyxxEntity KyxxEntity) {
		if (KyxxEntity == null) {
			return -1;
		}
		return this.KyxxDao.addKyxx(KyxxEntity);
	}

	/**
	 * 修改基地信息
	 */
	public int updateKyxx(List<KyxxEntity> KyxxList) {
		return KyxxDao.updateKyxx(KyxxList);
	}
	
	public List<KyxxEntity> getAllKyxxByJoin(String khlb){
		return this.KyxxDao.getAllKyxxByJoin(khlb);
	}
}
