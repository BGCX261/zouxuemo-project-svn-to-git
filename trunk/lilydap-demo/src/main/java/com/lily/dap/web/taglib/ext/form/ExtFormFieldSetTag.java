/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.web.taglib.ext.form;

import javax.servlet.jsp.JspException;

import com.lily.dap.web.taglib.TagUtils;

/**
 * <code>ExtFormFieldSetTag</code>
 * <p>����Ext��Form��fieldset��� js�����JSP Tag���ֶ�</p>
 *
 * @author ��ѧģ
 * @date 2008-4-17
 *
 * @jsp.tag name="extFormFieldSet"
 */
public class ExtFormFieldSetTag extends ExtFormColumnsTag {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = -2579401689477474355L;

	/**
	 * <code>xtype</code> xtype���ԣ��������÷�ʽ���ɵĴ��룬Ҫ��ָ��һ��xtype������new���ɵĴ��룬���Ǳ����
	 */
	protected String xtype = "fieldset";

	/**
	 * <code>bundle</code> ʹ�õ���Դ����
	 */
	protected String bundle = null;
	
	/**
	 * <code>title</code> ���⣬��ѡ
	 */
	protected String title = null;
	
	/**
	 * <code>collapsible</code> �Ƿ�����������Ĭ������
	 */
	protected String collapsible = "true";

	/**
	 * <code>autoHeight</code> �Զ����ø߶�
	 */
	protected String autoHeight = "true";
	
	/**
	 * ���� bundle.
	 * @return bundle
	 */
	public String getBundle() {
		return bundle;
	}

	/**
	 * ���� bundle ֵΪ <code>bundle</code>.
	 * @param bundle Ҫ���õ� bundle ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * ���� title.
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * ���� title ֵΪ <code>title</code>.
	 * @param title Ҫ���õ� title ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * ���� collapsible.
	 * @return collapsible
	 */
	public String getCollapsible() {
		return collapsible;
	}

	/**
	 * ���� collapsible ֵΪ <code>collapsible</code>.
	 * @param collapsible Ҫ���õ� collapsible ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setCollapsible(String collapsible) {
		this.collapsible = collapsible;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormColumnsTag#addPropertyScript(java.lang.StringBuffer)
	 */
	@Override
	protected void addPropertyScript(StringBuffer buf) throws JspException {
		ExtFormFieldsTag extFormFieldsTag = (ExtFormFieldsTag)findAncestorWithClass(this, ExtFormFieldsTag.class);
		if (extFormFieldsTag == null)
			throw new JspException("�����ҵ�extFormFields��ǩ����");
		
		if (xtype != null)
			buf.append("xtype: '").append(xtype).append("', ");
		
		if (title != null) {
    		if (bundle == null)
    			bundle = extFormFieldsTag.getBundle();

    		String s = TagUtils.getMessage(pageContext, bundle, title);
			buf.append("title: '").append(s).append("', ");
		}
		
		if (collapsible != null)
			buf.append("collapsible: ").append(collapsible).append(", ");
		
		buf.append("autoHeight: ").append(autoHeight).append(", ");
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormColumnsTag#releaseProperty()
	 */
	@Override
	protected void releaseProperty() {
		bundle = null;
	}
}
