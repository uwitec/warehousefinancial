/**
 * 
 */
package com.wfms.dao.common.queryview;

import java.util.List;

import com.wfms.model.common.queryview.QueryViewEntity;


/**
 * @author Administrator
 *
 */
public interface IQueryViewDao {
	
	public List<QueryViewEntity> getQueryViewByYhMk(int yhid,int mkid);
	
	public int deleteQueryView(int queryviewId);
	
	public int addQueryView(QueryViewEntity queryView);
}
