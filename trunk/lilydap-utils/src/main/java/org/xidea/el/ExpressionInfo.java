package org.xidea.el;

import java.util.List;

public interface ExpressionInfo{
	/**
	 * ���ر��ʽ�漰���ı���������
	 * @return
	 */
	public List<String> getVars();
	/**
	 * ���ر��ʽ��Դ����(JSON)
	 * @return
	 */
	public String toString();
}