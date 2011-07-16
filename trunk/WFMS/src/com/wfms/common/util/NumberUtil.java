package com.wfms.common.util;

import java.util.regex.Pattern;

public class NumberUtil {
	public static int format(String strInt) {
		int num = 0;
		try {
			num = Integer.parseInt(strInt);
		} catch (Exception e) {
			num = 0;
			System.out.println("数字格式化失败!");
		}
		return num;
	}

	public static int[] format(String[] strInts) {
		if (strInts.length == 0) {
			return new int[] {};
		}
		int num[] = new int[strInts.length];
		int i = 0;
		for (String strInt : strInts)
			try {
				num[i] = Integer.parseInt(strInt);
				i++;
			} catch (Exception e) {
				System.out.println("数字格式化失败!");
			}
		return num;
	}

	public static float formatFloat(String strFloat) {
		float num = 0;
		try {
			num = Float.parseFloat(strFloat);
		} catch (Exception e) {
			num = 0;
			System.out.println("数字格式化失败!");
		}
		return num;
	}

	public static double formatDouble(String strDouble) {
		double num = 0;
		try {
			num = Double.parseDouble(strDouble);
		} catch (Exception e) {
			num = 0;
			System.out.println("数字格式化失败!");
		}
		return num;
	}

	public static boolean isNumeric1(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumeric2(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static boolean isNumeric3(String str) {
		if (str.matches("\\d*")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNumeric4(String str) {
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}
		return true;
	}

}
