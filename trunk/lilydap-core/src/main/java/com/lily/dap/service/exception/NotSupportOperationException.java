/**
 * 
 */
package com.lily.dap.service.exception;

/**
 * ��֧�ֵĺ��������ݲ����쳣
 * 
 * @author zouxuemo
 *
 */
public class NotSupportOperationException extends ServiceException {
	/**
	 * <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 0L;

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 */
	public NotSupportOperationException(String msg) {
		super(msg);
	}

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 * @param ex �쳣����ĸ�
	 */
	public NotSupportOperationException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
