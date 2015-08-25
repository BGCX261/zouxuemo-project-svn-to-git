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
 * 指定数据对象不包含给定数据异常
 * －在去除指定对象的对其他对象引用时该对象不包含对它的引用时抛出异常
 * 
 * @author zouxuemo
 *
 */
public class DataNotIncludeException extends ServiceException {
	private static final long serialVersionUID = -4371024139320642230L;

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 */
	public DataNotIncludeException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 * @param ex 异常引起的根
	 */
	public DataNotIncludeException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
