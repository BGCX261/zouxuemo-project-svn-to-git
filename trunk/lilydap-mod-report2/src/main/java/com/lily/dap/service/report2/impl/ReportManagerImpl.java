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
 * �������ʵ�֣�ʵ�ֱ������ĳ�ʼ���������װ�غ�ָ����ʽ������
 */
@Service("report2Manager")
public class ReportManagerImpl extends BaseManager implements ReportManager {
	private static ReportFieldTokenProcessor fieldTokenProcessor = new ReportFieldTokenProcessor('{', '}');
	
    protected final Log logger = LogFactory.getLog(getClass());
    
    private String resource;

    private ChartProxy chartProxy = new ChartProxy();
    
    private Map<String, TableChartCacheNode> tableChartCacheMap = new HashMap<String, TableChartCacheNode>();
    
    /**
     * ��ű����ʽ���ã���ʽ������ʽ����ӳ�䣩
     */
    private Map<String, CssClass> cssClassMap = new HashMap<String, CssClass>();
    
	/**
	 * ��ű������ü��ϣ���������������Ϣӳ�䣩
	 */
	private Map<String, ReportConfig> reportConfigMap = new HashMap<String, ReportConfig>();

	/**
	 * ���� resource ֵΪ <code>resource</code>.
	 * @param resource Ҫ���õ� resource ֵ
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}
	
	/**
	 * ���� chartStrategys ֵΪ <code>chartStrategys</code>.
	 * @param chartStrategys
	 */
	public void setChartStrategys(Set<ChartStrategy> chartStrategys) {
		for (ChartStrategy chartStrategy : chartStrategys)
			chartProxy.addChartStrategy(chartStrategy);
	}

    public final void init() throws Exception {
        DocumentBuilder documentBuilder = createDocumentBuilder();
        
    	if (StringUtils.endsWithIgnoreCase(resource, ".xml")) {	//��������ָ��classpath��ĳ��xml�ļ�
            InputStream is = Util.getInputStream(resource);
            if (is == null) 
                throw new Exception("��·��'" + resource + "'�²����ҵ����������������ļ���");

            readReport(documentBuilder, resource, is);
    	} else {	//��������ָ��classpath��ĳ��Ŀ¼������xml�ļ�
    		URL url = Util.getResource(resource);
            if (url == null) 
                throw new Exception("��·��'" + resource + "'�²����ҵ����������������ļ���");
            
            String path = url.getFile();
            path = java.net.URLDecoder.decode(path, "UTF-8");
            
    		File file = new File(path);
    		if (!file.isDirectory())
    			throw new Exception("Ҫ�󱨱����ñ�����һ��xml�ļ�����ָ��һ��Ŀ¼��");
    		
    		//��������Ŀ¼�µ�����xml�ļ�
			File[] xmlFiles = file.listFiles(new FileFilter() {
				public boolean accept(File pathname) {
					return StringUtils.endsWithIgnoreCase(pathname.getName(), ".xml");
				}
			});
			
			//ѭ����xml�ļ��ж�������������Ϣ
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
        logger.info("��ȡ���������������ļ�[" + filename + "]...");

        try {
            Document doc = documentBuilder.parse(is);
    		Element rooterElement = doc.getDocumentElement();

    		/*
    		 * �������е�styleԪ�أ����й���styleԪ�ش���
    		 */
    		NodeList styleList = rooterElement.getElementsByTagName("style");
    		if (styleList.getLength() > 0) {
    			Element styleElement = (Element)styleList.item(0);
    			
    			ReportTemplateParser.parseCssClass(cssClassMap, styleElement);
    		}
     		
    		/*
    		 * �������е�reportԪ�أ����й���reportԪ�ش���
    		 */
    		NodeList reportList = rooterElement.getElementsByTagName("report");
    		for(int i = 0; i < reportList.getLength(); i++){
    			Element reportElement = (Element)reportList.item(i);
    			
    			//��������ģ��XML���ݣ���������ģ����������
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
			throw new RuntimeException("����ģ��" + reportTemplateName + "�����ڣ�����Ҫ�򿪵ı���ģ�������Ƿ���ȷ��");
		
		outReport(reportConfig, reportType, condition, paramMap, os);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.report2.ReportManager#outReport(com.lily.dap.service.report2.impl.ReportConfig, java.lang.String, java.util.Map, java.io.OutputStream)
	 */
	public void outReport(ReportConfig reportConfig, String reportType, Condition condition, 
			Map<String, Object> paramMap, OutputStream os) {
		//�ڼ���������ǰִ��SQL���²���
		execSql(reportConfig.getBeforeExecSql(), paramMap);
		
		Map<String, List<Map<String, Object>>> datasourceMap = new HashMap<String, List<Map<String, Object>>>();
		for (String name : reportConfig.getDataSourceMap().keySet()) {
			String datasource = reportConfig.getDataSourceMap().get(name);
			
			//ִ�б�������Դ��SQL��䣬���ؽ����ת����Map����
			List<Map<String, Object>> dataList = querySql(datasource, condition, paramMap);
			datasourceMap.put(name, dataList);
		}
		
		outReportWithData(reportConfig, reportType, paramMap, datasourceMap, os);
		
		//�ڱ�������Ժ�ִ��SQL���²���
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
		
		//����ģ�塢����Դ������Map���ɱ���ṹ
		Table table = new ReportBuilder(chartProxy).generateTableData(template, datasourceMap, configMap, paramMap);
		if (table.getChartCellList().size() > 0) {
			clearTableChartCache(60);
			
			TableChartCacheNode node = new TableChartCacheNode(table.getUid());
			for (Cell cell : table.getChartCellList())
				node.addChartCell(cell);

			tableChartCacheMap.put(table.getUid(), node);
		}
		
		//�Ա������ݽ�����ɫ������Ҫ����ı����������HTML����Excel��Word�ȱ��������ʽ
		ReportRender.renderReport(reportConfig.getDes(), table, cssClassMap, reportType, configMap, os);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.report2.ReportManager#outChart(java.lang.String, int, java.io.OutputStream)
	 */
	public void outChart(String tableUID, int chartUID, OutputStream os) {
		TableChartCacheNode node = tableChartCacheMap.get(tableUID);
		if (node == null)
			throw new RuntimeException("Ҫ���ͼ�����ڵı���δ�ҵ���");

		Cell chartCell = node.chartCellMap.get(chartUID);
		if (chartCell == null)
			throw new RuntimeException("Ҫ���ͼ��δ�ҵ���");
		
		byte[] data = (byte[])chartCell.getContext();
		try {
			os.write(data);
		} catch (IOException e) {
			throw new RuntimeException("���ͼ��ʧ�ܣ�" + e.getMessage());
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
			logger.error("ִ��SQL���[" + sql + "]����" + e.toString());
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
