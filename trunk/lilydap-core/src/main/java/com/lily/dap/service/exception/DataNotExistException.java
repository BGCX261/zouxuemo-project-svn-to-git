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
 * 操作对象数据不存在异常<br>
 * －在删除或者更新或者检索给定id的对象，对象不存在时抛出异常
 * 
 * @author zouxuemo
 *
 */
public class DataNotExistException extends ServiceException {
	private static final long serialVersionUID = 5998884008460802411L;

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 */
	public DataNotExistException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 * @param ex 异常引起的根
	 */
	public DataNotExistException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
