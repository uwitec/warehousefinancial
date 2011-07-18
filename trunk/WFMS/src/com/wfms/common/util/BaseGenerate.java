package com.wfms.common.util;

/**
 * @author CYC
 * @see BaseGenerate
 * @version 1.0
 *
 */
public class BaseGenerate {

	/**
	 * 
	 */
	public static String[] generateXnByNj(int nj,int xz)
	{
		String[] xns = new String[xz];
		for(int i=0;i<xz;i++,nj++){
			xns[i] = nj+"-"+(nj+1);
		}
		return xns;
	}
	
}
