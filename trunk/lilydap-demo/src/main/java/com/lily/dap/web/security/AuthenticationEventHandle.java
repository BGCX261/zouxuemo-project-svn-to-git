/**
 * 
 */
package com.lily.dap.web.security;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;

//import com.lily.dap.MyConstants;
import com.lily.dap.entity.organize.Person;
import com.lily.dap.service.common.LogManager;
import com.lily.dap.service.common.OnLineInfo;
import com.lily.dap.service.common.OnLineManager;
//import com.lily.dap.service.common.SettingManager;

/**
 * 系统登录和系统注销事件处理类
 * 
 * @author zouxuemo
 *
 */
public class AuthenticationEventHandle implements ApplicationListener<ApplicationEvent> {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private LogManager logManager;
	
//	@Autowired
//	private SettingManager settingManager;
	
	@Autowired
	private OnLineManager onLineManager;

	public void onApplicationEvent(ApplicationEvent e) {
		if (e instanceof AuthenticationSuccessEvent) {
			//登录成功后的事件处理
			AuthenticationSuccessEvent event = (AuthenticationSuccessEvent)e;
			Authentication authentication = event.getAuthentication();
			
			loginUser(authentication);
		} else if (e instanceof HttpSessionCreatedEvent) {
			HttpSession session = ((HttpSessionCreatedEvent)e).getSession();
			
			OnLineInfo onlineInfo = onLineManager.getOnlineUser(session.getId());
			if (onlineInfo == null)
				return;
			 
	        Person person = onlineInfo.getPerson();
	        String username = person.getUsername();
//	        
//			//把当前登录用户的CSS主题写入Session中
//			String cssTheme = settingManager.getPersonSettingValue(username, MyConstants.CSS_THEME);
//			if (cssTheme == null)
//				cssTheme = "";
//			
//			session.setAttribute(MyConstants.CSS_THEME, cssTheme);
//			
//			//把当前登录用户的在线消息定时接收时间间隔写入Session中
//			int messageCheckInterval = settingManager.getPersonSettingIntValue(username, MyConstants.MESSAGE_CHECK_INTERVAL);
//			session.setAttribute(MyConstants.MESSAGE_CHECK_INTERVAL, messageCheckInterval);
//			
//			//把当前登录用户的电子邮件定时接收时间间隔写入Session中
//			int mailCheckInterval = settingManager.getPersonSettingIntValue(username, MyConstants.MAIL_CHECK_INTERVAL);
//			session.setAttribute(MyConstants.MAIL_CHECK_INTERVAL, mailCheckInterval);
		} else if (e instanceof HttpSessionDestroyedEvent) {
			SecurityContext securityContext = ((HttpSessionDestroyedEvent)e).getSecurityContext();
			if (securityContext == null)
				return;
			
	        Authentication authentication = securityContext.getAuthentication();
	        if (authentication == null)
	        	return;

	        if (authentication.getDetails() instanceof WebAuthenticationDetails) {
				WebAuthenticationDetails details = (WebAuthenticationDetails)authentication.getDetails();
				String sessionId = details.getSessionId();
	        	
				logoutUser(sessionId);
	        }
		}
	}
	
	private void loginUser(Authentication authentication) {
		if (authentication == null)
			return;
		
		if (!(authentication.getPrincipal() instanceof Person))
			return;
		
		Person person = (Person)authentication.getPrincipal();
		String username = person.getUsername();
		String name = person.getName();
		
		if (authentication.getDetails() instanceof WebAuthenticationDetails) {
			WebAuthenticationDetails details = (WebAuthenticationDetails)authentication.getDetails();
			
			String ip = details.getRemoteAddress();
			String sessionId = details.getSessionId();
			
			if (onLineManager.getOnlineUser(sessionId) != null)
				return;
			
			onLineManager.loginUser(ip, sessionId, person);
			
			logManager.log(username, name, ip, "登录系统", "");
			
			if (logger.isDebugEnabled())
				logger.debug("用户 {}[{}] 登录系统，登录IP：{}，session：{}", new Object[]{name, username, ip, sessionId});
		}
	}
	
	private void logoutUser(String sessionId) {
		OnLineInfo onlineInfo = onLineManager.getOnlineUser(sessionId);
		if (onlineInfo == null)
			return;
		
		String ip = onlineInfo.getIp();
		Person person = onlineInfo.getPerson();
		String username = person.getUsername();
		String name = person.getName();
		
		onLineManager.logoutUser(sessionId);
		
		logManager.log(username, name, ip, "退出登陆", "");
		
		if (logger.isDebugEnabled())
			logger.debug("用户 {}[{}] 退出登陆，登录IP：{}，session：{}", new Object[]{name, username, ip, sessionId});
	}
}
