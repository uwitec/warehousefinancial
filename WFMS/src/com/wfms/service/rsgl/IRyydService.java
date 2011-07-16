/**
 *******************************************************************************
 * 文件名：IRyydService.java
 *
 * 描述�?
 * 
 * 创建日期：Apr 16, 2010 1:53:47 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.service.rsgl;

import com.wfms.common.entity.Page;
import com.wfms.model.rsgl.RyydEntity;


/**
 * <dl>  Description
 *  <dd> 项目名称：迅尔数字化校园信息平台ECMS
 *  <dd> 类名称：IRyydService
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Apr 16, 2010 1:53:47 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author ECMS
 * @see IRyydService
 * @version 1.0
 *
 */
public interface IRyydService {
	public Page getRyydByPage(Page page);
	
	public int addRyyd(RyydEntity ryyd);
	
	public int deleteRyydById(int id);
}
