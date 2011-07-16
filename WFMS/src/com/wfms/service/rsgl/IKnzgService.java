/**
 *******************************************************************************
 * 文件名：IKnzgService.java
 *
 * 描述�?
 * 
 * 创建日期：Apr 23, 2010 6:51:43 PM
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
import com.wfms.dto.rsgl.KnzgDto;
import com.wfms.model.rsgl.KnzgEntity;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：IKnzgService
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Apr 23, 2010 6:51:43 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author ZHANGYONG
 * @see IKnzgService
 * @version 1.0
 *
 */
public interface IKnzgService {
	
	public Page getKnzgByPage(Page page);

	public int addKnzg(KnzgEntity knzg);

	public int deleteKnzg(int id);

	public int updateKnzg(List<KnzgEntity> knzgList);
	
	public KnzgEntity getKnzgById(int id);
	
	public KnzgDto loadKnzgByRyId(int ryId);
	
	public int addOrUpdateKnzg(KnzgEntity knzg);
}
