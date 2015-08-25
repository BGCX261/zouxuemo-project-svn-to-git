/**
 * 
 */
package com.lily.dap.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EXT 使用的树的JSON数据结构
 * 
 * @author 邹学模r
 *
 */
public class ExtTreeData {
	private Map<String, Object> dataMap = new HashMap<String, Object>();
	
	private List<ExtTreeData> children = null;
	
	public void setData(String name, Object value) {
		setData(name, value, true);
	}
	
	/**
	 * 插入数据
	 * 
	 * @param name
	 * @param value
	 * @param replaceRepeatData 是否替换已有数据，如果为true，则插入数据和已有数据重复时替换当前数据，否则建立一个List，把已有数据和现有数据都存入List中
	 */
	@SuppressWarnings("unchecked")
	public ExtTreeData setData(String name, Object value, boolean replaceRepeatData) {
		if (dataMap.containsKey(name)) {
			if (replaceRepeatData) {
				dataMap.remove(name);
				dataMap.put(name, value);
			} else {
				Object val = dataMap.get(name);
				
				if (!(val instanceof ArrayList)) {
					List<Object> list = new ArrayList<Object>();
					list.add(val);
					val = list;
				}
				
				((List<Object>)val).add(value);
			}
		} else
			dataMap.put(name, value);
		
		return this;
	}
	
	public ExtTreeData addChildren(ExtTreeData child) {
		if (children == null)
			children = new ArrayList<ExtTreeData>();
		
		children.add(child);
		
		return this;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public List<ExtTreeData> getChildren() {
		return children;
	}
}
