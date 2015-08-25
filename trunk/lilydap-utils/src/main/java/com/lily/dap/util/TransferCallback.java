/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.util;

/**
 * <code>TransferCallback</code>
 * <p>���ݴ��ݻص��ӿ�</p>
 *
 * @author ��ѧģ
 * @date 2008-5-22
 */
public interface TransferCallback {
	/**
	 * ׼�����Կ����ص��������ڸ��ƶ����ÿ������ǰ���á�����ص���������ֵΪtrue����ִ�и���������Բ������������ֵΪfalse����ִ�и��Ʋ���
	 * 
	 * @param src
	 * @param srcFieldName
	 * @param srcFieldValue
	 * @param tgt
	 * @param tgtFieldName
	 * @param tgtFieldValue
	 * @return
	 */
	public boolean readyCopy(Object src, String srcFieldName, Object srcFieldValue, Object tgt, String tgtFieldName, Object tgtFieldValue);
}
