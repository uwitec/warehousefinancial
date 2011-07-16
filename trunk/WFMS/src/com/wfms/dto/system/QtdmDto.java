/**
 *******************************************************************************
 * 文件名：QtdmDto.java
 *
 * 描述�?
 * 
 * 创建日期：Jan 21, 2010 2:50:51 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dto.system;

import java.util.List;

import com.wfms.model.system.QtdmEntity;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：QtdmDto
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 21, 2010 2:50:51 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author GHT
 * @see QtdmDto
 * @version 1.0
 *
 */
public class QtdmDto {
	private QtdmEntity qtdm;
	private List<QtdmEntity> qtdmList;
	public QtdmEntity getQtdm() {
		return qtdm;
	}
	public void setQtdm(QtdmEntity qtdm) {
		this.qtdm = qtdm;
	}
	public List<QtdmEntity> getQtdmList() {
		return qtdmList;
	}
	public void setQtdmList(List<QtdmEntity> qtdmList) {
		this.qtdmList = qtdmList;
	}
}
