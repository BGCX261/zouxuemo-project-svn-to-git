/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext.form;

/**
 * <code>ExtFormHiddenTag</code>
 * <p>生成隐藏字段脚本的JSP标签</p>
 *
 * @author 邹学模
 * @date 2008-4-18
 *
 * @jsp.tag name="extFormHidden"
 */
public class ExtFormHiddenTag extends ExtFormFieldTag {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = 3997943165829871128L;

	/* （非 Javadoc）
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#modifyProperty()
	 */
	@Override
	protected void modifyProperty() {
		xtype = "hidden";
	}
}
