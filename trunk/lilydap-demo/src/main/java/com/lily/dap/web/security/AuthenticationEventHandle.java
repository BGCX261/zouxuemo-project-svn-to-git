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
 * ϵͳ��¼��ϵͳע���¼�������
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
			//��¼�ɹ�����¼�����
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
//			//�ѵ�ǰ��¼�û���CSS����д��Session��
//			String cssTheme = settingManager.getPersonSettingValue(username, MyConstants.CSS_THEME);
//			if (cssTheme == null)
//				cssTheme = "";
//			
//			session.setAttribute(MyConstants.CSS_THEME, cssTheme);
//			
//			//�ѵ�ǰ��¼�û���������Ϣ��ʱ����ʱ����д��Session��
//			int messageCheckInterval = settingManager.getPersonSettingIntValue(username, MyConstants.MESSAGE_CHECK_INTERVAL);
//			session.setAttribute(MyConstants.MESSAGE_CHECK_INTERVAL, messageCheckInterval);
//			
//			//�ѵ�ǰ��¼�û��ĵ����ʼ���ʱ����ʱ����д��Session��
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
			
			logManager.log(username, name, ip, "��¼ϵͳ", "");
			
			if (logger.isDebugEnabled())
				logger.debug("�û� {}[{}] ��¼ϵͳ����¼IP��{}��session��{}", new Object[]{name, username, ip, sessionId});
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
		
		logManager.log(username, name, ip, "�˳���½", "");
		
		if (logger.isDebugEnabled())
			logger.debug("�û� {}[{}] �˳���½����¼IP��{}��session��{}", new Object[]{name, username, ip, sessionId});
	}
}
