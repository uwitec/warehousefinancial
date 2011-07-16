/**
 *******************************************************************************
 * 文件名：IRyglDao.java
 *
 * 描述�?
 * 
 * 创建日期：Jan 22, 2010 3:52:38 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dao.rsgl;

import java.util.List;
import java.util.Map;

import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.model.rsgl.RyEntity;

/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：IRyglDao
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 22, 2010 3:52:38 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see IRyglDao
 * @version 1.0
 *
 */
public interface IRyglDao {
	
	public int insertRy(RyEntity ry);
	
	public int updateRy(RyEntity ry);
	
	public int deleteRy(int ryId);
	
	public List<RyEntity> getRyByIds(int[] id);
	
	public RyEntity getRyById(int id);
	
	public List<RyEntity> getRyByPage(Page page);
	
	public List<RyEntity> getAllRy();
	/**
	 * 
	 * <dl>
	 * <b>方法�?:getRyByBmAndExsitsUser</b>
	 * <dd>方法作用：根据部门查询有用户和没有用户的人员
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param bmId int					部门编号
	 * <dd>@param exsitsAble boolean		是否存在用户
	 * <dd>@return
	 * </dl>
	 */
	public List<RyEntity> getRyByBmAndExsitsUser(int bmId,boolean existsAble);
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:getRyCountByParams</b>
	 * <dd>方法作用：根据筛选条件查询记录�?�数
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param page
	 * <dd>@return
	 * </dl>
	 */
	public int getRyCountByParams(List<ConditionBean> list);
	
	public List<RyEntity> getRyByBmId(int bmId);
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:getRyByIfLs</b>
	 * <dd>方法作用：查询是否为老师身份的人�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param ifLs 	boolean			是否老师
	 * <dd>@return
	 * </dl>
	 */
	public List<RyEntity> getRyByIfLsAndBm(int bmId,boolean ifLs);
	
	public int updateRyByCodesAndConditions(Map<Object, Object> codes , Map<Object, Object> cons);
	
	public RyEntity getRyByBh(String rybh);
	
	public int addRyList(List<RyEntity> ryList);
	
	public int updateRyList(List<RyEntity> ryList);

}
