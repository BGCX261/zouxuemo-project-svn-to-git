/**
 * 
 */
package com.lily.dap.web.taglib.ext.form;

import javax.servlet.jsp.JspException;

/**
 * <code>ExtFormButtonTag</code>
 * <p>生成Button字段脚本的JSP标签</p>
 * 用法 : 
 *    <lilydap:extFormButton text="保存数据" buttonType="button/submit/reset" style="align: center" handler="onSaveDataHandler" propertys="enableToggle : true, pressed : false"/>
 *    
 * 	function onSaveDataHandler(){
 * 		...
 * 	}
 *
 * @author 邹学模
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

	/* （非 Javadoc）
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#modifyProperty()
	 */
	@Override
	protected void modifyProperty() {
		xtype = buttonType;
	}

	/* （非 Javadoc）
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
