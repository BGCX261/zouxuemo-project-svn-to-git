package com.lily.dap.util.convert;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

@SuppressWarnings("unchecked")
public class ConvertUtils {

	static {
		registerDateConverter();
	}

	/**
	 * ��ȡ�����еĶ��������(ͨ��getter����), ��ϳ�List.
	 * 
	 * @param collection ��Դ����.
	 * @param propertyName Ҫ��ȡ��������.
	 */
	public static List convertElementPropertyToList(final Collection collection, final String propertyName) {
		List list = new ArrayList();

		try {
			for (Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}

		return list;
	}

	/**
	 * ��ȡ�����еĶ��������(ͨ��getter����), ��ϳ��ɷָ���ָ����ַ���.
	 * 
	 * @param collection ��Դ����.
	 * @param propertyName Ҫ��ȡ��������.
	 * @param separator �ָ���.
	 */
	public static String convertElementPropertyToString(final Collection collection, final String propertyName,
			final String separator) {
		List list = convertElementPropertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * ����Apache BeanUtilsת���ַ�������Ӧ����.
	 * 
	 * @param value ��ת�����ַ���.
	 * @param toType ת��Ŀ������.
	 */
	public static Object convert(String value, Class<?> toType) {
		if (toType == String.class)
			return value;
		
		try {
			return org.apache.commons.beanutils.ConvertUtils.convert(value.trim(), toType);
		} catch (Exception e) {
			if (Date.class.equals(toType)) {
				//���ת������ΪDate�������Ű��ַ���ת��Ϊlong���ٰ�longת��ΪDate
				try {
					Long v = (Long)org.apache.commons.beanutils.ConvertUtils.convert(value.trim(), Long.class);
					
					return org.apache.commons.beanutils.ConvertUtils.convert(v, toType);
				} catch (Exception e1) {
					throw convertReflectionExceptionToUnchecked(e);
				}
			}
			
			throw convertReflectionExceptionToUnchecked(e);
		}
	}

	/**
	 * ����Apache BeanUtilsת��������Ӧ����.
	 * 
	 * @param value ��ת���Ķ���.
	 * @param toType ת��Ŀ������.
	 */
	public static Object convert(Object value, Class<?> toType) {
		if (value == null || value.getClass().equals(toType))
			return value;
		
		try {
			return org.apache.commons.beanutils.ConvertUtils.convert(value, toType);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}
	
	public static Object[] convert(String[] values, Class<?> toType) {
		if (values == null)
			return null;
		
		if (toType == String.class)
			return values;
		
		Object[] result = new Object[values.length];
		for(int i = 0, len = values.length; i < len; i++)
			result[i] = convert(values[i], toType);
		
		return result;
	}
	
	public static <T> T convert2(String value, Class<T> toType) {
		if (toType == String.class)
			return (T)value;
		
		try {
			return (T)org.apache.commons.beanutils.ConvertUtils.convert(value.trim(), toType);
		} catch (Exception e) {
			throw convertReflectionExceptionToUnchecked(e);
		}
	}
	
	public static <T> T[] convert2(String[] values, Class<T> toType) {
		if (values == null)
			return null;
		
		if (toType == String.class)
			return (T[])values;
		
		T[] result = (T[])Array.newInstance(toType,values.length); 

		for(int i = 0, len = values.length; i < len; i++)
			result[i] = (T)convert(values[i], toType);
		
		return result;
	}

	public static String convert(Object value) {
		return org.apache.commons.beanutils.ConvertUtils.convert(value);
	}

	/**
	 * ����Apache BeanUtils����Converter�ĸ�ʽ: yyyy-MM-dd �� yyyy-MM-dd HH:mm:ss
	 */
	private static void registerDateConverter() {
		DapDateConvert dc = new DapDateConvert();
		org.apache.commons.beanutils.ConvertUtils.register(dc, Date.class);
		org.apache.commons.beanutils.ConvertUtils.register(dc, String.class);
	}

	/**
	 * ������ʱ��checked exceptionת��Ϊunchecked exception.
	 */
	private static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException("Reflection Exception.", e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException("Reflection Exception.", ((InvocationTargetException) e).getTargetException());
		} else if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException("Unexpected Checked Exception.", e);
	}
}