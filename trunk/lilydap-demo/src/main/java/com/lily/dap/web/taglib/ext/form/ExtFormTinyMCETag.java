/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.web.taglib.ext.form;

import javax.servlet.jsp.JspException;

/**
 * <code>ExtFormTinyMCETag</code>
 * <p>����TinyMCEHTML���߱༭�ֶνű���JSP��ǩ</p>
 *
 * @author ��ѧģ
 * @date 2008-4-18
 *
 * @jsp.tag name="extFormTinyMCE"
 */
public class ExtFormTinyMCETag extends ExtFormFieldTag {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = 7768110507171260186L;

	/**
	 * <code>height</code> ���߱༭�ֶθ߶�
	 */
	protected String height = "300";
	
	/**
	 * <code>skin</code> ���߱༭�ֶη��
	 */
	protected String skin = "o2k7";
	
	/**
	 * <code>theme</code> ���߱༭�ֶ�ģʽ
	 */
	protected String theme = "advanced";
	
	/**
	 * <code>theme_advanced_buttons1</code> �߼�ģʽ�µ�һ�а�ť����
	 */
	protected String theme_advanced_buttons1 = "formatselect,fontselect,fontsizeselect,|,bold,italic,underline,strikethrough,|,forecolor,backcolor,|,justifyleft,justifycenter,justifyright, justifyfull,separator,bullist,numlist,|,link,unlink,image,|,undo,redo";
	
	/**
	 * <code>theme_advanced_buttons2</code> �߼�ģʽ�µڶ��а�ť����
	 */
	protected String theme_advanced_buttons2 = "";
	
	/**
	 * <code>theme_advanced_buttons3</code> �߼�ģʽ�µ����а�ť����
	 */
	protected String theme_advanced_buttons3 = "";
	
	/**
	 * ���� height ֵΪ <code>height</code>.
	 * @param height Ҫ���õ� height ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * ���� skin ֵΪ <code>skin</code>.
	 * @param skin Ҫ���õ� skin ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}

	/**
	 * ���� theme ֵΪ <code>theme</code>.
	 * @param theme Ҫ���õ� theme ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * ���� theme_advanced_buttons1 ֵΪ <code>theme_advanced_buttons1</code>.
	 * @param theme_advanced_buttons1 Ҫ���õ� theme_advanced_buttons1 ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTheme_advanced_buttons1(String theme_advanced_buttons1) {
		this.theme_advanced_buttons1 = theme_advanced_buttons1;
	}

	/**
	 * ���� theme_advanced_buttons2 ֵΪ <code>theme_advanced_buttons2</code>.
	 * @param theme_advanced_buttons2 Ҫ���õ� theme_advanced_buttons2 ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTheme_advanced_buttons2(String theme_advanced_buttons2) {
		this.theme_advanced_buttons2 = theme_advanced_buttons2;
	}

	/**
	 * ���� theme_advanced_buttons3 ֵΪ <code>theme_advanced_buttons3</code>.
	 * @param theme_advanced_buttons3 Ҫ���õ� theme_advanced_buttons3 ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTheme_advanced_buttons3(String theme_advanced_buttons3) {
		this.theme_advanced_buttons3 = theme_advanced_buttons3;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#modifyProperty()
	 */
	@Override
	protected void modifyProperty() {
		xtype = "tinymce";
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#addPropertyScript(java.lang.StringBuffer)
	 */
	@Override
	protected void addPropertyScript(StringBuffer buf) throws JspException {
		buf.append("height: ").append(height).append(", ");
		buf.append("tinymceSettings: {");
		buf.append("skin: '").append(skin).append("', ");
		buf.append("theme: '").append(theme).append("', ");
		buf.append("theme_advanced_buttons1: '").append(theme_advanced_buttons1).append("', ");
		buf.append("theme_advanced_buttons2: '").append(theme_advanced_buttons2).append("', ");
		buf.append("theme_advanced_buttons3: '").append(theme_advanced_buttons3).append("', ");
		buf.append("autoScroll: true, cleanup_on_startup: true, theme_advanced_toolbar_location: 'top', theme_advanced_toolbar_align: 'left', debug: false}, ");
	}
}
