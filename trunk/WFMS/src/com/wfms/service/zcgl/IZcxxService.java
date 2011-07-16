package com.wfms.service.zcgl;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.model.zcgl.ZcxxEntity;

public interface IZcxxService {

	public Page getZcxxByPage(Page page);

	public int addZcxx(ZcxxEntity zcxx);

	public int updateZcxx(List<ZcxxEntity> zcxx);

	public int deleteZcxx(int id);

	public List<ZcxxEntity> getZcxxList(String column, String zclx, String type);

	public List<ZcxxEntity> getZcxxByZclx(String column, String zclx);
	
	

}
