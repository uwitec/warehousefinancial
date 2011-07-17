/**
 * 
 */
package com.wfms.common.function.dao;

import java.util.List;

import com.wfms.common.dao.GeneralDao;
import com.wfms.common.function.entity.QueryView;

/**
 * @author Administrator
 * 
 */
public interface IQueryViewDao extends GeneralDao<QueryView> {

	public int addQueryView(QueryView queryView);

	public int deleteQueryView(int queryViewId);

	public List<QueryView> getQueryViewByModule(int yhid, int mkid);

}
