package com.wfms.service.jxc.jcsz;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.model.jxc.jcsz.KyxxEntity;

public interface IKyxxService {
	public Page getAllKyxx(Page page);
	public int deleteKyxx(int id);
	public int addKyxx(KyxxEntity KyxxEntity);
	public int updateKyxx(List<KyxxEntity> KyxxList);
	public List<KyxxEntity> getAllKyxxByJoin(String khlb);
}
