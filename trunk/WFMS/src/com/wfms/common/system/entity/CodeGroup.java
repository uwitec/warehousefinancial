package com.wfms.common.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.wfms.common.orm.BaseEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "CODE_GROUP")
public class CodeGroup extends BaseEntity {
	private String name;
	private String gb;
	private String remark;

	public CodeGroup() {
	}

	public CodeGroup(String id) {
		this.id = id;
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

}
