package com.cbmie.system.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * session监听器
 */
//@WebListener
public class SessionListener implements HttpSessionListener {
	
	private Logger log=LoggerFactory.getLogger(SessionListener.class);
	
	/**
	 * 当前系统中所有生效session映射
	 */
	public static Map<String, HttpSession> sessionMap = new HashMap<String, HttpSession>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();

		// 在application范围由一个HashSet集保存所有的session
		HashSet sessions = (HashSet) application.getAttribute("sessions");
		if (sessions == null) {
			sessions = new HashSet();
			application.setAttribute("sessions", sessions);
		}

		// 新创建的session均添加到HashSet集中
		sessions.add(session);
		sessionMap.put(session.getId(), session);
		log.info("session："+session.getId()+" add");
		// 可以在别处从application范围中取出sessions集合
		// 然后使用sessions.size()获取当前活动的session数，即为“在线人数”
	}

	@SuppressWarnings("rawtypes")
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		ServletContext application = session.getServletContext();
		HashSet sessions = (HashSet) application.getAttribute("sessions");

		// 销毁的session均从HashSet集中移除
		if(sessions != null && session != null){
			sessions.remove(session);
			sessionMap.remove(session.getId());
			log.info("session："+session.getId()+" remove");
		}
	}

}
