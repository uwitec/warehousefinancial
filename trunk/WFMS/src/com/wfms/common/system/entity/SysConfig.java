package com.wfms.common.system.entity;

import com.wfms.common.orm.BaseEntity;

// default package

/**
 * XtszEntity entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysConfig extends BaseEntity {


	private String key;
	private String value;
	private String bz;
	private String keyName;
	private String sfkd;
	private int sx;


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getSfkd() {
		return sfkd;
	}

	public void setSfkd(String sfkd) {
		this.sfkd = sfkd;
	}

	public int getSx() {
		return sx;
	}

	public void setSx(int sx) {
		this.sx = sx;
	}

}