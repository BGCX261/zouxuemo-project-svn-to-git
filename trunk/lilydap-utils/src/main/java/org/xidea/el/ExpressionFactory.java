package org.xidea.el;

import org.xidea.el.impl.ExpressionFactoryImpl;

/**
 * @see ExpressionFactoryImpl
 */
public abstract class ExpressionFactory {
	/**
	 * ���ϵͳĬ�ϵı��ʽ����(����ECMA262 ��׼��չ�ı��ʽ����,״̬(���ö���,�������չ)�������޸�)
	 * @return
	 */
	public static ExpressionFactory getInstance() {
		return ExpressionFactoryImpl.getInstance();
	}
	/**
	 * ���м�������ֱ�ӵı��ʽ�ı������ɱ��ʽ����
	 * @param el
	 * @return
	 */
	public abstract Expression create(Object el);
	/**
	 * �����ʽ�������м�״̬
	 * @param expression
	 * @return
	 */
	public abstract Object parse(String expression);
}