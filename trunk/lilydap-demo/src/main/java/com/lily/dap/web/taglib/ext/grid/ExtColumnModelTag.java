/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
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
 * <p>����Ext��Ext.grid.ColumnModel js�����JSP Tag</p>
 *
 * @author ��ѧģ
 * @date 2008-3-20
 *
 * @jsp.tag name="extColumnModel"
 */
public class ExtColumnModelTag extends BodyTagSupport {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = -7080034390003255015L;

	public static String DEFAULT_COLUMN_MODEL = "Ext.grid.ColumnModel";
	
	/**
	 * <code>name</code> ColumnModel��������ƣ���ѡ��������ã�������һ��ColumnModel�������
	 */
	private String name = null;
	
	/**
	 * <code>bundle</code> ʹ�õ���Դ����
	 */
	private String bundle = null;
	
	/**
	 * <code>model</code> ��Ӧ��ʵ�����ȫ·������������"com.lily.dap.model.right.Role"�������ֶ�
	 */
	private String model = null;
	
	/**
	 * <code>clazz</code> ���������ƣ���ѡ����������ã���Ĭ��ʹ��DEFAULT_COLUMN_MODEL������
	 */
	private String clazz = DEFAULT_COLUMN_MODEL;
	
	/**
	 * <code>onlyColumns</code> �����Column���飬������"new Ext.grid.ColumnModel"�Ľű����룬���ֻ����û�и���ǩʱʹ�ã�����и���ǩ���Զ�����
	 */
	private String onlyColumns = null;

	/**
	 * ���� name ֵΪ <code>name</code>.
	 * @param name Ҫ���õ� name ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ���� bundle ֵΪ <code>bundle</code>.
	 * @param bundle Ҫ���õ� bundle ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	/**
	 * ���� bundle.
	 * @return bundle
	 */
	public String getBundle() {
		return bundle;
	}

	/**
	 * ���� model ֵΪ <code>model</code>.
	 * @param model Ҫ���õ� model ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * ���� clazz ֵΪ <code>clazz</code>.
	 * @param clazz Ҫ���õ� clazz ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	/**
	 * ���� onlyColumns ֵΪ <code>onlyColumns</code>.
	 * @param onlyColumns Ҫ���õ� onlyColumns ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setOnlyColumns(String onlyColumns) {
		this.onlyColumns = onlyColumns;
	}
	
	// -------------------------------- ����Ϊ˽�б��� ----------------------------------
	/**
	 * <code>entity</code> JSON������ʵ�������
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
	 * ���� columnScriptList.
	 * @return columnScriptList
	 */
	public List<String> getColumnScriptList() {
		return columnScriptList;
	}

	/* ���� Javadoc��
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
			throw new JspException("δָ��Ҫ�����ʵ����");
		
		if (bundle == null && extGridPanelTag != null) 
			bundle = extGridPanelTag.getBundle();
		
		return EVAL_BODY_INCLUDE;
	}

	/* ���� Javadoc��
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
