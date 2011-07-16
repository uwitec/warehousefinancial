package com.wfms.service.system.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfms.common.entity.Page;
import com.wfms.common.util.CollectionUtil;
import com.wfms.common.util.DateUtil;
import com.wfms.common.util.EncoderUtil;
import com.wfms.common.util.MD5;
import com.wfms.common.util.StringUtil;
import com.wfms.constant.SystemConstant;
import com.wfms.dao.system.IGnmkDao;
import com.wfms.dao.system.IJsglDao;
import com.wfms.dao.system.IYhglDao;
import com.wfms.dto.system.GnmkDto;
import com.wfms.dto.system.YhDto;
import com.wfms.model.system.MacEntity;
import com.wfms.model.system.MemberGenInfo;
import com.wfms.model.system.MemberPart;
import com.wfms.model.system.MemberRight;
import com.wfms.model.system.ModuleGenInfo;
import com.wfms.model.system.PartGenInfo;
import com.wfms.model.system.PartRight;
import com.wfms.model.system.RoleGenInfo;
import com.wfms.model.system.RoleMember;
import com.wfms.service.system.IYhglService;

@Service
public class YhglServiceImpl implements IYhglService {
	private String syts = "20";

	@Autowired
	private IYhglDao yhglDao;
	@Autowired
	private IJsglDao jsglDao;
	@Autowired
	private IGnmkDao gnmkDao;

	public void setGnmkDao(IGnmkDao gnmkDao) {
		this.gnmkDao = gnmkDao;
	}

	public void setJsglDao(IJsglDao jsglDao) {
		this.jsglDao = jsglDao;
	}

	public void setYhglDao(IYhglDao yhglDao) {
		this.yhglDao = yhglDao;
	}

	/**
	 * 用户登录验证,如果正确则返回用户ID或者学生学号.失败则返回-1
	 */
	public String yhdl(String yhm, String mm, String yhz) {
		// 老师权限登录
		String yhId = "-1";
		if (SystemConstant.YHZ_TEACHER.equalsIgnoreCase(yhz)) {
			MemberGenInfo yh = yhglDao.getYhByDlzh(yhm);
			if (yh == null) {
				yhId = "-1";
			} else {
				String md5Pass = null;
				try {
					md5Pass = EncoderUtil.EncoderByMd5(mm);
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if (md5Pass != null && yh.getPassword().equals(md5Pass)) {
					// 状态未开启
					if ("true".equals(yh.getDeniedlogin())) {
						return "0";
					}
					
//					if(this.dbMac(this.getMac())){
						yhId = String.valueOf(yh.getId());
//					}else{
//						yhId = "-2";
//					}
				} else {
					yhId = "-1";
				}
			}
		}
		return yhId;
	}
	
	private String getMac() {
		String restr = "";
		try {
			String line;
			Process p = Runtime.getRuntime().exec("cmd.exe /c ipconfig /all");
			BufferedReader bd = new BufferedReader(new InputStreamReader(p
					.getInputStream()));
			while ((line = bd.readLine()) != null) {
				if(line.indexOf("Physical Address") != -1){
					if (line.indexOf(":") != -1) {
						restr = line.substring(line.indexOf(":") + 2);
						break; // 找到mac,推出循环
					}
				}
			}
			p.waitFor();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return restr;
	}
	
	private boolean dbMac(String mac){
		boolean flag = false;
		List<MacEntity> l = yhglDao.readLocalMac();
		MacEntity me = new MacEntity();
		String stime = DateUtil.getCurrentDateString();
		if(l.size() < 1){//没有使用过
			me.setDlcs(syts);
			me.setJssj((this.getEndDate(DateUtil.getCurrentDateString(), syts))[1]);
			me.setMac(MD5.getMd5Capital(mac+"0"+stime));//第一次试用,试用20天
			me.setZdcs(MD5.getMd5Capital("0"));//0试用，1正式使用
			me.setKssj(stime);
			me.setLmac(MD5.getMd5Capital(mac+"0"));
			if(yhglDao.addMac(me) > 0){
				flag = true;
			}
		}else{
			me = l.get(0);
			String m0 = MD5.getMd5Capital(mac+"0"+me.getKssj());
			String m1 = MD5.getMd5Capital(mac+"1"+me.getKssj());
			String de = me.getJssj().substring(0,4)+me.getJssj().substring(5,7)
			+me.getJssj().substring(8,10);
			if(me.getLmac().equals(MD5.getMd5Capital(mac+"1")) && me.getZdcs().equals(MD5.getMd5Capital("1"))){
				if(me.getMac().equals(m0) || me.getMac().equals(m1)){
					flag = true;
				}else{
					if(Integer.parseInt(de) >= Integer.parseInt(this.getEndDate(me.getKssj(), me.getDlcs())[0])){
						flag = true;
					}else{
						flag = false;
					}
				}
			}else if(me.getLmac().length() != 32 || me.getMac().length() != 32
					|| (!me.getZdcs().equals(MD5.getMd5Capital("0")) && !me.getZdcs().equals(MD5.getMd5Capital("1")))
					|| !de.equals((this.getEndDate(me.getKssj(), syts))[0])){
				flag = false;//随意更改数据
			}else{
				if(!me.getMac().equals(MD5.getMd5Capital(mac+"0"+me.getKssj()))
					&& me.getLmac().equals(MD5.getMd5Capital(mac+"0"))){
					me.setDlcs(syts);
					me.setJssj((this.getEndDate(DateUtil.getCurrentDateString(), syts))[1]);
					me.setMac(MD5.getMd5Capital(mac+"0"+stime));//第一次试用,试用20天
					me.setZdcs(MD5.getMd5Capital("0"));//0试用，1正式使用
					me.setKssj(stime);
					me.setLmac(MD5.getMd5Capital(mac+"0"));
					yhglDao.updateMac(me);
					flag = true;
				}else if(me.getMac().equals(MD5.getMd5Capital(mac+"0"+me.getKssj()))){//试用正常登录
					if(Integer.parseInt(de) >= Integer.parseInt(this.getEndDate(me.getKssj(), me.getDlcs())[0])){
						flag = true;
					}else{
						flag = false;
					}
				}else if(me.getLmac().equals(MD5.getMd5Capital(mac+"0"))){
					if(Integer.parseInt(de) >= Integer.parseInt(this.getEndDate(me.getKssj(), me.getDlcs())[0])){
						flag = true;
					}else{
						flag = false;
					}
				}
			}
		}
		return flag;
	}
	
	private String[] getEndDate(String date, String syts){
		String[] rep = new String[]{"20110101",DateUtil.getCurrentDateString()};
		String[] mdate = new String[]{"31","29","31","30","31","30","31","31","30","31","30","31"};
		
		String y = date.substring(0,4);
		String m = date.substring(5,7);
		String d = date.substring(8,10);
		
		int ed = (Integer.parseInt(d)+Integer.parseInt(syts));
		int dd = (ed - Integer.parseInt(mdate[Integer.parseInt(m)-1]));
		if(dd > 0){
			d = (dd+"").length() == 1 ? "0"+dd : dd+"";
			if(Integer.parseInt(m)+1 > 12){
				m = "01";
				y = (Integer.parseInt(y)+1)+"";
			}else{
				m = (Integer.parseInt(m)+1)+"";
				m = m.length() == 1 ? "0"+m : m;
			}
		}else{
			d = ed+"";
		}
		rep[0] = y+m+d;
		rep[1] = y+"-"+m+"-"+d;
		return rep;
	}
	
	public boolean updateJxcmac(String name){
		boolean flag = false;
		String mac = this.getMac();
		MacEntity me = new MacEntity();
		String stime = DateUtil.getCurrentDateString();
		if(name.substring(7, 15).equals(stime.replaceAll("-", ""))){
			List<MacEntity> l = yhglDao.readLocalMac();
			if(l.size() > 0){
				me = l.get(0);
				me.setDlcs(syts);
				me.setJssj(stime);
				me.setMac(MD5.getMd5Capital(mac+"1"+stime));//第一次试用,试用20天
				me.setZdcs(MD5.getMd5Capital("1"));//0试用，1正式使用
				me.setKssj(stime);
				me.setLmac(MD5.getMd5Capital(mac+"1"));
				yhglDao.updateMac(me);
				flag = true;
			}else{
				me.setDlcs(syts);
				me.setJssj(stime);
				me.setMac(MD5.getMd5Capital(mac+"1"+stime));//第一次试用,试用20天
				me.setZdcs(MD5.getMd5Capital("1"));//0试用，1正式使用
				me.setKssj(stime);
				me.setLmac(MD5.getMd5Capital(mac+"1"));
				yhglDao.addMac(me);
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 修改用户登录信息,获取功能模块信息
	 */
	public GnmkDto updYhdl(String yhId, String yhz) {
		if (StringUtil.isNullOrEmpty(yhId)) {
			return null;
		}
		if (SystemConstant.YHZ_TEACHER.equals(yhz)) {
			MemberGenInfo yh = yhglDao.getYhById(yhId);
			// 验证用户是否有效
			if (yh == null) {
				return null;
			}
			/**
			 * 更改用户登录信息
			 */
			// 设置上次登录时间
			yh.setScdlsj(yh.getDlsj());
			// 设置登录次数
			yh.setDlcs(yh.getDlcs() + 1);
			// 设置本次登录时间
			yh.setDlsj(DateUtil.formatDateTime(new Date()));
			// 修改用户登录信息
			yhglDao.updateYh(yh);
			return getYhGnmk(yh.getMemid());
		} else if (SystemConstant.YHZ_STUDENT.equals(yhz)) {
			return getXsGnmk(yhId);
		}
		// 登录用户组不存在
		return null;
	}

	/**
	 * 读取用户功能模块
	 */
	/*
	 * public GnmkDto getYhGnmk(int yhId){
	 *//**
		 * 获得用户功能模块
		 */
	/*
	 * MemberGenInfo yh = yhglDao.getYhById(yhId); if(yh == null ){ return null; }
	 * //获取数据传输DTO GnmkDto dto = new GnmkDto();
	 * 
	 * //实例化功能模块集合 List<ModuleGenInfo> gnmkList = new ArrayList<ModuleGenInfo>();
	 * //获取职务所拥有的角色 Set yhzwList = yh.getYhzws();
	 * 
	 * //记录用户所有角色集合 Set jsList = new HashSet();
	 * 
	 * //设置全局可添加标记 boolean isAdd = true;
	 * 
	 * //获取用户拥有职务的全部角色 for(Iterator iter = yhzwList.iterator();iter.hasNext();){
	 * YhzwEntity yhzw = (YhzwEntity)iter.next(); Set zwjsList =
	 * yhzw.getZw().getZwjss(); for(Iterator cIter =
	 * zwjsList.iterator();cIter.hasNext();){ isAdd = true; ZwjsEntity zwjs
	 * =(ZwjsEntity)cIter.next();
	 * 
	 * //避免重复 for(Iterator jsIter = jsList.iterator();jsIter.hasNext();){
	 * JsEntity js = (JsEntity)jsIter.next(); if(js.getId() ==
	 * zwjs.getJs().getId()){ isAdd = false; break; } } if(isAdd){
	 * jsList.add(zwjs.getJs()); } } }
	 * 
	 * //获取用户直接拥有的角色信息 Set yhjsList = yh.getYhjss(); for(Iterator iter =
	 * yhjsList.iterator();iter.hasNext();){ YhjsEntity yhjs =
	 * (YhjsEntity)iter.next(); isAdd = true; //避免重复 for(Iterator jsIter =
	 * jsList.iterator();jsIter.hasNext();){ JsEntity js =
	 * (JsEntity)jsIter.next(); if(js.getId() == yhjs.getJs().getId()){ isAdd =
	 * false; break; } } if(isAdd){ jsList.add(yhjs.getJs()); } }
	 * 
	 * //遍历用户所有角色 for(Iterator iter = jsList.iterator();iter.hasNext();){
	 * 
	 * //获得角色信息 JsEntity js = (JsEntity)iter.next(); //获得角色权限 Set jsqxList =
	 * js.getJsqxs();
	 * 
	 * //遍历角色所有权限 for(Iterator jqIter = jsqxList.iterator();jqIter.hasNext();){
	 * 
	 * //获取角色权限信息 PartRight jsqx = (PartRight)jqIter.next(); //实例化功能模块
	 * ModuleGenInfo gnmk; //获取权限对应功能模块
	 * if(SystemConstant.QXZT_QY.equals(jsqx.getQx().getZt()) &&
	 * jsqx.getQxlx()!=0){ gnmk = jsqx.getQx().getGnmk(); //获得功能模块权限 int qx =
	 * jsqx.getQxlx(); //设置模块权限 gnmk.setQxlx(qx); //添加至功能模块集合
	 * gnmkList.add(gnmk); } } }
	 * 
	 *//**
		 * 处理重复功能模块
		 */
	/*
	 * 
	 * //初始化返回的功能模块 List<ModuleGenInfo> rsList = new ArrayList<ModuleGenInfo>();
	 * 
	 * 
	 * //遍历所有用户对应功能模块 for(int i=0;i<gnmkList.size();i++){ //标记为可添加 isAdd =
	 * true; //遍历所有返回的功能模块 for(int j=0;j<rsList.size();j++){
	 * if(SystemConstant.QXZT_JY.equals(gnmkList.get(i).getQx().getZt()) ){
	 * isAdd = false; break; } //如果有相同的则比较权限 if(gnmkList.get(i).getId() ==
	 * rsList.get(j).getId()){
	 * if(gnmkList.get(i).getQxlx()>rsList.get(j).getQxlx()){ //取较大的权限
	 * rsList.get(j).setQxlx(gnmkList.get(i).getQxlx()); } //标记不可添加 isAdd =
	 * false; break; } } //根据标记添加功能模块 if(isAdd){ ModuleGenInfo gnmk =
	 * gnmkList.get(i); Hibernate.initialize(gnmk); rsList.add(gnmk); } }
	 * 
	 *//**
		 * 处理用户直接对应的功能模块
		 */
	/*
	 * 
	 * Set qxxzList = yh.getQxxzs();
	 * 
	 * //遍历所有直接对应功能模块 for(Iterator iter =qxxzList.iterator();iter.hasNext(); ){
	 * QxxzEntity qxxz =(QxxzEntity) iter.next(); //添加标记 isAdd = true; for(int
	 * i=0;i<gnmkList.size();i++){ if(gnmkList.get(i).getQx().getId() ==
	 * qxxz.getQx().getId()){ isAdd = false;
	 * gnmkList.get(i).setQxlx(qxxz.getQxlx()); break; } } if(isAdd){
	 * ModuleGenInfo gnmk = qxxz.getQx().getGnmk();
	 * if(SystemConstant.QXZT_QY.equals(qxxz.getQx().getZt()) &&
	 * qxxz.getQxlx()!=0){ gnmk.setQxlx(qxxz.getQxlx());
	 * Hibernate.initialize(gnmk); gnmkList.add(gnmk); } } }
	 * dto.setGnmkList(rsList); return dto; }
	 */

	/**
	 * 
	 * <dl>
	 * <b>方法名:getXsGnmk</b>
	 * <dd>方法作用：查询学生功能模块
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	 * <dd>
	 * 
	 * @param yhId
	 *            <dd>
	 * @return
	 *            </dl>
	 */
	public GnmkDto getXsGnmk(String xh) {
		if (StringUtil.isNullOrEmpty(xh)) {
			return null;
		}
		// 获取数据传输DTO
		GnmkDto dto = null;

		// 实例化功能模块集合
		List<ModuleGenInfo> gnmkList = new ArrayList<ModuleGenInfo>();

		PartGenInfo js = jsglDao.getJsByJsmc(SystemConstant.XSJS_JSMC);
		// 系统中不存在学生角色
		if (js == null) {
			return dto;
		}
		// 查询学生角色所有权限
		List jsqxList = js.getPartRights();

		// 遍历学生角色所有权限
		for (Iterator jqIter = jsqxList.iterator(); jqIter.hasNext();) {

			// 获取学生角色权限信息
			PartRight jsqx = (PartRight) jqIter.next();
			// 实例化功能模块
			ModuleGenInfo gnmk;
			// 获取权限对应功能模块
			if (SystemConstant.QXZT_QY.equals(jsqx.getRight().getRightStatus())
					&& jsqx.getRightType() != 0) {
				gnmk = jsqx.getRight().getModule();
				// 获得功能模块权限
				int qx = jsqx.getRightType();
				// 设置模块权限
				gnmk.setRightType(qx);
				// 添加至功能模块集合
				gnmkList.add(gnmk);
			}
		}
		dto = new GnmkDto();
		dto.setGnmkList(gnmkList);
		return dto;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param yhId
	 * @return 用户Dto
	 */
	public YhDto getYh(String yhId) {
		YhDto dto = new YhDto();
		MemberGenInfo yh = yhglDao.getYhById(yhId);
		dto.setYh(yh);
		return dto;
	}

	public YhDto getAllYh(Page page) {
		YhDto dto = new YhDto();
		List<MemberGenInfo> list = yhglDao.getAllYh(page);
		int count = yhglDao.getCount(page);
		page.setTotalCount(count);
		page.setDataList(list);
		dto.setPage(page);
		return dto;
	}

	public String addYh(MemberGenInfo yh) {
		return yhglDao.addYh(yh);
	}

	public int updYh(List<MemberGenInfo> list) {
		int i = 0;
		for (; i < list.size(); i++) {
			try {
				list.get(i).setPassword(EncoderUtil.EncoderByMd5(list.get(i).getPassword()));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			yhglDao.updateYh(list.get(i));
		}
		return i;
	}

	public int delYh(String id) {
		MemberGenInfo yh = yhglDao.getYhById(id);
		if (yh.getMemberRights() != null && yh.getMemberRights().size() != 0) {
			return -1;
		}
		if (yh.getMemberParts() != null && yh.getMemberParts().size() != 0) {
			return -2;
		}
		if (yh.getRoles() != null && yh.getRoles().size() != 0) {
			return -3;
		}
		return yhglDao.delYh(id);
	}

	public YhDto getYhzw(String yhId) {
		YhDto dto = new YhDto();
		List<RoleMember> rs = new ArrayList<RoleMember>();
		MemberGenInfo yh = yhglDao.getYhById(yhId);
		List<RoleGenInfo> yhzwList = yh.getRoles();
		if (yhzwList != null) {
			for (Iterator<RoleGenInfo> iter = yhzwList.iterator(); iter.hasNext();) {
				RoleGenInfo yhzw =  iter.next();
				RoleMember roleMember = new RoleMember();
				roleMember.setRole(yhzw);
				roleMember.setMember(yh);
				rs.add(roleMember);
			}
		}
		dto.setYhzwList(rs);
		return dto;
	}

	public YhDto getYhjs(String yhId) {
		YhDto dto = new YhDto();
		List<MemberPart> rs = new ArrayList<MemberPart>();
		MemberGenInfo yh = yhglDao.getYhById(yhId);
		List<PartGenInfo> partList = new ArrayList<PartGenInfo>();
		List<RoleGenInfo> roles = yh.getRoles();
		
		for(RoleGenInfo role:roles)
		{
			partList.addAll(role.getParts());
		}
		CollectionUtil.removeDuplicateWithOrder(partList);
		for (Iterator<PartGenInfo> iter = partList.iterator(); iter.hasNext();) {
			MemberPart yhjs = new MemberPart();
			yhjs.setPart(iter.next());
			rs.add(yhjs);
		}
		dto.setYhjsList(rs);
		return dto;
	}

	public YhDto getYhForZw(String zwId) {
		YhDto yhDto = new YhDto();
		List<MemberGenInfo> yhList = yhglDao.getYhForZw(zwId);
		yhDto.setYhList(yhList);
		return yhDto;
	}

	public YhDto getYhForZwNot(String zwId) {
		YhDto yhDto = new YhDto();
		List<MemberGenInfo> yhList = yhglDao.getYhForZwNot(zwId);
		yhDto.setYhList(yhList);
		return yhDto;
	}

	/**
	 * 读取用户功能模块
	 */
	public GnmkDto getYhGnmk(String yhId) {
		/**
		 * 获得用户功能模块
		 */
		if (yhId == null || "".equals(yhId)) {
			return null;
		}
		// 获取数据传输DTO
		GnmkDto dto = new GnmkDto();

		// 实例化功能模块集合
		List<Integer[]> qxList = yhglDao.getYhQxs(yhId);
		int[] qxIds = new int[qxList.size()];

		Map<Integer, Integer> qxLxMap = new HashMap<Integer, Integer>(qxIds.length);
		for (int i = 0; i < qxList.size(); i++) {
			qxIds[i] = qxList.get(i)[0];
			qxLxMap.put(qxIds[i], qxList.get(i)[1]);
		}
		if(qxIds==null || qxIds.length==0)
		{
			qxIds = new int[]{0};
		}
		List<ModuleGenInfo> list = gnmkDao.getGnmkByQxIds(qxIds);
		for (int j = 0; j < list.size(); j++) {
			if (list.get(j) != null) {
				list.get(j).setRightType(qxLxMap.get(list.get(j).getRight().getRightId()));
			}
			// ModuleGenInfo gnmk = list.get(j);
			// gnmk.setRightType(qxLxMap.get(gnmk.getRight().getRightId()));
		}
		dto.setGnmkList(list);
		return dto;
	}

	public YhDto getAllYh() {
		List<MemberGenInfo> list = yhglDao.getAllYh();
		YhDto dto = new YhDto();
		dto.setYhList(list);
		return dto;
	}

	public void loadMemberByDep(Page page, String depid) {
		page.setTotalCount(yhglDao.getDepMemsCount(depid));
		page.setDataList(yhglDao.getMemberByDep(page, depid));
	}

	public void loadMemberByRol(Page page, String rolid) {
		page.setTotalCount(yhglDao.getRolMemsCount(rolid));
		page.setDataList(yhglDao.getMemberByRol(page, rolid));
	}

	public String updateYhmm(String dlm, String oldmm, String newmm) {
		String re = "";
		String md5Pass = null;
		String md5Nnew = null;
		try {
			md5Pass = EncoderUtil.EncoderByMd5(oldmm);
			md5Nnew = EncoderUtil.EncoderByMd5(newmm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		MemberGenInfo yh = yhglDao.getYhByDlzh(dlm);
		if (md5Pass != null && md5Nnew != null
				&& yh.getPassword().equals(md5Pass)) {
			yh.setPassword(md5Nnew);
			if (yhglDao.updateYh(yh) > 0) {
				re = "密码修改成功！";
			}
		} else {
			re = "原始密码错误！";
		}
		return re;
	}

	public boolean addYhqx(String memid, String gnmkIds) {
		List<MemberRight> memberRightList = new ArrayList<MemberRight>();
		List<MemberRight> updateMemberList = new ArrayList<MemberRight>();
		String[] gnmkIdAry = gnmkIds.split(";");
		for (String gnmkId : gnmkIdAry) {
			ModuleGenInfo gnmk = gnmkDao.getGnmk(Integer.valueOf(gnmkId));
			if(gnmk.getRight()==null)
			{
				System.out.println("gnmkid==="+gnmkId);
			}
			MemberRight yhqx = new MemberRight();
			MemberGenInfo member = new MemberGenInfo();
			member.setMemid(memid);
			yhqx.setMember(member);
			yhqx.setRight(gnmk.getRight());
			yhqx.setRightStatus("1");
			yhqx.setRightType(2);
			Integer yhqxId = yhglDao.existsMemberRight(yhqx);
			if (yhqxId != null && yhqxId > 0) {
				yhqx.setId(yhqxId);
				updateMemberList.add(yhqx);
			} else {
				memberRightList.add(yhqx);
			}
		}
		int upCount = 0;
		if (updateMemberList != null && updateMemberList.size() > 0) {
			upCount = yhglDao.batchUpdateMemberRight(updateMemberList);
		}

		int addCount = 0;
		if (memberRightList != null && memberRightList.size() > 0) {
			addCount = yhglDao.batchAddYhqx(memberRightList);
		}
		return (upCount + addCount) > 0;
	}

	public boolean delYhqx(String memid, String gnmkIds) {
		String[] gnmkAry = gnmkIds.split(";");
		int res = 0;
		List<MemberRight> delMemberRight = new ArrayList<MemberRight>();
		for (String gnmkId : gnmkAry) {
			ModuleGenInfo gnmk = gnmkDao.getGnmk(Integer.valueOf(gnmkId));
			MemberRight yhqx = yhglDao.getYhqx(memid, gnmk.getRight().getRightId());
			if (yhqx != null) {
				res += yhglDao.delMemberRight(yhqx);
			} else {
				int rightType = yhglDao.getRightAndType(memid, gnmkId);
				yhqx = new MemberRight();
				MemberGenInfo member = new MemberGenInfo();
				member.setMemid(memid);
				yhqx.setMember(member);
				yhqx.setRight(gnmk.getRight());
				yhqx.setRightStatus("0");
				yhqx.setRightType(rightType);
				delMemberRight.add(yhqx);
			}
		}
		if (delMemberRight.size() > 0) {
			res += yhglDao.batchAddYhqx(delMemberRight);
		}
		return res > 0;
	}
	
	public boolean getYhzp(String userid, String path, String type){
		return yhglDao.getYhzp(userid, path, type);
	}

	public boolean updateYh(MemberGenInfo member) {
		int fecthCount =  yhglDao.updateYh(member);
		return fecthCount>0;
	}
}
