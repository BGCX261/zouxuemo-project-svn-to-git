package com.lily.dap.service.report2.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lily.dap.service.report2.impl.model.Table;

/**
 * @author Administrator
 *
 *	报表模版配置结构表
 */
public class ReportConfig implements Cloneable {
	/**
	 * 报表名
	 */
	private String name = "";
	
	/**
	 * 报表说明
	 */
	private String des = "";
	
	/**
	 * 导出文件的文件名，支持表达式计算
	 */
	private String fileName = null;
	
	/**
	 * 可用的数据源集合（数据源名和数据源语句映射）
	 */
	private Map<String, String> dataSourceMap = new HashMap<String, String>();
	
	/**
	 * 各个数据源可用配置参数集合，key值类似于'table_xxx', 'row_xxx', 'cell_xxx'
	 */
	private Map<String, Map<String, String>>configMap = new HashMap<String, Map<String, String>>();
	
	/**
	 * 检索数据源前需要执行的存储过程
	 */
	private String beforeExecSql = null;
	
	/**
	 * 报表数据生成完毕后需要执行的存储过程
	 */
	private String afterExecSql = null;
	
	/**
	 * 可用的报表体集合（报表类型－报表体配置集合）
	 */
	private List<Object> contexts = new ArrayList<Object>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Map<String, String> getDataSourceMap() {
		return dataSourceMap;
	}
	
	public String getDataSource(String name) {
		return dataSourceMap.get(name);
	}
	
	public void setDataSourceMap(Map<String, String> dataSourceMap) {
		this.dataSourceMap = dataSourceMap;
	}

	public void addDataSource(String name, String context) {
		dataSourceMap.put(name, context);
	}

	public Map<String, Map<String, String>> getConfigMap() {
		return configMap;
	}

	public Map<String, String> getConfig(String configName) {
		return configMap.get(configName);
	}

	public void setConfigMap(Map<String, Map<String, String>> configMap) {
		this.configMap = configMap;
	}

	public void addConfig(String configName, String paramName, String paramValue) {
		Map<String, String> map = configMap.get(configName);
		if (map == null) {
			map = new HashMap<String, String>();
			
			configMap.put(configName, map);
		}
		
		map.put(paramName, paramValue);
	}

	public String getBeforeExecSql() {
		return beforeExecSql;
	}

	public void setBeforeExecSql(String beforeExecSql) {
		this.beforeExecSql = beforeExecSql;
	}

	public String getAfterExecSql() {
		return afterExecSql;
	}

	public void setAfterExecSql(String afterExecSql) {
		this.afterExecSql = afterExecSql;
	}

	public List<Object> getContexts() {
		return contexts;
	}

	public void setContexts(List<Object> contexts) {
		this.contexts = contexts;
	}

	public void addContext(Object context) {
		contexts.add(context);
	}

	@Override
	public ReportConfig clone() throws CloneNotSupportedException {
		ReportConfig clone = (ReportConfig)super.clone();
		
		if (dataSourceMap.size() > 0) {
			Map<String, String> cloneDataSourceMap = new HashMap<String, String>();
			cloneDataSourceMap.putAll(dataSourceMap);
			clone.setDataSourceMap(cloneDataSourceMap);
		}
			
		if (configMap.size() > 0) {
			Map<String, Map<String, String>> cloneConfigMap = new HashMap<String, Map<String, String>>();
			cloneConfigMap.putAll(configMap);
			clone.setConfigMap(cloneConfigMap);
		}
			
		if (contexts.size() > 0) {
			List<Object> cloneContexts = new ArrayList<Object>();
			for (Object obj : contexts ) {
				if (obj instanceof Table)
					cloneContexts.add(((Table)obj).clone());
			}
			clone.setContexts(cloneContexts);
		}
		
		return clone;
	}
}
