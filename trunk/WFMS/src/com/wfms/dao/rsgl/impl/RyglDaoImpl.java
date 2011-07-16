/**
 *******************************************************************************
 * 文件名：RyglDaoImpl.java
 *
 * 描述�?
 * 
 * 创建日期：Jan 22, 2010 3:53:03 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dao.rsgl.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.dao.rsgl.IRyglDao;
import com.wfms.model.rsgl.RyEntity;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：RyglDaoImpl
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 22, 2010 3:53:03 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see RyglDaoImpl
 * @version 1.0
 *
 */
@Repository
public class RyglDaoImpl extends BaseDao implements IRyglDao {

	public int deleteRy(int ryId) {
		return super.delete(RyEntity.class,ryId);
	}

	public List<RyEntity> getRyByPage(Page page) {
		return super.executeQuery(RyEntity.class,page);
	}

	public int insertRy(RyEntity ry) {
		return super.add(ry);
	}

	public int updateRy(RyEntity ry) {
		return super.update(ry);
	}

	public int getRyCountByParams(List<ConditionBean> list) {
		return super.getTotalCount(RyEntity.class,list);
	}

	public List<RyEntity> getRyByBmAndExsitsUser(int bmId,boolean exsitsAble) {
		String queryHql="";
		if(exsitsAble)
		{
			queryHql="FROM RyEntity ry inner join MemberGenInfo yh on yh.ryid=ry.id where ry.bm.id=:bmId";
		}
		else
		{
			queryHql="select ry FROM RyEntity ry where ry not in(select ry FROM RyEntity ry inner join MemberGenInfo yh on yh.ryid=ry.id where ry.bm.id=:bmId)";
		}
		Map<String, Integer> params=new HashMap<String, Integer>(1);
		params.put("bmId",bmId);
		List<Object[]> joinList=super.executeQuery(queryHql,params);
		List<RyEntity> ryList=new ArrayList<RyEntity>(); 
		for(Iterator<Object[]> iter=joinList.iterator();iter.hasNext();)
		{
			Object[] objects=iter.next();
			RyEntity ry=(RyEntity)objects[0];
			ryList.add(ry);
		}
		return ryList;
	}
	
	public List<RyEntity> getRyByIds(int[] id){
		String hql = "FROM RyEntity where id in(";
		for(int i= 0;i<id.length;i++){
			if(i == id.length-1){
				hql += id[i] + ")";
				break;
			}
			hql += id[i] + ",";
		}
		return super.getList(hql);
	}

	public RyEntity getRyById(int id){
		return (RyEntity)super.getByPk(RyEntity.class, id);
	}

	public List<RyEntity> getRyByBmId(int bmId) {
		Map hqlParams=new HashMap(1);
		hqlParams.put("bm.id",bmId);
		return super.executeQuery(RyEntity.class,hqlParams);
	}

	public List<RyEntity> getRyByIfLsAndBm(int bmId,boolean ifLs) {
		String queryHql="";
		if(ifLs)
		{
			queryHql="FROM RyEntity ry inner join LsEntity ls on ls.ry.id=ry.id where ry.bm.id=?";
		}
		else
		{
			queryHql="select ry where ry not in(select ry FROM RyEntity ry inner join LsEntity ls on ls.ry.id=ry.id) where ry.bm.id=?";
		}
		List<Object[]> objList=super.executeQuery(queryHql,bmId);
		List<RyEntity> ryList=new ArrayList<RyEntity>(objList.size());
		for(Object[] objs:objList)
		{
			RyEntity ry=(RyEntity)objs[0];
			ryList.add(ry);
		}
		return ryList;
	}

	public int updateRyByCodesAndConditions(Map<Object, Object> codes,
			Map<Object, Object> cons) {
		return super.updateByCodesAndConditions(RyEntity.class, codes, cons);
	}

	public List<RyEntity> getAllRy() {
		return super.findAll(RyEntity.class);
	}

	public RyEntity getRyByBh(String rybh) {
		String hql = "FROM RyEntity where rybh=?";
		return (RyEntity)super.executeScalar(hql, rybh);
	}

	public int addRyList(List<RyEntity> ryList) {
		return super.batchAdd(ryList, ryList.size());
	}

	public int updateRyList(List<RyEntity> ryList) {
		return super.batchUpdate(ryList, ryList.size());
	}
}
