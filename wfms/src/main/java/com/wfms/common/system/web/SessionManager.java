package com.wfms.common.system.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.wfms.common.filter.ServletContextHandler;
import com.wfms.common.system.constant.UserCenter;
import com.wfms.common.system.entity.User;
import com.wfms.common.system.service.UserService;

/**
 * session管理器
 * 
 * @author CYC
 * 
 */
@Component("sessionManager")
public class SessionManager {

	private static Logger logger = LoggerFactory
			.getLogger(SessionManager.class);

	private static Map<String, HttpSession> sessionMap = new HashMap<String, HttpSession>();
	private static Map<String, Long> timeMap = new HashMap<String, Long>();

	private static final long Timer_Interval = 150000;
	public static final long Request_Interval = 30000;

	private static boolean isRuned = false;

	private static UserService userService;
	
	private static final Timer timer = new Timer();
	
	public static void run() {
		if (!isRuned) {
			try {
				getUserService().updateAllUserLoginStatus(
						UserCenter.Login_Status_False);
			} catch (Exception ex) {

			}

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
			logger.info("SessionManager running...");
		}
	}
	
	public static void stop(){
		if(timer!=null)
		{
			timer.cancel();
		}
	}

	private static UserService getUserService() {
		if (userService == null) {
			WebApplicationContext wac = ContextLoader
					.getCurrentWebApplicationContext();
			userService = (UserService) wac.getBean("userService");
		}
		return userService;
	}

	/**
	 * 保存用户对象到session
	 * 
	 * @param user
	 * @param request
	 */
	public static synchronized void saveSession(User user) throws Exception {
		getUserService().updateUserLoginStatus(user.getId(),
				UserCenter.Login_Status_True);
		HttpSession session = ServletContextHandler.getSession();
		session.setAttribute(UserCenter.Session_Name, user);
		sessionMap.put(session.getId(), session);
		timeMap.put(session.getId(), System.currentTimeMillis());
	}

	/***************************************************************************
	 * 更新请求时间
	 */
	public static void requestLastTime() {
		String id = ServletContextHandler.getSession().getId();
		if (timeMap.get(id) != null) {
			timeMap.put(id, System.currentTimeMillis());
		}
	}

	/***************************************************************************
	 * 清空当前请求线程session对象
	 */
	public static synchronized void clearSession() {
		clearSession(ServletContextHandler.getSession());
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
				getUserService().updateUserLoginStatus(user.getId(),
						UserCenter.Login_Status_False);
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
		return getUser(ServletContextHandler.getSession());
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
