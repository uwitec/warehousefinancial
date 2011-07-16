package com.wfms.dao.zcgl.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.entity.Page;
import com.wfms.dao.zcgl.IZcckDao;
import com.wfms.model.zcgl.CkdEntity;
import com.wfms.model.zcgl.CkdmxEntity;
@Repository
public class ZcckDaoImpl extends BaseDao implements IZcckDao{

	public List<CkdmxEntity> getCkdmxList(Page page) {
		return super.executeQuery(CkdmxEntity.class, page);
	}

	public int getCkdmxTotalCount(Page page) {
		return super.getTotalCount(CkdmxEntity.class, page.getConditionList());
	}

	public int getZcckdTotalCount(Page page) {
		return super.getTotalCount(CkdEntity.class, page.getConditionList());
	}

	public List<CkdEntity> getZcckdxList(Page page) {
		List<CkdEntity> l = super.executeQuery(CkdEntity.class, page);
		List<CkdEntity> le = new ArrayList<CkdEntity>();
		for(CkdEntity ce : l){
			ce.setZt(ce.getZt().equals("1") ? "费用已清" : "费用未清");
			le.add(ce);
		}
		return le;
	}

	public int addCkd(CkdEntity entity) {
		return super.add(entity);
	}

	public int deleteCkd(int id) {
		return super.delete(CkdEntity.class, id);
	}

	public List<CkdmxEntity> getCkdmxListByCkdId(int ckdId) {
		String hql = " FROM CkdmxEntity where 1=1 and ckd.id='"+ckdId+"'";
		return (List<CkdmxEntity>)super.getList(hql);
	}

	public int deleteCkdmx(CkdmxEntity ckdmx) {
		return super.delete(ckdmx);
	}
}
