/**
 * 
 */
package com.lily.dap.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EXT ʹ�õ�����JSON���ݽṹ
 * 
 * @author ��ѧģr
 *
 */
public class ExtTreeData {
	private Map<String, Object> dataMap = new HashMap<String, Object>();
	
	private List<ExtTreeData> children = null;
	
	public void setData(String name, Object value) {
		setData(name, value, true);
	}
	
	/**
	 * ��������
	 * 
	 * @param name
	 * @param value
	 * @param replaceRepeatData �Ƿ��滻�������ݣ����Ϊtrue����������ݺ����������ظ�ʱ�滻��ǰ���ݣ�������һ��List�����������ݺ��������ݶ�����List��
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
