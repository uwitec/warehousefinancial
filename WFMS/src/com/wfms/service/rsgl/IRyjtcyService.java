/**
 *******************************************************************************
 * 文件名：IRyjtcyService.java
 *
 * 描述�?
 * 
 * 创建日期：Apr 29, 2010 9:11:45 AM
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
import com.wfms.dto.rsgl.RyjtcyDto;
import com.wfms.model.rsgl.RyjtcyEntity;


/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：IRyjtcyService
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Apr 29, 2010 9:11:45 AM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see IRyjtcyService
 * @version 1.0
 *
 */
public interface IRyjtcyService {

	public int addRyjtcy(RyjtcyEntity ryjtcy);
	
	public int updateRyjtcyList(List<RyjtcyEntity> ryjtcyList);
	
	public int deleteRyjtcy(int ryjtcyId);
	
	public RyjtcyDto loadRyjtcyByRy(int ryId);

	public Page getRyjtcyByPage(Page page);
}
