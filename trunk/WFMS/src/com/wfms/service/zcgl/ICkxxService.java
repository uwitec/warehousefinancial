package com.wfms.service.zcgl;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.model.zcgl.CkxxEntity;
import com.wfms.model.zcgl.CkzcEntity;
import com.wfms.model.zcgl.ZclxEntity;

public interface ICkxxService {

	public Page getCkxxByPage(Page page);

	public int addCkxx(CkxxEntity ckxx,List<CkzcEntity> list);

	public int updateCkxx(List<CkxxEntity> ckxx);

	public int updateCkxx(CkxxEntity ckxx);

	public int deleteCkxx(int id);

	public List<CkxxEntity> getCkxxList(String userId);

	public List<ZclxEntity> getZclxListByCkId(String ckId);
	
	
	
}
