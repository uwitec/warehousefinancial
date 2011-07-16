package com.wfms.dao.system;

import java.util.List;

import com.wfms.model.system.ModuleGenInfo;
import com.wfms.model.system.RightGenInfo;

public interface IGnmkDao {
	public ModuleGenInfo getGnmk(int gnmkId);
	
	public List<ModuleGenInfo> getAllGnmk();
	
	public List<ModuleGenInfo> getSonGnmk(int parentId);
	
	public int addGnmk(ModuleGenInfo gnmk);

	public int delGnmk(int id);
	
	public int updGnmk(ModuleGenInfo gnmk);
	
	public int addQx(RightGenInfo qx);
	
	public int delQx(RightGenInfo qx);
	
	public List<ModuleGenInfo> getJsyyGnmk(int jsId);
	
	public List<ModuleGenInfo> getJswyGnmk(int jsId);
	
	public List<ModuleGenInfo> getAllLeafGnmk();
	
	public List<ModuleGenInfo> getGnmkByQxIds(int[] qxIds);
	
	public List<ModuleGenInfo> getGnmkByIds(int[] gnmkIds);
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:getAllRootGnmk</b>
	 * <dd>方法作用：查询所有根节点功能菜单
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@return
	 * </dl>
	 */
	public List<ModuleGenInfo> getAllRootGnmk();
}	
