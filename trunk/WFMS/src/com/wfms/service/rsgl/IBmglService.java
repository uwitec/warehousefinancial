/**
 * 
 */
package com.wfms.service.rsgl;

import java.util.List;

import com.wfms.dto.rsgl.BmDto;
import com.wfms.model.system.DepartGenInfo;


/**
 * @author Administrator
 *
 */
public interface IBmglService {
	public BmDto getAllBm();
	
	public BmDto getBmById(String depid);
	
	public int deleteBm(String depid);
	
	public int addBm(DepartGenInfo bm);
	
	public int updateBm(List<DepartGenInfo> bmList);
	
}
