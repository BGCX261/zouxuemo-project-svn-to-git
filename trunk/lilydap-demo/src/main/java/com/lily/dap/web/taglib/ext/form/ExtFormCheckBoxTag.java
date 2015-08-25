/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext.form;

/**
 * <code>ExtFormCheckBoxTag</code>
 * <p>生成CheckBox字段脚本的JSP标签</p>
 *
 * @author 邹学模
 * @date 2008-4-18
 *
 * @jsp.tag name="extFormCheckBox"
 */
public class ExtFormCheckBoxTag extends ExtFormFieldTag {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = 7252410769208228417L;

	/* （非 Javadoc）
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#modifyProperty()
	 */
	@Override
	protected void modifyProperty() {
		xtype = "checkbox";
	}
}
