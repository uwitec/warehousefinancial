package com.wfms.common.entity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonMethod;

import net.sf.json.JSONObject;

import com.wfms.common.util.ConditionBean;
import com.wfms.common.util.JSONUtil;
import com.wfms.common.util.StringUtil;

@JsonAutoDetect(value=JsonMethod.GETTER,getterVisibility=JsonAutoDetect.Visibility.PUBLIC_ONLY)
@SuppressWarnings("unchecked")
public class Page {
	private int totalCount = 0;//总记录数
	private int start = 0;//记录�?始数
	private int limit = 15;//记录结束
	private List<ConditionBean> conditionList=new ArrayList<ConditionBean>();//查询条件
	private String conditions;
	private Object queryParams;
	private List dataList;//返回数据
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public List getDataList() {
		return dataList;
	}
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	@JsonIgnore
	public List<ConditionBean> getConditionList() {
		return conditionList;
	}
	public void setConditionList(List<ConditionBean> conditionList) {
		this.conditionList = conditionList;
	}
	
	public void formatCondList(){
		// 查询条件
		JSONObject conObj = null;
		if (!StringUtil.isNullOrEmpty(conditions)) {
			try {
				conditions = java.net.URLDecoder.decode(conditions, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.out.println("条件字符串编码错误");
				e.printStackTrace();
			}
			conObj = JSONObject.fromObject(conditions);
		}
		this.setConditionList(JSONUtil.formatConditionList(conObj));
	}
	
	@JsonIgnore
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public Object getQueryParams() {
		return queryParams;
	}
	public void setQueryParams(Object queryParams) {
		this.queryParams = queryParams;
	}
}
