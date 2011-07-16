package com.wfms.service.system;

import com.wfms.model.system.RolePart;

public interface IZwJsglService {
	public int addZwJs(RolePart zwjs);
	public int delZwJs(int jsId,String zwId);
}
