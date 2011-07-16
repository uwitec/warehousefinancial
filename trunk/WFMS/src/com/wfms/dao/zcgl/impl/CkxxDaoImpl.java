package com.wfms.dao.zcgl.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.entity.Page;
import com.wfms.dao.zcgl.ICkxxDao;
import com.wfms.model.zcgl.CkxxEntity;
import com.wfms.model.zcgl.CkzcEntity;

@Repository
public class CkxxDaoImpl extends BaseDao implements ICkxxDao{

	public int addCkxx(CkxxEntity ckxx) {
		return super.add(ckxx);
	}

	public int deleteCkxx(int id) {
		return super.delete(CkxxEntity.class, id);
	}

	public List<CkxxEntity> getCkxxList(Page page) {
		return super.executeQuery(CkxxEntity.class, page);
	}

	public int getCkxxTotalCount(Page page) {
		return super.getTotalCount(CkxxEntity.class, page.getConditionList());
	}

	public int updateCkxx(List<CkxxEntity> ckxxList) {
		return super.batchUpdate(ckxxList, 15);
	}

	public int updateCkxx(CkxxEntity ckxx) {
		return super.update(ckxx);
	}

	public List<CkxxEntity> getCkxxList(String userId) {
//		String hql = "FROM CkxxEntity where glyid='"+userId+"'";
		String hql = "FROM CkxxEntity";
		List<CkxxEntity> ckxxList = (List<CkxxEntity>)super.getList(hql);
		if (ckxxList == null && "".equals(ckxxList)) {
			ckxxList = (List<CkxxEntity>)super.getList(CkxxEntity.class);
		}
		return ckxxList;
	}

	public List<CkzcEntity> getCkzcListByCkId(String ckId) {
		String hql = "FROM CkzcEntity ";
		if (ckId != null && !"".equals(ckId)) {
			hql += " where ckxx.id='"+ckId+"' ";
		}
		
		return (List<CkzcEntity>)super.getList(hql);
	}

	public CkxxEntity getCkxxById(int ckId) {
		return (CkxxEntity)super.getByPk(CkxxEntity.class, ckId);
	}

}
