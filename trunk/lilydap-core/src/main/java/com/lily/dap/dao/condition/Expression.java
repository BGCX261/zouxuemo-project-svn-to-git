/**
 * 
 */
package com.lily.dap.dao.condition;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

/**
 * ��ѯ�������࣬�����в�ѯ������������and,or,not,=,like,in,�ȵȣ��Ļ���
 *
 * @author zouxuemo
 */
public abstract class Expression {
	protected static int TYPE_NULL				 = 0;	//�ղ�����			�������������ʽ�������������ʽ��������
	protected static int TYPE_LOGICAL			 = 1;	//�߼�������		�������������ʽ�������������ʽ��������
	protected static int TYPE_CONDITION			 = 2;	//�������ʽ		�������߼���������������			�����������ʽ�������������ʽ��������
	protected static int TYPE_MODIFIED_CONDITION	 = 3;	//�����������ʽ	�������������ʽ��������
	protected static int TYPE_LEFT_PARENTHESES	 = 4;	//������			�������������ʽ�������������ʽ
	protected static int TYPE_RIGHT_PARENTHESES	 = 5;	//������			�������߼���������������			�����������ʽ�������������ʽ��������

	private static Map<Integer, int[]> expectedMap = new HashMap<Integer, int[]>();
	private static Map<Integer, int[]> allowMap = new HashMap<Integer, int[]>();

	static {
		expectedMap.put(TYPE_NULL, new int[]{TYPE_CONDITION, TYPE_MODIFIED_CONDITION, TYPE_LEFT_PARENTHESES});
		expectedMap.put(TYPE_LOGICAL, new int[]{TYPE_CONDITION, TYPE_MODIFIED_CONDITION, TYPE_LEFT_PARENTHESES});
		expectedMap.put(TYPE_CONDITION, new int[]{TYPE_LOGICAL, TYPE_RIGHT_PARENTHESES});
		expectedMap.put(TYPE_MODIFIED_CONDITION, new int[]{TYPE_CONDITION, TYPE_LEFT_PARENTHESES});
		expectedMap.put(TYPE_LEFT_PARENTHESES, new int[]{TYPE_CONDITION, TYPE_MODIFIED_CONDITION});
		expectedMap.put(TYPE_RIGHT_PARENTHESES, new int[]{TYPE_LOGICAL, TYPE_RIGHT_PARENTHESES});
		
		allowMap.put(TYPE_CONDITION, new int[]{TYPE_CONDITION, TYPE_MODIFIED_CONDITION, TYPE_LEFT_PARENTHESES});
		allowMap.put(TYPE_RIGHT_PARENTHESES, new int[]{TYPE_CONDITION, TYPE_MODIFIED_CONDITION, TYPE_LEFT_PARENTHESES});
	}
	
	protected abstract int type();
	
	public boolean expected(Expression next) {
		int[] expectedType = expectedMap.get(type());
		if (expectedType == null)
			return false;
		
		return ArrayUtils.contains(expectedType, next.type());
	}
	
	public boolean allow(Expression next) {
		int[] allowType = allowMap.get(type());
		if (allowType == null)
			return false;
		
		return ArrayUtils.contains(allowType, next.type());
	}
	
	public abstract String hql();
	
	public abstract Object[] params();
}
