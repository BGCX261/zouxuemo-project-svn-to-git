/**
 * 
 */
package com.lily.dap.service.report2.impl;

import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lily.dap.service.report2.impl.model.Cell;
import com.lily.dap.service.report2.impl.model.Chart;
import com.lily.dap.service.report2.impl.model.CssClass;
import com.lily.dap.service.report2.impl.model.Row;
import com.lily.dap.service.report2.impl.model.Table;

/**
 * @author xuemozou
 *
 * 报表模版分析器，分析模版数据，生成报表模版配置数据
 */
public class ReportTemplateParser {
    public static ReportConfig parseReportConfig(Element element) {
    	ReportConfig reportConfig = new ReportConfig();
    	
		String name = element.getAttribute("name");
		reportConfig.setName(name);
		
		String des = element.getAttribute("des");
		reportConfig.setDes(des);
		
		String filename = element.getAttribute("filename");
		if (filename != null && !"".equals(filename))
			reportConfig.setFileName(filename);
		
		/*
		 * 搜索datasource元素，读取datasource数据信息
		 */
		NodeList dataSourceList = element.getElementsByTagName("datasource");
		for(int i = 0, len1 = dataSourceList.getLength(); i < len1; i++){
			Element dataSourceElement = (Element)dataSourceList.item(i);
			
			String dataSourceName = dataSourceElement.getAttribute("name");
			if (dataSourceName == null)
				dataSourceName = "";
			
			String dataSourceContext = dataSourceElement.getTextContent();
			
			reportConfig.addDataSource(dataSourceName, dataSourceContext);
		}
		
		/*
		 * 搜索beforesql元素，读取beforesql数据信息
		 */
		NodeList beforeSQLList = element.getElementsByTagName("beforesql");
		if (beforeSQLList.getLength() > 0) {
			Element beforeSQLElement = (Element)beforeSQLList.item(0);
			
			String beforeSQL = beforeSQLElement.getTextContent();
			reportConfig.setBeforeExecSql(beforeSQL);
		}
		
		/*
		 * 搜索aftersql元素，读取aftersql数据信息
		 */
		NodeList afterSQLList = element.getElementsByTagName("aftersql");
		if (afterSQLList.getLength() > 0) {
			Element afterSQLElement = (Element)afterSQLList.item(0);
			
			String afterSQL = afterSQLElement.getTextContent();
			reportConfig.setAfterExecSql(afterSQL);
		}
		
		/*
		 * 搜索config元素，读取config数据信息
		 */
		NodeList configList = element.getElementsByTagName("config");
		for(int i = 0, len1 = configList.getLength(); i < len1; i++){
			Element configElement = (Element)configList.item(i);
			
			String configName = configElement.getAttribute("name");
		
			NodeList paramList = configElement.getElementsByTagName("param");
			for(int j = 0, len2 = paramList.getLength(); j < len2; j++){
				Element paramElement = (Element)paramList.item(j);
				
				String paramName = paramElement.getAttribute("name");
				String paramValue = paramElement.getTextContent();
				
				reportConfig.addConfig(configName, paramName, paramValue);
			}
		}
		
		Node node = element.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				Element e = (Element)node;
				
				String tagName = e.getTagName();
				if ("table".equals(tagName)) {
					Table table = parseTable(e);
					
					reportConfig.addContext(table);
				} else if ("graph".equals(tagName)) {
					// TODO 以后处理图表的配置
				}
			}
				
			node = node.getNextSibling();
		}
		
    	return reportConfig;
    }
    
    private static Table parseTable(Element element) {
    	Table table = new Table();
    	
		String datasource = element.getAttribute("datasource");
		if (datasource != null && !"".equals(datasource)) {
			String[] tmp = datasource.split(" ");
			for (String s : tmp) {
				if (!"".equals(s))
					table.addDatasource(s);
			}
		}
		
		String cssClass = element.getAttribute("class");
		table.setCssClass(cssClass);
		
		String cssStyle = element.getAttribute("style");
		table.setCssStyle(cssStyle);
		
		String displayzero = element.getAttribute("displayzero");
		if (displayzero != null)
			table.setDisplayZero(displayzero);
		
		/*
		 * 搜索colgroup元素，读取colgroup数据信息
		 */
		NodeList colgroupList = element.getElementsByTagName("colgroup");
		Element colgroupElement = (Element)colgroupList.item(0);
		NodeList colList = colgroupElement.getElementsByTagName("col");
		for(int i = 0, len1 = colList.getLength(); i < len1; i++){
			Element colElement = (Element)colList.item(i);
			
			String s = colElement.getAttribute("width");
			int width = Integer.parseInt(s);
			
			table.addWidth(width);
		}
		
		/*
		 * 搜索tr元素，读取tr数据信息
		 */
		NodeList trList = element.getElementsByTagName("tr");
		for(int i = 0, len1 = trList.getLength(); i < len1; i++){
			Row row = parseRow((Element)trList.item(i), table);
			
			table.addData(row);
		}
    	
    	return table;
    }
    
    private static Row parseRow(Element element, Table table) {
		Row row = new Row();
		row.setParent(table);
		
		String cssClass = element.getAttribute("class");
		row.setCssClass(cssClass);
		
		String cssStyle = element.getAttribute("style");
		row.setCssStyle(cssStyle);
		
		String s = element.getAttribute("height");
		if (s != null && !"".equals(s)) {
			int height = Integer.parseInt(s);
			row.setHeight(height);
		}
		
		String datasource = element.getAttribute("datasource");
		if (datasource != null && !"".equals(datasource))
			row.setDatasource(datasource);
		
		/*
		 * 搜索td元素，读取td数据信息
		 */
		NodeList tdList = element.getElementsByTagName("td");
		for(int j = 0, len2 = tdList.getLength(); j < len2; j++){
			Cell cell = parseCell((Element)tdList.item(j), row);
			cell.setParent(row);
			
			row.addData(cell);
		}
    	
    	return row;
    }
    
    private static Cell parseCell(Element element, Row row) {
		Cell cell = new Cell();
		
		String cssClass = element.getAttribute("class");
		cell.setCssClass(cssClass);
		
		String cssStyle = element.getAttribute("style");
		cell.setCssStyle(cssStyle);
		
		String s = element.getAttribute("rowspan");
		if (s != null && !"".equals(s)) {
			int rowSpan = Integer.parseInt(s);
			cell.setRowSpan(rowSpan);
		}
		
		s = element.getAttribute("colspan");
		if (s != null && !"".equals(s)) {
			int colSpan = Integer.parseInt(s);
			cell.setColSpan(colSpan);
		}
		
		String colgroup = element.getAttribute("colgroup");
		if (colgroup != null && !"".equals(colgroup))
			cell.setColgroup(colgroup);
		
		s = element.getAttribute("scale");
		if (s != null && !"".equals(s)) {
			int scale = Integer.parseInt(s);
			cell.setScale(scale);
		}
		
		//从配置读取值为0的显示内容，可以设置显示内容为指定值
		//为了兼容displayzero的以前用法，当未指定displayzero参数时，默认显示空字符串、当指定displayzero为true时，显示"0"字符
		s = element.getAttribute("displayzero");
		if (s != null) {
			String ss = s.toLowerCase();
			if (ss.equals("true"))
				cell.setDisplayZero("0");
			else if (ss.equals("false"))
				cell.setDisplayZero("");
			else
				cell.setDisplayZero(s);
		} else {
			cell.setDisplayZero("");
		}

		String datasource = element.getAttribute("datasource");
		if (datasource != null && !"".equals(datasource))
			cell.setDatasource(datasource);
		
		String owner = element.getAttribute("owner");
		if (owner != null && !"".equals(owner))
			cell.setOwner(owner);
			
		String property = element.getAttribute("property");
		if (property != null && !"".equals(property))
			cell.setProperty(property);
		
		String uniqueValue = element.getAttribute("uniqueValue");
		if (uniqueValue != null && !"".equals(uniqueValue))
			cell.setUniqueValue(uniqueValue);
		
		s = element.getAttribute("groupid");
		if (s != null && !"".equals(s)) {
			String groupids = " ";
			
			String[] tmp = s.split(" ");
			for (String groupid : tmp) {
				if (!"".equals(groupid)) {
					row.addGroupId(groupid);
				
					groupids += groupid + " ";
				}
			}
			
			cell.setGroupIds(groupids);
		}
		
		String mergeblank = element.getAttribute("mergeblank");
		if (mergeblank != null && "true".equals(mergeblank))
			cell.setMergeblank(true);
		
		String context = element.getTextContent();
		cell.setContext(context);
		
		s = element.getAttribute("html");
		if (s != null && !"".equals(s)) {
			boolean html = Boolean.parseBoolean(s);
			cell.setHtml(html);
		}
		
		/*
		 * 搜索renderer元素，读取脚本处理代码信息
		 */
		NodeList rendererList = element.getElementsByTagName("renderer");
		if (rendererList.getLength() > 0) {
			Element rendererElement = (Element) rendererList.item(0);

			String renderer = rendererElement.getTextContent();

			cell.setRenderer(renderer);
			
			return cell;
		}
		
		/*
		 * 搜索chart元素，读取chart数据信息
		 */
		NodeList chartList = element.getElementsByTagName("chart");
		if (chartList.getLength() > 0) {
			Element chartElement = (Element)chartList.item(0);
			
			Chart chart = parseChart(chartElement);
			cell.setChart(chart);
		}
    	
    	return cell;
    }
    
    public static Chart parseChart(Element element) {
		Chart chart = new Chart();
		
		String type = element.getAttribute("type");
		chart.setType(type);
		
		String datasource = element.getAttribute("datasource");
		chart.setDatasource(datasource);
		
		String s = element.getAttribute("width");
		if (s != null && !"".equals(s)) {
			float width = Float.parseFloat(s);
			chart.setWidth(width);
		}
		
		s = element.getAttribute("height");
		if (s != null && !"".equals(s)) {
			float height = Float.parseFloat(s);
			chart.setHeight(height);
		}
		
		/*
		 * 搜索param元素，读取param数据信息
		 */
		NodeList paramList = element.getElementsByTagName("param");
		for(int i = 0, len = paramList.getLength(); i < len; i++){
			Element paramElement = (Element)paramList.item(i);
			
			String name = paramElement.getAttribute("name");
			String value = paramElement.getTextContent();
			chart.addParam(name, value);
		}
		
		return chart;
    }
    
    public static void parseCssClass(Map<String, CssClass> classMap, Element element) {
		NodeList classList = element.getElementsByTagName("class");
		for(int i = 0, len1 = classList.getLength(); i < len1; i++){
			Element classElement = (Element)classList.item(i);
			
	    	String className = classElement.getAttribute("name");
	    	String extend = classElement.getAttribute("extend");
	    	CssClass cssClass = new CssClass(className, extend);
	    	
	    	if (extend != null && !"".equals(extend)) {
	    		String[] tmp = extend.split(" ");
	    		
	    		for (String s : tmp) {
	    			CssClass extendClass = classMap.get(s);
	    			if (extendClass != null)
	    				cssClass.addStyles(extendClass);
	    		}
	    	}
	    	
	    	NodeList styleList = classElement.getElementsByTagName("style");
			for(int j = 0, len2 = styleList.getLength(); j < len2; j++){
				Element styleElement = (Element)styleList.item(j);
				
				String styleName = styleElement.getAttribute("name");
				String styleType = styleElement.getAttribute("type");
				String styleScope = styleElement.getAttribute("scope");
				String context = styleElement.getTextContent();
				
				cssClass.addStyle(styleName, styleScope, styleType, context);
			}
			
			classMap.put(className, cssClass);
		}
    }
}
