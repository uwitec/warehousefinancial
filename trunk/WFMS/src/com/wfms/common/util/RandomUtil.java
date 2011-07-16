package com.wfms.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtil {
	/**
	 * <dl>
	 * <b>方法�?:getRan</b>
	 * <dd>方法作用：生成不重复随机数方法，参数为生成的个数，如果个数为0则返回空数组
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param size 
	 * <dd>@return
	 * </dl>
	 */
	public static int[] getRan(int size){
		if(size == 0){
			return new int[]{};
		}
		int[] ary = new int[size];
		List<Integer> list = new ArrayList<Integer>();
		Random ran = new Random();
		list.add(ran.nextInt(size));
		ary[0] = list.get(0);
		for(int i = 1;i< size;i++){
			boolean isAdd = false;
			while(!isAdd){
				isAdd = true;
				int rs = ran.nextInt(size);
				for(int num : list){
					if(num == rs){
						isAdd = false;
						break;
					}
				}
				if(isAdd == true){
					ary[i] = rs;
					list.add(rs);
				}
			}
		}
		return ary;
	}
}
