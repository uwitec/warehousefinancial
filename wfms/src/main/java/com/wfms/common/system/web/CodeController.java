package com.wfms.common.system.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wfms.common.dao.BaseService;
import com.wfms.common.system.entity.Code;
import com.wfms.common.web.BaseController;



@Controller
@Lazy(true)
@RequestMapping("/system/code_manage/*")
public class CodeController extends BaseController<Code>{

	@Override
	@Autowired
	public void setBaseService(@Qualifier("codeService")BaseService<Code> paramService) {
		this.baseService = paramService;
	}
	
}
