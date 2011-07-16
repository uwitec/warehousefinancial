/**
 * 
 */
package com.wfms.service.common.queryview;

import java.util.List;

import com.wfms.model.common.queryview.QueryViewEntity;


/**
 * @author Administrator
 *
 */
public interface IQueryViewService {
	
	public List<QueryViewEntity> loadYhMkQueryView(int yhId,int mkId);
	
	public int addQueryView(QueryViewEntity queryView);
	
	public int deleteQueryView(int queryViewId);

}
