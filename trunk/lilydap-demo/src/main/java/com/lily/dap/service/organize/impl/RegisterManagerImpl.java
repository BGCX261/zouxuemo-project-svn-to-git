/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.service.organize.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lily.dap.entity.organize.Person;
import com.lily.dap.service.organize.PersonManager;
import com.lily.dap.service.organize.RegisterManager;

/**
 * <code>RegisterManagerImpl</code>
 * <p>人员注册、注销管理实现</p>
 *
 * @author 邹学模
 * @date 2008-6-13
 */
@Service("registerManager")
public class RegisterManagerImpl implements RegisterManager {
	@Autowired
	private PersonManager personManager;
	
	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.RegisterManager#register(com.lily.dap.entity.organize.Person)
	 */
	public Person register(Person person) {
		person = personManager.createPerson(person);
		
		return person;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.organize.RegisterManager#unregister(long)
	 */
	public void unregister(long id) {
		personManager.removePerson(id);
	}
}
