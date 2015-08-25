/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext.grid;

import javax.servlet.jsp.JspException;

/**
 * <code>ExtDictionaryColumnTag</code>
 * <p>生成Ext的Ext.grid.DictionaryColumn js代码的JSP Tag</p>
 *
 * @author 邹学模
 * @date 2008-4-28
 *
 * @jsp.tag name="extDictionaryColumn"
 */
public class ExtDictionaryColumnTag extends ExtColumnTag {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = -377779695976194742L;

	/**
	 * <code>displayField</code> 显示字段
	 */
	private String displayField = "value";
	
	/**
	 * <code>valueField</code> 值字段
	 */
	private String valueField = "id";
	
	/**
	 * <code>store</code> 存放字典的store，必须
	 */
	private String store = null;
	
	/**
	 * 设置 displayField 值为 <code>displayField</code>.
	 * @param displayField 要设置的 displayField 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}

	/**
	 * 设置 valueField 值为 <code>valueField</code>.
	 * @param valueField 要设置的 valueField 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	/**
	 * 设置 store 值为 <code>store</code>.
	 * @param store 要设置的 store 值
	 * 
     * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setStore(String store) {
		this.store = store;
	}

	/* （非 Javadoc）
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

	/* （非 Javadoc）
	 * @see com.lily.dap.webapp.taglib.ext.grid.ExtColumnTag#modifyProperty()
	 */
	@Override
	protected void modifyProperty() {
		clazz = "Ext.grid.DictionaryColumn";
	}

}
