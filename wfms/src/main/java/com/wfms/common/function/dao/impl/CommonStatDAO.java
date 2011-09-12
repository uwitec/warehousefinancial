package com.wfms.common.function.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.wfms.common.dao.ExtDao;
import com.wfms.common.function.attribute.StatConditionBean;
import com.wfms.common.function.attribute.StatResultSetBean;
import com.wfms.common.function.entity.StatRule;
import com.wfms.common.function.util.StatUtil;


/**
 * @author CYC
 * @see CommonStatDAO
 * @version 1.0
 * 
 */
@Repository
public class CommonStatDAO extends ExtDao {


	/**
	 * 初始化StatResultSetBean
	 * 
	 * @param rsBean
	 *            输出变量
	 * @param condition
	 *            SQL 查询条件（and xx=xx and ...）
	 * @param rowKeyType
	 *            行查询字段
	 * @param rowKeys
	 *            行列表
	 * @param columnKeyType
	 *            列查询字段
	 * @param columnKeys
	 *            列列表
	 * @param tableName
	 *            查询表或视图
	 */

	public boolean initStatResultSetBean(StatResultSetBean rsBean,
			String rowKeyType, List<String> rowKeys, String columnKeyType,
			List<String> columnKeys, String tableName, String condition) {

		// 校验输入的合法性
		if ((rsBean == null) || (condition == null) || (rowKeyType == null)
				|| (rowKeys == null) || (columnKeyType == null)
				|| (columnKeys == null) || (tableName == null))
			return false;

		// 根据行项目和列项目构建数据集
		List<List<String>> rs;
		try {
			String sql;
			rs = new ArrayList<List<String>>();

			String[] inputValues = new String[2];
			for (int i = 0; i < rowKeys.size(); i++) {
				List<String> row = new ArrayList<String>();
				for (int j = 0; j < columnKeys.size(); j++) {
					sql = "select count(*) STATCOUNT from " + tableName
							+ " where " + rowKeyType + " =?" + " and "
							+ columnKeyType + " =?" + " " + condition;
					inputValues[0] = String.valueOf(rowKeys.get(i));
					inputValues[1] = String.valueOf(columnKeys.get(j));
					String statCountStr = getOneRs(sql, inputValues,
							"STATCOUNT");
					row.add(statCountStr);
				}
				rs.add(row);
			}
			rsBean.setRowKeys(rowKeys);
			rsBean.setRowNum(rowKeys.size());
			rsBean.setColumnKeys(columnKeys);
			rsBean.setColumnNum(columnKeys.size());
			rsBean.setRs(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;

	}

	/**
	 * 初始化StatResultSetBean
	 * 
	 * @param rsBean
	 *            输出变量
	 * @param condition
	 *            SQL 查询条件（and xx=xx and ...）
	 * @param rowKeyType
	 *            行查询字段
	 * @param rowKeys
	 *            行列表
	 * @param columnKeyType
	 *            列查询字段
	 * @param columnKeys
	 *            列列表
	 * @param tableName
	 *            查询表或视图
	 */

	public boolean initRsBeanByScope(StatResultSetBean rsBean,
			String rowKeyType, List<String> rowKeys, String columnKeyType,
			List<String> columnKeys, String tableName, String condition) {

		// 校验输入的合法性
		if ((rsBean == null) || (condition == null) || (rowKeyType == null)
				|| (rowKeys == null) || (columnKeyType == null)
				|| (columnKeys == null) || (tableName == null))
			return false;

		// 根据行项目和列项目构建数据集
		List<List<String>> rs;
		try {
			String sql;
			rs = new ArrayList<List<String>>();
			for (int i = 0; i < rowKeys.size(); i++) {
				List<String> row = new ArrayList<String>();
				for (int j = 0; j < columnKeys.size(); j++) {
					sql = "select count(*) STATCOUNT from " + tableName
							+ " where " + rowKeyType + " = '"
							+ String.valueOf(rowKeys.get(i)) + "' and ("
							+ columnKeyType + " between "
							+ String.valueOf(columnKeys.get(j)) + ") "
							+ condition;
					String statCountStr = getOneRs(sql, new String[0],
							"STATCOUNT");
					row.add(statCountStr);
				}
				rs.add(row);
			}
			rsBean.setRowKeys(rowKeys);
			rsBean.setRowNum(rowKeys.size());
			rsBean.setColumnKeys(columnKeys);
			rsBean.setColumnNum(columnKeys.size());
			rsBean.setRs(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;

	}

	public List<String> getColumnKeys(String inputField, String outputField,
			String tableName) {
		String sql;
		// 查询所有的列项目
		sql = "select " + outputField + " from " + tableName + " order by "
				+ inputField;
		List<String> columnKeys = new ArrayList<String>();
		try {
			columnKeys = arrayToList(getRs(sql, new String[0], outputField));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (columnKeys.isEmpty())
			return null;
		return columnKeys;
	}

	public List<String> getRowKeys(String field, String tableName,
			String condition) {
		String sql;
		// 查询所有行项目
		sql = "select " + field + " from " + tableName + " where 1=1 and "
				+ field + " is not null" + condition + " group by " + field
				+ " order by " + field;
		List<String> rowKeys = new ArrayList<String>();
		try {
			rowKeys = arrayToList(getRs(sql, new String[0], field));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return rowKeys;
	}

	/**
	 * 工具函数，String Array to List<String>
	 * 
	 * @param arr
	 * @return
	 */
	public List arrayToList(String arr[]) {
		ArrayList list = new ArrayList();
		for (String s : arr) {
			list.add(s);
		}
		return list;
	}

	/**
	 * 获取单列字符串数组
	 * 
	 * @param sql
	 * @param input
	 * @param output
	 * @return
	 * @throws Exception
	 */
	public String[] getRs(String sql, String input[], String output)
			throws SQLException {
		String result[] = (String[]) null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = SessionFactoryUtils.getDataSource(getSessionFactory())
			.getConnection();
			stmt = conn.prepareStatement(sql, 1004, 1007);
			for (int i = 0; i < input.length; i++)
				stmt.setString(i + 1, input[i]);
			rs = stmt.executeQuery();
			rs.first();
			int size = 0;
			if (rs.last())
				size = rs.getRow();
			result = new String[size];
			rs.beforeFirst();
			for (int i = 0; rs.next(); i++)
				result[i] = rs.getString(output);

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	/**
	 * 获取单行记录，返回类型为String[]
	 * 
	 * @param sql
	 * @param inputValue
	 * @param outputValue
	 * @return
	 */
	public String[] getOneRs(String sql, String inputValue[],
			String outputValue[]) throws SQLException {
		String result[] = new String[outputValue.length];
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = SessionFactoryUtils.getDataSource(getSessionFactory())
			.getConnection();
			stmt = conn.prepareStatement(sql);
			for (int i = 0; i < inputValue.length; i++)
				stmt.setString(i + 1, inputValue[i]);
			rs = stmt.executeQuery();
			if (rs.next()) {
				for (int i = 0; i < outputValue.length; i++)
					result[i] = rs.getString(outputValue[i]);
			} else {
				result = (String[]) null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			result = (String[]) null;
			throw e;

		}
		return result;
	}

	/**
	 * 获取单一行单一列的字段值
	 * 
	 * @param sql
	 * @param inputValue
	 * @param outputValue
	 * @return
	 */
	public String getOneRs(String sql, String inputValue[], String outputValue)
			throws SQLException {
		String tmp[] = getOneRs(sql, inputValue, new String[] { outputValue });
		String res = tmp != null ? tmp[0] : "";
		return res;
	}

	public StatRule getRuleByTabColumn(String tableName,String colName)
	{
		String queryHql="FROM StatRule where ssbmc=:ssbmc and zdmc=:zdmc";
		Map<String,String> queryParams=new HashMap<String, String>(2);
		queryParams.put("ssbmc",tableName);
		queryParams.put("zdmc",colName);
		return (StatRule)super.executeScalar(queryHql,queryParams);
	}
	
	public String buildStatSql(String sql, String inputValue[],
			String outputValue) throws SQLException {
		sql = "select all_ext.bjid,all_ext.mz,coalesce(sub_query.ct,0) from" +
				" (select bjid,mz,count(*) ct from xsjbxxb" +
				" group by bjid,mz) sub_query right join" +
				" (select bjdm.id bjid,lxdm.dmmc mz from" +
				" bjdmb bjdm,(select dmmc from lxdmb where lxdmb.sjlxdm='1009')" +
				" lxdm where exists(select null from (select bjid" +
				" from xsjbxxb group by bjid) bjxx where bjxx.bjid=bjdm.id)" +
				" and exists(select null from(select mz from xsjbxxb group by mz)" +
				" xsxx where  xsxx.mz=lxdm.dmmc)" +
				" group by bjdm.id,lxdm.dmmc) all_ext on" +
				" sub_query.bjid=all_ext.bjid and sub_query.mz=all_ext.mz";
		return sql;
	}
	

	/**
	 * 
	 * <dl>
	 * <b>方法名:getKeyMap</b>
	 * <dd>方法作用：
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选) 
	 * <dd>@param sql
	 * <dd>@param inputValue
	 * <dd>@param outputValue
	 * <dd>@return
	 * <dd>@throws SQLException
	 * </dl>
	 */
	public Map<String,String> getKeyMap(String sql, String[] inputValue, String[] outputValue)
			throws SQLException {
		HashMap<String,String> map = new HashMap<String,String>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = SessionFactoryUtils.getDataSource(getSessionFactory())
			.getConnection();
			stmt = conn.prepareStatement(sql);
			if(inputValue != null)
			{
				for (int i = 0; i < inputValue.length; i++)
					stmt.setString(i + 1, inputValue[i]);
			}
			rs = stmt.executeQuery();
			while( rs.next()) {
				map.put(rs.getString(outputValue[0]), rs.getString(outputValue[1]));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * �м�¼�
	 * 
	 * @param sql
	 * @param input
	 * @param output
	 * @return
	 * @throws SQLException
	 */
	public List<String> getKeyList(String sql, String input[], String output)
			throws SQLException {
		List<String> keyList = new ArrayList<String>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = SessionFactoryUtils.getDataSource(getSessionFactory())
			.getConnection();
			stmt = conn.prepareStatement(sql,1004,1007);
			if(input != null)
			{
			for (int i = 0; i < input.length; i++)
				stmt.setString(i + 1, input[i]);
			}
			rs = stmt.executeQuery();
			rs.last();
			keyList = new ArrayList<String>(rs.getRow());
			rs.beforeFirst();
			for (int i = 0; rs.next(); i++)
				keyList.add(rs.getString(output));

		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		return keyList;
	}
	
	/**
	 * ��ȡ��¼������������ΪList<Map>
	 * 
	 * @param sql
	 * @param inputValue
	 * @param outputValue
	 * @return List<Map>
	 */
	public Map<String,String> getStatResultMap(String sql, String inputValue[], String outputValue[])
			throws SQLException {
		HashMap<String,String> map = null; 
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = SessionFactoryUtils.getDataSource(getSessionFactory())
			.getConnection();
			stmt = conn.prepareStatement(sql);
			if(inputValue!=null)
			{
				for (int i = 0; i < inputValue.length; i++)
					stmt.setString(i + 1, inputValue[i]);
			}
			rs = stmt.executeQuery(); 
			map = new HashMap<String,String>();
			while(rs.next()){
				map.put(rs.getString(outputValue[0])+rs.getString(outputValue[1]), rs.getString(outputValue[2]));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}
	/**
	 * 初始化StatResultSetBean
	 * 
	 * @param rsBean
	 *            输出变量
	 * @param condition
	 *            SQL 查询条件（and xx=xx and ...）
	 * @param rowKeyType
	 *            行查询字段
	 * @param rowKeys
	 *            行列表
	 * @param columnKeyType
	 *            列查询字段
	 * @param columnKeys
	 *            列列表
	 * @param tableName
	 *            查询表或视图
	 */

	public boolean initStatResultSetBean(StatUtil statUtil,StatResultSetBean rsBean,StatConditionBean condBean) {

		String rowKeyType=condBean.getRowKeyType();
		String columnKeyType=condBean.getColumnKeyType();
		String tableName = condBean.getTableName();
		String statViewName = condBean.getStatViewName();
		String conditions  = condBean.getCondition()==null?"":condBean.getCondition();
		List<String> columnKeys = null;
		List<String> rowKeys = null;
		
		List<String> columnVals = null;
		List<String> rowVals = null;
		
		StatRule columnRule = getRuleByTabColumn(tableName, columnKeyType);
		StatRule rowRule = getRuleByTabColumn(tableName, rowKeyType);
		
		//处理行类型代码数据
		if(columnRule!=null && columnRule.getFllxdm()!=null && !"".equals(columnRule.getFllxdm()))
		{
			condBean.getRefSql()[1]="select "+columnRule.getLxdmzd()+","+columnRule.getLxdmxszd()+" from lxdmb lxdm where sjlxdm='"+columnRule.getFllxdm()+"'" +
			" and "+columnRule.getLxdmzd()+" in(select "+columnKeyType+" from "+tableName+" group by "+columnKeyType+")";
		}
		else if(columnRule != null && columnRule.getChildRef()!=0)
		{
			if("".equals(conditions))
			{
				condBean.getRefSql()[1]=getKeySQL(columnRule.getZdmc(),tableName, columnKeyType);
			}
			else
			{
				condBean.getRefSql()[1]="select "+columnRule.getZdmc()+","+columnRule.getRefzdmc()+" from "+statViewName
				+" group by "+columnRule.getZdmc()+","+columnRule.getRefzdmc()
				+" having 1=1"+conditions;
			}
		}
		else if(columnRule != null)
		{
			condBean.getRefSql()[1]="select id "+columnKeyType+","+columnRule.getRefzdmc()+" from "+columnRule.getRefbmc()+"  where id"
			+" in(select "+columnKeyType+" from "+tableName+")";
		}
		
		//处理列类型代码数据
		if(rowRule!=null && rowRule.getFllxdm()!=null && !"".equals(rowRule.getFllxdm()))
		{
			condBean.getRefSql()[0]="select "+rowRule.getLxdmzd()+" "+rowKeyType+","+rowRule.getLxdmxszd()+" from lxdmb lxdm where sjlxdm='"+rowRule.getFllxdm()+"'" +
			" and "+rowRule.getLxdmzd()+" in(select "+rowKeyType+" from "+tableName+" group by "+rowKeyType+")";
		}
		else if(rowRule != null && rowRule.getChildRef()!=0)
		{
			if("".equals(conditions))
			{
				condBean.getRefSql()[0]=getKeySQL(rowRule.getZdmc(),tableName, rowKeyType);
			}
			else
			{
				condBean.getRefSql()[0]="select "+rowRule.getZdmc()+","+rowRule.getRefzdmc()+" from "+statViewName
				+" group by "+rowRule.getZdmc()+","+rowRule.getRefzdmc()
				+" having 1=1"+conditions;
			}
		}
		else if(rowRule != null)
		{
			condBean.getRefSql()[1]="select id "+rowKeyType+","+rowRule.getRefzdmc()+" from "+rowRule.getRefbmc()+"  where id"
			+" in(select "+rowKeyType+" from "+tableName+")";
		}
		try {
			if(condBean.getRefSql()[1]!=null && !"".equals(condBean.getRefSql()[1]))
			{
				Map<String, String> columnKeyMap= getKeyMap(condBean.getRefSql()[1], null, new String[]{columnKeyType,columnRule.getRefzdmc()});
				condBean.setColumnObjects(columnKeyMap);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			if(condBean.getRefSql()[0]!=null && !"".equals(condBean.getRefSql()[0]))
			{
				Map<String, String> rowKeyMap= getKeyMap(condBean.getRefSql()[0], null, new String[]{rowKeyType,rowRule.getRefzdmc()});
				condBean.setRowObjects(rowKeyMap);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		if(condBean.getColumnObjects() == null)
		{
			String columnSQL="select "+columnKeyType+" from "
			+tableName+"  group by "+columnKeyType
			+" having "+columnKeyType+" is not null"+conditions;
			if(condBean.getRefSql()==null)
			{
				condBean.setRefSql(new String[2]);
			}
			condBean.getRefSql()[1]=columnSQL;
			try {
				columnKeys = getKeyList(columnSQL, null, columnKeyType);
				columnVals = columnKeys;
				condBean.setColumnObjects(columnKeys);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(condBean.getRowObjects() == null)
		{
			String rowSQL="select "+rowKeyType+" from "
			+tableName+"  group by "+rowKeyType
			+" having "+rowKeyType+" is not null"+conditions;
			if(condBean.getRefSql()==null)
			{
				condBean.setRefSql(new String[2]);
			}
			condBean.getRefSql()[0]=rowSQL;
			try {
				rowKeys = getKeyList(rowSQL, null, condBean.getRowKeyType());
				rowVals = rowKeys;
				condBean.setRowObjects(rowKeys);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 校验输入的合法性
		if ((rsBean == null) || (conditions == null) || (condBean.getRowKeyType() == null)
				|| (condBean.getRowObjects() == null) || (condBean.getColumnKeyType() == null)
				|| (condBean.getColumnObjects() == null) || (condBean.getTableName() == null))
			return false;
		
		if(condBean.getColumnObjects() instanceof Map)
		{
			Map<String,String> colMap = (Map<String, String>)condBean.getColumnObjects();
			columnKeys = new ArrayList<String>();
			columnVals = new ArrayList<String>();
			for(Map.Entry<String, String> col:colMap.entrySet())
			{
				columnKeys.add(col.getKey());
				columnVals.add(col.getValue());
			}
		}
		if(condBean.getRowObjects() instanceof Map)
		{
			Map<String,String> rowMap = (Map<String, String>)condBean.getRowObjects();
			rowKeys  = new ArrayList<String>();
			rowVals = new ArrayList<String>();
			for(Map.Entry<String, String> row:rowMap.entrySet())
			{
				rowKeys.add(row.getKey());
				rowVals.add(row.getValue());
			}
		}

		if(rowKeys==null || rowKeys.size()==0)
		{
			return false;
		}
		if(columnKeys == null || columnKeys.size()==0)
		{
			return false;
		}
		String existsStatSQL="select all_query."+rowKeyType+" "+rowKeyType+",all_query."+columnKeyType+" "+columnKeyType+",coalesce(sub_query.ct,0) rsCount" +
				" from (select "+rowKeyType+","+columnKeyType+",count(*) ct from "+statViewName+" group by "+rowKeyType+","+columnKeyType+" having 1=1"+conditions+") sub_query";
		
		String fullStatSQL = "(select "+rowKeyType+","+columnKeyType+" from" +
		" (select "+rowKeyType+","+columnKeyType+" from ("+condBean.getRefSql()[0]+") A "
		+"full join"
		+" (select "+columnKeyType+" from ("+condBean.getRefSql()[1]+")) B"
		+" on 1=1)) all_query";
		
		String statSQL=existsStatSQL+" right join "+fullStatSQL+
		" on sub_query."+rowKeyType+"=all_query."+rowKeyType+
				" and sub_query."+columnKeyType+"=all_query."+columnKeyType;
		// 根据行项目和列项目构建数据集
		List<List<String>> rs;
		try {
			rs = new ArrayList<List<String>>();
			Map<String,String> statResultMap= new HashMap<String, String>();
			statResultMap = getStatResultMap(statSQL,null,new String[]{condBean.getRowKeyType(),condBean.getColumnKeyType(),"rsCount"});
			for (int i = 0; i < rowKeys.size(); i++) {
				List<String> row = new ArrayList<String>();
				for (int j = 0; j < columnKeys.size(); j++) {
					String uniqueKey = rowKeys.get(i)+columnKeys.get(j);
					String statCountStr=statResultMap.get(uniqueKey);
					row.add(statCountStr);
				}
				rs.add(row);
			}
			rsBean.setRowKeys(rowVals);
			rsBean.setRowNum(rowVals.size());
			rsBean.setColumnKeys(columnVals);
			rsBean.setColumnNum(columnVals.size());
			rsBean.setRs(rs);
			statUtil.setColumnKeys(columnVals);
			statUtil.setRowKeys(rowVals);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public StatRule getStatRuleBySsbmcZd(String tableName,String zdmc)
	{
		String queryHql="FROM StatRule where ssbmc=:ssbmc and zdmc=:zdmc";
		Map<String, String> queryParams = new HashMap<String, String>(2);
		queryParams.put("ssbmc", tableName);
		queryParams.put("zdmc", zdmc);
		StatRule statRule = (StatRule)super.executeScalar(queryHql, queryParams);
		return statRule;
	}
	
	public String getKeySQL(String idOtherName,String tableName,String zdmc){
		String querySql="select 'select id "+idOtherName+",'||b.refzdmc||'" +
				" from '||b.refbmc||' where id in(select '||b.zdmc||'" +
				" from '||a.refbmc||' where id in(select '||a.zdmc||'" +
				" from '||a.ssbmc||'))'" +
				" from common_stat_rule a,common_stat_rule b" +
				" where a.id=b.childref and a.ssbmc=? and b.ssbmc=? and b.zdmc=?";
		return (String)super.executeSQLScalar(querySql,new String[]{tableName,tableName,zdmc});
	}
	
}
