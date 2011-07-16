package com.wfms.common.util;

import java.io.Serializable;

/**
	* 拼音处理信息
	* @author liusoft
	* created on 2002-12-19
	*/
class PinyinCode implements Serializable {
	public PinyinCode(String py, int cd) {
		pinyin = py;
		code = cd;
	}
	public String pinyin = null;
	public int code = 0;
}