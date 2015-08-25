/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.service.core.Evaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;


/**
 * <code>ComplexVariableResolverImpl</code>
 * <p>�����ʽ����ʹ�õı���������</p>
 * <p>֧�ֶԶ������ı�������</p>
 * <p>֧�ִ���Map����ı�������</p>
 * <p>�������Map���󣬽����ڶ����в���һ��keyΪ"$objectName$"��ֵ��Ϊ���Map������</p>
 * <p>��������ʱ���ȼ������ǲ���ͨ��"_"�ָ��ɱ��������ö���ͱ����������֣��������Ƿ��ж�Ӧ����Ķ�Ӧ����ֵ</p>
 * <p>�����һ��δ�ҵ�����ֵ�������ÿ�����󣬲����Ƿ��и��������ı���ֵ</p>
 * <p>ͨ���������ͱ���ֵ����ʱ��������ӦΪ�����������������ĸСд</p>
 * 
 * 		 Map<String, Object> map = new HashMap<String, Object>();
 * 		 map.put("$objectName$", "myMap");
 * 		 map.put("field1", "mapVal");
 * 		 
 * 		 Module module = new Module();
 * 		 module.setName("modelVal");
 * 		  
 * 		 ComplexVariableResolverImpl variableResolver = new ComplexVariableResolverImpl(map, module);
 * 		 try {
 * 			 System.out.println(variableResolver.resolveVariable("myMap_field1"));	//mapVal
 * 			 System.out.println(variableResolver.resolveVariable("module_name"));	//modelVal
 * 		} catch (EvaluatException e) {
 * 			e.printStackTrace();
 * 		}
 *
 * @author ��ѧģ
 * @date 2008-4-15
 */
public class ComplexVariableResolverImpl implements VariableResolver {
	private Map<String, Object> objectMap = new HashMap<String, Object>();
	private List<Object> objectList = new ArrayList<Object>();
	 
	public ComplexVariableResolverImpl(Object... objects) {
		for (Object obj : objects) {
			if (obj==null) continue;
			
			String name = null;
			
			if (obj instanceof Map) {
				Object o = ((Map)obj).get("$objectName$");
				if (o != null)
					name = o.toString();
			} else	{
				name = StringUtils.uncapitalize(obj.getClass().getSimpleName());
			}
			
			if (name != null)
				objectMap.put(name, obj);
			
			objectList.add(obj);
		}
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.core.Evaluator.VariableResolver#resolveVariable(java.lang.String)
	 */
	public Object resolveVariable(String name) throws EvaluatException {
		Object val;
		
		String[] tmp = name.split("_", 2);
		if (tmp.length == 2) {
			Object obj = objectMap.get(tmp[0]);
			if (obj != null) {
				val = resolveVariable(obj, tmp[1]);
				if (val != null)
					return val;
			}
		}
			
		for (Object obj : objectList) {
			val = resolveVariable(obj, name);
			if (val != null)
				return val;
		}

		return null;
	}

	private Object resolveVariable(Object obj, String name) {
		Object val = null;
		
		if (obj instanceof Map) {
			Map dataMap = (Map)obj;
			if (!dataMap.containsKey(name))
				return null;
			
			val = dataMap.get(name);
		} else {
			try {
				val = PropertyUtils.getProperty(obj, name);
			} catch (Exception e) {
				return null;
			}
		}
		
		return val;
	}
	
	public static void main(String[] args) {
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("$objectName$", "myMap");
		 map.put("field1", "mapVal");
		 
//		 Module module = new Module();
//		 module.setName("modelVal");
		  
		 ComplexVariableResolverImpl variableResolver = new ComplexVariableResolverImpl(map/*, module*/);
		 try {
			 System.out.println(variableResolver.resolveVariable("myMap_field1"));	//mapVal
			 System.out.println(variableResolver.resolveVariable("module_name"));	//modelVal
		} catch (EvaluatException e) {
			e.printStackTrace();
		}
	}
}
