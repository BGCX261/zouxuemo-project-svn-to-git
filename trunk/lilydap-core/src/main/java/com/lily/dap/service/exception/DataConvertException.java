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
 * 数据转换异常<br>
 * －数据转换出错时抛出异常
 * 
 * @author zouxuemo
 *
 */
public class DataConvertException extends ServiceException {
	private static final long serialVersionUID = -7316266682254774004L;

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 */
	public DataConvertException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 * @param ex 异常引起的根
	 */
	public DataConvertException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
