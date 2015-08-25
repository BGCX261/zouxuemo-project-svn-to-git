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
 * SQL��Դ����ʵ���࣬�����ṩϵͳ������Ҫ��SQL���ļ���<br>
 * ��������ϵͳ����ʱ��xml�ļ��м���SQL��Դ������Map<br>
 * XML����Դ��Ÿ�ʽ���£�<br>
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
 * sql��Դ���Զ��������Ҫ�������"${"��ͷ����"}"��β�����磺select xxx, xxx from xxx where xxx = ${param1} and xxx = ${param2}
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
     * �������SQL��Դ��Map��ÿ��Map��keyΪmodel���ƣ�valueΪһ������-��Դ��Map
     */
    private Map<String, Map<String, String>> sqlResourceMap = new HashMap<String, Map<String, String>>();
    
	/**
	 * ���� resource ֵΪ <code>resource</code>.
	 * @param resource Ҫ���õ� resource ֵ
	 */
	public void setResource(String resource) {
		this.resource = resource;
	}

    public final void init() throws Exception {
        List<String> xmlFilePaths = ResourceUtils.listFilePathFromResource(resource, "xml");
        if (xmlFilePaths.size() == 0)
        	throw new Exception("������Դ'" + resource + "'�²����ҵ�SQL��Դ����XML�ļ���");

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
        logger.info("��ȡSQL��Դ�����ļ�[" + filename + "]...");

        try {
            Document doc = documentBuilder.parse(is);
    		Element rooterElement = doc.getDocumentElement();

    		/*
    		 * �������е�modelԪ�أ����й���modelԪ�ش���
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
    			 * ����sqlԪ�أ���ȡsql������Ϣ
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
	 * ��ȡָ��ģ����ָ������SQL�����Դ
	 *
	 * @param model ģ����
	 * @param name ��Դ��
	 * @return ���δ�ҵ�������null�����򷵻�sql��Դ�ַ���
	 */
	public String getSqlResource(String model, String name) {
		return getSqlResource(model, name, null);
	}
	
	/**
	 * ��ȡָ��ģ����ָ������SQL�����Դ
	 *
	 * @param model ģ����
	 * @param name ��Դ��
	 * @param params �����Դ���������������ֵ����
	 * @return ���δ�ҵ�������null�����򷵻�sql��Դ�ַ���
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
