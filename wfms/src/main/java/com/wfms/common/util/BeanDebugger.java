package com.wfms.common.util;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class BeanDebugger {

	public static void dump(Object bean) {
		java.beans.PropertyDescriptor[] descriptors = getAvailablePropertyDescriptors(bean);

		for (int i = 0; descriptors != null && i < descriptors.length; i++) {
			java.lang.reflect.Method readMethod = descriptors[i]
					.getReadMethod();

			try {
				Object value = readMethod.invoke(bean, null);
				System.out.println("[" + bean.getClass().getName() + "]."
						+ descriptors[i].getName() + "("
						+ descriptors[i].getPropertyType().getName() + ") = "
						+ value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static java.beans.PropertyDescriptor[] getAvailablePropertyDescriptors(
			Object bean) {
		try {
			// 从 Bean 中解析属性信息并查找相关的 write 方法
			java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(bean
					.getClass());
			if (info != null) {
				java.beans.PropertyDescriptor pd[] = info
						.getPropertyDescriptors();
				Vector columns = new Vector();

				for (int i = 0; i < pd.length; i++) {
					String fieldName = pd[i].getName();

					if (fieldName != null && !fieldName.equals("class")) {
						columns.add(pd[i]);
					}
				}

				java.beans.PropertyDescriptor[] arrays = new java.beans.PropertyDescriptor[columns
						.size()];

				for (int j = 0; j < columns.size(); j++) {
					arrays[j] = (PropertyDescriptor) columns.get(j);
				}

				return arrays;
			}
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
		return null;
	}

	public static Map<String, String> getPropertyDiffrence(Object original,
			Object dest) {
		Map<String, String> diffMap = new HashMap<String, String>();
		java.beans.PropertyDescriptor[] descriptors = getAvailablePropertyDescriptors(original);

		if (original.getClass().equals(dest.getClass())) {
			for (int i = 0; descriptors != null && i < descriptors.length; i++) {
				java.lang.reflect.Method readMethod = descriptors[i]
						.getReadMethod();
				try {
					if (!descriptors[i].getPropertyType().isPrimitive()
							&&(descriptors[i].getPropertyType().getPackage().getName().equals("java.lang")
		            		||descriptors[i].getPropertyType().getPackage().getName().equals("java.util"))) {
						Object sourceValue = readMethod.invoke(original, null);
						Object destValue = readMethod.invoke(dest, null);
						if (sourceValue!=null && !sourceValue.equals(destValue)) {
							diffMap.put(descriptors[i].getName(), String
									.valueOf(destValue));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return diffMap;
		}
		return new HashMap<String, String>(0);
	}

}