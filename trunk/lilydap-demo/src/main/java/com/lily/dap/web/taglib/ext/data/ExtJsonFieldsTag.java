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
 * <code>ExtJsonFieldsTag</code>
 * <p>生成Ext的fields数据值 js代码的JSP Tag</p>
 *
 * <lilydap:extJsonFields name="fields" model="com.lily.dap.model.organize.Person"/>
 * 执行后将生成类似于如下代码：
  * 	var fields = [{name: 'id', type:'int'}, 'username', 'name', 'sex', 'birthday', 'mobile', 'phone', {name: 'enabled', type:'boolean'}, {name: 'state', type:'int'}];
 * 
 * <lilydap:extJsonFields model="organize.Person"/>
 * 执行后将生成类似于如下代码：
  * 	[{name: 'id', type:'int'}, 'username', 'name', 'sex', 'birthday', 'mobile', 'phone', {name: 'enabled', type:'boolean'}, {name: 'state', type:'int'}]
 * 
 * @author 邹学模
 * @date 2008-3-20
 *
 * @jsp.tag name="extJsonFields"
 */
@SuppressWarnings("unchecked")
public class ExtJsonFieldsTag extends TagSupport implements ExtJsonReaderInterface {
	private static final long serialVersionUID = -1292388416555118166L;

	/**
	 * <code>name</code> 定义fields的变量名，如果不设置，则不生成变量名
	 */
	private String name = null;
	
	/**
	 * <code>model</code> 对应的实体对象全路径名，类似于"com.lily.dap.model.right.Role"，必需字段
	 */
	private String model = null;
	
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
	 * 设置 model 值为 <code>model</code>.
	 * @param model 要设置的 model 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setModel(String model) {
		this.model = model;
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
		}
		
		if (entity == null)
			throw new JspException("未指定要输出的实体类");
		
		return EVAL_BODY_INCLUDE;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
    	StringBuffer buf = new StringBuffer();
    	if (name != null)
    		buf.append("var ").append(name).append(" = ");
    	
    	buf.append("[");
    	
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
    	
    	buf.append("]");
    	
    	if (name != null)
    		buf.append(";");
    	
		try {
			pageContext.getOut().print(buf.toString());
		} catch (IOException ioe) {
			throw new JspTagException(ioe.toString(), ioe);
		}
    	
    	release();
		return EVAL_PAGE;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#release()
	 */
	@Override
	public void release() {
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
