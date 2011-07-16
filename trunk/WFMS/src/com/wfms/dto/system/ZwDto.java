package com.wfms.dto.system;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.wfms.model.system.RoleGenInfo;

@JsonIgnoreProperties(value={"zw"})
public class ZwDto {
	private RoleGenInfo zw = new RoleGenInfo();
	private java.util.List<RoleGenInfo>zwList = new java.util.ArrayList<RoleGenInfo>();
	public RoleGenInfo getZw() {
		return zw;
	}
	public void setZw(RoleGenInfo zw) {
		this.zw = zw;
	}
	public java.util.List<RoleGenInfo> getZwList() {
		return zwList;
	}
	public void setZwList(java.util.List<RoleGenInfo> zwList) {
		this.zwList = zwList;
	}
}
