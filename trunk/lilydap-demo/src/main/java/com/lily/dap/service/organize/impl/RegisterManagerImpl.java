/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.service.organize.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lily.dap.entity.organize.Person;
import com.lily.dap.service.organize.PersonManager;
import com.lily.dap.service.organize.RegisterManager;

/**
 * <code>RegisterManagerImpl</code>
 * <p>��Աע�ᡢע������ʵ��</p>
 *
 * @author ��ѧģ
 * @date 2008-6-13
 */
@Service("registerManager")
public class RegisterManagerImpl implements RegisterManager {
	@Autowired
	private PersonManager personManager;
	
	/* ���� Javadoc��
	 * @see com.lily.dap.service.organize.RegisterManager#register(com.lily.dap.entity.organize.Person)
	 */
	public Person register(Person person) {
		person = personManager.createPerson(person);
		
		return person;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.organize.RegisterManager#unregister(long)
	 */
	public void unregister(long id) {
		personManager.removePerson(id);
	}
}
