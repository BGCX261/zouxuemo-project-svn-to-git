/*
 * package com.lily.dap.service.right.exception;
 * class RightInfoRepeatException
 * 
 * 创建日期 2005-8-8
 *
 * 开发者 zouxuemo
 *
 * 淄博百合电子有限公司版权所有
 */
package com.lily.dap.service.exception;


/**
 * 错误的数据状态异常<br>
 * －当操作某个对象时，发现操作需要满足的状态不符合，抛出此异常
 * 
 * @author zouxuemo
 *
 */
public class IllegalDataStateException extends ServiceException {
	private static final long serialVersionUID = 8551088524345171465L;

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 */
	public IllegalDataStateException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 * @param ex 异常引起的根
	 */
	public IllegalDataStateException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
