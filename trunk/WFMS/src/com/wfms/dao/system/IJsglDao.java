package com.wfms.dao.system;

import java.util.List;

import com.wfms.model.system.PartGenInfo;
import com.wfms.model.system.PartRight;

public interface IJsglDao {
	public List<PartGenInfo> getAllJs();
	
	public PartGenInfo getJs(int id);
	
	public int addJs(PartGenInfo js);
	
	public int updJs(PartGenInfo js);
	
	public int delJs(int id);
	
	public int addJsqx(PartRight jsqx);
	
	public int delJsqx(PartRight jsqx);
	
	public int updJsqx(PartRight jsqx);
	
	public PartRight getJsqx(int partId,int gnmkId);
	
	public List<PartRight> getAllJsqx(int id);
	
	public List<PartGenInfo> getYhwyJs(String yhId);
	
	public int addYhjs(String yhId,int jsId);
	
	public int delYhjs(String yhId,int jsId);
	
	public List<PartGenInfo> getJsForZw(String zwId);
	
	public List<PartGenInfo> getJsForZwNot(String zwId);
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:getJsIdByJsmc</b>
	 * <dd>方法作用：根据角色名称查询角色编�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param jsmc
	 * <dd>@return
	 * </dl>
	 */
	public PartGenInfo getJsByJsmc(String jsmc);
	
	public int batchAddJsqx(List<PartRight> partRightList);
}
