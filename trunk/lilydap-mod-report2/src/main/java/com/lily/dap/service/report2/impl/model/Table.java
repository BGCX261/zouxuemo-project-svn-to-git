/**
 * 
 */
package com.lily.dap.service.report2.impl.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

/**
 * @author xuemozou
 *
 * ���ݱ�������
 */
public class Table implements Cloneable {
	/**
	 * Ψһ����ֵ
	 */
	private String uid;
	
	/**
	 * ���ݱ����и��еĿ��
	 */
	private List<Integer> widths = new ArrayList<Integer>();
	
	/**
	 * ������ʽ�࣬��������������н��̳б���ָ������ʽ
	 */
	private String cssClass = null;
	
	/**
	 * ������ʽ��񣬱�������������н��̳б���ָ������ʽ���
	 */
	private String cssStyle = null;
	
	/**
	 * ����Ǳ��ӳ�䣬��ָ��ӳ�������Դ
	 */
	private List<String> datasources = new ArrayList<String>();
	
	/**
	 * ���ֵΪ0ʱ��ʾ��ֵ��Ĭ��ʲôҲ����ʾ
	 */
	private String displayZero = null;
	
	/**
	 * ������������
	 */
	private List<Row> data = new ArrayList<Row>();
	
	/**
	 * ��Ŵ�ͼ��ĵ�Ԫ���б�
	 */
	private List<Cell> chartCellList = new ArrayList<Cell>();

	public Table() {
		uid = RandomStringUtils.randomAscii(16);
	}

	public Table(List<Integer> widths, String cssClass, String cssStyle,
			List<String> datasources, String displayZero) {
		this.widths = widths;
		this.cssClass = cssClass;
		this.cssStyle = cssStyle;
		this.datasources = datasources;
		this.displayZero = displayZero;
		
		uid = RandomStringUtils.randomAscii(16);
	}

	public String getUid() {
		return uid;
	}

	public List<Integer> getWidths() {
		return widths;
	}

	public void setWidths(List<Integer> widths) {
		this.widths = widths;
	}

	public void addWidth(int width) {
		this.widths.add(width);
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

	public List<String> getDatasources() {
		return datasources;
	}

	public void setDatasources(List<String> datasources) {
		this.datasources = datasources;
	}

	public void addDatasource(String datasource) {
		this.datasources.add(datasource);
	}

	public String getDisplayZero() {
		return displayZero;
	}

	public void setDisplayZero(String displayZero) {
		this.displayZero = displayZero;
	}

	public List<Row> getData() {
		return data;
	}
	
	public void addChartCellList(Cell cell) {
		chartCellList.add(cell);
	}
	
	public List<Cell> getChartCellList() {
		return chartCellList;
	}
	
	public void clearChartCellList() {
		chartCellList.clear();
	}

	public Row getRow(int row) {
		if (row < 0 || row >= data.size())
			return null;
		
		return data.get(row);
	}

	public void setData(List<Row> data) {
		this.data = data;
	}
	
	public void addData(Row row) {
		this.data.add(row);
	}

	public int rowCount() {
		return data.size();
	}

	public int colCount() {
		return widths.size();
	}
	
	public Table clone() throws CloneNotSupportedException {
		Table clone = (Table)super.clone();
		clone.uid = RandomStringUtils.randomAscii(16);
		
		if (widths.size() > 0) {
			List<Integer> cloneWidths = new ArrayList<Integer>();
			cloneWidths.addAll(widths);
			clone.setWidths(cloneWidths);
		}
		
		if (datasources.size() > 0) {
			List<String> cloneDatasources = new ArrayList<String>();
			cloneDatasources.addAll(datasources);
			clone.setDatasources(cloneDatasources);
		}
		
		if (data.size() > 0) {
			List<Row> cloneRow = new ArrayList<Row>();
			for (Row row : data)
				cloneRow.add(row.clone());
			clone.setData(cloneRow);
		}
		
		return clone;
	}
}
