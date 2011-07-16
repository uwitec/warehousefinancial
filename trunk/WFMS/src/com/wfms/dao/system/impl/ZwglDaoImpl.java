package com.wfms.dao.system.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.util.StringUtil;
import com.wfms.dao.system.IZwglDao;
import com.wfms.model.system.DepartGenInfo;
import com.wfms.model.system.MemberGenInfo;
import com.wfms.model.system.RoleGenInfo;

@Repository
public class ZwglDaoImpl extends BaseDao implements IZwglDao {

	public int delZw(String id) {
		return super.delete(RoleGenInfo.class, id);
	}

	public List<RoleGenInfo> getAllZw() {
		Object obj = super.getList("FROM RoleGenInfo");
		return obj == null ?  null : (List<RoleGenInfo>)obj;
	}

	public RoleGenInfo getZwById(String id) {
		Object obj = super.getByPk(RoleGenInfo.class, id);
		return obj == null ?  null : (RoleGenInfo)obj;
	}

	public int updZw(RoleGenInfo zw) {
		return super.update(zw);
	}

	public String insertZw(RoleGenInfo zw) {
		Serializable sid = super.insert(zw);
		String res=(String)sid;
		return res;
	}
	
	public List<RoleGenInfo> getZwForYhNot(String yhId) {
		MemberGenInfo member = (MemberGenInfo)super.getByPk(MemberGenInfo.class, yhId);
		List<RoleGenInfo> hasRoles = member.getRoles();
		List<RoleGenInfo> roleList = getAllZw();
		for(int i=0;i<roleList.size();i++)
		{
			for(int j=0;j<hasRoles.size();j++)
			{
				if(roleList.get(i).getRolid().equals(hasRoles.get(j).getRolid()))
				{
					roleList.remove(i);
				}
			}
		}
		return roleList;
	}

	public List<RoleGenInfo> getRolesByDepart(String depid) {
		DepartGenInfo depart = (DepartGenInfo)super.getByPk(DepartGenInfo.class, depid);
		if(depart!=null)
		{
			return depart.getRoles();
		}
		return new ArrayList<RoleGenInfo>(0);
	}

	public RoleGenInfo getRoleByNameAndDepart(String name, String departname) {
		String hql="";
		String[] params = null;
		if(!StringUtil.isNullOrEmpty(name)
				&& !StringUtil.isNullOrEmpty(departname))
		{
			hql = "FROM RoleGenInfo where name=? and depart.name=?";
			params = new String[]{name,departname};
		}
		else if(StringUtil.isNullOrEmpty(departname))
		{
			hql = "FROM RoleGenInfo where name=?";
			params = new String[]{name};
		}
		return (RoleGenInfo)super.executeScalar(hql, params);
	}

	public int updateRoleList(List<RoleGenInfo> roleList) {
		return super.batchUpdate(roleList, roleList.size());
	}

}
