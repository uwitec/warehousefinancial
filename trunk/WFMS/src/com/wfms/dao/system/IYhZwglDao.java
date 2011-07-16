package com.wfms.dao.system;

import java.util.List;

import com.wfms.common.util.ConditionBean;
import com.wfms.model.system.RoleGenInfo;
import com.wfms.model.system.RoleMember;

public interface IYhZwglDao {
	public int insertYhZw(RoleMember yhzw);
	public int delYhZwByYhAndZw(RoleMember yhzw);
	public RoleGenInfo getZwByDm(String name);
	public int getYhzwCount(List<ConditionBean> list);
}
