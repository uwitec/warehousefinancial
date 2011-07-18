package com.wfms.common.system.entity;

// default package

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.wfms.common.orm.BaseEntity;

/**
 * RolGenInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ROL_GENINF", uniqueConstraints = {})
public class RoleGenInfo extends BaseEntity {

	private String rolename;
	private String deptId;
	private String createtime;
	private String mem0;
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);
	private Set<RoleModule> roleModules = new HashSet<RoleModule>(0);

	@Column(name = "ROLENAME", nullable = false, length = 64)
	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@Column(name = "DEPT_ID", nullable = false, length = 32)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	@Column(name = "CREATETIME", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "MEM0", length = 128)
	public String getMem0() {
		return this.mem0;
	}

	public void setMem0(String mem0) {
		this.mem0 = mem0;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	public Set<RoleModule> getRoleModules() {
		return this.roleModules;
	}

	public void setRoleModules(Set<RoleModule> roleModules) {
		this.roleModules = roleModules;
	}

}