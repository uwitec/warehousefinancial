package com.wfms.service.jxc.query.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.dao.jxc.query.IQueryDao;
import com.wfms.service.jxc.query.IQueryService;

@Service
public class QueryServiceimpl implements IQueryService {
	@Autowired 
	private IQueryDao iQueryDao;
	
	public List getCustomers(String type, String custmc, String ftime, String etime){
		List list = new ArrayList();
		List reli = new ArrayList();
		if(type.equals("0")){//0：供应商   1：经销商
			list = iQueryDao.getSuppliers(custmc, ftime, etime);
		}else{
			list = iQueryDao.getDealer(custmc, ftime, etime);
		}
		for(int i=0;i<list.size();i++){
			Object[] ps = (Object[])list.get(i);
			if((ps[10] != null && !ps[10].equals("0")) || (ps[12] != null && !ps[12].equals("0"))){
				reli.add(ps);
			}
		}
		return reli;
	}
	
	public List getProducts(String cpid, String ftime, String etime){
		List relist = new ArrayList();
		List list = iQueryDao.getProducts(cpid, ftime, etime);
		if(cpid == null || cpid.equals("")){
			for(int i=0;i<list.size();i++){
				Object[] p = (Object[])list.get(i);
				if((p[10] == null || p[10].equals("0")) && (p[13] == null || p[13].equals("0"))){
					continue;
				}
				relist.add(p);
			}
		}else{
			for(int i=0;i<list.size();i++){
				relist.add((Object[])list.get(i));
			}
		}
		return relist;
	}
	
	public List getMonth(String ftime, String etime){
		List list = new ArrayList();
		List gssr = iQueryDao.getMonsr(ftime, etime);
		List rkzc = iQueryDao.getMonrkzc(ftime, etime);
		List fyzc = iQueryDao.getMonfyzc(ftime, etime);//0人费用，1车费用
		List ckhz = iQueryDao.getMonckhzBylx(ftime, etime);
		List rkhz = iQueryDao.getMonrkhzBylx(ftime, etime);
		
		String[] zj = new String[4];
		int sr = 0;
		try{
			Object[] ss = (Object[])gssr.get(0);
			sr = Integer.parseInt(String.valueOf(ss[0]));
		}catch(Exception ex){}
		int rk = 0;
		try{
			Object[] ss = (Object[])rkzc.get(0);
			rk = Integer.parseInt(String.valueOf(String.valueOf(ss[0])));
		}catch(Exception ex){}
		int fy = 0;
		
		String[] zfy = new String[]{"人费用","0","车费用","0"};
		for(int i=0;i<fyzc.size();i++){
			Object[] ps = (Object[])fyzc.get(i);
			if(String.valueOf(ps[0]).equals("0")){
				zfy[1] = String.valueOf(ps[1]);
				fy += Integer.parseInt(zfy[1]);
			}
			if(String.valueOf(ps[0]).equals("1")){
				zfy[3] = String.valueOf(ps[1]);
				fy += Integer.parseInt(zfy[3]);
			}
		}
		zj[0] = ""+sr;//销售出库收入
		zj[1] = ""+rk;//入库，成本
		zj[2] = ""+fy;//人员和车费用
		zj[3] = ""+(sr-rk-fy);//相当于纯利润
		
		list.add(zj);
		list.add(zfy);
		list.add(ckhz);
		list.add(rkhz);
		return list;
	}
	
	public List getXs(String ftime, String etime, String xs){
		return iQueryDao.getXs(ftime, etime, xs);
	}
}
