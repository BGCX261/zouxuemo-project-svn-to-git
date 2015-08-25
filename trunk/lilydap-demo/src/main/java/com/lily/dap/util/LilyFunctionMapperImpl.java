/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.util;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.lily.dap.service.core.Evaluator.FunctionMapper;

//import com.lily.dap.model.dictionary.Dictionary;
//import com.lily.dap.service.ContextUtil;
//import com.lily.dap.service.common.AddressParserManager;
//import com.lily.dap.service.common.DictionaryManager;
//import com.lily.dap.service.core.Evaluator.FunctionMapper;
//import com.lily.dap.service.core.exception.DataNotExistException;

/**
 * <code>LilyFunctionMapperImpl</code>
 * <p>�����ʽʹ�õĺ���ӳ��ʵ�֣��ṩ�����ĺ�����������ȡ�ֵ�ֵ����ȡ��ֵַ�ȳ��ú�������ʵ��</p>
 *
 * @author ��ѧģ
 * @date 2008-4-15
 */
@SuppressWarnings("unchecked")
public class LilyFunctionMapperImpl implements FunctionMapper {
	public static String DEFAULT_PREFIX = "lily";
	
	private static Map<String, Map<String, String>> keyCacheMap = new HashMap<String, Map<String, String>>();
	
	public Method resolveFunction(String prefix, String localName) {
		if (prefix == null || "".equals(prefix))
			prefix = DEFAULT_PREFIX;
		
		String functionName = prefix + ":" + localName;
		
		if (!functionMap.containsKey(functionName))
			return null;
		
		return functionMap.get(functionName);
	}
	
	private static Map<String, Method> functionMap = new HashMap<String, Method>();
	static {
	    Class<LilyFunctionMapperImpl> c = LilyFunctionMapperImpl.class;
	    
	    try {
			functionMap.put(DEFAULT_PREFIX + ":" + "join", c.getMethod("join", new Class[] { String.class, String.class }));
			functionMap.put(DEFAULT_PREFIX + ":" + "substring", c.getMethod("substring", new Class[] { String.class, Integer.TYPE, Integer.TYPE }));
			functionMap.put(DEFAULT_PREFIX + ":" + "left", c.getMethod("left", new Class[] { String.class, Integer.TYPE }));
			functionMap.put(DEFAULT_PREFIX + ":" + "right", c.getMethod("right", new Class[] { String.class, Integer.TYPE }));
			functionMap.put(DEFAULT_PREFIX + ":" + "current", c.getMethod("current", new Class[] {}));
			functionMap.put(DEFAULT_PREFIX + ":" + "formatDate", c.getMethod("formatDate", new Class[] { Date.class, String.class }));
			functionMap.put(DEFAULT_PREFIX + ":" + "padByZero", c.getMethod("padByZero", new Class[] { Integer.TYPE, Integer.TYPE }));
			functionMap.put(DEFAULT_PREFIX + ":" + "randomGUID", c.getMethod("randomGUID", new Class[] { Integer.TYPE }));
			functionMap.put(DEFAULT_PREFIX + ":" + "iif", c.getMethod("iif", new Class[] { Boolean.TYPE, String.class, String.class }));
			functionMap.put(DEFAULT_PREFIX + ":" + "dictById", c.getMethod("dictById", new Class[] { String.class, Integer.TYPE }));
			functionMap.put(DEFAULT_PREFIX + ":" + "dictByCode", c.getMethod("dictByCode", new Class[] { String.class, String.class }));
			functionMap.put(DEFAULT_PREFIX + ":" + "key2Value", c.getMethod("key2Value", new Class[] { String.class, String.class }));
			functionMap.put(DEFAULT_PREFIX + ":" + "addressName", c.getMethod("addressName", new Class[] { String.class }));
		} catch (Exception e) {}
	}
	
	/**
	 * ���ظ����ַ�����ָ��λ�ú�ָ�����ȵ����ַ���
	 *
	 * @param str
	 * @param start
	 * @param len
	 * @return
	 */
	public static String substring(String str, int start, int len) {
        return StringUtils.substring(str, start, start+len);
	}
	
	/**
	 * ���������ַ���Ϊһ���ַ���
	 *
	 * @param left
	 * @param right
	 * @return
	 */
	public static String join(String left, String right) {
        return left+right;
	}
	
	/**
	 * ȡ�����ַ�����ʼָ�����ַ������ַ���
	 *
	 * @param str
	 * @param len
	 * @return
	 */
	public static String left(String str, int len) {
		return StringUtils.left(str, len);
	}
	
	/**
	 * ȡ�����ַ���ĩβָ�����ַ������ַ���
	 *
	 * @param str
	 * @param len
	 * @return
	 */
	public static String right(String str, int len) {
		return StringUtils.right(str, len);
	}
	
	/**
	 * �����ǰ����
	 *
	 * @return
	 */
	public static Date current() {
		return new Date(System.currentTimeMillis());
	}
	
	/**
	 * ���ָ����ʽ�ĸ�������/ʱ���ַ���
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String format) {
		if (date == null)
			return "";
		
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * ���ݲ������ʽ�Ľ�������ص�һ�����ߵڶ����ַ�����ֵ
	 *
	 * @param b
	 * @param t
	 * @param f
	 * @return
	 */
	public static String iif(boolean b, String t, String f) {
		if (b)
			return t;
		else
			return f;
	}

	/**
	 * ������ת��Ϊ�ַ���������ָ�����ֵ�λ���������λ����"0"����
	 *
	 * @param num
	 * @param digit
	 * @return
	 */
	public static String padByZero(int num, int digit) {
		return StringUtils.leftPad(String.valueOf(num), digit, '0');
	}
	
	/**
	 * ����ָ�����ȵ�����ַ���
	 * 
	 * @param len ����ַ������ȣ��������32���ַ�
	 * @return
	 */
	public static String randomGUID(int len) {
		String uid = new RandomGUID().valueAfterMD5;
		
		return uid.substring(0, len > 32 ? 32 : len);
	}
	
//	/**
//	 * �����ֵ�ID��Ӧ���ֵ�ֵ
//	 *
//	 * @param catalog
//	 * @param dictId
//	 * @return
//	 */
//	public static String dictById(String catalog, int dictId) {
//		DictionaryManager dictionaryManager = (DictionaryManager)ContextUtil.getBean("dictionaryManager");
//		try {
//			Dictionary dictionary = dictionaryManager.getDictionary(catalog, dictId);
//			return dictionary.getDictValue();
//		} catch (DataNotExistException e) {
//			return "";
//		}
//	}
//	
//	/**
//	 * �����ֵ�����Ӧ���ֵ�ֵ
//	 *
//	 * @param catalog
//	 * @param dictCode
//	 * @return
//	 */
//	public static String dictByCode(String catalog, String dictCode) {
//		DictionaryManager dictionaryManager = (DictionaryManager)ContextUtil.getBean("dictionaryManager");
//		try {
//			Dictionary dictionary = dictionaryManager.getDictionary(catalog, dictCode);
//			return dictionary.getDictValue();
//		} catch (DataNotExistException e) {
//			return "";
//		}
//	}
	
	/**
	 * �ṩӳ���ַ������ݣ������ַ����и���key��Ӧ��ֵ���ṩ����֧�֣�
	 * ʾ����
	 * 		Key2Value("2", "1-����,2-����,3-����") return "����"
	 * 
	 * @param key
	 * @param mapString
	 * @return
	 */
	public static String key2Value(String key, String mapString) {
		Map<String, String> map = keyCacheMap.get(mapString);
		if (map == null) {
			map = new HashMap<String, String>();
			
			String[] items = mapString.split(",");
			for (String item : items) {
				String tmp[] = item.trim().split("-");
				
				if (tmp.length > 1)
					map.put(tmp[0].trim(), tmp[1].trim());
			}
			
			keyCacheMap.put(mapString, map);
		}
		
		String val = map.get(key);
		if (val == null)
			val = "";
		
		return val; 
	}
	
//	/**
//	 * ���ص�ַ��Ӧ����ʾ��
//	 *
//	 * @param address
//	 * @return
//	 */
//	public static String addressName(String address) {
//		AddressParserManager addressParserManager = (AddressParserManager)ContextUtil.getBean("addressParserManager");
//		try {
//			return addressParserManager.getDisplayName(address);
//		} catch (Exception e) {
//			return "";
//		}
//	}	
}
