package com.wfms.common.function.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import xuner.web.mvc.MvcUtil;

import com.wfms.common.attribute.BaseTree;
import com.wfms.common.attribute.Tree;
import com.wfms.common.util.JSONUtil;
import com.wfms.common.util.StringUtil;
import com.wfms.common.util.TreeUtil;

@Controller
@RequestMapping("/common/organize_manage/*")
public class OrganizeAction {
/*	
	@Autowired
	private IYhglService yhglService;
	@Autowired
	private IDepartService departService;
	
	@RequestMapping(params="method=rySelector")
	public String forwardRySelector(){
		return "/common/function/rySelector";
	}
	
	@RequestMapping(params="method=depSelector")
	public String forwardDepSelector(){
		return "/common/function/departSelector";
	}
	
	@RequestMapping(params="method=stuSelector")
	public String forwardStuSelector(){
		return "/common/function/studentSelector";
	}
	
	@RequestMapping(params="method=getAllOrganize")
	public void getAllOrganize(
			@RequestParam(value="dispRole",required=false,defaultValue="false")
			boolean dispRole,HttpServletResponse response,@RequestParam(value="depth",required=false,defaultValue="2")int depth) throws IOException{
		List allDepartList = departService.loadAllDepartment();
		List<Tree> rootDepartList = new ArrayList<Tree>();
		for (int i = 0; i < allDepartList.size(); i++) {
			Tree departTree = (Tree) allDepartList.get(i);
			if (departTree.getParent() == null) {
				rootDepartList.add(departTree);
			}
		}
		StringBuffer sbDepart = new StringBuffer();
		for(Tree rootDepart:rootDepartList) {
			TreeUtil.generateTreeBuffer(sbDepart,true,dispRole,(List<Tree>)allDepartList, (Tree)rootDepart,depth);
		}
		String departJsonStr = TreeUtil.generateJsonStyle(new String(sbDepart));
		response.setCharacterEncoding("utf-8");
		response.getWriter().println(departJsonStr);
	}
	
	*//**
	 * 
	 * <dl>
	 * <b>方法名:getAllDeparts</b>
	 * <dd>方法作用：查询所有部门
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选) 
	 * <dd>@param dispRole
	 * <dd>@param response
	 * <dd>@throws IOException
	 * </dl>
	 *//*
	@RequestMapping(params="method=getAllDeparts")
	public void getAllDeparts(HttpServletResponse response) throws IOException{
		List allDepartList = departService.loadAllDepartment();
		List<BaseTree> rootDepartList = new ArrayList<BaseTree>();
		for(int i=0;i< allDepartList.size();i++)
		{
			BaseTree departTree =(BaseTree)allDepartList.get(i);
			if(departTree.getParent()==null)
			{
				departTree.setCheckBox(true);
				rootDepartList.add(departTree);
			}
		}
		StringBuffer sbDepart = new StringBuffer();
		for(Tree rootDepart:rootDepartList)
		{
			TreeUtil.generateTreeBuffer(sbDepart,true,false,(List<Tree>)allDepartList, (Tree)rootDepart);
		}
		String departJsonStr = TreeUtil.generateJsonStyle(new String(sbDepart));
		response.setCharacterEncoding("utf-8");
		response.getWriter().println(departJsonStr);
	}
	
	@RequestMapping(params="method=getDepartMembers")
	public ModelAndView getDepartMembers(@RequestParam(value="id",required=false,defaultValue="0")String id,Page page) throws IOException{
		if(!StringUtil.isNullOrEmpty(id))
		{	
			if(id.indexOf("DEP")!=-1)
			{
				yhglService.loadMemberByDep(page, id);
			}
			else if(id.indexOf("ROL")!=-1)
			{
				yhglService.loadMemberByRol(page, id);
			}
			else if("0".equals(id))
			{
				yhglService.getAllYh(page);
			}
		}
		JSONObject jsonObj = JSONUtil.formatObject(page,new String[]{
				"roleParts","roleRights","parts","company","parent","parts","roleParts",
				"memberRights","members","memberParts","roleMembers","ry","roles","parent","children"});
		return MvcUtil.jsonObjectModelAndView(jsonObj);
	}*/
}
