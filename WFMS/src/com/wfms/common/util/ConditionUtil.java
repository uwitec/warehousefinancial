package com.wfms.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wfms.common.orm.Condition;
import com.wfms.common.orm.Rule;

public class ConditionUtil {
	public static List<Condition> format(String strParams){
		if(strParams == null || "".equals(strParams)){
			return new ArrayList<Condition>();
		}
		List<Condition> cons = new ArrayList<Condition>();
		String[] params = strParams.split("&");
		for(String param : params){
			if(param == null || "".equals(param)){
				continue;
			}
			String[] strCon = param.split("=");
			if(strCon.length < 2 || strCon[1] == null || "".equals(strCon[1])){
				continue;
			}
			cons.add(new Condition(strCon[0],strCon[1]));
		}
		return cons;
	}

	public static List<Condition> format(String strParams,Map<String,Rule> config){
		if(strParams == null || "".equals(strParams)){
			return new ArrayList<Condition>();
		}
		List<Condition> cons = new ArrayList<Condition>();
		String[] params = strParams.split("&");
		for(String param : params){
			if(param == null || "".equals(param)){
				continue;
			}
			String[] strCon = param.split("=");
			if(strCon.length < 2 || strCon[1] == null || "".equals(strCon[1])){
				continue;
			}
			boolean bo = true;
			for(String key : config.keySet()){
				if(strCon[0].equals(key)){
					bo = false;
					cons.add(new Condition(strCon[0],strCon[1],config.get(key)));
					break;
				}
			}
			if(bo){
				cons.add(new Condition(strCon[0],strCon[1]));
			}
		}
		return cons;
	}
}
