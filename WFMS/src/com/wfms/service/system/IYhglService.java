package com.wfms.service.system;

import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.dto.system.GnmkDto;
import com.wfms.dto.system.YhDto;
import com.wfms.model.system.MemberGenInfo;


public interface IYhglService {
	/**
	 * 用户登录,返回-1则登录失�?,返回0则用户被禁用
	 * @param yhm
	 * @param mm
	 * @param yhz
	 * @return
	 */
	public String yhdl(String yhm,String mm,String yhz);
	public boolean updateJxcmac(String name);
	/**
	 * 更改用户信息
	 * @param yhId
	 * @return
	 */
	public GnmkDto updYhdl(String yhId,String yhz);
	
	/**
	 * 获取用户信息
	 * @param yhId
	 * @return 用户Dto
	 */
	public GnmkDto getYhGnmk(String yhId);
	
	public YhDto getYh(String yhId);

	
	public YhDto getAllYh(Page page);
	
	public String addYh(MemberGenInfo yh);
	
	public int updYh(List<MemberGenInfo> list);
	
	public int delYh(String id);
	
	public YhDto getYhjs(String yhId);
	
	public YhDto getYhzw(String yhId);
	
	public YhDto getYhForZw(String zwId);

	public YhDto getYhForZwNot(String zwId);
	
	public YhDto getAllYh();
	
	public void loadMemberByDep(Page page,String depid);
	
	public void loadMemberByRol(Page page,String rolid);
	
	public String updateYhmm(String dlm, String oldmm, String newmm);
	
	public boolean addYhqx(String memid,String gnmkIds);
	
	public boolean delYhqx(String memid,String gnmkIds);
	
	public boolean getYhzp(String userid, String path, String type);
	
	public boolean updateYh(MemberGenInfo member);
}
