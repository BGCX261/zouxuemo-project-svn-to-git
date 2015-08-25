/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext.form;

import javax.servlet.jsp.JspException;

import com.lily.dap.web.taglib.TagUtils;

/**
 * <code>ExtFormFieldSetTag</code>
 * <p>生成Ext的Form的fieldset面板 js代码的JSP Tag表单字段</p>
 *
 * @author 邹学模
 * @date 2008-4-17
 *
 * @jsp.tag name="extFormFieldSet"
 */
public class ExtFormFieldSetTag extends ExtFormColumnsTag {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = -2579401689477474355L;

	/**
	 * <code>xtype</code> xtype属性，对于配置方式生成的代码，要求指定一个xtype，对于new生成的代码，不是必需的
	 */
	protected String xtype = "fieldset";

	/**
	 * <code>bundle</code> 使用的资源类名
	 */
	protected String bundle = null;
	
	/**
	 * <code>title</code> 标题，可选
	 */
	protected String title = null;
	
	/**
	 * <code>collapsible</code> 是否允许收缩，默认允许
	 */
	protected String collapsible = "true";

	/**
	 * <code>autoHeight</code> 自动设置高度
	 */
	protected String autoHeight = "true";
	
	/**
	 * 返回 bundle.
	 * @return bundle
	 */
	public String getBundle() {
		return bundle;
	}

	/**
	 * 设置 bundle 值为 <code>bundle</code>.
	 * @param bundle 要设置的 bundle 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * 返回 title.
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置 title 值为 <code>title</code>.
	 * @param title 要设置的 title 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 返回 collapsible.
	 * @return collapsible
	 */
	public String getCollapsible() {
		return collapsible;
	}

	/**
	 * 设置 collapsible 值为 <code>collapsible</code>.
	 * @param collapsible 要设置的 collapsible 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setCollapsible(String collapsible) {
		this.collapsible = collapsible;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormColumnsTag#addPropertyScript(java.lang.StringBuffer)
	 */
	@Override
	protected void addPropertyScript(StringBuffer buf) throws JspException {
		ExtFormFieldsTag extFormFieldsTag = (ExtFormFieldsTag)findAncestorWithClass(this, ExtFormFieldsTag.class);
		if (extFormFieldsTag == null)
			throw new JspException("不能找到extFormFields标签定义");
		
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

	/* （非 Javadoc）
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormColumnsTag#releaseProperty()
	 */
	@Override
	protected void releaseProperty() {
		bundle = null;
	}
}
