/**
 *******************************************************************************
 * 文件名：IRyglService.java
 *
 * 描述�?
 * 
 * 创建日期：Jan 22, 2010 3:53:27 PM
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
import com.wfms.dto.rsgl.RyDto;
import com.wfms.model.rsgl.RyEntity;


/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：IRyglService
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 22, 2010 3:53:27 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see IRyglService
 * @version 1.0
 *
 */
public interface IRyglService {
	
	public Page loadRyForPage(Page page);
	
	public int addRy(RyEntity ry);
	
	public int updateRy(RyEntity ry);
	
	public RyDto getRyById(int id);
	
	public int updateRyList(List<RyEntity> ryList);
	
	public int deleteRy(int[] ryId);
	
	public RyDto getRyByIds(int[] ids);
	
	public List<RyEntity> getAllRy();

	public List<RyEntity> loadRyByBmAndExistsYh(String bmId,boolean existsAble);

	public RyDto loadRybyBm(String bmId);
	
	public String addYhForRy(int ryId,String dlzh,String dlmm);
	
	public RyDto loadRyByNotLsAnBm(String bmId);
}
