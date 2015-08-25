/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext.data;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.PropertyUtils;

import com.lily.dap.util.BaseEntityHelper;

/**
 * <code>ExtJsonStoreTag</code>
 * <p>生成Ext的Ext.data.JsonStore js代码的JSP Tag</p>
 *
 * <lilydap:extJsonStore name="personStore" id="personStore" url="organizeQuery.do?method=queryPerson" totalProperty="total" root="data" model="com.lily.dap.model.organize.Person"/>
 * 执行后将生成类似于如下代码：
 * var personStore = new Ext.data.JsonStore({ 
 * 	url : 'organizeQuery.do?method=queryPerson',
 * 	totalProperty: 'total', 
 * 	root: 'data', 
 * 	id:'personStore',
 * 	successProperty :'success',
 * 	fields: [{name: 'id', type:'int'}, 'username', 'name', 'sex', 'birthday', 'mobile', 'phone', {name: 'enabled', type:'boolean'}, {name: 'state', type:'int'}]
 * }); 
 * 
 * @author 邹学模
 * @date 2008-3-20
 *
 * @jsp.tag name="extJsonStore"
 */
@SuppressWarnings("unchecked")
public class ExtJsonStoreTag extends TagSupport implements ExtJsonReaderInterface {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = 5348484879890453492L;

	/**
	 * <code>id</code> Store的ID值，可选
	 */
	private String id = null;
	
	/**
	 * <code>name</code> 定义Store的变量名，必需字段
	 */
	private String name = null;
	
	/**
	 * <code>url</code> url数据来源
	 */
	private String url = null;
	
	/**
	 * <code>model</code> Store数据结构对应的实体对象全路径名，类似于"com.lily.dap.model.right.Role"，必需字段
	 */
	private String model = null;
	
	/**
	 * <code>totalProperty</code> 传入的Json数据中表示总条数的属性名，如果不是分页的数据，不必设置
	 */
	private String totalProperty = null;
	
	/**
	 * <code>root</code> 传入的Json数据中存放数据的属性名，如果不是分页的数据，不必设置
	 */
	private String root = null;
	
	/**
	 * <code>propertys</code> 附加属性设置，可选，标签会把附加属性加入到属性中去。要求格式为"xxx: 'xxx', xxx:xxx,..."形式
	 */
	private String propertys = null;
	
	/**
	 * 设置 id 值为 <code>id</code>.
	 * @param id 要设置的 id 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 设置 name 值为 <code>name</code>.
	 * @param name 要设置的 name 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 设置 url 值为 <code>url</code>.
	 * @param url 要设置的 url 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 设置 model 值为 <code>model</code>.
	 * @param model 要设置的 model 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * 设置 totalProperty 值为 <code>totalProperty</code>.
	 * @param totalProperty 要设置的 totalProperty 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTotalProperty(String totalProperty) {
		this.totalProperty = totalProperty;
	}

	/**
	 * 设置 root 值为 <code>root</code>.
	 * @param root 要设置的 root 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setRoot(String root) {
		this.root = root;
	}

	/**
	 * 设置 propertys 值为 <code>propertys</code>.
	 * @param propertys 要设置的 propertys 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setPropertys(String propertys) {
		this.propertys = propertys;
	}
	
// -------------------------------- 以下为私有变量 ----------------------------------
	/**
	 * <code>entity</code> JSON检索的实体对象类
	 */
	private Class entity = null;
	
	/**
	 * <code>convertScriptMap</code> 定义的转换器Map
	 */
	private Map<String, String> convertScriptMap = new HashMap<String, String>();

	/**
	 * @return the entity
	 */
	public Class getEntity() {
		return entity;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.webapp.taglib.ext.ExtJsonReaderInterface#getConvertScriptMap()
	 */
	public Map<String, String> getConvertScriptMap() {
		return convertScriptMap;
	}

	public int doStartTag() throws JspException {
		if (model != null) {
			try {
				entity = BaseEntityHelper.parseEntity(model);
			} catch (Exception e) {
				throw new JspException(e);
			}
		} else {
			Object parent = getParent();
			if (parent != null) {
				if (parent instanceof ExtDataTargetInterface)
					entity = ((ExtDataTargetInterface)parent).getEntity();
			}
		}
		
		if (entity == null)
			throw new JspException("未指定Reader要输出的实体类");
		
		return EVAL_BODY_INCLUDE;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		ExtDataTargetInterface extDataTargetInterface = null;
		if (getParent() != null) 
			extDataTargetInterface = (ExtDataTargetInterface)getParent();
		
    	StringBuffer buf = new StringBuffer();
    	if (name != null)
    		buf.append("var ").append(name).append(" = ");
    	
    	buf.append("new ").append("Ext.data.JsonStore").append("({");
    	
    	if (id != null)
    		buf.append("	id: '" + id + "',\r\n");
    	
    	buf.append("	url: '" + url + "',\r\n");
    	
//    	buf.append("	remoteSort: true,\r\n");
    	
    	if (root != null)
    		buf.append("	root: '" + root + "',\r\n");
    	
    	if (totalProperty != null)
    		buf.append("	totalProperty: '" + totalProperty + "',\r\n");
    	
    	buf.append("	successProperty: 'success',\r\n");
    	
    	if (propertys != null)
    		buf.append(propertys + ",\r\n");
    	
    	buf.append("	fields: [");
    	
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors( entity );
		
    	boolean first = true;
		for( int i = 0; i < pds.length; i++ ){
			if( pds[i].getReadMethod() == null )
				continue;

			String key = pds[i].getName();
			Class type = pds[i].getPropertyType();
			String typeName = type.getName();
			
			StringBuffer buf1 = new StringBuffer();
			if (type == String.class || "char".equals(typeName)) {
				String convertScript = convertScript(key);
				
				if ("".equals(convertScript))
					buf1.append("'").append(key).append("'");
				else
					buf1.append("{name: '").append(key).append("'").append(convertScript).append("}");
				
			} else if (type == Integer.class || type == Long.class || 
						"byte".equals(typeName) || "int".equals(typeName) || "long".equals(typeName)) {
				buf1.append("{name: '").append(key).append("', type: 'int'").append(convertScript(key)).append("}");
				
			} else if (type == Float.class || type == Double.class || 
						"double".equals(typeName) || "float".equals(typeName)) {
				buf1.append("{name: '").append(key).append("', type: 'float'").append(convertScript(key)).append("}");
				
			} else if (type == Boolean.class || "boolean".equals(typeName)) {
				buf1.append("{name: '").append(key).append("', type: 'boolean'").append(convertScript(key)).append("}");
				
			} else if (type == Date.class || type == Timestamp.class) {
				buf1.append("{name: '").append(key).append("', type: 'date'").append(convertScript(key)).append("}");
			} else
				continue;
			
			if (first)
				first = false;
			else
				buf.append(", ");
			
			buf.append(buf1);
		}
    	
    	buf.append("]})");
    	
    	if (name != null) {
    		buf.append(";\r\n");
    		
    		try {
    			pageContext.getOut().print(buf.toString());
    		} catch (IOException ioe) {
    			throw new JspTagException(ioe.toString(), ioe);
    		}
        	
        	if (extDataTargetInterface != null)
        		extDataTargetInterface.setDataScript(name);
    	} else if (extDataTargetInterface != null) {
    		String dataScript = buf.toString();
    		
    		extDataTargetInterface.setDataScript(dataScript);
    	}
    	
    	release();
		return EVAL_PAGE;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#release()
	 */
	@Override
	public void release() {
		id = null;
		name = null;
		url = null;
		model = null;
		totalProperty = null;
		root = null;
		
		entity = null;
		convertScriptMap.clear();
		
		super.release();
	}
	
	private String convertScript(String key) {
		if (convertScriptMap.containsKey(key))
			return ", convert: " + convertScriptMap.get(key);
		else
			return "";
	}
}
