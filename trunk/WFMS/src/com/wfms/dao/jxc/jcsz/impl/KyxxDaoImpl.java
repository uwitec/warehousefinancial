/**
 *******************************************************************************
 * 文件名：HzqyDaoImpl.java
 *
 * 描述：
 * 
 * 创建日期：Apr 8, 2010 11:45:06 AM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dao.jxc.jcsz.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.dao.jxc.jcsz.IKyxxDao;
import com.wfms.model.jxc.jcsz.KyxxEntity;

@Repository
public class KyxxDaoImpl extends BaseDao implements IKyxxDao {
	public List<KyxxEntity> getAllKyxx() {
		return super.findAll(KyxxEntity.class);
	}
	public List<KyxxEntity> getKyxxEntity(Page page) {
		return super.executeQuery(KyxxEntity.class, page);
	}
	public int getKyxxEntityCount(List<ConditionBean> list) {
		return super.getTotalCount(KyxxEntity.class, list);
	}
	public int deleteKyxx(int id) {
		return super.delete(KyxxEntity.class, id);
	}
	public KyxxEntity getKyxxEntityPk(int id) {
		return (KyxxEntity)super.getByPk(KyxxEntity.class, id);
	}
	public int addKyxx(KyxxEntity KyxxEntity) {
		return super.add(KyxxEntity);
	}
	public int updateKyxx(List<KyxxEntity> KyxxList) {
		return super.batchUpdate(KyxxList,15);
	}
	public List<KyxxEntity> getAllKyxxByJoin(String khlb) {
		String hql = " From KyxxEntity where khlb=?";
		return super.executeQuery(hql, khlb);
	}
}
