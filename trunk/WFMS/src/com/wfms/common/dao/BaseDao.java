package com.wfms.common.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.CacheMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.common.util.Rule;
import com.wfms.common.util.SortOrder;
import com.wfms.common.util.StringUtil;

public class BaseDao extends HibernateDaoSupport {

	@Autowired
	@Qualifier("sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private int modifyByHql(String hql) {
		int row = 0;
		try {
			row = this.getHibernateTemplate().bulkUpdate(hql);
		} catch (Exception e) {
			e.printStackTrace();
			row = -1;
		}
		return row;
	}

	private int modifyBySql(String sql) {
		int row = 0;
		try {
			row = this.getSession().createSQLQuery(sql).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			row = 0;
		}
		return row;
	}

	protected int updateByHql(String hql) {
		return this.modifyByHql(hql);
	}

	protected int updateBySql(String sql) {
		return this.modifyBySql(sql);
	}

	protected int deleteByHql(String hql) {
		return this.modifyByHql(hql);
	}

	protected int deleteBySql(String sql) {
		return this.modifyBySql(sql);
	}

	@SuppressWarnings("unchecked")
	protected int getTotalCount(Class clazz) {
		String hql = "SELECT COUNT(*) FROM " + clazz.getSimpleName();
		int count = 0;
		try {
			Object result = this.getSession().createQuery(hql).uniqueResult();
			if (result == null) {
				return 0;
			}
			count = Integer.parseInt(String.valueOf(result));
		} catch (Exception e) {
			count = 0;
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	protected int getTotalCountByHql(String strhql) {
		int count = 0;
		try {
			Object result = this.getSession().createQuery(strhql)
					.uniqueResult();
			if (result == null) {
				return 0;
			}
			count = Integer.parseInt(String.valueOf(result));
		} catch (Exception e) {
			count = 0;
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	protected int getTotalCountBySql(String strsql) {
		int count = 0;
		try {
			Object result = this.getSession().createSQLQuery(strsql)
					.uniqueResult();
			if (result == null) {
				return 0;
			}
			count = ((BigDecimal) result).intValue();
			;
		} catch (Exception e) {
			count = 0;
		}
		return count;
	}

	protected int getTotalCountBySql(String strsql, String column, int[] inVals) {
		int count = 0;
		String querySql = this.buildInParamForHql(new StringBuffer(strsql),
				column, inVals);
		count = getTotalCountBySql(querySql);
		return count;
	}

	protected Serializable insert(Object entity) {
		Serializable id = null;
		try {
			id = this.getHibernateTemplate().save(entity);
		} catch (Exception e) {
			e.printStackTrace();
			id = null;
		}
		return id;
	}

	protected int add(Object obj) {
		int retVal = 0;
		Serializable sid = this.insert(obj);
		retVal = Integer.valueOf(String.valueOf(sid));
		return retVal;
	}

	protected Object adds(Object obj) {
		Object result;
		Serializable sid = this.insert(obj);
		result = Integer.valueOf(String.valueOf(sid));
		return result;
	}

	protected int addUpdByPkExists(Object obj, Serializable primaryVal) {
		int retVal = 0;
		boolean addAble = super.getHibernateTemplate().get(obj.getClass(),
				primaryVal) == null ? true : false;
		if (addAble) {
			retVal = this.add(obj);
		} else {
			retVal = update(obj);
		}
		return retVal;
	}

	@SuppressWarnings("all")
	protected int addUpdByQueryExt(Object obj, Object hqlParams) {
		int retVal = 0;
		String entityName = obj.getClass().getSimpleName();
		StringBuffer sbModHql = new StringBuffer("FROM " + entityName
				+ " where 1=1");
		String modHql = this.buildParameterForHql(sbModHql, hqlParams);
		boolean addAble = executeScalar(modHql, null) == null ? true : false;
		if (addAble) {
			retVal = this.add(obj);
		} else {
			retVal = update(obj);
		}
		return retVal;
	}

	protected int update(Object entity) {
		int row = 0;
		try {
			this.getHibernateTemplate().update(entity);
			row = 1;
		} catch (Exception e) {
			e.printStackTrace();
			row = -1;
		}

		return row;
	}

	protected int delete(Object entity) {
		int row = 0;
		try {
			this.getHibernateTemplate().delete(entity);
			row = 1;
		} catch (Exception e) {
			e.printStackTrace();
			row = 0;
		}
		return row;
	}

	@SuppressWarnings("unchecked")
	protected int deleteByConditions(Class clazz, Map<Object, Object> conditions) {
		int row = 0;
		if (conditions.size() == 0) {
			return 0;
		}
		try {
			String entityName = clazz.getSimpleName();
			String hql = "DELETE FROM " + entityName + " where 1=1";
			for (Iterator iter = conditions.keySet().iterator(); iter.hasNext();) {
				String key = String.valueOf(iter.next());
				hql += " and " + key + "='" + conditions.get(key) + "'";
			}
			row = this.getHibernateTemplate().bulkUpdate(hql);
		} catch (Exception e) {
			row = 0;
		}

		return row;
	}

	@SuppressWarnings("unchecked")
	protected int delete(Class clazz, String id) {
		int row = 0;
		try {
			String hql = "DELETE FROM " + clazz.getName() + " WHERE id=?";
			row = this.getHibernateTemplate().bulkUpdate(hql, id);
		} catch (Exception e) {
			e.printStackTrace();
			row = 0;
		}
		return row;
	}

	@SuppressWarnings("unchecked")
	protected int delete(Class clazz, int id) {
		int row = 0;
		try {
			String hql = "DELETE FROM " + clazz.getSimpleName() + " WHERE id=?";
			row = this.getHibernateTemplate().bulkUpdate(hql, id);
		} catch (Exception e) {
			e.printStackTrace();
			row = 0;
		}
		return row;
	}

	/**
	 * 功能：删除数�?
	 * 
	 * @param clazz
	 * @param id
	 */
	@SuppressWarnings("unchecked")
	protected int delete(Class clazz, int fid, int id) {
		int row = 0;
		try {
			String hql = "DELETE FROM " + clazz.getSimpleName()
					+ " WHERE xsxxEntity.id=?" + "and sxjdYrxxEntity.id=?";
			row = this.getHibernateTemplate().bulkUpdate(hql, fid, id);
		} catch (Exception e) {
			e.printStackTrace();
			row = 0;
		}
		return row;
	}

	@SuppressWarnings("unchecked")
	protected int batchDelete(Class clazz, long[] id) {
		int row = 0;
		if (id.length <= 0) {
			return 0;
		}
		try {
			String hql = "DELETE FROM " + clazz.getSimpleName()
					+ " WHERE id in(";
			for (int i = 0; i < id.length; i++) {
				if (i == id.length - 1) {
					hql += id[i] + ")";
					break;
				}
				hql += id[i] + ",";
			}
			row = this.getHibernateTemplate().bulkUpdate(hql);
		} catch (Exception e) {
			e.printStackTrace();
			row = 0;
		}
		return row;
	}

	@SuppressWarnings("unchecked")
	protected int batchDelete(Class clazz, int[] id) {
		int row = 0;
		if (id.length <= 0) {
			return 0;
		}
		try {
			String hql = "DELETE FROM " + clazz.getSimpleName()
					+ " WHERE id in(";
			for (int i = 0; i < id.length; i++) {
				if (i == id.length - 1) {
					hql += id[i] + ")";
					break;
				}
				hql += id[i] + ",";
			}
			row = this.getHibernateTemplate().bulkUpdate(hql);
		} catch (Exception e) {
			e.printStackTrace();
			row = 0;
		}
		return row;
	}

	@SuppressWarnings("unchecked")
	protected Object getByPk(Class clazz, int id) {
		Object result;
		try {
			result = this.getHibernateTemplate().get(clazz, id);
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected Object getByPk(Class clazz, long id) {
		Object result;
		try {
			result = this.getHibernateTemplate().get(clazz, id);
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected Object getByPk(Class clazz, String id) {
		Object result;
		try {
			result = this.getHibernateTemplate().get(clazz, id);
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected Object getEntityByConditions(String hql, Object[] param) {
		Object result;
		try {
			List resultList = this.getHibernateTemplate().find(hql, param);
			if (resultList.size() == 0) {
				result = null;
			} else {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected Object getEntityByConditions(Class cls, Map<Object, Object> cons) {
		Object result;
		try {
			String hql = "FROM " + cls.getSimpleName() + " where 1=1";
			for (Object key : cons.keySet()) {
				hql += " and " + key + "='" + cons.get(key) + "'";
			}
			List resultList = this.getHibernateTemplate().find(hql);
			if (resultList.size() == 0) {
				result = null;
			} else {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected Object getEntityByConditions(String hql, Object param) {
		Object result;
		try {
			List resultList = this.getHibernateTemplate().find(hql, param);
			if (resultList.size() == 0) {
				result = null;
			} else {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected Object getEntityByConditions(String hql) {
		Object result;
		try {
			List resultList = this.getHibernateTemplate().find(hql);
			if (resultList.size() == 0) {
				result = null;
			} else {
				result = resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List getList(String hql) {
		List result;
		try {
			result = this.getHibernateTemplate().find(hql);
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List getList(Class cls, List<ConditionBean> cons) {
		List result;
		try {
			String hql = this.buildParameter(new StringBuffer("FROM "
					+ cls.getSimpleName()), cons);
			result = this.getHibernateTemplate().find(hql);
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List getList(String hql, Object[] params) {
		List result;
		try {
			result = this.getHibernateTemplate().find(hql, params);
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List getList(Class clazz, Map<Object, Object> conditions) {
		List result = null;
		try {
			String hql = "FROM " + clazz.getName() + " where 1=1";
			for (Object key : conditions.keySet()) {
				hql += " and " + key + "='" + conditions.get(key) + "'";
			}
			result = this.getHibernateTemplate().find(hql);
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List getList(Class clazz) {
		List result = null;
		try {
			String hql = "FROM " + clazz.getName();
			result = this.getHibernateTemplate().find(hql);
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List query(Page page, String strhql) {
		// TODO;
		List result = null;
		try {
			// 创建查询
			Query query = this.getSession().createQuery(strhql);
			if (page != null) {
				if(page.getQueryParams()!=null)
				{
					this.setParameter(query, page.getQueryParams());
				}
				// 设置query的第�?条数�?
				query.setFirstResult(page.getStart());
				// 设置query的数据条�?
				query.setMaxResults(page.getLimit());
			}
			result = query.list();

		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List query(Page page, String strhql, Object obj) {
		// TODO;
		List result = null;
		try {
			// 创建查询
			Query query = this.getSession().createQuery(strhql);
			if (page != null) {
				// 设置query的第�?条数�?
				query.setFirstResult(page.getStart());
				// 设置query的数据条�?
				query.setMaxResults(page.getLimit());
			}
			// 设置参数
			query.setParameter(0, obj);
			result = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List query(Page page, String strhql, Object[] params) {
		// TODO;
		List result = null;
		try {
			// 创建查询
			Query query = this.getSession().createQuery(strhql);
			if (page != null) {
				// 设置query的第�?条数�?
				query.setFirstResult(page.getStart());
				// 设置query的数据条�?
				query.setMaxResults(page.getLimit());
			}
			// 设置参数
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
			result = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List queryBySql(Page page, String strsql) {
		// TODO;
		List result = null;
		try {
			// 创建查询
			Query query = this.getSession().createSQLQuery(strsql);
			if (page != null) {
				// 设置query的第�?条数�?
				query.setFirstResult(page.getStart());
				// 设置query的数据条�?
				query.setMaxResults(page.getLimit());
			}
			result = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List queryBySql(Page page, String strsql, List params) {
		// TODO;
		List result = null;
		try {
			// 创建查询
			Query query = this.getSession().createSQLQuery(strsql);
			if (page != null) {
				// 设置query的第�?条数�?
				query.setFirstResult(page.getStart());
				// 设置query的数据条�?
				query.setMaxResults(page.getLimit());
			}
			// 设置参数
			for (int i = 0; i < params.size(); i++) {
				query.setParameter(i, params.get(i));
			}
			result = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected int delete(Class clazz, java.io.Serializable id) {
		int row = 0;
		try {
			String hql = "DELETE FROM " + clazz.getName() + " WHERE id=?";
			row = this.getHibernateTemplate().bulkUpdate(hql, id);
		} catch (Exception e) {
			e.printStackTrace();
			row = 0;
		}
		return row;
	}

/*	@SuppressWarnings("unchecked")
	protected int getTotalCount(Class cls, Map hqlParams) {
		String hql = "FROM " + cls.getSimpleName();
		return this.getTotalCount(hql, hqlParams);
	}*/

	@SuppressWarnings("unchecked")
	protected int getTotalCount(Class cls, Map conditions) {
		String hql = "SELECT COUNT(*) FROM " + cls.getName() + " where 1=1";
		for (Object key : conditions.keySet()) {
			hql += " and " + key + "='" + conditions.get(key) + "'";
		}
		int count = 0;
		try {
			Object result = this.getSession().createQuery(hql).uniqueResult();
			if (result == null) {
				return 0;
			}
			count = Integer.parseInt(String.valueOf(result));
		} catch (Exception e) {
			count = 0;
		}
		return count;
	}

	protected int getTotalCount(Class cls, List<ConditionBean> conditions,Object...queryParams) {
		String hql = "SELECT COUNT(*) FROM " + cls.getName();
		hql = this.buildParameter(new StringBuffer(hql), conditions);
		int count = 0;
		try {
			Query query = this.getSession().createQuery(hql);
			if(queryParams!=null)
			{
				this.setParameter(query, queryParams);
			}
			Object result = query.uniqueResult();
			if (result == null) {
				return 0;
			}
			count = Integer.parseInt(String.valueOf(result));
		} catch (Exception e) {
			count = 0;
		}
		return count;
	}

	protected int getTotalCount(String hql, Object hqlParams) {
		Long amount = new Long(0);
		int sqlFrom = hql.indexOf("from") > hql.indexOf("FROM") ? hql
				.indexOf("from") : hql.indexOf("FROM");
		int sqlGroupby = hql.indexOf("group") > hql.indexOf("GROUP") ? hql
				.indexOf("group") : hql.indexOf("GROUP");
		int sqlOrderby = hql.indexOf("order by") > hql.indexOf("ORDER BY") ? hql
				.indexOf("order by")
				: hql.indexOf("ORDER BY");
		String countStr = "";
		// 因为此方法只取得查询的结果�?�数，所以将查询语句中可能存在的排序语句去掉来提高查询效�?
		if (sqlGroupby > 0) {
			countStr = "select count(count(*)) "
					+ hql.substring(sqlFrom, sqlOrderby);
		} else if (sqlOrderby > 0) {
			countStr = "select count(*) " + hql.substring(sqlFrom, sqlOrderby);
		} else {
			countStr = "select count(*) " + hql.substring(sqlFrom);
		}
		countStr = this.buildParameterForHql(new StringBuffer(countStr),
				hqlParams);
		Query qry = null;
		try {
			qry = this.getSession().createQuery(countStr);
			Object obj = qry.uniqueResult();
			if (obj != null) {
				amount = (Long) obj;
			} else {
				return 0;
			}
		} catch (HibernateException ex) {
		} finally {
			// this.getSession().clear();
		}
		return amount.intValue();
	}

	protected int getTotalCount(String hql, Object hqlParams, String inColumn,
			int[] inVals) {
		Integer amount = new Integer(0);
		int sqlFrom = hql.indexOf("from") > hql.indexOf("FROM") ? hql
				.indexOf("from") : hql.indexOf("FROM");
		int sqlGroupby = hql.indexOf("group") > hql.indexOf("GROUP") ? hql
				.indexOf("group") : hql.indexOf("GROUP");
		int sqlOrderby = hql.indexOf("order by") > hql.indexOf("ORDER BY") ? hql
				.indexOf("order by")
				: hql.indexOf("ORDER BY");
		String countStr = "";
		// 因为此方法只取得查询的结果�?�数，所以将查询语句中可能存在的排序语句去掉来提高查询效�?
		if (sqlGroupby > 0) {
			countStr = "select count(count(*)) "
					+ hql.substring(sqlFrom, sqlOrderby);
		} else if (sqlOrderby > 0) {
			countStr = "select count(*) " + hql.substring(sqlFrom, sqlOrderby);
		} else {
			countStr = "select count(*) " + hql.substring(sqlFrom);
		}
		countStr = this.buildParameterForHql(new StringBuffer(countStr),
				hqlParams);
		countStr = this.buildInParamForHql(new StringBuffer(countStr),
				inColumn, inVals);
		Query qry = null;
		try {
			qry = this.getSession().createQuery(countStr);
			Object obj = qry.uniqueResult();
			if (obj != null) {
				amount = (Integer) obj;
			} else {
				return 0;
			}
		} catch (HibernateException ex) {
		} finally {
			// this.getSession().clear();
		}
		return amount.intValue();
	}

	@SuppressWarnings("unchecked")
	protected int executeDelete(Class cls, Object hqlParams) {
		int fetchCount = 0;
		String entityName = cls.getSimpleName();
		StringBuffer sbDelHql = new StringBuffer("DELETE FROM " + entityName);
		String delHql = this.buildParameterForHql(sbDelHql, hqlParams);
		if (delHql.indexOf("and") != -1 || delHql.indexOf("AND") != -1) {
			fetchCount = this.executeModify(delHql, null);
		}
		return fetchCount;
	}

	protected int executeDelete(String deleteHql, Object queryParams) {
		int fetchCount = 0;
		fetchCount = this.executeModify(deleteHql, queryParams);
		return fetchCount;
	}

	protected int executeDelete(String deleteHql, Object queryParams,
			String inCol, int[] inVals) {
		int fetchCount = 0;
		deleteHql = this.buildInParamForHql(new StringBuffer(deleteHql), inCol,
				inVals);
		fetchCount = this.executeModify(deleteHql, queryParams);
		return fetchCount;
	}

	@SuppressWarnings("unchecked")
	protected List findAll(Class clz) {
		return super.getHibernateTemplate().find("FROM " + clz.getSimpleName());
	}

	@SuppressWarnings("unchecked")
	protected int updateAll(Class cls, Object hqlAttrs) {
		int fetchCount = 0;
		StringBuffer sbUpdateHql = new StringBuffer("UPDATE "
				+ cls.getSimpleName() + " set ");
		String updateHql = this.buildAttrForHql(sbUpdateHql, hqlAttrs);
		if (hqlAttrs != null) {
			fetchCount = this.executeModify(updateHql, null);
		}
		return fetchCount;
	}

	@SuppressWarnings("unchecked")
	protected int deleteAll(Class cls) {
		int fetchCount = 0;
		String hql = "DELETE FROM " + cls.getSimpleName();
		fetchCount = this.executeModify(hql, null);
		return fetchCount;
	}

	@SuppressWarnings("unchecked")
	protected int executeUpdate(Class cls, Object attrVals, Object params) {
		int fetchCount = 0;
		String entityName = cls.getSimpleName();
		StringBuffer sbUpdateHql = new StringBuffer("UPDATE " + entityName
				+ " set ");
		String updateHql = "";

		updateHql = this.buildAttrForHql(sbUpdateHql, attrVals);
		updateHql = this.buildParameterForHql(new StringBuffer(updateHql),
				params);
		if (attrVals != null
				&& (updateHql.indexOf("AND") != -1 || updateHql.indexOf("and") != -1)
				&& (updateHql.indexOf("where") != -1 || updateHql
						.indexOf("WHERE") != -1)) {
			fetchCount = this.executeModify(updateHql, null);
		}
		return fetchCount;
	}

	protected int executeUpdate(Class cls, Object attrVals, String column,
			int[] inVals) {
		int fetchCount = 0;
		String entityName = cls.getSimpleName();
		StringBuffer sbUpdateHql = new StringBuffer("UPDATE " + entityName
				+ " set ");
		String updateHql = "";

		updateHql = this.buildAttrForHql(sbUpdateHql, attrVals);
		updateHql = this.buildInParamForHql(new StringBuffer(updateHql),
				column, inVals);
		if (attrVals != null
				&& (updateHql.indexOf("AND") != -1 || updateHql.indexOf("and") != -1)
				&& (updateHql.indexOf("where") != -1 || updateHql
						.indexOf("WHERE") != -1)) {
			fetchCount = this.executeModify(updateHql, null);
		}
		return fetchCount;
	}

	@SuppressWarnings("unchecked")
	protected Object executeScalar(Class cls, Object hqlParams) {
		Object obj = null;
		String entityName = cls.getSimpleName();
		StringBuffer sbScalarHql = new StringBuffer("FROM " + entityName);
		Session session = this.getSession();
		String scalarHql = this.buildParameterForHql(sbScalarHql, hqlParams);
		Query query = session.createQuery(scalarHql);
		obj = query.uniqueResult();
		return obj;
	}

	protected Object executeScalar(String hql, Object queryParams) {
		Object obj = null;
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		this.setParameter(query, queryParams);
		obj = query.uniqueResult();
		return obj;
	}

	protected Object executeSQLScalar(String sql, Class cls, Object queryParams) {
		Object obj = null;
		Session session = this.getSession();
		Query query = null;
		if (cls != null) {
			query = session.createSQLQuery(sql).addEntity(cls);
		} else {
			query = session.createSQLQuery(sql);
		}
		this.setParameter(query, queryParams);
		obj = query.uniqueResult();
		return obj;
	}
	
	protected Object executeSQLScalar(String sql, Class cls) {
		return executeSQLScalar(sql,cls,null);
	}

	protected Object executeSQLScalar(String sql, Object queryParams) {
		return this.executeSQLScalar(sql, null, queryParams);
	}

	protected Object executeSQLScalar(String sql) {
		return this.executeSQLScalar(sql, null);
	}

	protected Object executeSQLScalar(String sql, Object queryParams,
			String inColumn, int[] inVals) {
		Object obj = null;
		Session session = this.getSession();
		sql = this.buildInParamForHql(new StringBuffer(sql), inColumn, inVals);
		if (sql.indexOf("in") == -1 && sql.indexOf("IN") == -1) {
			return new ArrayList(0);
		}
		Query query = session.createSQLQuery(sql);
		this.setParameter(query, queryParams);
		obj = query.uniqueResult();
		return obj;
	}

	private int executeModify(String hql, Object params) {
		Session session = this.getSession();
		int rowCount = 0;

		Query query = session.createQuery(hql);
		this.setParameter(query, params);
		rowCount = query.executeUpdate();
		return rowCount;
	}

	@SuppressWarnings("unchecked")
	protected List executeQuery(Class cls) {
		List result;
		try {
			String hql = "FROM " + cls.getSimpleName();
			result = this.getHibernateTemplate().find(hql);
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected List executeQuery(String hql, Object queryParams) {
		Session session = this.getSession();
		List list = null;
		Query query = session.createQuery(hql);
		this.setParameter(query, queryParams);
		list = query.list();
		return list;
	}

	protected List executeQuery(String hql) {
		Session session = this.getSession();
		List list = null;
		Query query = session.createQuery(hql);
		list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	protected List executeQuery(String hql, Object queryParams,
			Map<String, SortOrder> orderMap) {
		Session session = this.getSession();
		List list = null;
		String[] orderColumns = null;
		SortOrder[] sortOrders = null;
		if (orderMap != null && orderMap.keySet().size() > 0) {
			int ind = 0;
			orderColumns = new String[orderMap.keySet().size()];
			sortOrders = new SortOrder[orderMap.keySet().size()];
			for (Iterator iter = orderMap.entrySet().iterator(); iter.hasNext();) {
				Map.Entry<String, SortOrder> entry = (Map.Entry) iter.next();
				orderColumns[ind] = entry.getKey();
				sortOrders[ind] = entry.getValue();
				ind++;
			}
			hql = this.buildSortForHql(new StringBuffer(hql), orderColumns,
					sortOrders);
		}
		Query query = session.createQuery(hql);
		if (queryParams != null) {
			setParameter(query, queryParams);
		}
		list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	protected List executeQuery(String hql, Object params, int firstIndex,
			int maxCount) {
		Session session = this.getSession();
		List list = null;
		Query query = session.createQuery(hql).setFirstResult(firstIndex)
				.setMaxResults(maxCount);
		if (params != null) {
			setParameter(query, params);
		}
		list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	protected List executeQuery(Class cls, Object hqlParams) {
		Session session = this.getSession();
		List list = null;
		String entityName = cls.getSimpleName();
		StringBuffer sbQueryHql = new StringBuffer("FROM " + entityName);
		String queryHql = this.buildParameterForHql(sbQueryHql, hqlParams);
		Query query = session.createQuery(queryHql);

		list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	protected List executeQuery(Class cls, Object hqlParams, int firstIndex,
			int maxCount) {
		Session session = this.getSession();
		List list = null;
		String entityName = cls.getSimpleName();
		StringBuffer sbQueryHql = new StringBuffer("FROM " + entityName);
		String queryHql = this.buildParameterForHql(sbQueryHql, hqlParams);
		Query query = session.createQuery(queryHql).setFirstResult(firstIndex)
				.setMaxResults(maxCount);

		list = query.list();
		return list;
	}

	protected List executeQuery(String hql, Object queryParams,
			String inColumn, int[] inVals) {
		Session session = this.getSession();
		List list = null;
		String queryHql = this.buildInParamForHql(new StringBuffer(hql),
				inColumn, inVals);
		if (queryHql.indexOf("in") == -1 && queryHql.indexOf("IN") == -1) {
			return new ArrayList(0);
		}
		Query query = session.createQuery(queryHql);
		this.setParameter(query, queryParams);
		list = query.list();
		return list;
	}

	protected List executeQuery(String hql, String inColumn, int[] inVals) {
		return executeQuery(hql, null, inColumn, inVals);
	}

	@SuppressWarnings("unchecked")
	protected List<Object[]> executeSQLQuery(String sql, Object sqlParams,
			int firstIndex, int maxCount) {
		Session session = this.getSession();
		List<Object[]> objsList = null;
		String querySQL = this.buildParameterForHql(new StringBuffer(sql),
				sqlParams);
		Query query = session.createSQLQuery(querySQL).setFirstResult(
				firstIndex).setMaxResults(maxCount);
		objsList = query.list();
		return objsList;
	}

	@SuppressWarnings("unchecked")
	protected List<Object[]> executeSQLQuery(String sql, Object sqlParams,
			Object queryParams) {
		Session session = this.getSession();
		List<Object[]> objsList = null;
		String querySQL = this.buildParameterForHql(new StringBuffer(sql),
				sqlParams);
		Query query = session.createSQLQuery(querySQL);
		this.setParameter(query, queryParams);
		objsList = query.list();
		return objsList;
	}

	@SuppressWarnings("unchecked")
	protected List executeSQLQuery(String sql) {
		return executeSQLQuery(sql, null);
	}

	@SuppressWarnings("unchecked")
	protected List executeSQLQuery(String sql, Object queryParams) {
		Session session = this.getSession();
		List objsList = null;
		Query query = session.createSQLQuery(sql);
		this.setParameter(query, queryParams);
		objsList = query.list();
		return objsList;
	}

	@SuppressWarnings("unchecked")
	protected List executeSQLQuery(String sql, List<ConditionBean> conditions) {
		Session session = this.getSession();
		List objsList = null;
		Query query = session.createSQLQuery(sql);
		sql = this.buildParameter(new StringBuffer(sql), conditions);
		objsList = query.list();
		return objsList;
	}

	@SuppressWarnings("unchecked")
	protected List executeSQLQuery(String sql, Class cls,
			List<ConditionBean> conditions) {
		Session session = this.getSession();
		List objsList = null;
		Query query = null;
		sql = this.buildParameter(new StringBuffer(sql), conditions);
		if (cls == null) {
			query = session.createSQLQuery(sql);
		} else {
			query = session.createSQLQuery(sql).addEntity(cls);
		}
		objsList = query.list();
		return objsList;
	}

	protected List executeSQLQuery(String sql, Class<?> clzz, Object sqlParams,
			Object queryParams) {
		Session session = this.getSession();
		List objsList = null;
		String querySQL = this.buildParameterForHql(new StringBuffer(sql),
				sqlParams);
		Query query = null;
		if (clzz == null) {
			query = session.createSQLQuery(querySQL);
		} else {
			query = session.createSQLQuery(querySQL).addEntity(clzz);
		}
		this.setParameter(query, queryParams);
		objsList = query.list();
		return objsList;
	}

	protected List executeSQLQuery(String sql, Class<?> clzz, Object queryParams) {
		return executeSQLQuery(sql, clzz, null, queryParams);
	}

	protected List executeSQLQuery(String sql, Object queryParams,
			String inColumn, int[] inVals) {
		return executeSQLQuery(sql, null, null, queryParams, inColumn, inVals);
	}

	protected List executeSQLQuery(String sql, String inColumn, int[] inVals) {
		return executeSQLQuery(sql, null, null, null, inColumn, inVals);
	}

	protected List executeSQLQuery(String sql, Class clz, String inColumn,
			int[] inVals) {
		return executeSQLQuery(sql, clz, null, null, inColumn, inVals);
	}

	protected List<Object[]> executeSQLQuery(String sql, Class clz,
			Object sqlParams, Object queryParams, String inColumns, int[] inVals) {
		Session session = this.getSession();
		List<Object[]> objsList = null;
		String querySQL = this.buildParameterForHql(new StringBuffer(sql),
				sqlParams);
		if (!StringUtil.isNullOrEmpty(inColumns) && inVals != null
				&& inVals.length > 0) {
			querySQL = this.buildInParamForHql(new StringBuffer(querySQL),
					inColumns, inVals);
		}
		Query query = null;
		if (clz == null)
			query = session.createSQLQuery(querySQL);
		else
			query = session.createSQLQuery(querySQL).addEntity(clz);
		this.setParameter(query, queryParams);
		objsList = query.list();
		return objsList;
	}

	protected int batchSaveOrUpdaete(List transientInstances) {
		try {
			this.getHibernateTemplate().saveOrUpdateAll(transientInstances);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	protected int batchAdd(List transientInstances, int commitNum) {
		if (transientInstances == null || transientInstances.size() == 0) {
			return 0;
		}
		Transaction tx = null;
		HibernateTemplate hbTemp = getHibernateTemplate();
		hbTemp.setAllowCreate(true);
		Session session = hbTemp.getSessionFactory().openSession();
		int fetchCount = 0;
		try {
			tx = session.beginTransaction();
			int i = 0;
			for (Object transientInstance : transientInstances) {
				session.save(transientInstance);
				i++;
				if (i == commitNum) {
					tx.commit();
					fetchCount += i;
					i = 0;
					session.flush();
					session.clear();
					tx = session.beginTransaction();
				}
			}
			if (i != 0) {
				tx.commit();
				fetchCount += i;
				session.flush();
				session.clear();
			}
			return fetchCount;
		} catch (Exception re) {
			if (tx != null && tx.isActive())
				tx.rollback();
			re.printStackTrace();
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	protected int batchUpdate(List transientInstances, int commitNum) {
		Transaction tx = null;
		HibernateTemplate hbTemp = getHibernateTemplate();
		hbTemp.setAllowCreate(true);
		Session session = hbTemp.getSessionFactory().openSession();
		int fetchCount = 0;
		try {
			tx = session.beginTransaction();
			int i = 0;
			for (Object transientInstance : transientInstances) {
				session.update(transientInstance);
				i++;
				if (i == commitNum) {
					tx.commit();
					fetchCount += i;
					i = 0;
					session.flush();
					session.clear();
					tx = session.beginTransaction();
				}
			}
			if (i != 0) {
				tx.commit();
				fetchCount += i;
				session.flush();
				session.clear();
			}
			return fetchCount;
		} catch (Exception re) {
			if (tx != null && tx.isActive())
				tx.rollback();
			return -1;
		}
	}

	protected int executeSQLUpdate(String sql, Object paramObject)
			throws SQLException {
		int affectedCount = 0;
		Session session = this.getSession();
		Connection conn = null;

		session.setCacheMode(CacheMode.IGNORE);
		PreparedStatement stmt = null;

		conn = SessionFactoryUtils.getDataSource(getSessionFactory())
				.getConnection();
		conn.setAutoCommit(false);
		if (paramObject != null) {
			if (paramObject instanceof Map) {
				Map<String, String> dataMap = (Map<String, String>) paramObject;
				Map<Integer, String> paramMap = this.antlrSQLParam(sql);
				try {
					stmt = conn.prepareStatement(paramMap.get(0));
					int paramsCount = paramMap.size();
					for (int j = 1; j < paramsCount; j++) {
						stmt.setObject(j, dataMap.get(paramMap.get(j)));
					}
					affectedCount = stmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
					conn.rollback();
				}
			} else if (paramObject instanceof String[]) {
				String[] values = (String[]) paramObject;
				try {
					stmt = conn.prepareStatement(sql);
					for (int i = 0; i < values.length; i++) {
						stmt.setObject(i + 1, values[i]);
					}
					affectedCount = stmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
					conn.rollback();
				}
			}
		} else {
			try {
				stmt = conn.prepareStatement(sql);
				affectedCount = stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				conn.rollback();
			}
		}
		conn.commit();
		return affectedCount;
	}

	protected int executeSQLUpdate(String sql) throws SQLException {
		int affectedCount = 0;
		Session session = this.getSession();
		session.setCacheMode(CacheMode.IGNORE);

		Connection conn = null;
		PreparedStatement stmt = null;
		conn = SessionFactoryUtils.getDataSource(getSessionFactory())
				.getConnection();
		conn.setAutoCommit(false);
		try {
			stmt = conn.prepareStatement(sql);
			affectedCount = stmt.executeUpdate();
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		}
		conn.commit();
		return affectedCount;
	}

	protected int[] executeSQLBatchUpdate(String[] sqls) {
		int[] affectedCount = null;
		Session session = this.getSession();
		session.setCacheMode(CacheMode.IGNORE);

		Connection conn = null;
		Statement st = null;
		try {
			conn = SessionFactoryUtils.getDataSource(getSessionFactory())
					.getConnection();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			if (sqls != null && sqls.length > 0) {
				for (String sql : sqls) {
					if (!"".equals(sql))
						;
					st.addBatch(sql);
				}
			} else {
				return null;
			}
			affectedCount = st.executeBatch();
			st.clearBatch();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return affectedCount;
	}

	public int[] batchSQLUpdate(String sql, List<Map<String, String>> mapList) {
		int[] affectedCount = null;
		Session session = this.getSession();
		Transaction tran = session.beginTransaction();
		session.setCacheMode(CacheMode.IGNORE);

		PreparedStatement stmt = null;
		Map<Integer, String> paramMap = this.antlrSQLParam(sql);
		try {
			stmt = SessionFactoryUtils.getDataSource(getSessionFactory())
					.getConnection().prepareStatement(paramMap.get(0));
			for (int i = 0; i < mapList.size(); i++) {
				Map<String, String> valMap = mapList.get(i);
				int paramsCount = paramMap.size();
				for (int j = 1; j < paramsCount; j++) {
					stmt.setObject(j, valMap.get(paramMap.get(j)));
				}
				stmt.addBatch();
			}
			affectedCount = stmt.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace(); // To change body of catch statement use
			// File | Settings | File Templates.
			tran.rollback();
		}
		tran.commit();
		return affectedCount;
	}

	@SuppressWarnings("unchecked")
	private void setParameter(Query query, Object queryParams, Integer... index) {
		if (queryParams != null) {
			if (queryParams instanceof ArrayList) {
				ArrayList params = (ArrayList) queryParams;
				for (int i = 0; i < params.size(); i++) {
					query.setParameter(i, params.get(i));
				}
			} else if (queryParams instanceof Hashtable) {
				Hashtable params = (Hashtable) queryParams;
				Iterator iter = params.keySet().iterator();
				while (iter.hasNext()) {
					String paramName = (String) iter.next();
					Object paramValue = params.get(paramName);
					query.setParameter(paramName, paramValue);
				}
			} else if (queryParams instanceof HashMap) {
				HashMap params = (HashMap) queryParams;
				Iterator iter = params.keySet().iterator();
				while (iter.hasNext()) {
					String paramName = (String) iter.next();
					Object paramValue = params.get(paramName);
					query.setParameter(paramName, paramValue);
				}
			} else if (queryParams instanceof String[]) {
				String[] params = (String[]) queryParams;
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i, params[i]);
				}
			} else if (queryParams instanceof int[]
					|| queryParams instanceof Integer[]) {
				int[] params = (int[]) queryParams;
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i, params[i]);
				}
			} else if (queryParams instanceof long[]
					|| queryParams instanceof Long[]) {
				long[] params = (long[]) queryParams;
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i, params[i]);
				}
			} else if (queryParams instanceof float[]
					|| queryParams instanceof Float[]) {
				float[] params = (float[]) queryParams;
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i, params[i]);
				}
			} else if (queryParams instanceof double[]
					|| queryParams instanceof Double[]) {
				double[] params = (double[]) queryParams;
				for (int i = 0; i < params.length; i++) {
					query.setParameter(i, params[i]);
				}
			} else if (queryParams instanceof String
					|| queryParams instanceof Integer) {
				if (index != null && index.length != 0)
				{
					query.setParameter(index[0], queryParams);
				}
				else
				{
					query.setParameter(0, queryParams);
				}
			} else if (queryParams instanceof Object[]) {
				Object[] objs = (Object[]) queryParams;
				for (int i = 0; i < objs.length; i++) {
					setParameter(query, objs[i], i);
				}
			} else {
				throw new IllegalArgumentException(
						"Query Arguments invalidated,the parameter type of Query is not matched in ArrayList,HashMap,Hashtable,String");
			}
		}
	}

	@SuppressWarnings("unchecked")
	private String buildAttrForHql(StringBuffer sbHql, Object hqlAttrs) {
		String attrPart = "";
		if (sbHql != null) {
			if (hqlAttrs != null) {
				if (hqlAttrs instanceof Hashtable) {
					Hashtable attrs = (Hashtable) hqlAttrs;
					Iterator iter = attrs.keySet().iterator();
					while (iter.hasNext()) {
						String attrName = (String) iter.next();
						Object attrValue = attrs.get(attrName);
						sbHql.append(attrName + "='" + attrValue + "',");
					}
				} else if (hqlAttrs instanceof HashMap) {
					HashMap attrs = (HashMap) hqlAttrs;
					Iterator iter = attrs.keySet().iterator();
					while (iter.hasNext()) {
						String attrName = (String) iter.next();
						Object attrValue = attrs.get(attrName);
						sbHql.append(attrName + "='" + attrValue + "',");
					}
				} else {
					throw new IllegalArgumentException(
							"Attribute Arguments invalidated,the parameter type of Attribute is not matched in HashMap,Hashtable");
				}
				attrPart = sbHql.substring(0, sbHql.length() - 1);
			}
		}
		return attrPart;

	}

	@SuppressWarnings("unchecked")
	private String buildParameterForHql(StringBuffer sbHql, Object hqlParams) {
		String paramsPart = "";
		if (sbHql != null) {
			if (sbHql.indexOf("group by") != -1
					&& sbHql.indexOf("GROUP BY") != -1) {
				if (sbHql.indexOf("having") < 0 && sbHql.indexOf("HAVING") < 0) {
					sbHql.append(" having 1=1");
				}
			} else {
				if (sbHql.indexOf("where") < 0 && sbHql.indexOf("WHERE") < 0) {
					sbHql.append(" where 1=1");
				}
			}
			if (hqlParams != null) {
				if (hqlParams instanceof Hashtable) {
					Hashtable params = (Hashtable) hqlParams;
					Iterator iter = params.keySet().iterator();
					while (iter.hasNext()) {
						String paramName = (String) iter.next();
						Object paramValue = params.get(paramName);
						sbHql.append(" and " + paramName + "='" + paramValue
								+ "'");
					}
				} else if (hqlParams instanceof HashMap) {
					HashMap params = (HashMap) hqlParams;
					Iterator iter = params.keySet().iterator();
					while (iter.hasNext()) {
						String paramName = (String) iter.next();
						Object paramValue = params.get(paramName);
						sbHql.append(" and " + paramName + "='" + paramValue
								+ "'");
					}
				} else {
					throw new IllegalArgumentException(
							"Parameter Arguments invalidated,the parameter type of Query is not matched in HashMap,Hashtable");
				}
			}
			paramsPart = String.valueOf(sbHql);
		}
		return paramsPart;
	}

	@SuppressWarnings("unchecked")
	private String buildInParamForHql(StringBuffer sbHql, String column,
			int[] inVals) {
		String paramsPart = "";
		if (sbHql != null) {
			if (sbHql.indexOf("group by") != -1
					|| sbHql.indexOf("GROUP BY") != -1) {
				if (sbHql.indexOf("having") < 0 && sbHql.indexOf("HAVING") < 0) {
					sbHql.append(" having 1=1");
				}
			} else {
				if (sbHql.indexOf("where") < 0 && sbHql.indexOf("WHERE") < 0) {
					sbHql.append(" where 1=1");
				}
			}
			if (column != null && inVals != null && inVals.length > 0) {
				sbHql.append(" and " + column + " in(");
				for (Object obj : inVals) {
					sbHql.append("'" + obj + "',");
				}
				paramsPart = sbHql.substring(0, sbHql.length() - 1) + ")";
			} else {
				paramsPart = sbHql.append(" and 1<>1").toString();
			}
		}
		return paramsPart;
	}

	@SuppressWarnings("unchecked")
	private String buildSortForHql(StringBuffer sbHql, String sortColumn,
			SortOrder sortOrder) {
		String paramsPart = "";
		if (sbHql != null) {
			if (sbHql.indexOf("order by") < 0 && sbHql.indexOf("ORDER BY") < 0) {
				sbHql.append(" order by ");
			}
			if (sortOrder != null) {
				sbHql.append(sortColumn + " " + sortOrder);
			} else {
				sbHql.append(sortColumn);
			}
		}
		paramsPart = String.valueOf(sbHql);
		return paramsPart;
	}

	@SuppressWarnings("unchecked")
	private String buildSortForHql(StringBuffer sbHql, String[] sortColumns,
			SortOrder[] sortOrders) {
		String paramsPart = "";
		if (sbHql != null && sortColumns != null) {
			if (sbHql.indexOf("order by") < 0 && sbHql.indexOf("ORDER BY") < 0) {
				sbHql.append(" order by ");
			}
			if (sortOrders == null) {
				for (String orderCol : sortColumns) {
					sbHql.append(orderCol + ",");
				}
			} else {
				for (int i = 0; i < sortColumns.length; i++) {
					if (sortOrders[i] != null) {
						sbHql
								.append(sortColumns[i] + " " + sortOrders[i]
										+ ",");
					} else {
						sbHql.append(sortColumns[i] + ",");
					}
				}
			}
		}
		paramsPart = String.valueOf(sbHql.substring(0, sbHql.length() - 1));
		return paramsPart;
	}

	private Map<Integer, String> antlrSQLParam(String sql) {
		Map<Integer, String> paramsMap = null;
		if (sql == null || sql.indexOf(":") < 0) {
			return paramsMap;
		}
		String[] paramsArray = sql.split("=");
		if (paramsArray.length == 1) {
			paramsArray = sql.split(":");
		}
		paramsMap = new HashMap<Integer, String>(paramsArray.length - 1);
		String[] replaceStr = new String[paramsArray.length - 1];
		for (int i = 1; i < paramsArray.length; i++) {
			String overStr = "";
			String tempStr = paramsArray[i];
			String[] ttStr = tempStr.split(" ");
			overStr = ttStr[0];
			String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/~！@#�?%…�??&*（）—�??+|{}【�?��?�；：�?��?��?��?�，、]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(overStr);
			overStr = m.replaceAll("").trim();
			paramsMap.put(i, overStr);
			replaceStr[i - 1] = overStr;
		}
		String overSql = sql;
		for (String paramStr : replaceStr) {
			overSql = overSql.replace(":" + paramStr, "?");
		}
		paramsMap.put(0, overSql);
		return paramsMap;
	}

	protected Class<?> getClassByTableName(String tableName) {
		Class<?> clzz = null;
		Configuration cfg = new Configuration().configure();
		Iterator<?> iter = cfg.getTableMappings();
		Iterator<?> iter1 = cfg.getClassMappings();
		while (iter.hasNext()) {
			Object obj = iter.next();
			System.out.println(obj);
		}
		while (iter1.hasNext()) {
			Object obj = iter.next();
			System.out.println(obj);
		}
		return clzz;
	}

	@SuppressWarnings("unchecked")
	protected int updateByCodesAndConditions(Class clazz,
			Map<Object, Object> codes, Map<Object, Object> conditions) {
		if (codes.size() == 0 || conditions.size() == 0) {
			return 0;
		}
		String hql = "UPDATE " + clazz.getSimpleName() + " set ";
		int i = 0;
		for (Object key : codes.keySet()) {
			i++;
			if (i == codes.keySet().size()) {
				hql += key + "='" + codes.get(key) + "' where 1=1";
			} else {
				hql += key + "='" + codes.get(key) + "',";
			}
		}
		for (Object key : conditions.keySet()) {
			hql += " and " + key + "='" + conditions.get(key) + "'";
		}
		int row = this.getHibernateTemplate().bulkUpdate(hql);
		return row;
	}

	protected List executeQuery(Class clzz, Page page) {
		StringBuffer sbQL = new StringBuffer("FROM " + clzz.getSimpleName());
		String ql = this.buildParameter(sbQL, page.getConditionList());
		return this.query(page, ql);
	}

	protected String buildParameter(StringBuffer sbQL,
			List<ConditionBean> conditions) {
		String strJoin = "";
		String strOrder = "";
		String strGroup = "";
		
		StringBuffer sbCond = null;
		
		if(sbQL.toString().toUpperCase().indexOf("WHERE")==-1)
		{
			sbCond = new StringBuffer(" WHERE 1=1");
		}else
		{
			sbCond = new StringBuffer();
		}
		if (conditions != null && conditions.size() > 0) {
			for (ConditionBean cond : conditions) {
				Rule rule = cond.getRule();
				switch (rule) {
				case EQUAL:
					sbCond.append(" and " + cond.getColumn() + " ='"
							+ cond.getValue() + "'");
					break;
				case NOTEQUAL:
					sbCond.append(" and " + cond.getColumn() + " <>'"
							+ cond.getValue() + "'");
					break;
				case LLIKE:
					sbCond.append(" and " + cond.getColumn() + " like '"
							+ cond.getValue() + "%'");
					break;
				case RLIKE:
					sbCond.append(" and " + cond.getColumn() + " like '%"
							+ cond.getValue() + "'");
					break;
				case ALIKE:
					sbCond.append(" and " + cond.getColumn() + " like '%"
							+ cond.getValue() + "%'");
					break;
				case IN:
					sbCond.append(" and " + cond.getColumn() + " in ("
							+ cond.getValue() + ")");
					break;
				case NOTIN:
					sbCond.append(" and " + cond.getColumn() + " not in ("
							+ cond.getValue() + ")");
					break;
				case BIGTHAN:
					sbCond.append(" and " + cond.getColumn() + " > '"
							+ cond.getValue() + "'");
					break;
				case LITTLETHAN:
					sbCond.append(" and " + cond.getColumn() + " < '"
							+ cond.getValue() + "'");
					break;
				case GROUPBY:
					strGroup += cond.getColumn() + ",";
					break;
				case ORDERBYASC:
					strOrder += cond.getColumn() + ",";
					break;
				case ORDERBYDESC:
					strOrder += cond.getColumn() + " DESC,";
					break;
				case INNERJOIN:
					strJoin += " INNER JOIN "+cond.getColumn()+"."+cond.getValue()+" "
					+cond.getValue();
					break;
				default:
					break;
				}
			}
			if(!"".equals(strJoin) && strJoin.length() > 0)
			{
				sbQL.append(strJoin);
			}
			if (!"".equals(strGroup) && strGroup.length() > 0) {
				sbQL.append(" GROUP BY "
						+ strGroup.substring(0, strGroup.length() - 1));
			}
			sbQL.append(sbCond);
			if (!"".equals(strOrder) && strOrder.length() > 0) {
				sbQL.append(" ORDER BY "
						+ strOrder.substring(0, strOrder.length() - 1));
			}
		}
		return sbQL.toString();
	}

	protected List executeQuery(String hql, Page page) {
		StringBuffer sbQL = new StringBuffer(hql);
		String ql = this.buildParameter(sbQL, page.getConditionList());
		return this.query(page, ql);
	}

	protected int getTotalCount(String hql, List<ConditionBean> conditions) {
		hql = this.buildParameter(new StringBuffer(hql), conditions);
		int count = 0;
		try {
			Object result = this.getSession().createQuery(hql).uniqueResult();
			if (result == null) {
				return 0;
			}
			count = Integer.parseInt(String.valueOf(result));
		} catch (Exception e) {
			count = 0;
		}
		return count;
	}
}
