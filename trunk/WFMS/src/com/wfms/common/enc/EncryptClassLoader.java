package com.wfms.common.enc;


public class EncryptClassLoader extends ClassLoader {

	public Class loadClass( String name, boolean resolve )
	
    throws ClassNotFoundException {
  
		// 我们要创建的Class对象
		Class clasz = null;

		// 必需的步�?1：如果类已经在系统缓冲之中，
		// 我们不必再次装入�?
		clasz = findLoadedClass(name);

		if (clasz != null)
			return clasz;

		// 下面是定制部�?
		byte classData[] = null/* 通过某种方法获取字节码数�? */;
		if (classData != null) {
			// 成功读取字节码数据，现在把它转换成一个Class对象
			clasz = defineClass(name, classData, 0, classData.length);
		}

		// 必需的步�?2：如果上面没有成功，
		// 我们尝试用默认的ClassLoader装入�?
		if (clasz == null)
			clasz = findSystemClass(name);

		// 必需的步�?3：如有必要，则装入相关的�?
		if (resolve && clasz != null)
			resolveClass(clasz);

		// 把类返回给调用�??
		return clasz;
	}
}
