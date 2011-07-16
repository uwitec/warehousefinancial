package com.wfms.service.jxc.query;

import java.util.List;

public interface IQueryService {
	public List getCustomers(String type, String custmc, String ftime, String etime);
	
	public List getProducts(String cpid, String ftime, String etime);
	
	public List getMonth(String ftime, String etime);
	
	public List getXs(String ftime, String etime, String xs);
}
