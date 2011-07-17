package com.wfms.common.function.util;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

/**
 * 
 * <dl>  Description
 *  <dd> 项目名称：数字化校园信息平台ECMS
 *  <dd> 类名称：DataImportValidater
 *  <dd> 类描述：
 *  <dd> 创建人：Administrator
 *  <dd> 创建时间：Mar 4, 2010 1:59:38 PM
 *  <dd> 修改人：�?
 *  <dd> 修改时间：无
 *  <dd> 修改备注：无
 * </dl>
 * @author CYC
 * @see DataImportValidater
 * @version 1.0
 *
 */
public class DataImportValidater {
	public static boolean required(String key, Map<String, String> fieldValueMap) {
		if (fieldValueMap == null)
			return false;
		if (fieldValueMap.get(key) == null
				|| fieldValueMap.get(key).equalsIgnoreCase(""))
			return false;
		else
			return true;
	}

	public static boolean isXN(String value) {
		if (value.length() != 9)
			return false;
		return true;
	}

	public static String getXQ(String value) {
		if ("第一学期".equalsIgnoreCase(value) || "1".equalsIgnoreCase(value))
			return "1";
		else if ("第二学期".equalsIgnoreCase(value) || "2".equalsIgnoreCase(value))
			return "2";
		else
			return null;
	}

	public static boolean init(String key, String value,
			Map<String, String> fieldValueMap) {
		if (fieldValueMap.get(key) == null
				|| fieldValueMap.get(key).equalsIgnoreCase("")) {
			fieldValueMap.put(key, value);
		}
		return true;
	}

	public static boolean isValidateDate(String key,
			Map<String, String> fieldValueMap) {
		String dateStr = fieldValueMap.get(key);
		// SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-mm-ss");
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.parse(dateStr);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean isValidateDBType(String value, String tableName,
			String column) {
		// DAO dao = new DAO();
		String[] keys = { column };
		String[] values = { value };
		try {
			// int rsCount = dao.getRsCount(tableName, keys, values);
			// if(rsCount>0) return true;
			// else return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static String getBooleanValue(String chValue, String defaultValue) {
		if (chValue == null || chValue.equalsIgnoreCase(""))
			return defaultValue; // ȱʡֵΪ
		if ("1".equalsIgnoreCase(chValue) || "true".equalsIgnoreCase(chValue)) {
			return "true";
		} else if ("0".equalsIgnoreCase(chValue)
				|| "false".equalsIgnoreCase(chValue)) {
			return "false";
		} else
			return null;
	}

	public static boolean hasImportGrant(String xh, HttpSession session) {
		// HttpSession session = WebContextFactory.get().getSession();
		String userGroup = (String) session.getAttribute("userGroup");
		if (userGroup == null || "".equals(userGroup))
			return false;
		// DAO dao = new DAO();
		/*
		 * if(userGroup.equalsIgnoreCase(Constants.YHLX_YXGLY)){ String yxdm =
		 * (String)session.getAttribute("userOrgan"); if(yxdm==null ||
		 * "".equals(yxdm)) { return false; } String xymcSql="select XYMC from
		 * xydmb where XYDM=?";
		 * 
		 * String sql = "select yx from view_xsxx where xh=?"; try { //String
		 * yxmc=dao.getOneRs(xymcSql,new String[]{yxdm},"XYMC"); //String realYx =
		 * dao.getOneRs(sql, new String[]{xh}, "YX"); //if (realYx!=null &&
		 * realYx.equalsIgnoreCase(yxmc)) return true; //else return false; }
		 * catch (SQLException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); return false; } } else { String existsSql =
		 * "select count(xh) rsCount from view_xsxx where xh=?"; String
		 * recordCount=""; try { //recordCount = dao.getOneRs(existsSql,new
		 * String[]{xh},"rsCount"); } catch (SQLException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 * if(recordCount==null || "0".equals(recordCount) ||
		 * "".equals(recordCount)) { return false; } }
		 */
		return true;
	}

	//浮点型判�?
	public static boolean isDecimal(String str) {
		if (str == null || "".equals(str))
			return false;
		Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
		return pattern.matcher(str).matches();
	}

	// 整型判断
	public static boolean isInteger(String str) {
		if (str == null)
			return false;
		Pattern pattern = Pattern.compile("[0-9]+");
		return pattern.matcher(str).matches();
	}

	public static boolean isValidateNumber(String value) {
		if (value == null || "".equals(value)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
		if (value.matches("\\d*") || pattern.matcher(value).matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * <dl>
	 * <b>方法�?:isValidatePrecision</b>
	 * <dd>方法作用：验证数字位�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??)
	 * <dd>
	 * 
	 * @param value
	 *            <dd>
	 * @param target
	 *            <dd>
	 * @return
	 *            </dl>
	 */
	public static boolean isValidatePrecision(String value, int target) {
		boolean validate = false;
		if (isValidateNumber(value)) {
			String temp = value.replaceAll("\\.", "");
			if (temp.length() <= target)
				validate = true;
		}
		return validate;
	}

	/**
	 * 
	 * <dl>
	 * <b>方法�?:isValidatePrecision</b>
	 * <dd>方法作用：验证数字精�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??)
	 * <dd>
	 * 
	 * @param value
	 *            <dd>
	 * @param target
	 *            <dd>
	 * @return
	 *            </dl>
	 */
	public static boolean isValidateScale(String value, int target) {
		boolean validate = false;
		if (isValidateNumber(value)) {
			int dotIndex = value.indexOf(".");
			if (dotIndex == -1 && target==0) {
				validate=true;
			}
			else if(dotIndex!=-1)
			{
				String temp = value.substring(dotIndex+1);
				if (temp.length() <= target) {
					validate = true;
				}
			}
		}
		return validate;
	}
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:isValidateCNChar</b>
	 * <dd>方法作用：验证中�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param value
	 * <dd>@return
	 * </dl>
	 */
	public static boolean isValidateCNChar(String value) {
		String regex="^[a-zA-Z0-9\u4E00-\u9FA5]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher match=pattern.matcher(value);
		if(match.matches())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean isValidateVarchar2Len(String value,int target) {
		boolean validate = false;
		if(value==null || "".equals(value))
		{
			return true;
		}
		if(value.getBytes().length<=target)
		{
			validate=true;
		}
		return validate;
	}

}
