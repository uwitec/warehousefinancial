package com.wfms.common.system.entity;

import java.io.Serializable;

public class RoleMemberPK implements Serializable{

	public RoleMemberPK(){
	}

	private RoleGenInfo role;
	
	private User member;
	

	public RoleGenInfo getRole() {
		return role;
	}

	public void setRole(RoleGenInfo role) {
		this.role = role;
	}

	public User getMember() {
		return member;
	}

	public void setMember(User member) {
		this.member = member;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
	

}
