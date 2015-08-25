package com.lily.dap.service.report2.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lily.dap.service.report2.impl.model.Table;

/**
 * @author Administrator
 *
 *	����ģ�����ýṹ��
 */
public class ReportConfig implements Cloneable {
	/**
	 * ������
	 */
	private String name = "";
	
	/**
	 * ����˵��
	 */
	private String des = "";
	
	/**
	 * �����ļ����ļ�����֧�ֱ��ʽ����
	 */
	private String fileName = null;
	
	/**
	 * ���õ�����Դ���ϣ�����Դ��������Դ���ӳ�䣩
	 */
	private Map<String, String> dataSourceMap = new HashMap<String, String>();
	
	/**
	 * ��������Դ�������ò������ϣ�keyֵ������'table_xxx', 'row_xxx', 'cell_xxx'
	 */
	private Map<String, Map<String, String>>configMap = new HashMap<String, Map<String, String>>();
	
	/**
	 * ��������Դǰ��Ҫִ�еĴ洢����
	 */
	private String beforeExecSql = null;
	
	/**
	 * ��������������Ϻ���Ҫִ�еĴ洢����
	 */
	private String afterExecSql = null;
	
	/**
	 * ���õı����弯�ϣ��������ͣ����������ü��ϣ�
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
