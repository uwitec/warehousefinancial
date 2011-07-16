package com.wfms.dao.jxc.query;

import java.util.List;

public interface IQueryDao {
	//产品报表
	public List getProducts(String cpid, String ftime, String etime);
	//供应商
	public List getSuppliers(String supid, String ftime, String etime);
	//经销商
	public List getDealer(String deaid, String ftime, String etime);
	public List getMonsr(String ftime, String etime);
	public List getMonrkzc(String ftime, String etime);
	public List getMonfyzc(String ftime, String etime);
	public List getMonckhzBylx(String ftime, String etime);
	public List getMonrkhzBylx(String ftime, String etime);
	public List getXs(String ftime, String etime, String xs);
}
