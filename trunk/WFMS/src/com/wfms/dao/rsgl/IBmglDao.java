package com.wfms.dao.rsgl;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.model.rsgl.BmEntity;
import com.wfms.model.system.DepartGenInfo;


public interface IBmglDao {
	public List<DepartGenInfo> getAllBm();
	
	public DepartGenInfo getBmById(String id);
	
	public int deleteBm(String depid);
	
	public int addBm(DepartGenInfo bm);
	
	public int updateBm(DepartGenInfo bm);
	
	public List<DepartGenInfo> getBmByPage(Page page);
}
