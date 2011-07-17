package com.wfms.common.function.dao;

import java.util.List;
import java.util.Set;

import com.wfms.common.function.entity.Column;
import com.wfms.common.function.entity.StatDefine;

public interface IStatDefineDao {
	
	public Set<String> getStatTables(String sql);
	
	public List<StatDefine> getAllStatDefine();
	
	public StatDefine getStatDefine(String tableNString);
	
	public List<Column> getStatCondDefine(String tableName);
}
