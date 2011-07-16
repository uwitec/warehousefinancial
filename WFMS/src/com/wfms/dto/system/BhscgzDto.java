package com.wfms.dto.system;

import java.util.ArrayList;
import java.util.List;

import com.wfms.model.system.BhscgzszEntity;

public class BhscgzDto {
	private List<BhscgzszEntity> bhscgzList = new ArrayList<BhscgzszEntity>();

	public List<BhscgzszEntity> getBhscgzList() {
		return bhscgzList;
	}

	public void setBhscgzList(List<BhscgzszEntity> bhscgzList) {
		this.bhscgzList = bhscgzList;
	}
}
