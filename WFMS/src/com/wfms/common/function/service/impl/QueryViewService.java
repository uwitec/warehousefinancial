/**
 * 
 */
package com.wfms.common.function.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.common.function.dao.IQueryViewDao;
import com.wfms.common.function.entity.QueryView;
import com.wfms.common.function.service.IQueryViewService;

/**
 * @author Administrator
 * 
 */
@Service
public class QueryViewService implements IQueryViewService {

	private IQueryViewDao queryViewDao = null;

	@Autowired
	public void setQueryViewDao(IQueryViewDao queryViewDao) {
		this.queryViewDao = queryViewDao;
	}

	public int addQueryView(QueryView queryView) {
		return queryViewDao.addQueryView(queryView);
	}

	public int deleteQueryView(int queryViewId) {
		return queryViewDao.deleteQueryView(queryViewId);
	}

	public List<QueryView> loadYhMkQueryView(int yhId, int mkId) {
		return queryViewDao.getQueryViewByModule(yhId, mkId);
	}

}
