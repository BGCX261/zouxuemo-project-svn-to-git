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
 * Ҫ����������ʹ���쳣<br>
 * ����ɾ������ʱ�����Ѿ���ʹ��ʱ�׳��쳣
 * 
 * @author zouxuemo
 *
 */
public class DataHaveUsedException extends ServiceException {
	private static final long serialVersionUID = 731260937057705229L;

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 */
	public DataHaveUsedException(String msg) {
		super(msg);
	}

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 * @param ex �쳣����ĸ�
	 */
	public DataHaveUsedException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
