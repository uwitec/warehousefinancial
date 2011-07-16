package com.wfms.dao.system.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.dao.system.IZwJsglDao;
import com.wfms.model.system.RolePart;

@Repository
public class ZwJsglDaoImpl extends BaseDao implements IZwJsglDao {

	public int insertZwJs(RolePart zwjs) {
		Serializable sid= super.insert(zwjs);
		int res=Integer.valueOf(String.valueOf(sid));
		return res;
	}

	public int delZwJsByYhAndZw(int jsId,String zwId) {
		String hql="delete from RolePart zwjs where zwjs.role.rolid='"+zwId+"' and zwjs.part.partId="+jsId;
		return super.deleteByHql(hql);
	}

	
}
