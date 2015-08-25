/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.web.taglib.ext.form;

import javax.servlet.jsp.JspException;

/**
 * <code>ExtFormTriggerFieldTag</code>
 * <p>���ɴ������ؼ���Field�ű���JSP��ǩ</p>
 * �÷� : 
 *    <lilydap:extFormTriggerField field="returnVisitMan" labelTemplate="<a id=returnVisitManSelect href=#>$$</a>" name="returnVisitMan"  triggerClass="x-form-clear-trigger" propertys="readOnly : true, listeners : {'render' : clearFormField}"/>
 *    
	function clearFormField(component){
		component.trigger.on('click', function(e){
			resetFormFields(conditionForm, 'installManAlias','condition(installMan,in)');
			queryTask.delay(600);
		});
	}
 *
 * @author ����
 * @date 2008-6-12
 *
 * @jsp.tag name="extFormTriggerField"
 */
public class ExtFormTriggerFieldTag extends ExtFormFieldTag {
	/**
	 * <code>serialVersionUID</code> 
	 */
	private static final long serialVersionUID = -3849076595233171627L;

	/**
	 * <code>triggerClass</code> �����ؼ�css��ʽ
	 */
	protected String triggerClass = "x-form-clear-trigger";
	


	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public String getTriggerClass() {
		return triggerClass;
	}

	/**
	 * ���� triggerClass ֵΪ <code>triggerClass</code>.
	 * @param triggerClass Ҫ���õ� triggerClass ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTriggerClass(String triggerClass) {
		this.triggerClass = triggerClass;
	}



	/* ���� Javadoc��
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#modifyProperty()
	 */
	@Override
	protected void modifyProperty() {
		xtype = "trigger";
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#addPropertyScript(java.lang.StringBuffer)
	 */
	@Override
	protected void addPropertyScript(StringBuffer buf) throws JspException {
		buf.append("triggerClass: '").append(triggerClass).append("', ");
	}
}
