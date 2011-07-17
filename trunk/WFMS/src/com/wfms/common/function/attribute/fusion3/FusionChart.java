package com.wfms.common.function.attribute.fusion3;

import java.util.List;

public class FusionChart {
	
	private Chart chart;
	
	private List<CategoryUnit> categories;
	
	private List<DataSet> dataset;

	private List<Data> data;
	
	private List<Data> categoryData;
	
	
	public List<Data> getCategoryData() {
		return categoryData;
	}

	public void setCategoryData(List<Data> categoryData) {
		this.categoryData = categoryData;
	}

	public Chart getChart() {
		return chart;
	}

	public void setChart(Chart chart) {
		this.chart = chart;
	}

	public List<CategoryUnit> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryUnit> categories) {
		this.categories = categories;
	}

	public List<DataSet> getDataset() {
		return dataset;
	}

	public void setDataset(List<DataSet> dataset) {
		this.dataset = dataset;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}



}
