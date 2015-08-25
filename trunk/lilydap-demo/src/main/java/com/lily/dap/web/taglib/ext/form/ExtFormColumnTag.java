/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.lily.dap.web.taglib.ext.ExtItemsInterface;

/**
 * <code>ExtFormColumnTag</code>
 * <p>生成Ext的Form列 js代码的JSP Tag表单字段</p>
 *
 * @author 邹学模
 * @date 2008-4-17
 *
 * @jsp.tag name="extFormColumn"
 */
public class ExtFormColumnTag extends BodyTagSupport implements ExtItemsInterface {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = 5387801652594176216L;

	/**
	 * <code>width</code> 列宽度属性
	 */
	protected String width = null;
	
	/**
	 * <code>layout</code> 列布局属性，默认为"form"布局
	 */
	protected String layout = "form";
	
	/**
	 * <code>propertys</code> 附加属性设置，可选，标签会把附加属性加入到属性中去。要求格式为"xxx: 'xxx', xxx:xxx,..."形式
	 */
	protected String propertys = null;

	/**
	 * 返回 width.
	 * @return width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * 设置 width 值为 <code>width</code>.
	 * @param width 要设置的 width 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * 返回 propertys.
	 * @return propertys
	 */
	public String getPropertys() {
		return propertys;
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
	
	// -------------------------------- 以下为私有变量 ----------------------------------
	
	/**
	 * <code>itemsScriptList</code> 需要生成的items脚本列表，由本标签包含的子标签负责填写
	 */
	private List<String> itemsScriptList = new ArrayList<String>();
	
	public List<String> getItemsScriptList() {
		return itemsScriptList;
	}
	
	@Override
	public int doStartTag() throws JspException {
		Object parent = getParent();
		if (!(parent instanceof ExtFormColumnsTag))
			throw new JspException("不能找到extFormColumns标签定义");
		
		return EVAL_BODY_INCLUDE;
	}
	

	@Override
	public int doEndTag() throws JspException {
		ExtFormColumnsTag extFormColumnsTag = (ExtFormColumnsTag)getParent();
		List<String> parentItemsScriptList = extFormColumnsTag.getItemsScriptList();
		
		StringBuffer buf = new StringBuffer();
		buf.append("{");
		
		if (layout != null)
			buf.append("layout: '").append(layout).append("', ");
		
		if (width != null)
			buf.append("columnWidth: ").append(width).append(", ");
		
    	if (propertys != null)
    		buf.append(propertys + ", ");
    	
    	buf.append("items: [");
		
		boolean first = true;
		for (String s : itemsScriptList) {
			if (first)
				first = false;
			else
				buf.append(",\r\n");
			
			buf.append(s);
		}
		
		buf.append("]}");
		String itemsScript = buf.toString();
		parentItemsScriptList.add(itemsScript);
		
		release();
		return EVAL_PAGE;
	}
	
	@Override
	public void release() {
		for (int i = itemsScriptList.size() - 1; i>= 0; i--)
			itemsScriptList.remove(itemsScriptList.get(i));
	}
}
