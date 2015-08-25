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
 * Struts2 Utils��.
 * 
 * ʵ�ֻ�ȡRequest/Response/Session����ȡ������������ѯ�����������ݲ������ʵ������Լ��ƹ�jsp/
 * freemakerֱ������ı��ļ򻯺���.
 * 
 * @author ��ѧģ
 */
public class Struts2Utils {

	// -- header �������� --//
	private static final String ENCODING_PREFIX = "encoding";
	private static final String NOCACHE_PREFIX = "no-cache";
	private static final String ENCODING_DEFAULT = "UTF-8";
	private static final boolean NOCACHE_DEFAULT = true;

	// -- content-type �������� --//
	private static final String TEXT_TYPE = "text/plain";
	private static final String JSON_TYPE = "application/json";
	private static final String XML_TYPE = "text/xml";
	private static final String HTML_TYPE = "text/html";
	private static final String JS_TYPE = "text/javascript";

	private static Logger logger = LoggerFactory.getLogger(Struts2Utils.class);

//	private static Mapper mapper = null;
	
	// -- ȡ��Request/Response/Session�ļ򻯺��� --//
	/**
	 * ȡ��HttpSession�ļ򻯺���.
	 */
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * ȡ��HttpRequest�ļ򻯺���.
	 */
	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * ȡ��HttpResponse�ļ򻯺���.
	 */
	public static HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * ����WEB��ServletContext
	 * 
	 * @return
	 */
	public static ServletContext getServletContext() {
		return ServletActionContext.getRequest().getSession()
				.getServletContext();
	}

	/**
	 * ����������л�ȡ���ݣ�������������ʵ�������
	 * 
	 * @param entityClass Ҫ������ݵ�ʵ����
	 * @param allowModifyFields ��������ֵ���ֶ��б�ֻ��������б��ϵ��ֶβſ��Դ������������ֶ�ֵ
	 * @return ����������ݺ��ʵ�����
	 */
	public static <T extends BaseEntity> T writeModel(Class<T> entityClass, String[] allowModifyFields) {
		T t = null;
		try {
			t = entityClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("���ܴ���ʵ����[" + entityClass.getName()
					+ "]������ʵ�����Ƿ���Ĭ�Ϲ��캯��������Ĭ�Ϲ��캯���Ƿ�Ϊ�����ɷ��ʣ�", e);
		}

		return writeModel(t, allowModifyFields);
	}

	/**
	 * ����������л�ȡ���ݣ���������ʵ�����
	 * 
	 * @param t Ҫ������ݵ�ʵ�����
	 * @param allowModifyFields ��������ֵ���ֶ��б�ֻ��������б��ϵ��ֶβſ��Դ������������ֶ�ֵ��ֵ����Ϊ�գ���ʾ�����ֶζ���������
	 * @return �������ʵ�����Ϊnull������null�����򷵻�������ݺ��ʵ�����
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
	 * ��HttpServletRequest�л�ȡ��ѯ������������������ҳ��Ϣ��������Щ��Ϣ����Condition����<br>
	 * HttpServletRequest���ݵĲ�����condition.��Ϊǰ׺�������ѯ�������������Ҫ����ѯ��������ɸ���ѯ����������֮����"."�ָ������磺condition.field1.gt��condition.field2�����δָ����ѯ������Ĭ���ǵ��ڲ�ѯ<br>
	 * <br>
	 * ���������clazz�����������ֵ������ת����Bean�ж�Ӧ���ֶ����ͣ��������ת��������ԣ������in��notin��������ֵ��������","�ָ��Ķ��ֵ�������between��notbetween��������ֵ��������","�ָ�������ֵ<br>
	 * ���δ����clazz��������Ҫ���ڲ�ѯ�ֶ�����ָ���ֶ�����<br>
	 * <br>
	 * ��չ������field���ݶ��Ա�ʵ��sql���ʹ��Condition��֧��<br>
	 * field��ʽ���£�����"[]"������Ϊ��ѡ<br>
	 * 	&nbsp;[����$]�ֶ���[#�ֶ�����]�����磺module$create_date#date<br>
	 * 		&nbsp;&nbsp;1.���� - ���sql���Ϊ����ѯ��������Ĳ�ѯ����������Ҫָ����ѯ�ֶ������Ǹ���<br>
	 * 		&nbsp;&nbsp;2.�ֶ��� - ��ѯ�ֶ���<br>
	 * 		&nbsp;&nbsp;3.�ֶ����� - ��Ϊsql���޷�������ѯ�ֶε����ͣ���Ҫ�ڴ�ָ���������ָ������Ĭ���ֶ�����Ϊ�ַ�������<br>
	 * <br>
	 * ���¸���һ����ѯ�����������������£�<br>
	 * 	&nbsp;condition.module$create_date#date.gt<br>
	 * 
	 * @param clazz �ṩ��ѯ�ֶε��ֶ����ͣ����Ϊnull����Ҫ����HttpServletRequest��������ж����ֶ�����
	 * @return
	 */
	public static Condition buildCondition(Class<?> clazz) {
		return buildCondition(clazz, null);
	}
	
	/**
	 * ��HttpServletRequest�л�ȡ��ѯ������������������ҳ��Ϣ��������Щ��Ϣ����Condition����<br>
	 * HttpServletRequest���ݵĲ�����condition.��Ϊǰ׺�������ѯ�������������Ҫ����ѯ��������ɸ���ѯ����������֮����"."�ָ������磺condition.field1.gt��condition.field2�����δָ����ѯ������Ĭ���ǵ��ڲ�ѯ<br>
	 * <br>
	 * ���������clazz�����������ֵ������ת����Bean�ж�Ӧ���ֶ����ͣ��������ת��������ԣ������in��notin��������ֵ��������","�ָ��Ķ��ֵ�������between��notbetween��������ֵ��������","�ָ�������ֵ<br>
	 * ���δ����clazz��������Ҫ���ڲ�ѯ�ֶ�����ָ���ֶ�����<br>
	 * <br>
	 * ��չ������field���ݶ��Ա�ʵ��sql���ʹ��Condition��֧��<br>
	 * field��ʽ���£�����"[]"������Ϊ��ѡ<br>
	 * 	&nbsp;[����$]�ֶ���[#�ֶ�����]�����磺module$create_date#date<br>
	 * 		&nbsp;&nbsp;1.���� - ���sql���Ϊ����ѯ��������Ĳ�ѯ����������Ҫָ����ѯ�ֶ������Ǹ���<br>
	 * 		&nbsp;&nbsp;2.�ֶ��� - ��ѯ�ֶ���<br>
	 * 		&nbsp;&nbsp;3.�ֶ����� - ��Ϊsql���޷�������ѯ�ֶε����ͣ���Ҫ�ڴ�ָ���������ָ������Ĭ���ֶ�����Ϊ�ַ�������<br>
	 * <br>
	 * ���¸���һ����ѯ�����������������£�<br>
	 * 	&nbsp;condition.module$create_date#date.gt<br>
	 * 
	 * 
	 * @param clazz �ṩ��ѯ�ֶε��ֶ����ͣ����Ϊnull����Ҫ����HttpServletRequest��������ж����ֶ�����
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
	 * ���ظ���ǰ׺�Ĳ�����-ֵMap�����в���������ȥ��ǰ׺�Ĳ����������ǰ׺Ϊnull���߿��ַ������򷵻����еĲ�����-ֵMap<br>
	 * ���磺request�����{"condition.field1" : "value1"}������������getParameters("condition.")�󽫷���{"field1" : "value1"}
	 * 
	 * @param prefix ����ǰ׺������Ϊnull����ַ���
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

	// -- �ƹ�jsp/freemakerֱ������ı��ĺ��� --//
	/**
	 * ֱ��������ݵļ�㺯��.
	 * 
	 * eg. render("text/plain", "hello", "encoding:GBK"); render("text/plain",
	 * "hello", "no-cache:false"); render("text/plain", "hello", "encoding:GBK",
	 * "no-cache:false");
	 * 
	 * @param headers
	 *            �ɱ��header���飬Ŀǰ���ܵ�ֵΪ"encoding:"��"no-cache:",Ĭ��ֵ�ֱ�ΪUTF-8��true.
	 */
	public static void render(final String contentType, final String content,
			final String... headers) {
		try {
			// ����headers����
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
							+ "����һ���Ϸ���header����");
			}

			HttpServletResponse response = ServletActionContext.getResponse();

			// ����headers����
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
	 * ֱ������ı�.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderText(final String text, final String... headers) {
		render(TEXT_TYPE, text, headers);
	}

	/**
	 * ֱ�����HTML.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderHtml(final String html, final String... headers) {
		render(HTML_TYPE, html, headers);
	}

	/**
	 * ֱ�����XML.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderXml(final String xml, final String... headers) {
		render(XML_TYPE, xml, headers);
	}

	/**
	 * ֱ�����JSON.
	 * 
	 * @param jsonString
	 *            json�ַ���.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final String jsonString,
			final String... headers) {
		render(JSON_TYPE, jsonString, headers);
	}

	/**
	 * ֱ�����JSON.
	 * 
	 * @param map
	 *            Map����,����ת��Ϊjson�ַ���.
	 * @see #render(String, String, String...)
	 */
	@SuppressWarnings("unchecked")
	public static void renderJson(final Map map, final String... headers) {
		String jsonString = JsonHelper.formatObjectToJsonString(map);
		
		render(JSON_TYPE, jsonString, headers);
	}

	/**
	 * ֱ�����JSON.
	 * 
	 * @param object
	 *            Java����,����ת��Ϊjson�ַ���.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Object object, final String... headers) {
		String jsonString = JsonHelper.formatObjectToJsonString(object);
		
		render(JSON_TYPE, jsonString, headers);
	}

	/**
	 * ֱ�����JSON.
	 * 
	 * @param collection
	 *            Java���󼯺�, ����ת��Ϊjson�ַ���.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Collection<?> collection,
			final String... headers) {
		String jsonString = JsonHelper.formatObjectToJsonString(collection);
		
		render(JSON_TYPE, jsonString, headers);
	}

	/**
	 * ֱ�����JSON.
	 * 
	 * @param array
	 *            Java��������, ����ת��Ϊjson�ַ���.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(final Object[] array, final String... headers) {
		String jsonString = JsonHelper.formatObjectToJsonString(array);
		
		render(JSON_TYPE, jsonString, headers);
	}

	/**
	 * ֱ�����֧�ֿ���Mashup��JSONP.
	 * 
	 * @param callbackName
	 *            callback������.
	 * @param contentMap
	 *            Map����,����ת��Ϊjson�ַ���.
	 * @see #render(String, String, String...)
	 */
	@SuppressWarnings("unchecked")
	public static void renderJsonp(final String callbackName,
			final Map contentMap, final String... headers) {
		String jsonParam = JsonHelper.formatObjectToJsonString(contentMap);

		StringBuilder result = new StringBuilder().append(callbackName).append(
				"(").append(jsonParam).append(");");

		// ��ȾContent-TypeΪjavascript�ķ�������,������Ϊjavascript���,
		// ��callback197("{content:'Hello World!!!'}");
		render(JS_TYPE, result.toString(), headers);
	}
}
