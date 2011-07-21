package com.wfms.common.dao;

import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import oracle.jdbc.OracleResultSetMetaData;

import org.hibernate.CacheMode;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.jdbc.Work;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

public class ExtDao<T> extends BaseDao {

	@Autowired
	@Qualifier("sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	protected int batchAdd(List transientInstances, int commitNum) {
		if (transientInstances == null || transientInstances.size() == 0) {
			return 0;
		}
		Transaction tx = null;
		HibernateTemplate hbTemp = getHibernateTemplate();
		hbTemp.setAllowCreate(true);
		Session session = hbTemp.getSessionFactory().openSession();
		int fetchCount = 0;
		try {
			tx = session.beginTransaction();
			int i = 0;
			for (Object transientInstance : transientInstances) {
				session.save(transientInstance);
				i++;
				if (i == commitNum) {
					tx.commit();
					fetchCount += i;
					i = 0;
					session.flush();
					session.clear();
					tx = session.beginTransaction();
				}
			}
			if (i != 0) {
				tx.commit();
				fetchCount += i;
				session.flush();
				session.clear();
			}
			return fetchCount;
		} catch (Exception re) {
			if (tx != null && tx.isActive())
				tx.rollback();
			re.printStackTrace();
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	protected int batchUpdate(List transientInstances, int commitNum) {
		Transaction tx = null;
		HibernateTemplate hbTemp = getHibernateTemplate();
		hbTemp.setAllowCreate(true);
		Session session = hbTemp.getSessionFactory().openSession();
		int fetchCount = 0;
		try {
			tx = session.beginTransaction();
			int i = 0;
			for (Object transientInstance : transientInstances) {
				session.update(transientInstance);
				i++;
				if (i == commitNum) {
					tx.commit();
					fetchCount += i;
					i = 0;
					session.flush();
					session.clear();
					tx = session.beginTransaction();
				}
			}
			if (i != 0) {
				tx.commit();
				fetchCount += i;
				session.flush();
				session.clear();
			}
			return fetchCount;
		} catch (Exception re) {
			if (tx != null && tx.isActive())
				tx.rollback();
			return -1;
		}
	}

	protected int[] executeSQLBatchUpdate(String[] sqls) {
		int[] affectedCount = null;
		Session session = this.getSession();
		session.setCacheMode(CacheMode.IGNORE);

		Connection conn = null;
		Statement st = null;
		try {
			conn = SessionFactoryUtils.getDataSource(getSessionFactory())
					.getConnection();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			if (sqls != null && sqls.length > 0) {
				for (String sql : sqls) {
					if (!"".equals(sql))
						;
					st.addBatch(sql);
				}
			} else {
				return null;
			}
			affectedCount = st.executeBatch();
			st.clearBatch();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return affectedCount;
	}

	protected Class<?> getClassByTableName(String tableName) {
		Class<?> clzz = null;
		Configuration cfg = new Configuration().configure();
		Iterator<?> iter = cfg.getTableMappings();
		Iterator<?> iter1 = cfg.getClassMappings();
		while (iter.hasNext()) {
			Object obj = iter.next();
			System.out.println(obj);
		}
		while (iter1.hasNext()) {
			Object obj = iter.next();
			System.out.println(obj);
		}
		return clzz;
	}

	@SuppressWarnings("unchecked")
	public List findWithSQL(final String sql) {
		List list = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws SQLException, HibernateException {
						SQLQuery query = session.createSQLQuery(sql);
						query.addScalar("NX",
								new org.hibernate.type.StringType());
						List children = query.list();
						return children;
					}
				});
		return list;
	}

	/**
	 * 查询并返回结果集,结果集中的内容已经都转为了字符串
	 */
	@SuppressWarnings("unchecked")
	public List<List<String>> findSql(final String sql) {
		List<List<String>> mainObjList = (List<List<String>>) getHibernateTemplate()
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						int n = -1;
						Connection con = null;
						PreparedStatement stmt = null;
						ResultSet rs = null;

						try {
							DataSource ds = SessionFactoryUtils
									.getDataSource(getSessionFactory());
							if (ds == null) {
								throw new SQLException("get dataSource is null");
							}
							con = ds.getConnection();
							System.out.println("findSql---sql2----->" + sql);
							stmt = con.prepareStatement(sql);
							rs = stmt.executeQuery();

						} catch (HibernateException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}

						List<List<String>> list = new ArrayList<List<String>>();// 每行为一个list
						try {
							ResultSetMetaData rsmd = rs.getMetaData();
							int colsNum = rsmd.getColumnCount();// 取得列数
							int rsNum = 0;
							while (rs.next()) {
								rsNum++;
								System.out.println("rsNum===" + rsNum + " ");
								List<String> subList = new ArrayList<String>();// 每列的类型为string
								for (int i = 1; i <= colsNum; i++) {
									System.out.println("\ti===" + i);
									// int type= rsmd.getColumnType(i);
									String columnType = getDataType(rsmd
											.getColumnType(i), rsmd.getScale(i));
									String val = "";
									if (columnType.equalsIgnoreCase("Date")) {
										Timestamp timest = rs.getTimestamp(i);
										if (timest != null) {
											long times = timest.getTime();
											Date date = new Date(times);
											SimpleDateFormat df = new SimpleDateFormat(
													"yyyy-MM-dd HH:mm:ss",
													Locale.CHINA);
											val = df.format(date);
										}
									} else if (columnType
											.equalsIgnoreCase("Number")) {
										Object obj = rs.getObject(i);
										if (obj != null) {
											int m = rs.getInt(i);
											val = Integer.toString(m);
										}
									} else if (columnType
											.equalsIgnoreCase("blob")) {
										val = "不支持blob数据的读取";
									} else if (columnType
											.equalsIgnoreCase("clob")) {
										val = getOracleClobField(rs, i);

									} else {
										val = rs.getString(i);
									}
									if (val == null) {
										val = "";
									}
									subList.add(val);
								}
								if (subList.size() > 0) {
									list.add(subList);
								}
							}
						} catch (Exception ex5) {
							ex5.printStackTrace();
							System.out.println("ex5.getMessage="
									+ ex5.getMessage());
							list = null;
						} finally {
							try {
								if (rs != null) {
									rs.close();
									rs = null;
								}
							} catch (Exception e2) {
								e2.printStackTrace();
								System.out.println("e2.getMessage="
										+ e2.getMessage());
							}
							try {
								if (stmt != null) {
									stmt.close();
									stmt = null;
								}
							} catch (Exception e3) {
								e3.printStackTrace();
								System.out.println("e3.getMessage="
										+ e3.getMessage());
							}
							try {
								if (con != null) {
									con.close();
									con = null;
								}
							} catch (Exception e4) {
								e4.printStackTrace();
								System.out.println("e4.getMessage="
										+ e4.getMessage());
							}
						}
						return list;
					}
				});
		return mainObjList;
	}

	private String getOracleClobField(ResultSet rset, int index)
			throws Exception {
		StringBuffer buffS = new StringBuffer();
		Clob clob = rset.getClob(index + 1);
		if (clob == null)
			return " ";
		Reader reader = clob.getCharacterStream();
		char buff[] = new char[1024];
		for (int len = 0; (len = reader.read(buff)) != -1;)
			buffS.append(buff, 0, len);
		return buffS.toString();
	}

	private static String getDataType(int type, int scale) {
		String dataType = "";

		switch (type) {
		case Types.LONGVARCHAR: // -1
			dataType = "Long";
			break;
		case Types.CHAR: // 1
			dataType = "Character";
			break;
		case Types.NUMERIC: // 2
			switch (scale) {
			case 0:
				dataType = "Number";
				break;
			case -127:
				dataType = "Float";
				break;
			default:
				dataType = "Number";
			}
			break;
		case Types.VARCHAR: // 12
			dataType = "String";
			break;
		case Types.DATE: // 91
			dataType = "Date";
			break;
		case Types.TIMESTAMP: // 93
			dataType = "Date";
			break;
		case Types.BLOB:
			dataType = "Blob";
			break;
		default:
			dataType = "String";
		}
		return dataType;
	}

	public List<String> arrayToList(String arr[]) {
		List<String> list = null;
		if (arr != null) {
			list = new ArrayList<String>(arr.length);
			for (String s : arr) {
				list.add(s);
			}
		} else
			list = new ArrayList<String>(0);
		return list;
	}

	/**
	 * 获取记录集，返回类型为List<String[]>
	 * 
	 * @param sql
	 * @param inputValue
	 * @param outputValue
	 * @return
	 */
	public List<String[]> getArrayList(String sql, String inputValue[],
			String outputValue[]) throws SQLException {
		List<String[]> list = new ArrayList<String[]>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = SessionFactoryUtils.getDataSource(getSessionFactory())
					.getConnection();
			;
			stmt = conn.prepareStatement(sql);
			for (int i = 0; i < inputValue.length; i++)
				stmt.setString(i + 1, inputValue[i]);
			String tmp[];
			for (rs = stmt.executeQuery(); rs.next(); list.add(tmp)) {
				tmp = new String[outputValue.length];
				for (int i = 0; i < outputValue.length; i++) {
					String tempstr = rs.getString(outputValue[i]);
					tmp[i] = tempstr == null || !outputValue[i].contains("sj")
							|| tempstr.length() != 14 ? tempstr
							: (new StringBuilder(String.valueOf(tempstr
									.substring(0, 4)))).append("年").append(
									tempstr.substring(4, 6)).append("月")
									.append(tempstr.substring(6, 8)).append(
											"日 ").append(
											tempstr.substring(8, 10)).append(
											":").append(
											tempstr.substring(10, 12)).append(
											":").append(
											tempstr.substring(12, 14))
									.toString();
					if (tmp[i] == null || tmp[i].equalsIgnoreCase(""))
						tmp[i] = "";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			list = null;
			throw e;
		}
		return list;
	}

	/**
	 * 获取单行记录，返回类型为String[]
	 * 
	 * @param sql
	 * @param inputValue
	 * @param outputValue
	 * @return
	 */
	public String[] getOneRs(final String sql, final String inputValue[],
			final String outputValue[]) throws SQLException {
		final String result[] = new String[outputValue.length];
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		Work work = new Work() {
			public void execute(Connection conn) throws SQLException {
				// 通过JDBC API执行用于批量更新的SQL语句
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.executeUpdate();
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					for (int i = 0; i < outputValue.length; i++)
						result[i] = rs.getString(outputValue[i]);
				}
			}
		};
		/*
		 * try { conn = SessionFactoryUtils.getDataSource(getSessionFactory())
		 * .getConnection(); ; stmt = conn.prepareStatement(sql); for (int i =
		 * 0; i < inputValue.length; i++) stmt.setString(i + 1, inputValue[i]);
		 * rs = stmt.executeQuery(); if (rs.next()) { for (int i = 0; i <
		 * outputValue.length; i++) result[i] = rs.getString(outputValue[i]); }
		 * else { result = (String[]) null; } } catch (SQLException e) {
		 * e.printStackTrace(); result = null; throw e; }
		 */
		this.getSession().doWork(work);
		return result;
	}

	protected String[] getColumnName(String sql) throws SQLException {
		String tit[] = (String[]) null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSetMetaData rsmd = null;
		ResultSet rs = null;
		try {
			conn = SessionFactoryUtils.getDataSource(getSessionFactory())
					.getConnection();
			;
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			rsmd = rs.getMetaData();
			tit = new String[rsmd.getColumnCount()];
			for (int i = 0; i < rsmd.getColumnCount(); i++)
				tit[i] = rsmd.getColumnLabel(i + 1);
		} catch (SQLException e) {
			e.printStackTrace();
			tit = new String[0];
			throw e;
		}
		return tit;
	}

	/**
	 * 获取所有中文注释列名
	 * 
	 * @param cName
	 * @param tabName
	 * @return
	 * @throws SQLException
	 */
	/**
	 * 获取所有中文注释列名
	 * 
	 * @param cName
	 * @param tabName
	 * @return
	 * @throws SQLException
	 */
	public String[] getColumnNameCN(String cName[], String tabName)
			throws SQLException {
		String tit[] = new String[cName.length];
		String sql = "select COMMENTS from user_col_comments where table_name=? and COLUMN_NAME=?";
		for (int i = 0; i < cName.length; i++) {
			String tmp[] = getOneRs(sql, new String[] { tabName.toUpperCase(),
					cName[i].toUpperCase() }, new String[] { "COMMENTS" });
			if (tmp == null) {
				tit[i] = cName[i];
			} else {
				tit[i] = tmp[0];
				if (tit[i] == null
						|| tit[i].toLowerCase().equalsIgnoreCase("null")
						|| tit[i].equalsIgnoreCase(""))
					tit[i] = cName[i];
				if (tit[i].indexOf('(') >= 0)
					tit[i] = tit[i].substring(0, tit[i].indexOf('('));
				if (tit[i].indexOf('（') >= 0)
					tit[i] = tit[i].substring(0, tit[i].indexOf('（'));
			}
		}
		return tit;
	}

	public String[] getColumnNameCN(String[] fields, String[] tableNames)
			throws SQLException {
		String tit[] = new String[fields.length];
		StringBuffer querySqlBuffer = new StringBuffer(
				"select COLUMN_NAME,COMMENTS from user_col_comments"
						+ " where table_name in(");
		for (int i = 0; i < tableNames.length; i++) {
			if (i != tableNames.length - 1)
				querySqlBuffer.append("'" + tableNames[i] + "',");
			else
				querySqlBuffer.append("'" + tableNames[i] + "')");
		}
		querySqlBuffer.append(" and COLUMN_NAME in(");
		for (int i = 0; i < fields.length; i++) {
			if (i != fields.length - 1)
				querySqlBuffer.append("'" + fields[i] + "',");
			else
				querySqlBuffer.append("'" + fields[i] + "')");
		}
		Map<String, String> columnCNMap = getResultMap(querySqlBuffer
				.toString(), null, new String[] { "COLUMN_NAME", "COMMENTS" });
		int mark = 0;
		for (String field : fields) {
			tit[mark] = columnCNMap.get(field.toUpperCase().trim());
			if (tit[mark] == null
					|| tit[mark].toLowerCase().equalsIgnoreCase("null")
					|| tit[mark].equalsIgnoreCase("")) {
				tit[mark] = field;
				continue;
			}
			// 忽略注释
			if (tit[mark].indexOf('(') >= 0)
				tit[mark] = tit[mark].substring(0, tit[mark].indexOf('('));
			if (tit[mark].indexOf('（') >= 0)
				tit[mark] = tit[mark].substring(0, tit[mark].indexOf('（'));
			if (tit[mark].indexOf('[') >= 0)
				tit[mark] = tit[mark].substring(0, tit[mark].indexOf('['));
			if (tit[mark].indexOf('【') >= 0)
				tit[mark] = tit[mark].substring(0, tit[mark].indexOf('【'));
			mark++;
		}
		return tit;
	}

	public Map<String, String> getResultMap(final String sql,
			final String inputValue[], final String outputValue[])
			throws SQLException {
		final HashMap<String, String> map = new HashMap<String, String>();
		Work work = new Work() {
			public void execute(Connection conn) throws SQLException {
				// 通过JDBC API执行用于批量更新的SQL语句
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.executeUpdate();
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					map.put(rs.getString(outputValue[0]), rs
							.getString(outputValue[1]));
				}
			}
		};
		this.getSession().doWork(work);
		/*
		 * try { conn = SessionFactoryUtils.getDataSource(getSessionFactory())
		 * .getConnection(); stmt = conn.prepareStatement(sql); if (inputValue !=
		 * null) { for (int i = 0; i < inputValue.length; i++) stmt.setString(i +
		 * 1, inputValue[i]); } rs = stmt.executeQuery(); map = new HashMap<String,
		 * String>(); while (rs.next()) { map.put(rs.getString(outputValue[0]),
		 * rs .getString(outputValue[1])); } } catch (SQLException e) {
		 * e.printStackTrace(); throw e; }
		 */
		return map;
	}

	private ClassMetadata getClassMetaData(Class cls) {
		ClassMetadata entityMeta = this.getHibernateTemplate()
				.getSessionFactory().getClassMetadata(cls);
		return entityMeta;
	}

	private Map getAllClassMetaData() {
		SessionFactory factory = getHibernateTemplate().getSessionFactory();
		Map metaMap = factory.getAllClassMetadata();
		return metaMap;
	}

	public String[] getIdentiferColumns(String tableName) {
		String[] pks = null;
		Map metaMap = getAllClassMetaData();
		for (String key : (Set<String>) metaMap.keySet()) {
			AbstractEntityPersister classMetadata = (AbstractEntityPersister) metaMap
					.get(key);
			if (tableName.equalsIgnoreCase(classMetadata.getTableName())) {
				pks = classMetadata.getIdentifierColumnNames();
			}
		}
		return pks;
	}

	public String[] getUniqueColumns(String tableName) {
		String[] pks = null;
		Map metaMap = getAllClassMetaData();
		for (String key : (Set<String>) metaMap.keySet()) {
			AbstractEntityPersister classMetadata = (AbstractEntityPersister) metaMap
					.get(key);
			if (tableName.equalsIgnoreCase(classMetadata.getTableName())) {
				IdentifierGenerator idGen = classMetadata
						.getIdentifierGenerator();
			}
		}
		return pks;
	}

	public String[] getTableClsMapping() {
		SessionFactory factory = getHibernateTemplate().getSessionFactory();
		Map metaMap = factory.getAllClassMetadata();
		Map<String, String> entityTableMap = new HashMap<String, String>();
		for (String key : (Set<String>) metaMap.keySet()) {
			AbstractEntityPersister classMetadata = (AbstractEntityPersister) metaMap
					.get(key);
			System.out.println("classMeata Key" + key);
			String[] pks = classMetadata.getIdentifierColumnNames();
			String tableName = classMetadata.getTableName().toLowerCase();
			String className = classMetadata.getEntityMetamodel().getName();
			if (logger.isDebugEnabled()) {
				logger.debug(tableName + " --> " + className);
			}
			entityTableMap.put(tableName, className.substring(className
					.lastIndexOf(".") + 1));
		}
		return null;
	}

	public Map<String, Object> getPropertiesMeta(Object entityObj,
			String propertyName) {

		ClassMetadata entityMeta = this.getHibernateTemplate()
				.getSessionFactory().getClassMetadata(entityObj.getClass());
		Object[] propertyValues = entityMeta.getPropertyValues(entityObj,
				EntityMode.POJO);
		String[] propertyNames = entityMeta.getPropertyNames();
		Type[] propertyTypes = entityMeta.getPropertyTypes();

		// get a Map of all properties which are not collections or associations
		Map<String, Object> namedValues = new HashMap<String, Object>();
		for (int i = 0; i < propertyNames.length; i++) {
			if (!propertyTypes[i].isEntityType()
					&& !propertyTypes[i].isCollectionType()) {
				namedValues.put(propertyNames[i], propertyValues[i]);
			}
		}
		return namedValues;
	}

	public Set<String> getTableName(String sql) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSetMetaData rsmd = null;
		ResultSet rs = null;
		Set<String> tableSet = new HashSet<String>();
		try {
			conn = SessionFactoryUtils.getDataSource(getSessionFactory())
					.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if (rs != null) {
				rs.next();
				// rs.previous();
			}
			rsmd = rs.getMetaData();
			OracleResultSetMetaData orarsmd = (OracleResultSetMetaData) rsmd;
			orarsmd.isSearchable(1);
			// orarsmd.
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				if (!tableSet.contains(rsmd.getTableName(i))) {
					tableSet.add(orarsmd.getTableName(i));
					tableSet.add(orarsmd.getSchemaName(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return tableSet;
	}
}
