/**
 *******************************************************************************
 * 文件名：IKnzgDao.java
 *
 * 描述�?
 * 
 * 创建日期：Apr 23, 2010 6:40:39 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dao.rsgl;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.model.rsgl.KnzgEntity;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：IKnzgDao
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Apr 23, 2010 6:40:39 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author ZHANGYONG
 * @see IKnzgDao
 * @version 1.0
 *
 */
public interface IKnzgDao {
	public int getKnzgCount(List<ConditionBean> cons);

	public List<KnzgEntity> getKnzgByPage(Page page);

	public int addKnzg(KnzgEntity knzg);

	public int deleteKnzg(int id);

	public int updateKnzg(List<KnzgEntity> knzgList);

	public KnzgEntity getKnzg(int id);
	
	public KnzgEntity getKnzgByRyId(int ryId);
	
	public int saveOrUpdateKnzg(KnzgEntity knzg);
	
}
