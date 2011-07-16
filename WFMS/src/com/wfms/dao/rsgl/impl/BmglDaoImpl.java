package com.wfms.dao.rsgl.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.entity.Page;
import com.wfms.dao.rsgl.IBmglDao;
import com.wfms.model.system.DepartGenInfo;

@Repository
public class BmglDaoImpl extends BaseDao implements IBmglDao {

	public int addBm(DepartGenInfo depart) {
		return super.add(depart);
	}

	public int deleteBm(String depid) {
		return super.delete(DepartGenInfo.class, depid);
	}

	public List<DepartGenInfo> getAllBm() {
		return super.executeQuery(DepartGenInfo.class);
	}

	public DepartGenInfo getBmById(String id) {
		return (DepartGenInfo)super.getByPk(DepartGenInfo.class, id);
	}

	public int updateBm(DepartGenInfo bm) {
		return super.update(bm);
	}

	public List<DepartGenInfo> getBmByPage(Page page) {
		return super.executeQuery(DepartGenInfo.class, page);
	}

}
