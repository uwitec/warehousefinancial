package com.wfms.common.system.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.wfms.common.dao.BaseService;
import com.wfms.common.system.entity.User;
import com.wfms.common.system.service.UserService;
import com.wfms.common.util.MvcUtil;
import com.wfms.common.web.BaseController;

@Controller
@Lazy(true)
@RequestMapping("/system/user_manage/*")
public class UserController extends BaseController<User> {
	@Override
	@Autowired
	public void setBaseService(
			@Qualifier("userService") BaseService<User> paramService) {
		this.baseService = paramService;
	}

	@RequestMapping("login.do")
	@ResponseBody
	public JSONObject login(HttpSession session,
			@RequestParam("loginId") String loginId,
			@RequestParam("loginPwd") String loginPwd,
			@RequestParam("verify") String verify) throws Exception {
		String sessionVerify = (String) session.getAttribute("validateCode");
		JSONObject jo = new JSONObject();
		if (sessionVerify == null) {
			sessionVerify = "";
		}
		logger.info("用户:" + loginId + ",正在尝试登录系统...");

		/*
		 * if(verify==null ||
		 * verify.equals("")||!verify.equalsIgnoreCase(sessionVerify)){
		 * jo.put("msg", "登录失败!请输入正确的验证码!"); jo.put("code", -300);
		 * jo.put("success", false); }else
		 */
		if (loginId == null || loginId.equals("")) {
			jo.put("msg", "登录失败!请输入正确的帐号!");
			jo.put("code", -400);
			jo.put("success", false);
		} else if (loginPwd == null || loginPwd.equals("")) {
			jo.put("msg", "登录失败!请输入正确的密码!");
			jo.put("code", -500);
			jo.put("success", false);
		} else {
			List<User> userList = ((UserService) getEntityService())
					.loadLoginUser(loginId, loginPwd);
			if (userList.size() > 0) {
				session.setAttribute("userName", userList.get(0).getUsername());
				jo.put("msg", "登录成功");
				jo.put("success", true);
				SessionManager.saveSession(userList.get(0));
			} else {
				jo.put("msg", "登录失败!请输入正确的账号,密码!");
				jo.put("success", false);
				logger.info("用户:" + loginPwd + ",登录系统失败!");
			}
		}
		return jo;
		// return MvcUtil.jsonObjectModelAndView(jo);
	}

	@RequestMapping("loginout.do")
	@JsonBackReference
	@JsonManagedReference
	public ModelAndView loginout(HttpSession session) {
		SessionManager.clearSession(session);
		JSONObject jo = new JSONObject();
		jo.put("success", true);
		logger.info("用户:" + session.getAttribute("userName") + ",退出系统!");
		return MvcUtil.jsonObjectModelAndView(jo);
	}

}
