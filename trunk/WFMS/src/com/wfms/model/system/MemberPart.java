package com.wfms.model.system;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * MemberRight entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MEMBER_PART", schema = "ECM", uniqueConstraints = {})
public class MemberPart implements java.io.Serializable {

	private Integer id;
	private PartGenInfo part;
	private MemberGenInfo member;

		
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMID", unique = false, nullable = false, insertable = true, updatable = true)
	public MemberGenInfo getMember() {
		return member;
	}

	public void setMember(MemberGenInfo member) {
		this.member = member;
	}

	@Id
	@Column(name="ID",precision=10,scale=0)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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