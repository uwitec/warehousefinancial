package com.wfms.model.system;
// default package

/**
 * QtdmEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class QtdmEntity implements java.io.Serializable {

	// Fields
	
	private int id;
	private String dmjc;
	private String dmmc;
	private String dmlb;
	private String bz;

	// Constructors

	/** default constructor */
	public QtdmEntity() {
	}
	public QtdmEntity(int id) {
		this.id = id;
	}

	/** minimal constructor */
	public QtdmEntity(String dmjc, String dmmc, String dmlb) {
		this.dmjc = dmjc;
		this.dmmc = dmmc;
		this.dmlb = dmlb;
	}

	/** full constructor */
	public QtdmEntity(String dmjc, String dmmc, String dmlb, String bz) {
		this.dmjc = dmjc;
		this.dmmc = dmmc;
		this.dmlb = dmlb;
		this.bz = bz;
	}

	// Property accessors


	public String getDmjc() {
		return this.dmjc;
	}

	public void setDmjc(String dmjc) {
		this.dmjc = dmjc;
	}

	public String getDmmc() {
		return this.dmmc;
	}

	public void setDmmc(String dmmc) {
		this.dmmc = dmmc;
	}

	public String getDmlb() {
		return this.dmlb;
	}

	public void setDmlb(String dmlb) {
		this.dmlb = dmlb;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}