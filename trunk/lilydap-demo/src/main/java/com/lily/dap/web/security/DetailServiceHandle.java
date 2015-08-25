/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <code>DetailServiceHandle</code>
 * <p>根据用户名加载相应的用户信息处理</p>
 *
 * @author 邹学模
 * @date 2008-3-31
 */
public interface DetailServiceHandle {
	/**
	 * 支持的服务类型
	 *
	 * @return
	 */
	public String supportUserType();
	
	/**
	 * 根据用户名加载相应用户信息及对应的角色和许可信息
	 *
	 * @param username
	 * @return
	 * @throws UsernameNotFoundException
	 * @throws DataAccessException
	 */
	public UserDetails loadUserByUsername(String username, String rolePrefix) throws UsernameNotFoundException, DataAccessException;
}
