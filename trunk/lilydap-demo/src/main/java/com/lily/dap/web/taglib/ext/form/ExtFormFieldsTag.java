/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
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
 * <p>��/�ӿڹ���ע��</p>
 *
 * @author ��ѧģ
 * @date 2008-4-17
 *
 * @jsp.tag name="extFormFields"
 */
public class ExtFormFieldsTag extends BodyTagSupport implements ExtItemsInterface {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = 4827848495764869839L;
	
	/**
	 * <code>name</code> ����items�ı���������������ã������ɱ�����������и���ǩ�������Ժ���
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
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
	 * ���� name ֵΪ <code>name</code>.
	 * @param name Ҫ���õ� name ֵ
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
	
	// -------------------------------- ����Ϊ˽�б��� ----------------------------------
	/**
	 * <code>entity</code> JSON������ʵ�������
	 */
	private Class entity = null;
	
	/**
	 * <code>itemsScriptList</code> ��Ҫ���ɵ�items�ű��б��ɱ���ǩ�������ӱ�ǩ������д
	 */
	private List<String> itemsScriptList = new ArrayList<String>();

	/**
	 * <code>tabIndex</code> ����Զ�����tab˳���������
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
			throw new JspException("δָ��Ҫ�����ʵ����");
		
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
