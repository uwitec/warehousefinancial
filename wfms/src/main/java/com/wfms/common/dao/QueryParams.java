package com.wfms.common.dao;

public class QueryParams {
	private String queryString;
	private Object[] queryParams;
	public QueryParams(){}
	public QueryParams(String queryString,Object[] queryParams){
		this.queryParams = queryParams;
		this.queryString = queryString;
	}
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public Object[] getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(Object[] queryParams) {
		this.queryParams = queryParams;
	}
}
