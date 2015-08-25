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
 * ���������״̬�쳣<br>
 * ��������ĳ������ʱ�����ֲ�����Ҫ�����״̬�����ϣ��׳����쳣
 * 
 * @author zouxuemo
 *
 */
public class IllegalDataStateException extends ServiceException {
	private static final long serialVersionUID = 8551088524345171465L;

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 */
	public IllegalDataStateException(String msg) {
		super(msg);
	}

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 * @param ex �쳣����ĸ�
	 */
	public IllegalDataStateException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
