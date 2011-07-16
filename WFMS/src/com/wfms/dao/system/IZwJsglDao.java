package com.wfms.dao.system;


import com.wfms.model.system.RolePart;

public interface IZwJsglDao {
	public int insertZwJs(RolePart zwjs);
	public int delZwJsByYhAndZw(int jsId,String zwId);
}
