/*
 * package com.lily.dap.service.right.exception;
 * class RightAccessException
 * 
 * �������� 2005-8-8
 *
 * ������ zouxuemo
 *
 * �Ͳ��ٺϵ������޹�˾��Ȩ����
 */
package com.lily.dap.service.exception;

/**
 * �����쳣����Ϊ���з�������쳣��ĸ���
 * 
 * @author zouxuemo
 *
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -3950860655222698709L;

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 */
	public ServiceException(String msg) {
		super(msg);
	}

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 * @param ex �쳣����ĸ�
	 */
	public ServiceException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
