package com.lily.dap.dao.condition;

/**
 * ����������ʽ���ṩis null,is not null�����
 *
 * @author zouxuemo
 */
public class SimpleExpression extends Expression {
	private static String CONDITION_ISNULL = "is null";
	private static String CONDITION_ISNOTNULL = "is not null";
	
	private String name;
	
	private String op;
	
	private SimpleExpression(String name, String op) {
		this.name = name;
		this.op = op;
	}

	@Override
	public String hql() {
		return name + " " + op;
	}

	@Override
	public Object[] params() {
		return null;
	}

	@Override
	protected int type() {
		return TYPE_CONDITION;
	}
	
	/**
	 * ����is null�����
	 * 
	 * @return
	 */
	public static SimpleExpression isnull(String name) {
		return new SimpleExpression(name, CONDITION_ISNULL);
	}
	
	/**
	 * ����is not null�����
	 * 
	 * @return
	 */
	public static SimpleExpression notnull(String name) {
		return new SimpleExpression(name, CONDITION_ISNOTNULL);
	}
}
