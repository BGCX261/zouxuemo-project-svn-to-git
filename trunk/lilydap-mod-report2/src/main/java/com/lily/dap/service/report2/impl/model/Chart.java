/**
 * 
 */
package com.lily.dap.service.report2.impl.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuemozou
 *
 * ͼ��������Ϣ
 */
public class Chart implements Cloneable {
	private static int uid_counter = 0;
	
	/**
	 * ��ͼ�����ö�Ӧ��Ψһ����ֵ
	 */
	private int uid;
	
	/**
	 * ͼ������
	 */
	private String type;
	
	/**
	 * ͼ������Դ����
	 */
	private String datasource;
	
	/**
	 * ͼ���ڵ�Ԫ����ռ�Ŀ�Ȱٷֱȣ�Ĭ�ϰٷְ�
	 */
	private float width = 100;
	
	/**
	 * ͼ���ڵ�Ԫ����վ�ĸ߶Ȱٷֱȣ�Ĭ�ϰٷְ�
	 */
	private float height = 100;
	
	/**
	 * ͼ�����Map
	 */
	Map<String, String> params = new HashMap<String, String>();

	public Chart() {
		this.uid = ++uid_counter;
	}

	public Chart(String type, String datasource) {
		this.uid = ++uid_counter;
		this.type = type;
		this.datasource = datasource;
	}

	public int getUid() {
		return uid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	public void addParam(String name, String value) {
		this.params.put(name, value);
	}

	public Chart clone() throws CloneNotSupportedException {
		Chart clone = (Chart)super.clone();
		
		if (params.size() > 0) {
			Map<String, String> cloneParams = new HashMap<String, String>();
			cloneParams.putAll(params);
			clone.setParams(cloneParams);
		}
		
		return clone;
	}
}
