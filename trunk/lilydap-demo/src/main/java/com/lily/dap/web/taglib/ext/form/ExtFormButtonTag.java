/**
 * 
 */
package com.lily.dap.web.taglib.ext.form;

import javax.servlet.jsp.JspException;

/**
 * <code>ExtFormButtonTag</code>
 * <p>����Button�ֶνű���JSP��ǩ</p>
 * �÷� : 
 *    <lilydap:extFormButton text="��������" buttonType="button/submit/reset" style="align: center" handler="onSaveDataHandler" propertys="enableToggle : true, pressed : false"/>
 *    
 * 	function onSaveDataHandler(){
 * 		...
 * 	}
 *
 * @author ��ѧģ
 * @date 2011-7-15
 *
 * @jsp.tag name="extFormButton"
 */
public class ExtFormButtonTag extends ExtFormFieldTag {
	protected String text = null;
	
	protected String handler = null;
	
	protected String buttonType = "button";
	
	protected String style = "align: center";
	
	/**
	 * @param text
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @param handler
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setHandler(String handler) {
		this.handler = handler;
	}

	/**
	 * @param buttonType
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}

	/**
	 * @param style
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#modifyProperty()
	 */
	@Override
	protected void modifyProperty() {
		xtype = buttonType;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#addPropertyScript(java.lang.StringBuffer)
	 */
	@Override
	protected void addPropertyScript(StringBuffer buf) throws JspException {
		if (text != null)
			buf.append("text: '").append(text).append("', ");
		
		if (handler != null)
			buf.append("handler: ").append(handler).append(", ");
		
		if (style != null)
			buf.append("style: '").append(style).append("', ");
	}
}
