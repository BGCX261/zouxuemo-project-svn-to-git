/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.web.taglib.ext.grid;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <code>ExtSelectionModelTag</code>
 * <p>����Ext��Ext.grid.SelectionModel js�����JSP Tag</p>
 *
 * @author ��ѧģ
 * @date 2008-3-20
 *
 * @jsp.tag name="extSelectionModel"
 */
public class ExtSelectionModelTag extends TagSupport {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = 4561631361399576059L;

	public static String DEFAULT_SELECTION_MODEL = "Ext.grid.CheckboxSelectionModel";
	
	/**
	 * <code>name</code> ��������ƣ���ѡ��������ã�������һ���������
	 */
	private String name = null;
	
	/**
	 * <code>clazz</code> ���������ƣ���ѡ����������ã���Ĭ��ʹ��DEFAULT_SELECTION_MODEL������
	 */
	private String clazz = DEFAULT_SELECTION_MODEL;
	
	/**
	 * <code>propertys</code> �����������ã���ѡ����ǩ��Ѹ������Լ��뵽������ȥ��Ҫ���ʽΪ"xxx: 'xxx', xxx:xxx,..."��ʽ
	 */
	private String propertys = null;

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
	 * ���� clazz ֵΪ <code>clazz</code>.
	 * @param clazz Ҫ���õ� clazz ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	/**
	 * ���� propertys ֵΪ <code>propertys</code>.
	 * @param propertys Ҫ���õ� propertys ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setPropertys(String propertys) {
		this.propertys = propertys;
	}

	/* ���� Javadoc��
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		ExtGridPanelTag extGridPanelTag = (ExtGridPanelTag)getParent();
		if (extGridPanelTag == null)
			throw new JspException("�����ҵ�extGridPanel��ǩ����");
		
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
