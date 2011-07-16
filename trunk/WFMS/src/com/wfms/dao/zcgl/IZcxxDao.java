package com.wfms.dao.zcgl;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.model.zcgl.ZcxxEntity;

public interface IZcxxDao {
	
	public List<ZcxxEntity> getZcxxList(Page page);

	public int getZcxxTotalCount(Page page);
	
	public int addZcxx(ZcxxEntity zcxx);

	public int updateZcxx(List<ZcxxEntity> zcxx);
	
	public int updateZcxx(ZcxxEntity zcxx);

	public int deleteZcxx(int id);

	public List<ZcxxEntity> getZcxxList(String column, String zclx);

	public List<ZcxxEntity> getZcxxByZclx(String column, String zclx);
	
	public ZcxxEntity getZcxxById(int zcxxId);
	public List<ZcxxEntity> getZcxxListBysjdyo(String column, String zclx);
}
