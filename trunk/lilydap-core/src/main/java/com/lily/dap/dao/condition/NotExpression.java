package com.lily.dap.dao.condition;

/**
 * Not–ﬁ Œ‘ÀÀ„∑˚
 * 
 * @author zouxuemo
 */
public class NotExpression extends Expression {
	private static String EXPRESSION_NOT = "not";

	@Override
	public String hql() {
		return EXPRESSION_NOT;
	}

	@Override
	public Object[] params() {
		return null;
	}

	@Override
	protected int type() {
		return TYPE_MODIFIED_CONDITION;
	}
	
	public static NotExpression not() {
		return new NotExpression();
	}
}
