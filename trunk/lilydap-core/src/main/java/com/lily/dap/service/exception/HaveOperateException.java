package com.lily.dap.service.exception;

/**
 * 对象已操作异常
 * －在对对象要进行指定操作时发现对象已经做过这个操作（如：审批操作），不允许再次进行该操作时抛出异常
 * 
 * @author Administrator
 *
 */

public class HaveOperateException extends ServiceException {
	private static final long serialVersionUID = -8820454339846845941L;

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 */
	public HaveOperateException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 * @param ex 异常引起的根
	 */
	public HaveOperateException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
