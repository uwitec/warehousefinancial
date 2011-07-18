package com.wfms.common.system.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wfms.common.dao.GeneralService;
import com.wfms.common.system.entity.ModuleGenInfo;
import com.wfms.common.web.BaseController;

@Controller
@Lazy(true)
@RequestMapping("/system/module_manage/*")
public class ModuleController extends BaseController<ModuleGenInfo> {
	@Override
	@Autowired
	public void setBaseService(@Qualifier("moduleService")GeneralService<ModuleGenInfo> baseService) {
		super.setBaseService(baseService);
	}
}
