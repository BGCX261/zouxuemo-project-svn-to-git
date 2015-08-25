package com.lily.dap.service.report2.impl.model;

public class Cell implements Cloneable {
	/**
	 * <code>table</code> 单元格所在行
	 */
	private Row parent = null;
	
	/**
	 * 跨行数量
	 */
	private int rowSpan = 1;
	
	/**
	 * 跨列数量
	 */
	private int colSpan = 1;
	
	/**
	 * 当前单元格在列上所属的组，在插入新列时，对于插入新列所属的组将被扩展colSpan属性以匹配每行的列数
	 */
	private String colgroup = null;
	
	/**
	 * 报表样式类
	 */
	private String cssClass = null;
	
	/**
	 * 报表样式风格
	 */
	private String cssStyle = null;
	
	/**
	 * 单元格宽度（在生成数据时计算出实际宽度）
	 */
	private int width = 0;
	
	/**
	 * 单元格高度（在生成数据时计算出实际高度）
	 */
	private int height = 0;
	
	/**
	 * 如果是单元格映射，则映射数据源
	 */
	private String datasource = null;
	
	/**
	 * 映射数据源字段
	 */
	private String property = null;
	
	/**
	 * 如果有多个表映射，则对应的表映射名
	 */
	private String owner = null;
	
	/**
	 * 如果是单元格映射，对应的唯一值
	 */
	private String uniqueValue = null;
	
	/**
	 * 当前单元格所属的组ID字符串，以空格开头和结尾，多个组ID之间用空格分隔
	 */
	private String groupIds = null;
	
	/**
	 * 是否空单元格合并（与前或后面的单元格合并）
	 */
	private boolean mergeblank = false;
	
	/**
	 * 如果需要设置数值精度，则为精度值，否则为-1
	 */
	private int scale = -1;
	
	/**
	 * 如果值为0时显示的值，默认什么也不显示
	 */
	private String displayZero = null;
	
	/**
	 * 渲染脚本代码，可以在此对输出的数据进行处理
	 */
	private String renderer = null;

	/**
	 * 输出HTML时，是否需要进行HTML转码 
	 */
	private boolean html = false;
	
	/**
	 * 如果需要转换格式，则格式化字符串
	 */
	private String format = null;
	
	/**
	 * 单元格内容，可以是一个字符串，或者是Chart图像
	 */
	private Object context = "";
	
	/**
	 * 如果单元格存放图表，则提供图表配置信息
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
