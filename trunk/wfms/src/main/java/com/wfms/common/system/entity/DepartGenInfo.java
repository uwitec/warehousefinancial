package com.wfms.common.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.wfms.common.orm.BaseEntity;

@Entity
@Table(name = "DEP_GENINF", uniqueConstraints = {})
public class DepartGenInfo extends BaseEntity {

	private User user;
	private String deptname;
	private String parentid;
	private String memo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEM_ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "DEPTNAME", nullable = false, length = 64)
	public String getDeptname() {
		return this.deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	@Column(name = "PARENTID", nullable = false, length = 32)
	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	@Column(name = "MEMO", length = 128)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}