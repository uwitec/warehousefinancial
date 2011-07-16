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
import com.wfms.model.jxc.jcsz.JxcRyglEntity;

public interface IJxcRyglDao {
	public List<JxcRyglEntity> getAllRygl();
	public int getJxcRyglEntityCount(List<ConditionBean> list);
	public List<JxcRyglEntity> getJxcRyglEntity(Page page);
	public int deleteRygl(int id);
	public JxcRyglEntity getJxcRyglEntityPk(int id);
	public int addRygl(JxcRyglEntity JxcRyglEntity);
	public int updateRygl(List<JxcRyglEntity> KhxxList);
	public List<JxcRyglEntity> getAllRyglByJoin(String khlb);
	
}
