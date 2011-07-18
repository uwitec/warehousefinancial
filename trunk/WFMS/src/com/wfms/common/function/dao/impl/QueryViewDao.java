package com.wfms.common.function.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.BaseDao;
import com.wfms.common.function.dao.IQueryViewDao;
import com.wfms.common.function.entity.QueryView;

/**
 * @author Administrator
 * 
 */
@Repository
public class QueryViewDao extends BaseDao<QueryView> implements IQueryViewDao {

	public List<QueryView> getQueryViewByModule(int yhid, int mkid) {
		String queryHql = "FROM QueryView where yhid=:yhId and mkid=:mkId";
		Map<String, Integer> hqlParams = new HashMap<String, Integer>(2);
		hqlParams.put("yhId", yhid);
		hqlParams.put("mkId", mkid);
		return (List<QueryView>) super.executeQuery(queryHql, hqlParams);
	}

	public int addQueryView(QueryView queryView) {
		return 0;
	}

	public int deleteQueryView(int queryViewId) {
		return 0;
	}


}
