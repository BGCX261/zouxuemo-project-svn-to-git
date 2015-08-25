package com.lily.dap.service.report2.impl.model;

import java.util.ArrayList;
import java.util.List;

public class Row implements Cloneable {
	/**
	 * <code>parent</code> �����ڱ�
	 */
	Table parent = null;
	
	/**
	 * ���и߶�
	 */
	private int height = 25;
	
	/**
	 * ������ʽ�࣬���а����ĵ�Ԫ�񽫼̳б���ָ������ʽ
	 */
	private String cssClass = null;
	
	/**
	 * ������ʽ��񣬱��а����ĵ�Ԫ�񽫼̳б���ָ������ʽ���
	 */
	private String cssStyle = null;

	/**
	 * �������ӳ�䣬��ָ��ӳ�������Դ
	 */
	private String datasource = null;
	
	/**
	 * ���а��������е�Ԫ��
	 */
	private List<Cell> data = new ArrayList<Cell>();
	
	/**
	 * �����ӳ��ģʽ��������ڵĵ�Ԫ���з�����Ϣ���������з���ID�ַ���
	 */
	private List<String> groupIds = new ArrayList<String>();

	public Row() {
	}

	public Row(int height, String cssClass, String cssStyle, String datasource) {
		this.height = height;
		this.cssClass = cssClass;
		this.cssStyle = cssStyle;
		this.datasource = datasource;
	}

	public Table getParent() {
		return parent;
	}

	public void setParent(Table parent) {
		this.parent = parent;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public List<Cell> getData() {
		return data;
	}
	
	public void addData(Cell cell) {
		this.data.add(cell);
	}

	public void setData(List<Cell> data) {
		this.data = data;
	}
	
	public int cellCount() {
		return data.size();
	}

	public List<String> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<String> groupIds) {
		this.groupIds = groupIds;
	}

	public void addGroupId(String groupId) {
		if (!groupIds.contains(groupId))
			groupIds.add(groupId);
	}
	
	public Row clone() throws CloneNotSupportedException {
		Row clone = (Row)super.clone();
		
		if (groupIds.size() > 0) {
			List<String> cloneGroupIds = new ArrayList<String>();
			cloneGroupIds.addAll(groupIds);
			clone.setGroupIds(cloneGroupIds);
		}
		
		if (data.size() > 0) {
			List<Cell> cloneData = new ArrayList<Cell>();
			for (Cell cell : data)
				cloneData.add(cell.clone());
			clone.setData(cloneData);
		}
		
		return clone;
	}
}
