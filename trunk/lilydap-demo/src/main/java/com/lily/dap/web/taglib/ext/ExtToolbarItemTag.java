/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ������������޹�˾
 */

package com.lily.dap.web.taglib.ext;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.lily.dap.web.taglib.TagUtils;

/**
 * <code>ExtToolbarItemTag</code>
 * <p>����Ext��Ext.grid.Toolbar��item js�����JSP Tag</p>
 *
 * @author ��ѧģ
 * @date 2008-3-20
 *
 * @jsp.tag name="extToolbarItem"
 */
public class ExtToolbarItemTag extends TagSupport {
	private static final long serialVersionUID = 1168261187780348911L;
	
	/**
	 * <code>position</code> ��������ťλ�ã�top/bottom��
	 */
	private String position = "top";
	
	/**
	 * <code>bundle</code> ʹ�õ���Դ����
	 */
	private String bundle = null;

	/**
	 * <code>xtype</code> ��������Ŀ����
	 */
	private String xtype = null;
	
	/**
	 * <code>header</code> ��ť���⣬��������
	 */
	private String text = null;
	
	/**
	 * <code>iconCls</code> ��ťͼ�꣬��ѡ
	 */
	private String iconCls = null;
	
	/**
	 * <code>handler</code> ��������
	 */
	private String handler = null;
	
	/**
	 * <code>clazz</code> ���������ƣ���ѡ����������ã���ʹ�������ֶ�
	 */
	private String clazz = null;
	
	/**
	 * <code>propertys</code> �����������ã���ѡ����ǩ��Ѹ������Լ��뵽������ȥ��Ҫ���ʽΪ"xxx: 'xxx', xxx:xxx,..."��ʽ
	 */
	private String propertys = null;

//	/**
//	 * ���� name ֵΪ <code>name</code>.
//	 * @param name Ҫ���õ� name ֵ
//	 * 
//     * @jsp.attribute required="false" rtexprvalue="true"
//	 */
//	public void setName(String name) {
//		this.name = name;
//	}

	/**
	 * ���� position ֵΪ <code>position</code>.
	 * @param position Ҫ���õ� position ֵ
	 * 
     * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * ���� xtype ֵΪ <code>xtype</code>.
	 * @param xtype Ҫ���õ� xtype ֵ
	 * 
     * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setXtype(String xtype) {
		this.xtype = xtype;
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
	 * ���� text ֵΪ <code>text</code>.
	 * @param text Ҫ���õ� text ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * ���� iconCls ֵΪ <code>iconCls</code>.
	 * @param iconCls Ҫ���õ� iconCls ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	/**
	 * ���� handler ֵΪ <code>handler</code>.
	 * @param handler Ҫ���õ� handler ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setHandler(String handler) {
		this.handler = handler;
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
		ExtPanelInterface extPanelInterface = (ExtPanelInterface)getParent();
		if (extPanelInterface == null)
			throw new JspException("�����ҵ�ʵ��ExtPanelInterface�ӿڵı�ǩ����");
		
		StringBuffer buf = new StringBuffer();
		
		if (clazz != null)
			buf.append("new ").append(clazz).append("({");
		else
			buf.append("{");
		
		buf.append("xtype: '").append(xtype).append("'");
		
		if (bundle == null)
			bundle = extPanelInterface.getBundle();
		
		if (text != null)
			buf.append(", text: '").append(TagUtils.getMessage(pageContext, bundle, text)).append("'");

		if (iconCls != null)
			buf.append(", iconCls: '").append(iconCls).append("'");
		
		if (handler != null)
			buf.append(", handler: ").append(handler);
		
		if (propertys != null)
			buf.append(", ").append(propertys);
		
		if (clazz != null)
			buf.append("})");
		else
			buf.append("}");
		
		if ("top".equals(position))
			extPanelInterface.getTbarScriptList().add(buf.toString());
		else
			extPanelInterface.getBbarScriptList().add(buf.toString());
		
		return EVAL_BODY_INCLUDE;
	}
}
