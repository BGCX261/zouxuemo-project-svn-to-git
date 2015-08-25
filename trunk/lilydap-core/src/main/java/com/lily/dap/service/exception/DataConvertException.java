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
 * ����ת���쳣<br>
 * ������ת������ʱ�׳��쳣
 * 
 * @author zouxuemo
 *
 */
public class DataConvertException extends ServiceException {
	private static final long serialVersionUID = -7316266682254774004L;

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 */
	public DataConvertException(String msg) {
		super(msg);
	}

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 * @param ex �쳣����ĸ�
	 */
	public DataConvertException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
