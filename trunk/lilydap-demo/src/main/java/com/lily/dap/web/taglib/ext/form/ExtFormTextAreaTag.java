/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.web.taglib.ext.form;

import javax.servlet.jsp.JspException;

/**
 * <code>ExtFormTextAreaTag</code>
 * <p>����TextArea�ֶνű���JSP��ǩ</p>
 *
 * @author ��ѧģ
 * @date 2008-4-18
 *
 * @jsp.tag name="extFormTextArea"
 */
public class ExtFormTextAreaTag extends ExtFormFieldTag {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = -774512256342603112L;

	/**
	 * <code>height</code> �ı����ֶθ߶�
	 */
	protected String height = "300";
	
	/**
	 * ���� height ֵΪ <code>height</code>.
	 * @param height Ҫ���õ� height ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#modifyProperty()
	 */
	@Override
	protected void modifyProperty() {
		xtype = "textarea";
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#addPropertyScript(java.lang.StringBuffer)
	 */
	@Override
	protected void addPropertyScript(StringBuffer buf) throws JspException {
		buf.append("height: ").append(height).append(", ");
	}
}
