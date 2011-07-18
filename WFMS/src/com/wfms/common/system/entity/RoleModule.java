package com.wfms.common.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ROLE_MODULE", schema = "dbo", catalog = "wfms")
public class RoleModule implements java.io.Serializable {

	private RoleGenInfo role;
	private ModuleGenInfo module;
	private String granttype;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID", nullable = false)
	public RoleGenInfo getRole() {
		return role;
	}

	public void setRole(RoleGenInfo role) {
		this.role = role;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODULE_ID", nullable = false)
	public ModuleGenInfo getModule() {
		return module;
	}

	public void setModule(ModuleGenInfo module) {
		this.module = module;
	}

	@Column(name = "GRANTTYPE", length = 32)
	public String getGranttype() {
		return this.granttype;
	}

	public void setGranttype(String granttype) {
		this.granttype = granttype;
	}

}