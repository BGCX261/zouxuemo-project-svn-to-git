package com.lily.dap.dao.condition;

/**
 * ”“¿®ª°‘ÀÀ„∑˚
 * 
 *
 * @author zouxuemo
 */
public class RightParenthesesExpression extends Expression {
	private static String EXPRESSION_RIGHT_PARENTHESES = ")";

	@Override
	public String hql() {
		return EXPRESSION_RIGHT_PARENTHESES;
	}

	@Override
	public Object[] params() {
		return null;
	}

	@Override
	protected int type() {
		return TYPE_RIGHT_PARENTHESES;
	}
	
	public static RightParenthesesExpression rp() {
		return new RightParenthesesExpression();
	}
}
