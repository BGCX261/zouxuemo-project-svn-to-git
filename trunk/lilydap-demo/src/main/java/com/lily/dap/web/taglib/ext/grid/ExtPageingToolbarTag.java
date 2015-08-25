/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext.grid;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

/**
 * <code>ExtPageingToolbarTag</code>
 * <p>生成Ext的Ext.grid.PageingToolbar js代码的JSP Tag</p>
 *
 * @author 邹学模
 * @date 2008-3-20
 *
 * @jsp.tag name="extPageingToolbar"
 */
public class ExtPageingToolbarTag extends TagSupport {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = 4561631361399576059L;

	public static String DEFAULT_PAGING_TOORBAR = "Ext.PagingToolbar";
	
	public static String DEFAULT_PAGE_SIZE = "18";
	
	/**
	 * <code>name</code> 对象的名称，可选，如果设置，则生成一个对象变量
	 */
	private String name = null;
	
	/**
	 * <code>clazz</code> 生成类名称，可选，如果不设置，则默认使用DEFAULT_PAGING_TOORBAR来生成
	 */
	private String clazz = DEFAULT_PAGING_TOORBAR;
	
	/**
	 * <code>pageSize</code> 分页的每页记录条数，可选，如果不设置，则默认使用DEFAULT_PAGE_SIZE
	 */
	private Object pageSize = DEFAULT_PAGE_SIZE;
	
	/**
	 * <code>displayInfo</code> 是否显示信息
	 */
	private String displayInfo = "true";
	
	/**
	 * <code>displayMsg</code> 显示信息格式
	 */
	private String displayMsg = "显示第 {0} 条到 {1} 条记录，共 {2}  条";
	
	/**
	 * <code>emptyMsg</code> 空数据时的提示信息
	 */
	private String emptyMsg = "未找到记录数据";
	
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
	 * 设置 pageSize 值为 <code>pageSize</code>.
	 * @param pageSize 要设置的 pageSize 值
	 * @throws JspException 
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setPageSize(Object pageSize) throws JspException {
		this.pageSize = ExpressionEvaluatorManager.evaluate("pageSize", pageSize.toString(), Object.class, this, pageContext);
	}

	/**
	 * 设置 displayInfo 值为 <code>displayInfo</code>.
	 * @param displayInfo 要设置的 displayInfo 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setDisplayInfo(String displayInfo) {
		this.displayInfo = displayInfo;
	}

	/**
	 * 设置 displayMsg 值为 <code>displayMsg</code>.
	 * @param displayMsg 要设置的 displayMsg 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setDisplayMsg(String displayMsg) {
		this.displayMsg = displayMsg;
	}

	/**
	 * 设置 emptyMsg 值为 <code>emptyMsg</code>.
	 * @param emptyMsg 要设置的 emptyMsg 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setEmptyMsg(String emptyMsg) {
		this.emptyMsg = emptyMsg;
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
		
		buf.append("new ").append(clazz).append("({");
		buf.append("pageSize: ").append(pageSize).append(", ");
		
		if (extGridPanelTag.getStore() != null)
			buf.append("store: ").append(extGridPanelTag.getStore()).append(", ");
		
		buf.append("displayInfo: ").append(displayInfo).append(", ");
		buf.append("displayMsg: '").append(displayMsg).append("', ");
		buf.append("emptyMsg: '").append(emptyMsg).append("'");
		
		if (propertys != null)
			buf.append(", ").append(propertys);
		
		buf.append("});");
		
		if (name != null) {
			buf.insert(0, "var " + name + " = ").append(";");
			extGridPanelTag.getVarScriptList().add(buf.toString());
			extGridPanelTag.setPageingToolbarScript(name);
		} else {
			extGridPanelTag.setPageingToolbarScript(buf.toString());
		}
		
		return EVAL_BODY_INCLUDE;
	}
}
