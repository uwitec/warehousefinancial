package com.wfms.common.dao;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.wfms.common.orm.BaseEntity;
import com.wfms.common.orm.Condition;
import com.wfms.common.orm.Page;
import com.wfms.common.util.HibernateUtils;
import com.wfms.common.util.ReflectionUtils;

public class BaseDao<T extends BaseEntity> extends HibernateDaoSupport {
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	protected int getCount(Class<?> clazz, List<Condition> conditions) {
		String queryStr = "SELECT COUNT(*) FROM " + clazz.getSimpleName();
		QueryParams queryParams = buildConditions(conditions);
		Query query = null;
		if (queryParams != null) {
			query = this.getSession().createQuery(
					queryStr + queryParams.getQueryString());
			buildParameter(query, queryParams);
		} else {
			query = this.getSession().createQuery(queryStr);
		}
		List<?> results = query.list();
		if (results.size() != 1) {
			return 0;
		}
		int result = 0;
		try {
			result = Integer.parseInt(results.get(0).toString());
		} catch (Exception e) {
			return 0;
		}
		return result;
	}

	public int getCount(String hql, Object... queryParam) {
		Query query = getSession().createQuery(hql);
		setParameter(query,
				queryParam != null && queryParam.length > 0 ? queryParam[0]
						: null);
		Object obj = query.uniqueResult();
		if (obj instanceof Integer || obj.getClass() == int.class) {
			return ((Integer) obj);
		} else if (obj instanceof Long || obj.getClass() == long.class) {
			return ((Long) obj).intValue();
		} else if (obj instanceof BigDecimal) {
			return ((BigDecimal) obj).intValue();
		}
		return 0;
	}

	protected Page find(Class<?> clazz, Page page) {
		String queryStr = "FROM " + clazz.getSimpleName();
		QueryParams queryParams = buildConditions(page.getConditions());
		Query query = null;
		if (queryParams != null) {
			query = this.getSession().createQuery(
					queryStr + queryParams.getQueryString()
							+ QLAppender.orderBy(page));
			buildParameter(query, queryParams);
		} else {
			query = this.getSession().createQuery(
					queryStr + QLAppender.orderBy(page));
		}
		query.setFirstResult(page.getStart());
		query.setMaxResults(page.getLimit());
		List<?> list = query.list();
		page.setResults(list);
		page.setTotal(this.getCount(clazz, page.getConditions()));
		return page;
	}

	private QueryParams buildConditions(List<Condition> conditions) {
		StringBuffer strbf = QLAppender.preWhere();
		Object[] queryParmas = new Object[0];
		if (conditions != null && conditions.size() != 0) {
			queryParmas = new Object[conditions.size()];
			QLAppender.conditions(strbf, queryParmas, conditions);
		}
		return new QueryParams(strbf.toString(), queryParmas);
	}

	private void buildParameter(Query query, QueryParams queryParams) {
		if (queryParams == null) {
			return;
		}
		for (int i = 0; i < queryParams.getQueryParams().length; i++) {
			if (queryParams.getQueryParams()[i] instanceof Collection) {// 如果是集合需要调用setParameterList方法设置参数
				query.setParameterList("list" + i, (Collection<?>) queryParams
						.getQueryParams()[i]);
			} else {
				query.setParameter(i, queryParams.getQueryParams()[i]);
			}
		}
	}

	/**
	 * 通用持久化对象操作方法
	 */

	/**
	 * 参数递归标记
	 */
	private int paramLevel = 0;
	/**
	 * list参数序列
	 */
	private int listParamIdx = 0;

	protected Class<T> entityClass;

	public BaseDao(Class<T> cls) {
		this.entityClass = cls;
	}

	public BaseDao() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(super
				.getClass());
	}

	public Serializable save(T entity) {
		Assert.notNull(entity, "entity不能为空");
		return getSession().save(entity);
	}

	public void update(T entity) {
		Assert.notNull(entity, "entity不能为空");
		getSession().merge(entity);
	}

	public void delete(T entity) {
		Assert.notNull(entity, "entity不能为空");
		getSession().delete(entity);
	}

	public void delete(String id) {
		Assert.notNull(id, "id不能为空");
		getSession().delete(this.fetch(id));
	}

	@SuppressWarnings("unchecked")
	public T fetch(String id) {
		Assert.notNull(id, "id不能为空");
		Query query = getSession().createQuery(
				"FROM " + this.entityClass.getName() + " WHERE ID=?");
		query.setString(0, id);
		return (T) query.uniqueResult();
		// Clob,Blob流已被关闭
		// getSession().load(this.entityClass, id);
	}

	public Page fetch(Page page) {
		return this.find(entityClass, page);
	}

	protected List<?> findAll() {
		return getHibernateTemplate().loadAll(entityClass);
		// return this.getHibernateTemplate()
		// .find("FROM " + entityClass.getName());
	}

	/**
	 * 复合条件查询封装方法
	 * 
	 * @param page
	 * @return
	 */
	public Page find(Page page) {
		Assert.notNull(page, "page不能为空");
		Criteria criteria = HibernateUtils.createCriteria(getSession(),
				this.entityClass, null);
		HibernateUtils.setParameter(criteria, page);
		page.setResults(criteria.list());
		return page;
	}

	@SuppressWarnings("unchecked")
	protected List<T> executeQuery(String[] wheres, Object param) {
		StringBuffer hqlBuffer = new StringBuffer("FROM "
				+ this.entityClass.getName());
		hqlBuffer.append(QLAppender.where(wheres));
		Query query = this.getSession().createQuery(hqlBuffer.toString());
		setParameter(query, param);
		resumeIdx();
		List<T> list = (List<T>) query.list();
		return list;
	}

	protected List<?> executeQuery(String hql, Object param) {
		Query query = this.getSession().createQuery(hql);
		setParameter(query, param);
		resumeIdx();
		List<?> list = query.list();
		return list;
	}

	protected List<?> executeQuery(String hql, Object param, int start,
			int limit) {
		Query query = this.getSession().createQuery(hql);
		setParameter(query, param);
		resumeIdx();
		query.setFirstResult(start);
		query.setMaxResults(limit);
		List<?> list = query.list();
		return list;
	}

	protected List<?> executeSQLQuery(String sql, Object param,
			Class<?>... persistCls) {
		return executeSQLQuery(sql, param, -1, -1, persistCls);
	}

	protected Object executeScalar(String hql, Object param) {
		Query query = null;
		QueryParams queryParams = assignToCondList(param);
		hql = hql + (queryParams != null ? queryParams.getQueryString() : "");
		query = this.getSession().createQuery(hql);
		if (queryParams != null) {
			buildParameter(query, queryParams);
		} else {
			setParameter(query, param);
			resumeIdx();
		}
		Object result = query.uniqueResult();
		return result;
	}

	protected List<?> executeSQLQuery(String sql, Object param, int start,
			int limit, Class<?>... persistCls) {
		Query query = null;
		QueryParams queryParams = assignToCondList(param);
		sql = sql + (queryParams != null ? queryParams.getQueryString() : "");
		if (persistCls != null && persistCls.length != 0) {
			query = this.getSession().createSQLQuery(sql).addEntity(
					persistCls[0]);
		} else {
			query = this.getSession().createSQLQuery(sql);
		}
		if (queryParams != null) {
			buildParameter(query, queryParams);
		} else {
			setParameter(query, param);
			resumeIdx();
		}
		if (start != -1 || limit != -1) {
			query.setFirstResult(start);
			query.setMaxResults(limit);
		}
		return query.list();
	}

	protected Object executeSQLScalar(String sql, Object param,
			Class<?>... persistClass) {
		Query query = null;
		QueryParams queryParams = assignToCondList(param);
		sql = sql + (queryParams != null ? queryParams.getQueryString() : "");
		if (persistClass != null && persistClass.length > 0) {
			query = this.getSession().createSQLQuery(sql).addEntity(
					persistClass[0]);
		} else {
			query = this.getSession().createSQLQuery(sql);
		}
		if (queryParams != null) {
			buildParameter(query, queryParams);
		} else {
			setParameter(query, param);
			resumeIdx();
		}
		Object result = query.uniqueResult();
		return result;
	}

	protected int executeSQLUpdate(String sql, Object param) {
		Query query = null;
		QueryParams queryParams = assignToCondList(param);
		sql = sql + (queryParams != null ? queryParams.getQueryString() : "");
		query = this.getSession().createSQLQuery(sql);
		if (queryParams != null) {
			buildParameter(query, queryParams);
		} else {
			setParameter(query, param);
			resumeIdx();
		}
		int result = query.executeUpdate();
		return result;
	}

	public int executeHQLUpdate(String hql, Object param) {
		Query query = null;
		QueryParams queryParams = assignToCondList(param);
		hql = hql + (queryParams != null ? queryParams.getQueryString() : "");
		query = this.getSession().createQuery(hql);
		if (queryParams != null) {
			buildParameter(query, queryParams);
		} else {
			setParameter(query, param);
			resumeIdx();
		}
		int result = query.executeUpdate();
		return result;
	}

	@SuppressWarnings("unchecked")
	private QueryParams assignToCondList(Object param) {
		QueryParams queryParams = null;
		if (param != null && param instanceof List
				&& ((List<?>) param).size() != 0
				&& ((List<?>) param).get(0) instanceof Condition) {
			List<Condition> conditions = (List<Condition>) param;
			queryParams = buildConditions(conditions);
		}
		return queryParams;
	}

	private void setParamArray(Query query, Object[] params) {
		if (params == null || params.length == 0) {
			return;
		}
		for (int i = 0; i < params.length; i++) {
			if (params[i] instanceof Collection) {// 如果是集合需要调用setParameterList方法设置参数
				query.setParameterList("list" + i, (Collection<?>) params[i]);
			} else {
				query.setParameter(i, params[i]);
			}
		}
	}

	private void setParameter(Query query, Object paramObj, Integer... index) {
		if (paramObj != null) {
			if (paramObj instanceof Collection) {
				Collection<?> params = (Collection<?>) paramObj;
				if (paramLevel != 0) {
					for (String paramName : query.getNamedParameters()) {
						if (paramName.equals("list" + listParamIdx)) {
							listParamIdx++;
							query.setParameterList(paramName, params);
							break;
						}
					}
				} else {
					Iterator<?> iter = params.iterator();
					int idx = 0;
					while (iter.hasNext()) {
						paramLevel++;
						setParameter(query, iter.next(), idx);
						paramLevel--;
						idx++;
					}
				}
			} else if (paramObj instanceof Dictionary) {
				Dictionary<?, ?> params = (Dictionary<?, ?>) paramObj;
				while (params.keys().hasMoreElements()) {
					String paramName = (String) params.keys().nextElement();
					Object paramValue = params.elements().nextElement();
					query.setParameter(paramName, paramValue);
				}
			} else if (paramObj instanceof Map) {
				Map<?, ?> params = (Map<?, ?>) paramObj;
				Iterator<?> iter = params.keySet().iterator();
				while (iter.hasNext()) {
					String paramName = (String) iter.next();
					Object paramValue = params.get(paramName);
					query.setParameter(paramName, paramValue);
				}
			} else if (paramObj instanceof String) {
				if (index != null && index.length != 0) {
					query.setString(index[0], (String) paramObj);
				} else {
					query.setString(0, (String) paramObj);
				}
			} else if (paramObj instanceof Integer
					|| paramObj.getClass() == int.class) {
				if (index != null && index.length != 0) {
					query.setInteger(index[0], (Integer) paramObj);
				} else {
					query.setInteger(0, (Integer) paramObj);
				}
			} else if (paramObj instanceof Float
					|| paramObj.getClass() == float.class) {
				if (index != null && index.length != 0) {
					query.setFloat(index[0], (Float) paramObj);
				} else {
					query.setFloat(0, (Float) paramObj);
				}
			} else if (paramObj instanceof Date
					|| paramObj.getClass() == Date.class) {
				if (index != null && index.length != 0) {
					query.setDate(index[0], (Date) paramObj);
				} else {
					query.setDate(0, (Date) paramObj);
				}
			} else if (paramObj instanceof BigDecimal) {
				if (index != null && index.length != 0) {
					query.setBigDecimal(index[0], (BigDecimal) paramObj);
				} else {
					query.setBigDecimal(0, (BigDecimal) paramObj);
				}
			} else if (paramObj instanceof Character) {
				if (index != null && index.length != 0) {
					query.setCharacter(index[0], (Character) paramObj);
				} else {
					query.setCharacter(0, (Character) paramObj);
				}
			} else if (paramObj.getClass().isArray()) {
				for (int i = 0; i < Array.getLength(paramObj); i++) {
					paramLevel++;
					setParameter(query, Array.get(paramObj, i), i);
					paramLevel--;
				}
			} else if (paramLevel != 0) {
				if (index != null && index.length != 0) {
					query.setParameter(index[0], paramObj);
				} else {
					query.setParameter(0, paramObj);
				}
			} else {
				throw new IllegalArgumentException(
						"Query Arguments invalidated,the parameter type of Query is not matched in ArrayList,HashMap,Hashtable,String,Integer,Date");
			}
		}
	}

	/**
	 * 重置Idx
	 */
	private void resumeIdx() {
		paramLevel = 0;
		listParamIdx = 0;
	}

}
