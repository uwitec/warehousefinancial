package com.wfms.dao.zcgl;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.model.zcgl.CkxxEntity;
import com.wfms.model.zcgl.CkzcEntity;

public interface ICkxxDao {
	
	public List<CkxxEntity> getCkxxList(Page page);
	
	public int getCkxxTotalCount(Page page);
	
	public int addCkxx(CkxxEntity Ckxx);

	public int updateCkxx(List<CkxxEntity> ckxx);

	public int updateCkxx(CkxxEntity ckxx);

	public int deleteCkxx(int id);

	public List<CkxxEntity> getCkxxList(String userId);

	public List<CkzcEntity> getCkzcListByCkId(String ckId);
	
	public CkxxEntity getCkxxById(int ckId);


}
