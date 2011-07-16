/**
 *******************************************************************************
 * 文件名：BeanUtil.java
 *
 * 描述�?
 * 
 * 创建日期：Apr 19, 2010 4:57:23 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.wfms.common.entity.TreeEntity;



/**
 * <dl>
 * Description
 * <dd> 项目名称：数字化校园信息平台ECMS
 * <dd> 类名称：BeanUtil
 * <dd> 类描述：
 * <dd> 创建人：Administrator
 * <dd> 创建时间：Apr 19, 2010 4:57:23 PM
 * <dd> 修改人：�?
 * <dd> 修改时间：无
 * <dd> 修改备注：无
 * </dl>
 * 
 * @author CYC
 * @see BeanUtil
 * @version 1.0
 * 
 */
public class BeanUtil {

	private static Type dateType = Date.class;
	private static Type stringType = String.class;

	public static void copyProperty(Object sourceObj, Object destObj)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Method[] method1 = sourceObj.getClass().getDeclaredMethods();
		Method[] method2 = destObj.getClass().getDeclaredMethods();
		for (int i = 0; i < method2.length; i++) {
			if (method2[i].getName().indexOf("set") > -1) {
				String fieldName = method2[i].getName();
				fieldName = fieldName.substring(fieldName.indexOf("set") + 3,
						fieldName.length());
				for (int j = 0; j < method1.length; j++) {

					if (method1[j].getName().indexOf("get") > -1
							&& method1[j].getName().indexOf(fieldName) > -1) {
						Object value = method1[j].invoke(sourceObj, null);

						if (method2[i].getParameterTypes().length > 0
								&& !method2[i].getParameterTypes()[0]
										.toString().equals(
												method1[j].getReturnType()
														.toString())) {

							if (method1[j].getReturnType().toString().equals(
									stringType)
									&& method2[i].getParameterTypes()[0]
											.toString().equals(dateType)) {
								value = parseDate(value.toString());
							} else if (method1[j].getReturnType().toString().equals(
									dateType)
									&& method2[i].getParameterTypes()[0]
											.toString().equals(stringType)) {
								value = formatDate((Date) value);
							}

							else {
								value = value.toString();
							}
						}
						Object[] params = { value };
						method2[i].invoke(destObj, params);
						break;
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		
		BeanUtil beanUtil = new BeanUtil();
		TreeEntity tree1=new TreeEntity();
		tree1.setLeaf(true);
		tree1.setQtip("text");
		tree1.setText("parentTree");
		java.util.List<TreeEntity> childTrees=new ArrayList<TreeEntity>(2);
		TreeEntity childTree=new TreeEntity();
		childTree.setText("childTree");
		childTrees.add(childTree);
		tree1.setChildren(childTrees);
		
		TreeEntity tree2=new TreeEntity();
		
		try {
			beanUtil.copyProperty(tree1, tree2);
			System.out.println(tree2.getText());
			System.out.println(tree2.getLeaf());
			if(tree2.getChildren()!=null)
			{
				for(TreeEntity child:tree2.getChildren())
				{
					System.out.println(child.getText());
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static void fillProperties(Class clz,Map propertyVal)
	{
		
	}
	
	public static String formatDate(Date date) {
		String str = "";
		DateFormat format = DateFormat.getDateInstance();
		if (date != null) {
			str = format.format(date);
		}
		return str;
	}

	public static Date parseDate(String str) {
		Date date = null;
		DateFormat format = DateFormat.getDateInstance();
		if (str != null && str.trim().length() > 0) {
			try {
				date = format.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}
}
