package com.lily.dap.service.exception;

/**
 * �����Ѳ����쳣
 * ���ڶԶ���Ҫ����ָ������ʱ���ֶ����Ѿ���������������磺�������������������ٴν��иò���ʱ�׳��쳣
 * 
 * @author Administrator
 *
 */

public class HaveOperateException extends ServiceException {
	private static final long serialVersionUID = -8820454339846845941L;

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 */
	public HaveOperateException(String msg) {
		super(msg);
	}

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 * @param ex �쳣����ĸ�
	 */
	public HaveOperateException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
