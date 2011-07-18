package com.wfms.common.system.entity;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.wfms.common.orm.BaseEntity;

@Entity
@Table(name = "MEMBER_RIGHT", uniqueConstraints = {})
public class UserModule extends BaseEntity {

	private String id;
	private User user;
	private ModuleGenInfo module;
	private String granttype;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEM_ID", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MODULE_ID", nullable = false)
	public ModuleGenInfo getModule() {
		return module;
	}

	public void setModule(ModuleGenInfo module) {
		this.module = module;
	}

	@Column(name = "GRANTTYPE", nullable = false, length = 32)
	public String getGranttype() {
		return this.granttype;
	}

	public void setGranttype(String granttype) {
		this.granttype = granttype;
	}

}