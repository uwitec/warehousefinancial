/**
 *******************************************************************************
 * 文件名：ILxdmService.java
 *
 * 描述�?
 * 
 * 创建日期：Jan 26, 2010 12:59:24 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.service.system;
import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.dto.system.LxdmDto;
import com.wfms.model.system.LxdmEntity;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：ILxdmService
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 26, 2010 12:59:24 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see ILxdmglService
 * @version 1.0
 *
 */
public interface ILxdmglService {
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:getSubLxdmBySuperJc</b>
	 * <dd>方法作用：根据父级类型代码简称查询其下的�?有类型代�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param superJc
	 * <dd>@return
	 * </dl>
	 */
	public LxdmDto loadSubLxdmBySupJc(String superJc);
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:loadSubLxdmBySupDm</b>
	 * <dd>方法作用：根据父级代码查询所有其下类型代�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param superDm
	 * <dd>@return
	 * </dl>
	 */
	public LxdmDto loadSubLxdmBySupDm(String superDm);
	
	public List<String> loadAllLxdmMk();
	
	public List<LxdmEntity> loadLxdmByMkmc(String mkmc);
	
	public Page loadLxdmBySuperDm(Page page);
	
	public int addLxdm (LxdmEntity lxdm);
	
	public int updateLxdm(List<LxdmEntity> lxdm);
	
	public int deleteLxdm(int lxdmId);
	
	public String getDmmcByLxdm(String lxdm);
	
	
}
