package com.wfms.common.system.entity;

// default package

import javax.persistence.Column;
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
 * MemberRight entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MEMBER_RIGHT",  uniqueConstraints = {})
public class UserModule extends BaseEntity {

	// Fields

	private RightGenInfo right;
	private User member;
	private int rightType;
	private String controlType;
	private String rightStatus;
	private String grantAble;

	// Constructors

	/** default constructor */
	public UserModule() {
	}

	/** full constructor */
	public UserModule(String id, RightGenInfo rightGeninfo, User memGeninfo,
			int rightType, String controlType, String rightStatus) {
		this.right = rightGeninfo;
		this.member = memGeninfo;
		this.rightType = rightType;
		this.controlType = controlType;
		this.rightStatus = rightStatus;
	}

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = RightGenInfo.class)
	@JoinColumn(name = "RIGHTID")
	public RightGenInfo getRight() {
		return this.right;
	}

	public void setRight(RightGenInfo right) {
		this.right = right;
	}

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinColumn(name = "MEMID")
	public User getMember() {
		return this.member;
	}

	public void setMemBer(User member) {
		this.member = member;
	}

	@Column(name = "RightType", unique = false, nullable = false, insertable = true, updatable = true, precision = 4, scale = 0)
	public int getRightType() {
		return this.rightType;
	}

	public void setRightType(int rightType) {
		this.rightType = rightType;
	}

	@Column(name = "ControlType", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public String getControlType() {
		return this.controlType;
	}

	public void setControlType(String controlType) {
		this.controlType = controlType;
	}

	@Column(name = "RightStatus", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
	public String getRightStatus() {
		return this.rightStatus;
	}

	public void setRightStatus(String rightStatus) {
		this.rightStatus = rightStatus;
	}

	public void setMember(User member) {
		this.member = member;
	}

	@Column(name = "GRANTABLE", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public String getGrantAble() {
		return grantAble;
	}

	public void setGrantAble(String grantAble) {
		this.grantAble = grantAble;
	}
}