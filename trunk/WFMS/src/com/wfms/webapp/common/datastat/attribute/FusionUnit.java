package com.wfms.webapp.common.datastat.attribute;

import java.util.ArrayList;
import java.util.List;

public class FusionUnit {
	
	private List<StatUnit> set = new ArrayList<StatUnit>(0);
	private List<StatUnit> categorySet = new ArrayList<StatUnit>(0);
	
	public List<StatUnit> getSet() {
		return set;
	}
	public void setSet(List<StatUnit> set) {
		this.set = set;
	}
	public List<StatUnit> getCategorySet() {
		return categorySet;
	}
	public void setCategorySet(List<StatUnit> categorySet) {
		this.categorySet = categorySet;
	}
	
}
