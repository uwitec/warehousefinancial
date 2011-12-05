package com.wfms.common.system.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wfms.common.attribute.TreeNode;
import com.wfms.common.dao.BaseService;
import com.wfms.common.system.entity.ModuleGenInfo;
import com.wfms.common.system.service.ModuleService;
import com.wfms.common.util.MvcUtil;
import com.wfms.common.util.TreeUtil;
import com.wfms.common.web.BaseController;

@Controller
@Lazy(true)
@RequestMapping("/system/module_manage/*")
public class ModuleController extends BaseController<ModuleGenInfo> {
	@Override
	@Autowired
	public void setBaseService(
			@Qualifier("moduleService") BaseService<ModuleGenInfo> paramService) {
		this.baseService = paramService;
	}

	@RequestMapping("userModuleTree.do")
	public ModelAndView getModuleTreeByUser(HttpServletResponse response,
			HttpSession session) throws IOException {
		String userId = SessionManager.getUser().getId();
		List<ModuleGenInfo> userModuleList = ((ModuleService) getEntityService())
				.loadUserModules(userId);
		List<TreeNode> treeList = TreeUtil.initTree("", userModuleList);
		return MvcUtil.jsonArrayModelAndView(treeList);
	}

	@RequestMapping("childModules.do")
	public ModelAndView getAllChildModules(
			HttpServletResponse response,
			HttpSession session,
			@RequestParam(value = "id", required = false, defaultValue = "0") String id)
			throws IOException {
		List<ModuleGenInfo> subModules = ((ModuleService) getEntityService())
				.loadSubModules(id);
		List<TreeNode> treeList = TreeUtil.initTree("", subModules);
		return MvcUtil.jsonArrayModelAndView(treeList);
	}

	@Override
	protected ModelAndView afterLoadAll(HttpServletRequest request,
			HttpServletResponse response, List<ModuleGenInfo> entityList) {
		List<TreeNode> treeList = TreeUtil.initTree("", entityList);
		return MvcUtil.jsonArrayModelAndView(treeList);
	}
}
