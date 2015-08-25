/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext.form;

import javax.servlet.jsp.JspException;

/**
 * <code>ExtFormDateFieldTag</code>
 * <p>生成日期字段脚本的JSP标签</p>
 *
 * @author 邹学模
 * @date 2008-4-18
 *
 * @jsp.tag name="extFormDateField"
 */
public class ExtFormDateFieldTag extends ExtFormFieldTag {
	/**
	 * <code>format</code> 日期格式
	 */
	protected String format = "Y-m-d";
	
	/**
	 * <code>hideTrigger</code> 是否隐藏日期选择按钮
	 */
	protected String hideTrigger = null;
	
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = 4675250768094424224L;

	/**
	 * 设置 format 值为 <code>format</code>.
	 * @param format 要设置的 format 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * 设置 hideTrigger 值为 <code>hideTrigger</code>.
	 * @param hideTrigger 要设置的 hideTrigger 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setHideTrigger(String hideTrigger) {
		this.hideTrigger = hideTrigger;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#modifyProperty()
	 */
	@Override
	protected void modifyProperty() {
		xtype = "datefield";
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#addPropertyScript(java.lang.StringBuffer)
	 */
	@Override
	protected void addPropertyScript(StringBuffer buf) throws JspException {
		buf.append("format: '").append(format).append("', ");
		
		if (hideTrigger != null)
			buf.append("hideTrigger: ").append(hideTrigger).append(", ");
	}
}
