package org.xidea.el;

import java.util.Map;

public interface ReferenceExpression{
	/**
	 * ���ݴ���ı��������ģ�ִ�б��ʽ
	 * @see ExpressionImpl#prepare(Map)
	 * @param context ������
	 * @return
	 */
	public Reference prepare(Object context);
	/**
	 * ���ر��ʽ��Դ����
	 * @return
	 */
	public String toString();
}