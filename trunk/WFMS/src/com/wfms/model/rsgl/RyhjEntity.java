package com.wfms.model.rsgl;
// default package

/**
 * RyhjEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RyhjEntity implements java.io.Serializable {

	// Fields

	private int id;
	private RyEntity ry;
	private String hjcgbh;
	private String hjxmbh;
	private String hjcgmc;
	private String hjrq;
	private String hjlbdm;
	private String jljbdm;
	private String sjdjdm;
	private String xklydm;
	private String sjdw;
	private String bz;

	// Constructors

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	/** default constructor */
	public RyhjEntity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHjcgbh() {
		return this.hjcgbh;
	}

	public void setHjcgbh(String hjcgbh) {
		this.hjcgbh = hjcgbh;
	}

	public String getHjxmbh() {
		return this.hjxmbh;
	}

	public void setHjxmbh(String hjxmbh) {
		this.hjxmbh = hjxmbh;
	}

	public String getHjcgmc() {
		return this.hjcgmc;
	}

	public void setHjcgmc(String hjcgmc) {
		this.hjcgmc = hjcgmc;
	}

	public String getHjrq() {
		return this.hjrq;
	}

	public void setHjrq(String hjrq) {
		this.hjrq = hjrq;
	}

	public String getHjlbdm() {
		return this.hjlbdm;
	}

	public void setHjlbdm(String hjlbdm) {
		this.hjlbdm = hjlbdm;
	}

	public String getJljbdm() {
		return this.jljbdm;
	}

	public void setJljbdm(String jljbdm) {
		this.jljbdm = jljbdm;
	}

	public String getSjdjdm() {
		return sjdjdm;
	}

	public void setSjdjdm(String sjdjdm) {
		this.sjdjdm = sjdjdm;
	}

	public String getXklydm() {
		return this.xklydm;
	}

	public void setXklydm(String xklydm) {
		this.xklydm = xklydm;
	}

	public String getSjdw() {
		return this.sjdw;
	}

	public void setSjdw(String sjdw) {
		this.sjdw = sjdw;
	}

	public RyEntity getRy() {
		return ry;
	}

	public void setRy(RyEntity ry) {
		this.ry = ry;
	}


}