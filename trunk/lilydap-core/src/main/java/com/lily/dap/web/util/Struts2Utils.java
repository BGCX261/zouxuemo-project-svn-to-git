/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: Struts2Utils.java 665 2009-11-20 17:47:03Z calvinxiu $
 */
package com.lily.dap.web.util;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
//import org.dozer.CustomFieldMapper;
//import org.dozer.DozerBeanMapper;
//import org.dozer.DozerBeanMapperSingletonWrapper;
//import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.BaseEntity;
import com.lily.dap.util.TransferUtils;
//import com.lily.dap.util.dozer.CustomMapConverter;

/**
 * Struts2 Utils类.
 * 
 * 实现获取Request/Response/Session、获取参数变量、查询你条件、根据参数填充实体对象、以及绕过jsp/
 * freemaker直接输出文本的简化函数.
 * 
 * @author 邹学模
 */
public class Struts2Utils {

	// -- header 常量定义 --//
	private static final String ENCODING_PREFIX = "encoding";
	private static final String NOCACHE_PREFIX = "no-cache";
	private static final String ENCODING_DEFAULT = "UTF-8";
	private static final boolean NOCACHE_DEFAULT = true;

	// -- content-type 常量定义 --//
	private static final String TEXT_TYPE = "text/plain";
	private static final String JSON_TYPE = "application/json";
	private static final String XML_TYPE = "text/xml";
	private static final String HTML_TYPE = "text/html";
	private static final String JS_TYPE = "text/javascript";

	private static Logger logger = LoggerFactory.getLogger(Struts2Utils.class);

//	private static Mapper mapper = null;
	
	// -- 取得Request/Response/Session的简化函数 --//
	/**
	 * 取得HttpSession的简化函数.
	 */
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * 取得HttpRequest的简化函数.
	 */
	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 取得HttpResponse的简化函数.
	 */
	public static HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 返回WEB的ServletContext
	 * 
	 * @return
	 */
	public static ServletContext getServletContext() {
		return ServletActionContext.getRequest().getSession()
				.getServletContext();
	}

	/**
	 * 从请求参数中获取数据，创建并填充给定实体类对象
	 * 
	 * @param entityClass 要填充数据的实体类
	 * @param allowModifyFields 允许设置值的字段列表，只有在这个列表上的字段才可以从请求中设置字段值
	 * @return 返回填充数据后的实体对象
	 */
	public static <T extends BaseEntity> T writeModel(Class<T> entityClass, String[] allowModifyFields) {
		T t = null;
		try {
			t = entityClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("不能创建实体类[" + entityClass.getName()
					+ "]，请检查实体类是否定义默认构造函数、或者默认构造函数是否为公共可访问！", e);
		}

		return writeModel(t, allowModifyFields);
	}

	/**
	 * 从请求参数中获取数据，填充给定的实体对象
	 * 
	 * @param t 要填充数据的实体对象
	 * @param allowModifyFields 允许设置值的字段列表，只有在这个列表上的字段才可以从请求中设置字段值，值可以为空，表示所有字段都允许设置
	 * @return 如果给定实体对象为null，返回null，否则返回填充数据后的实体对象
	 */
	@SuppressWarnings("unchecked")
	public static <T extends BaseEntity> T writeModel(T t, String[] allowModifyFields) {
		if (t != null) {
			Map<String, String> parameters = new HashMap<String, String>();

			HttpServletRequest request = getRequest();
			Enumeration enumeration = request.getParameterNames();
			while (enumeration.hasMoreElements()) {
				String paramName = (String) enumeration.nextElement();
				if (allowModifyFields != null && !ArrayUtils.contains(allowModifyFields, paramName))
					continue;
				
				String paramValue = request.getParameter(paramName);
				parameters.put(paramName, paramValue);
			}
			
//			getMapper().map(parameters, t);

			String[] rules = parameters.keySet().toArray(new String[0]);
			TransferUtils.copy(rules, parameters, t, null);
		}

		return t;
	}
//	
//	private synchronized static Mapper getMapper() {
//		if (mapper == null) {
//			mapper = DozerBeanMapperSingletonWrapper.getInstance();
//			
//	  		CustomFieldMapper customFieldMapper = new CustomMapConverter();
//			((DozerBeanMapper) mapper).setCustomFieldMapper(customFieldMapper);
//		}
//		
//		return mapper;
//	}
	
	/**
	 * 从HttpServletRequest中获取查询条件、排序条件、分页信息，根据这些信息创建Condition对象<br>
	 * HttpServletRequest传递的参数以condition.作为前缀，后跟查询条件名，如果需要，查询条件名后可跟查询条件操作，之间用"."分隔，例如：condition.field1.gt、condition.field2。如果未指定查询条件，默认是等于查询<br>
	 * <br>
	 * 如果给定了clazz参数，则参数值将试着转换成Bean中对应的字段类型，如果不能转换，则忽略，如果是in、notin操作符，值可以是以","分隔的多个值，如果是between、notbetween操作符，值必须是以","分隔的两个值<br>
	 * 如果未给定clazz参数，则要求在查询字段名后指定字段类型<br>
	 * <br>
	 * 扩展参数中field数据段以便实现sql语句使用Condition的支持<br>
	 * field格式如下，其中"[]"中内容为可选<br>
	 * 	&nbsp;[表名$]字段名[#字段类型]，例如：module$create_date#date<br>
	 * 		&nbsp;&nbsp;1.表名 - 如果sql语句为多表查询，则给定的查询条件可能需要指定查询字段属于那个表<br>
	 * 		&nbsp;&nbsp;2.字段名 - 查询字段名<br>
	 * 		&nbsp;&nbsp;3.字段类型 - 因为sql中无法给出查询字段的类型，需要在此指定，如果不指定，则默认字段类型为字符串类型<br>
	 * <br>
	 * 以下给出一个查询条件参数的例子如下：<br>
	 * 	&nbsp;condition.module$create_date#date.gt<br>
	 * 
	 * @param clazz 提供查询字段的字段类型，如果为null，则要求在HttpServletRequest请求参数中定义字段类型
	 * @return
	 */
	public static Condition buildCondition(Class<?> clazz) {
		return buildCondition(clazz, null);
	}
	
	/**
	 * 从HttpServletRequest中获取查询条件、排序条件、分页信息，根据这些信息创建Condition对象<br>
	 * HttpServletRequest传递的参数以condition.作为前缀，后跟查询条件名，如果需要，查询条件名后可跟查询条件操作，之间用"."分隔，例如：condition.field1.gt、condition.field2。如果未指定查询条件，默认是等于查询<br>
	 * <br>
	 * 如果给定了clazz参数，则参数值将试着转换成Bean中对应的字段类型，如果不能转换，则忽略，如果是in、notin操作符，值可以是以","分隔的多个值，如果是between、notbetween操作符，值必须是以","分隔的两个值<br>
	 * 如果未给定clazz参数，则要求在查询字段名后指定字段类型<br>
	 * <br>
	 * 扩展参数中field数据段以便实现sql语句使用Condition的支持<br>
	 * field格式如下，其中"[]"中内容为可选<br>
	 * 	&nbsp;[表名$]字段名[#字段类型]，例如：module$create_date#date<br>
	 * 		&nbsp;&nbsp;1.表名 - 如果sql语句为多表查询，则给定的查询条件可能需要指定查询字段属于那个表<br>
	 * 		&nbsp;&nbsp;2.字段名 - 查询字段名<br>
	 * 		&nbsp;&nbsp;3.字段类型 - 因为sql中无法给出查询字段的类型，需要在此指定，如果不指定，则默认字段类型为字符串类型<br>
	 * <br>
	 * 以下给出一个查询条件参数的例子如下：<br>
	 * 	&nbsp;condition.module$create_date#date.gt<br>
	 * 
	 * 
	 * @param clazz 提供查询字段的字段类型，如果为null，则要求在HttpServletRequest请求参数中定义字段类型
	 * @param callback
	 * @return
	 */
	public static Condition buildCondition(Class<?> clazz, ConditionParseCallback callback) {
		HttpServletRequest request = getRequest();
		
		Condition condition = Condition.create();
		ConditionHelper.fillCondition(condition, clazz, request, callback);
		ConditionHelper.fillOrder(condition, request);
		ConditionHelper.fillPage(condition, request);
		
		return condition;
	}
	
	/**
	 * 返回给定前缀的参数名-值Map，其中参数名将是去除前缀的参数名，如果前缀为null或者空字符串，则返回所有的参数名-值Map<br>
	 * 例如：request存放了{"condition.field1" : "value1"}参数，当调用getParameters("condition.")后将返回{"field1" : "value1"}
	 * 
	 * @param prefix 参数前缀，允许为null或空字符串
	 * @return
	 */
	public static Map<String, String> getParameters(String prefix) {
		HttpServletRequest request = getRequest();

		return WebUtils.getParametersStartingWith(request, prefix);
	}

	/**
	 * Gets a parameter as a string.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @return The value of the parameter or null if the parameter was not found
	 *         or if the parameter is a zero-length string.
	 */
	public static String getParameter(String name) {
		// return getParameter(name, false);
		return getParameter_Ext(name, false);
	}

	/**
	 * Gets a parameter as a string.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @param emptyStringsOK
	 *            Return the parameter values even if it is an empty string.
	 * @return The value of the parameter or null if the parameter was not
	 *         found.
	 */
	public static String getParameter(String name, boolean emptyStringsOK) {
		HttpServletRequest request = getRequest();

		String temp = request.getParameter(name);
		if (temp != null) {
			if (temp.equals("") && !emptyStringsOK) {
				return null;
			} else {
				return temp;
			}
		} else {
			return null;
		}
	}

	public static String getParameter_Ext(String name, boolean NULLStringOK) {
		HttpServletRequest request = getRequest();

		String temp = request.getParameter(name);
		if (temp == null) {
			if (!NULLStringOK) {
				return "";
			} else {
				return null;
			}
		} else {
			return temp;
		}
	}

	/**
	 * Gets a parameter as a boolean.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @return True if the value of the parameter was "true", false otherwise.
	 */
	public static boolean getBooleanParameter(String name) {
		return getBooleanParameter(name, false);
	}

	/**
	 * Gets a parameter as a boolean.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @return True if the value of the parameter was "true", false otherwise.
	 */
	public static boolean getBooleanParameter(String name, boolean defaultVal) {
		HttpServletRequest request = getRequest();

		String temp = request.getParameter(name);
		if ("true".equals(temp) || "on".equals(temp)) {
			return true;
		} else if ("false".equals(temp) || "off".equals(temp)) {
			return false;
		} else {
			return defaultVal;
		}
	}

	/**
	 * Gets a parameter as an int.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @return The int value of the parameter specified or the default value if
	 *         the parameter is not found.
	 */
	public static int getIntParameter(String name, int defaultNum) {
		HttpServletRequest request = getRequest();

		String temp = request.getParameter(name);
		if (temp != null && !temp.equals("")) {
			int num = defaultNum;
			try {
				num = Integer.parseInt(temp.trim());
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	/**
	 * Gets a list of int parameters.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @param defaultNum
	 *            The default value of a parameter, if the parameter can't be
	 *            converted into an int.
	 */
	public static int[] getIntParameters(String name, int defaultNum) {
		HttpServletRequest request = getRequest();

		String[] paramValues = request.getParameterValues(name);
		if (paramValues == null) {
			return null;
		}
		if (paramValues.length < 1) {
			return new int[0];
		}
		int[] values = new int[paramValues.length];
		for (int i = 0; i < paramValues.length; i++) {
			try {
				values[i] = Integer.parseInt(paramValues[i].trim());
			} catch (Exception e) {
				values[i] = defaultNum;
			}
		}
		return values;
	}

	/**
	 * Gets a parameter as a double.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @return The double value of the parameter specified or the default value
	 *         if the parameter is not found.
	 */
	public static double getDoubleParameter(String name, double defaultNum) {
		HttpServletRequest request = getRequest();

		String temp = request.getParameter(name);
		if (temp != null && !temp.equals("")) {
			double num = defaultNum;
			try {
				num = Double.parseDouble(temp.trim());
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	/**
	 * Gets a parameter as a long.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @return The long value of the parameter specified or the default value if
	 *         the parameter is not found.
	 */
	public static long getLongParameter(String name, long defaultNum) {
		HttpServletRequest request = getRequest();

		String temp = request.getParameter(name);
		if (temp != null && !temp.equals("")) {
			long num = defaultNum;
			try {
				num = Long.parseLong(temp.trim());
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	/**
	 * Gets a list of long parameters.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @param defaultNum
	 *            The default value of a parameter, if the parameter can't be
	 *            converted into a long.
	 */
	public static long[] getLongParameters(String name, long defaultNum) {
		HttpServletRequest request = getRequest();

		String[] paramValues = request.getParameterValues(name);
		if (paramValues == null) {
			return null;
		}
		if (paramValues.length < 1) {
			return new long[0];
		}
		long[] values = new long[paramValues.length];
		for (int i = 0; i < paramValues.length; i++) {
			try {
				values[i] = Long.parseLong(paramValues[i].trim());
			} catch (Exception e) {
				values[i] = defaultNum;
			}
		}
		return values;
	}

	/**
	 * Gets a parameter as a string.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @return The value of the parameter or null if the parameter was not found
	 *         or if the parameter is a zero-length string.
	 */
	public static String getAttribute(String name) {
		return getAttribute(name, false);
	}

	/**
	 * Gets a parameter as a string.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the parameter you want to get
	 * @param emptyStringsOK
	 *            Return the parameter values even if it is an empty string.
	 * @return The value of the parameter or null if the parameter was not
	 *         found.
	 */
	public static String getAttribute(String name, boolean emptyStringsOK) {
		HttpServletRequest request = getRequest();

		String temp = (String) request.getAttribute(name);
		if (temp != null) {
			if (temp.equals("") && !emptyStringsOK) {
				return null;
			} else {
				return temp;
			}
		} else {
			return null;
		}
	}

	/**
	 * Gets an attribute as a boolean.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the attribute you want to get
	 * @return True if the value of the attribute is "true", false otherwise.
	 */
	public static boolean getBooleanAttribute(String name) {
		HttpServletRequest request = getRequest();

		String temp = (String) request.getAttribute(name);
		if (temp != null && temp.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets an attribute as a int.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the attribute you want to get
	 * @return The int value of the attribute or the default value if the
	 *         attribute is not found or is a zero length string.
	 */
	public static int getIntAttribute(String name, int defaultNum) {
		HttpServletRequest request = getRequest();

		String temp = (String) request.getAttribute(name);
		if (temp != null && !temp.equals("")) {
			int num = defaultNum;
			try {
				num = Integer.parseInt(temp.trim());
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	/**
	 * Gets an attribute as a long.
	 * 
	 * @param request
	 *            The HttpServletRequest object, known as "request" in a JSP
	 *            page.
	 * @param name
	 *            The name of the attribute you want to get
	 * @return The long value of the attribute or the default value if the
	 *         attribute is not found or is a zero length string.
	 */
	public static long getLongAttribute(String name, long defaultNum) {
		HttpServletRequest request = getRequest();

		String temp = (String) request.getAttribute(name);
		if (temp != null && !temp.equals("")) {
			long num = defaultNum;
			try {
				num = Long.parseLong(temp.trim());
			} catch (Exception ignored) {
			}
			return num;
		} else {
			return defaultNum;
		}
	}

	// -- 绕过jsp/freemaker直接输出文本的函数 --//
	/**
	 * 直接输出内容的简便函数.
	 * 
	 * eg. render("text/plain", "hello", "encoding:GBK"); render("text/plain",
	 * "hello", "no-cache:false"); render("text/plain", "hello", "encoding:GBK",
	 * "no-cache:false");
	 * 
	 * @param headers
	 *            可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
	 */
	public static void render(final String contentType, final String content,
			final String... headers) {
		try {
			// 分析headers参数
			String encoding = ENCODING_DEFAULT;
			boolean noCache = NOCACHE_DEFAULT;
			for (String header : headers) {
				String headerName = StringUtils.substringBefore(header, ":");
				String headerValue = StringUtils.substringAfter(header, ":");

				if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
					encoding = headerValue;
				} else if (StringUtils.equalsIgnoreCase(headerName,
						NOCACHE_PREFIX)) {
					noCache = Boolean.parseBoolean(headerValue);
				} else
					throw new IllegalArgumentException(headerName
							+ "不是一个合法的header类型");
			}

			HttpServletResponse response = ServletActionContext.getResponse();

			// 设置headers参数
			String fullContentType = contentType + ";charset=" + encoding;
			response.setContentType(fullContentType);
			if (noCache) {
				WebUtils.setNoCacheHeader(response);
			}

			response.getWriter().write(content);
			response.getWriter().flush();

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 直接输出文本.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderText(final String text, final String... headers) {
		render(TEXT_TYPE, text, headers);
	}

	/**
	 * 直接输出HTML.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderHtml(final String html, final String... headers) {
		render(HTML_TYPE, html, headers);
	}

	/**
	 * 直接输出XML.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderXml(final String xml, final String... headers) {
		render(XML_TYPE, xml, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param jsonString
	 *            json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final String jsonString,
			final String... headers) {
		render(JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param map
	 *            Map对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	@SuppressWarnings("unchecked")
	public static void renderJson(final Map map, final String... headers) {
		String jsonString = JsonHelper.formatObjectToJsonString(map);
		
		render(JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param object
	 *            Java对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Object object, final String... headers) {
		String jsonString = JsonHelper.formatObjectToJsonString(object);
		
		render(JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param collection
	 *            Java对象集合, 将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Collection<?> collection,
			final String... headers) {
		String jsonString = JsonHelper.formatObjectToJsonString(collection);
		
		render(JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param array
	 *            Java对象数组, 将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Object[] array, final String... headers) {
		String jsonString = JsonHelper.formatObjectToJsonString(array);
		
		render(JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出支持跨域Mashup的JSONP.
	 * 
	 * @param callbackName
	 *            callback函数名.
	 * @param contentMap
	 *            Map对象,将被转化为json字符串.
	 * @see #render(String, String, String...)
	 */
	@SuppressWarnings("unchecked")
	public static void renderJsonp(final String callbackName,
			final Map contentMap, final String... headers) {
		String jsonParam = JsonHelper.formatObjectToJsonString(contentMap);

		StringBuilder result = new StringBuilder().append(callbackName).append(
				"(").append(jsonParam).append(");");

		// 渲染Content-Type为javascript的返回内容,输出结果为javascript语句,
		// 如callback197("{content:'Hello World!!!'}");
		render(JS_TYPE, result.toString(), headers);
	}
}
