package com.wfms.webapp.common.datastat.attribute;



public class FusionChart {
	
	private String caption;//一行标题
	private String subCaption = "";//二行标题
	private String xAxisName;//横坐标图示
	private String yAxisName;//纵坐标图示
	private String rotateYAxisName ="1";
	private String showValues = "1";//是否在图表显示对应的数据值，默认为1(True)
	private String decimalPrecision ="0";
	private String showNames = "1";
	private int baseFontSize=12;
	private int outCnvBaseFontSiz=20;
	private String numberSuffix ="个";//计量单位
	private int pieSliceDepth= 1;//
	private int formatNumberScale=0;
	
	private StatResultSetBean rsBean;
	
	private FusionUnit graph;

	

	public FusionUnit getGraph() {
		return graph;
	}

	public void setGraph(FusionUnit graph) {
		this.graph = graph;
	}

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

	public String getXAxisName() {
		return xAxisName;
	}

	public void setXAxisName(String axisName) {
		xAxisName = axisName;
	}

	public String getYAxisName() {
		return yAxisName;
	}

	public void setYAxisName(String axisName) {
		yAxisName = axisName;
	}

	public String getRotateYAxisName() {
		return rotateYAxisName;
	}

	public void setRotateYAxisName(String rotateYAxisName) {
		this.rotateYAxisName = rotateYAxisName;
	}

	public String getShowValues() {
		return showValues;
	}

	public void setShowValues(String showValues) {
		this.showValues = showValues;
	}

	public String getDecimalPrecision() {
		return decimalPrecision;
	}

	public void setDecimalPrecision(String decimalPrecision) {
		this.decimalPrecision = decimalPrecision;
	}

	public String getShowNames() {
		return showNames;
	}

	public void setShowNames(String showNames) {
		this.showNames = showNames;
	}

	public int getBaseFontSize() {
		return baseFontSize;
	}

	public void setBaseFontSize(int baseFontSize) {
		this.baseFontSize = baseFontSize;
	}

	public int getOutCnvBaseFontSiz() {
		return outCnvBaseFontSiz;
	}

	public void setOutCnvBaseFontSiz(int outCnvBaseFontSiz) {
		this.outCnvBaseFontSiz = outCnvBaseFontSiz;
	}

	public String getNumberSuffix() {
		return numberSuffix;
	}

	public void setNumberSuffix(String numberSuffix) {
		this.numberSuffix = numberSuffix;
	}

	public int getPieSliceDepth() {
		return pieSliceDepth;
	}

	public void setPieSliceDepth(int pieSliceDepth) {
		this.pieSliceDepth = pieSliceDepth;
	}

	public int getFormatNumberScale() {
		return formatNumberScale;
	}

	public void setFormatNumberScale(int formatNumberScale) {
		this.formatNumberScale = formatNumberScale;
	}

	public StatResultSetBean getRsBean() {
		return rsBean;
	}

	public void setRsBean(StatResultSetBean rsBean) {
		this.rsBean = rsBean;
	}
	
}
