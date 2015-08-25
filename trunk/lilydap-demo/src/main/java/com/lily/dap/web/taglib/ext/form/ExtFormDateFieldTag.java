/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.web.taglib.ext.form;

import javax.servlet.jsp.JspException;

/**
 * <code>ExtFormDateFieldTag</code>
 * <p>���������ֶνű���JSP��ǩ</p>
 *
 * @author ��ѧģ
 * @date 2008-4-18
 *
 * @jsp.tag name="extFormDateField"
 */
public class ExtFormDateFieldTag extends ExtFormFieldTag {
	/**
	 * <code>format</code> ���ڸ�ʽ
	 */
	protected String format = "Y-m-d";
	
	/**
	 * <code>hideTrigger</code> �Ƿ���������ѡ��ť
	 */
	protected String hideTrigger = null;
	
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = 4675250768094424224L;

	/**
	 * ���� format ֵΪ <code>format</code>.
	 * @param format Ҫ���õ� format ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * ���� hideTrigger ֵΪ <code>hideTrigger</code>.
	 * @param hideTrigger Ҫ���õ� hideTrigger ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setHideTrigger(String hideTrigger) {
		this.hideTrigger = hideTrigger;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#modifyProperty()
	 */
	@Override
	protected void modifyProperty() {
		xtype = "datefield";
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#addPropertyScript(java.lang.StringBuffer)
	 */
	@Override
	protected void addPropertyScript(StringBuffer buf) throws JspException {
		buf.append("format: '").append(format).append("', ");
		
		if (hideTrigger != null)
			buf.append("hideTrigger: ").append(hideTrigger).append(", ");
	}
}
