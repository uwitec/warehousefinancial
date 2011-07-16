/**
 *******************************************************************************
 * 文件名：IRyhjService.java
 *
 * 描述�?
 * 
 * 创建日期：Mar 16, 2010 2:04:00 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.service.rsgl;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.model.rsgl.RyhjEntity;


/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：IRyhjService
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 16, 2010 2:04:00 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see IRyhjService
 * @version 1.0
 *
 */
public interface IRyhjService {

	public int addRyhj(RyhjEntity ryhj);
	
	public int updateRyhjList(List<RyhjEntity> ryhjList);
	
	public int deleteRyhj(int ryhjId);
	
	public Page loadRyhjByPage(Page page);
}
