/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext.grid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.lily.dap.util.BaseEntityHelper;

/**
 * <code>ExtColumnModelTag</code>
 * <p>生成Ext的Ext.grid.ColumnModel js代码的JSP Tag</p>
 *
 * @author 邹学模
 * @date 2008-3-20
 *
 * @jsp.tag name="extColumnModel"
 */
public class ExtColumnModelTag extends BodyTagSupport {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = -7080034390003255015L;

	public static String DEFAULT_COLUMN_MODEL = "Ext.grid.ColumnModel";
	
	/**
	 * <code>name</code> ColumnModel对象的名称，可选，如果设置，则生成一个ColumnModel对象变量
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
	 * <code>clazz</code> 生成类名称，可选，如果不设置，则默认使用DEFAULT_COLUMN_MODEL来生成
	 */
	private String clazz = DEFAULT_COLUMN_MODEL;
	
	/**
	 * <code>onlyColumns</code> 仅输出Column数组，不包括"new Ext.grid.ColumnModel"的脚本代码，这个只能在没有父标签时使用，如果有父标签，自动忽略
	 */
	private String onlyColumns = null;

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
	 * 设置 onlyColumns 值为 <code>onlyColumns</code>.
	 * @param onlyColumns 要设置的 onlyColumns 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setOnlyColumns(String onlyColumns) {
		this.onlyColumns = onlyColumns;
	}
	
	// -------------------------------- 以下为私有变量 ----------------------------------
	/**
	 * <code>entity</code> JSON检索的实体对象类
	 */
	private Class entity = null;
	
	private List<String> columnScriptList = new ArrayList<String>();
	
	/**
	 * @return the entity
	 */
	public Class getEntity() {
		return entity;
	}
	
	/**
	 * 返回 columnScriptList.
	 * @return columnScriptList
	 */
	public List<String> getColumnScriptList() {
		return columnScriptList;
	}

	/* （非 Javadoc）
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		if (model != null) {
			try {
				entity = BaseEntityHelper.parseEntity(model);
			} catch (Exception e) {
				throw new JspException(e);
			}
		}

		ExtGridPanelTag extGridPanelTag = (ExtGridPanelTag)getParent();
		
		if (entity == null && extGridPanelTag != null) 
			entity = extGridPanelTag.getEntity();
		
		if (entity == null)
			throw new JspException("未指定要输出的实体类");
		
		if (bundle == null && extGridPanelTag != null) 
			bundle = extGridPanelTag.getBundle();
		
		return EVAL_BODY_INCLUDE;
	}

	/* （非 Javadoc）
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		ExtGridPanelTag extGridPanelTag = (ExtGridPanelTag)getParent();
		
		StringBuffer buf = new StringBuffer();
		
		boolean isOnlyColumns = onlyColumns != null && "true".equals(onlyColumns.toLowerCase()) ? true : false; 
		if (extGridPanelTag != null)
			isOnlyColumns = false;
		
		if (!isOnlyColumns)
			buf.append("new ").append(clazz).append("(");
		
		buf.append("[");
		
		boolean first = true;
		for (String s : columnScriptList) {
			if (first)
				first = false;
			else
				buf.append(", ");
			
			buf.append(s);
		}
		
		buf.append("]");
		
		if (!isOnlyColumns)
			buf.append(")");
		
		
		if (name != null)
			buf.insert(0, "var " + name + " = ").append(";");
			
		if (extGridPanelTag != null) {
			if (name != null) {
				extGridPanelTag.getVarScriptList().add(buf.toString());
				extGridPanelTag.setCmScript(name);
			} else {
				extGridPanelTag.setCmScript(buf.toString());
			}
		} else {
			try {
				pageContext.getOut().print(buf.toString());
			} catch (IOException ioe) {
				throw new JspTagException(ioe.toString(), ioe);
			}
		}
		
    	release();
		return EVAL_PAGE;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#release()
	 */
	@Override
	public void release() {
		bundle = null;
		entity = null;
		
		for (int i = columnScriptList.size() - 1; i>= 0; i--)
			columnScriptList.remove(columnScriptList.get(i));
		
		super.release();
	}
}
