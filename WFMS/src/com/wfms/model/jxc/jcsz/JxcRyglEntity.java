package com.wfms.model.jxc.jcsz;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JXC_YGXXB", schema = "ECM", uniqueConstraints = {})
public class JxcRyglEntity implements java.io.Serializable {

	// Fields 人员维护

	private int id;
	private String ygmc;
	private String lxdh;
	private String xb;
	private String sfzh;
	private String deptid;
	private String rzsj;
	private String zt;
	private String lzsj;
	private String memo;
	private String deptmc;


	// Property accessors
	@Id
	@Column(name = "ID", unique = true, nullable = false, insertable = true, updatable = true, precision = 10, scale = 0)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "YGMC", unique = false, nullable = false, insertable = true, updatable = true, length = 40)
	public String getYgmc() {
		return this.ygmc;
	}

	public void setYgmc(String ygmc) {
		this.ygmc = ygmc;
	}

	@Column(name = "LXDH", unique = false, nullable = false, insertable = true, updatable = true, length = 40)
	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	@Column(name = "XB", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getXb() {
		return this.xb;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}

	@Column(name = "SFZH", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public String getSfzh() {
		return this.sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	@Column(name = "DEPTID", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	public String getDeptid() {
		return this.deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	@Column(name = "RZSJ", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	public String getRzsj() {
		return this.rzsj;
	}

	public void setRzsj(String rzsj) {
		this.rzsj = rzsj;
	}

	@Column(name = "ZT", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getZt() {
		return this.zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	@Column(name = "LZSJ", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	public String getLzsj() {
		return this.lzsj;
	}

	public void setLzsj(String lzsj) {
		this.lzsj = lzsj;
	}

	@Column(name = "DEPTMC", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	public String getDeptmc() {
		return this.deptmc;
	}

	public void setDeptmc(String deptmc) {
		this.deptmc = deptmc;
	}

	@Column(name = "MEMO", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}