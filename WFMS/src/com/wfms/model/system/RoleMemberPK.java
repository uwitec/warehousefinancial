package com.wfms.model.system;

import java.io.Serializable;

public class RoleMemberPK implements Serializable{

	public RoleMemberPK(){
	}

	private RoleGenInfo role;
	
	private MemberGenInfo member;
	

	public RoleGenInfo getRole() {
		return role;
	}

	public void setRole(RoleGenInfo role) {
		this.role = role;
	}

	public MemberGenInfo getMember() {
		return member;
	}

	public void setMember(MemberGenInfo member) {
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
