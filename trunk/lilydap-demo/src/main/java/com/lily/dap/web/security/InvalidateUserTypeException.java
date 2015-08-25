/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.security;

import org.springframework.dao.DataAccessException;

/**
 * <code>InvalidateUserTypeException</code>
 * <p>不识别的用户类型异常</p>
 *
 * @author 邹学模
 * @date 2008-3-31
 */
public class InvalidateUserTypeException extends DataAccessException {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = 5281383833503357477L;

	public InvalidateUserTypeException(String msg) {
		super(msg);
	}
}
