package com.wfms.dao.common.datastat;

import java.util.List;
import java.util.Set;

import com.wfms.model.common.dataimport.ColumnEntity;
import com.wfms.model.common.datastat.StatDefineEntity;

public interface IStatDefineDao {
	
	public Set<String> getStatTables(String sql);
	
	public List<StatDefineEntity> getAllStatDefine();
	
	public StatDefineEntity getStatDefine(String tableNString);
	
	public List<ColumnEntity> getStatCondDefine(String tableName);
}
