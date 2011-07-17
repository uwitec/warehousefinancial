package com.wfms.common.system.web;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wfms.common.system.entity.SysConfig;
import com.wfms.common.web.BaseController;

@Controller
@Lazy(true)
@RequestMapping("/system/sysconfig_manage/*")
public class SysConfigController extends BaseController<SysConfig>{
	
}
