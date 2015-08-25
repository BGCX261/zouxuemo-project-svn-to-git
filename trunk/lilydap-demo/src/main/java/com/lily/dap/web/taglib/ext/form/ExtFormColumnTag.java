/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.web.taglib.ext.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.lily.dap.web.taglib.ext.ExtItemsInterface;

/**
 * <code>ExtFormColumnTag</code>
 * <p>����Ext��Form�� js�����JSP Tag���ֶ�</p>
 *
 * @author ��ѧģ
 * @date 2008-4-17
 *
 * @jsp.tag name="extFormColumn"
 */
public class ExtFormColumnTag extends BodyTagSupport implements ExtItemsInterface {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = 5387801652594176216L;

	/**
	 * <code>width</code> �п������
	 */
	protected String width = null;
	
	/**
	 * <code>layout</code> �в������ԣ�Ĭ��Ϊ"form"����
	 */
	protected String layout = "form";
	
	/**
	 * <code>propertys</code> �����������ã���ѡ����ǩ��Ѹ������Լ��뵽������ȥ��Ҫ���ʽΪ"xxx: 'xxx', xxx:xxx,..."��ʽ
	 */
	protected String propertys = null;

	/**
	 * ���� width.
	 * @return width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * ���� width ֵΪ <code>width</code>.
	 * @param width Ҫ���õ� width ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * ���� propertys.
	 * @return propertys
	 */
	public String getPropertys() {
		return propertys;
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
	
	// -------------------------------- ����Ϊ˽�б��� ----------------------------------
	
	/**
	 * <code>itemsScriptList</code> ��Ҫ���ɵ�items�ű��б��ɱ���ǩ�������ӱ�ǩ������д
	 */
	private List<String> itemsScriptList = new ArrayList<String>();
	
	public List<String> getItemsScriptList() {
		return itemsScriptList;
	}
	
	@Override
	public int doStartTag() throws JspException {
		Object parent = getParent();
		if (!(parent instanceof ExtFormColumnsTag))
			throw new JspException("�����ҵ�extFormColumns��ǩ����");
		
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
