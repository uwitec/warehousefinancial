package com.wfms.dao.system;

import java.util.List;

import com.wfms.model.system.RoleGenInfo;

public interface IZwglDao {
	public List<RoleGenInfo> getAllZw();
	
	public RoleGenInfo getZwById(String id);
	
	public int updZw(RoleGenInfo zw);
	
	public int delZw(String id);
	
	public String insertZw(RoleGenInfo zw);
	
	/**
	 * 根据用户查询用户没有的职务信�?
	 * @param yhId 用户编号
	 * @return List<ZwEntity>
	 */
	public List<RoleGenInfo> getZwForYhNot(String yhId);
	
	public List<RoleGenInfo> getRolesByDepart(String depid);
	
	public RoleGenInfo getRoleByNameAndDepart(String name,String departname);
	
	public int updateRoleList(List<RoleGenInfo> roleList);
}
