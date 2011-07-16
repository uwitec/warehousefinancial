package com.wfms.dto.system;

import java.util.ArrayList;
import java.util.List;

import com.wfms.model.system.ModuleGenInfo;

public class GnmkDto {
	private List<ModuleGenInfo> gnmkList = new ArrayList<ModuleGenInfo>();
	private ModuleGenInfo gnmk = new ModuleGenInfo();
	public List<ModuleGenInfo> getGnmkList() {
		return gnmkList;
	}
	public void setGnmkList(List<ModuleGenInfo> gnmkList) {
		this.gnmkList = gnmkList;
	}
	public ModuleGenInfo getGnmk() {
		return gnmk;
	}
	public void setGnmk(ModuleGenInfo gnmk) {
		this.gnmk = gnmk;
	}
}
