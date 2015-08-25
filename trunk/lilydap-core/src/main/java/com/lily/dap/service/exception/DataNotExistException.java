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
 * �����������ݲ������쳣<br>
 * ����ɾ�����߸��»��߼�������id�Ķ��󣬶��󲻴���ʱ�׳��쳣
 * 
 * @author zouxuemo
 *
 */
public class DataNotExistException extends ServiceException {
	private static final long serialVersionUID = 5998884008460802411L;

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 */
	public DataNotExistException(String msg) {
		super(msg);
	}

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 * @param ex �쳣����ĸ�
	 */
	public DataNotExistException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
