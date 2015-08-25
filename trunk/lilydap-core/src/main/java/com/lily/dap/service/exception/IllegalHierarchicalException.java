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
 * ����Ĳ�ι�ϵ�쳣<br>
 * �������Ρ���״�ȶ������ݽṹ�е����ִ��ڲ�����Ļ�����ϵ���磺���������ӣ����ְ����˸������߸������ӣ�����û�й��������ȵ�...��ʱ�׳�
 * 
 * @author zouxuemo
 *
 */
public class IllegalHierarchicalException extends ServiceException {
	private static final long serialVersionUID = -6567542248690128957L;

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 */
	public IllegalHierarchicalException(String msg) {
		super(msg);
	}

	/**
	 * ���캯��
	 * 
	 * @param msg �쳣��Ϣ
	 * @param ex �쳣����ĸ�
	 */
	public IllegalHierarchicalException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
