/**
 * 
 */
package com.lily.dap.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import com.lily.dap.dao.Condition;
import com.lily.dap.dao.condition.Expression;
import com.lily.dap.dao.condition.LeftParenthesesExpression;
import com.lily.dap.dao.condition.LogicalExpression;
import com.lily.dap.dao.condition.Order;
import com.lily.dap.dao.condition.RightParenthesesExpression;

/**
 * Condition�������������ȡCondition�еĲ�ѯ������������������ɲ�����ѯHQL��where�Ӿ�<br>
 * ���ص�where�Ӿ��ʽ�����ڣ� where field1 = ? and field2 = ? or ... er by field1, field2 desc, ...<br>
 * ���Conditionû�в�ѯ�������򷵻�null
 *
 * @author zouxuemo
 */
public class ConditionParser {
	private Condition condition = null;
	
	private String hql = null;
	
	private List<Object> params = new ArrayList<Object>();
	
	
	public ConditionParser(Condition condition) {
		this.condition = condition;
	}
	
	public String parse(boolean includeOrder) {
		clear();

		if (condition == null)
			return null;
		
		String whereHQL = null, orderHQL = null;
		if (condition.getConditions().size() > 0) {
			HqlAssembler hqlAssembler = new HqlAssembler(" where ", " ");
			
			int leftParenthesesCount = 0;
			
			Expression prevExpression = null;
			for (Expression expression : condition.getConditions()) {
				if (expression instanceof LeftParenthesesExpression)
					leftParenthesesCount++;
				else if (expression instanceof RightParenthesesExpression)
					leftParenthesesCount--;
				
				boolean expected = true, allow = false;
				if (prevExpression != null) {
					expected = prevExpression.expected(expression);
					if (!expected) {
						allow = prevExpression.allow(expression);
						if (!allow)
							throw new RuntimeException("Condition�߼����󣬴���������˳�򲻷����﷨��");
					}
				}
				
				if (allow) {
					Expression insertExpression = LogicalExpression.and();
					
					hqlAssembler.append(insertExpression.hql());
					
					Object[] appendParams = insertExpression.params();
					if (appendParams != null && appendParams.length > 0) {
						for (Object obj : appendParams)
							params.add(obj);
					}
				}
				
				hqlAssembler.append(expression.hql());
				
				Object[] appendParams = expression.params();
				if (appendParams != null && appendParams.length > 0) {
					for (Object obj : appendParams)
						params.add(obj);
				}
				
				prevExpression = expression;
			}
			
			if (leftParenthesesCount != 0)
				throw new RuntimeException("Condition�߼���������������������ƥ�䣡");
				
			whereHQL = hqlAssembler.toString();
		}
		
		if (includeOrder && condition.getOrders().size() > 0) {
			HqlAssembler hqlAssembler = new HqlAssembler(" order by ", ", ");
			
			for (Order order : condition.getOrders())
				hqlAssembler.append(order.hql());
			
			orderHQL = hqlAssembler.toString();
		}
		
		if (whereHQL != null)
			hql = whereHQL;
		
		if (orderHQL != null) {
			if (whereHQL != null)
				hql += orderHQL;
			else
				hql = orderHQL;
		}
		
		return hql;
	}

	private void clear() {
		hql = null;
		params.clear();
	}
	
	public String hql() {
		return hql;
	}
	
	public Object[] params() {
		return params.toArray(new Object[0]);
	}
}

class HqlAssembler {
	private StringBuffer buf = new StringBuffer();
	
	private boolean first = true;
	
	private String start;
	
	private String connector;
	
	public HqlAssembler(String start, String connector) {
		this.start = start;
		this.connector = connector;
	}
	
	public void append(String fragment) {
		if (fragment == null)
			return;
		
		if (first) {
			buf.append(start);
			
			first = false;
		} else
			buf.append(connector);
		
		buf.append(fragment);
	}
	
	public String toString() {
		return buf.toString();
	}
}
 
