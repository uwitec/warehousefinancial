package com.wfms.common.system.entity;

// default package

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.wfms.common.orm.BaseEntity;

/**
 * MemberRight entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MEMBER_PART", uniqueConstraints = {})
public class MemberPart extends BaseEntity {

	private Integer id;
	private PartGenInfo part;
	private User member;

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMID", unique = false, nullable = false, insertable = true, updatable = true)
	public User getMember() {
		return member;
	}

	public void setMember(User member) {
		this.member = member;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "PARTID", unique = false, nullable = false, insertable = true, updatable = true)
	public PartGenInfo getPart() {
		return part;
	}

	public void setPart(PartGenInfo part) {
		this.part = part;
	}

}