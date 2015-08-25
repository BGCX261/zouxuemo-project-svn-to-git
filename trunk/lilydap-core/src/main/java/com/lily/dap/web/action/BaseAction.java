package com.lily.dap.web.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

/**
 * <code>BaseAction</code>
 * <p>
 * Action基类，所有Action应该继承本基类
 * </p>
 * 
 * @author 邹学模
 * @date 2008-10-30
 */
public abstract class BaseAction extends ActionSupport {
	private static final long serialVersionUID = -3014580444253857092L;

	protected final Logger logger = LoggerFactory.getLogger(getClass());
}
