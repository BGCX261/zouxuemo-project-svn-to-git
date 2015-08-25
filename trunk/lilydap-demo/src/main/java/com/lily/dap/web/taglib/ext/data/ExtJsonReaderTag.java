/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
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
 * <code>ExtJsonReaderTag</code>
 * <p>����Ext��Ext.data.JsonReader js�����JSP Tag</p>
 *
 * <lilydap:extJsonReader name="personReader" id="personReader" url="organizeQuery.do?method=queryPerson" totalProperty="total" root="data" model="com.lily.dap.model.organize.Person"/>
 * ִ�к��������������´��룺
 * var personReader = new Ext.data.JsonReader({ 
 * 	totalProperty: 'total', 
 * 	root: 'data', 
 * 	id:'personReader',
 * 	successProperty :'success',
 * 	fields: [{name: 'id', type:'int'}, 'username', 'name', 'sex', 'birthday', 'mobile', 'phone', {name: 'enabled', type:'boolean'}, {name: 'state', type:'int'}]
 * }); 
 * 
 * @author ��ѧģ
 * @date 2008-3-20
 *
 * @jsp.tag name="extJsonReader"
 */
@SuppressWarnings("unchecked")
public class ExtJsonReaderTag extends TagSupport implements ExtJsonReaderInterface {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = 5348484879890453492L;

	public static String DEFAULT_READER_MODEL = "Ext.data.JsonReader";
	
	/**
	 * <code>clazz</code> Ĭ�ϵ�Reader������
	 */
	private String clazz = DEFAULT_READER_MODEL;
	
	/**
	 * <code>id</code> Reader��IDֵ����ѡ
	 */
	private String id = null;
	
	/**
	 * <code>name</code> ����Reader�ı��������������ǩ����Ƕ����������ǩ�ڣ���Ǳ����ֶΣ������Ǳ����ֶ�
	 */
	private String name = null;
	
	/**
	 * <code>model</code> Reader���ݽṹ��Ӧ��ʵ�����ȫ·������������"com.lily.dap.model.right.Role"�������ֶ�
	 */
	private String model = null;
	
	/**
	 * <code>totalProperty</code> �����Json�����б�ʾ����������������������Ƿ�ҳ�����ݣ���������
	 */
	private String totalProperty = null;
	
	/**
	 * <code>root</code> �����Json�����д�����ݵ���������������Ƿ�ҳ�����ݣ���������
	 */
	private String root = null;
	
	/**
	 * ���� id ֵΪ <code>id</code>.
	 * @param id Ҫ���õ� id ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * ���� name ֵΪ <code>name</code>.
	 * @param name Ҫ���õ� name ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ���� model ֵΪ <code>model</code>.
	 * @param model Ҫ���õ� model ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * ���� totalProperty ֵΪ <code>totalProperty</code>.
	 * @param totalProperty Ҫ���õ� totalProperty ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTotalProperty(String totalProperty) {
		this.totalProperty = totalProperty;
	}

	/**
	 * ���� root ֵΪ <code>root</code>.
	 * @param root Ҫ���õ� root ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setRoot(String root) {
		this.root = root;
	}
// -------------------------------- ����Ϊ˽�б��� ----------------------------------
	/**
	 * <code>entity</code> JSON������ʵ�������
	 */
	private Class entity = null;
	
	/**
	 * <code>convertScriptMap</code> �����ת����Map
	 */
	private Map<String, String> convertScriptMap = new HashMap<String, String>();

	/**
	 * @return the entity
	 */
	public Class getEntity() {
		return entity;
	}

	/* ���� Javadoc��
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
			throw new JspException("δָ��ReaderҪ�����ʵ����");
		
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
    	
    	buf.append("new ").append(clazz).append("({");
    	
    	if (id != null)
    		buf.append("id: '" + id + "', ");
    	
    	if (root != null)
    		buf.append("root: '" + root + "', ");
    	
    	if (totalProperty != null)
    		buf.append("totalProperty: '" + totalProperty + "', ");
    	
    	buf.append("successProperty: 'success'}, ");
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
    	
    	buf.append("])");
    	
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
