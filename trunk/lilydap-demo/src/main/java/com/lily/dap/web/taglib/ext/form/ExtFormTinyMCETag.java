/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext.form;

import javax.servlet.jsp.JspException;

/**
 * <code>ExtFormTinyMCETag</code>
 * <p>生成TinyMCEHTML在线编辑字段脚本的JSP标签</p>
 *
 * @author 邹学模
 * @date 2008-4-18
 *
 * @jsp.tag name="extFormTinyMCE"
 */
public class ExtFormTinyMCETag extends ExtFormFieldTag {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = 7768110507171260186L;

	/**
	 * <code>height</code> 在线编辑字段高度
	 */
	protected String height = "300";
	
	/**
	 * <code>skin</code> 在线编辑字段风格
	 */
	protected String skin = "o2k7";
	
	/**
	 * <code>theme</code> 在线编辑字段模式
	 */
	protected String theme = "advanced";
	
	/**
	 * <code>theme_advanced_buttons1</code> 高级模式下第一行按钮定义
	 */
	protected String theme_advanced_buttons1 = "formatselect,fontselect,fontsizeselect,|,bold,italic,underline,strikethrough,|,forecolor,backcolor,|,justifyleft,justifycenter,justifyright, justifyfull,separator,bullist,numlist,|,link,unlink,image,|,undo,redo";
	
	/**
	 * <code>theme_advanced_buttons2</code> 高级模式下第二行按钮定义
	 */
	protected String theme_advanced_buttons2 = "";
	
	/**
	 * <code>theme_advanced_buttons3</code> 高级模式下第三行按钮定义
	 */
	protected String theme_advanced_buttons3 = "";
	
	/**
	 * 设置 height 值为 <code>height</code>.
	 * @param height 要设置的 height 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * 设置 skin 值为 <code>skin</code>.
	 * @param skin 要设置的 skin 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setSkin(String skin) {
		this.skin = skin;
	}

	/**
	 * 设置 theme 值为 <code>theme</code>.
	 * @param theme 要设置的 theme 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * 设置 theme_advanced_buttons1 值为 <code>theme_advanced_buttons1</code>.
	 * @param theme_advanced_buttons1 要设置的 theme_advanced_buttons1 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTheme_advanced_buttons1(String theme_advanced_buttons1) {
		this.theme_advanced_buttons1 = theme_advanced_buttons1;
	}

	/**
	 * 设置 theme_advanced_buttons2 值为 <code>theme_advanced_buttons2</code>.
	 * @param theme_advanced_buttons2 要设置的 theme_advanced_buttons2 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTheme_advanced_buttons2(String theme_advanced_buttons2) {
		this.theme_advanced_buttons2 = theme_advanced_buttons2;
	}

	/**
	 * 设置 theme_advanced_buttons3 值为 <code>theme_advanced_buttons3</code>.
	 * @param theme_advanced_buttons3 要设置的 theme_advanced_buttons3 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTheme_advanced_buttons3(String theme_advanced_buttons3) {
		this.theme_advanced_buttons3 = theme_advanced_buttons3;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#modifyProperty()
	 */
	@Override
	protected void modifyProperty() {
		xtype = "tinymce";
	}

	/* （非 Javadoc）
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
