package com.wfms.webapp.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import xuner.web.mvc.MvcUtil;

import com.wfms.common.entity.AjaxMsg;
import com.wfms.common.util.BeanConvort;
import com.wfms.common.util.DateUtil;
import com.wfms.common.util.JSONUtil;
import com.wfms.common.util.NumberUtil;
import com.wfms.common.util.QxUtil;
import com.wfms.common.util.StringUtil;
import com.wfms.dto.system.ZwDto;
import com.wfms.model.system.DepartGenInfo;
import com.wfms.model.system.MemberGenInfo;
import com.wfms.model.system.RoleGenInfo;
import com.wfms.model.system.RoleMember;
import com.wfms.service.rsgl.IBmglService;
import com.wfms.service.system.IYhZwglService;
import com.wfms.service.system.IYhglService;
import com.wfms.service.system.IZwglService;

@Controller
@Lazy(true)
@RequestMapping("/system/zwglAction.do")
public class ZwglAction{
	
	@Autowired
	private IYhglService yhService;
	@Autowired
	private IZwglService zwglService;
	@Autowired
	private IYhZwglService yhzwService;
	
	@RequestMapping(params="method=initZwgl")
	public String initZwgl(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		Object obj = request.getSession().getAttribute("gnmkList");
		if(obj == null){
			return "error";
		}
		int qxlx = QxUtil.getQx(id, obj);
		request.setAttribute("qxlx", qxlx);
		request.setAttribute("mkid", id);
		return "/system/zwgl_list";
	}
	
	//获取全部职务信息
	@RequestMapping(params="method=getAllZw")
	@ResponseBody
	public List<RoleGenInfo> getAllZw(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ZwDto zwDto=zwglService.getAllZw();
		List<RoleGenInfo> roleList = new ArrayList<RoleGenInfo>(zwDto.getZwList().size());
		for(RoleGenInfo role:zwDto.getZwList())
		{
			RoleGenInfo tempRole = new RoleGenInfo();
			tempRole.setName(role.getName());
			tempRole.setDepart(role.getDepart());
			tempRole.setCreateTime(role.getCreateTime());
			tempRole.setRolid(role.getRolid());
			tempRole.setSynopsis(role.getSynopsis());
			roleList.add(tempRole);
		}
		return roleList;
		/*JSONArray jay =JSONUtil.formatArray(roleList,true, new String[]
       {"parts","roleParts","roleMembers","members","member","parent","departs","roles"
			,"chargeRole","children","childNodes","company","roleParts"});*/
		//response.setCharacterEncoding("UTF-8");
		//return MvcUtil.jsonArrayModelAndView(jay);
		//return zwDto.getZwList();
	}
	
	//职务授权-获取单个职务信息
	@RequestMapping(params="method=getZw")
	public String getZw(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strId = request.getParameter("id");
		if(StringUtil.isNullOrEmpty(strId)){
			return "error";
		}
		ZwDto zwDto = zwglService.getZwById(strId);
		if(zwDto.getZw()==null){
			return "error";
		}
		request.setAttribute("zw", zwDto.getZw());
		return "/system/zwgl_sq";
	}
	
	//增加职务信息
	@RequestMapping(params="method=addZw")
	public ModelAndView addZw(HttpServletRequest request,
			HttpServletResponse response,
			RoleGenInfo zw)
			throws Exception {
		zw.setCreateTime(DateUtil.formatDate(new Date()));
		String row = zwglService.addZw(zw);
		AjaxMsg msg = new AjaxMsg();
		if(!StringUtil.isNullOrEmpty(row)){
			msg.setSuccess(true);
		}else{
			msg.setSuccess(false);
			msg.setMessage("添加失败,服务器繁忙,请稍后重试!");
		}
		JSONObject obj = JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	//删除职务信息
	@RequestMapping(params="method=delZw")
	public ModelAndView delZw(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("id")String rolid)
	throws Exception {
		if(StringUtil.isNullOrEmpty(rolid)){
			return new ModelAndView("error");
		}
		int row=zwglService.delZw(rolid);
		AjaxMsg msg=new AjaxMsg();
		if(row==-1)
		{
			msg.setSuccess(false);
			msg.setMessage("删除职务失败,该职务下存在用户信息!");
		}
		else if(row==-2)
		{
			msg.setSuccess(false);
			msg.setMessage("删除职务失败,该职务下存在角色信息!");
		}
		else if(row==0)
		{
			msg.setSuccess(false);
			msg.setMessage("删除职务失败!");
		}
		else
		{
			msg.setSuccess(true);
		}
		response.setCharacterEncoding("utf-8");
		JSONObject obj = JSONUtil.formatObject(msg);
		return MvcUtil.jsonObjectModelAndView(obj);
	}
	
	//批量修改职务
	@RequestMapping(params="method=updZw")
	public ModelAndView updZw(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String data = request.getParameter("data");
		data = java.net.URLDecoder.decode(data,"UTF-8"); 
		JSONArray ja = JSONArray.fromObject(data);
		
		List<RoleGenInfo> list = BeanConvort.boundToArray(RoleGenInfo.class, ja);
		int row = zwglService.updZw(list);
		AjaxMsg msg = new AjaxMsg();
		
		if(row < 0){
			msg.setSuccess(false);
			msg.setMessage("更新失败,服务器繁忙,请稍后重试!");
		}else{
			msg.setSuccess(true);
		}
		response.setCharacterEncoding("utf-8");
		JSONObject obj = JSONUtil.formatObject(msg);
		return MvcUtil.jsonObjectModelAndView(obj);
	}

	
	//读取用户没有的职务
	@RequestMapping(params="method=getYhwyZw")
	public ModelAndView getYhwyZw(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("id")String memid)
			throws Exception {
		if(StringUtil.isNullOrEmpty(memid)){
			return new ModelAndView("error");
		}
		List<RoleGenInfo> zwList=zwglService.getZwForYhNot(memid);
		JSONArray jay =JSONUtil.formatArray(zwList, new String[]{"parent","childNodes","objects","company","members","parts","roleParts"
				,"chargeRole","company","roles"});
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonArrayModelAndView(jay);
	}
	
	//添加用户职务（为用户指定职务，增加yhzwb信息）
	@RequestMapping(params="method=addYhzw")
	public ModelAndView addYhzw(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="yhId")String yhId,
			@RequestParam(value="zwId")String zwId)
			throws Exception {
		if(StringUtil.isNullOrEmpty(yhId) ||
				StringUtil.isNullOrEmpty(zwId)){
			return new ModelAndView("error");
		}
		MemberGenInfo yh=yhService.getYh(yhId).getYh();
		boolean containRole = false;
		boolean res = false;
		for(RoleGenInfo role:yh.getRoles())
		{
			if(role.getRolid().equals(zwId))
			{
				containRole = true;
				break;
			}
		}
		if(containRole)
		{
			res = true;
		}
		else
		{
			yh.getRoles().add(zwglService.getZwById(zwId).getZw());
			res = yhService.updateYh(yh);
		}
		AjaxMsg msg=new AjaxMsg();
		if(res)
		{
			msg.setSuccess(true);
			msg.setMessage("增加用户职务成功!");
		}
		else
		{
			msg.setSuccess(false);
			msg.setMessage("增加用户职务失败!");
		}
		JSONObject jay =JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(jay);
	}
	
	//删除用户职务
	@RequestMapping(params="method=delYhzw")
	public ModelAndView delYhzw(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String strYhId = request.getParameter("yhId");
		String strZwId = request.getParameter("zwId");
		
		if(StringUtil.isNullOrEmpty(strYhId) || StringUtil.isNullOrEmpty(strZwId)){
			return new ModelAndView("error");
		}
		MemberGenInfo member = yhService.getYh(strYhId).getYh();
		member.getRoles().remove(zwglService.getZwById(strZwId));
		//RoleMember yhzwEntity=new RoleMember();
		//yhzwEntity.setMember(new MemberGenInfo(strYhId));
		//yhzwEntity.setRole(new RoleGenInfo(strZwId));
		//int res=yhzwService.delYhZw(yhzwEntity);
		boolean res = yhService.updateYh(member);
		AjaxMsg msg=new AjaxMsg();
		if(res)
		{
			msg.setSuccess(true);
		}
		else
		{
			msg.setSuccess(false);
			msg.setMessage("删除用户的职务信息失败!");
		}
		JSONObject jay =JSONUtil.formatObject(msg);
		response.setCharacterEncoding("UTF-8");
		return MvcUtil.jsonObjectModelAndView(jay);
	}
	
	@RequestMapping(params="method=getRolByDepart")
	@ResponseBody
	public List<RoleGenInfo> getRoleByDepart(@RequestParam("depid")String depid)
	{
		List<RoleGenInfo> roles = zwglService.loadRoleByDepart(depid);
		return roles;
	}
	
}
