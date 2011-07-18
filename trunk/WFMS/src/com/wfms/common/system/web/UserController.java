package com.wfms.common.system.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wfms.common.dao.BaseService;
import com.wfms.common.system.entity.User;
import com.wfms.common.web.BaseController;

@Controller
@Lazy(true)
@RequestMapping("/system/user_manage/*")
public class UserController extends BaseController<User> {
	@Override
	@Autowired
	public void setBaseService(@Qualifier("userService")
			BaseService<User> baseService) {
		super.setBaseService(baseService);
	}

}
