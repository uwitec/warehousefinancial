package com.wfms.common.function.util;


/**
 * @author CYC
 * @see ConditionUtil
 * @version 1.0
 *
 */
public class ConditionUtil {

	public static String addCondition(String conditions, String key, String value) {
		if ((conditions == null) || (key == null) || (value == null)|| (key == "") || (value == ""))
			return conditions;
		conditions = (new StringBuilder(String.valueOf(conditions))).append(
				" and ").append(key).append(" = '").append(value).append("'")
				.toString();
		return conditions;
	}
	
	public static String addCondition(String conditions, String condition) {
		if ((conditions == null)||(condition==null) || "".equals(condition))
			return conditions;
		conditions = (new StringBuilder(String.valueOf(conditions))).append(
				" and ").append(condition).toString();
		return conditions;
	}
	
	public static String addLikeCondition(String conditions, String key, String value) {
		if ((conditions == null) || (key == null) || (value == null)|| (key == "") || (value == ""))
			return conditions;
		conditions = (new StringBuilder(String.valueOf(conditions))).append(
				" and ").append(key).append(" like '%").append(value).append("%'")
				.toString();
		return conditions;
	}
	public static String addDateGreaterCondition(String conditions, String key, String value) {
		if ((conditions == null) || (key == null) || (value == null)|| (key == "") || (value == ""))
			return conditions;
		conditions = (new StringBuilder(String.valueOf(conditions)))
			.append(" and to_date(")
			.append(key).append(",'yyyy-mm-dd')>to_date('")
			.append(value)
			.append("','yyyy-mm-dd')")
			.toString();
		return conditions;
	}
	public static String addDateLesserCondition(String conditions, String key, String value) {
		if ((conditions == null) || (key == null) || (value == null)|| (key == "") || (value == ""))
			return conditions;
		conditions = (new StringBuilder(String.valueOf(conditions)))
			.append(" and to_date(")
			.append(key).append(",'yyyy-mm-dd')<to_date('")
			.append(value)
			.append("','yyyy-mm-dd')")
			.toString();
		return conditions;
	}
	public static String addDateGreaterSysTimeCondition(String conditions, String key) {
		if ((conditions == null) || (key == null) || (key == "") )
			return conditions;
		conditions = (new StringBuilder(String.valueOf(conditions)))
			.append(" and to_date(")
			.append(key).append(",'yyyy-mm-dd')>(sysdate-1)")
			.toString();
		return conditions;
	}
	public static String addDateLesserSysTimeCondition(String conditions, String key) {
		if ((conditions == null) || (key == null) || (key == ""))
			return conditions;
		conditions = (new StringBuilder(String.valueOf(conditions)))
			.append(" and to_date(")
			.append(key).append(",'yyyy-mm-dd')<(sysdate-1)")
			.toString();
		return conditions;
	}
	
	public static String createOrderBy(String[] cols,boolean isAsc){
		if (cols==null||cols.length==0) return null;
		String order = " order by ";
		for(int i=0;i<cols.length-1;i++){
			order = order + cols[i] + ",";
		}
		order=order+cols[cols.length-1];
		if(isAsc){
			order=order+" asc";
		}else
			order=order+" desc";
		
		return order;		
	}
	
	public static String addOrderBy(String orders,String[] cols,boolean isAsc){
		if(orders==null||orders.equalsIgnoreCase("")) return null;
		if (cols==null||cols.length==0) return null;
		orders = orders+",";
		for(int i=0;i<cols.length-1;i++){
			orders = orders + cols[i] + ",";
		}
		orders=orders+cols[cols.length-1];
		if(isAsc){
			orders=orders+" asc";
		}else
			orders=orders+" desc";
		
		return orders;		
	}
	
}

