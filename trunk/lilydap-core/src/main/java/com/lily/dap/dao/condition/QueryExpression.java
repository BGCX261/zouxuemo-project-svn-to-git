package com.lily.dap.dao.condition;

/**
 * �Ӳ�ѯ��������ʽ���ṩin��not in�Ӳ�ѯ
 *
 * @author zouxuemo
 */
public class QueryExpression extends Expression {
	private static String SUBQUERY_IN = "in";
	private static String SUBQUER_NOTIN = "not in";
	
	private String name;
	
	private String op;
	
	private String subQuery;
	
	private QueryExpression(String name, String op, String subQuery) {
		this.name = name;
		this.op = op;
		this.subQuery = subQuery;
	}

	@Override
	public String hql() {
		return name + " " + op + " (" + subQuery + ")";
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
	 * ����in query�����
	 * 
	 * @return
	 */
	public static QueryExpression inquery(String name, String subquery) {
		return new QueryExpression(name, SUBQUERY_IN, subquery);
	}
	
	/**
	 * ����not in query�����
	 * 
	 * @return
	 */
	public static QueryExpression notinquery(String name, String subquery) {
		return new QueryExpression(name, SUBQUER_NOTIN, subquery);
	}
}
