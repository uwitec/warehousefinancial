package com.wfms.dao.zcgl;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.model.zcgl.ZclxEntity;

public interface IZclxDao {
	
	public List<ZclxEntity> getZclxList(Page page);
	
	public int getZclxTotalCount(Page page);
	
	public int addZclx(ZclxEntity zclxEntity);

	public int updateZclx(List<ZclxEntity> zclxEntity);

	public int deleteZclx(int id);

	public List<ZclxEntity> getZclxList();
	
	public ZclxEntity getZclxById(int zclxId);
	
}
