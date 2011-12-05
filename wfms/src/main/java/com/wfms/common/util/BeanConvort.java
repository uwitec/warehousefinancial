package com.wfms.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author CYC
 * @see BeanConvort
 * @version 1.0
 * 
 */
public class BeanConvort {

	private static final String GET_PREFIX = "get";
	private static final String SET_PREFIX = "set";
	private static final String ID_ATTRIBUTE= "id";
	
	static Object instantiate(Class sibling, String className)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		ClassLoader cl = sibling.getClassLoader();
		if (cl != null) {
			try {
				Class cls = cl.loadClass(className);
				return cls.newInstance();
			} catch (Exception ex) {
			}
		}
		try {
			cl = ClassLoader.getSystemClassLoader();
			if (cl != null) {
				Class cls = cl.loadClass(className);
				return cls.newInstance();
			}
		} catch (Exception ex) {
		}
		cl = Thread.currentThread().getContextClassLoader();
		Class cls = cl.loadClass(className);
		return cls.newInstance();
	}

	private static Object convertInBound(Class<?> paramType) {
		Object instance = null;
		try {
			instance = instantiate(paramType, paramType.getName());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instance;
	}

	public static List boundToArray(Class<?> paramType, List list)
			throws Exception {
		List instanceList = new ArrayList(list.size());
		Object instance = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof Map) {
				Map paramMap = (Map) list.get(i);
				instance = boundToObject(paramType,null, paramMap);
				instanceList.add(instance);
			}
		}
		return instanceList;
	}

	private static Field[] getAllFields(Object bean) {
		Set allFields = new HashSet();
		Class clazz = bean.getClass();
		while (clazz != Object.class) {
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				allFields.add(fields[i]);
			}
			clazz = clazz.getSuperclass();
		}
		return (Field[]) allFields.toArray(new Field[allFields.size()]);
	}

	private static Map<String, Class> getAllFieldsType(Object bean) {
		Set allFields = new HashSet();
		Map<String, Class> allFiMap = new HashMap<String, Class>();
		Class clazz = bean.getClass();
		while (clazz != Object.class) {
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				String[] shortFields = fields[i].getName().split("\\.");
				String fieldShortName = shortFields[shortFields.length - 1];
				allFiMap.put(fieldShortName, fields[i].getType());
			}
			clazz = clazz.getSuperclass();
		}
		return allFiMap;
	}

	private static Method[] getAllMethods(Object bean) {
		Set allMethods = new HashSet();
		Class clazz = bean.getClass();
		while (clazz != Object.class) {
			Method[] methods = clazz.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				allMethods.add(methods[i]);
			}
			clazz = clazz.getSuperclass();
		}
		return (Method[]) allMethods.toArray(new Method[allMethods.size()]);
	}
	
	private static Map<String, Method> getAllMethodsMap(Object bean) {
		Map<String, Method> allMethodsMap = new HashMap<String, Method>();
		Class clazz = bean.getClass();
		while (clazz != Object.class) {
			Method[] methods = clazz.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				String methodName = methods[i].getName();
				String methodKey = methodName.replace(SET_PREFIX, "");
				String tempKey = methodKey.replaceFirst("\\w+",
						(char) (methodKey.charAt(0) + 32) + "");
				methodKey = tempKey + methodKey.substring(1);
				allMethodsMap.put(methodKey,methods[i]);
			}
			clazz = clazz.getSuperclass();
		}
		return allMethodsMap;
	}

	private static Map<String,Method> getMethodsMapByNameFilter(Object bean,String filterStr)
	{
		Map<String, Method> methodsMap = new HashMap<String, Method>();
		Class clazz = bean.getClass();
		while (clazz != Object.class) {
			Method[] methods = clazz.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				String methodName = methods[i].getName();
				if(methodName.indexOf(filterStr)!=-1)
				{
					String methodKey = methodName.replace(filterStr, "");
					String tempKey = methodKey.replaceFirst("\\w+",
							(char) (methodKey.charAt(0) + 32) + "");
					methodKey = tempKey + methodKey.substring(1);
					methodsMap.put(methodKey,methods[i]);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return methodsMap;
	}
	
	private static Method[] getMethodsByNameFilter(Object bean, String filterStr) {
		Set allMethods = new HashSet();
		Class clazz = bean.getClass();
		while (clazz != Object.class) {
			Method[] methods = clazz.getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				if (methods[i].getName().indexOf(filterStr) != -1) {
					allMethods.add(methods[i]);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return (Method[]) allMethods.toArray(new Method[allMethods.size()]);
	}

	private static Method[] getAllSetMethods(Object bean) {
		return getMethodsByNameFilter(bean, SET_PREFIX);
	}

	private static Map<String, Method> getSetMethodsMap(Object bean) {
		Map<String,Method> setMethodsMap=getMethodsMapByNameFilter(bean,SET_PREFIX);
		return setMethodsMap;
	}
	private static Map<String, Method> getGetMethodsMap(Object bean) {
		Map<String,Method> setMethodsMap=getMethodsMapByNameFilter(bean,GET_PREFIX);
		return setMethodsMap;
	}

	public static Object boundToObject(Class<?> cls,Object obj, Map paramVals)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Object outObject = obj;
		if(outObject == null)
		{
			outObject = convertInBound(cls);
		}
		Map<String, Class> fieldMap = getAllFieldsType(outObject);
		Map<String, Method> setMethodsMap = getSetMethodsMap(outObject);
		Map<String, Method> getMethodsMap = getGetMethodsMap(outObject);
		Set<Map.Entry<?, ?>> entrySets = paramVals.entrySet();
		for (Map.Entry<?,?> entry : entrySets) {
			String paramValKey = entry.getKey().toString();
			if (paramValKey.indexOf(".") != -1) {
				String key = entry.getKey().toString();

				String[] keys = key.split("\\.");
				int keyInd = key.indexOf(".");

				Map<String,Object> subParamVals = new HashMap<String,Object>(1);
					subParamVals.put(key.substring(keyInd + 1), entry
							.getValue());
					Object subObject = null;
					if(getMethodsMap.get(keys[0]) !=null)
					{
						subObject = getMethodInvoke(outObject, getMethodsMap.get(keys[0]));
						//如果属�?�为空，则进行实例化,否则继续为属性对象设置属性�??
						if(subObject == null)
						{
							subObject = boundToObject(fieldMap
									.get(keys[0]),null, subParamVals);
						}
						else
						{
							boundToObject(subObject.getClass(),subObject,subParamVals);
						}
					}
					methodInvoke(outObject, setMethodsMap.get(keys[0]),
							subObject, fieldMap.get(keys[0]));
			}
			//如果未为Class传入相关参数，则直接实例化Class
			else {
				methodInvoke(outObject, setMethodsMap.get(paramValKey), entry
						.getValue(), fieldMap.get(paramValKey));
			}
		}
		return outObject;
	}

	private static void methodInvoke(Object bean, Method method, Object arg,
			Class<?> fieldCls) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		if (method !=null && method.getParameterTypes().length == 1) {
			if (fieldCls == int.class || fieldCls == Integer.class) {
				method.invoke(bean, new Object[] { "".equals(arg)?0:Integer.valueOf(String
						.valueOf(arg)) });
			} else if (fieldCls == float.class || fieldCls == Float.class) {
				method.invoke(bean, new Object[] {"".equals(arg)?0: Float.valueOf(String
						.valueOf(arg)) });
			} else if (fieldCls == long.class || fieldCls == Long.class) {
				method.invoke(bean, new Object[] { "".equals(arg)?0:Long.valueOf(String
						.valueOf(arg)) });
			} else if (fieldCls == double.class || fieldCls == Double.class) {
				method.invoke(bean, new Object[] { "".equals(arg)?0:Double.valueOf(String
						.valueOf(arg)) });
			}
			else if(fieldCls == BigDecimal.class)
			{
				method.invoke(bean, new Object[] { BigDecimal.valueOf(Double.valueOf(String.valueOf(arg)))});
			}
			else if (fieldCls == String.class) {
				method.invoke(bean, new Object[] { "null".equals(String.valueOf(arg))?"":String.valueOf(arg) });
			}
			else if(fieldCls == Boolean.class || fieldCls == boolean.class)
			{
				method.invoke(bean, new Object[]{"null".equals(String.valueOf(arg))?false:Boolean.valueOf((String)arg)});
			}
			else {
				method.invoke(bean, new Object[] { arg });
			}
		}
	}
	
	private static Object getMethodInvoke(Object bean, Method method) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
			if(method.getParameterTypes().length==0)
			{
				return method.invoke(bean, new Object[]{});
			}
			return null;
		}
	
	private static void preventNullAttr(Class<?> rootCls,Object obj) throws IllegalArgumentException, IllegalAccessException{   
        Class<?> objClass = obj.getClass(); 
        for (int i = 0; i < objClass.getDeclaredFields().length; i++) {   
            boolean accessible = false;   
            Field[] fields = objClass.getDeclaredFields();
            for(Field field:fields)
            {
	            accessible = field.isAccessible();  
	            Class<?> fieldType = field.getType();   
	            Object fieldValue = null;   
	            String fieldName = field.getName();
	            if (accessible == false);{   
	                field.setAccessible(true);  
	            }   
	            //如果为id属�?�，则直接设置�?�为-1
	            if(ID_ATTRIBUTE.equals(fieldName))
        		{
	            		if(field.get(obj).equals(0)
	            				&& obj.getClass()!=rootCls)
	            		{
	            			field.set(obj, -1);
	            		}
        		}
	            //如果是基本类型，跳过  
	            // 如果是lang包或者util包下面的类，可以跳过   
	            // 这里不严谨，不过对于实体而言可以接受�?             
	            // 因为实体的属性一般不是这两个包下面的就是自定义的�?   
	            if (fieldType.isPrimitive()
	            		||fieldType.getPackage().getName().equals("java.lang")
	            		||fieldType.getPackage().getName().equals("java.util")) {   
	            	//恢复可访问属�?
	            	field.setAccessible(accessible);
	            	continue;   
	            }   
	            
                fieldValue = field.get(obj);;   
	            //不为空，则直接�?�归属�?�，并设置id设置�?-1
	            if(fieldValue != null)
	            {
	            	//preventNullAttr(null,fieldValue);
	            }
	            //为空则先实例化，然后再设置id属�?�为-1
	            else{   
                    // 实例化实体属�?   
                	Object newValue = convertInBound(fieldType);    
                	//设置实体属�?��??
                	field.set(obj,newValue);
                    //防止新建的这个实例的属�?�如果是自定义类型的话可能存在的null值，递归调用   
                    preventNullAttr(rootCls,newValue);   
                    break;
	            }   
	            field.setAccessible(accessible);
            }
        }   
    }  
	
	public static void preventNullAttr(List<Class<?>> filterCls,Object obj) throws IllegalArgumentException, IllegalAccessException{   
        Class<?> objClass = obj.getClass(); 
        for (int i = 0; i < objClass.getDeclaredFields().length; i++) {   
            boolean accessible = false;   
            Field[] fields = objClass.getDeclaredFields();
            for(Field field:fields)
            {
	            accessible = field.isAccessible();  
	            Class<?> fieldType = field.getType();   
	            Object fieldValue = null;   
	            String fieldName = field.getName();
	            if (accessible == false);{   
	                field.setAccessible(true);  
	            }   
	            //如果为id属�?�，则直接设置�?�为-1
	            if(ID_ATTRIBUTE.equals(fieldName))
        		{
	            		if(field.get(obj).equals(0)
	            				&& !filterCls.contains(obj.getClass()))
	            		{
	            			field.set(obj, -1);
	            		}
        		}
	            //如果是基本类型，跳过  
	            // 如果是lang包或者util包下面的类，可以跳过   
	            // 这里不严谨，不过对于实体而言可以接受�?             
	            // 因为实体的属性一般不是这两个包下面的就是自定义的�?   
	            if (fieldType.isPrimitive()
	            		||fieldType.getPackage().getName().equals("java.lang")
	            		||fieldType.getPackage().getName().equals("java.util")) {   
	            	//恢复可访问属�?
	            	field.setAccessible(accessible);
	            	continue;   
	            }   
	            
                fieldValue = field.get(obj);;   
	            //不为空，则直接�?�归属�?�，并设置id设置�?-1
	            if(fieldValue != null)
	            {
	            	//preventNullAttr(null,fieldValue);
	            }
	            //为空则先实例化，然后再设置id属�?�为-1
	            else{   
                    // 实例化实体属�?   
                	Object newValue = convertInBound(fieldType);    
                	//设置实体属�?��??
                	field.set(obj,newValue);
                    //防止新建的这个实例的属�?�如果是自定义类型的话可能存在的null值，递归调用   
                    preventNullAttr(filterCls,newValue);   
                    break;
	            }   
	            field.setAccessible(accessible);
            }
        }   
    }  
	
	public static Object boundToObject(Class<?> cls,Object obj, Map<?,?> paramVals,boolean preventNull)
	throws IllegalArgumentException, IllegalAccessException,
	InvocationTargetException {
		obj = boundToObject(cls, obj, paramVals);
		if(preventNull)
		{
			preventNullAttr(obj.getClass(),obj);
		}
		return obj;
	}
	
	public static List boundToArray(Class<?> paramType, List<?> list,boolean preventNull)
	throws Exception {
		List instanceList = new ArrayList(list.size());
		Object instance = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof Map) {
				Map paramMap = (Map) list.get(i);
				instance = boundToObject(paramType,null, paramMap,preventNull);
				instanceList.add(instance);
			}
		}
		return instanceList;
	}

	public static void main(String[] args) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		Map lsJson = new HashMap(1);
		lsJson.put("jydw.bz","暂无备注" );
		//lsJson.put("jydw.id","11");
		Map jwJson=new HashMap(1);
		//jwJson.put("zyjxjh.zyxx.zydm", "10131414");
//		/System.out.println(ls.getJydw().getId());
		//System.out.println("bm id is"+ls.getRy().getBm().getId());
		//System.out.println(ls.getRy().getBm().getBmms());
		//System.out.println(ls.getRy().getBm().getCjsj());
		//System.out.println(ls.getJydw().getBz());
		/*JxrwEntity jxrw=(JxrwEntity) boundToObject(JxrwEntity.class,null, jwJson);
		System.out.println(jxrw.getZyjxjh().getZyxx().getZydm());*/
	}
}
