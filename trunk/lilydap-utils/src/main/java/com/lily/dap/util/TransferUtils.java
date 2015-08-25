/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.xidea.el.Expression;
import org.xidea.el.ExpressionFactory;

import com.lily.dap.util.convert.ConvertUtils;

/**
 * <code>TransferUtils</code>
 * <p>���ݴ��ݹ����࣬�ṩ���ݸ��ƺ�����ת���Ĺ��߷���</p>
 *
 * @author ��ѧģ
 * @date 2008-5-22
 */
public class TransferUtils {
	public static String TRANSFER_SOURCE_OBJECT_PREFIX = "$source";
	
	private static ExpressionFactory expressionFactory = ExpressionFactory.getInstance();

	private static Map<Class<?>, List<String>> classFieldNameCacheMap = new HashMap<Class<?>, List<String>>();
	
	/**
	 * ͬһ���Ͷ���֮�临�����ݣ�Ҫ���Ƶ�����Ҫ�ṩgetter��setter������������Բ��ɶ��򲻿�д������Բ�����<br>
	 * �������ݳ���ʱ�׳�RuntimeException�쳣
	 *
	 * @param src Ҫ���Ƶ�Դ����
	 * @param tgt ���Ƶ���Ŀ�����
	 * @param includes Ҫ���ƵĶ��������ַ������飬������������е����Խ������ƣ��������includesΪnull����ʾ���Զ�Ҫ����
	 * @param excludes �����ƵĶ��������ַ������飬����������е����Խ������ƣ��������excludesΪnull����ʾû����Ҫ�ų�������
	 * @param callback ����ÿ����������ʱ���ûص��������ڻص������п��Կ����Ƿ�Ҫ���������������
	 */
	@SuppressWarnings("unchecked")
	public static <T> void copy(T src, T tgt, String[] includes, String[] excludes, TransferCallback callback) {
		if (src == null)	throw new IllegalArgumentException("Դ��������Ϊ��");
		if (tgt == null)	throw new IllegalArgumentException("Ŀ���������Ϊ��");
		
		Class clazz = src.getClass();
		
		if (includes == null)
			includes = getBeansPropertyNames(clazz);
		
		for (String name : includes) {
			if (excludes != null && ArrayUtils.contains(excludes, name))
				continue;
			
			try {
				Object v = PropertyUtils.getProperty(src, name);
				
				if (callback != null && !callback.readyCopy(src, name, v, tgt, name, PropertyUtils.getProperty(tgt, name)))
					continue;
					
				PropertyUtils.setProperty(tgt, name, v);
			} catch (Exception e) {
				throw new RuntimeException("�����ֶ�[" + name + "]���ݳ���", e);
			}
		}
	}
	
	/**
	 * ����Դ�������ݵ�Ŀ�����Դ�����Ŀ�����һ����ͬһ�ֶ������ͣ���Ҫ��Դ���������ṩgetter������Ŀ���������Ҫ�ṩsetter����
	 *
	 * @param src Դ���󣬿�����һ��Bean��������һ��Map
	 * @param tgt Ŀ����󣬿�����һ��Bean��������һ��Map
	 * @param callback ����ÿ����������ʱ���ûص��������ڻص������п��Կ����Ƿ�Ҫ���������������
	 */
	public static void copy(Object src, Object tgt, TransferCallback callback) {
		copy(null, src, tgt, true, callback);
	}

	/**
	 * ����Դ�������ݵ�Ŀ�����Դ�����Ŀ�����һ����ͬһ�ֶ������ͣ���Ҫ��Դ���������ṩgetter������Ŀ���������Ҫ�ṩsetter����
	 * <p>�����ַ��������ָ�ʽ��"field-field"��ͨ��"-"�ָ�ָ��Դ�������Ժ�Ŀ��������ԣ�"field"����ʾԴ�������Ժ�Ŀ���������Ϊͬһ������</p>
	 * <p>ʾ����</p>
	 * <p>String[] rules = new String[]{"src_field1-tgt_field3", "field2"}</p>
	 * �������ݳ���ʱ��Ҫ���Ƶ����Բ����ڡ�Ҫ���Ƶ����Բ�֧��getter/setter�������ȵȣ��׳�RuntimeException�쳣
	 *
	 * @param rules ���ƹ����ַ������飬���Ҫ��Դ�����Ƶ�Ŀ����������ֵ�����δ���壬���Դ�����ж�ȡ������������Ϊ���ƹ���
	 * @param src Դ���󣬿�����һ��Bean��������һ��Map
	 * @param tgt Ŀ����󣬿�����һ��Bean��������һ��Map
	 * @param callback ����ÿ����������ʱ���ûص��������ڻص������п��Կ����Ƿ�Ҫ���������������
	 */
	public static void copy(String[] rules, Object src, Object tgt, TransferCallback callback) {
		copy(rules, src, tgt, true, callback);
	}
	
	/**
	 * ��Դ������Ŀ�����֮�临�����ݣ�Դ�����Ŀ�����һ����ͬһ�ֶ������ͣ�<br>
	 * ����Դ�����Ƶ�Ŀ�����ʱ��Ҫ��Դ���������ṩgetter������Ŀ���������Ҫ�ṩsetter����<br>
	 * ����Ŀ������Ƶ�Դ�����ǣ�Ҫ��Ŀ����������ṩgetter������Դ��������Ҫ�ṩsetter����<br>
	 * <p>�����ַ��������ָ�ʽ��"field-field"��ͨ��"-"�ָ�ָ��Դ�������Ժ�Ŀ��������ԣ�"field"����ʾԴ�������Ժ�Ŀ���������Ϊͬһ������</p>
	 * <p>ʾ����</p>
	 * <p>String[] rules = new String[]{"src_field1-tgt_field3", "field2"}</p>
	 * �������ݳ���ʱ��Ҫ���Ƶ����Բ����ڡ�Ҫ���Ƶ����Բ�֧��getter/setter�������ȵȣ��׳�RuntimeException�쳣
	 *
	 * @param rules ���ƹ����ַ������飬���Ҫ��Դ�����Ƶ�Ŀ����������ֵ�����δ���壬���Դ�����ж�ȡ������������Ϊ���ƹ���
	 * @param src Դ���󣬿�����һ��Bean��������һ��Map
	 * @param tgt Ŀ����󣬿�����һ��Bean��������һ��Map
	 * @param direction ���Ʒ���Ϊtrue����ʾ��Դ�����Ƶ�Ŀ�����Ϊfalse����ʾ��Ŀ������Ƶ�Դ����
	 * @param callback ����ÿ����������ʱ���ûص��������ڻص������п��Կ����Ƿ�Ҫ���������������
	 */
	public static void copy(String[] rules, Object src, Object tgt, boolean direction, TransferCallback callback) {
		if (src == null)	throw new IllegalArgumentException("Դ��������Ϊ��");
		if (tgt == null)	throw new IllegalArgumentException("Ŀ���������Ϊ��");
		
		if (rules == null)
			rules = getObjectsPropertyNames(src);
		
		for (String rule : rules) {
			String srcFieldName, tgtFieldName;
			
			String[] tmp = rule.split("-", 2);
			srcFieldName = tmp[0].trim();
			if (tmp.length == 1)
				tgtFieldName = tmp[0].trim();
			else
				tgtFieldName = tmp[1].trim();
			
			if (direction)
				copy(src, srcFieldName, tgt, tgtFieldName, callback);
			else
				copy(tgt, tgtFieldName, src, srcFieldName, callback);
		}
	}

	/**
	 * ����Դ�����ָ������ֵ��Ŀ������ָ�������У����Զ����ûص��������͸�����Ϣ<br>
	 * �������ݳ���ʱ��Ҫ���Ƶ����Բ����ڡ�Ҫ���Ƶ����Բ�֧��getter/setter�������ȵȣ��׳�RuntimeException�쳣
	 *
	 * @param src Դ���󣬿�����һ��Bean��������һ��Map
	 * @param srcFieldName
	 * @param tgt Ŀ����󣬿�����һ��Bean��������һ��Map
	 * @param tgtFieldName
	 * @param callback
	 */
	public static void copy(Object src, String srcFieldName, Object tgt, String tgtFieldName, TransferCallback callback) {
		try {
			Object v1 = getPropertyValue(src, srcFieldName);
			Object v2 = null;
			try {
				v2 = getPropertyValue(tgt, tgtFieldName);
			} catch (Exception e) {}
			
			if (callback != null && !callback.readyCopy(src, srcFieldName, v1, tgt, tgtFieldName, v2))
				return;
			
			v1 = convert(v1, tgt, tgtFieldName);
			setPropertyValue(tgt, tgtFieldName, v1);
		} catch (Exception e) {
			throw new RuntimeException("���ݸ���[" + srcFieldName + "��" + tgtFieldName + "]����", e);
		}
	}
	
	/**
	 * ���ø������������ֵ�������������ֵ������������Ͳ�һ�£�������ת������ֵΪͬ������<br>
	 * �������ݳ���ʱ��Ҫ���õ����Բ����ڡ�Ҫ���õ����Բ�֧��setter�������ȵȣ��׳�RuntimeException�쳣
	 * 
	 * @param tgt
	 * @param tgtFieldName Ŀ����󣬿�����һ��Bean��������һ��Map
	 * @param tgtFieldVal
	 */
	public static void setProperty(Object tgt, String tgtFieldName, Object tgtFieldVal) {
		try {
			Object val = convert(tgtFieldVal, tgt, tgtFieldName);
			setPropertyValue(tgt, tgtFieldName, val);
		} catch (Exception e) {
			throw new RuntimeException("��������[" + tgtFieldName + "ֵΪ" + tgtFieldVal + "]����", e);
		}
	}
    
	/**
	 * �ṩ1���������Դ��ͨ�����ʽ�����������ת����д�뵽Ŀ��������<br>
	 * ת������Ϊ�õȺŷָ��ĸ�ֵ���ʽ�ַ������飬�Ⱥ������Ŀ������ָ���ֶΣ��Ⱥ��ұ��ǰ���Դ�����ֶεı��ʽ�����ʽ��֧�ֵ��ú�������<br>
	 * ���ڴ���������Դ�����ʽ����ͨ��"$source[index].field"������ĳ������Դ��ĳ�������ֶΣ�����indexΪ����Դ�ڲ����е�˳����0��ʼ<br>
	 * �����ڵ�һ������Դ�����ԣ�������ͨ������˵��"$source[0].field"�����ã�Ҳ����ֱ��ָ��field������<br>
	 * ʾ����<br>
	 * 	String[] rules = new String[]{"tgt_field1=src_field1+10", "tgt_field2=$source[0].field2"}
	 *
	 * @param rules ����ת�������ַ�������
	 * @param tgt Ҫд���Ŀ����󣬿�����һ��Bean��������һ��Map
	 * @param src ���Դ���󣬿�����һ��Bean��������һ��Map
	 */
	@SuppressWarnings("unchecked")
	public static void transfer(String[] rules, Object tgt, Object... src) {
		if (src == null || src.length == 0)	throw new IllegalArgumentException("Դ��������Ϊ��");
		if (tgt == null)	throw new IllegalArgumentException("Ŀ���������Ϊ��");
		if (rules == null || rules.length == 0)	throw new IllegalArgumentException("ת�����򼯺�δ����");
		
		//����JSEL���ʽ��Ҫ�Ĳ���Map�����Ȱ�����Դ����List��Ϊ��ΪTRANSFER_SOURCE_OBJECT_PREFIX��ֵ��ӵ�����Map��
		//Ȼ������һ��Դ�����������������ֵ�Ե�����Map
		Map context = new HashMap();
		
		List list = new ArrayList();
		for (Object obj : src)
			list.add(obj);
		
		context.put(TRANSFER_SOURCE_OBJECT_PREFIX, list);
		
		Object obj = list.get(0);
		try {
			Map map = PropertyUtils.describe(obj);
			
			context.putAll(map);
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		} catch (NoSuchMethodException e) {
		}
		
		//����ÿ��ת�����򣬵���JSELִ�б��ʽ���㣬�Ѽ���������ת��д��Ŀ������Ŀ���ֶ���
		for (String rule : rules) {
			if (rule == null || rule.length() == 0)
				throw new IllegalArgumentException("ת������δ����");
			
			String[] tmp = rule.split("=", 2);
			if (tmp.length != 2)
				throw new IllegalArgumentException("ת�������ʽ[" + rule + "]���󣬸�ʽҪ��Ϊ'xxx=xxx'��ʽ");
			
			String tgtFieldName = tmp[0].trim();
			String transferExpression = tmp[1].trim();
			
			try {
				Expression el = expressionFactory.create(transferExpression);
				Object result = el.evaluate(context);
				
				result = convert(result, tgt, tgtFieldName);
				setPropertyValue(tgt, tgtFieldName, result);
			} catch (Exception e) {
				throw new RuntimeException("ִ��ת������[" + rule + "]����", e);
			}
		}
	}
	
	/**
	 * ����ת��ֵΪָ�������ָ���ֶε����ͣ��������ת�����׳��쳣<br>
	 * ���ָ��������һ��Map�������ֶ����ͺ�ֵ������ͬ��ֵ����ת����ֱ�ӷ��أ����������ת������
	 * 
	 * @param value
	 * @param tgt
	 * @param tgtFieldName
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	private static Object convert(Object value, Object bean, String fieldName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (!(bean instanceof Map)) {
			Class srcFieldClass = value.getClass();
			
			Class tgtFieldClass;
			if (bean instanceof Class)
				tgtFieldClass = PropertyUtils.getPropertyType(bean, fieldName);
			else
				tgtFieldClass = PropertyUtils.getPropertyType(bean, fieldName);
			
			if (!srcFieldClass.equals(tgtFieldClass)) {
				if (tgtFieldClass.equals(String.class)) {
					value = ConvertUtils.convert(value);
				} else if (srcFieldClass.equals(String.class)) {
					value = ConvertUtils.convert((String)value, tgtFieldClass);
				} else {
					String temp = ConvertUtils.convert(value);
					value = ConvertUtils.convert(temp, tgtFieldClass);
				}
			}
		}
		
		return value;
	}
	
	private static String[] getObjectsPropertyNames(Object obj) {
		if (obj instanceof Map<?, ?>) {
			Map<?, ?> map = (Map<?, ?>)obj;
			Set<?> set = map.keySet();
			
			int index = 0;
			String[] property = new String[set.size()];
			for (Object o : set)
				property[index++] = o.toString();
			
			return property;
		} else {
			Class<?> clazz = obj.getClass();
			String[] property = getBeansPropertyNames(clazz);
			
			return property;
		}
	}
	
	private static String[] getBeansPropertyNames(Class<?> clazz) {
		List<String> list;
		if (classFieldNameCacheMap.containsKey(clazz))
			list = classFieldNameCacheMap.get(clazz);
		else {
			list = new ArrayList<String>();
			
			//��ȡ���пɶ������б��������ࣩ
			List<Field> fields = BeanUtils.getDeclaredFields(clazz);
			for (Field field : fields) {
				String name = field.getName();
				
				try {
					if (BeanUtils.isFieldWriteable(clazz, name))
						list.add(name);
				} catch (SecurityException e) {
				} catch (NoSuchFieldException e) {
				}
			}

			classFieldNameCacheMap.put(clazz, list);
		}

		return list.toArray(new String[0]);
	}
	
	@SuppressWarnings("unchecked")
	private static Object getPropertyValue(Object obj, String property) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (obj instanceof Map) {
			Map map = (Map)obj;
			if (!map.containsKey(property))
				throw new NoSuchMethodException("Map�в�������Ϊ[" + property + "]������");
				
			return map.get(property);
		} else
			return PropertyUtils.getProperty(obj, property);
	}
	
	@SuppressWarnings("unchecked")
	private static void setPropertyValue(Object obj, String property, Object value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (obj instanceof Map) {
			Map map = (Map)obj;
			
			if (map.containsKey(property))
				map.remove(property);
			
			map.put(property, value);
		} else
			PropertyUtils.setProperty(obj, property, value);
	}
}
