/**
 *******************************************************************************
 * 文件名：BaseGenerate.java
 *
 * 描述�?
 * 
 * 创建日期：Mar 20, 2010 10:41:22 AM
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
 *  <dd> 类名称：BaseGenerate
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 20, 2010 10:41:22 AM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see BaseGenerate
 * @version 1.0
 *
 */
public class BaseGenerate {

	/**
	 * 
	 * <dl>
	 * <b>方法�?:generateXnByNj</b>
	 * <dd>方法作用：根据年级和学制生成学年数组
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param nj
	 * <dd>@param xz
	 * <dd>@return
	 * </dl>
	 */
	public static String[] generateXnByNj(int nj,int xz)
	{
		String[] xns = new String[xz];
		for(int i=0;i<xz;i++,nj++){
			xns[i] = nj+"-"+(nj+1);
		}
		return xns;
	}
	
}
