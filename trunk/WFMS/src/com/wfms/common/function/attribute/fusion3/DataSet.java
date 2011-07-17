package com.wfms.common.function.attribute.fusion3;

import java.util.List;

public class DataSet {
	/**
	 * 分组名称
	 */
	private String seriesname;
	
	/**
	 * 分组数据
	 */
	private List<Data> data;
	
	/**
	 * 分组颜色
	 */
	private String color;

	public String getSeriesname() {
		return seriesname;
	}

	public void setSeriesname(String seriesname) {
		this.seriesname = seriesname;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
}
