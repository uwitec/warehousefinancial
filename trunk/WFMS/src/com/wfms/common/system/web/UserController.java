package com.wfms.common.system.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wfms.common.dao.BaseService;
import com.wfms.common.system.entity.User;
import com.wfms.common.system.service.UserService;
import com.wfms.common.util.MVCUtil;
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
	
	@RequestMapping("login.do")
	public ModelAndView login(HttpSession session,
			@RequestParam("loginId")String loginId,
			@RequestParam("loginPwd")String loginPwd,
			@RequestParam("verify")String verify){
			String sessionVerify = (String) session.getAttribute("validateCode");
			JSONObject jo = new JSONObject();
			if(sessionVerify==null){
				sessionVerify = "";
			}
			logger.info("用户:"+loginId+",正在尝试登录系统...");
			
			/*if(verify==null || verify.equals("")||!verify.equalsIgnoreCase(sessionVerify)){
				jo.put("msg", "登录失败!请输入正确的验证码!");
				jo.put("code", -300);
				jo.put("success", false);
			}else */
			if(loginId==null || loginId.equals("")){
				jo.put("msg", "登录失败!请输入正确的帐号!");
				jo.put("code", -400);
				jo.put("success", false);
			}else if(loginPwd==null || loginPwd.equals("")){
				jo.put("msg", "登录失败!请输入正确的密码!");
				jo.put("code", -500);
				jo.put("success", false);
			}else{
					List<User> userList = ((UserService)getEntityService()).loadLoginUser(loginId, loginPwd);
					if(userList.size()>0){
						session.setAttribute("userId", userList.get(0).getId());
						session.setAttribute("userName", userList.get(0).getUsername());
						jo.put("msg", "登录成功");
						jo.put("success", true);				
					}else{
						jo.put("msg", "登录失败!请输入正确的账号,密码!");
						jo.put("success", false);
						logger.info("用户:"+loginPwd+",登录系统失败!");
					}							
			}
			return MVCUtil.jsonObjectModelAndView(jo);
		}

	@Override
	protected void onLoad(HttpServletRequest request,
			HttpServletResponse response, User entity, ModelAndView mav) {
		super.onLoad(request, response, entity, mav);
	}
	
		
}
