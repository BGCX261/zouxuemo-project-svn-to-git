/**
 * 
 */
package com.lily.dap.service.exception;

/**
 * 不支持的函数及数据操作异常
 * 
 * @author zouxuemo
 *
 */
public class NotSupportOperationException extends ServiceException {
	/**
	 * <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 0L;

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 */
	public NotSupportOperationException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 * @param ex 异常引起的根
	 */
	public NotSupportOperationException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
