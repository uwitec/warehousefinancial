/**
 * 
 */
package com.wfms.common.function.service;

import java.util.List;

import com.wfms.common.function.entity.QueryView;


/**
 * @author Administrator
 *
 */
public interface IQueryViewService {
	
	public List<QueryView> loadYhMkQueryView(int yhId,int mkId);
	
	public int addQueryView(QueryView queryView);
	
	public int deleteQueryView(int queryViewId);

}
