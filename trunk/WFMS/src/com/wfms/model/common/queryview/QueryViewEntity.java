package com.wfms.model.common.queryview;



// default package

/**
 * QueryView entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class QueryViewEntity implements java.io.Serializable {

	// Fields

	private int id;
	private int yhid;
	private int mkid;
	private String stmc;
	private String conditions;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getStmc() {
		return stmc;
	}
	public void setStmc(String stmc) {
		this.stmc = stmc;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public int getYhid() {
		return yhid;
	}
	public void setYhid(int yhid) {
		this.yhid = yhid;
	}
	public int getMkid() {
		return mkid;
	}
	public void setMkid(int mkid) {
		this.mkid = mkid;
	}



}