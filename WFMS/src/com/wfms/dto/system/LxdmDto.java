package com.wfms.dto.system;

import java.util.List;

import com.wfms.model.system.LxdmEntity;

public class LxdmDto {
	
	private LxdmEntity lxdm;
	
	private List<LxdmEntity> lxdmList;

	public LxdmEntity getLxdm() {
		return lxdm;
	}

	public void setLxdm(LxdmEntity lxdm) {
		this.lxdm = lxdm;
	}

	public List<LxdmEntity> getLxdmList() {
		return lxdmList;
	}

	public void setLxdmList(List<LxdmEntity> lxdmList) {
		this.lxdmList = lxdmList;
	}
	
	
}
