package com.wfms.service.jxc.fywh;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.model.jxc.fygl.FywhEntity;

public interface IFywhService {
	public List<FywhEntity> getFywhEntity();

	public int getFywhEntityCount(List<ConditionBean> list);

	public Page getFywhEntity(Page page);

	public int addFywh(FywhEntity FywhEntity);

	public int updateFywh(List<FywhEntity> FywhEntity);

	public int deleteFywh(int id);
	
	public List<FywhEntity> getAllXfz(String type);
}
