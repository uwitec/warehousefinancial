package com.wfms.model.jxc.jcsz;
// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * KyxxEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "JXC_KYXX", schema = "ECM", uniqueConstraints = {})
public class KyxxEntity implements java.io.Serializable {

	// Fields 客户维护

	private int id;
	private String jdbh;
	private String jdmc;
	private String xxdz;
	private String fzr;
	private String dh;
	private String qydz;
	private String sxgsid;
	private String sxgsmc;
	private String memo;
	private String khlb;


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

	@Column(name = "JDBH", unique = false, nullable = false, insertable = true, updatable = true, length = 40)
	public String getJdbh() {
		return this.jdbh;
	}

	public void setJdbh(String jdbh) {
		this.jdbh = jdbh;
	}

	@Column(name = "KHLB", unique = false, nullable = false, insertable = true, updatable = true, length = 40)
	public String getKhlb() {
		return khlb;
	}

	public void setKhlb(String khlb) {
		this.khlb = khlb;
	}

	@Column(name = "JDMC", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getJdmc() {
		return this.jdmc;
	}

	public void setJdmc(String jdmc) {
		this.jdmc = jdmc;
	}

	@Column(name = "XXDZ", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
	public String getXxdz() {
		return this.xxdz;
	}

	public void setXxdz(String xxdz) {
		this.xxdz = xxdz;
	}

	@Column(name = "FZR", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	public String getFzr() {
		return this.fzr;
	}

	public void setFzr(String fzr) {
		this.fzr = fzr;
	}

	@Column(name = "DH", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	public String getDh() {
		return this.dh;
	}

	public void setDh(String dh) {
		this.dh = dh;
	}

	@Column(name = "QYDZ", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
	public String getQydz() {
		return this.qydz;
	}

	public void setQydz(String qydz) {
		this.qydz = qydz;
	}

	@Column(name = "SXGSID", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	public String getSxgsid() {
		return this.sxgsid;
	}

	public void setSxgsid(String sxgsid) {
		this.sxgsid = sxgsid;
	}

	@Column(name = "SXGSMC", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	public String getSxgsmc() {
		return this.sxgsmc;
	}

	public void setSxgsmc(String sxgsmc) {
		this.sxgsmc = sxgsmc;
	}

	@Column(name = "MEMO", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}