package com.lily.dap.dao.condition;

/**
 * ×óÀ¨»¡ÔËËã·û
 * 
 *
 * @author zouxuemo
 */
public class LeftParenthesesExpression extends Expression {
	private static String EXPRESSION_LEFT_PARENTHESES = "(";

	@Override
	public String hql() {
		return EXPRESSION_LEFT_PARENTHESES;
	}

	@Override
	public Object[] params() {
		return null;
	}

	@Override
	protected int type() {
		return TYPE_LEFT_PARENTHESES;
	}
	
	public static LeftParenthesesExpression lp() {
		return new LeftParenthesesExpression();
	}
}
