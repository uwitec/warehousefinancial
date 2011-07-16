package com.wfms.dao.jxc.jcsz;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.model.jxc.jcsz.QCglEntity;

public interface IQcglDao {
	public List<QCglEntity> getQCglEntity();

	public int getQCglEntityCount(List<ConditionBean> list);

	public List<QCglEntity> getQCglEntity(Page page);

	public int addQcgl(QCglEntity QCglEntity);

	public int updateQcgl(List<QCglEntity> QCglEntity);

	public int deleteQcgl(int id);
}
