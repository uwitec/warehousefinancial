package com.wfms.dao.zcgl.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.entity.Page;
import com.wfms.dao.zcgl.IZclxDao;
import com.wfms.model.zcgl.ZclxEntity;

@Repository
public class ZclxDaoImpl extends BaseDao implements IZclxDao{

	public int addZclx(ZclxEntity zclx) {
		return super.add(zclx);
	}

	public int deleteZclx(int id) {
		return super.delete(ZclxEntity.class, id);
	}

	public List<ZclxEntity> getZclxList(Page page) {
		return super.executeQuery(ZclxEntity.class, page);
	}

	public int getZclxTotalCount(Page page) {
		return super.getTotalCount(ZclxEntity.class, page.getConditionList());
	}

	public int updateZclx(List<ZclxEntity> zclxList) {
		return super.batchUpdate(zclxList, 15);
	}

	public List<ZclxEntity> getZclxList() {
		return (List<ZclxEntity>)super.getList(ZclxEntity.class);
	}

	public ZclxEntity getZclxById(int zclxId) {
		return (ZclxEntity)super.getByPk(ZclxEntity.class, zclxId);
	}

}
