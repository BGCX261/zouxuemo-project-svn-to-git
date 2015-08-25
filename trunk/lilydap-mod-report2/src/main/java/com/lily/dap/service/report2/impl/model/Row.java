package com.lily.dap.service.report2.impl.model;

import java.util.ArrayList;
import java.util.List;

public class Row implements Cloneable {
	/**
	 * <code>parent</code> 行所在表
	 */
	Table parent = null;
	
	/**
	 * 本行高度
	 */
	private int height = 25;
	
	/**
	 * 报表样式类，本行包含的单元格将继承本行指定的样式
	 */
	private String cssClass = null;
	
	/**
	 * 报表样式风格，本行包含的单元格将继承本行指定的样式风格
	 */
	private String cssStyle = null;

	/**
	 * 如果是行映射，则指定映射的数据源
	 */
	private String datasource = null;
	
	/**
	 * 本行包含的所有单元格
	 */
	private List<Cell> data = new ArrayList<Cell>();
	
	/**
	 * 针对行映射模式，如果行内的单元格有分组信息，则存放所有分组ID字符串
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
