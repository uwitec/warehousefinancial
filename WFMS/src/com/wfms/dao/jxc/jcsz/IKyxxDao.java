/**
 *******************************************************************************
 * 文件名：IHzqyDao.java
 *
 * 描述�?
 * 
 * 创建日期：Apr 8, 2010 11:45:06 AM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dao.jxc.jcsz;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.model.jxc.jcsz.KyxxEntity;

public interface IKyxxDao {
	public List<KyxxEntity> getAllKyxx();
	public int getKyxxEntityCount(List<ConditionBean> list);
	public List<KyxxEntity> getKyxxEntity(Page page);
	public int deleteKyxx(int id);
	public KyxxEntity getKyxxEntityPk(int id);
	public int addKyxx(KyxxEntity KyxxEntity);
	public int updateKyxx(List<KyxxEntity> KhxxList);
	public List<KyxxEntity> getAllKyxxByJoin(String khlb);
	
}
