package com.lily.dap.service.report2.impl.model;

public class Cell implements Cloneable {
	/**
	 * <code>table</code> ��Ԫ��������
	 */
	private Row parent = null;
	
	/**
	 * ��������
	 */
	private int rowSpan = 1;
	
	/**
	 * ��������
	 */
	private int colSpan = 1;
	
	/**
	 * ��ǰ��Ԫ���������������飬�ڲ�������ʱ�����ڲ��������������齫����չcolSpan������ƥ��ÿ�е�����
	 */
	private String colgroup = null;
	
	/**
	 * ������ʽ��
	 */
	private String cssClass = null;
	
	/**
	 * ������ʽ���
	 */
	private String cssStyle = null;
	
	/**
	 * ��Ԫ���ȣ�����������ʱ�����ʵ�ʿ�ȣ�
	 */
	private int width = 0;
	
	/**
	 * ��Ԫ��߶ȣ�����������ʱ�����ʵ�ʸ߶ȣ�
	 */
	private int height = 0;
	
	/**
	 * ����ǵ�Ԫ��ӳ�䣬��ӳ������Դ
	 */
	private String datasource = null;
	
	/**
	 * ӳ������Դ�ֶ�
	 */
	private String property = null;
	
	/**
	 * ����ж����ӳ�䣬���Ӧ�ı�ӳ����
	 */
	private String owner = null;
	
	/**
	 * ����ǵ�Ԫ��ӳ�䣬��Ӧ��Ψһֵ
	 */
	private String uniqueValue = null;
	
	/**
	 * ��ǰ��Ԫ����������ID�ַ������Կո�ͷ�ͽ�β�������ID֮���ÿո�ָ�
	 */
	private String groupIds = null;
	
	/**
	 * �Ƿ�յ�Ԫ��ϲ�����ǰ�����ĵ�Ԫ��ϲ���
	 */
	private boolean mergeblank = false;
	
	/**
	 * �����Ҫ������ֵ���ȣ���Ϊ����ֵ������Ϊ-1
	 */
	private int scale = -1;
	
	/**
	 * ���ֵΪ0ʱ��ʾ��ֵ��Ĭ��ʲôҲ����ʾ
	 */
	private String displayZero = null;
	
	/**
	 * ��Ⱦ�ű����룬�����ڴ˶���������ݽ��д���
	 */
	private String renderer = null;

	/**
	 * ���HTMLʱ���Ƿ���Ҫ����HTMLת�� 
	 */
	private boolean html = false;
	
	/**
	 * �����Ҫת����ʽ�����ʽ���ַ���
	 */
	private String format = null;
	
	/**
	 * ��Ԫ�����ݣ�������һ���ַ�����������Chartͼ��
	 */
	private Object context = "";
	
	/**
	 * �����Ԫ����ͼ�����ṩͼ��������Ϣ
	 */
	private Chart chart = null;

	public Cell() {
	}

	public Cell(int rowSpan, int colSpan, String colgroup, String cssClass, String context) {
		this.rowSpan = rowSpan;
		this.colSpan = colSpan;
		this.colgroup = colgroup;
		this.cssClass = cssClass;
		this.context = context;
	}

	public Cell(String cssClass, String property) {
		this.cssClass = cssClass;
		this.property = property;
	}

	public Cell(String cssClass, String datasource, String uniqueValue) {
		this.cssClass = cssClass;
		this.datasource = datasource;
		this.uniqueValue = uniqueValue;
	}

	public Cell(int rowSpan, int colSpan, String cssClass, Chart chart) {
		this.rowSpan = rowSpan;
		this.colSpan = colSpan;
		this.cssClass = cssClass;
		this.chart = chart;
	}

	public Cell(int rowSpan, int colSpan, String cssClass, String cssStyle,
			String datasource, String property, String owner,
			String uniqueValue, int scale, String format, String context, String renderer) {
		this.rowSpan = rowSpan;
		this.colSpan = colSpan;
		this.cssClass = cssClass;
		this.cssStyle = cssStyle;
		this.datasource = datasource;
		this.property = property;
		this.owner = owner;
		this.uniqueValue = uniqueValue;
		this.scale = scale;
		this.format = format;
		this.context = context;
		this.renderer = renderer;
	}

	public Row getParent() {
		return parent;
	}

	public void setParent(Row parent) {
		this.parent = parent;
	}

	public int getRowSpan() {
		return rowSpan;
	}

	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}

	public int getColSpan() {
		return colSpan;
	}

	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}

	public String getColgroup() {
		return colgroup;
	}

	public void setColgroup(String colgroup) {
		this.colgroup = colgroup;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getDatasource() {
		return datasource;
	}

	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getUniqueValue() {
		return uniqueValue;
	}

	public void setUniqueValue(String uniqueValue) {
		this.uniqueValue = uniqueValue;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public boolean isMergeblank() {
		return mergeblank;
	}

	public void setMergeblank(boolean mergeblank) {
		this.mergeblank = mergeblank;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public String getDisplayZero() {
		return displayZero;
	}

	public void setDisplayZero(String displayZero) {
		this.displayZero = displayZero;
	}
	public String getRenderer() {
		return renderer;
	}

	public void setRenderer(String renderer) {
		this.renderer = renderer;
	}

	public boolean isHtml() {
		return html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}
	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Object getContext() {
		return context;
	}

	public void setContext(Object context) {
		this.context = context;
	}

	public Chart getChart() {
		return chart;
	}

	public void setChart(Chart chart) {
		this.chart = chart;
	}

	public Cell clone() throws CloneNotSupportedException {
		Cell clone = (Cell)super.clone();
		if (chart != null)
			clone.setChart(chart.clone());
		
		return clone;
	}
	
	public static void main(String[] args) throws CloneNotSupportedException {
		Chart chart = new Chart("bar3d", "datasource");
		Cell cell = new Cell(2, 2, "chart", chart);
		Cell clone = cell.clone();
		clone.getChart().setType("pie3d");
		System.out.println(cell.getChart().getType());
	}
}
