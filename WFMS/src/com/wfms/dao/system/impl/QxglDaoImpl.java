package com.wfms.dao.system.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.dao.system.IQxglDao;
import com.wfms.model.system.RightGenInfo;

/**
 * 
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：QxglDaoImpl
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Apr 22, 2010 7:28:42 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see QxglDaoImpl
 * @version 1.0
 *
 */
@Repository
public class QxglDaoImpl extends BaseDao implements IQxglDao {

	public int addQx(RightGenInfo qx) {
		int affectCount=-1;
		try{
			Serializable strId=super.insert(qx);
			affectCount=Integer.valueOf(String.valueOf(strId));
		}catch(Exception he){}
		return affectCount;
	}

	public int deleteQx(int id) {
		return super.delete(RightGenInfo.class, id);
	}

	public List<RightGenInfo> getAllQx() {
		Object obj = super.getList("FROM RightGenInfo");
		List<RightGenInfo> qxList=null;
		if(obj!=null && obj instanceof List)
		{
			qxList=(List<RightGenInfo>)obj;
		}
		return qxList;
	}

	public RightGenInfo getQxById(int id) {
		Object obj = super.getByPk(RightGenInfo.class, id);
		return obj== null ? null : (RightGenInfo)obj;
	}

	public int updateQx(RightGenInfo qx) {
		return super.update(qx);
	}

	public List<RightGenInfo> getQxsByIds(int[] qxIds) {
		String queryHql="FROM RightGenInfo";
		return super.executeQuery(queryHql, "rightId", qxIds);
	}

}
