package com.lily.dap.dao.condition;


/**
 * IN��������ʽ���ṩin��not in�����
 *
 * @author zouxuemo
 */
public class InExpression extends Expression {
	private static String CONDITION_IN = "in";
	private static String CONDITION_NOTIN = "not in";
	
	private String name;
	
	private String op;
	
	private Object [] values;
	
	private InExpression(String name, String op, Object [] values) {
		this.name = name;
		this.op = op;
		this.values = values;
	}
	
	@Override
	public String hql() {
		StringBuffer buf = new StringBuffer();
		buf.append(name);
		buf.append(" ");
		buf.append(op);
		buf.append(" (?");
		
		int count = values.length - 1;
		while (count-- > 0)
			buf.append(", ?");
		
		buf.append(")");
		
		return buf.toString();
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
	 * ����in�����
	 * 
	 * @return
	 */
	public static InExpression in(String name, Object... value) {
		return new InExpression(name, CONDITION_IN, value);
	}
	
	/**
	 * ����not in�����
	 * 
	 * @return
	 */
	public static InExpression notin(String name, Object... value) {
		return new InExpression(name, CONDITION_NOTIN, value);
	}
}
