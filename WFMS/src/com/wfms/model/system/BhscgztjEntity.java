package com.wfms.model.system;
// default package

/**
 * BhscgztjEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class BhscgztjEntity implements java.io.Serializable {

	// Fields

	private int id;
	private int fjid;
	private String bmc;
	private String zdmc;
	private String sfglqtb;
	private String glbmc;
	private String glzdmc;
	private String bz;

	// Constructors

	/** default constructor */
	public BhscgztjEntity() {
	}

	/** minimal constructor */
	public BhscgztjEntity(int fjid, String bmc, String zdmc, String sfglqtb) {
		this.fjid = fjid;
		this.bmc = bmc;
		this.zdmc = zdmc;
		this.sfglqtb = sfglqtb;
	}

	/** full constructor */
	public BhscgztjEntity(int fjid, String bmc, String zdmc, String sfglqtb,
			String glbmc, String glzdmc, String bz) {
		this.fjid = fjid;
		this.bmc = bmc;
		this.zdmc = zdmc;
		this.sfglqtb = sfglqtb;
		this.glbmc = glbmc;
		this.glzdmc = glzdmc;
		this.bz = bz;
	}

	// Property accessors

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFjid() {
		return this.fjid;
	}

	public void setFjid(int fjid) {
		this.fjid = fjid;
	}

	public String getBmc() {
		return this.bmc;
	}

	public void setBmc(String bmc) {
		this.bmc = bmc;
	}

	public String getZdmc() {
		return this.zdmc;
	}

	public void setZdmc(String zdmc) {
		this.zdmc = zdmc;
	}

	public String getSfglqtb() {
		return this.sfglqtb;
	}

	public void setSfglqtb(String sfglqtb) {
		this.sfglqtb = sfglqtb;
	}

	public String getGlbmc() {
		return this.glbmc;
	}

	public void setGlbmc(String glbmc) {
		this.glbmc = glbmc;
	}

	public String getGlzdmc() {
		return this.glzdmc;
	}

	public void setGlzdmc(String glzdmc) {
		this.glzdmc = glzdmc;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}