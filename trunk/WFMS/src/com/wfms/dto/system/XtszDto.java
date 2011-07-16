/**
 * 
 */
package com.wfms.dto.system;

import java.util.ArrayList;
import java.util.List;

import com.wfms.model.system.XtszEntity;

/**
 * @author Administrator
 *
 */
public class XtszDto {
	private List<XtszEntity> xtszList = new ArrayList<XtszEntity>();

	public List<XtszEntity> getXtszList() {
		return xtszList;
	}

	public void setXtszList(List<XtszEntity> xtszList) {
		this.xtszList = xtszList;
	}
	
}
