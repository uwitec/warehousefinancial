package com.wfms.common.system.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.wfms.common.orm.BaseEntity;

/**
 * Rolepart entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ROLE_PART", uniqueConstraints = {})
public class RolePart extends BaseEntity{

	// Fields

	private Integer id;
	private PartGenInfo part;
	private RoleGenInfo role;


	/** default constructor */
	public RolePart() {
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARTID", unique = false, nullable = true, insertable = true, updatable = true)
	public PartGenInfo getPart() {
		return part;
	}

	public void setPart(PartGenInfo part) {
		this.part = part;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLID", unique = false, nullable = false, insertable = true, updatable = true)
	public RoleGenInfo getRole() {
		return role;
	}

	public void setRole(RoleGenInfo role) {
		this.role = role;
	}

}