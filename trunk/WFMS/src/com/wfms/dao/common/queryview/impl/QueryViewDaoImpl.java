/**
 * 
 */
package com.wfms.dao.common.queryview.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.dao.common.queryview.IQueryViewDao;
import com.wfms.model.common.queryview.QueryViewEntity;

/**
 * @author Administrator
 *
 */
@Repository
public class QueryViewDaoImpl extends BaseDao implements IQueryViewDao{

	public int addQueryView(QueryViewEntity queryView) {
		return super.add(queryView);
	}

	public int deleteQueryView(int queryviewId) {
		return super.delete(QueryViewEntity.class,queryviewId);
	}

	public List<QueryViewEntity> getQueryViewByYhMk(int yhid, int mkid) {
		String queryHql="FROM QueryViewEntity where yhid=:yhId and mkid=:mkId";
		Map<String, Integer> hqlParams=new HashMap<String, Integer>(2);
		hqlParams.put("yhId", yhid);
		hqlParams.put("mkId", mkid);
		return super.executeQuery(queryHql, hqlParams);
	}

}
