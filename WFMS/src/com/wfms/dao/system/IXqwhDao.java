package com.wfms.dao.system;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.model.system.XqwhEntity;

public interface IXqwhDao {
	public List<XqwhEntity> getXqwhEntity();

	public int getXqwhEntityCount(List<ConditionBean> list);

	public List<XqwhEntity> getXqwhEntity(Page page);

	public int addXqwh(XqwhEntity xqwhEntity);

	public int updateXqwh(List<XqwhEntity> xqwhEntity);

	public int deleteXqwh(int id);
}
