package com.wfms.service.system;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.model.system.XqwhEntity;

public interface IXqwhService {
	public List<XqwhEntity> getXqwhEntity();

	public int getXqwhEntityCount(List<ConditionBean> list);

	public Page getXqwhEntity(Page page);

	public int addXqwh(XqwhEntity xqwhEntity);

	public int updateXqwh(List<XqwhEntity> xqwhEntity);

	public int deleteXqwh(int id);
}
