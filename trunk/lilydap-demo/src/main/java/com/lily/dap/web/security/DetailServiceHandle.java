/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.web.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <code>DetailServiceHandle</code>
 * <p>�����û���������Ӧ���û���Ϣ����</p>
 *
 * @author ��ѧģ
 * @date 2008-3-31
 */
public interface DetailServiceHandle {
	/**
	 * ֧�ֵķ�������
	 *
	 * @return
	 */
	public String supportUserType();
	
	/**
	 * �����û���������Ӧ�û���Ϣ����Ӧ�Ľ�ɫ�������Ϣ
	 *
	 * @param username
	 * @return
	 * @throws UsernameNotFoundException
	 * @throws DataAccessException
	 */
	public UserDetails loadUserByUsername(String username, String rolePrefix) throws UsernameNotFoundException, DataAccessException;
}
