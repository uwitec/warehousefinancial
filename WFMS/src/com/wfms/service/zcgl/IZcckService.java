package com.wfms.service.zcgl;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.model.zcgl.CkdEntity;
import com.wfms.model.zcgl.CkdmxEntity;

public interface IZcckService {
	
	public Page  getCkdmxByPage(Page page);

	public Page getZcckdByPage(Page page);

	public int addCkd(CkdEntity entity, List<CkdmxEntity> list);

	public int deleteCkd(int id);



}
