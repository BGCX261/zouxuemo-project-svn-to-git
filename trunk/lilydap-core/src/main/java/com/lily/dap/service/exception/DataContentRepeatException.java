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
 * 数据内容和现有数据重复异常<br>
 * －在添加或者更新数据时，检查出不允许重复的数据信息重复抛出
 * 
 * @author zouxuemo
 *
 */
public class DataContentRepeatException extends ServiceException {
	private static final long serialVersionUID = -947563095895662581L;

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 */
	public DataContentRepeatException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 * @param ex 异常引起的根
	 */
	public DataContentRepeatException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
