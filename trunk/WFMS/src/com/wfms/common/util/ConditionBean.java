/**
 *******************************************************************************
 * 文件名：ConditionUtil.java
 *
 * 描述�?
 * 
 * 创建日期：Mar 1, 2010 6:15:48 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.common.util;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：ConditionUtil
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 1, 2010 6:15:48 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see ConditionBean
 * @version 1.0
 *
 */
public class ConditionBean {
	private Rule rule;//比较规则
	private String column;//比较字段
	private String value;//比较�?
	
	public ConditionBean(String column,String value,Rule rule){
		this.column = column;
		this.value = value;
		this.rule = rule;
	}
	public ConditionBean(String column,Rule rule){
		this.column = column;
		this.rule = rule;
	}
	
	
	public Rule getRule() {
		return rule;
	}
	public void setRule(Rule rule) {
		this.rule = rule;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
