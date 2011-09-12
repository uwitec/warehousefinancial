package com.wfms.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtil {
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
