/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.web.security;

import org.springframework.dao.DataAccessException;

/**
 * <code>InvalidateUserTypeException</code>
 * <p>��ʶ����û������쳣</p>
 *
 * @author ��ѧģ
 * @date 2008-3-31
 */
public class InvalidateUserTypeException extends DataAccessException {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = 5281383833503357477L;

	public InvalidateUserTypeException(String msg) {
		super(msg);
	}
}
