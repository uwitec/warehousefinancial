package com.wfms.dto.system;

import java.util.ArrayList;
import java.util.List;

import com.wfms.common.entity.Page;
import com.wfms.model.system.MemberGenInfo;
import com.wfms.model.system.MemberPart;
import com.wfms.model.system.MemberRight;
import com.wfms.model.system.RoleMember;

public class YhDto {
	private MemberGenInfo yh;
	private List<RoleMember> yhzwList = new ArrayList<RoleMember>();
	private List<MemberGenInfo> yhList=new ArrayList<MemberGenInfo>();
	private List<MemberPart> yhjsList = new ArrayList<MemberPart>(0);
	private Page page = new Page();
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public MemberGenInfo getYh() {
		return yh;
	}

	public void setYh(MemberGenInfo yh) {
		this.yh = yh;
	}
	public List<MemberPart> getYhjsList() {
		return yhjsList;
	}
	public void setYhjsList(List<MemberPart> yhjsList) {
		this.yhjsList = yhjsList;
	}
	public List<RoleMember> getYhzwList() {
		return yhzwList;
	}
	public void setYhzwList(List<RoleMember> yhzwList) {
		this.yhzwList = yhzwList;
	}
	public List<MemberGenInfo> getYhList() {
		return yhList;
	}
	public void setYhList(List<MemberGenInfo> yhList) {
		this.yhList = yhList;
	}
	
}
