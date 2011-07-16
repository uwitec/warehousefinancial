package com.wfms.dao.system.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.util.ConditionBean;
import com.wfms.dao.system.IYhZwglDao;
import com.wfms.model.system.RoleGenInfo;
import com.wfms.model.system.RoleMember;

@Repository
public class YhZwglDaoImpl extends BaseDao implements IYhZwglDao {

	public int insertYhZw(RoleMember yhzw) {
		Serializable sid= super.insert(yhzw);
		return 1;
	}

	public int delYhZwByYhAndZw(RoleMember yhzw) {
		String hql="delete from RoleMember yhzw where yhzw.member.memid='"+yhzw.getMember().getMemid()+"' and yhzw.role.rolid='"+yhzw.getRole().getRolid()+"'";
		return super.updateByHql(hql);
	}

	public RoleGenInfo getZwByDm(String name){
		Object obj = super.getEntityByConditions("FROM ZwEntity where zwdm=?", name);
		return obj == null ? null : (RoleGenInfo) obj;
	}

	public int getYhzwCount(List<ConditionBean> list) {
		return super.getTotalCount(RoleMember.class, list);
	}
	
}
