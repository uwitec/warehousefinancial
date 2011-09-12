package com.wfms.common.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.wfms.common.orm.Condition;
import com.wfms.common.orm.Page;
import com.wfms.common.orm.Rule;



/**
 * QL语句组装
 * 
 * @author CYC
 * 
 */
public class QLAppender {

	public static String where(String[] wheres) {
		if (wheres != null && wheres.length > 0) {
			StringBuffer where = new StringBuffer();
			where.append(" ");
			where.append("where");
			where.append(" ");
			for (int i = 0; i < wheres.length; i++) {
				if (wheres[i] == null) {
					continue;
				}
				where.append(wheres[i]);

				if (i + 1 < wheres.length) {
					where.append(" ");
					where.append("and");
					where.append(" ");
				}
			}
			return where.toString();
		}
		return "";
	}

	/**
	 * 返回例如userName,password
	 * 
	 * @param columns
	 * @return
	 */
	public String intoColumns(String columns[]) {
		if (columns != null && columns.length > 0) {
			return "(" + split(columns) + ")";
		} else {

			return "*";
		}
	}

	/**
	 * 返回字符串 values(?,?,?....)
	 * 
	 * @param length
	 * @return
	 */
	public String values(String valuesColumn) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" values").append("(").append(valuesColumn)
				.append(")");
		return sqlBuffer.toString();
	}

	/**
	 * 返回?,?,?样子的字符串
	 * 
	 * @param length
	 * @param bracket
	 * @return
	 */
	public String splitParams(int length) {
		StringBuffer sqlBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sqlBuffer.append("?");
			if (i + 1 < length) {
				sqlBuffer.append(",");
			}
		}
		return sqlBuffer.toString();
	}

	public String split(String[] columns) {
		if (columns == null || columns.length == 0) {
			return "";
		}
		StringBuffer sqlBuffer = new StringBuffer();
		for (int i = 0; i < columns.length; i++) {
			sqlBuffer.append(columns[i]);
			if (i + 1 < columns.length) {
				sqlBuffer.append(",");
			}
		}
		return sqlBuffer.toString();
	}

	public String set(String[] set) {
		if (set == null || set.length == 0) {
			return "";
		}
		StringBuffer sqlBuffer = new StringBuffer(" set ");
		sqlBuffer.append(split(set));
		return sqlBuffer.toString();
	}

	public String orderBy(String by, boolean desc) {
		if (by == null) {
			return "";
		}
		return " order by " + by + " " + (desc ? "desc" : "");
	}

	public static String orderBy(Page page) {
		if (page.getOrderByColumn() != null
				&& page.getOrderByColumn().length() > 0
				&& page.getOrderByRule() != null
				&& page.getOrderByRule().length() > 0) {
			return " ORDER BY " + page.getOrderByColumn() + " "
					+ page.getOrderByRule() + " ";
		}
		return "";
	}

	public static void conditions(StringBuffer strbf, Object[] queryParmas,
			List<Condition> conditions) {
		List<Condition> conditionTmp = new ArrayList<Condition>();
		for (Condition c : conditions) {
			if (c.getValue() instanceof Collection) {
				conditionTmp.add(c);
			}
		}
		conditions.removeAll(conditionTmp);
		conditions.addAll(conditionTmp);
		for (int i = 0; i < conditions.size(); i++) {
			if (conditions.get(i).getRule() == Rule.ALLLIKE) {
				strbf.append(" AND " + conditions.get(i).getName() + " like ?");
				queryParmas[i] = "%" + conditions.get(i).getValue() + "%";
				continue;
			} else if (conditions.get(i).getRule() == Rule.LEFTLIKE) {
				strbf.append(" AND " + conditions.get(i).getName() + " like ?");
				queryParmas[i] = "%" + conditions.get(i).getValue();
				continue;
			} else if (conditions.get(i).getRule() == Rule.RIGHTLIKE) {
				strbf.append(" AND " + conditions.get(i).getName() + " like ?");
				queryParmas[i] = conditions.get(i).getValue() + "%";
				continue;
			} else if (conditions.get(i).getRule() == Rule.THAN) {
				strbf.append(" AND " + conditions.get(i).getName() + ">?");
				queryParmas[i] = conditions.get(i).getValue();
				continue;
			} else if (conditions.get(i).getRule() == Rule.LESSTHAN) {
				strbf.append(" AND " + conditions.get(i).getName() + "<?");
				queryParmas[i] = conditions.get(i).getValue();
				continue;
			} else if (conditions.get(i).getRule() == Rule.EQUAL) {
				strbf.append(" AND " + conditions.get(i).getName() + "=?");
				queryParmas[i] = conditions.get(i).getValue();
				continue;
			} else if (conditions.get(i).getRule() == Rule.NOTEQUAL) {
				strbf.append(" AND " + conditions.get(i).getName() + "<>?");
				queryParmas[i] = conditions.get(i).getValue();
				continue;
			} else if (conditions.get(i).getRule() == Rule.IN) {
				strbf.append(" AND " + conditions.get(i).getName()
						+ " in (:list" + i + ")");
				queryParmas[i] = conditions.get(i).getValue();
				continue;
			} else if (conditions.get(i).getRule() == Rule.NOTIN) {
				strbf.append(" AND " + conditions.get(i).getName()
						+ " NOT IN (:list" + i + ")");
				queryParmas[i] = conditions.get(i).getValue();
				continue;
			} else if (conditions.get(i).getRule() == Rule.NOTIN_STRING) {
				strbf.append(" AND " + conditions.get(i).getName()
						+ " NOT IN (?)");
				queryParmas[i] = conditions.get(i).getValue();
				continue;
			} else if (conditions.get(i).getRule() == Rule.IN_STRING) {
				strbf.append(" AND " + conditions.get(i).getName() + " IN (?)");
				queryParmas[i] = conditions.get(i).getValue();
				continue;
			} else {
				strbf.append(" AND " + conditions.get(i).getName()
						+ " like('%?%')");
				queryParmas[i] = conditions.get(i).getValue();
				continue;
			}

		}
	}

	public static void conditions(StringBuffer strbf, Object[] queryParmas,
			String tableName, List<Condition> conditions) {
		List<Condition> conditionTmp = new ArrayList<Condition>();
		for (Condition c : conditions) {
			if (c.getValue() instanceof Collection) {
				conditionTmp.add(c);
			}
		}
		conditions.removeAll(conditionTmp);
		conditions.addAll(conditionTmp);
		for (int i = 0; i < conditions.size(); i++) {
			if (conditions.get(i).getRule() == Rule.ALLLIKE) {
				strbf.append(" AND " + tableName + "."
						+ conditions.get(i).getName() + " like ?");
				queryParmas[i] = "%" + conditions.get(i).getValue() + "%";
				continue;
			} else if (conditions.get(i).getRule() == Rule.LEFTLIKE) {
				strbf.append(" AND " + tableName + "."
						+ conditions.get(i).getName() + " like ?");
				queryParmas[i] = "%" + conditions.get(i).getValue();
				continue;
			} else if (conditions.get(i).getRule() == Rule.RIGHTLIKE) {
				strbf.append(" AND " + tableName + "."
						+ conditions.get(i).getName() + " like ?");
				queryParmas[i] = conditions.get(i).getValue() + "%";
				continue;
			} else if (conditions.get(i).getRule() == Rule.THAN) {
				strbf.append(" AND " + tableName + "."
						+ conditions.get(i).getName() + ">?");
				queryParmas[i] = conditions.get(i).getValue();
				continue;
			} else if (conditions.get(i).getRule() == Rule.LESSTHAN) {
				strbf.append(" AND " + tableName + "."
						+ conditions.get(i).getName() + "<?");
				queryParmas[i] = conditions.get(i).getValue();
				continue;
			} else if (conditions.get(i).getRule() == Rule.EQUAL) {
				strbf.append(" AND " + tableName + "."
						+ conditions.get(i).getName() + "=?");
				queryParmas[i] = conditions.get(i).getValue();
				continue;
			} else if (conditions.get(i).getRule() == Rule.NOTEQUAL) {
				strbf.append(" AND " + tableName + "."
						+ conditions.get(i).getName() + "<>?");
				queryParmas[i] = conditions.get(i).getValue();
				continue;
			} else if (conditions.get(i).getRule() == Rule.IN) {
				strbf.append(" AND " + tableName + "."
						+ conditions.get(i).getName() + " in (:list" + i + ")");
				queryParmas[i] = conditions.get(i).getValue();
				continue;
			} else if (conditions.get(i).getRule() == Rule.NOTIN) {
				strbf.append(" AND " + conditions.get(i).getName()
						+ " NOT IN (:list" + i + ")");
				queryParmas[i] = conditions.get(i).getValue();
				continue;
			}
			// else if(conditions.get(i).getRule() == Rule.NOTIN_STRING){
			// strbf.append(" AND " + conditions.get(i).getName() +
			// " NOT IN (");
			// queryParmas[i] = conditions.get(i).getValue();
			// if(conditions.get(i).getValue().getClass().isArray()){
			// for(int j = 0; j < Array.getLength(conditions.get(i).getValue());
			// j++){
			// Object value = Array.get(conditions.get(i).getValue(), i);
			// if(j != 0 ){
			// strbf.append(",");
			// }
			// strbf.append("?");
			// i++;
			// }
			// strbf.append(")");
			//
			// }
			// continue;
			// }else if(conditions.get(i).getRule() == Rule.IN_STRING){
			// strbf.append(" AND " + conditions.get(i).getName() + " IN (?)");
			// queryParmas[i] = conditions.get(i).getValue();
			// continue;
			// }
			else {
				strbf.append(" AND " + tableName + "."
						+ conditions.get(i).getName() + " like('%?%')");
				queryParmas[i] = conditions.get(i).getValue();
				continue;
			}

		}
	}
	
	public static StringBuffer preWhere(){
		return new StringBuffer(" WHERE 1=1");
	}
}
