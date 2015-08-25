/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext.grid;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <code>ExtSelectionModelTag</code>
 * <p>生成Ext的Ext.grid.SelectionModel js代码的JSP Tag</p>
 *
 * @author 邹学模
 * @date 2008-3-20
 *
 * @jsp.tag name="extSelectionModel"
 */
public class ExtSelectionModelTag extends TagSupport {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = 4561631361399576059L;

	public static String DEFAULT_SELECTION_MODEL = "Ext.grid.CheckboxSelectionModel";
	
	/**
	 * <code>name</code> 对象的名称，可选，如果设置，则生成一个对象变量
	 */
	private String name = null;
	
	/**
	 * <code>clazz</code> 生成类名称，可选，如果不设置，则默认使用DEFAULT_SELECTION_MODEL来生成
	 */
	private String clazz = DEFAULT_SELECTION_MODEL;
	
	/**
	 * <code>propertys</code> 附加属性设置，可选，标签会把附加属性加入到属性中去。要求格式为"xxx: 'xxx', xxx:xxx,..."形式
	 */
	private String propertys = null;

	/**
	 * 设置 name 值为 <code>name</code>.
	 * @param name 要设置的 name 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 设置 clazz 值为 <code>clazz</code>.
	 * @param clazz 要设置的 clazz 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	/**
	 * 设置 propertys 值为 <code>propertys</code>.
	 * @param propertys 要设置的 propertys 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setPropertys(String propertys) {
		this.propertys = propertys;
	}

	/* （非 Javadoc）
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		ExtGridPanelTag extGridPanelTag = (ExtGridPanelTag)getParent();
		if (extGridPanelTag == null)
			throw new JspException("不能找到extGridPanel标签定义");
		
		StringBuffer buf = new StringBuffer();
		
		buf.append("new ").append(clazz).append("(");
		
		if (propertys != null)
			buf.append("{").append(propertys).append("}");
		
		buf.append(")");
		
		if (name != null) {
			buf.insert(0, "var " + name + " = ").append(";");
			extGridPanelTag.getVarScriptList().add(buf.toString());
			extGridPanelTag.setSmScript(name);
		} else {
			extGridPanelTag.setSmScript(buf.toString());
		}
		
		return EVAL_BODY_INCLUDE;
	}
}
