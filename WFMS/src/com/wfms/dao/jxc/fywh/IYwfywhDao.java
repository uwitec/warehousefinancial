package com.wfms.dao.jxc.fywh;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.model.jxc.fygl.YwfywhEntity;

public interface IYwfywhDao {
	public List<YwfywhEntity> getYwfywhEntity();

	public int getYwfywhEntityCount(List<ConditionBean> list);

	public List<YwfywhEntity> getYwfywhEntity(Page page);

	public int addFywh(YwfywhEntity YwfywhEntity);

	public int updateFywh(List<YwfywhEntity> YwfywhEntity);

	public int deleteFywh(int id);
	
	public List<YwfywhEntity> getAllXfz(String ywd, String stime, String etime);
	
	public List<YwfywhEntity> getDcjf() ;
}
