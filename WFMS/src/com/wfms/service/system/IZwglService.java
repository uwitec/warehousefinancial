package com.wfms.service.system;

import java.util.List;

import com.wfms.dto.system.ZwDto;
import com.wfms.model.system.RoleGenInfo;

public interface IZwglService {
	public ZwDto getAllZw();
	
	public ZwDto getZwById(String id);
	
	public int updZw(List<RoleGenInfo> zwList);
	
	public int delZw(String id);

	public String addZw(RoleGenInfo zw);
	
	public List<RoleGenInfo> getZwForYhNot(String yhId);
	
	public List<RoleGenInfo> loadRoleByDepart(String depid);
}
