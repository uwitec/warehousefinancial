package com.wfms.dao.zcgl;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.model.zcgl.CkdEntity;
import com.wfms.model.zcgl.CkdmxEntity;

public interface IZcckDao {

	public List<CkdmxEntity> getCkdmxList(Page page);

	public int getCkdmxTotalCount(Page page);

	public List<CkdEntity> getZcckdxList(Page page);

	public int getZcckdTotalCount(Page page);

	public int addCkd(CkdEntity entity);

	public int deleteCkd(int id);
	
	public List<CkdmxEntity> getCkdmxListByCkdId(int ckdId);

	public int deleteCkdmx(CkdmxEntity ckdmx);
}
