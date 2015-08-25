package com.lily.dap.dao.condition;


/**
 * ������������ʽ���ṩ���ڡ����ڵ��ڡ�С�ڡ�С�ڵ��ڡ����ڡ������ڡ�ƥ�䡢��ƥ�䡢��ƥ�䡢��ƥ�������
 *
 * @author zouxuemo
 */
public class NormalExpression extends Expression {
	private static String CONDITION_GT = ">";
	private static String CONDITION_GE = ">=";
	private static String CONDITION_LT = "<";
	private static String CONDITION_LE = "<=";
	private static String CONDITION_EQ = "=";
	private static String CONDITION_NE = "<>";
	private static String CONDITION_LIKE = "like";
	private static String CONDITION_NOTLIKE = "not like";
	
	private String name;
	
	private String op;
	
	private Object[] values;
	
	private NormalExpression(String name, String op, Object[] values) {
		this.name = name;
		this.op = op;
		this.values = values;
	}
	
	@Override
	public String hql() {
		return name + " " + op + " ?";
	}

	@Override
	public Object[] params() {
		return values;
	}

	@Override
	protected int type() {
		return TYPE_CONDITION;
	}
	
	/**
	 * �������������
	 * 
	 * @return
	 */
	public static NormalExpression gt(String name, Object value) {
		return new NormalExpression(name, CONDITION_GT, new Object[]{value});
	}
	
	/**
	 * �������ڵ��������
	 * 
	 * @return
	 */
	public static NormalExpression ge(String name, Object value) {
		return new NormalExpression(name, CONDITION_GE, new Object[]{value});
	}
	
	/**
	 * ����С�������
	 * 
	 * @return
	 */
	public static NormalExpression lt(String name, Object value) {
		return new NormalExpression(name, CONDITION_LT, new Object[]{value});
	}
	
	/**
	 * ����С�ڵ��������
	 * 
	 * @return
	 */
	public static NormalExpression le(String name, Object value) {
		return new NormalExpression(name, CONDITION_LE, new Object[]{value});
	}
	
	/**
	 * �������������
	 * 
	 * @return
	 */
	public static NormalExpression eq(String name, Object value) {
		return new NormalExpression(name, CONDITION_EQ, new Object[]{value});
	}
	
	/**
	 * ���������������
	 * 
	 * @return
	 */
	public static NormalExpression ne(String name, Object value) {
		return new NormalExpression(name, CONDITION_NE, new Object[]{value});
	}
	
	/**
	 * ����ƥ�������
	 * 
	 * @return
	 */
	public static NormalExpression like(String name, Object value) {
		String val = "%" + value.toString() + "%";
		
		return new NormalExpression(name, CONDITION_LIKE, new Object[]{val});
	}
	
	/**
	 * ������ƥ�������
	 * 
	 * @return
	 */
	public static NormalExpression llike(String name, Object value) {
		String val = value.toString() + "%";
		
		return new NormalExpression(name, CONDITION_LIKE, new Object[]{val});
	}
	
	/**
	 * ������ƥ�������
	 * 
	 * @return
	 */
	public static NormalExpression rlike(String name, Object value) {
		String val = "%" + value.toString();
		
		return new NormalExpression(name, CONDITION_LIKE, new Object[]{val});
	}
	
	/**
	 * ������ƥ�������
	 * 
	 * @return
	 */
	public static NormalExpression notlike(String name, Object value) {
		String val = "%" + value.toString() + "%";
		
		return new NormalExpression(name, CONDITION_NOTLIKE, new Object[]{val});
	}
}
