package org.xidea.el;

/**
 * ��2ֵ֮��ļ��㡣
 * ��Ԫ���������Ҫת��Ϊ��Ԫ��ʾ
 * ֵ������������������ߴ�vs�л�ȡֵ
 * @author jindw
 */
public interface OperationStrategy {

	/**
	 * @param op ����������
	 * @param vs ���������
	 * @return ������
	 * @see org.xidea.el.impl.OperationStrategyImpl#evaluate
	 */
	public Object evaluate(ExpressionToken token,ValueStack vs) ;
	public Object getVar(ValueStack vs,Object key);
}
