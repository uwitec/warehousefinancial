package com.wfms.dao.zcgl;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.model.zcgl.CkzcEntity;
import com.wfms.model.zcgl.RkdEntity;
import com.wfms.model.zcgl.RkdmxEntity;

public interface IZcrkDao {

	public List<RkdmxEntity> getRkdmxList(Page page);

	public int getRkdmxTotalCount(Page page);

	public List<RkdEntity> getZcrkdxList(Page page);

	public int getZcrkdTotalCount(Page page);

	public int addRkd(RkdEntity entity);

	public int deleteRkd(int id);

	public List<RkdmxEntity> getRkdmxByZcId(int zcId);
	
	public int updateRkdmx(RkdmxEntity tRkdmx);

	public List<RkdmxEntity> getRkdmxByZclx(String column, String zclx);
	
	public List<RkdmxEntity> getRkdmxListByRkdId(int rkdId);
	
	public int deleteRkdmx(RkdmxEntity rkdmx);
	
	public CkzcEntity getCkzc(int ckId,int zclxId,int zcId);
	
	public int addCkzc(CkzcEntity ckzc);
	
	public int updateCkzc(CkzcEntity ckzc);

	public List<CkzcEntity> getCkzcList(Page page);

	public int getCkzcTotalCount(Page page);
}
