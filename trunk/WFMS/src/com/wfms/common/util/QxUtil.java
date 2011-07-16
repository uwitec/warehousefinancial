package com.wfms.common.util;

import java.util.List;

import com.wfms.model.system.ModuleGenInfo;

public class QxUtil {
	public static int getQx(Object id,Object gnmkList){
		List<ModuleGenInfo> list;
		int intId;
		if(gnmkList == null){
			return 0;
		}
		try {
			intId = Integer.parseInt(String.valueOf(id));
			list = (List<ModuleGenInfo>)gnmkList;
		} catch (Exception e) {
			System.out.println("功能模块权限读取失败!");
			return 0;
		}
		for(int i=0;i<list.size();i++){
			if(intId == list.get(i).getModuleId()){
				return list.get(i).getRightType();
			}
		}
		return 0;
	}
}
