package com.wfms.dto.rsgl;

import java.util.ArrayList;
import java.util.List;

import com.wfms.model.rsgl.RyEntity;

public class RyDto {
	private RyEntity ry;
	private List<RyEntity> ryList = new ArrayList<RyEntity>();
	public RyEntity getRy() {
		return ry;
	}
	public void setRy(RyEntity ry) {
		this.ry = ry;
	}
	public List<RyEntity> getRyList() {
		return ryList;
	}
	public void setRyList(List<RyEntity> ryList) {
		this.ryList = ryList;
	}
}
