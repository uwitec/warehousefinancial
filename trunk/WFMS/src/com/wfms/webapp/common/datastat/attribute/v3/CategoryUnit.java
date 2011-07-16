package com.wfms.webapp.common.datastat.attribute.v3;

public class CategoryUnit {
	
	public CategoryUnit(){}
	
	public CategoryUnit(Category category)
	{
		this.category = category;
	}
	
	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	
}
