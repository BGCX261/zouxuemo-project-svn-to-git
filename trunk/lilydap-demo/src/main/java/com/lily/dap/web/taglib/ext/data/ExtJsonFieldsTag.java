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
 * <code>ExtJsonFieldsTag</code>
 * <p>����Ext��fields����ֵ js�����JSP Tag</p>
 *
 * <lilydap:extJsonFields name="fields" model="com.lily.dap.model.organize.Person"/>
 * ִ�к��������������´��룺
  * 	var fields = [{name: 'id', type:'int'}, 'username', 'name', 'sex', 'birthday', 'mobile', 'phone', {name: 'enabled', type:'boolean'}, {name: 'state', type:'int'}];
 * 
 * <lilydap:extJsonFields model="organize.Person"/>
 * ִ�к��������������´��룺
  * 	[{name: 'id', type:'int'}, 'username', 'name', 'sex', 'birthday', 'mobile', 'phone', {name: 'enabled', type:'boolean'}, {name: 'state', type:'int'}]
 * 
 * @author ��ѧģ
 * @date 2008-3-20
 *
 * @jsp.tag name="extJsonFields"
 */
@SuppressWarnings("unchecked")
public class ExtJsonFieldsTag extends TagSupport implements ExtJsonReaderInterface {
	private static final long serialVersionUID = -1292388416555118166L;

	/**
	 * <code>name</code> ����fields�ı���������������ã������ɱ�����
	 */
	private String name = null;
	
	/**
	 * <code>model</code> ��Ӧ��ʵ�����ȫ·������������"com.lily.dap.model.right.Role"�������ֶ�
	 */
	private String model = null;
	
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
		}
		
		if (entity == null)
			throw new JspException("δָ��Ҫ�����ʵ����");
		
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
