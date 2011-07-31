package com.wfms.common.system.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import xuner.web.mvc.MvcUtil;

import com.wfms.common.attribute.TreeNode;
import com.wfms.common.dao.BaseService;
import com.wfms.common.system.entity.ModuleGenInfo;
import com.wfms.common.system.service.ModuleService;
import com.wfms.common.util.TreeUtil;
import com.wfms.common.web.BaseController;

@Controller
@Lazy(true)
@RequestMapping("/system/module_manage/*")
public class ModuleController extends BaseController<ModuleGenInfo> {
	@Override
	@Autowired
	public void setBaseService(@Qualifier("moduleService")
	BaseService<ModuleGenInfo> baseService) {
		super.setBaseService(baseService);
	}

	@RequestMapping("userModuleTree.do")
	public ModelAndView getModuleTreeByUser(HttpServletResponse response,HttpSession session) throws IOException {
		String userId = (String) session.getAttribute("userId");
		List<ModuleGenInfo> userModuleList = ((ModuleService) getEntityService())
				.loadUserModules(userId);
		List<TreeNode> treeList = TreeUtil.initTree("", userModuleList);
		/*for (int i = 0; i < userModuleList.size(); i++) {
			JSONObject jo = new JSONObject();
			ModuleGenInfo module = userModuleList.get(i);
			jo.put("id", module.getId());
			jo.put("text", module.getModulename());
			jo.put("url", module.getForwardpage());
			jo.put("leaf", ((ModuleService) getEntityService()).loadSubModules(
					module.getId()).size() == 0);
			jo.put("path", 0);
			jo.put("p_id", module.getParentid());
			ja.add(jo);
		}*/
		return MvcUtil.jsonArrayModelAndView(JSONArray.fromObject(treeList));
	}
}
