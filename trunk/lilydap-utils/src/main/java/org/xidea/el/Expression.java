package org.xidea.el;

public interface Expression{
	/**
	 * ���ݴ���ı��������ģ�map ����javabean����ִ�б��ʽ
	 * @param context ������
	 * @return
	 * @see ExpressionImpl#evaluate(varMap)
	 */
	public Object evaluate(Object context);
	/**
	 * ���ݴ���ı��������ģ���ֵ���飩��ִ�б��ʽ
	 * @param keyValue ��ֵ�ԣ�������������һ����ֵ�ԣ�
	 * @return
	 */
	public Object evaluate(Object... keyValue);
}