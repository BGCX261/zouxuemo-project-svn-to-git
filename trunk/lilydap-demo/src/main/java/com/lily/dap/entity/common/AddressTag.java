/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.entity.common;

import java.io.Serializable;

/**
 * <code>AddressTag</code>
 * <p>��ַ��ǽӿڣ�������Ҫ�ɵ�ַ����������ĵ�ַʵ�ֱ��ӿ�</p>
 *
 * @author ��ѧģ
 * @date 2008-3-27
 */
public interface AddressTag {
	/**
	 * ���ص�ַ����
	 *
	 * @return
	 */
	public String getAddressType();
	
	/**
	 * ���ص�ַ����ֵ
	 *
	 * @return
	 */
	public Serializable getSerializable();
}
