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
 * 数据内容无效异常<br>
 * －检查传入的数据内容不符合系统要求格式时抛出异常
 * 
 * @author zouxuemo
 *
 */
public class DataContentInvalidateException extends ServiceException {
	private static final long serialVersionUID = -7316266682254774004L;

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 */
	public DataContentInvalidateException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 * @param ex 异常引起的根
	 */
	public DataContentInvalidateException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
