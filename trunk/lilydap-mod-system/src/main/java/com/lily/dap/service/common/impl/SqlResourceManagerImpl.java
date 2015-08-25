package com.lily.dap.service.common.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.lily.dap.util.ResourceUtils;

/**
 * SQL资源服务实现类，负责提供系统所有需要的SQL语句的检索<br>
 * 服务类在系统启动时从xml文件中加载SQL资源，存入Map<br>
 * XML中资源存放格式如下：<br>
 * &lt;resource&gt;<br>
 * &nbsp;&nbsp;&lt;model name="xxx"&gt;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&lt;sql name="xxx"&gt;&lt;![CDATA[<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;select xxx, xxx from xxx where xxx = ${param1} and xxx = ${param2}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;]]&gt;&lt;/sql&gt;<br>
 * &nbsp;&nbsp;&lt;/model&gt;<br>
 * &nbsp;&nbsp;...<br>
 * &nbsp;&nbsp;&lt;model name="xxx"&gt;<br>
 * &nbsp;&nbsp;&lt;/model&gt;<br>
 * &lt;/resource&gt;<br>
 * sql资源可以定义参数，要求参数以"${"开头，以"}"结尾，例如：select xxx, xxx from xxx where xxx = ${param1} and xxx = ${param2}
 * 
 * date: 2010-12-1
 * @author zouxuemo
 */
@Service("sqlResourceManager")
public class SqlResourceManagerImpl {
	public final static String PARAM_PREFIX = "${" ;
	public final static String PARAM_SUFFIX = "}" ;
	
    protected final Log logger = LogFactory.getLog(getClass());
    
    private String resource;

    /**
     * 存放所有SQL资源的Map，每个Map的key为model名称，value为一个名称-资源的Map
     */
    private Map<String, Map<String, String>> sqlResourceMap = new HashMap<String, Map<String, String>>();
    
	/**
	 * 设置 resource 值为 <code>resource</code>.
	 * @param resource 要设置的 resource 值
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}

    public final void init() throws Exception {
        List<String> xmlFilePaths = ResourceUtils.listFilePathFromResource(resource, "xml");
        if (xmlFilePaths.size() == 0)
        	throw new Exception("给定资源'" + resource + "'下不能找到SQL资源配置XML文件！");

        DocumentBuilder documentBuilder = createDocumentBuilder();
        for (String xmlFilePath : xmlFilePaths) {
        	InputStream is = ResourceUtils.getInputStream(xmlFilePath);
        
        	readResource(documentBuilder, xmlFilePath, is);
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
            throw new Exception("Error in sql resource config", e);
        }
    }
    
    private void readResource(DocumentBuilder documentBuilder, String filename, InputStream is) throws Exception {
        logger.info("读取SQL资源配置文件[" + filename + "]...");

        try {
            Document doc = documentBuilder.parse(is);
    		Element rooterElement = doc.getDocumentElement();

    		/*
    		 * 遍历所有的model元素，进行构建model元素处理
    		 */
    		NodeList modelList = rooterElement.getElementsByTagName("model");
    		for(int i = 0, len = modelList.getLength(); i < len; i++){
    			Element modelElement = (Element)modelList.item(i);
    			String modelName = modelElement.getAttribute("name");
    			
    			Map<String, String> modelMap = sqlResourceMap.get(modelName);
    			if (modelMap == null) {
    				modelMap = new HashMap<String, String>();
    				
    				sqlResourceMap.put(modelName, modelMap);
    			}
    			
    			/*
    			 * 搜索sql元素，读取sql数据信息
    			 */
    			NodeList sqlList = modelElement.getElementsByTagName("sql");
    			for(int j = 0, len1 = sqlList.getLength(); j < len1; j++){
    				Element sqlElement = (Element)sqlList.item(j);
    				String sqlName = sqlElement.getAttribute("name");
    				String sqlContext = sqlElement.getTextContent().trim();
    				
    				if (modelMap.containsKey(sqlName))
    					modelMap.remove(sqlName);
    				
    				modelMap.put(sqlName, sqlContext);
    			}
    		}
        } catch (Exception e) {
            throw new Exception("Error in sql resource config", e);
        }
    }
	
	/**
	 * 获取指定模块下指定名称SQL语句资源
	 *
	 * @param model 模块名
	 * @param name 资源名
	 * @return 如果未找到，返回null，否则返回sql资源字符串
	 */
	public String getSqlResource(String model, String name) {
		return getSqlResource(model, name, null);
	}
	
	/**
	 * 获取指定模块下指定名称SQL语句资源
	 *
	 * @param model 模块名
	 * @param name 资源名
	 * @param params 如果资源带参数，传入参数值集合
	 * @return 如果未找到，返回null，否则返回sql资源字符串
	 */
	public String getSqlResource(String model, String name, Map<String, Object> params) {
		if (!sqlResourceMap.containsKey(model))
			return null;
		
		Map<String, String> modelMap = sqlResourceMap.get(model);
		if (!modelMap.containsKey(name))
			return null;
		
		String sqlContext = modelMap.get(name);
		sqlContext = processContext(sqlContext, params);
		
		return sqlContext;
	}
	
	private String processContext(String context, Map<String, Object> params) {
		int begin = context.indexOf(PARAM_PREFIX);
		if (begin == -1)
			return context;
		
		StringBuffer buf = new StringBuffer();
		
		int end = -1, len = context.length();
		while (begin >= 0) {
			buf.append(context.substring(end + 1, begin));
			
			end = context.indexOf(PARAM_SUFFIX, begin + 2);
			if (end == -1)
				end = len;
			
			String paramName = context.substring(begin + 2, end);
			Object paramValue = (params == null) ? null : params.get(paramName);
			if (paramValue != null)
				buf.append(paramValue);
			
			begin = context.indexOf(PARAM_PREFIX, end + 1);
		}
		
		if (end < len - 1)
			buf.append(context.substring(end + 1));
		
		return buf.toString();
	}
	
	public static void main(String[] args) {
		SqlResourceManagerImpl manager = new SqlResourceManagerImpl();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("param2", "abc");
		
		System.out.println(manager.processContext("select {xxx}, xxx from xxx where xxx = ${param1} and xxx = ${param2}", params));
		System.out.println(manager.processContext("select xxx, xxx from xxx where xxx = ${param1} and xxx = ${param2", params));
		System.out.println(manager.processContext("select xxx, xxx from xxx where xxx = ${param1} and xxx = ${param2", null));
	}
}
