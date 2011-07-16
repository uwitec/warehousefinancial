/**
 *******************************************************************************
 * 文件名：IBhscgzglService.java
 *
 * 描述�?
 * 
 * 创建日期：Jan 20, 2010 8:14:05 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.service.system;

import java.util.List;

import com.wfms.dto.system.BhscgzDto;
import com.wfms.model.system.BhscgzszEntity;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：IBhscgzglService
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 20, 2010 8:14:05 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see IBhscgzglService
 * @version 1.0
 *
 */
public interface IBhscgzglService {
	
	public int deleteBhscgz(int bhscgzId);
	
	public int updateBhscgz(List<BhscgzszEntity> list);
	
	public BhscgzDto loadBhscgzByGzmc(String gzmc);
}
