package com.wfms.model.system;

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

/**
 * MemberRight entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MEMBER_RIGHT", schema = "ECM", uniqueConstraints = {})
public class MemberRight implements java.io.Serializable {

	// Fields

	private Integer id;
	private RightGenInfo right;
	private MemberGenInfo member;
	private int rightType;
	private String controlType;
	private String rightStatus;
	private String grantAble;

	// Constructors

	/** default constructor */
	public MemberRight() {
	}

	/** full constructor */
	public MemberRight(Integer id, RightGenInfo rightGeninfo,
			MemberGenInfo memGeninfo, int rightType, String controlType,
			String rightStatus) {
		this.id = id;
		this.right = rightGeninfo;
		this.member = memGeninfo;
		this.rightType = rightType;
		this.controlType = controlType;
		this.rightStatus = rightStatus;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE,generator="webdemo_gen")
	@TableGenerator(name="webdemo_gen",
			table="tb_generator",
			pkColumnName="gen_name",
			valueColumnName="gen_value",
			pkColumnValue="memberright_pk",
			allocationSize=1)	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY,targetEntity=RightGenInfo.class)
	@JoinColumn(name="RIGHTID")
	public RightGenInfo getRight() {
		return this.right;
	}

	public void setRight(RightGenInfo right) {
		this.right = right;
	}

	@ManyToOne(fetch=FetchType.LAZY,targetEntity=MemberGenInfo.class)
	@JoinColumn(name="MEMID")
	public MemberGenInfo getMember() {
		return this.member;
	}

	public void setMemBer(MemberGenInfo member) {
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

	public void setMember(MemberGenInfo member) {
		this.member = member;
	}
	
	@Column(name = "GRANTABLE", unique = false, nullable = true, insertable = true, updatable = true, length=1)
	public String getGrantAble() {
		return grantAble;
	}

	public void setGrantAble(String grantAble) {
		this.grantAble = grantAble;
	}
}