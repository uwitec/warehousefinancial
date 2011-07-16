package com.wfms.common.util;

import com.wfms.common.entity.Page;



public class PageUtil {
	public static Page format(String start,String limit){
		Page page = new Page();
		int numStart = 0;
		int numLimit = 15;
		try {
			numStart = Integer.parseInt(start);
			numLimit = Integer.parseInt(limit);
		} catch (Exception e) {
			numStart = 0;
			numLimit = 15;
		}
		page.setStart(numStart);
		page.setLimit(numLimit);
		return page;
	}
}
