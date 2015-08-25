/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.service.organize;

import com.lily.dap.entity.organize.Person;

/**
 * <code>RegisterManager</code>
 * <p>人员注册、注销管理</p>
 *
 * @author 邹学模
 * @date 2008-6-13
 */
public interface RegisterManager {
	/**
	 * 注册人员信息，包括添加人员信息、添加邮件账户信息等
	 *
	 * @param person
	 * @return
	 */
	public Person register(Person person);
	
	/**
	 * 人员注销，包括删除人员信息、注销邮件账户信息等
	 *
	 * @param id
	 */
	public void unregister(long id);
}
