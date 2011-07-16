package com.wfms.dao.system;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.model.system.MacEntity;
import com.wfms.model.system.MemberGenInfo;
import com.wfms.model.system.MemberRight;

public interface IYhglDao {
	/**
	 * 根据用户登录帐号得到用户信息
	 * @param dlzh
	 * @return 用户信息
	 */
	public MemberGenInfo getYhByDlzh(String dlzh);
	
	/**
	 * 根据用户编号得到用户信息
	 * @param id
	 * @return 用户信息
	 */
	public MemberGenInfo getYhById(String id);
	
	public List<MacEntity> readLocalMac();
	public int updateMac(MacEntity me);
	public int addMac(MacEntity me);
	
	/**
	 * 修改用户信息
	 * @param yh
	 * @return 影响行数 
	 */
	public int updateYh(MemberGenInfo yh);
	
	public List<MemberGenInfo> getAllYh(Page page);
	
	public String addYh(MemberGenInfo yh);
	
	public int delYh(String id);
	
	public int getCount(Page page);
	
	public List<MemberGenInfo> getYhForZw(String zwId);
	
	public List<MemberGenInfo> getYhForZwNot(String zwId);
	
	public MemberGenInfo getYhByRyId(int ryId);
	
	/**
	 * 
	 * <dl>
	 * <b>方法�?:getYhQxs</b>
	 * <dd>方法作用：根据用户编号查询该用户的所有可用权限和权限类型
	 * <dd>重写备注�?(这里描述重写原因、结果或备注)
	 * <dd>适用条件�?(这里描述这个方法的�?�用条件 �? 可�??)
	 * <dd>执行流程�?(这里描述这个方法的执行流�? �? 可�??)
	 * <dd>适用方法�?(这里描述这个方法的使用方�? �? 可�??)
	 * <dd>注意事项�?(这里描述这个方法的注意事�? �? 可�??) 
	 * <dd>@param yhId
	 * <dd>@return
	 * </dl>
	 */
	public List<Integer[]> getYhQxs(String yhId);
	
	public List<MemberGenInfo> getAllYh();
	
	public int getDepMemsCount(String depid);
	
	public List<MemberGenInfo> getMemberByDep(Page page,String depid);
	
	public int getRolMemsCount(String rolid);
	
	public List<MemberGenInfo> getMemberByRol(Page page,String rolid);
	
	public int batchAddYhqx(List<MemberRight> memberRightList);
	
	public int existsMemberRight(MemberRight memberRight);
	
	public int batchUpdateMemberRight(List<MemberRight> memberRightList);
	
	public MemberRight getYhqx(String memid,int rightId);
	
	public int delMemberRight(MemberRight memberRight);
	
	public int getRightAndType (String memid,String gnmkid);
	
	public boolean getYhzp(String userid, String path, String type);
}
