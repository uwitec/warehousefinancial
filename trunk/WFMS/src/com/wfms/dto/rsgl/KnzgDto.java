/**
 *******************************************************************************
 * 文件名：KnzgDto.java
 *
 * 描述�?
 * 
 * 创建日期：Apr 23, 2010 6:49:54 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dto.rsgl;

import java.util.List;

import com.wfms.model.rsgl.KnzgEntity;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：KnzgDto
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Apr 23, 2010 6:49:54 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author ZHANGYONG
 * @see KnzgDto
 * @version 1.0
 *
 */
public class KnzgDto {
	private KnzgEntity knzg;
	private List<KnzgEntity> knzgList;

	public KnzgEntity getKnzg() {
		return knzg;
	}

	public void setKnzg(KnzgEntity knzg) {
		this.knzg = knzg;
	}

	public List<KnzgEntity> getKnzgList() {
		return knzgList;
	}

	public void setKnzgList(List<KnzgEntity> knzgList) {
		this.knzgList = knzgList;
	}

}
