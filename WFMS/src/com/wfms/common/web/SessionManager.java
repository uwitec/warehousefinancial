package com.wfms.common.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.wfms.common.filter.ServletContextUtil;
import com.wfms.common.system.constant.UserCenter;
import com.wfms.common.system.entity.User;
import com.wfms.common.system.service.UserService;

/**
 * 管理session
 * 
 * @author CYC
 * 
 */
@Service("sessionManager")
public class SessionManager {

	private static Map<String, HttpSession> sessionMap = new HashMap<String, HttpSession>();
	private static Map<String, Long> timeMap = new HashMap<String, Long>();

	private static final long Timer_Interval = 150000;
	public static final long Request_Interval = 30000;

	private static boolean isRuned = false;

	public static void run() {
		if (!isRuned) {
			try {
				// getUserService().setUserLoginStatus(UserCenter.Login_Status_False);
			} catch (Exception ex) {

			}

			Timer timer = new Timer();
			TimerTask timerTask = new TimerTask() {
				public void run() {
					Set<String> set = sessionMap.keySet();
					Iterator<String> iterator = set.iterator();
					long current = System.currentTimeMillis();
					while (iterator.hasNext()) {
						String key = iterator.next();
						long mill = timeMap.get(key);
						if (current - mill > Timer_Interval) {
							clearSession(sessionMap.get(key));
						}
					}
				}
			};
			timer.schedule(timerTask, 0, Timer_Interval);
			isRuned = true;
		}
	}

	private static UserService getUserService() {
		WebApplicationContext wac = ContextLoader
				.getCurrentWebApplicationContext();
		UserService userService = (UserService) wac.getBean("userService");
		return userService;
	}

	/**
	 * 保存用户对象到session
	 * 
	 * @param user
	 * @param request
	 */
	public static synchronized void saveSession(User user) throws Exception {
		// getUserService().setUserLoginStatus(user.getId(),UserCenter.Login_Status_True);

		HttpSession session = ServletContextUtil.getSession();
		session.setAttribute(UserCenter.Session_Name, user);
		sessionMap.put(session.getId(), session);
		timeMap.put(session.getId(), System.currentTimeMillis());
	}

	/***************************************************************************
	 * 更新请求时间
	 */
	public static void requestLastTime() {
		String id = ServletContextUtil.getSession().getId();
		if (timeMap.get(id) != null) {
			timeMap.put(id, System.currentTimeMillis());
		}
	}

	/***************************************************************************
	 * 清空当前请求线程session对象
	 */
	public static synchronized void clearSession() {
		clearSession(ServletContextUtil.getSession());
	}

	/**
	 * 清空session
	 * 
	 * @param session
	 */
	public static synchronized void clearSession(HttpSession session) {
		User user = getUser(session);
		if (user != null) {
			try {
				// getUserService().setUserLoginStatus(user.getId(),UserCenter.Login_Status_False);
				timeMap.remove(session.getId());
				sessionMap.remove(session.getId());
				session.invalidate();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 获取当前请求线程session对象
	 * 
	 * @return
	 */
	public static User getUser() {
		return getUser(ServletContextUtil.getSession());
	}

	/**
	 * 获取session中的用户对象
	 * 
	 * @param session
	 * @return
	 */
	public static User getUser(HttpSession session) {
		try {
			return (User) session.getAttribute(UserCenter.Session_Name);
		} catch (Exception ex) {
			return null;
		}
	}
}
