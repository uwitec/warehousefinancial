package com.wfms.common.entity;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Delayed;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wfms.common.util.StringUtil;

import net.sf.json.util.PropertyFilter;

public class SimpleFieldProcessor implements PropertyFilter {

	Log log = LogFactory.getLog(this.getClass());

	/**
	 * 忽略的属性名称
	 */
	private String[] fields;

	/**
	 * 是否忽略集合
	 */
	private boolean ignoreColl = false;

	/**
	 * 空参构造方法<br/> 默认不忽略集合
	 */
	public SimpleFieldProcessor() {
		// empty
	}

	/**
	 * 构造方法
	 * 
	 * @param fields
	 *            忽略属性名称数组
	 */
	public SimpleFieldProcessor(String[] fields) {
		this.fields = fields;
	}

	/**
	 * 构造方法
	 * 
	 * @param ignoreColl
	 *            是否忽略集合
	 * @param fields
	 *            忽略属性名称数组
	 */
	public SimpleFieldProcessor(boolean ignoreColl, String[] fields) {
		this.fields = fields;
		this.ignoreColl = ignoreColl;
	}

	/**
	 * 构造方法
	 * 
	 * @param ignoreColl
	 *            是否忽略集合
	 */
	public SimpleFieldProcessor(boolean ignoreColl) {
		this.ignoreColl = ignoreColl;
	}

	public boolean apply(Object source, String name, Object value) {
		Field declaredField = null;
		try {
			declaredField = source.getClass().getDeclaringClass().getDeclaredField(name);
		} catch (Exception e) {
			log.equals("没有找到属性" + name);
			e.printStackTrace();
			return true;
		}
		if (declaredField == null || declaredField.getDeclaringClass() == null
				|| declaredField.getDeclaringClass() != source.getClass()) {
			return true;
		}
		// 忽略集合
		if (declaredField != null) {
			if (ignoreColl) {
				if (declaredField.getType() == Collection.class
						|| declaredField.getType() == Set.class
						|| declaredField.getType() == Map.class
						|| declaredField.getType() == List.class) {
					return true;
				}
			}
		}

		// 忽略设定的属性
		if (fields != null && fields.length > 0) {
			if (StringUtil.hasInArray(fields, name)) {
				return true;
			}
		}

		return false;
	}

	public String[] getFields() {
		return fields;
	}

	/**
	 * 设置忽略的属性
	 * 
	 * @param fields
	 */
	public void setFields(String[] fields) {
		this.fields = fields;
	}

	public boolean isIgnoreColl() {
		return ignoreColl;
	}

	/**
	 * 设置是否忽略集合类
	 * 
	 * @param ignoreColl
	 */
	public void setIgnoreColl(boolean ignoreColl) {
		this.ignoreColl = ignoreColl;
	}
}
