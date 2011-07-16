/**
 *******************************************************************************
 * 文件名：IImportDao.java
 *
 * 描述：
 * 
 * 创建日期：Jan 29, 2010 12:16:48 PM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.dao.common.dataimport;

import java.util.List;
import java.util.Map;

import com.wfms.model.common.dataimport.ColumnEntity;
import com.wfms.model.common.dataimport.DrgzEntity;



/**
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：IImportDao
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Jan 29, 2010 12:16:48 PM
 *  <dd> 修改人：无
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see IImportDao
 * @version 1.0
 *
 */
public interface IImportDao {
	
	public List<ColumnEntity> getColDetailByTabName(String tableName);
	
	/**
	 * 
	 * <dl>
	 * <b>方法名:batchImport</b>
	 * <dd>方法作用：批量导入外部数据源数据
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选) 
	 * <dd>@param dataList			List of Map 外部数据源数据
	 * <dd>@return					int 		导入数据记录数
	 * </dl>
	 */
	public int batchImport(List<Map> dataList);
	
	/**
	 * 
	 * <dl>
	 * <b>方法名:batchUpdateExists</b>
	 * <dd>方法作用：从外部数据源批量覆盖更新系统已有数据
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选) 
	 * <dd>@param dataList	 List of Map	外部数据源获取数据
	 * <dd>@return	int						更新记录数
	 * </dl>
	 */
	public int batchUpdateExists(List<Map> dataList);
	
	/**
	 * 
	 * <dl>
	 * <b>方法名:getAllUpdateList</b>
	 * <dd>方法作用：查询所有需要覆盖更新的数据
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选) 
	 * <dd>@param tableName				内部数据表名
	 * <dd>@param uniqueKeys			唯一标识列名列表
	 * <dd>@return
	 * </dl>							List of Map
	 */
	public List<Map> getAllUpdateList(String tableName,String[] uniqueKeys);
	
	/**
	 * 
	 * <dl>
	 * <b>方法名:exsitsRsByPks</b>
	 * <dd>方法作用：根据数据行唯一判定条件查询该记录是否存在
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选) 
	 * <dd>@param tableName				表名
	 * <dd>@param pks					唯一标识列
	 * <dd>@param pkVals				唯一标识列值
	 * <dd>@return					boolean 是否存在
	 * </dl>
	 */
	public boolean exsitsRsByPks(String tableName,String[] pks,String[] pkVals);
	
	/**
	 * 
	 * <dl>
	 * <b>方法名:updateByPks</b>
	 * <dd>方法作用： 根据唯一标识列更新数据
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选) 
	 * <dd>@param tableName				表名
	 * <dd>@param pks					唯一标识列
	 * <dd>@param pkVals				唯一标识列值
	 * <dd>@param columns 				更新的列			
	 * <dd>@param values 				更新的列的值			
	 * <dd>@return			boolean			更新结果
	 * </dl>
	 */
	public boolean updateByPks(String tableName,String[] columns,String[] values,String[] pks,String[] pkVals);
	
	/**
	 * 
	 * <dl>
	 * <b>方法名:insertWithAutoID</b>
	 * <dd>方法作用：根据自动编号主键标识列,增加数据
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选) 
	 * <dd>@param tableName
	 * <dd>@param columns
	 * <dd>@param values
	 * <dd>@return
	 * </dl>
	 */
	public boolean insertWithAutoID(String tableName, String columns[],
			String values[]);
	
	/**
	 * 
	 * <dl>
	 * <b>方法名:getDrgzByTableName</b>
	 * <dd>方法作用：根据表名,查询该表字段的数据验证规则
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选) 
	 * <dd>@param tableName				表名
	 * <dd>@return				List of DrgzEntity
	 * </dl>
	 */
	public List<DrgzEntity> getDrgzByTableName(String tableName);
	
	/**
	 * 
	 * <dl>
	 * <b>方法名:getUniqColsByTabName</b>
	 * <dd>方法作用：查询
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选) 
	 * <dd>@param tableName
	 * <dd>@return
	 * </dl>
	 */
	public List<String[]> getUniqColsByTabName(String tableName);
	
	public int batchInsertWithAutoID(List<String> sqlList);
	
}
