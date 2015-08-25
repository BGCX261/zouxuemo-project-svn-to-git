/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext.form;

import javax.servlet.jsp.JspException;

/**
 * <code>ExtFormMultiComboTag</code>
 * <p>生成多选下拉列表字段脚本的JSP标签</p>
 *
 * @author 孙成才
 * @date 2009-7-10
 *
 * @jsp.tag name="extFormMultiCombo"
 */
public class ExtFormMultiComboTag extends ExtFormFieldTag {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = -8336787781614422311L;

	/**
	 * <code>editable</code> 下拉列表的文本框是否可编辑
	 */
	protected String editable = "false";

	/**
	 * <code>triggerAction</code> triggerAction属性
	 */
	protected String triggerAction = "all";

	/**
	 * <code>mode</code> 下拉列表检索数据模式
	 */
	protected String mode = "local";

	/**
	 * <code>store</code> 下拉列表的数据源
	 */
	protected String store = null;

	/**
	 * <code>displayField</code> 下拉列表显示字段
	 */
	protected String displayField = null;

	/**
	 * <code>valueField</code> 下拉列表值字段
	 */
	protected String valueField = null;

	/**
	 * <code>hiddenName</code> 隐藏字段名，如果下拉框是显示值和实际值分离的情况，要求必须指定hiddenName
	 */
	protected String hiddenName = null;

	/**
	 * 设置 editable 值为 <code>editable</code>.
	 * @param editable 要设置的 editable 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setEditable(String editable) {
		this.editable = editable;
	}

	/**
	 * 设置 triggerAction 值为 <code>triggerAction</code>.
	 * @param triggerAction 要设置的 triggerAction 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTriggerAction(String triggerAction) {
		this.triggerAction = triggerAction;
	}

	/**
	 * 设置 mode 值为 <code>mode</code>.
	 * @param mode 要设置的 mode 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * 设置 store 值为 <code>store</code>.
	 * @param store 要设置的 store 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setStore(String store) {
		this.store = store;
	}

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

//	/**
//	 * 设置 hiddenName 值为 <code>hiddenName</code>.
//	 * @param hiddenName 要设置的 hiddenName 值
//	 * 
//	 * @jsp.attribute required="false" rtexprvalue="true"
//	 */
//	public void setHiddenName(String hiddenName) {
//		this.hiddenName = hiddenName;
//	}

	/* （非 Javadoc）
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#modifyProperty()
	 */
	@Override
	protected void modifyProperty() {
		xtype = "multicombo";
		
		if (valueField == null)
			valueField = displayField;

		//如果值字段和显示字段不一致，则要求设置hiddenName属性，因此必须保证id值不能和hiddenName相同，否则显示出错
		if (!valueField.equals(displayField)) {
			hiddenName = name != null ? name : field;
			
			if ((id != null && id.equals(hiddenName)) ||
				(id == null && field != null && field.equals(hiddenName)))
				id = "_" + hiddenName;
		}
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#addPropertyScript(java.lang.StringBuffer)
	 */
	@Override
	protected void addPropertyScript(StringBuffer buf) throws JspException {
		if (editable != null)
			buf.append("editable: ").append(editable).append(", ");
		
		if (triggerAction != null)
			buf.append("triggerAction: '").append(triggerAction).append("', ");
		
		if (store != null)
			buf.append("store: ").append(store).append(", ");
		
		if (mode != null)
			buf.append("mode: '").append(mode).append("', ");
		
		if (displayField != null)
			buf.append("displayField: '").append(displayField).append("', ");
		
		if (valueField != null)
			buf.append("valueField: '").append(valueField).append("', ");
		
		if (hiddenName != null)
			buf.append("hiddenName: '").append(hiddenName).append("', ");
	}
}
