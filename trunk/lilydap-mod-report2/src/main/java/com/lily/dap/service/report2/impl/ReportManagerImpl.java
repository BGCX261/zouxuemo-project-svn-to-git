/**
 * 
 */
package com.lily.dap.service.report2.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.lily.dap.dao.Condition;
import com.lily.dap.service.core.BaseManager;
import com.lily.dap.service.core.Evaluator.EvaluatException;
import com.lily.dap.service.report2.ReportManager;
import com.lily.dap.service.report2.impl.chart.ChartProxy;
import com.lily.dap.service.report2.impl.chart.ChartStrategy;
import com.lily.dap.service.report2.impl.model.Cell;
import com.lily.dap.service.report2.impl.model.CssClass;
import com.lily.dap.service.report2.impl.model.Table;
import com.lily.dap.util.Util;

/**
 * @author xuemozou
 *
 * 报表管理实现，实现报表环境的初始化、报表的装载和指定格式的输入
 */
@Service("report2Manager")
public class ReportManagerImpl extends BaseManager implements ReportManager {
	private static ReportFieldTokenProcessor fieldTokenProcessor = new ReportFieldTokenProcessor('{', '}');
	
    protected final Log logger = LogFactory.getLog(getClass());
    
    private String resource;

    private ChartProxy chartProxy = new ChartProxy();
    
    private Map<String, TableChartCacheNode> tableChartCacheMap = new HashMap<String, TableChartCacheNode>();
    
    /**
     * 存放表格样式配置（样式名和样式内容映射）
     */
    private Map<String, CssClass> cssClassMap = new HashMap<String, CssClass>();
    
	/**
	 * 存放报表配置集合（配置名和配置信息映射）
	 */
	private Map<String, ReportConfig> reportConfigMap = new HashMap<String, ReportConfig>();

	/**
	 * 设置 resource 值为 <code>resource</code>.
	 * @param resource 要设置的 resource 值
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}
	
	/**
	 * 设置 chartStrategys 值为 <code>chartStrategys</code>.
	 * @param chartStrategys
	 */
	public void setChartStrategys(Set<ChartStrategy> chartStrategys) {
		for (ChartStrategy chartStrategy : chartStrategys)
			chartProxy.addChartStrategy(chartStrategy);
	}

    public final void init() throws Exception {
        DocumentBuilder documentBuilder = createDocumentBuilder();
        
    	if (StringUtils.endsWithIgnoreCase(resource, ".xml")) {	//报表配置指定classpath下某个xml文件
            InputStream is = Util.getInputStream(resource);
            if (is == null) 
                throw new Exception("在路径'" + resource + "'下不能找到报表生成器配置文件！");

            readReport(documentBuilder, resource, is);
    	} else {	//报表配置指定classpath下某个目录的所有xml文件
    		URL url = Util.getResource(resource);
            if (url == null) 
                throw new Exception("在路径'" + resource + "'下不能找到报表生成器配置文件！");
            
            String path = url.getFile();
            path = java.net.URLDecoder.decode(path, "UTF-8");
            
    		File file = new File(path);
    		if (!file.isDirectory())
    			throw new Exception("要求报表配置必须是一个xml文件或者指定一个目录！");
    		
    		//遍历报表目录下的所有xml文件
			File[] xmlFiles = file.listFiles(new FileFilter() {
				public boolean accept(File pathname) {
					return StringUtils.endsWithIgnoreCase(pathname.getName(), ".xml");
				}
			});
			
			//循环从xml文件中读出报表配置信息
			for (File xmlFile : xmlFiles) {
				InputStream is = new FileInputStream(xmlFile);
				
				readReport(documentBuilder, resource + xmlFile.getName(), is);
			}
    	}
    }
    
    private DocumentBuilder createDocumentBuilder() throws Exception {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);

            DocumentBuilder documentBuilder;

            try {
            	documentBuilder = dbf.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                throw new Exception("Error creating document builder", e);
            }
            
            return documentBuilder;
        } catch (Exception e) {
            throw new Exception("Error in report config", e);
        }
    }
    
    private void readReport(DocumentBuilder documentBuilder, String filename, InputStream is) throws Exception {
        logger.info("读取报表生成器配置文件[" + filename + "]...");

        try {
            Document doc = documentBuilder.parse(is);
    		Element rooterElement = doc.getDocumentElement();

    		/*
    		 * 遍历所有的style元素，进行构建style元素处理
    		 */
    		NodeList styleList = rooterElement.getElementsByTagName("style");
    		if (styleList.getLength() > 0) {
    			Element styleElement = (Element)styleList.item(0);
    			
    			ReportTemplateParser.parseCssClass(cssClassMap, styleElement);
    		}
     		
    		/*
    		 * 遍历所有的report元素，进行构建report元素处理
    		 */
    		NodeList reportList = rooterElement.getElementsByTagName("report");
    		for(int i = 0; i < reportList.getLength(); i++){
    			Element reportElement = (Element)reportList.item(i);
    			
    			//分析报表模版XML数据，构建报表模版配置数据
    			ReportConfig reportConfig = ReportTemplateParser.parseReportConfig(reportElement);
    			
    			reportConfigMap.put(reportConfig.getName(), reportConfig);
    		}
        } catch (Exception e) {
            throw new Exception("Error in report config", e);
        }
    }
    
	/* (non-Javadoc)
	 * @see com.lily.dap.service.report2.ReportManager#getReportConfigDes(java.lang.String)
	 */
	public String getReportConfigDes(String name) {
    	ReportConfig reportConfig = getReportConfig(name);
    	if (reportConfig == null)
    		return null;
    	else
    		return reportConfig.getDes();
	}

	public String getReportExportFileName(String name, Map<String, Object> paramMap) {
    	ReportConfig reportConfig = getReportConfig(name);
    	if (reportConfig == null)
    		return null;
    	
    	if (reportConfig.getFileName() == null)
    		return reportConfig.getDes();
    	
    	String exportFileName = reportConfig.getFileName();
		try {
			exportFileName = fieldTokenProcessor.evaluateFieldToken(exportFileName, paramMap);
		} catch (EvaluatException e1) {}
    	
		return exportFileName;
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.report2.ReportManager#getReportConfigNames()
	 */
	public String[] getReportConfigNames() {
    	String[] names = new String[reportConfigMap.size()];
    	
    	Iterator<String> it = reportConfigMap.keySet().iterator();
    	int i = 0;
    	
    	while (it.hasNext())
    		names[i++] = it.next();
    	
    	return names;
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.report2.ReportManager#addCssClass(com.lily.dap.service.report2.impl.model.CssClass)
	 */
	public void addCssClass(CssClass cssClass) {
		String className = cssClass.getClassName();
		
		cssClassMap.put(className, cssClass);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.report2.ReportManager#extendCssClass(java.lang.String, java.lang.String)
	 */
	public CssClass extendCssClass(String className, String extendClassName) {
		CssClass extendClass = getCssClass(extendClassName);
		if (extendClass == null)
			return null;
		
		CssClass cssClass = null;
		try {
			cssClass = extendClass.clone();
		} catch (CloneNotSupportedException e) {}
		
		if (cssClass != null) {
			cssClass.setClassName(className);
			cssClass.setExtend(extendClassName);
		}
		
		return cssClass;
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.report2.ReportManager#getCssClass(java.lang.String)
	 */
	public CssClass getCssClass(String className) {
		return cssClassMap.get(className);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.report2.ReportManager#removeCssClass(java.lang.String)
	 */
	public CssClass removeCssClass(String className) {
		return cssClassMap.remove(className);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.report2.ReportManager#cloneReportConfig(java.lang.String)
	 */
	public ReportConfig cloneReportConfig(String name) {
		ReportConfig reportConfig = getReportConfig(name);
		if (reportConfig != null)
			try {
				reportConfig= reportConfig.clone();
			} catch (CloneNotSupportedException e) {}
				
		return reportConfig;
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.report2.ReportManager#getReportConfig(java.lang.String)
	 */
	public ReportConfig getReportConfig(String name) {
   		return reportConfigMap.get(name);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.report2.ReportManager#outReport(java.lang.String, java.lang.String, java.util.Map, java.io.OutputStream)
	 */
	public void outReport(String reportTemplateName, String reportType, Condition condition, 
			Map<String, Object> paramMap, OutputStream os) {
		ReportConfig reportConfig = getReportConfig(reportTemplateName);
		if (reportConfig == null)
			throw new RuntimeException("报表模板" + reportTemplateName + "不存在，请检查要打开的报表模板名称是否正确！");
		
		outReport(reportConfig, reportType, condition, paramMap, os);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.report2.ReportManager#outReport(com.lily.dap.service.report2.impl.ReportConfig, java.lang.String, java.util.Map, java.io.OutputStream)
	 */
	public void outReport(ReportConfig reportConfig, String reportType, Condition condition, 
			Map<String, Object> paramMap, OutputStream os) {
		//在检索数据以前执行SQL更新操作
		execSql(reportConfig.getBeforeExecSql(), paramMap);
		
		Map<String, List<Map<String, Object>>> datasourceMap = new HashMap<String, List<Map<String, Object>>>();
		for (String name : reportConfig.getDataSourceMap().keySet()) {
			String datasource = reportConfig.getDataSourceMap().get(name);
			
			//执行报表数据源的SQL语句，返回结果集转换的Map集合
			List<Map<String, Object>> dataList = querySql(datasource, condition, paramMap);
			datasourceMap.put(name, dataList);
		}
		
		outReportWithData(reportConfig, reportType, paramMap, datasourceMap, os);
		
		//在报表输出以后执行SQL更新操作
		execSql(reportConfig.getAfterExecSql(), paramMap);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.report2.ReportManager#outReportWithData(com.lily.dap.service.report2.impl.ReportConfig, java.lang.String, java.util.Map, java.util.Map, java.io.OutputStream)
	 */
	public void outReportWithData(ReportConfig reportConfig, String reportType,
			Map<String, Object> paramMap,
			Map<String, List<Map<String, Object>>> datasourceMap, OutputStream os) {
		Map<String, Map<String, String>>configMap = reportConfig.getConfigMap();
		Table template = (Table)reportConfig.getContexts().get(0);
		
		//根据模板、数据源、配置Map生成报表结构
		Table table = new ReportBuilder(chartProxy).generateTableData(template, datasourceMap, configMap, paramMap);
		if (table.getChartCellList().size() > 0) {
			clearTableChartCache(60);
			
			TableChartCacheNode node = new TableChartCacheNode(table.getUid());
			for (Cell cell : table.getChartCellList())
				node.addChartCell(cell);

			tableChartCacheMap.put(table.getUid(), node);
		}
		
		//对报表数据进行润色，根据要输出的报表类型输出HTML或者Excel、Word等报表输出格式
		ReportRender.renderReport(reportConfig.getDes(), table, cssClassMap, reportType, configMap, os);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.report2.ReportManager#outChart(java.lang.String, int, java.io.OutputStream)
	 */
	public void outChart(String tableUID, int chartUID, OutputStream os) {
		TableChartCacheNode node = tableChartCacheMap.get(tableUID);
		if (node == null)
			throw new RuntimeException("要输出图表所在的报表未找到！");

		Cell chartCell = node.chartCellMap.get(chartUID);
		if (chartCell == null)
			throw new RuntimeException("要输出图表未找到！");
		
		byte[] data = (byte[])chartCell.getContext();
		try {
			os.write(data);
		} catch (IOException e) {
			throw new RuntimeException("输出图表失败－" + e.getMessage());
		}
	}

	private void execSql(String sql, Map<String, Object> paramMap) {
		if (sql == null || "".equals(sql))
			return;
			
		try {
			sql = fieldTokenProcessor.evaluateFieldToken(sql, paramMap);
		} catch (EvaluatException e1) {}
		
		try {
			dao.sqlExecute(sql);
		} catch (Exception e) {
			logger.error("执行SQL语句[" + sql + "]错误－" + e.toString());
		}
	}
	
	private List<Map<String, Object>> querySql(String sql, Condition condition, Map<String, Object> paramMap) {
		if (sql == null || "".equals(sql))
			return new ArrayList<Map<String, Object>>();
		
		try {
			sql = fieldTokenProcessor.evaluateFieldToken(sql, paramMap);
		} catch (EvaluatException e2) {}
		
		return dao.sqlQuery(sql, condition);
	}
	
	private void clearTableChartCache(int clearMinute) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -clearMinute);
		Date limitTime = calendar.getTime();
		
		Map<String, TableChartCacheNode> newTableChartCacheMap = new HashMap<String, TableChartCacheNode>();
		for (TableChartCacheNode node : tableChartCacheMap.values()) {
			Date cacheTime = node.cacheTime;
			if (cacheTime.getTime() >= limitTime.getTime() )
				newTableChartCacheMap.put(node.tableUid, node);
		}
		
		tableChartCacheMap.clear();
		tableChartCacheMap = newTableChartCacheMap;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("A");
		list.add(0, "B");
		
		for (String s : list)
			System.out.println(s);
		System.out.println("--------------------");
		
		list.add(1, "C");
		
		for (String s : list)
			System.out.println(s);
		System.out.println("--------------------");
		
		list.add(3, "D");
		
		for (String s : list)
			System.out.println(s);
		
		list.add(5, "D");
	}
	
	class TableChartCacheNode {
		public String tableUid;
		
		public Map<Integer, Cell> chartCellMap = new HashMap<Integer, Cell>();
		
		public Date cacheTime = new Date();

		public TableChartCacheNode(String tableUid) {
			this.tableUid = tableUid;
		}
		
		public void addChartCell(Cell cell) {
			int uid = cell.getChart().getUid();
			
			chartCellMap.put(uid, cell);
		}
	}
}
