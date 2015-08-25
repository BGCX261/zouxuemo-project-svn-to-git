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
 * 要操作数据已使用异常<br>
 * －在删除对象时对象已经被使用时抛出异常
 * 
 * @author zouxuemo
 *
 */
public class DataHaveUsedException extends ServiceException {
	private static final long serialVersionUID = 731260937057705229L;

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 */
	public DataHaveUsedException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 * @param ex 异常引起的根
	 */
	public DataHaveUsedException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
