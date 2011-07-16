package com.wfms.dao.jxc.jcsz.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.dao.jxc.jcsz.IQcglDao;
import com.wfms.model.jxc.jcsz.QCglEntity;
@Repository
public class QcglDaoimpl extends BaseDao implements IQcglDao{

	public int addQcgl(QCglEntity QCglEntity) {
		return super.add(QCglEntity);
	}

	public int deleteQcgl(int id) {
		return super.delete(QCglEntity.class, id);
	}

	public List<QCglEntity> getQCglEntity() {
		return super.findAll(QCglEntity.class);
	}

	public List<QCglEntity> getQCglEntity(Page page) {
		return super.executeQuery(QCglEntity.class, page);
	}

	public int getQCglEntityCount(List<ConditionBean> list) {
		return super.getTotalCount(QCglEntity.class, list);
	}

	public int updateQcgl(List<QCglEntity> QCglEntity) {
		return super.batchUpdate(QCglEntity,15);
	}

}
