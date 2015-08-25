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
 * <p>JSON组字符串辅助工具类</p>
 *
 * @author 邹学模
 * @date 2008-5-29
 */
public class JsonHelper {
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * 分析JSON对象字符串，并转换成指定的Bean对象
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
			throw new RuntimeException("转换json字符串为对象失败", e);
		}
	}
	
	/**
	 * 分析JSON对象数组字符串，并转换成指定的Bean对象数组<br>
	 * 要求JSON字符串格式为：[{prop1: val1,...propn: valn},..,{prop1: val1,...propn: valn}]
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
			throw new RuntimeException("转换json字符串为对象数组失败", e);
		}
	}
	
	/**
	 * 分析JSON对象数组字符串，并转换成指定的Bean对象列表<br>
	 * 要求JSON字符串格式为：[{prop1: val1,...propn: valn},..,{prop1: val1,...propn: valn}]
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
			throw new RuntimeException("转换json字符串为对象列表失败", e);
		}
	}

	/**
	 * 分析JSON对象Map字符串，并转换成指定的Key和value的Map<br>
	 * 要求JSON字符串格式为：prop1: {},...propn: {}}
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
			throw new RuntimeException("转换json字符串为Map失败", e);
		}
	}
	
	/**
	 * 从顶到底分析给定json字符串，并针对每个节点转换成对应类型数据<br>
	 * 	1.节点值为{xx: xx, ..., xx: xx}，则返回LinkHashMap类型数据<br>
	 * 	2.节点值为[xxx, ..., xxx]，则返回ArrayList类型数据<br>
	 * 	3.节点值为'xxx'或"xxx"，则返回字符串数据<br>
	 * 	4.节点值以-或数字开头，则返回数值数据(类型为Number，可能的类型包括：long、int、double)<br>
	 * 	5.节点值是true、yes或false、no（不区分大小写），则返回布尔型数据<br>
	 * 	6.节点值是null（不区分大小写），则返回null<br>
	 * <br>
	 * 示例：<br>
	 * 	{width: [123, 0, 7.89, 'abce', true]} 返回 Map{key: width, value: List[Long(123), Integer(0), Double(7.89), String(abcd), Boolean(true)]}<br>
	 * 	[123, 0, 7.89, 'abce', true] 返回 List[Long(123), Integer(0), Double(7.89), String(abcd), Boolean(true)]
	 * 
	 * @param <T>
	 * @param jsonStr
	 * @return
	 */
	public static <T> T parseJsonString(String jsonStr) {
		return JSONDecoder.decode(jsonStr);
	}
	
	/**
	 * 输出给定对象的Json字符串<br>
	 * <br>
	 * 如果对象是String，返回JSON格式："xxxx"<br>
	 * 如果对象是int,long,float,double,...，返回JSON格式：xxxx<br>
	 * 如果对象是boolean，返回JSON格式：true/false<br>
	 * 如果对象是Date，返回JSON格式：日期所包含的秒数，类似于1292994112828<br>
	 * 如果对象是Bean，返回JSON格式：{prop1: val1,...propn: valn}<br>
	 * 如果对象是数组或者Collection，返回JSON格式：[{prop1: val1,...propn: valn},..,{prop1: val1,...propn: valn}]<br>
	 * 如果对象是Key-Bean的Map，返回JSON格式：{prop1: {},...propn: {}}
	 *
	 * @param entity
	 * @return
	 */
	public static String formatObjectToJsonString(Object entity) {
		try {
			return mapper.writeValueAsString(entity);
		} catch (Exception e) {
			throw new RuntimeException("转换对象为json字符串失败", e);
		}
	}
	
	/**
	 * 根据异常组合失败的JSON字符串
	 * 返回JSON格式：
	 * 	{success: false, error: '异常信息'}
	 *
	 * @param e
	 * @return
	 */
	public static String combinFailJsonString(Exception e) {
		return combinFailJsonString(e.getMessage());
	}

	/**
	 * 组合失败的JSON字符串
	 * 返回JSON格式：
	 * 	{success: false, error: '错误信息'}
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
			throw new RuntimeException("构建失败json字符串失败", e);
		}
		
		return jsonString;
	}
	
	/**
	 * 组合成功的JSON字符串及附加信息（附加信息必须是偶数个可变参数，对应key-value值对，例如：combinSuccessJsonString("id", 123)）<br>
	 * <br>
	 * 其中值对中的值可以是原型、日期类型、字符串类型、数组、列表、Map<br>
	 * 如果值是String，返回JSON格式：{success: true, name: "value"}<br>
	 * 如果值是int,long,float,double,...，返回JSON格式：{success: true, name: value}<br>
	 * 如果值是boolean，返回JSON格式：{success: true, name: true/false}<br>
	 * 如果值是Date，返回JSON格式：日期所包含的秒数，类似于{success: true, name: 1292994112828}<br>
	 * 如果值是Bean，返回JSON格式：{success: true, name: {prop1: val1,...propn: valun}}<br>
	 * 如果值是数组或者Collection，返回JSON格式：{success: true, name: [{prop1: val1,...propn: valn},..,{prop1: val1,...propn: valn}]}<br>
	 * 如果值是Map，返回JSON格式：{success: true, name: {prop1: val1/{...},...propn: valn/{...}}}
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
			throw new RuntimeException("构建成功json字符串失败", e);
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
