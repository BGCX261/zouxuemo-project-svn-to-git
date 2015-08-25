package com.lily.dap.web.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.xidea.el.json.JSONDecoder;

/**
 * <code>JsonHelper</code>
 * <p>JSON���ַ�������������</p>
 *
 * @author ��ѧģ
 * @date 2008-5-29
 */
public class JsonHelper {
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * ����JSON�����ַ�������ת����ָ����Bean����
	 *
	 * @param clazz
	 * @param jsonStr
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static <T> T parseJsonStringToObject(Class<T> clazz, String jsonStr) {
		try {
			return mapper.readValue(jsonStr, clazz);
		} catch (Exception e) {
			throw new RuntimeException("ת��json�ַ���Ϊ����ʧ��", e);
		}
	}
	
	/**
	 * ����JSON���������ַ�������ת����ָ����Bean��������<br>
	 * Ҫ��JSON�ַ�����ʽΪ��[{prop1: val1,...propn: valn},..,{prop1: val1,...propn: valn}]
	 *
	 * @param clazz
	 * @param jsonStr
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static <T> T[] parseJsonStringToArray(Class<T> clazz, String jsonStr) {
		if (jsonStr == null || "".equals(jsonStr))
			return null;

		try {
			return mapper.readValue(jsonStr, TypeFactory.arrayType(clazz));
		} catch (Exception e) {
			throw new RuntimeException("ת��json�ַ���Ϊ��������ʧ��", e);
		}
	}
	
	/**
	 * ����JSON���������ַ�������ת����ָ����Bean�����б�<br>
	 * Ҫ��JSON�ַ�����ʽΪ��[{prop1: val1,...propn: valn},..,{prop1: val1,...propn: valn}]
	 *
	 * @param clazz
	 * @param jsonStr
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static <T> List<T> parseJsonStringToList(Class<T> clazz, String jsonStr) {
		if (jsonStr == null || "".equals(jsonStr))
			return null;
		
		try {
			return mapper.readValue(jsonStr, TypeFactory.collectionType(List.class, clazz));
		} catch (Exception e) {
			throw new RuntimeException("ת��json�ַ���Ϊ�����б�ʧ��", e);
		}
	}

	/**
	 * ����JSON����Map�ַ�������ת����ָ����Key��value��Map<br>
	 * Ҫ��JSON�ַ�����ʽΪ��prop1: {},...propn: {}}
	 *
	 * @param keyClass
	 * @param valClass
	 * @param jsonStr
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <K, T> Map<K, T> parseJsonStringToMap(Class<K> keyClass, Class<T> valClass, String jsonStr) {
		if (jsonStr == null || "".equals(jsonStr))
			return null;
		
		try {
			return mapper.readValue(jsonStr, TypeFactory.mapType(Map.class, keyClass, valClass));
		} catch (Exception e) {
			throw new RuntimeException("ת��json�ַ���ΪMapʧ��", e);
		}
	}
	
	/**
	 * �Ӷ����׷�������json�ַ����������ÿ���ڵ�ת���ɶ�Ӧ��������<br>
	 * 	1.�ڵ�ֵΪ{xx: xx, ..., xx: xx}���򷵻�LinkHashMap��������<br>
	 * 	2.�ڵ�ֵΪ[xxx, ..., xxx]���򷵻�ArrayList��������<br>
	 * 	3.�ڵ�ֵΪ'xxx'��"xxx"���򷵻��ַ�������<br>
	 * 	4.�ڵ�ֵ��-�����ֿ�ͷ���򷵻���ֵ����(����ΪNumber�����ܵ����Ͱ�����long��int��double)<br>
	 * 	5.�ڵ�ֵ��true��yes��false��no�������ִ�Сд�����򷵻ز���������<br>
	 * 	6.�ڵ�ֵ��null�������ִ�Сд�����򷵻�null<br>
	 * <br>
	 * ʾ����<br>
	 * 	{width: [123, 0, 7.89, 'abce', true]} ���� Map{key: width, value: List[Long(123), Integer(0), Double(7.89), String(abcd), Boolean(true)]}<br>
	 * 	[123, 0, 7.89, 'abce', true] ���� List[Long(123), Integer(0), Double(7.89), String(abcd), Boolean(true)]
	 * 
	 * @param <T>
	 * @param jsonStr
	 * @return
	 */
	public static <T> T parseJsonString(String jsonStr) {
		return JSONDecoder.decode(jsonStr);
	}
	
	/**
	 * ������������Json�ַ���<br>
	 * <br>
	 * ���������String������JSON��ʽ��"xxxx"<br>
	 * ���������int,long,float,double,...������JSON��ʽ��xxxx<br>
	 * ���������boolean������JSON��ʽ��true/false<br>
	 * ���������Date������JSON��ʽ��������������������������1292994112828<br>
	 * ���������Bean������JSON��ʽ��{prop1: val1,...propn: valn}<br>
	 * ����������������Collection������JSON��ʽ��[{prop1: val1,...propn: valn},..,{prop1: val1,...propn: valn}]<br>
	 * ���������Key-Bean��Map������JSON��ʽ��{prop1: {},...propn: {}}
	 *
	 * @param entity
	 * @return
	 */
	public static String formatObjectToJsonString(Object entity) {
		try {
			return mapper.writeValueAsString(entity);
		} catch (Exception e) {
			throw new RuntimeException("ת������Ϊjson�ַ���ʧ��", e);
		}
	}
	
	/**
	 * �����쳣���ʧ�ܵ�JSON�ַ���
	 * ����JSON��ʽ��
	 * 	{success: false, error: '�쳣��Ϣ'}
	 *
	 * @param e
	 * @return
	 */
	public static String combinFailJsonString(Exception e) {
		return combinFailJsonString(e.getMessage());
	}

	/**
	 * ���ʧ�ܵ�JSON�ַ���
	 * ����JSON��ʽ��
	 * 	{success: false, error: '������Ϣ'}
	 *
	 * @param error
	 * @return
	 */
	public static String combinFailJsonString(String error) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);
		map.put("error", error);
		
		String jsonString = null;
		try {
			jsonString = formatObjectToJsonString(map);
		} catch (Exception e) {
			throw new RuntimeException("����ʧ��json�ַ���ʧ��", e);
		}
		
		return jsonString;
	}
	
	/**
	 * ��ϳɹ���JSON�ַ�����������Ϣ��������Ϣ������ż�����ɱ��������Ӧkey-valueֵ�ԣ����磺combinSuccessJsonString("id", 123)��<br>
	 * <br>
	 * ����ֵ���е�ֵ������ԭ�͡��������͡��ַ������͡����顢�б�Map<br>
	 * ���ֵ��String������JSON��ʽ��{success: true, name: "value"}<br>
	 * ���ֵ��int,long,float,double,...������JSON��ʽ��{success: true, name: value}<br>
	 * ���ֵ��boolean������JSON��ʽ��{success: true, name: true/false}<br>
	 * ���ֵ��Date������JSON��ʽ��������������������������{success: true, name: 1292994112828}<br>
	 * ���ֵ��Bean������JSON��ʽ��{success: true, name: {prop1: val1,...propn: valun}}<br>
	 * ���ֵ���������Collection������JSON��ʽ��{success: true, name: [{prop1: val1,...propn: valn},..,{prop1: val1,...propn: valn}]}<br>
	 * ���ֵ��Map������JSON��ʽ��{success: true, name: {prop1: val1/{...},...propn: valn/{...}}}
	 *
	 * @param name
	 * @param value
	 * @return
	 */
	public static String combinSuccessJsonString(Object... valuePairs) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("success", true);

		if (valuePairs != null && valuePairs.length > 0) {
			Object key = null;
			
			for (int i = 0, len = valuePairs.length; i < len; i++) {
				if (key == null)
					key = valuePairs[i];
				else {
					map.put(key, valuePairs[i]);
					
					key = null;
				}
			}
		}
		
		String jsonString = null;
		try {
			jsonString = formatObjectToJsonString(map);
		} catch (Exception e) {
			throw new RuntimeException("�����ɹ�json�ַ���ʧ��", e);
		}
		
		return jsonString;
	}
	
	public static void main(String[] args) {
		String s = "{a: [{b: 123, c: 'abc'},{b: 123, c: 'abc'}], b: 'ccc'}";
	
//		Object o = JsonHelper.parseJsonStringToObject(Object.class, s);
//		System.out.println(o);
		
		Map map = JsonHelper.parseJsonString("{a: [{b: 123, c: 'abc'},{b: 123, c: 'abc'}], b: 'ccc'}");
		System.out.println(map);
		
		List list = JsonHelper.parseJsonString("[{b: 123, c: 'abc'},{b: 123, c: 'abc'}]");
		System.out.println(list);
		
		list = JsonHelper.parseJsonString("[123, 0, 12.34, 'abcde', true]");
		System.out.println(list);
		
		map = JsonHelper.parseJsonString("{width: [123, 456, 789]}");
		System.out.println(map);
		for (Object o : (List)map.get("width"))
			System.out.println(((Number)o).doubleValue());
		
		map = JsonHelper.parseJsonString("{a: 123, b: '2011-12-31', c: 0, d: false, e: 12.34}");
		System.out.println(map);
	}
}
