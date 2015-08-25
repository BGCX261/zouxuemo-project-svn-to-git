/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext.form;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.lily.dap.util.BaseEntityHelper;
import com.lily.dap.web.taglib.ext.ExtItemsInterface;

/**
 * <code>ExtFormFieldsTag</code>
 * <p>类/接口功能注释</p>
 *
 * @author 邹学模
 * @date 2008-4-17
 *
 * @jsp.tag name="extFormFields"
 */
public class ExtFormFieldsTag extends BodyTagSupport implements ExtItemsInterface {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = 4827848495764869839L;
	
	/**
	 * <code>name</code> 定义items的变量名，如果不设置，则不生成变量名，如果有父标签，则本属性忽略
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	private String name = null;
	
	/**
	 * <code>bundle</code> 使用的资源类名
	 */
	private String bundle = null;
	
	/**
	 * <code>model</code> 对应的实体对象全路径名，类似于"com.lily.dap.model.right.Role"，必需字段
	 */
	private String model = null;

	/**
	 * 设置 name 值为 <code>name</code>.
	 * @param name 要设置的 name 值
	 */
	public void setName(String name) {
		this.name = name;
	}

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
	 * 返回 bundle.
	 * @return bundle
	 */
	public String getBundle() {
		return bundle;
	}

	/**
	 * 设置 model 值为 <code>model</code>.
	 * @param model 要设置的 model 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setModel(String model) {
		this.model = model;
	}
	
	// -------------------------------- 以下为私有变量 ----------------------------------
	/**
	 * <code>entity</code> JSON检索的实体对象类
	 */
	private Class entity = null;
	
	/**
	 * <code>itemsScriptList</code> 需要生成的items脚本列表，由本标签包含的子标签负责填写
	 */
	private List<String> itemsScriptList = new ArrayList<String>();

	/**
	 * <code>tabIndex</code> 存放自动设置tab顺序的序列器
	 */
	private int tabIndex = 0;
	
	/**
	 * @return the entity
	 */
	public Class getEntity() {
		return entity;
	}
	
	public List<String> getItemsScriptList() {
		return itemsScriptList;
	}

	public int generateNewTabIndex() {
		tabIndex++;
		
		return tabIndex;
	}

	public int doStartTag() throws JspException {
		if (model != null) {
			try {
				entity = BaseEntityHelper.parseEntity(model);
			} catch (Exception e) {
				throw new JspException(e);
			}
		}

		ExtFormPanelTag extFormPanelTag = (ExtFormPanelTag)getParent();
		
		if (entity == null && extFormPanelTag != null) 
			entity = extFormPanelTag.getEntity();
		
		if (entity == null)
			throw new JspException("未指定要输出的实体类");
		
		if (bundle == null && extFormPanelTag != null) 
			bundle = extFormPanelTag.getBundle();
		
		tabIndex = 0;
		
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		StringBuffer buf = new StringBuffer();
		
		buf.append("[");
		boolean first = true;
		for (String s : itemsScriptList) {
			if (first)
				first = false;
			else
				buf.append(",\r\n");
			
			buf.append(s);
		}
		buf.append("]");
		
		ExtFormPanelTag extFormPanelTag = (ExtFormPanelTag)getParent();
		if (extFormPanelTag != null)
			extFormPanelTag.setItemsScript(buf.toString());
		else {
			if (name != null && !"".equals(name)) {
				buf.insert(0, "var " + name + " = ");
				buf.append(";");
			}
				
			try {
				pageContext.getOut().print(buf.toString());
			} catch (IOException ioe) {
				throw new JspTagException(ioe.toString(), ioe);
			}
		}
		
    	release();
		return EVAL_PAGE;
	}

	@Override
	public void release() {
		bundle = null;
		entity = null;
		
		for (int i = itemsScriptList.size() - 1; i>= 0; i--)
			itemsScriptList.remove(itemsScriptList.get(i));
	}
}
