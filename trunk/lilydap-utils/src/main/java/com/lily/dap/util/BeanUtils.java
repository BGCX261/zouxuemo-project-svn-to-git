package com.lily.dap.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * �����Utils��������.
 * 
 * �ṩ�ַ���˽��ֱ�Ӷ�ȡfiled������.
 */
public class BeanUtils {

	protected static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

	private BeanUtils() {
	}

	/**
	 * ֱ�Ӷ�ȡ��������ֵ,����private/protected���η�,������getter����.
	 */
	public static Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException {
		Field field = getDeclaredField(object, fieldName);
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.error("�������׳����쳣{}", e.getMessage());
		}
		return result;
	}

	/**
	 * ֱ�����ö�������ֵ,����private/protected���η�,������setter����.
	 */
	public static void setFieldValue(Object object, String fieldName, Object value) throws NoSuchFieldException {
		Field field = getDeclaredField(object, fieldName);
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		try {
			field.set(object, value);
		} catch (IllegalAccessException e) {
			logger.error("�������׳����쳣:{}", e.getMessage());
		}
	}

	/**
	 * ѭ������ת��,��ȡ�����DeclaredField.
	 */
	public static Field getDeclaredField(Object object, String fieldName) throws NoSuchFieldException {
		return getDeclaredField(object.getClass(), fieldName);
	}
	
	/**
	 * ѭ������ת��,��ȡ���DeclaredField.
	 */
	@SuppressWarnings("unchecked")
	public static Field getDeclaredField(Class clazz, String fieldName) throws NoSuchFieldException {
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				// Field���ڵ�ǰ�ඨ��,��������ת��
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + fieldName);
	}

	/**
	 * ��ȡ���������и��༰�丸������пɶ������б���
	 */
	public static List<Field> getDeclaredFields(Object object) {
		return getDeclaredFields(object.getClass());
	}
	
	/**
	 * ��ȡ�����༰�丸������пɶ������б���
	 */
	public static List<Field> getDeclaredFields(Class<?> clazz) {
		Map<String, Field> fields = new HashMap<String, Field>();
		List<Field> fieldList = new ArrayList<Field>();
		
		//��ȡ���е������ԣ���ӵ�Map��
		insertField(clazz, fields, fieldList, clazz.getDeclaredFields());
		
		//�������࣬�����������ԣ���ӵ�Map�У���������а����˺�������ͬ�����ԣ��������
		Class<?> superClazz = clazz.getSuperclass();
		while (superClazz != null) {
			insertField(clazz, fields, fieldList, superClazz.getDeclaredFields());
			
			superClazz = superClazz.getSuperclass();
		}
		
		return fieldList;
	}
	
	private static void insertField(Class<?> clazz, Map<String, Field> map, List<Field>list, Field[] fields) {
		// �������ֵ��Map�У����Map�а�����ͬ�������ԣ��������
		for (int i = 0; i < fields.length; i++) {
			String name = fields[i].getName();
			
			try {
				if (!BeanUtils.isFieldReadable(clazz, name))
					continue;
			} catch (Exception e) {
				continue;
			}
			
			if (!map.containsKey(name)) {
				map.put(name, fields[i]);
				list.add(fields[i]);
			}
		}
	}

	/**
	 * ��ȡ���������и��༰�丸������пɶ�����������
	 */
	public static String[] getDeclaredFieldNames(Object object) {
		return getDeclaredFieldNames(object.getClass());
	}

	/**
	 * ��ȡ�����༰�丸������пɶ�����������
	 */
	public static String[] getDeclaredFieldNames(Class<?> clazz) {
		List<Field> fields = getDeclaredFields(clazz);
		String[] fieldNames = new String[fields.size()];
		
		for (int i = 0, len = fields.size(); i < len; i++)
			fieldNames[i] = fields.get(i).getName();
		
		return fieldNames;
	}
	
	/**
	 * �жϸ����������Ƿ�ɶ�
	 *
	 * @param clazz
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	@SuppressWarnings({ "unchecked" })
	public static boolean isFieldReadable(Class clazz, String fieldName) throws SecurityException, NoSuchFieldException {
		try {
			getFieldsReadMethod(clazz, fieldName);
		} catch (NoSuchMethodException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * ��ȡ���Ե�getter����
	 *
	 * @param clazz
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	public static Method getFieldsReadMethod(Class clazz, String fieldName) throws NoSuchFieldException, SecurityException, NoSuchMethodException {
		Field field = getDeclaredField(clazz, fieldName);
		Class type = field.getType();
		
		String readMethodName;
		if (type == boolean.class || type == null) {
		    readMethodName = "is" + capitalize(fieldName);
		} else {
		    readMethodName = "get" + capitalize(fieldName);
		}
		
		Method method = null;
		try {
			method = clazz.getMethod(readMethodName, new Class[0]);
		} catch (NoSuchMethodException e) {
			if (readMethodName.startsWith("get"))
				throw e;
			
			readMethodName = "get" + capitalize(fieldName);
			method = clazz.getMethod(readMethodName, new Class[0]);
		}
		
		return method;
	}

	/**
	 * �жϸ����������Ƿ��д
	 *
	 * @param clazz
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	@SuppressWarnings({ "unchecked" })
	public static boolean isFieldWriteable(Class clazz, String fieldName) throws SecurityException, NoSuchFieldException {
		try {
			getFieldsWriteMethod(clazz, fieldName);
		} catch (NoSuchMethodException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * ��ȡ���Ե�setter����
	 *
	 * @param clazz
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	public static Method getFieldsWriteMethod(Class clazz, String fieldName) throws NoSuchFieldException, SecurityException, NoSuchMethodException {
		Field field = getDeclaredField(clazz, fieldName);
		Class type = field.getType();
		
		String writeMethodName = "set" + capitalize(fieldName);
		return clazz.getMethod(writeMethodName, new Class[]{ type });
	}
	
	/**
	 * ����ĸ��д
	 *
	 * @param str
	 * @return
	 */
	private static String capitalize(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
}
