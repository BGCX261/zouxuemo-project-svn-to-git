/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext.form;

import javax.servlet.jsp.JspException;

/**
 * <code>ExtFormTextAreaTag</code>
 * <p>生成TextArea字段脚本的JSP标签</p>
 *
 * @author 邹学模
 * @date 2008-4-18
 *
 * @jsp.tag name="extFormTextArea"
 */
public class ExtFormTextAreaTag extends ExtFormFieldTag {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = -774512256342603112L;

	/**
	 * <code>height</code> 文本区字段高度
	 */
	protected String height = "300";
	
	/**
	 * 设置 height 值为 <code>height</code>.
	 * @param height 要设置的 height 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#modifyProperty()
	 */
	@Override
	protected void modifyProperty() {
		xtype = "textarea";
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#addPropertyScript(java.lang.StringBuffer)
	 */
	@Override
	protected void addPropertyScript(StringBuffer buf) throws JspException {
		buf.append("height: ").append(height).append(", ");
	}
}
