package com.wfms.dao.system;

import java.util.List;

import com.wfms.model.system.RightGenInfo;

public interface IQxglDao {
	public List<RightGenInfo> getAllQx();
	
	public RightGenInfo getQxById(int id);
	
	public int deleteQx(int id);
	
	public int addQx(RightGenInfo Qx);
	
	public int updateQx(RightGenInfo Qx);
	
	public List<RightGenInfo> getQxsByIds(int[] qxIds);
}
