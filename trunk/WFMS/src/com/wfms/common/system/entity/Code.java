package com.wfms.common.system.entity;

import com.wfms.common.orm.BaseEntity;

// default package

/**
 * LxdmEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Code extends BaseEntity {

	private String lxdm;
	private String dmmc;
	private String sjlxdm;
	private String dmjc;
	private String sfkbj;
	private String mkmc;
	private String gbdm;

	public String getGbdm() {
		return gbdm;
	}

	public void setGbdm(String gbdm) {
		this.gbdm = gbdm;
	}

	public Code() {
	}

	public Code(String lxdm, String dmmc, String sjlxdm, String sfkbj) {
		this.lxdm = lxdm;
		this.dmmc = dmmc;
		this.sjlxdm = sjlxdm;
		this.sfkbj = sfkbj;
	}

	/** full constructor */
	public Code(String lxdm, String dmmc, String sjlxdm, String dmjc,
			String sfkbj, String mkmc) {
		this.lxdm = lxdm;
		this.dmmc = dmmc;
		this.sjlxdm = sjlxdm;
		this.dmjc = dmjc;
		this.sfkbj = sfkbj;
		this.mkmc = mkmc;
	}

	// Property accessors

	public String getLxdm() {
		return this.lxdm;
	}

	public void setLxdm(String lxdm) {
		this.lxdm = lxdm;
	}

	public String getDmmc() {
		return this.dmmc;
	}

	public void setDmmc(String dmmc) {
		this.dmmc = dmmc;
	}

	public String getSjlxdm() {
		return this.sjlxdm;
	}

	public void setSjlxdm(String sjlxdm) {
		this.sjlxdm = sjlxdm;
	}

	public String getDmjc() {
		return this.dmjc;
	}

	public void setDmjc(String dmjc) {
		this.dmjc = dmjc;
	}

	public String getSfkbj() {
		return this.sfkbj;
	}

	public void setSfkbj(String sfkbj) {
		this.sfkbj = sfkbj;
	}

	public String getMkmc() {
		return this.mkmc;
	}

	public void setMkmc(String mkmc) {
		this.mkmc = mkmc;
	}

}