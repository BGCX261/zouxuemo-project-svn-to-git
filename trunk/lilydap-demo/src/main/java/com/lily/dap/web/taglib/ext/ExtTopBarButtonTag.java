/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.lily.dap.web.taglib.TagUtils;

/**
 * <code>ExtTopBarButtonTag</code>
 * <p>生成Ext的Ext.grid.Toolbar的Button js代码的JSP Tag</p>
 *
 * @author 邹学模
 * @date 2008-3-20
 *
 * @jsp.tag name="extTopBarButton"
 */
public class ExtTopBarButtonTag extends TagSupport {
	private static final long serialVersionUID = 1168261187780348911L;
	
//	/**
//	 * <code>name</code> 对象的名称，可选，如果设置，则生成一个对象变量
//	 */
//	private String name = null;
	
	/**
	 * <code>bundle</code> 使用的资源类名
	 */
	private String bundle = null;
	
	/**
	 * <code>header</code> 按钮标题，必需输入
	 */
	private String text = null;
	
	/**
	 * <code>iconCls</code> 按钮图标，可选
	 */
	private String iconCls = null;
	
	/**
	 * <code>handler</code> 处理函数
	 */
	private String handler = null;
	
	/**
	 * <code>clazz</code> 生成类名称，可选，如果不设置，则使用配置字段
	 */
	private String clazz = null;
	
	/**
	 * <code>propertys</code> 附加属性设置，可选，标签会把附加属性加入到属性中去。要求格式为"xxx: 'xxx', xxx:xxx,..."形式
	 */
	private String propertys = null;

//	/**
//	 * 设置 name 值为 <code>name</code>.
//	 * @param name 要设置的 name 值
//	 * 
//     * @jsp.attribute required="false" rtexprvalue="true"
//	 */
//	public void setName(String name) {
//		this.name = name;
//	}

	/**
	 * 设置 bundle 值为 <code>bundle</code>.
	 * @param bundle 要设置的 bundle 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * 设置 text 值为 <code>text</code>.
	 * @param text 要设置的 text 值
	 * 
     * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 设置 iconCls 值为 <code>iconCls</code>.
	 * @param iconCls 要设置的 iconCls 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	/**
	 * 设置 handler 值为 <code>handler</code>.
	 * @param handler 要设置的 handler 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setHandler(String handler) {
		this.handler = handler;
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
		ExtPanelInterface extPanelInterface = (ExtPanelInterface)getParent();
		if (extPanelInterface == null)
			throw new JspException("不能找到实现ExtPanelInterface接口的标签定义");
		
		if (text == null)
			throw new JspException("未设置按钮标题");
		
		StringBuffer buf = new StringBuffer();
		
		if (clazz != null)
			buf.append("new ").append(clazz).append("({");
		else
			buf.append("{");
		
		if (bundle == null)
			bundle = extPanelInterface.getBundle();
		
		buf.append("text: '").append(TagUtils.getMessage(pageContext, bundle, text)).append("'");

		if (iconCls != null)
			buf.append(", iconCls: '").append(iconCls).append("'");
		
		if (handler != null)
			buf.append(", handler: ").append(handler);
		
		if (propertys != null)
			buf.append(",").append(propertys);
		
		if (clazz != null)
			buf.append("})");
		else
			buf.append("}");
		
		extPanelInterface.getTbarScriptList().add(buf.toString());
		
		return EVAL_BODY_INCLUDE;
	}
}
