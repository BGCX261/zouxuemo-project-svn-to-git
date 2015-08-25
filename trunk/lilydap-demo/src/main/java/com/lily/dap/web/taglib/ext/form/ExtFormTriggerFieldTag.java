/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext.form;

import javax.servlet.jsp.JspException;

/**
 * <code>ExtFormTriggerFieldTag</code>
 * <p>生成带触发控件的Field脚本的JSP标签</p>
 * 用法 : 
 *    <lilydap:extFormTriggerField field="returnVisitMan" labelTemplate="<a id=returnVisitManSelect href=#>$$</a>" name="returnVisitMan"  triggerClass="x-form-clear-trigger" propertys="readOnly : true, listeners : {'render' : clearFormField}"/>
 *    
	function clearFormField(component){
		component.trigger.on('click', function(e){
			resetFormFields(conditionForm, 'installManAlias','condition(installMan,in)');
			queryTask.delay(600);
		});
	}
 *
 * @author 孙暾
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
	 * <code>triggerClass</code> 触发控件css样式
	 */
	protected String triggerClass = "x-form-clear-trigger";
	


	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public String getTriggerClass() {
		return triggerClass;
	}

	/**
	 * 设置 triggerClass 值为 <code>triggerClass</code>.
	 * @param triggerClass 要设置的 triggerClass 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTriggerClass(String triggerClass) {
		this.triggerClass = triggerClass;
	}



	/* （非 Javadoc）
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#modifyProperty()
	 */
	@Override
	protected void modifyProperty() {
		xtype = "trigger";
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#addPropertyScript(java.lang.StringBuffer)
	 */
	@Override
	protected void addPropertyScript(StringBuffer buf) throws JspException {
		buf.append("triggerClass: '").append(triggerClass).append("', ");
	}
}
