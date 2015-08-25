/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
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
 * <p>供表达式分析使用的变量解析器</p>
 * <p>支持对多个对象的变量检索</p>
 * <p>支持传入Map对象的变量检索</p>
 * <p>如果传入Map对象，建议在对象中插入一个key为"$objectName$"的值作为这个Map的名称</p>
 * <p>解析变量时，先检查变量是不是通过"_"分隔成变量的作用对象和变量名两部分，并查找是否有对应对象的对应变量值</p>
 * <p>如果第一步未找到变量值，则遍历每个对象，查找是否有给定变量的变量值</p>
 * <p>通过对象名和变量值搜索时，对象名应为对象短类名并且首字母小写</p>
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
 * @author 邹学模
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

	/* （非 Javadoc）
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
