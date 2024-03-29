/**
 *******************************************************************************
 * 文件名：ImportDao.java
 *
 * 描述：
 * 
 * 创建日期：Jan 29, 2010 11:04:09 AM
 * 
 * 本系统是商用软件，未经授权擅自复制或传播本程序的部分或全部将是非法的
 *
 *  Copyright 2010 迅尔科技, Inc. All rights reserved.
 *
 *******************************************************************************
 */
package com.wfms.common.function.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.ExtDao;
import com.wfms.common.function.dao.IImportDao;
import com.wfms.common.function.entity.Column;
import com.wfms.common.function.entity.ImportRule;
import com.wfms.common.function.entity.UniqueColumn;

/**
 * @author CYC
 * @see ImportDao
 * @version 1.0
 * 
 */
@Repository
public class ImportDao extends ExtDao implements IImportDao {

	protected final String SCHEMA_NAME = "ECM";
	protected final String PARAM_TAB_NAME = "tableName";
	protected final String PARAM_OWNER = "owner";

	protected final String COMMENT_SQL = "select id,tablename,column_name,data_type,data_length,data_precision,data_scale,nullable,data_default,comments from V_COL_DETAILS where tableName=:tableName and column_name<>'ID'";
	protected final String UNIQUE_HQL = "from UniqueColumn where tableName=:tableName";
	protected final String UNIQUE_SQL = "select * from V_UNIQUE_COL where TABLE_NAME=:tableName";

	public List<Column> getColDetailByTabName(String tableName) {
		List<Column> colList = null;
		Map sqlParams = new HashMap(1);
		sqlParams.put(PARAM_TAB_NAME, tableName);
		colList = (List<Column>) super.executeSQLQuery(COMMENT_SQL,sqlParams,
				Column.class);
		return colList;
	}

	public int batchImport(List<Map> dataList) {
		return super.batchAdd(dataList, 1000);
	}

	public int batchUpdateExists(List<Map> dataList) {
		return super.batchUpdate(dataList, 1000);
	}

	public List<Map> getAllUpdateList(String tableName, String[] uniqueKeys) {
		super.getClassByTableName(tableName);
		return null;
	}

	public boolean exsitsRsByPks(String tableName, String[] pkCols,
			String[] pkVals) {
		boolean exsits = false;
		StringBuffer sbSql = new StringBuffer("select count(*) from "
				+ tableName + " where 1=1");
		if (pkCols != null) {
			for (String pkCol : pkCols) {
				sbSql.append(" and " + pkCol + "=?");
			}
		}
		BigDecimal bCount = (BigDecimal) super.executeSQLScalar(String
				.valueOf(sbSql), pkVals);
		int rsCount = bCount.intValue();
		if (rsCount > 0) {
			exsits = true;
		}
		return exsits;
	}

	public boolean updateByPks(String tableName, String[] columns,
			String[] values, String[] pks, String[] pkVals) {
		boolean updateResult = false;
		StringBuffer sbSql = new StringBuffer("update " + tableName);
		if (columns != null && values != null
				&& columns.length == values.length) {
			sbSql.append(" set ");
			for (int i = 0; i < columns.length; i++) {
				String col = columns[i];
				String val = values[i];
				if (i < columns.length - 1)
					sbSql.append(col + "='" + val + "',");
				else
					sbSql.append(col + "='" + val + "'");
			}
		}
		sbSql.append(" where 1=1");
		if (pks != null && pkVals != null && pks.length == pkVals.length) {
			String primaryKey = "";
			for (String pk : pks) {
				if (primaryKey.equalsIgnoreCase(""))
					primaryKey += pk;
				else
					primaryKey += "||" + pk;
			}
			String pkValue = "";
			for (String pv : pkVals) {

				pkValue += pv;
			}
			sbSql.append(" and " + primaryKey + "='" + pkValue + "'");
		}
		if (sbSql.indexOf("and") != -1) {
			int affectedCount = 0;
			try {
				affectedCount = super.executeSQLUpdate(String.valueOf(sbSql),
						null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (affectedCount > 0) {
				updateResult = true;
			}
		}
		return updateResult;
	}

	public boolean insertWithAssignID(String tableName, String columns[],
			String values[]) {
		StringBuffer sbSql = new StringBuffer("insert into ");
		sbSql.append(tableName);
		sbSql.append("( ID,");
		for (int i = 0; i < columns.length; i++) {
			sbSql.append(columns[i]);
			sbSql.append(i != columns.length - 1 ? "," : "");
		}
		sbSql.append(" ) values( HIBERNATE_SEQUENCE.nextval,");
		for (int i = 0; i < values.length; i++)
			if (i == values.length - 1)
				sbSql.append("?");
			else
				sbSql.append("?,");
		sbSql.append(" )");
		int affectedCount = 0;
		try {
			affectedCount = super.executeSQLUpdate(String.valueOf(sbSql),
					values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return affectedCount > 0 ? true : false;
	}

	public boolean insertWithAutoID(String tableName, String columns[],
			String values[]) {
		StringBuffer sbSql = new StringBuffer("insert into ");
		sbSql.append(tableName);
		sbSql.append("( ID,");
		for (int i = 0; i < columns.length; i++) {
			sbSql.append(columns[i]);
			sbSql.append(i != columns.length - 1 ? "," : "");
		}
		sbSql.append(" ) values( HIBERNATE_SEQUENCE.nextval,");
		for (int i = 0; i < values.length; i++)
			if (i == values.length - 1)
				sbSql.append("?");
			else
				sbSql.append("?,");
		sbSql.append(" )");
		int affectedCount = 0;
		try {
			affectedCount = super.executeSQLUpdate(String.valueOf(sbSql),
					values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return affectedCount > 0 ? true : false;
	}
	
	public int batchInsertWithAutoID(List<String> sqlList) {
		String[] sqls = new String[sqlList.size()];
		for(int i=0;i<sqlList.size();i++)
		{
			sqls[i] = sqlList.get(i);
		}
		int[] affectedCount = super.executeSQLBatchUpdate(sqls);
		int totalCount = 0;
		if(affectedCount!=null)
		{
			for(int i=0;i<affectedCount.length;i++)
			{
				totalCount += affectedCount[i];
			}
		}
		return totalCount;
	}
	
	public List<ImportRule> getDrgzByTableName(String tableName) {
		String queryHql = "FROM DrgzEntity where ssbmc=:ssbmc";
		Map<String, String> queryParam = new HashMap<String, String>(1);
		queryParam.put("ssbmc", tableName);
		return super.executeQuery(queryHql, queryParam);
	}

	public List<String[]> getUniqColsByTabName(String tableName) {
		Map<String, String> queryParam = new HashMap<String, String>(1);
		queryParam.put("tableName", tableName);
		List<UniqueColumn> uniqueCols = super.executeSQLQuery(UNIQUE_SQL,queryParam,UniqueColumn.class);
		List<String[]> uniqueList = new ArrayList<String[]>(5);
		Map<String, String[]> uniqColsMap = null;
		Map<String, Map<String, String[]>> constraintMap = new HashMap<String, Map<String, String[]>>(
				5);
		for (UniqueColumn uniCol : uniqueCols) {
			if (!constraintMap.containsKey(uniCol.getConstraintName())) {
				uniqColsMap = new HashMap<String, String[]>(1);
				fillUniqueCols(uniCol, uniqColsMap);
				constraintMap.put(uniCol.getConstraintName(), uniqColsMap);
			} else {
				uniqColsMap = constraintMap.get(uniCol.getConstraintName());
				fillUniqueCols(uniCol, uniqColsMap);
				constraintMap.put(uniCol.getConstraintName(), uniqColsMap);
			}
		}
		for (Map.Entry<String, Map<String, String[]>> uniqueMap : constraintMap
				.entrySet()) {
			uniqueList.add(uniqueMap.getValue().get(tableName));
		}
		return uniqueList;
	}

	private void fillUniqueCols(UniqueColumn uniCol,
			Map<String, String[]> uniqColsMap) {
		if (!uniqColsMap.containsKey(uniCol.getTableName())) {
			uniqColsMap.put(uniCol.getTableName(), new String[] { uniCol
					.getColumnName() });
		} else {
			String[] cols = uniqColsMap.get(uniCol.getTableName());
			String[] dcols = new String[cols.length + 1];
			System.arraycopy(cols, 0, dcols, 0, cols.length);
			dcols[dcols.length - 1] = uniCol.getColumnName();
			uniqColsMap.put(uniCol.getTableName(), dcols);
		}
	}
}
