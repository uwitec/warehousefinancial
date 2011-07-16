package com.wfms.common.util;

import java.security.MessageDigest;

public class MD5 {

	/**
	 * MD5加密，返回字母为小写的加密结果，默认长度为32位
	 * @param str	需要加密的字符串
	 * @return
	 */
	public static String getMd5LowerCase(String str){
		return xxMd5(str, 32).toLowerCase();
	}
	
	/**
	 * MD5加密，返回字母为大写的加密结果，默认长度为32位
	 * @param str	需要加密的字符串
	 * @return
	 */
	public static String getMd5Capital(String str){
		return dxMd5(str, 32).toUpperCase();
	}

	/**
	 * MD5加密，返回字母为大写的加密结果
	 * @param str		需要加密的字符串
	 * @param count		需要加密长度
	 * @return
	 */
	public static String dxMd5(String str, int count){
		return encodeMd5(str, count).toUpperCase();
	}
	
	/**
	 * MD5加密，返回字母为小写的加密结果
	 * @param str		需要加密的字符串
	 * @param count		需要加密长度
	 * @return
	 */
	public static String xxMd5(String str, int count){
		return encodeMd5(str, count).toLowerCase();
	}
	
	/**
	 * 返回默认加密后信息(及没有大小写之分)
	 * @param str		需要加密的字符串
	 * @param count		需要加密长度
	 * @return
	 */
	public static String defaultMd5(String str, int count){
		return encodeMd5(str, count);
	}
	
	/**
	 * MD5加密，返回正常加密后结果
	 * @param str		需要加密的字符串
	 * @param count		需要加密长度
	 * @return
	 */
	public static String encodeMd5(String str, int count){
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0){
					i += 256;
				}
				if (i < 16){
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			if(count == 16){
				result = buf.toString().substring(8, 24);
			}else{
				result = buf.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String args[]) {
		System.out.println(dxMd5("0",32));
		System.out.println(xxMd5("0",16));
		System.out.println(getMd5LowerCase("0"));
		System.out.println(encodeMd5("0",32));
	}
}
