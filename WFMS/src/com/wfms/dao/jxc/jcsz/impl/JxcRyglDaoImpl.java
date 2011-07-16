package com.wfms.dao.jxc.jcsz.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.dao.jxc.jcsz.IJxcRyglDao;
import com.wfms.model.jxc.jcsz.JxcRyglEntity;

@Repository
public class JxcRyglDaoImpl extends BaseDao implements IJxcRyglDao {
	public List<JxcRyglEntity> getAllRygl() {
		return super.findAll(JxcRyglEntity.class);
	}
	public List<JxcRyglEntity> getJxcRyglEntity(Page page) {
		return super.executeQuery(JxcRyglEntity.class, page);
	}
	public int getJxcRyglEntityCount(List<ConditionBean> list) {
		return super.getTotalCount(JxcRyglEntity.class, list);
	}
	public int deleteRygl(int id) {
		return super.delete(JxcRyglEntity.class, id);
	}
	public JxcRyglEntity getJxcRyglEntityPk(int id) {
		return (JxcRyglEntity)super.getByPk(JxcRyglEntity.class, id);
	}
	public int addRygl(JxcRyglEntity JxcRyglEntity) {
		return super.add(JxcRyglEntity);
	}
	public int updateRygl(List<JxcRyglEntity> RyglList) {
		return super.batchUpdate(RyglList,15);
	}
	public List<JxcRyglEntity> getAllRyglByJoin(String khlb) {
		String hql = " From JxcRyglEntity where khlb=?";
		return super.executeQuery(hql, khlb);
	}
}
