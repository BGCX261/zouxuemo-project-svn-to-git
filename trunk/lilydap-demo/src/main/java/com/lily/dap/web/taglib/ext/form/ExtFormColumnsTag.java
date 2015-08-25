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
 * <code>ExtFormColumnsTag</code>
 * <p>����Ext��Form��column���� js�����JSP Tag���ֶ�</p>
 *
 * @author ��ѧģ
 * @date 2008-4-17
 *
 * @jsp.tag name="extFormColumns"
 */
public class ExtFormColumnsTag extends BodyTagSupport implements ExtItemsInterface {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = 584406962131680016L;
	
	/**
	 * <code>layout</code> column�Ĳ������ԣ������޸�
	 */
	protected String layout = "column";
	
	/**
	 * <code>anchor</code> anchor����
	 */
	protected String anchor = "100%";
	
	/**
	 * <code>propertys</code> �����������ã���ѡ����ǩ��Ѹ������Լ��뵽������ȥ��Ҫ���ʽΪ"xxx: 'xxx', xxx:xxx,..."��ʽ
	 */
	protected String propertys = null;

	/**
	 * ���� anchor.
	 * @return anchor
	 */
	public String getAnchor() {
		return anchor;
	}

	/**
	 * ���� anchor ֵΪ <code>anchor</code>.
	 * @param anchor Ҫ���õ� anchor ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setAnchor(String anchor) {
		this.anchor = anchor;
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
	
	// -------------------------------- ����Ϊ������ʵ�� ----------------------------------
	/**
	 * �ɼ̳е����ฺ���޸����е�����Ĭ��ֵ
	 *
	 */
	protected void modifyProperty() {
		return;
	}
	
	/**
	 * �ɼ̳е����ฺ����Ӷ�������Խű�����
	 *
	 * @param buf
	 * @throws JspException
	 */
	protected void addPropertyScript(StringBuffer buf) throws JspException {
		return;
	}
	
	/**
	 * �ɼ̳е����ฺ���ͷŶ��������
	 *
	 */
	protected void releaseProperty() {
		return;
	}
	
	@Override
	public int doStartTag() throws JspException {
		Object parent = getParent();
		if (!(parent instanceof ExtItemsInterface))
			throw new JspException("�����ҵ�field������ǩ����");
		
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		ExtItemsInterface parent = (ExtItemsInterface)getParent();
		List<String> parentItemsScriptList = parent.getItemsScriptList();
		
		//����������޸����Է���
		modifyProperty();
		
		StringBuffer buf = new StringBuffer();
		buf.append("{");
		
		if (layout != null)
			buf.append("layout: '").append(layout).append("', ");
		
		if (anchor != null)
			buf.append("anchor: '").append(anchor).append("', ");

    	//�����������Ӷ������Խű�����
    	addPropertyScript(buf);
		
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
		//����������ͷŶ������Է���
		releaseProperty();
		
		for (int i = itemsScriptList.size() - 1; i>= 0; i--)
			itemsScriptList.remove(itemsScriptList.get(i));
	}
}
