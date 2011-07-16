package com.wfms.common.entity;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.wfms.common.util.StringUtil;

public class InvisibleFilter extends AbstractMethodFilter {
	// 过滤条件，标注中有符合这个条件的property将被过滤掉
	private String _sGUIID;
	private String[] _sGUIDS;

	public InvisibleFilter(final String guiid) {
		_sGUIID = guiid;
	}

	public InvisibleFilter(final String[] guiids) {
		_sGUIDS = guiids;
	}

	public boolean apply(final Method method) {
		if (StringUtil.isNullOrEmpty(_sGUIID)
				&& (StringUtil.isNullOrSizeZero(_sGUIDS))) {
			return false; // 表示不做限制
		}
		if (!StringUtil.isNullOrEmpty(_sGUIID)) {
			if (method.isAnnotationPresent(Invisible.class)) {
				Invisible anno = method.getAnnotation(Invisible.class);
				String[] value = anno.value();
				for (int i = 0; i < value.length; i++) {
					if (_sGUIID.equals(value[i])) {
						return true;
					}
				}
			}
		} else if (StringUtil.isNullOrSizeZero(_sGUIDS)) {
			if (method.isAnnotationPresent(Invisible.class)) {
				Invisible anno = method.getAnnotation(Invisible.class);
				String[] value = anno.value();
				Set<String> compareSet = new HashSet<String>();
				for(String val:value)
				{
					compareSet.add(val);
				}
				for(String filVal:_sGUIDS)
				{
					if(!compareSet.add(filVal))
					{
						return true;
					}
				}
			}
		}
		return false;
	}
}
