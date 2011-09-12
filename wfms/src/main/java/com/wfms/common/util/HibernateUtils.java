package com.wfms.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.util.Assert;

import com.wfms.common.orm.BaseEntity;
import com.wfms.common.orm.Condition;
import com.wfms.common.orm.Page;
import com.wfms.common.orm.Rule;


/**
 * hibernate查询工具类
 * 
 * @author CYC
 * 
 */
public class HibernateUtils {

	public static Criteria createCriteria(Session session,
			Class<? extends BaseEntity> entityClass, Criterion[] criterions) {
		Criteria criteria = session.createCriteria(entityClass);
		if (criterions != null) {
			for (Criterion criterion : criterions) {
				criteria.add(criterion);
			}
		}
		return criteria;
	}

	/**
	 * 构建Criterion
	 */
	private static Criterion createCriterion(String fieldName,
			Object fieldValue, Rule matchType, Criteria... criteria) {
		Criterion criterion = null;
		Assert.hasText(fieldName, "fieldName不能为空");
		if (fieldName.indexOf(".") == -1) {
			switch (matchType) {
			case EQUAL:
				criterion = Restrictions.eq(fieldName, fieldValue);
				break;
			case ALLLIKE:
				criterion = Restrictions.like(fieldName, (String) fieldValue,
						MatchMode.ANYWHERE);
				break;
			case LEFTLIKE:
				criterion = Restrictions.like(fieldName, (String) fieldValue,
						MatchMode.START);
				break;
			case RIGHTLIKE:
				criterion = Restrictions.like(fieldName, (String) fieldValue,
						MatchMode.END);
				break;
			case LESSTHAN:
				criterion = Restrictions.lt(fieldName, fieldValue);
				break;
			case LESSEQUAL:
				criterion = Restrictions.le(fieldName, fieldValue);
				break;
			case THAN:
				criterion = Restrictions.gt(fieldName, fieldValue);
				break;
			case THANEQUAL:
				criterion = Restrictions.ge(fieldName, fieldValue);
				break;
			case NOTEQUAL:
				criterion = Restrictions.ne(fieldName, fieldValue);
				break;
			case IN:
				if (fieldValue instanceof Collection) {
					criterion = Restrictions.in(fieldName,
							(Collection<?>) fieldValue);
				} else if (fieldValue.getClass().isArray()) {
					criterion = Restrictions.in(fieldName,
							(Object[]) fieldValue);
				}
				break;
			}
		} else {
			/*
			 * String[] fieldNames = fieldName.split("\\."); switch (matchType)
			 * { case EQUAL: criterion =
			 * Property.forName(fieldName).eq(fieldValue); break; case ALLLIKE:
			 * criterion =
			 * Property.forName(fieldNames[0]).getProperty(fieldNames[1]).like(
			 * (String) fieldValue, MatchMode.ANYWHERE); break; case LEFTLIKE:
			 * criterion = Property.forName(fieldName).like( (String)
			 * fieldValue, MatchMode.START); break; case RIGHTLIKE: criterion =
			 * Property.forName(fieldName).like( (String) fieldValue,
			 * MatchMode.END); break; case LESSTHAN: criterion =
			 * Property.forName(fieldName).lt(fieldValue); break; case
			 * LESSEQUAL: criterion =
			 * Property.forName(fieldName).le(fieldValue); break; case THAN:
			 * criterion = Property.forName(fieldName).gt(fieldValue); break;
			 * case THANEQUAL: criterion =
			 * Property.forName(fieldName).ge(fieldValue); break; case NOTEQUAL:
			 * criterion = Property.forName(fieldName).ne(fieldValue); break;
			 * case IN: if (fieldValue instanceof Collection) { criterion =
			 * Property.forName(fieldName).in( (Collection<?>) fieldValue); }
			 * else if (fieldValue.getClass().isArray()) { criterion =
			 * Property.forName(fieldName).in( (Object[]) fieldValue); } break;
			 * }
			 */
		}
		return criterion;
	}

	/**
	 * 根据Criteria返回总数
	 * 
	 * @param criteria
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static long countCriteriaResult(Criteria criteria) {
		CriteriaImpl impl = (CriteriaImpl) criteria;

		Projection projection = impl.getProjection();
		ResultTransformer resultTransformer = impl.getResultTransformer();

		List<?> orderEntries = null;
		orderEntries = (List<?>) ReflectionUtils.getFieldValue(impl,
				"orderEntries");
		ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());

		Object countObj = criteria.setProjection(Projections.rowCount())
				.uniqueResult();

		long totalCount = 0;
		if (countObj instanceof Integer) {
			totalCount = new Long((Integer) countObj);
		} else if (countObj instanceof Long) {
			totalCount = (Long) countObj;
		}
		criteria.setProjection(projection);

		if (projection == null) {
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (resultTransformer != null) {
			criteria.setResultTransformer(resultTransformer);
		}

		ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);

		return totalCount;
	}

	/**
	 * 设置排序参数
	 * 
	 * @param criteria
	 * @param page
	 * @return
	 */
	public static Criteria setCompositorParameter(Criteria criteria, Page page) {
		if (page == null) {
			return criteria;
		}
		String fieldName = page.getOrderByColumn();
		String fieldRule = page.getOrderByRule();
		if (fieldName != null && !"".equals(fieldName)) {
			if (page.getOrderByRule() == null || "".equals(fieldRule)) {
				criteria.addOrder(Order.desc(fieldName));
			} else {
				if ("ASC".equalsIgnoreCase(fieldRule)) {
					criteria.addOrder(Order.asc(fieldName));
				} else if ("DESC".equalsIgnoreCase(fieldRule)) {
					criteria.addOrder(Order.desc(fieldName));
				}
			}
		}

		return criteria;
	}

	/**
	 * 根据Condition数组构建Criterion并装载到Criteria返回
	 * 
	 * @param criteria
	 * @param conditions
	 * @return
	 */
	public static Criteria setConditionParameter(Criteria criteria,
			Condition[] conditions) {
		if (conditions.length > 0) {
			List<Criterion> criterions = new ArrayList<Criterion>();
			for (Condition condition : conditions) {
				Criterion criterion = null;
				if (!condition.isMultiFilter()) {
					criterion = createCriterion(condition.getName(),
							condition.getValue(), condition.getRule(), criteria);
					if (criteria != null) {
						criterions.add(criterion);
					}
				} else {
					Disjunction disjunction = Restrictions.disjunction();
					for (String filedName : condition.getNames()) {
						criterion = createCriterion(filedName,
								condition.getValue(), condition.getRule());
						if (criterion != null) {
							disjunction.add(criterion);
						}
					}
					criterions.add(disjunction);
				}
			}
			for (Criterion criterion : criterions) {
				criteria.add(criterion);
			}
		}

		return criteria;
	}

	/**
	 * 根据Condition[]构建criteria并返回
	 */
	public static Criteria setConditionParameter(Criteria criteria,
			List<Condition> conditionList) {
		if (conditionList != null) {
			Condition[] cnditions = new Condition[conditionList.size()];
			for (int i = 0; i < conditionList.size(); ++i) {
				cnditions[i] = ((Condition) conditionList.get(i));
			}
			return setConditionParameter(criteria, cnditions);
		}

		return criteria;
	}

	/**
	 * 从Page中取出条件List并设置到Criteria返回
	 * 
	 * @param criteria
	 * @param page
	 * @return
	 */
	public static Criteria setParameter(Criteria criteria, Page page) {
		setConditionParameter(criteria, page.getConditions());
		// 参数设置,向前兼容,应用于在Controller中自定义过滤条件，如Page.addCondition(name,value,rule);
		/*
		 * if (page.getConditions() != null && page.getConditions().size() != 0)
		 * { for (Condition condition : page.getConditions()) { Criterion
		 * criterion = createCriterion(condition.getName(),
		 * condition.getValue(), condition.getRule()); if(criterion!=null) {
		 * criteria.add(criterion); } } }
		 */
		if (page.isReadTotalCount()) {
			Long totalCount = countCriteriaResult(criteria);
			page.setTotal(totalCount.intValue());
		}

		criteria.setFirstResult(page.getStart());
		criteria.setMaxResults(page.getLimit());

		setCompositorParameter(criteria, page);

		return criteria;
	}

}