package com.wfms.dao.common.datastat;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.wfms.common.dao.ExtDao;
import com.wfms.model.common.dataimport.ColumnEntity;
import com.wfms.model.common.datastat.StatDefineEntity;

@Repository
public class StatDefineDao extends ExtDao implements IStatDefineDao {

	public List<StatDefineEntity> getAllStatDefine() {
		return super.executeQuery(StatDefineEntity.class);
	}

	public StatDefineEntity getStatDefine(String tableNString) {
		return (StatDefineEntity) super.executeScalar(
				"FROM StatDefineEntity where ssb=?", tableNString);
	}

	public List<ColumnEntity> getStatCondDefine(String[] tableNames) {
		StatDefineEntity statDefine = this.getStatDefine(tableNames[0]);
		String[] fields = null;
		try {
			fields = super.getColumnName(statDefine.getSql());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		List<String> topTrCh = null;
		try {
			topTrCh = super.arrayToList(getColumnNameCN(fields, tableNames));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (topTrCh == null || topTrCh.size() == 0)
			return null;
		List<ColumnEntity> columnEntityList = new ArrayList<ColumnEntity>(
				fields.length);
		for (int i = 0; i < fields.length; i++) {
			ColumnEntity column = new ColumnEntity();
			column.setColumnName(fields[i]);
			column.setComments(topTrCh.get(i));
			columnEntityList.add(column);
		}
		return columnEntityList;
	}

	public List<ColumnEntity> getStatCondDefine(String tableName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<String> getStatTables(String sql) {
		try {
			return super.getTableName(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
