/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.web.taglib.ext.grid;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.lily.dap.util.BaseEntityHelper;
import com.lily.dap.web.taglib.TagUtils;

/**
 * <code>ExtColumnTag</code>
 * <p>����Ext��Ext.grid.ColumnModel ��Column js�����JSP Tag</p>
 *
 * @author ��ѧģ
 * @date 2008-3-20
 *
 * @jsp.tag name="extColumn"
 */
public class ExtColumnTag extends TagSupport {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = 8659418009595302033L;

	/**
	 * <code>id</code> �е�IDֵ����ѡ
	 */
	protected String id = null;
	
	/**
	 * <code>name</code> �ж�������ƣ���ѡ��������ã�������һ���ж������������Ҫ������clazz����
	 */
	protected String name = null;
	
	/**
	 * <code>bundle</code> ʹ�õ���Դ����
	 */
	private String bundle = null;
	
	/**
	 * <code>header</code> ��ͷ���⣬��ѡ����������ã���ȡʵ������е�labelע��
	 */
	protected String header = null;
	
	/**
	 * <code>field</code> ������ʵ���������������ѡ�����field���Բ����ã���clazz���Ա�������
	 */
	protected String field = null;
	
	/**
	 * <code>clazz</code> ���������ƣ���ѡ��������ã����ʹ�ø�����newһ���¶��󣬲��ѵ�ǰ������Ϊ�������ݸ��ö���
	 */
	protected String clazz = null;
	
	/**
	 * <code>width</code> �п��
	 */
	protected String width = null;
	
	/**
	 * <code>renderer</code> ��Ⱦ����
	 */
	protected String renderer = null;
	
	/**
	 * <code>propertys</code> �����������ã���ѡ����ǩ��Ѹ������Լ��뵽������ȥ��Ҫ���ʽΪ"xxx: 'xxx', xxx:xxx,..."��ʽ
	 */
	protected String propertys = null;
	
	/**
	 * <code>editor</code> �༭�������ã���ѡ��������ñ༭���ԣ���ѱ���������Ϊ�������ݸ�editor���ԡ�Ҫ���ʽΪ"xxx: 'xxx', xxx:xxx,..."��ʽ
	 */
	protected String editor = null;

	/**
	 * ���� id ֵΪ <code>id</code>.
	 * @param id Ҫ���õ� id ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setId(String id) {
		this.id = id;
	}

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
	 * ���� field ֵΪ <code>field</code>.
	 * @param field Ҫ���õ� field ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setField(String field) {
		this.field = field;
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
	 * ���� header ֵΪ <code>header</code>.
	 * @param header Ҫ���õ� header ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setHeader(String header) {
		this.header = header;
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
	 * ���� width ֵΪ <code>width</code>.
	 * @param width Ҫ���õ� width ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * ���� renderer ֵΪ <code>renderer</code>.
	 * @param renderer Ҫ���õ� renderer ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setRenderer(String renderer) {
		this.renderer = renderer;
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

	/**
	 * ���� editor ֵΪ <code>editor</code>.
	 * @param editor Ҫ���õ� editor ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setEditor(String editor) {
		this.editor = editor;
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
	
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		ExtGridPanelTag extGridPanelTag = (ExtGridPanelTag)findAncestorWithClass(this, ExtGridPanelTag.class);
		if (extGridPanelTag == null && name != null)
			throw new JspException("�����ҵ�extGridPanel��ǩ����");
		
		ExtColumnModelTag extColumnModelTag = (ExtColumnModelTag)getParent();
		if (extColumnModelTag == null)
			throw new JspException("�����ҵ�extColumnModel��ǩ����");

		//�����ڱ��Ѿ�������nameָ���Ķ���
//		if (name != null && clazz == null)
//			throw new JspException("������name���ԣ�����û�ж���clazz����");
		
//		if (field == null && clazz == null)
//			throw new JspException("field���Ժ�clazz���Ա������ٶ���һ��");

		if (name != null && clazz == null) {
			extColumnModelTag.getColumnScriptList().add(name);
			return SKIP_BODY;
		}
		
		//����������޸����Է���
		modifyProperty();
		
		StringBuffer buf = new StringBuffer();
		
		if (name != null) {
			buf.append("var ").append(name).append(" = new ").append(clazz).append("(");
		} else {
			if (clazz != null)
				buf.append("new ").append(clazz).append("(");
		}
		
		buf.append("{");
    	
    	if (id != null)
    		buf.append("id: '" + id + "', ");
    	else if (field != null)
    		buf.append("id: '" + field + "', ");
    			
    	if (field != null)
    		buf.append("dataIndex: '" + field + "', ");

    	String headTitle = null;
    	if (header != null) {
    		if (bundle == null)
    			bundle = extColumnModelTag.getBundle();
    		
    		headTitle = TagUtils.getMessage(pageContext, bundle, header);
    	} else if (field != null) {
    		try {
				headTitle = BaseEntityHelper.getEntityFieldLabel(extColumnModelTag.getEntity(), field, true);
			} catch (Exception e) {}
    	}
    	
    	if (headTitle != null)
    		buf.append("header: '" + headTitle + "', ");
    	
    	if (width != null)
    		buf.append("width: " + width + ", ");
    	
    	if (renderer != null)
    		buf.append("renderer: " + renderer + ", ");

    	//�����������Ӷ������Խű�����
    	addPropertyScript(buf);
		
    	if (propertys != null)
    		buf.append(propertys + ", ");
		
    	if (editor != null)
    		buf.append("editor:{" + editor + "}, ");
    	
    	if (buf.charAt(buf.length() - 1) != '{') {
    		buf.setLength(buf.length() - 2);
    		buf.append("}");
    	} else {
    		buf.setLength(buf.length() - 1);
    	}
		
		if (name != null) {
			buf.append(");");
			
			extColumnModelTag.getColumnScriptList().add(name);
			extGridPanelTag.getVarScriptList().add(buf.toString());
		} else {
			if (clazz != null)
				buf.append(")");
			
			extColumnModelTag.getColumnScriptList().add(buf.toString());
		}
		
		bundle = null;
		
		//����������ͷŶ������Է���
		releaseProperty();
		
		return SKIP_BODY;
	}
}
