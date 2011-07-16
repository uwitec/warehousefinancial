package com.wfms.service.system;

import java.util.List;

import com.wfms.dto.system.GnmkDto;
import com.wfms.model.system.ModuleGenInfo;

public interface IGnmkService {
	/**
	 * 获取全部功能模块
	 * @return GnmkDto
	 */
	public GnmkDto getAllGnmk();
	/**
	 * 获取全部功能模块全路�?
	 * @return GnmkDto
	 */
	public GnmkDto getAllGnmk(List<ModuleGenInfo> list);
	
	public GnmkDto getSonGnmk(int parentId);
	
	public int addGnmk(ModuleGenInfo gnmk);

	public int delGnmk(int id);
	
	public int updGnmkList(List<ModuleGenInfo> list);
	
	public GnmkDto getJsyyGnmk(int jsId);
	
	public GnmkDto getJswyGnmk(int jsId);
	
	public GnmkDto getYhwyGnmk(List<ModuleGenInfo> list);
}
