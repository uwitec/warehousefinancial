package com.wfms.common.system.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.wfms.common.orm.BaseEntity;

@Entity
@Table(name="ROL_MEM"
, uniqueConstraints = {  }
)
@IdClass(RoleMemberPK.class)

public class UserRole extends BaseEntity{

	private RoleGenInfo role;
	private User member;

	
	//@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	//@JoinColumn(name = "MEMID", unique = false, nullable = false, insertable = true, updatable = true)
	@Id
	@ManyToOne(fetch=FetchType.EAGER)
	/*@JoinTable(name="ROL_MEM",joinColumns={@JoinColumn(name="MEMID")},
	    	inverseJoinColumns={@JoinColumn(name="ROLID")})*/
	@NotFound(action=NotFoundAction.IGNORE)
	public User getMember() {
		return member;
	}

	public void setMember(User member) {
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
