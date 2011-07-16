/**
 *******************************************************************************
 * 文件名：KnzgDaoImpl.java
 *
 * 描述�?
 * 
 * 创建日期：Apr 23, 2010 6:40:55 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dao.rsgl.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.dao.rsgl.IKnzgDao;
import com.wfms.model.rsgl.KnzgEntity;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：KnzgDaoImpl
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Apr 23, 2010 6:40:55 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author ZHANGYONG
 * @see KnzgDaoImpl
 * @version 1.0
 *
 */
@Repository
public class KnzgDaoImpl extends  BaseDao  implements IKnzgDao{

	public int addKnzg(KnzgEntity knzg) {
		return super.add(knzg);
	}

	public int deleteKnzg(int id) {
		return super.delete(KnzgEntity.class, id);
	}

	public KnzgEntity getKnzg(int id) {
		return (KnzgEntity)super.getByPk(KnzgEntity.class, id);
	}

	public List<KnzgEntity> getKnzgByPage(Page page) {
		return super.executeQuery(KnzgEntity.class, page);
	}
	
	public int getKnzgCount(List<ConditionBean> cons) {
		return super.getTotalCount(KnzgEntity.class, cons);
	}

	public int updateKnzg(List<KnzgEntity> knzgList) {
		return super.batchUpdate(knzgList, 15);
	}

	public KnzgEntity getKnzgByRyId(int ryId) {
		String queryHql="FROM KnzgEntity where ry.id=?";
		return (KnzgEntity)super.executeScalar(queryHql, ryId);
	}
	
	public int saveOrUpdateKnzg(KnzgEntity knzg) {
		if(knzg.getId()==0)
		{
			return super.add(knzg);
		}
		else
		{
			return super.update(knzg);
		}
	}

}
