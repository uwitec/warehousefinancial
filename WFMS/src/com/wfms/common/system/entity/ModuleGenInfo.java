package com.wfms.common.system.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.wfms.common.orm.BaseEntity;

@Entity
@Table(name = "MODULE_GENINF", uniqueConstraints = {})
public class ModuleGenInfo extends BaseEntity {

	private String parentid;
	private String modulename;
	private String phoneticize;
	private String forwardpage;
	private String createtime;
	private String description;
	private String moduleType;
	private String memo;
	private Set<RoleModule> roleModules = new HashSet<RoleModule>(0);
	private Set<UserModule> memModules = new HashSet<UserModule>(0);

	@Column(name = "PARENTID", length = 32)
	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	@Column(name = "MODULENAME", nullable = false, length = 32)
	public String getModulename() {
		return this.modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	@Column(name = "PHONETICIZE", length = 32)
	public String getPhoneticize() {
		return this.phoneticize;
	}

	public void setPhoneticize(String phoneticize) {
		this.phoneticize = phoneticize;
	}

	@Column(name = "FORWARDPAGE", nullable = false, length = 128)
	public String getForwardpage() {
		return this.forwardpage;
	}

	public void setForwardpage(String forwardpage) {
		this.forwardpage = forwardpage;
	}

	@Column(name = "CREATETIME", length = 20)
	public String getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Column(name = "DESCRIPTION", length = 64)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "MEMO", length = 128)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "module")
	public Set<RoleModule> getRoleModules() {
		return this.roleModules;
	}

	public void setRoleModules(Set<RoleModule> roleModules) {
		this.roleModules = roleModules;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<UserModule> getMemModules() {
		return this.memModules;
	}

	public void setMemModules(Set<UserModule> memModules) {
		this.memModules = memModules;
	}

	@Column(name = "MODULETYPE", length = 1)
	public String getModuleType() {
		return moduleType;
	}

	public void setModuleType(String moduleType) {
		this.moduleType = moduleType;
	}

}