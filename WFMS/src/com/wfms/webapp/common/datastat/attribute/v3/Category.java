package com.wfms.webapp.common.datastat.attribute.v3;

public class Category {
	
	//private Category category;
	
	public Category(){}
	
	public Category(String label)
	{
		this.label = label;
	}
	//X坐标显示标签
	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
