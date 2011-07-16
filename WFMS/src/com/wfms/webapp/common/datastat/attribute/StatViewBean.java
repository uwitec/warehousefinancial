package com.wfms.webapp.common.datastat.attribute;

import com.wfms.common.entity.AjaxMsg;

public class StatViewBean {

	private String pieColumnReportURI;
	private String pieRowReportURI;
	private String barReportURI;
	private AjaxMsg ajaxMsg = new AjaxMsg();
	private FusionChart fusionChart;
	private com.wfms.webapp.common.datastat.attribute.v3.FusionChart v3FusionChart;
	
	public com.wfms.webapp.common.datastat.attribute.v3.FusionChart getV3FusionChart() {
		return v3FusionChart;
	}
	public void setV3FusionChart(
			com.wfms.webapp.common.datastat.attribute.v3.FusionChart fusionChart) {
		v3FusionChart = fusionChart;
	}
	public FusionChart getFusionChart() {
		return fusionChart;
	}
	public void setFusionChart(FusionChart fusionChart) {
		this.fusionChart = fusionChart;
	}
	public String getPieColumnReportURI() {
		return pieColumnReportURI;
	}
	public void setPieColumnReportURI(String pieColumnReportURI) {
		this.pieColumnReportURI = pieColumnReportURI;
	}
	public String getPieRowReportURI() {
		return pieRowReportURI;
	}
	public void setPieRowReportURI(String pieRowReportURI) {
		this.pieRowReportURI = pieRowReportURI;
	}
	public String getBarReportURI() {
		return barReportURI;
	}
	public void setBarReportURI(String barReportURI) {
		this.barReportURI = barReportURI;
	}
	public AjaxMsg getAjaxMsg() {
		return ajaxMsg;
	}
	public void setAjaxMsg(AjaxMsg ajaxMsg) {
		this.ajaxMsg = ajaxMsg;
	}
	
	
}
