package com.wfms.model.system;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="ROL_MEM"
    ,schema="ECM"
, uniqueConstraints = {  }
)
@IdClass(RoleMemberPK.class)

public class RoleMember {

	private RoleGenInfo role;
	private MemberGenInfo member;

	
	//@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	//@JoinColumn(name = "MEMID", unique = false, nullable = false, insertable = true, updatable = true)
	@Id
	@ManyToOne(fetch=FetchType.EAGER)
	/*@JoinTable(name="ROL_MEM",joinColumns={@JoinColumn(name="MEMID")},
	    	inverseJoinColumns={@JoinColumn(name="ROLID")})*/
	@NotFound(action=NotFoundAction.IGNORE)
	public MemberGenInfo getMember() {
		return member;
	}

	public void setMember(MemberGenInfo member) {
		this.member = member;
	}

	
	//@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	//@JoinColumn(name = "ROLID", unique = false, nullable = false, insertable = true, updatable = true)
	@Id
	@ManyToOne(fetch=FetchType.EAGER)
	/*@JoinTable(name="ROL_MEM",joinColumns={@JoinColumn(name="ROLID")},
	    	inverseJoinColumns={@JoinColumn(name="MEMID")})
	@NotFound(action=NotFoundAction.IGNORE)*/
	public RoleGenInfo getRole() {
		return role;
	}

	public void setRole(RoleGenInfo role) {
		this.role = role;
	}

}
