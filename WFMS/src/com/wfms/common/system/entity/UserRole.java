package com.wfms.common.system.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.wfms.common.orm.BaseEntity;

@Entity
@Table(name = "ROL_MEM", uniqueConstraints = {})
public class UserRole extends BaseEntity {

	private User user;
	private RoleGenInfo role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEM_ID", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID", nullable = false)
	public RoleGenInfo getRole() {
		return role;
	}

	public void setRole(RoleGenInfo role) {
		this.role = role;
	}
}
