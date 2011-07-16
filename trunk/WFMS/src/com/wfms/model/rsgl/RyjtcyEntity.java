package com.wfms.model.rsgl;
// default package

/**
 * RyjtcyEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RyjtcyEntity implements java.io.Serializable {

	private int id;
	private RyEntity ry;
	private String ch;
	private String xm;
	private String xb;
	private String dwhxx;
	private String sf;
	private String sfzh;
	private String zzmmdm;
	private String jkzkdm;
	private String dwdh;
	private String bz;
	private float ysr;
	private String csrq;
	private String lxfs;

	// Constructors

	/** default constructor */
	public RyjtcyEntity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RyEntity getRy() {
		return ry;
	}

	public void setRy(RyEntity ry) {
		this.ry = ry;
	}

	public String getCh() {
		return this.ch;
	}

	public void setCh(String ch) {
		this.ch = ch;
	}

	public String getXm() {
		return this.xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getZzmmdm() {
		return this.zzmmdm;
	}

	public void setZzmmdm(String zzmmdm) {
		this.zzmmdm = zzmmdm;
	}

	public String getDwdh() {
		return this.dwdh;
	}

	public void setDwdh(String dwdh) {
		this.dwdh = dwdh;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getXb() {
		return xb;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}

	public String getDwhxx() {
		return dwhxx;
	}

	public void setDwhxx(String dwhxx) {
		this.dwhxx = dwhxx;
	}

	public String getSf() {
		return sf;
	}

	public void setSf(String sf) {
		this.sf = sf;
	}

	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public String getJkzkdm() {
		return jkzkdm;
	}

	public void setJkzkdm(String jkzkdm) {
		this.jkzkdm = jkzkdm;
	}

	public float getYsr() {
		return ysr;
	}

	public void setYsr(float ysr) {
		this.ysr = ysr;
	}

	public String getCsrq() {
		return csrq;
	}

	public void setCsrq(String csrq) {
		this.csrq = csrq;
	}

	public String getLxfs() {
		return lxfs;
	}

	public void setLxfs(String lxfs) {
		this.lxfs = lxfs;
	}

}