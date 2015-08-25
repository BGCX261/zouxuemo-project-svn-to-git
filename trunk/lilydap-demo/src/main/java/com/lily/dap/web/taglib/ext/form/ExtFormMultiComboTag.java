/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.web.taglib.ext.form;

import javax.servlet.jsp.JspException;

/**
 * <code>ExtFormMultiComboTag</code>
 * <p>���ɶ�ѡ�����б��ֶνű���JSP��ǩ</p>
 *
 * @author ��ɲ�
 * @date 2009-7-10
 *
 * @jsp.tag name="extFormMultiCombo"
 */
public class ExtFormMultiComboTag extends ExtFormFieldTag {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = -8336787781614422311L;

	/**
	 * <code>editable</code> �����б���ı����Ƿ�ɱ༭
	 */
	protected String editable = "false";

	/**
	 * <code>triggerAction</code> triggerAction����
	 */
	protected String triggerAction = "all";

	/**
	 * <code>mode</code> �����б��������ģʽ
	 */
	protected String mode = "local";

	/**
	 * <code>store</code> �����б������Դ
	 */
	protected String store = null;

	/**
	 * <code>displayField</code> �����б���ʾ�ֶ�
	 */
	protected String displayField = null;

	/**
	 * <code>valueField</code> �����б�ֵ�ֶ�
	 */
	protected String valueField = null;

	/**
	 * <code>hiddenName</code> �����ֶ������������������ʾֵ��ʵ��ֵ����������Ҫ�����ָ��hiddenName
	 */
	protected String hiddenName = null;

	/**
	 * ���� editable ֵΪ <code>editable</code>.
	 * @param editable Ҫ���õ� editable ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setEditable(String editable) {
		this.editable = editable;
	}

	/**
	 * ���� triggerAction ֵΪ <code>triggerAction</code>.
	 * @param triggerAction Ҫ���õ� triggerAction ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTriggerAction(String triggerAction) {
		this.triggerAction = triggerAction;
	}

	/**
	 * ���� mode ֵΪ <code>mode</code>.
	 * @param mode Ҫ���õ� mode ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * ���� store ֵΪ <code>store</code>.
	 * @param store Ҫ���õ� store ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setStore(String store) {
		this.store = store;
	}

	/**
	 * ���� displayField ֵΪ <code>displayField</code>.
	 * @param displayField Ҫ���õ� displayField ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setDisplayField(String displayField) {
		this.displayField = displayField;
	}

	/**
	 * ���� valueField ֵΪ <code>valueField</code>.
	 * @param valueField Ҫ���õ� valueField ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

//	/**
//	 * ���� hiddenName ֵΪ <code>hiddenName</code>.
//	 * @param hiddenName Ҫ���õ� hiddenName ֵ
//	 * 
//	 * @jsp.attribute required="false" rtexprvalue="true"
//	 */
//	public void setHiddenName(String hiddenName) {
//		this.hiddenName = hiddenName;
//	}

	/* ���� Javadoc��
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#modifyProperty()
	 */
	@Override
	protected void modifyProperty() {
		xtype = "multicombo";
		
		if (valueField == null)
			valueField = displayField;

		//���ֵ�ֶκ���ʾ�ֶβ�һ�£���Ҫ������hiddenName���ԣ���˱��뱣֤idֵ���ܺ�hiddenName��ͬ��������ʾ����
		if (!valueField.equals(displayField)) {
			hiddenName = name != null ? name : field;
			
			if ((id != null && id.equals(hiddenName)) ||
				(id == null && field != null && field.equals(hiddenName)))
				id = "_" + hiddenName;
		}
	}

	/* ���� Javadoc��
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
