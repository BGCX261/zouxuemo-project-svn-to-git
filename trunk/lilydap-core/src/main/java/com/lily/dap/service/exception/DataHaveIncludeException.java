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
 * ��������Ѱ����쳣<br>
 * ���ڸ�ָ��������Ӷ�������������ʱ�ö����Ѿ������˶���������ʱ�׳��쳣
 * 
 * @author zouxuemo
 *
 */
public class DataHaveIncludeException extends ServiceException {
	private static final long serialVersionUID = -7472362990515031291L;

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 */
	public DataHaveIncludeException(String msg) {
		super(msg);
	}

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 * @param ex �쳣����ĸ�
	 */
	public DataHaveIncludeException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
