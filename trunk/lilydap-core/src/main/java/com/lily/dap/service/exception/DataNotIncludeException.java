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
 * ָ�����ݶ��󲻰������������쳣
 * ����ȥ��ָ������Ķ�������������ʱ�ö��󲻰�������������ʱ�׳��쳣
 * 
 * @author zouxuemo
 *
 */
public class DataNotIncludeException extends ServiceException {
	private static final long serialVersionUID = -4371024139320642230L;

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 */
	public DataNotIncludeException(String msg) {
		super(msg);
	}

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 * @param ex �쳣����ĸ�
	 */
	public DataNotIncludeException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
