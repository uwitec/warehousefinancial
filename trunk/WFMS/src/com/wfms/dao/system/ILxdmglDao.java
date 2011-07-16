/**
 * 类型代码管理接口
 */
package com.wfms.dao.system;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.common.util.ConditionBean;
import com.wfms.model.system.LxdmEntity;

public interface ILxdmglDao {
	public int insertLxdm(LxdmEntity lxdm);
	public int deleteLxdm(int lxdmId);
	public int updLxdm(LxdmEntity lxdm);
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:getAllLxdmCategory</b>
	 * <dd>方法作用：查询所有类型代码分�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@return
	 * </dl>
	 */
	public List<LxdmEntity> getAllLxdmCategory();
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:getAllLxdm</b>
	 * <dd>方法作用：查询所有类型代�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@return
	 * </dl>
	 */
	public List<LxdmEntity> getAllLxdm();
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:getAllLxdmBySuper</b>
	 * <dd>方法作用：根据父级类型代�?,查询�?有子级类型代�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param superDm 父级代码
	 * <dd>@return 			List of Lxdm
	 * </dl>
	 */
	public List<LxdmEntity> getSubLxdmBySupDm(String superDm);
	
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:getSubLxdmByDmjc</b>
	 * <dd>方法作用：根据代码简称查询所有子级类型代�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param superJc			父级代码�?�?
	 * <dd>@return				List of Lxdm
	 * </dl>
	 */
	public List<LxdmEntity> getSubLxdmBySuperJc(String superJc);
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:getLxdmBySuperDmAndDmVal</b>
	 * <dd>方法作用：根据代码�?�和父级代码查询类型代码
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param superDm		父级代码 
	 * <dd>@param dmmc			代码名称
	 * <dd>@return				LxdmEntity
	 * </dl>
	 */
	public LxdmEntity getLxdmBySuperDmAndDmmc(String superDm,String dmmc);
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:getDmByCkbCkzd</b>
	 * <dd>方法作用：根据参考表的参考字�?,得到对应�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param wbzd					外部字段
	 * <dd>@param wbz					外部�?
	 * <dd>@param nbzd					内部字段
	 * <dd>@param ckb					参�?�表
	 * <dd>@return						String
	 * </dl>
	 */
	public String getDmByCkbCkzd(String wbzd,String wbz,String nbzd,String ckb);

	/**
	 * 
	 * <dl>
	 * <b>方法�?:getLxmcByLxdm</b>
	 * <dd>方法作用：根据类型代码查询类型代码名�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param lxdm
	 * <dd>@return
	 * </dl>
	 */
	public String getDmmcByLxdm(String lxdm);
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:getAllDmmk</b>
	 * <dd>方法作用：查询所有代码模块名�?
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@return
	 * </dl>
	 */
	public List<String> getAllDmmk();
	
	public List<LxdmEntity> getLxdmByMkmc(String mkmc);

	public List<LxdmEntity> getLxdmBySuperDmCond(Page page);

	public int getLxdmCountByPage(List<ConditionBean> cons);
	
	public int updateLxdm(List<LxdmEntity> lxdm);
	
}
