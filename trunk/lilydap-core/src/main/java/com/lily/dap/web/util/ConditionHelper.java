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
 * 从HttpServletRequest读取查询条件、排序条件、分页信息，由此填充Condition对象的工具类
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
		 * 传递的参数以condition.作为前缀，后跟查询条件名，如果需要，查询条件名后可跟查询条件操作，之间用"."分隔，例如：condition.field1.gt、condition.field2。如果未指定查询条件，默认是等于查询
		 * 参数值将试着转换成Bean中对应的字段类型，如果不能转换，则忽略，如果是in、notin操作符，值可以是以","分隔的多个值，如果是between、notbetween操作符，值必须是以","分隔的两个值
		 */
		
		/*
		 * 扩展参数中field数据段以便实现sql语句使用Condition的支持
		 * field格式如下，其中"[]"中内容为可选
		 * 	[表名$]字段名[#字段类型]，例如：module$create_date#date
		 * 		1.表名 - 如果sql语句为多表查询，则给定的查询条件可能需要指定查询字段属于那个表
		 * 		2.字段名 - 查询字段名
		 * 		3.字段类型 - 因为sql中无法给出查询字段的类型，需要在此指定，如果不指定，则默认字段类型为字符串类型
		 * 
		 * 以下给出一个查询条件参数的例子如下：
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
			if (clazz != null) {		//hql的查询条件分析
				PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(clazz, name);
				if (pd == null)
					throw new RuntimeException("查询条件字段[" + name +"]未找到！");
				
				type = pd.getPropertyType();
			} else {					//sql的查询条件分析
				String typeName = PARAMETER_TYPE_STRING;
				
				int pos = name.indexOf('#');
				if (pos > 0) {
					typeName = name.substring(pos + 1);
					name = name.substring(0, pos);
					
					// 检查name是否有不合法字符，防止sql注入问题
					if (StringUtils.indexOfIgnoreCase(name, " and ") >= 0 ||
						StringUtils.indexOfIgnoreCase(name, " or ") >= 0)
						throw new RuntimeException("查询条件字段[" + name + "]输入不合法字符，可能会导致SQL注入安全问题！");
				}
				
				type = typeName2Class(typeName);
			}
			
			//解析字段的表名前缀
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
				// not like时如果条件值为空字符串，则认为是不匹配所有的
				condition.notlike(name, value);
			} else if (Condition.CONDITION_IN.equals(op)) {
				Object[] v = ConvertUtils.convert(value.split(","), type);
				condition.in(name, v);
			} else if (Condition.CONDITION_NOTIN.equals(op)) {
				Object[] v = ConvertUtils.convert(value.split(","), type);
				condition.notin(name, v);
			} else if (Condition.CONDITION_BETWEEN.equals(op)) {
				Object[] v = ConvertUtils.convert(value.split(","), type);
				Assert.isTrue(v.length == 2, "between查询条件参数值数量错误，应该有2个参数值");
				
				condition.between(name, v[0], v[1]);
			} else if (Condition.CONDITION_NOTBETWEEN.equals(op)) {
				Object[] v = ConvertUtils.convert(value.split(","), type);
				Assert.isTrue(v.length == 2, "not between查询条件参数值数量错误，应该有2个参数值");
				
				condition.notbetween(name, v[0], v[1]);
			} else
				throw new RuntimeException("查询条件操作符[" + op +"]不识别！");
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
		 * 参数名为sort存放排序字段，参数名为dir存放排序方向
		 */
		String sort = request.getParameter(PARAMETER_SORT);
		String dir = request.getParameter(PARAMETER_DIR);
		fillOrder(condition, sort, dir);
		
		/*
		 * 参数名为order存放多个排序条件，之间以","分隔，每个排序条件可以是字段名，或者是字段名和排序方向，之间以"."分隔，例如：field1,field2.desc,field3.asc
		 */
		String order = request.getParameter(PARAMETER_ORDER);
		fillOrder(condition, order);
		
		return condition;
	}
	
	public static Condition fillPage(Condition condition, HttpServletRequest request) {
		if (condition == null)
			condition = Condition.create();

		/*
		 * 参数名为pageSize存放页尺寸，参数名为pageNo存放从1开始的页号
		 * 参数名为start存放起始行号，参数名为limit存放记录最大条数
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
