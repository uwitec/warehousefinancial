package com.wfms.dao.zcgl.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.entity.Page;
import com.wfms.dao.zcgl.IZcrkDao;
import com.wfms.model.zcgl.CkzcEntity;
import com.wfms.model.zcgl.RkdEntity;
import com.wfms.model.zcgl.RkdmxEntity;
@Repository
public class ZcrkDaoImpl extends BaseDao implements IZcrkDao{

	public List<RkdmxEntity> getRkdmxList(Page page) {
		return super.executeQuery(RkdmxEntity.class, page);
	}

	public int getRkdmxTotalCount(Page page) {
		return super.getTotalCount(RkdmxEntity.class, page.getConditionList());
	}

	public int getZcrkdTotalCount(Page page) {
		return super.getTotalCount(RkdEntity.class, page.getConditionList());
	}

	public List<RkdEntity> getZcrkdxList(Page page) {
		List<RkdEntity> l = super.executeQuery(RkdEntity.class, page);
		List<RkdEntity> le = new ArrayList<RkdEntity>();
		for(RkdEntity r : l){
			r.setZt(r.getZt().equals("1") ? "费用已清" : "费用未清");
			le.add(r);
		}
		return le;
	}

	public int addRkd(RkdEntity entity) {
		return super.add(entity);
	}

	public int deleteRkd(int id) {
		return super.delete(RkdEntity.class, id);
	}

	public List<RkdmxEntity> getRkdmxByZcId(int zcId) {
		String hql = " FROM RkdmxEntity where 1=1 and zcxx.id='"+zcId+"'";
		return (List<RkdmxEntity>)super.getList(hql);
	}

	public int updateRkdmx(RkdmxEntity rkdmx) {
		return super.update(rkdmx);
	}

	public List<RkdmxEntity> getRkdmxByZclx(String column, String zclx) {
		String hql = "FROM RkdmxEntity where 1=1 ";
		if (column != null && !"".equals(column) && zclx != null && !"".equals(zclx)) {
			hql += " and "+column+"='"+zclx+"' and rksl>'0' ";
		}
		return (List<RkdmxEntity> )super.getList(hql);
	}
	
	public List<RkdmxEntity> getRkdmxListByRkdId(int rkdId){
		String hql = "FROM RkdmxEntity where 1=1 and rkd.id='"+rkdId+"'";
		return (List<RkdmxEntity>)super.getList(hql);
	}

	public int deleteRkdmx(RkdmxEntity rkdmx) {
		return super.delete(rkdmx);
	}

	public int addCkzc(CkzcEntity ckzc) {
		return super.add(ckzc);
	}

	public CkzcEntity getCkzc(int ckId, int zclxId, int zcId) {
		String hql = " FROM CkzcEntity where ckxx.id='"+ckId+"' and zclx.id='"+zclxId+"' and zcxx.id='"+zcId+"' ";
		return (CkzcEntity)super.getEntityByConditions(hql);
	}

	public int updateCkzc(CkzcEntity ckzc) {
		return super.update(ckzc);
	}

	
	public List<CkzcEntity> getCkzcList(Page page) {
		return super.executeQuery(CkzcEntity.class, page);
	}

	public int getCkzcTotalCount(Page page) {
		return super.getTotalCount(CkzcEntity.class, page.getConditionList());
	}
}
