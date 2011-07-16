/**
 * 
 */
package com.wfms.service.common.queryview.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.dao.common.queryview.IQueryViewDao;
import com.wfms.model.common.queryview.QueryViewEntity;
import com.wfms.service.common.queryview.IQueryViewService;

/**
 * @author Administrator
 *
 */
@Service
public class QueryViewServiceImpl implements IQueryViewService {

	private IQueryViewDao queryViewDao = null;

	@Autowired
	public void setQueryViewDao(IQueryViewDao queryViewDao) {
		this.queryViewDao = queryViewDao;
	}

	public int addQueryView(QueryViewEntity queryView) {
		return queryViewDao.addQueryView(queryView);
	}

	public int deleteQueryView(int queryViewId) {
		return queryViewDao.deleteQueryView(queryViewId);
	}

	public List<QueryViewEntity> loadYhMkQueryView(int yhId, int mkId) {
		return queryViewDao.getQueryViewByYhMk(yhId, mkId);
	}

}
