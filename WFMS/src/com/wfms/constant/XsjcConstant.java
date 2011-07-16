package com.wfms.constant;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class XsjcConstant {

		/**
		 * 学生总评成绩-操行成绩
		 */
		public static final String XSZPCJ_CXCJ="cxcj";
		
		/**
		 * 学生总评成绩-理论+实习成绩平均分
		 */
		public static final String XSZPCJ_PJF ="pjf";
		
		/**
		 * 学生总评成绩-班级名次
		 */
		public static final String XSZPCJ_BJMC ="bjmc";
		
		/**
		 * 学生总评成绩-学号
		 */
		public static final String XSZPCJ_XSXH = "xh";
		
		/**
		 * 学生总评成绩-姓名
		 */
		public static final String XSZPCJ_XSXM = "xm";
		
		public static final Map<String, String> zpCjHeadersMap;
		
		static{
			zpCjHeadersMap = new LinkedHashMap<String, String>(3);
			zpCjHeadersMap.put(XsjcConstant.XSZPCJ_XSXH,"学号");
			zpCjHeadersMap.put(XsjcConstant.XSZPCJ_XSXM, "姓名");
			zpCjHeadersMap.put(XsjcConstant.XSZPCJ_CXCJ, "操行成绩");
			zpCjHeadersMap.put(XsjcConstant.XSZPCJ_PJF, "平均分");
			zpCjHeadersMap.put(XsjcConstant.XSZPCJ_BJMC, "班级排名");
		}
}
