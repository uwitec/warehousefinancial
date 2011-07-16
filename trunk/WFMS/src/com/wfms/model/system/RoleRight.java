package com.wfms.model.system;

// default package

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * PartRight entity.
 * 
 * @author MyEclipse Persistence Tools
 */
//@Entity
//@Table(name = "ROLE_RIGHT", schema = "ECM", uniqueConstraints = {})
public class RoleRight implements java.io.Serializable {

	// Fields

	private Integer id;
	private RoleGenInfo role;
	private RightGenInfo right;
	private int rightType;
	private String rightStatus;

	// Constructors

	/** default constructor */
	public RoleRight() {
	}

	/** full constructor */
	public RoleRight(Integer id, RoleGenInfo role,
			RightGenInfo right, int rightType, String rightStatus) {
		this.id = id;
		this.role = role;
		this.right = right;
		this.rightType = rightType;
		this.rightStatus = rightStatus;
	}

	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, insertable = true, updatable = true, precision = 10, scale = 0)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLID", unique = false, nullable = false, insertable = true, updatable = true)
	public RoleGenInfo getRole() {
		return role;
	}

	public void setRole(RoleGenInfo role) {
		this.role = role;
	}
	
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "RightID", unique = false, nullable = false, insertable = true, updatable = true)
	public RightGenInfo getRight() {
		return right;
	}

	public void setRight(RightGenInfo right) {
		this.right = right;
	}

	@Column(name = "RightType", unique = false, nullable = false, insertable = true, updatable = true, precision = 4, scale = 0)
	public int getRightType() {
		return this.rightType;
	}

	public void setRightType(int rightType) {
		this.rightType = rightType;
	}

	@Column(name = "RightStatus", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public String getRightStatus() {
		return this.rightStatus;
	}

	public void setRightStatus(String rightStatus) {
		this.rightStatus = rightStatus;
	}

}