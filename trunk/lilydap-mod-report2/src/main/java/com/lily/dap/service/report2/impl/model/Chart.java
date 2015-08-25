/**
 * 
 */
package com.lily.dap.service.report2.impl.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xuemozou
 *
 * 图表配置信息
 */
public class Chart implements Cloneable {
	private static int uid_counter = 0;
	
	/**
	 * 本图表配置对应的唯一索引值
	 */
	private int uid;
	
	/**
	 * 图表类型
	 */
	private String type;
	
	/**
	 * 图表数据源名称
	 */
	private String datasource;
	
	/**
	 * 图表在单元格中占的宽度百分比，默认百分百
	 */
	private float width = 100;
	
	/**
	 * 图表在单元格中站的高度百分比，默认百分百
	 */
	private float height = 100;
	
	/**
	 * 图表参数Map
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
