package com.wfms.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	public static Date formatDateToString(String strDate){
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf.parse(strDate);
		} catch (Exception e) {
			date = new Date();
		}
		return date;
	}
	public static Date formatDateTimeToString(String strDate){
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = sdf.parse(strDate);
		} catch (Exception e) {
			date = new Date();
		}
		return date;
	}
	public static String formatDate(Date date){
		String strDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			strDate = sdf.format(date);
		} catch (Exception e) {
			strDate = sdf.format(new Date());
		}
		return strDate;
	}
	public static String formatDateTime(Date date){
		String strDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			strDate = sdf.format(date);
		} catch (Exception e) {
			strDate = sdf.format(new Date());
		}
		return strDate;
	}
	
	public static String getCurrentDateString(){
		Calendar calendar=new GregorianCalendar();
		int m = calendar.get(Calendar.MONTH)+1;
		int d = calendar.get(Calendar.DAY_OF_MONTH);
		StringBuffer sbDateStr=new StringBuffer()
		.append(calendar.get(Calendar.YEAR))
		.append("-").append(m < 10 ? "0"+m : m)
		.append("-").append(d < 10 ? "0"+d : d);
		return String.valueOf(sbDateStr);
	}
	
	public static String getCurrentTimeString(){
		Calendar calendar=new GregorianCalendar();
		int m = calendar.get(Calendar.MONTH)+1;
		int d = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		
		StringBuffer sbTimeStr=new StringBuffer()
		.append(calendar.get(Calendar.YEAR))
		.append("-").append(m < 10 ? "0"+m : m)
		.append("-").append(d < 10 ? "0"+d : d)
		.append(" ").append(hour < 10 ? "0"+hour:hour)
		.append(":").append(minute < 10 ? "0"+minute:minute)
		.append(":").append(second < 10 ? "0"+second:second);
		return String.valueOf(sbTimeStr);
	}
}
