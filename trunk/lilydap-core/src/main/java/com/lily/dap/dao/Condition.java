package com.lily.dap.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.lily.dap.dao.condition.BetweenExpression;
import com.lily.dap.dao.condition.Expression;
import com.lily.dap.dao.condition.InExpression;
import com.lily.dap.dao.condition.LeftParenthesesExpression;
import com.lily.dap.dao.condition.LogicalExpression;
import com.lily.dap.dao.condition.NormalExpression;
import com.lily.dap.dao.condition.NotExpression;
import com.lily.dap.dao.condition.NullExpression;
import com.lily.dap.dao.condition.Order;
import com.lily.dap.dao.condition.QueryExpression;
import com.lily.dap.dao.condition.RightParenthesesExpression;
import com.lily.dap.dao.condition.SimpleExpression;

/**
 * �ṩ��ѯ������������������ҳ��Ϣ�������࣬��dao�ӿڵ�query�ȷ���ʹ��
 * 
 * @author zouxuemo
 */
public class Condition {
	public static String CONDITION_EQ = "eq";
	public static String CONDITION_NE = "ne";
	public static String CONDITION_GE = "ge";
	public static String CONDITION_GT = "gt";
	public static String CONDITION_LE = "le";
	public static String CONDITION_LT = "lt";
	public static String CONDITION_LIKE = "like";
	public static String CONDITION_LLIKE = "llike";
	public static String CONDITION_RLIKE = "rlike";
	public static String CONDITION_NOTLIKE = "notlike";
	public static String CONDITION_IN = "in";
	public static String CONDITION_NOTIN = "notin";
	public static String CONDITION_BETWEEN = "between";
	public static String CONDITION_NOTBETWEEN = "notbetween";
	public static String CONDITION_ISNULL = "isnull";
	public static String CONDITION_NOTNULL = "notnull";
	public static String CONDITION_INQUERY = "inquery";
	public static String CONDITION_NOTINQUERY = "notinquery";
	
	public static String ORDER_ASC = "asc";
	public static String ORDER_DESC = "desc";
	
	/**   
	 * IGNORE:���������ѯ����<br>
	 * <br>
	 * �����ϣ��Ժ�ǰ̨���˿��ַ�������Ϊ��Ҫ���Ը������������Ҫ��ѯ����ֵΪ���ַ�����������ֵ��������ΪEMPTY����ֵ
	 */
	@Deprecated
	public static String IGNORE = "$ignore$";
	
	/**
	 * �����������ֵ����Ϊ�ǲ�ѯ����ֵΪ���ַ���
	 */
	public static String EMPTY = "$empty$";
	
	private List<Expression> conditions = new ArrayList<Expression>();
	
	private List<Order> orders = new ArrayList<Order>();
	
	private int start = 0;
	
	private int limit = 0;
	
	public Condition() {
		conditions.add(new NullExpression());
	}
	
	/**
	 * ��̬������new������Condition����
	 * 
	 * @return
	 */
	public static Condition create() {
		return new Condition();
	}
	
	/**
	 * ����and���ӷ�
	 * 
	 * @return
	 */
	public Condition and() {
		conditions.add(LogicalExpression.and());
		
		return this;
	}
	
	/**
	 * ����or���ӷ�
	 * 
	 * @return
	 */
	public Condition or() {
		conditions.add(LogicalExpression.or());
		
		return this;
	}
	
	/**
	 * ����not������
	 * 
	 * @return
	 */
	public Condition not() {
		conditions.add(NotExpression.not());
		
		return this;
	}
	
	/**
	 * ������Բ����
	 * 
	 * @return
	 */
	public Condition lp() {
		conditions.add(LeftParenthesesExpression.lp());
		
		return this;
	}
	
	/**
	 * ������Բ����
	 * 
	 * @return
	 */
	public Condition rp() {
		conditions.add(RightParenthesesExpression.rp());
		
		return this;
	}
	
	/**
	 * ������ڲ�ѯ����
	 * 
	 * @return
	 */
	public Condition gt(String name, Object value) {
		Assert.hasText(name, "name����Ϊ��");
		
		if (value != null)
			conditions.add(NormalExpression.gt(name, value));
		
		return this;
	}
	
	/**
	 * ������ڵ��ڲ�ѯ����
	 * 
	 * @return
	 */
	public Condition ge(String name, Object value) {
		Assert.hasText(name, "name����Ϊ��");
		
		if (value != null)
			conditions.add(NormalExpression.ge(name, value));
		
		return this;
	}
	
	/**
	 * ����С�ڲ�ѯ����
	 * 
	 * @return
	 */
	public Condition lt(String name, Object value) {
		Assert.hasText(name, "name����Ϊ��");
		
		if (value != null)
			conditions.add(NormalExpression.lt(name, value));
		
		return this;
	}
	
	/**
	 * ����С�ڵ��ڲ�ѯ����
	 * 
	 * @return
	 */
	public Condition le(String name, Object value) {
		Assert.hasText(name, "name����Ϊ��");
		
		if (value != null)
			conditions.add(NormalExpression.le(name, value));
		
		return this;
	}
	
	/**
	 * ������ڲ�ѯ����
	 * 
	 * @return
	 */
	public Condition eq(String name, Object value) {
		Assert.hasText(name, "name����Ϊ��");
		
		if (value != null)
			conditions.add(NormalExpression.eq(name, value));
		
		return this;
	}
	
	/**
	 * ������ڲ�ѯ����
	 * 
	 * @return
	 */
	public Condition eq(String[] names, Object[] values) {
		if (names == null || values == null)
			return this;
		
		Assert.isTrue(names != null && values != null && names.length == values.length, 
				"names�е��ֶ�������������values�е��ֶ�ֵ�������");
		
		for (int i = 0; i < names.length; i++) 
			eq(names[i], values[i]);
		
		return this;
	}
	
	/**
	 * ���벻���ڲ�ѯ����
	 * 
	 * @return
	 */
	public Condition ne(String name, Object value) {
		Assert.hasText(name, "name����Ϊ��");
		
		if (value != null)
			conditions.add(NormalExpression.ne(name, value));
		
		return this;
	}
	
	/**
	 * ��������ƥ���ѯ����
	 * 
	 * @return
	 */
	public Condition like(String name, Object value) {
		Assert.hasText(name, "name����Ϊ��");
		
		if (value != null)
			conditions.add(NormalExpression.like(name, value));
		
		return this;
	}
	
	/**
	 * ������ƥ���ѯ����
	 * 
	 * @return
	 */
	public Condition llike(String name, Object value) {
		Assert.hasText(name, "name����Ϊ��");
		
		if (value != null)
			conditions.add(NormalExpression.llike(name, value));
		
		return this;
	}
	
	/**
	 * ������ƥ���ѯ����
	 * 
	 * @return
	 */
	public Condition rlike(String name, Object value) {
		Assert.hasText(name, "name����Ϊ��");
		
		if (value != null)
			conditions.add(NormalExpression.rlike(name, value));
		
		return this;
	}
	
	/**
	 * �������߶���ƥ���ѯ����
	 * 
	 * @return
	 */
	public Condition notlike(String name, Object value) {
		Assert.hasText(name, "name����Ϊ��");
		
		if (value != null)
			conditions.add(NormalExpression.notlike(name, value));
		
		return this;
	}
	
	/**
	 * ����in��ѯ����
	 * 
	 * @return
	 */
	public Condition in(String name, Object... value) {
		Assert.hasText(name, "name����Ϊ��");
		
		if (value != null && value.length > 0)
			conditions.add(InExpression.in(name, value));
		
		return this;
	}
	
	/**
	 * ����not in��ѯ����
	 * 
	 * @return
	 */
	public Condition notin(String name, Object... value) {
		Assert.hasText(name, "name����Ϊ��");
		
		if (value != null && value.length > 0)
			conditions.add(InExpression.notin(name, value));
		
		return this;
	}
	
	/**
	 * ����between...and...��ѯ����
	 *
	 * @param name �����ֶ���
	 * @param value1 between����ֵ
	 * @param value2 and����ֵ
	 * @return
	 */
	public Condition between(String name, Object value1, Object value2) {
		Assert.hasText(name, "name����Ϊ��");
		Assert.notNull(value1, "beterrnֵ����Ϊ��");
		Assert.notNull(value2, "andֵ����Ϊ��");
		
		conditions.add(BetweenExpression.between(name, value1, value2));
		return this;
	}
	
	/**
	 * ����not between...and...��ѯ����
	 *
	 * @param name �����ֶ���
	 * @param value1 between����ֵ
	 * @param value2 and����ֵ
	 * @return
	 */
	public Condition notbetween(String name, Object value1, Object value2) {
		Assert.hasText(name, "name����Ϊ��");
		Assert.notNull(value1, "beterrnֵ����Ϊ��");
		Assert.notNull(value2, "andֵ����Ϊ��");
		
		conditions.add(BetweenExpression.notbetween(name, value1, value2));
		return this;
	}
	
	/**
	 * ���� IS NULL��ѯ����
	 *
	 * @param name
	 * @return
	 */
	public Condition isnull(String name) {
		Assert.hasText(name, "name����Ϊ��");
		
		conditions.add(SimpleExpression.isnull(name));
		return this;
	}
	
	/**
	 * ���� IS NOT NULL��ѯ����
	 *
	 * @param name
	 * @return
	 */
	public Condition notnull(String name) {
		Assert.hasText(name, "name����Ϊ��");
		
		conditions.add(SimpleExpression.notnull(name));
		return this;
	}
	
	/**
	 * ����IN�Ӳ�ѯ��ѯ����
	 * 
	 * @return
	 */
	public Condition inquery(String name, String subquery) {
		Assert.hasText(name, "name����Ϊ��");
		
		if (subquery != null && subquery.length() > 0)
			conditions.add(QueryExpression.inquery(name, subquery));
		
		return this;
	}
	
	/**
	 * ����NOT IN�Ӳ�ѯ��ѯ����
	 * 
	 * @return
	 */
	public Condition notinquery(String name, String subquery) {
		Assert.hasText(name, "name����Ϊ��");
		
		if (subquery != null && subquery.length() > 0)
			conditions.add(QueryExpression.notinquery(name, subquery));
		
		return this;
	}
	
	/**
	 * ����������������
	 * 
	 * @return
	 */
	public Condition asc(String name) {
		Assert.hasText(name, "name����Ϊ��");

		orders.add(Order.asc(name));
		return this;
	}
	
	/**
	 * ���뵹����������
	 * 
	 * @return
	 */
	public Condition desc(String name) {
		Assert.hasText(name, "name����Ϊ��");

		orders.add(Order.desc(name));
		return this;
	}
	
	/**
	 * ���÷�ҳ��Ϣ��ҳ�ߴ硢ҳ�ţ�
	 * 
	 * @param pageNo ��ʼҳ�ţ���1��ʼ
	 * @param pageSize ÿҳ�ߴ磬���Ϊ0��ʾ����ҳ
	 * @return
	 */
	public Condition page(int pageNo, int pageSize) {
		Assert.isTrue(pageNo >= 0, "page no����Ϊ0��������");
		Assert.isTrue(pageSize >= 0, "page size����Ϊ0��������");
		
		if (pageNo > 0)
			this.start = (pageNo - 1) * pageSize;
		else
			this.start = 0;
    	this.limit = pageSize;
			
		return this;
	}
	
	/**
	 * ���÷�ҳ��Ϣ����ʼ�кźͷ���������
	 * 
	 * @param start ��ʼ�кţ���0��ʼ
	 * @param limit ���Ʒ������������Ϊ0��ʾ����ҳ
	 * @return
	 */
	public Condition limit(int start, int limit) {
		Assert.isTrue(start >= 0, "start����Ϊ0��������");
		Assert.isTrue(limit >= 0, "limit����Ϊ0��������");
		
		this.start = start;
		this.limit = limit;
		
		return this;
	}
	
	public Condition condition(String name, String op, Object... value) {
		if (CONDITION_EQ.equals(op)) {
			checkValuesLength(value, 1, 1);
			
			eq(name, value[0]);
		} else if (CONDITION_NE.equals(op)) {
			checkValuesLength(value, 1, 1);
			
			ne(name, value[0]);
		} else if (CONDITION_GE.equals(op)) {
			checkValuesLength(value, 1, 1);
			
			ge(name, value[0]);
		} else if (CONDITION_GT.equals(op)) {
			checkValuesLength(value, 1, 1);
			
			gt(name, value[0]);
		} else if (CONDITION_LE.equals(op)) {
			checkValuesLength(value, 1, 1);
			
			le(name, value[0]);
		} else if (CONDITION_LT.equals(op)) {
			checkValuesLength(value, 1, 1);
			
			lt(name, value[0]);
		} else if (CONDITION_LIKE.equals(op)) {
			checkValuesLength(value, 1, 1);
			
			like(name, value[0]);
		} else if (CONDITION_LLIKE.equals(op)) {
			checkValuesLength(value, 1, 1);
			
			llike(name, value[0]);
		} else if (CONDITION_RLIKE.equals(op)) {
			checkValuesLength(value, 1, 1);
			
			rlike(name, value[0]);
		} else if (CONDITION_NOTLIKE.equals(op)) {
			checkValuesLength(value, 1, 1);
			
			notlike(name, value[0]);
		} else if (CONDITION_IN.equals(op)) {
			checkValuesLength(value, 1, -1);
			
			in(name, value);
		} else if (CONDITION_NOTIN.equals(op)) {
			checkValuesLength(value, 1, -1);
			
			notin(name, value);
		} else if (CONDITION_BETWEEN.equals(op)) {
			checkValuesLength(value, 2, 2);
			
			between(name, value[0], value[1]);
		} else if (CONDITION_NOTBETWEEN.equals(op)) {
			checkValuesLength(value, 2, 2);
			
			notbetween(name, value[0], value[1]);
		} else if (CONDITION_ISNULL.equals(op)) {
			checkValuesLength(value, -1, 0);
			
			isnull(name);
		} else if (CONDITION_NOTNULL.equals(op)) {
			checkValuesLength(value, -1, 0);
			
			notnull(name);
		} else if (CONDITION_INQUERY.equals(op)) {
			checkValuesLength(value, 1, 1);
			
			inquery(name, value[0].toString());
		} else if (CONDITION_NOTINQUERY.equals(op)) {
			checkValuesLength(value, 1, 1);
			
			notinquery(name, value[0].toString());
		} else
			Assert.isTrue(false, "��ʶ������������ַ���");

		return this;
	}
	
	public void clearCondition() {
		conditions.clear();
	}
	
	public Condition order(String name, String dir) {
		if (ORDER_ASC.equals(dir)) 
			asc(name);
		 else if (ORDER_DESC.equals(dir)) 
			desc(name);
		 else
			 Assert.isTrue(false, "��ʶ����������ַ���");
		
		return this;
	}
	
	public void clearOrder() {
		orders.clear();
	}
	
	private void checkValuesLength(Object[] values, int minLength, int maxLength) {
		Assert.isTrue((minLength  < 0 || values.length >= minLength) && (maxLength  < 0 || values.length <= maxLength), "����ֵ��������");
	}
	
	/********** �ṩ��Condition���������� **********/
	
	public List<Expression> getConditions() {
		return conditions;
	}
	
//	public Expression getCondition(String name, String op) {
//		for (Expression expression : conditions) {
//			
//		}
//	}
	
	public List<Order> getOrders() {
		return orders;
	}

	public int getStart() {
		return start;
	}

	public int getLimit() {
		return limit;
	}
}
