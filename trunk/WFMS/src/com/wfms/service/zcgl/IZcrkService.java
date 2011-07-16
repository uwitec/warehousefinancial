package com.wfms.service.zcgl;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.model.zcgl.CkzcEntity;
import com.wfms.model.zcgl.RkdEntity;
import com.wfms.model.zcgl.RkdmxEntity;

public interface IZcrkService {
	

	public Page  getRkdmxByPage(Page page);

	public Page getZcrkdByPage(Page page);

	public int addRkd(RkdEntity entity, List<RkdmxEntity> list);

	public int deleteRkd(int id);
	
	//public RkdmxEntity getRkdmxByZcId(int zcId);

	public List<RkdmxEntity> getRkdmxByZclx(String column, String zclx);

	public Page getCkzcByPage(Page page);

}
