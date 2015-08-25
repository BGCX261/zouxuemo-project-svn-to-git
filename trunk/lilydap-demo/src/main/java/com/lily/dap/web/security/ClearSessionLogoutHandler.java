/**
 * 
 */
package com.lily.dap.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * 注销时清除所有的Session信息，由此会触发acegi的session清除机制，清除注销的用户
 * 
 * @author zouxuemo
 *
 */
public class ClearSessionLogoutHandler implements LogoutHandler {

	/* (non-Javadoc)
	 * @see org.acegisecurity.ui.logout.LogoutHandler#logout(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.acegisecurity.Authentication)
	 */
	public void logout(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication) {
		request.getSession().invalidate();
	}

}
