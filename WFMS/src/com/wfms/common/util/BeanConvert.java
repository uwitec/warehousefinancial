/**
 *******************************************************************************
 * 文件名：BeanConvert.java
 *
 * 描述�?
 * 
 * 创建日期：Jan 27, 2010 10:50:07 AM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：BeanConvert
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 27, 2010 10:50:07 AM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see BeanConvert
 * @version 1.0
 *
 */
public class BeanConvert {

    private static final String ADD_PREFIX = "add";
    private static final String REMOVE_PREFIX = "remove";
    private static final String GET_PREFIX = "get";
    private static final String SET_PREFIX = "set";
    private static final String IS_PREFIX = "is";
    
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
    
    public static Object convertInBound(Class<?> paramType)
    {
    	Object instance=null;
    	try {
    		instance=instantiate(paramType,paramType.getName());
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
    
    public static List boundToArray(Class<?> paramType,List list) throws Exception
    {
    	List instanceList=new ArrayList(list.size());
    	Object instance=null;
    	for(int i=0;i<list.size();i++)
    	{
    		if(list.get(i) instanceof Map)
    		{
    			Map paramMap=(Map)list.get(i);
    			instance=boundToObject(paramType, paramMap);
    			instanceList.add(instance);
    		}
    	}
    	return instanceList;
    }
    
    public static Object boundToObject(Class<?> paramType,Map paramVals) throws Exception
    {
    	Object instance=null;
    	Set<Map.Entry> entrySets=paramVals.entrySet();
    	Map subMap1=null;
    	String keyName="";
    	Set<String> keyNames= new HashSet<String>(5);
    	Map<String,Map> subMaps=new HashMap<String, Map>(5);
    	for(Map.Entry entry:entrySets)
    	{
    		if(entry.getKey().toString().indexOf(".")!=-1)
    		{
    			String key=entry.getKey().toString();
    		
    			String[] keys=key.split("\\.");
    			int keyLength=keys.length;
    			keyName=keys[0];
    			if(!keyNames.contains(keyName))
    			{
    				keyNames.add(keyName);
    			}
    			subMap1=subMaps.get(keyName);
    			if(subMap1==null)
    			{
    				subMap1=new HashMap(1);
    			}
    			if(keys.length==2)
    			{
    				subMap1.put(keys[keyLength-1], entry.getValue());
    			}
				subMaps.put(keyName, subMap1);
    		}
    	}
    	for(String key:keyNames)
    	{
    		paramVals.put(key,subMaps.get(key));
    	}
    	instance=convertInBound(paramType);
    	Field[] fields=getAllFields(instance);
    	Method[] setMethods=getMethodsByNameFilter(instance,SET_PREFIX);
    	Map<String, Class> fieldTypes=getAllFieldsType(instance);
    	for(int i=0;i<setMethods.length;i++)
    	{
    		String key=setMethods[i].getName();
    		if(key.indexOf(SET_PREFIX)!=-1)
    		{
    			String fieldKey=key.replace(SET_PREFIX,"");
    			String tempKey=fieldKey.replaceFirst("\\w+", (char)(fieldKey.charAt(0)+32)+"");
    			fieldKey=tempKey+fieldKey.substring(1);
    			if(paramVals.get(fieldKey)!=null && !"".equals(paramVals.get(fieldKey))){
    				if(fieldTypes.get(fieldKey)!=null)
    				{
	    				if(fieldTypes.get(fieldKey)==int.class || fieldTypes.get(fieldKey)==Integer.class)
	    				{
	    					setMethods[i].invoke(instance, new Object[]{Integer.valueOf(String.valueOf(paramVals.get(fieldKey)))});
	    				}
	    				else if(fieldTypes.get(fieldKey)==long.class || fieldTypes.get(fieldKey)==Long.class)
	    				{
	    					setMethods[i].invoke(instance, new Object[]{Long.valueOf(String.valueOf(paramVals.get(fieldKey)))});
	    				}
	    				else if(fieldTypes.get(fieldKey)==float.class || fieldTypes.get(fieldKey)==Float.class)
	    				{
	    					setMethods[i].invoke(instance, new Object[]{Float.valueOf(String.valueOf(paramVals.get(fieldKey)))});
	    				}
	    				else if(fieldTypes.get(fieldKey)==double.class || fieldTypes.get(fieldKey)==Double.class)
	    				{
	    					setMethods[i].invoke(instance, new Object[]{Double.valueOf(String.valueOf(paramVals.get(fieldKey)))});
	    				}
	    				else if(fieldTypes.get(fieldKey)==String.class)
		    			{
		    					setMethods[i].invoke(instance, new Object[]{String.valueOf(paramVals.get(fieldKey))});
		    			}
		    			else if(paramVals.get(fieldKey) instanceof Map)
		    			{
		    				Map subMap=(Map)paramVals.get(fieldKey);
		    				Object subObject=null;
		    				
							subObject=fieldTypes.get(fieldKey).newInstance();
	    				
		    				Method[] subSetMethods=getMethodsByNameFilter(subObject,"set");
		    				Map<String,Class> subFieldsType=getAllFieldsType(subObject);
		    				for(int k=0;k<subSetMethods.length;k++)
		    				{
		    					String subKey=subSetMethods[k].getName().replace("set","").toLowerCase();
		    					if(subMap.get(subKey)!=null && subFieldsType.get(subKey)!=null)
		    					{
		    						if(subFieldsType.get(subKey)==int.class || subFieldsType.get(subKey)==Integer.class){
		    							subSetMethods[k].invoke(subObject, new Object[]{Integer.valueOf(String.valueOf(subMap.get(subKey)))});
		    						}else if(subFieldsType.get(subKey)==float.class || subFieldsType.get(subKey)==Float.class){
		    							subSetMethods[k].invoke(subObject, new Object[]{Float.valueOf(String.valueOf(subMap.get(subKey)))});
		    						}else if(subFieldsType.get(subKey)==long.class || subFieldsType.get(subKey)==Long.class){
		    							subSetMethods[k].invoke(subObject, new Object[]{Long.valueOf(String.valueOf(subMap.get(subKey)))});
		    						}else if(subFieldsType.get(subKey)==double.class || subFieldsType.get(subKey)==Double.class){
		    							subSetMethods[k].invoke(subObject, new Object[]{Double.valueOf(String.valueOf(subMap.get(subKey)))});
		    						}
		    						else if(subFieldsType.get(subKey)== String.class)
		    						{
		    							subSetMethods[k].invoke(subObject, new Object[]{String.valueOf(subMap.get(subKey))});
		    						}
		    						else{
		    							subSetMethods[k].invoke(subObject, new Object[]{subMap.get(subKey)});
		    						}
	    						}
		    				}
		    				setMethods[i].invoke(instance, new Object[]{subObject});
		    			}
    				}
    			}
    		}
    	}
    	return instance;
    }
    
    protected static Field[] getAllFields(Object bean)
    {
        Set allFields = new HashSet();

        Class clazz = bean.getClass();

        while (clazz != Object.class)
        {
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++)
            {
                allFields.add(fields[i]);
            }

            clazz = clazz.getSuperclass();
        }

        return (Field[]) allFields.toArray(new Field[allFields.size()]);
    }
    
    protected static Map<String,Class> getAllFieldsType(Object bean)
    {
    	Set allFields = new HashSet();
    	Map<String,Class> allFiMap=new HashMap<String, Class>();
    	Class clazz = bean.getClass();
    	
    	while (clazz != Object.class)
    	{
    		Field[] fields = clazz.getDeclaredFields();
    		for (int i = 0; i < fields.length; i++)
    		{
    			String[] shortFields=fields[i].getName().split("\\.");
    			String fieldShortName=shortFields[shortFields.length-1];
    			allFiMap.put(fieldShortName, fields[i].getType());
    		}
    		
    		clazz = clazz.getSuperclass();
    	}
    	
    	return allFiMap;
    }
    
    protected static Method[] getAllMethods(Object bean)
    {
    	Set allMethods = new HashSet();
    	
    	Class clazz = bean.getClass();
    	
    	while (clazz != Object.class)
    	{
    		Method[] methods = clazz.getDeclaredMethods();
    		for (int i = 0; i < methods.length; i++)
    		{
    			allMethods.add(methods[i]);
    		}
    		
    		clazz = clazz.getSuperclass();
    	}
    	
    	return (Method[]) allMethods.toArray(new Method[allMethods.size()]);
    }
    
    protected static Method[] getMethodsByNameFilter(Object bean,String filterStr)
    {
    	Set allMethods = new HashSet();
    	
    	Class clazz = bean.getClass();
    	
    	while (clazz != Object.class)
    	{
    		Method[] methods = clazz.getDeclaredMethods();
    		for (int i = 0; i < methods.length; i++)
    		{
    			if(methods[i].getName().indexOf(filterStr)!=-1)
    			{
    				allMethods.add(methods[i]);
    			}
			}
    		
    		clazz = clazz.getSuperclass();
    	}
    	return (Method[]) allMethods.toArray(new Method[allMethods.size()]);
    }
}
