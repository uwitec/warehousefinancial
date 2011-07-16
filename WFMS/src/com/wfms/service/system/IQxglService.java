/**
 * 
 */
package com.wfms.service.system;

import java.util.List;

import com.wfms.dto.system.QxDto;
import com.wfms.model.system.RightGenInfo;

/**
 * @author Administrator
 *
 */
public interface IQxglService {
	public QxDto getAllQx();
	
	public QxDto getQxById(int id);
	
	public int deleteQx(int id);
	
	public int addQx(RightGenInfo qx);
	
	public int updateQx(List<RightGenInfo> qxList);
	
}
