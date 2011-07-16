package com.wfms.service.zcgl;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.model.zcgl.ZclxEntity;

public interface IZclxService {
	
	public Page getZclxByPage(Page page);

	public int addZclx(ZclxEntity zclxEntity);

	public int updateZclx(List<ZclxEntity> zclxEntity);

	public int deleteZclx(int id);
	
	public List<ZclxEntity> getZclxList();
	
}
