package com.wfms.webapp.common.datastat.attribute.v3;

public class Chart {
	/**
	 * 
	 */
	private String caption;// 标题
	private String subCaption;// 图标副标题
	private String xaxisname;// X轴描述
	private String yaxisname;// Y轴描述

	/**
	 * 功能特性
	 */
	private String palette;// 1-5,5种预先定义好的颜色盘
	private String animation;// 是否有动态效果，0：无，1：有
	private String showvalues = "0";// 0/1 是否显示每个图上数值

	/**
	 * 数字格式
	 */
	private String formatnumberscale = "0";// 是否显示成K\M的形式，如1043，formatNumberScale=1：1.04K
											// formatNumberScale=1：1043
	private String numberprefix = "";// 数字前缀,如$
	private String numberSuffix = "";// 数字后缀

	/**
	 * 分隔线和网格
	 */
	private String numdivlines;// >0 水平分隔线数目

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getSubCaption() {
		return subCaption;
	}

	public void setSubCaption(String subCaption) {
		this.subCaption = subCaption;
	}

	public String getXaxisname() {
		return xaxisname;
	}

	public void setXaxisname(String xaxisname) {
		this.xaxisname = xaxisname;
	}

	public String getYaxisname() {
		return yaxisname;
	}

	public void setYaxisname(String yaxisname) {
		this.yaxisname = yaxisname;
	}

	public String getPalette() {
		return palette;
	}

	public void setPalette(String palette) {
		this.palette = palette;
	}

	public String getAnimation() {
		return animation;
	}

	public void setAnimation(String animation) {
		this.animation = animation;
	}

	public String getShowvalues() {
		return showvalues;
	}

	public void setShowvalues(String showvalues) {
		this.showvalues = showvalues;
	}

	public String getFormatnumberscale() {
		return formatnumberscale;
	}

	public void setFormatnumberscale(String formatnumberscale) {
		this.formatnumberscale = formatnumberscale;
	}

	public String getNumberprefix() {
		return numberprefix;
	}

	public void setNumberprefix(String numberprefix) {
		this.numberprefix = numberprefix;
	}

	public String getNumberSuffix() {
		return numberSuffix;
	}

	public void setNumberSuffix(String numberSuffix) {
		this.numberSuffix = numberSuffix;
	}

	public String getNumdivlines() {
		return numdivlines;
	}

	public void setNumdivlines(String numdivlines) {
		this.numdivlines = numdivlines;
	}

}
