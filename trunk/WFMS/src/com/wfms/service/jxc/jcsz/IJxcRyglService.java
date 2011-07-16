package com.wfms.service.jxc.jcsz;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.model.jxc.jcsz.JxcRyglEntity;

public interface IJxcRyglService {
	public Page getAllRygl(Page page);
	public int deleteRygl(int id);
	public int addRygl(JxcRyglEntity JxcRyglEntity);
	public int updateRygl(List<JxcRyglEntity> RyglList);
	public List<JxcRyglEntity> getAllRyglByJoin(String khlb);
}
