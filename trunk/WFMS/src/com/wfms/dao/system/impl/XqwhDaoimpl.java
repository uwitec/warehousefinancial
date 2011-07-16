package com.wfms.dao.system.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.dao.system.IXqwhDao;
import com.wfms.model.system.XqwhEntity;
@Repository
public class XqwhDaoimpl extends BaseDao implements IXqwhDao{

	public int addXqwh(XqwhEntity xqwhEntity) {
		return super.add(xqwhEntity);
	}

	public int deleteXqwh(int id) {
		return super.delete(XqwhEntity.class, id);
	}

	public List<XqwhEntity> getXqwhEntity() {
		return super.findAll(XqwhEntity.class);
	}

	public List<XqwhEntity> getXqwhEntity(Page page) {
		return super.executeQuery(XqwhEntity.class, page);
	}

	public int getXqwhEntityCount(List<ConditionBean> list) {
		return super.getTotalCount(XqwhEntity.class, list);
	}

	public int updateXqwh(List<XqwhEntity> xqwhEntity) {
		return super.batchUpdate(xqwhEntity,15);
	}

}
