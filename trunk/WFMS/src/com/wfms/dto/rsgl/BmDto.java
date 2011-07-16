package com.wfms.dto.rsgl;

import java.util.ArrayList;
import java.util.List;

import com.wfms.model.system.DepartGenInfo;

public class BmDto {
	private DepartGenInfo bm = new DepartGenInfo();
	private List<DepartGenInfo> bmList = new ArrayList<DepartGenInfo>();

	public DepartGenInfo getBm() {
		return bm;
	}
	public void setBm(DepartGenInfo bm) {
		this.bm = bm;
	}
	public List<DepartGenInfo> getBmList() {
		return bmList;
	}
	public void setBmList(List<DepartGenInfo> bmList) {
		this.bmList = bmList;
	}
}
