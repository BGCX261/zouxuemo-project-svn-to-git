/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.web.taglib.ext.data;

/**
 * <code>ExtDataTargetInterface</code>
 * <p>����Դʹ���߽ӿ�</p>
 *
 * @author ��ѧģ
 * @date 2008-4-18
 */
@SuppressWarnings("unchecked")
public interface ExtDataTargetInterface {
	/**
	 * ����ʹ�������õ�ʵ������
	 *
	 * @return
	 */
	public Class getEntity();
	
	/**
	 * ����ʹ���ߵ���������Դ�ű���������ʹ���ߵ�����Դ
	 *
	 * @param dataScript
	 */
	public void setDataScript(String dataScript);
}
