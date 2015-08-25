/**
 * 
 */
package com.lily.dap.web.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * ע��ʱ������е�Session��Ϣ���ɴ˻ᴥ��acegi��session������ƣ����ע�����û�
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
