package com.wfms.common.util;

import java.util.ArrayList;
import java.util.List;

public class Decomposition {
	
	public static void main(String[] args) {
		int[] fctorList = decomFctor(2*3*5*7*9*11*13);
		for(int  fctor:fctorList)
		{
			System.out.println(fctor);
		}
	}
	
	public static List<Integer> decomQualityFctor(int   dd) 
	{ 
		if(dd<2)
		{
			System.out.println("无法分解");
			return new ArrayList<Integer>(0);
		}
		return ToFactor(dd,2); 
	} 
	
	public static int[] decomFctor(int   dd) 
	{ 
		int[] factorNums = new int[0];
		if(dd<2)
		{
			System.out.println("无法分解");
			return factorNums;
		}
		List<Integer> factorList = ToFactor(dd,2);
		if(factorList!=null)
		{
			factorNums = new int[factorList.size()];
			for(int i=0;i<factorList.size();i++)
			{
				factorNums[i] = factorList.get(i);
			}
		}
		return factorNums;
	} 
	
	private static List<Integer>   ToFactor(int   Td,int   FacNum) 
	{ 
		List<Integer> factorList = new ArrayList<Integer>(5);
		if(Td   !=   FacNum) 
		{ 
			if(Td%FacNum   ==   0) 
			{ 
				factorList.add(FacNum);
				factorList.addAll(ToFactor(Td/FacNum,2)); 
			} 
			else 
			{ 
				factorList.addAll(ToFactor(Td,FacNum+1)); 
			} 
		} 
		else 
		{ 
			factorList.add(FacNum);
		} 
		return factorList;
	} 

}
