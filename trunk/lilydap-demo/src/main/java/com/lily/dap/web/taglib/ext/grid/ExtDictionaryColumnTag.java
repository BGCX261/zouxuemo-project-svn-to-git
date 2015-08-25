/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.web.taglib.ext.grid;

import javax.servlet.jsp.JspException;

/**
 * <code>ExtDictionaryColumnTag</code>
 * <p>����Ext��Ext.grid.DictionaryColumn js�����JSP Tag</p>
 *
 * @author ��ѧģ
 * @date 2008-4-28
 *
 * @jsp.tag name="extDictionaryColumn"
 */
public class ExtDictionaryColumnTag extends ExtColumnTag {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = -377779695976194742L;

	/**
	 * <code>displayField</code> ��ʾ�ֶ�
	 */
	private String displayField = "value";
	
	/**
	 * <code>valueField</code> ֵ�ֶ�
	 */
	private String valueField = "id";
	
	/**
	 * <code>store</code> ����ֵ��store������
	 */
	private String store = null;
	
	/**
	 * ���� displayField ֵΪ <code>displayField</code>.
	 * @param displayField Ҫ���õ� displayField ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}

	/**
	 * ���� valueField ֵΪ <code>valueField</code>.
	 * @param valueField Ҫ���õ� valueField ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	/**
	 * ���� store ֵΪ <code>store</code>.
	 * @param store Ҫ���õ� store ֵ
	 * 
     * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setStore(String store) {
		this.store = store;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.webapp.taglib.ext.grid.ExtColumnTag#addPropertyScript(java.lang.StringBuffer)
	 */
	@Override
	protected void addPropertyScript(StringBuffer buf) throws JspException {
		if (displayField != null)
			buf.append("displayField: '").append(displayField).append("', ");
		
		if (valueField != null)
			buf.append("valueField: '").append(valueField).append("', ");
		
		if (store != null)
			buf.append("store: ").append(store).append(", ");
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.webapp.taglib.ext.grid.ExtColumnTag#modifyProperty()
	 */
	@Override
	protected void modifyProperty() {
		clazz = "Ext.grid.DictionaryColumn";
	}

}
