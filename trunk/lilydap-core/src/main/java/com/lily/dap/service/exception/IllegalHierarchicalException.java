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
 * 错误的层次关系异常<br>
 * －在树形、网状等对象数据结构中当发现存在不合理的机构关系（如：父包含了子，子又包含了父、或者父包含子，但子没有关联父、等等...）时抛出
 * 
 * @author zouxuemo
 *
 */
public class IllegalHierarchicalException extends ServiceException {
	private static final long serialVersionUID = -6567542248690128957L;

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 */
	public IllegalHierarchicalException(String msg) {
		super(msg);
	}

	/**
	 * 构造函数
	 * 
	 * @param msg 异常信息
	 * @param ex 异常引起的根
	 */
	public IllegalHierarchicalException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
