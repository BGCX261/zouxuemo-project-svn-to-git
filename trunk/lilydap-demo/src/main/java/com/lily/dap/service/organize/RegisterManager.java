/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.service.organize;

import com.lily.dap.entity.organize.Person;

/**
 * <code>RegisterManager</code>
 * <p>��Աע�ᡢע������</p>
 *
 * @author ��ѧģ
 * @date 2008-6-13
 */
public interface RegisterManager {
	/**
	 * ע����Ա��Ϣ�����������Ա��Ϣ������ʼ��˻���Ϣ��
	 *
	 * @param person
	 * @return
	 */
	public Person register(Person person);
	
	/**
	 * ��Աע��������ɾ����Ա��Ϣ��ע���ʼ��˻���Ϣ��
	 *
	 * @param id
	 */
	public void unregister(long id);
}
