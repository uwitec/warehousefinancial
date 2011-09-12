package com.wfms.common.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Page {
	private int start;
	private int limit = 20;
	private int total;
	private List<Condition> conditions = new ArrayList<Condition>(5);
	private List<?> results;
	private String orderByColumn;
	private String orderByRule;

	/**
	 * 是否在Creteria创建完成后，直接抓取总数并设置page的totalCount
	 */
	private boolean readTotalCount = true;
	
	/**
	 * 属性规则Map
	 */
	private Map<String, Rule> ruleConfig = new HashMap<String, Rule>(5);
	
	public Page() {
	}

	public Page(int start, int limit) {
		this.start = start;
		this.limit = limit;
	}

	public Page(int start, int limit, List<Condition> conditions) {
		this.start = start;
		this.limit = limit;
		this.conditions = conditions;
	}

	public void initCondition() {
		if (conditions == null) {
			conditions = new ArrayList<Condition>(5);
		}
	}

	public void addCondition(String name, Object value) {
		addCondition(name, value, Rule.ALLLIKE);
	}

	public void addCondition(String name, Object value, Rule rule) {
		this.initCondition();
		if (value instanceof Collection) {
			Collection<?> list = (Collection<?>) value;
			if (list.size() != 0) {
				conditions.add(new Condition(name, value, rule));
			}
		} else {
			conditions.add(new Condition(name, value, rule));
		}
	}

	public void addCondition(int index, String name, Object value) {
		addCondition(index, name, value, Rule.ALLLIKE);
	}

	public void addCondition(int index, String name, Object value, Rule rule) {
		this.initCondition();
		conditions.add(index, new Condition(name, value, rule));
	}

	public int getStart() {
		return start;
	}

	public void setStart(int strat) {
		this.start = strat;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	public List<?> getResults() {
		return results;
	}

	public void setResults(List<?> results) {
		this.results = results;
	}

	public int getTotalPage() {
		if (this.getLimit() == 0)
			return 1;

		return (this.total % this.limit == 0) ? (this.total / this.limit)
				: (this.total / this.limit + 1);
	}

	public int getCurrentPage() {
		if (this.getLimit() == 0)
			return 1;
		return this.start / this.limit + 1;
	}

	public String getOrderByColumn() {
		return orderByColumn;
	}

	public void setOrderByColumn(String orderByColumn) {
		this.orderByColumn = orderByColumn;
	}

	public String getOrderByRule() {
		return orderByRule;
	}

	public void setOrderByRule(String orderByRule) {
		this.orderByRule = orderByRule;
	}

	public boolean isReadTotalCount() {
		return readTotalCount;
	}

	public void setReadTotalCount(boolean readTotalCount) {
		this.readTotalCount = readTotalCount;
	}

	public Map<String, Rule> getRuleConfig() {
		return ruleConfig;
	}

	public void setRuleConfig(Map<String, Rule> ruleConfig) {
		this.ruleConfig = ruleConfig;
	}
	
	
}
