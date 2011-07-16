package com.wfms.dto.system;

import java.util.ArrayList;
import java.util.List;

import com.wfms.model.system.RightGenInfo;

public class QxDto {
	private RightGenInfo qx = new RightGenInfo();
	private List<RightGenInfo> qxList = new ArrayList<RightGenInfo>();
	public RightGenInfo getQx() {
		return qx;
	}
	public void setQx(RightGenInfo qx) {
		this.qx = qx;
	}
	public List<RightGenInfo> getQxList() {
		return qxList;
	}
	public void setQxList(List<RightGenInfo> qxList) {
		this.qxList = qxList;
	}

	
}
