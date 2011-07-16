package com.wfms.model.common.datastat;
// default package

/**
 * StatRuleEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class StatRuleEntity implements java.io.Serializable {

	// Fields

	private int id;
	private String ssbmc;
	private String zdmc;
	private String refzdmc;
	private String refbmc;
	private String fllxdm;
	private String lxdmzd;
	private int childRef;
	private String lxdmxszd;
	
	// Constructors

	public String getLxdmxszd() {
		return lxdmxszd;
	}

	public void setLxdmxszd(String lxdmxszd) {
		this.lxdmxszd = lxdmxszd;
	}

	/** default constructor */
	public StatRuleEntity() {
	}

	// Property accessors

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSsbmc() {
		return ssbmc;
	}

	public void setSsbmc(String ssbmc) {
		this.ssbmc = ssbmc;
	}

	public String getZdmc() {
		return this.zdmc;
	}

	public void setZdmc(String zdmc) {
		this.zdmc = zdmc;
	}

	public String getRefzdmc() {
		return this.refzdmc;
	}

	public void setRefzdmc(String refzdmc) {
		this.refzdmc = refzdmc;
	}

	public String getRefbmc() {
		return this.refbmc;
	}

	public void setRefbmc(String refbmc) {
		this.refbmc = refbmc;
	}

	public String getFllxdm() {
		return this.fllxdm;
	}

	public void setFllxdm(String fllxdm) {
		this.fllxdm = fllxdm;
	}

	public String getLxdmzd() {
		return this.lxdmzd;
	}

	public void setLxdmzd(String lxdmzd) {
		this.lxdmzd = lxdmzd;
	}

	public int getChildRef() {
		return childRef;
	}

	public void setChildRef(int childRef) {
		this.childRef = childRef;
	}

}