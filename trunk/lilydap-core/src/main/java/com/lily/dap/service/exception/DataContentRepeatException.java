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
 * �������ݺ����������ظ��쳣<br>
 * ������ӻ��߸�������ʱ�������������ظ���������Ϣ�ظ��׳�
 * 
 * @author zouxuemo
 *
 */
public class DataContentRepeatException extends ServiceException {
	private static final long serialVersionUID = -947563095895662581L;

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 */
	public DataContentRepeatException(String msg) {
		super(msg);
	}

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 * @param ex �쳣����ĸ�
	 */
	public DataContentRepeatException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
