/*
 * package com.lily.dap.service.right.exception;
 * class RightInfoRepeatException
 * 
 * �������� 2005-8-8
 *
 * ������ zouxuemo
 *
 * �Ͳ��ٺϵ������޹�˾��Ȩ����
 */
package com.lily.dap.service.exception;


/**
 * ����������Ч�쳣<br>
 * ����鴫����������ݲ�����ϵͳҪ���ʽʱ�׳��쳣
 * 
 * @author zouxuemo
 *
 */
public class DataContentInvalidateException extends ServiceException {
	private static final long serialVersionUID = -7316266682254774004L;

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 */
	public DataContentInvalidateException(String msg) {
		super(msg);
	}

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 * @param ex �쳣����ĸ�
	 */
	public DataContentInvalidateException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
