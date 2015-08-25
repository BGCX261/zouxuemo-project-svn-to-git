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
 * 添加数据已包含异常<br>
 * －在给指定对象添加对其他对象引用时该对象已经包含了对它的引用时抛出异常
 * 
 * @author zouxuemo
 *
 */
public class DataHaveIncludeException extends ServiceException {
	private static final long serialVersionUID = -7472362990515031291L;

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 */
	public DataHaveIncludeException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 * @param ex 异常引起的根
	 */
	public DataHaveIncludeException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
