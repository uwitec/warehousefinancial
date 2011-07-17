package com.wfms.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class ReflectionUtils {
	private static Logger logger = LoggerFactory
			.getLogger(ReflectionUtils.class);
	@SuppressWarnings("unchecked")
	private static Field getDeclaredField(Object object, String fieldName) {
		Assert.notNull(object, "object不能为空");
		Assert.hasText(fieldName, "fieldName");
		for (Class clazz = object.getClass(); clazz != Object.class; clazz = clazz
				.getSuperclass()) {
			try {
				return clazz.getDeclaredField(fieldName);
			} catch (NoSuchFieldException localNoSuchFieldException) {
			}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	private static Method getDeclaredMethod(Object object, String methodName,
			Class<?>[] parameterTypes) {
		Assert.notNull(object, "object不能为空");
		Assert.hasText(methodName, "methodName");

		for (Class clazz = object.getClass(); clazz != Object.class; clazz = clazz
				.getSuperclass()) {
			try {
				return clazz.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException localNoSuchMethodException) {
			}
		}
		return null;
	}

	public static Object getFieldValue(Object object, String fieldName) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			return null;
			// throw new IllegalArgumentException("Could not find field [" +
			// fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		if (result == null) {
			result = field.getClass();
		}
		return result;
	}

	public static void setFieldValue(Object object, String fieldName,
			Object fieldValue) {
		Field field = getDeclaredField(object, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field ["
					+ fieldName + "] on target [" + object + "]");
		}

		makeAccessible(field);
		try {
			field.set(object, fieldValue);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常:{}", e.getMessage());
		}
	}

	public static Object invokeMethod(Object object, String methodName,
			Class<?>[] parameterTypes, Object[] parameterValues) {
		Method method = getDeclaredMethod(object, methodName, parameterTypes);

		if (method == null) {
			throw new IllegalArgumentException("Could not find method ["
					+ methodName + "] on target [" + object + "]");
		}

		method.setAccessible(true);
		try {
			return method.invoke(object, parameterValues);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	public static Object invokeGetterMethod(Object object, String fieldName) {
		String getterMethodName = "get" + StringUtils.capitalize(fieldName);
		return invokeMethod(object, getterMethodName, new Class[0],
				new Object[0]);
	}
	@SuppressWarnings("unchecked")
	public static void invokeSetterMethod(Object target, String fieldName,
			Class<?> parameterType, Object parameterValue) {
		String setterMethodName = "set" + StringUtils.capitalize(fieldName);
		Class type = (parameterType != null) ? parameterType : parameterValue
				.getClass();
		invokeMethod(target, setterMethodName, new Class[] { type },
				new Object[] { parameterValue });
	}

	public static void invokeSetterMethod(Object target, String propertyName,
			Object parameterValue) {
		invokeSetterMethod(target, propertyName, null, parameterValue);
	}

	private static void makeAccessible(Field field) {
		if ((Modifier.isPublic(field.getModifiers()))
				&& (Modifier.isPublic(field.getDeclaringClass().getModifiers())))
			return;
		field.setAccessible(true);
	}
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getSuperClassGenricType(Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}
	@SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(Class clazz, int index) {
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getSimpleName()
					+ "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if ((index >= params.length) || (index < 0)) {
			logger.warn("Index: " + index + ", Size of "
					+ clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getSimpleName()
					+ " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	public static RuntimeException convertReflectionExceptionToUnchecked(
			Exception e) {
		if ((e instanceof IllegalAccessException)
				|| (e instanceof IllegalArgumentException)
				|| (e instanceof NoSuchMethodException)) {
			return new IllegalArgumentException("Reflection Exception.", e);
		}
		if (e instanceof InvocationTargetException) {
			return new RuntimeException("Reflection Exception.",
					((InvocationTargetException) e).getTargetException());
		}
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}
}