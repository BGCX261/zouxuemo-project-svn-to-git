package com.lily.dap.web.util;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.lily.dap.dao.Condition;
import com.lily.dap.util.convert.ConvertUtils;

/**
 * ��HttpServletRequest��ȡ��ѯ������������������ҳ��Ϣ���ɴ����Condition����Ĺ�����
 *
 * @author zouxuemo
 */
public class ConditionHelper {
	public static String PARAMETER_CONDITION_PREFIX = "condition.";

	public static String PARAMETER_ORDER = "order";

	public static String PARAMETER_SORT = "sort";

	public static String PARAMETER_DIR = "dir";

	public static String PARAMETER_START = "start";

	public static String PARAMETER_LIMIT = "limit";

	public static String PARAMETER_PAGE_NO = "pageNo";

	public static String PARAMETER_PAGE_SIZE = "pageSize";
	
	public static String PARAMETER_TYPE_STRING = "string";
	
	public static String PARAMETER_TYPE_INTEGER = "int";
	
	public static String PARAMETER_TYPE_LONG = "long";
	
	public static String PARAMETER_TYPE_DOUBLE = "double";
	
	public static String PARAMETER_TYPE_DATE = "date";
	
	public static Condition fillCondition(Condition condition, Class<?> clazz, HttpServletRequest request, ConditionParseCallback callback) {
		Map<String, String> params = WebUtils.getParametersStartingWith(request, PARAMETER_CONDITION_PREFIX);
		if (callback != null)
			callback.preProcessCondition(params);
		
		return fillCondition(condition, clazz, params);
	}
	
	@SuppressWarnings("deprecation")
	public static Condition fillCondition(Condition condition, Class<?> clazz, Map<String, String> params) {
		/*
		 * ���ݵĲ�����condition.��Ϊǰ׺�������ѯ�������������Ҫ����ѯ��������ɸ���ѯ����������֮����"."�ָ������磺condition.field1.gt��condition.field2�����δָ����ѯ������Ĭ���ǵ��ڲ�ѯ
		 * ����ֵ������ת����Bean�ж�Ӧ���ֶ����ͣ��������ת��������ԣ������in��notin��������ֵ��������","�ָ��Ķ��ֵ�������between��notbetween��������ֵ��������","�ָ�������ֵ
		 */
		
		/*
		 * ��չ������field���ݶ��Ա�ʵ��sql���ʹ��Condition��֧��
		 * field��ʽ���£�����"[]"������Ϊ��ѡ
		 * 	[����$]�ֶ���[#�ֶ�����]�����磺module$create_date#date
		 * 		1.���� - ���sql���Ϊ����ѯ��������Ĳ�ѯ����������Ҫָ����ѯ�ֶ������Ǹ���
		 * 		2.�ֶ��� - ��ѯ�ֶ���
		 * 		3.�ֶ����� - ��Ϊsql���޷�������ѯ�ֶε����ͣ���Ҫ�ڴ�ָ���������ָ������Ĭ���ֶ�����Ϊ�ַ�������
		 * 
		 * ���¸���һ����ѯ�����������������£�
		 * 	condition.module$create_date#date.gt
		 */
		if (condition == null)
			condition = Condition.create();
		
		for (String paramName : params.keySet()) {
			String value = params.get(paramName);
			if (value == null || "".equals(value) || Condition.IGNORE.equals(value))
				continue;
			
			if (Condition.EMPTY.equals(value))
				value = "";
			else
				value = value.trim();
			
			String[] tmp = paramName.split("\\.");
			String name = tmp[0], op = Condition.CONDITION_EQ;
			if (tmp.length > 1)
				op = tmp[1].toLowerCase();

//			if ("".equals(value) && !Condition.CONDITION_EQ.equals(op) && !Condition.CONDITION_NE.equals(op) && !Condition.CONDITION_NOTLIKE.equals(op))
//				continue;

			Class<?> type;
			if (clazz != null) {		//hql�Ĳ�ѯ��������
				PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(clazz, name);
				if (pd == null)
					throw new RuntimeException("��ѯ�����ֶ�[" + name +"]δ�ҵ���");
				
				type = pd.getPropertyType();
			} else {					//sql�Ĳ�ѯ��������
				String typeName = PARAMETER_TYPE_STRING;
				
				int pos = name.indexOf('#');
				if (pos > 0) {
					typeName = name.substring(pos + 1);
					name = name.substring(0, pos);
					
					// ���name�Ƿ��в��Ϸ��ַ�����ֹsqlע������
					if (StringUtils.indexOfIgnoreCase(name, " and ") >= 0 ||
						StringUtils.indexOfIgnoreCase(name, " or ") >= 0)
						throw new RuntimeException("��ѯ�����ֶ�[" + name + "]���벻�Ϸ��ַ������ܻᵼ��SQLע�밲ȫ���⣡");
				}
				
				type = typeName2Class(typeName);
			}
			
			//�����ֶεı���ǰ׺
			int pos = name.indexOf('$');
			if (pos > 0) {
				String tableNamePrefix = name.substring(0, pos);
				String fieldName = name.substring(pos + 1);
				
				name = tableNamePrefix + '.' + fieldName;
			}
			 
			if (Condition.CONDITION_EQ.equals(op)) {
				Object v = ConvertUtils.convert(value, type);
				
				condition.eq(name, v);
			} else if (Condition.CONDITION_NE.equals(op)) {
				Object v = ConvertUtils.convert(value, type);
				
				condition.ne(name, v);
			} else if (Condition.CONDITION_GE.equals(op)) {
				Object v = ConvertUtils.convert(value, type);
				
				condition.ge(name, v);
			} else if (Condition.CONDITION_GT.equals(op)) {
				Object v = ConvertUtils.convert(value, type);
				
				condition.gt(name, v);
			} else if (Condition.CONDITION_LE.equals(op)) {
				Object v = ConvertUtils.convert(value, type);
				
				condition.le(name, v);
			} else if (Condition.CONDITION_LT.equals(op)) {
				Object v = ConvertUtils.convert(value, type);
				
				condition.lt(name, v);
			} else if (Condition.CONDITION_LIKE.equals(op)) {
				condition.like(name, value);
			} else if (Condition.CONDITION_LLIKE.equals(op)) {
				condition.llike(name, value);
			} else if (Condition.CONDITION_RLIKE.equals(op)) {
				condition.rlike(name, value);
			} else if (Condition.CONDITION_NOTLIKE.equals(op)) {
				// not likeʱ�������ֵΪ���ַ���������Ϊ�ǲ�ƥ�����е�
				condition.notlike(name, value);
			} else if (Condition.CONDITION_IN.equals(op)) {
				Object[] v = ConvertUtils.convert(value.split(","), type);
				condition.in(name, v);
			} else if (Condition.CONDITION_NOTIN.equals(op)) {
				Object[] v = ConvertUtils.convert(value.split(","), type);
				condition.notin(name, v);
			} else if (Condition.CONDITION_BETWEEN.equals(op)) {
				Object[] v = ConvertUtils.convert(value.split(","), type);
				Assert.isTrue(v.length == 2, "between��ѯ��������ֵ��������Ӧ����2������ֵ");
				
				condition.between(name, v[0], v[1]);
			} else if (Condition.CONDITION_NOTBETWEEN.equals(op)) {
				Object[] v = ConvertUtils.convert(value.split(","), type);
				Assert.isTrue(v.length == 2, "not between��ѯ��������ֵ��������Ӧ����2������ֵ");
				
				condition.notbetween(name, v[0], v[1]);
			} else
				throw new RuntimeException("��ѯ����������[" + op +"]��ʶ��");
		}
		
		return condition;
	}
	
	public static Condition fillOrder(Condition condition, String sort, String dir) {
		if (condition == null)
			condition = Condition.create();
		
		if (sort == null || "".equals(sort))
			return condition;
		
		if (dir == null || !Condition.ORDER_DESC.equals(dir.toLowerCase()))
			condition.asc(sort);
		else
			condition.desc(sort);
		
		return condition;
	}
	
	public static Condition fillOrder(Condition condition, String order) {
		if (condition == null)
			condition = Condition.create();
		
		if (order != null && !"".equals(order)) {
			String[] tmp = order.split(",");
			
			for (String s : tmp) {
				s = s.trim();
				if ("".equals(s))
					continue;
				
				String[] o = s.split("\\.");
				if (o.length == 1 || !Condition.ORDER_DESC.equals(o[1].toLowerCase()))
					condition.asc(o[0]);
				else
					condition.desc(o[0]);
			}
		}

		return condition;
	}
	
	public static Condition fillOrder(Condition condition, HttpServletRequest request) {
		if (condition == null)
			condition = Condition.create();
		
		/*
		 * ������Ϊsort��������ֶΣ�������Ϊdir���������
		 */
		String sort = request.getParameter(PARAMETER_SORT);
		String dir = request.getParameter(PARAMETER_DIR);
		fillOrder(condition, sort, dir);
		
		/*
		 * ������Ϊorder��Ŷ������������֮����","�ָ���ÿ�����������������ֶ������������ֶ�����������֮����"."�ָ������磺field1,field2.desc,field3.asc
		 */
		String order = request.getParameter(PARAMETER_ORDER);
		fillOrder(condition, order);
		
		return condition;
	}
	
	public static Condition fillPage(Condition condition, HttpServletRequest request) {
		if (condition == null)
			condition = Condition.create();

		/*
		 * ������ΪpageSize���ҳ�ߴ磬������ΪpageNo��Ŵ�1��ʼ��ҳ��
		 * ������Ϊstart�����ʼ�кţ�������Ϊlimit��ż�¼�������
		 */
		String pageSize = request.getParameter(PARAMETER_PAGE_SIZE);
		String pageNo = request.getParameter(PARAMETER_PAGE_NO);
		String start = request.getParameter(PARAMETER_START);
		String limit = request.getParameter(PARAMETER_LIMIT);
		
		if (pageSize != null && !"".equals(pageSize) &&
				pageNo != null && !"".equals(pageNo)) {
			int size = Integer.parseInt(pageSize);
			int no = Integer.parseInt(pageNo);
			
			condition.page(no, size);
		} else if (start != null && !"".equals(start) &&
				limit != null && !"".equals(limit)) {
			int s = Integer.parseInt(start);
			int l = Integer.parseInt(limit);
			
			condition.limit(s, l);
		}
		
		return condition;
	}
	
	private static Class<?> typeName2Class(String typeName) {
		if (PARAMETER_TYPE_DOUBLE.equals(typeName))
			return Double.class;
		else if (PARAMETER_TYPE_INTEGER.equals(typeName))
			return Integer.class;
		else if (PARAMETER_TYPE_LONG.equals(typeName))
			return Long.class;
		else if (PARAMETER_TYPE_DATE.equals(typeName))
			return Date.class;
		else
			return String.class;
	}
}
