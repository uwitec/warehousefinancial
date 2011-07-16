package com.wfms.model.rsgl;
// default package

/**
 * RyydEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RyydEntity implements java.io.Serializable {

	// Fields

	private int id;
	private RyEntity ry;
	private String ydlbdm;
	private String ydyy;
	private String ydsj;
	private String jbr;
	private String shr;
	private String shrq;
	private String bz;
	private String sflz;
	private BmEntity ydqBm;
	private BmEntity ydhBm;


	public BmEntity getYdqBm() {
		return ydqBm;
	}

	public void setYdqBm(BmEntity ydqBm) {
		this.ydqBm = ydqBm;
	}

	public BmEntity getYdhBm() {
		return ydhBm;
	}

	public void setYdhBm(BmEntity ydhBm) {
		this.ydhBm = ydhBm;
	}

	public String getSflz() {
		return sflz;
	}

	public void setSflz(String sflz) {
		this.sflz = sflz;
	}


	public RyEntity getRy() {
		return ry;
	}

	public void setRy(RyEntity ry) {
		this.ry = ry;
	}

	/** default constructor */
	public RyydEntity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYdlbdm() {
		return this.ydlbdm;
	}

	public void setYdlbdm(String ydlbdm) {
		this.ydlbdm = ydlbdm;
	}

	public String getYdyy() {
		return this.ydyy;
	}

	public void setYdyy(String ydyy) {
		this.ydyy = ydyy;
	}

	public String getYdsj() {
		return this.ydsj;
	}

	public void setYdsj(String ydsj) {
		this.ydsj = ydsj;
	}

	public String getJbr() {
		return this.jbr;
	}

	public void setJbr(String jbr) {
		this.jbr = jbr;
	}

	public String getShr() {
		return this.shr;
	}

	public void setShr(String shr) {
		this.shr = shr;
	}

	public String getShrq() {
		return this.shrq;
	}

	public void setShrq(String shrq) {
		this.shrq = shrq;
	}

	public String getBz() {
		return this.bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

}