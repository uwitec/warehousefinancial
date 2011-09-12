package com.wfms.common.system.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.wfms.common.orm.BaseEntity;

/**
 * 类型代码
 * 
 * @author CYC
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CODE_GENINF")
public class Code extends BaseEntity {
	private CodeGroup codeGroup;
	private String code;
	private String shortName;
	private String name;
	private String gb;
	private String systemCode;
	private String remark;
	private String parentID;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "GROUPID")
	public CodeGroup getCodeGroup() {
		return codeGroup;
	}

	public void setCodeGroup(CodeGroup codeGroup) {
		this.codeGroup = codeGroup;
	}

	@Column(name = "SHORTNAME", length = 32)
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "GB", length = 32)
	public String getGb() {
		return gb;
	}

	public void setGb(String gb) {
		this.gb = gb;
	}

	@Column(name = "MEMO")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "PARENTID")
	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	@Column(name = "ISSYSTEMCODE")
	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	@Column(name = "CODE", length = 32)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
