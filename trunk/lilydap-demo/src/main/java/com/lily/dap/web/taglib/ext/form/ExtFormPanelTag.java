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
import com.lily.dap.web.taglib.TagUtils;
import com.lily.dap.web.taglib.ext.ExtPanelInterface;
import com.lily.dap.web.taglib.ext.data.ExtDataTargetInterface;

/**
 * <code>ExtFormPanelTag</code>
 * <p>����Ext��FormPanel js�����JSP Tag���ֶ�</p>
 *
 * @author ��ѧģ
 * @date 2008-4-17
 *
 * @jsp.tag name="extFormPanel"
 */
@SuppressWarnings("unchecked")
public class ExtFormPanelTag extends BodyTagSupport implements ExtPanelInterface, ExtDataTargetInterface {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = 6186861768453286440L;

	public static String DEFAULT_FORM_PANEL_PANEL = "Ext.FormPanel";
	
	/**
	 * <code>id</code> FORM��IDֵ����ѡ
	 */
	private String id = null;
	
	/**
	 * <code>name</code> FORM��������ƣ�����
	 */
	private String name = null;
	
	/**
	 * <code>clazz</code> ���������ƣ���ѡ����������ã���Ĭ��ʹ��DEFAULT_FORM_PANEL_PANEL������
	 */
	private String clazz = DEFAULT_FORM_PANEL_PANEL;
	
	/**
	 * <code>bundle</code> ʹ�õ���Դ����
	 */
	private String bundle = null;
	
	/**
	 * <code>title</code> ���ڱ���
	 */
	private String title = null;
	
	/**
	 * <code>model</code> Reader���ݽṹ��Ӧ��ʵ�����ȫ·������������"com.lily.dap.model.right.Role"�������ֶ�
	 */
	private String model = null;

	/**
	 * <code>url</code> FORMװ�ص�URL·��
	 */
	private String url = null;
	
	/**
	 * <code>method</code> FORM�ύ��ʽ��Ĭ��Ϊ"POST"
	 */
	protected String method = "POST";
	
	/**
	 * <code>frame</code> �Ƿ�������
	 */
	protected String frame = "true";
	
	/**
	 * <code>labelAlign</code> �ֶα�ǩ���뷽ʽ��Ĭ��Ϊ"right"
	 */
	protected String labelAlign = "right";
	
	/**
	 * <code>labelWidth</code> �ֶα�ǩ��ȣ�Ĭ��Ϊ70
	 */
	protected String labelWidth = "70";
	
	/**
	 * <code>reader</code> reader���ԣ�Ҫ������
	 */
	protected String reader = null;
	
	/**
	 * <code>propertys</code> �����������ã���ѡ����ǩ��Ѹ������Լ��뵽������ȥ��Ҫ���ʽΪ"xxx: 'xxx', xxx:xxx,..."��ʽ
	 */
	private String propertys = null;
	
	/**
	 * ���� id.
	 * @return id
	 */
	public String getId() {
		return id;
	}

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
	 * ���� name.
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * ���� name ֵΪ <code>name</code>.
	 * @param name Ҫ���õ� name ֵ
	 * 
	 * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ���� clazz.
	 * @return clazz
	 */
	public String getClazz() {
		return clazz;
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
	 * ���� bundle.
	 * @return bundle
	 */
	public String getBundle() {
		return bundle;
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
	 * ���� title.
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * ���� title ֵΪ <code>title</code>.
	 * @param title Ҫ���õ� title ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * ���� model.
	 * @return model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * ���� model ֵΪ <code>model</code>.
	 * @param model Ҫ���õ� model ֵ
	 * 
	 * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * ���� url.
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * ���� url ֵΪ <code>url</code>.
	 * @param url Ҫ���õ� url ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * ���� method.
	 * @return method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * ���� method ֵΪ <code>method</code>.
	 * @param method Ҫ���õ� method ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * ���� frame.
	 * @return frame
	 */
	public String getFrame() {
		return frame;
	}

	/**
	 * ���� frame ֵΪ <code>frame</code>.
	 * @param frame Ҫ���õ� frame ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setFrame(String frame) {
		this.frame = frame;
	}

	/**
	 * ���� reader.
	 * @return reader
	 */
	public String getReader() {
		return reader;
	}

	/**
	 * ���� reader ֵΪ <code>reader</code>.
	 * @param reader Ҫ���õ� reader ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setReader(String reader) {
		this.reader = reader;
	}

	/**
	 * ���� labelAlign.
	 * @return labelAlign
	 */
	public String getLabelAlign() {
		return labelAlign;
	}

	/**
	 * ���� labelAlign ֵΪ <code>labelAlign</code>.
	 * @param labelAlign Ҫ���õ� labelAlign ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setLabelAlign(String labelAlign) {
		this.labelAlign = labelAlign;
	}

	/**
	 * ���� labelWidth.
	 * @return labelWidth
	 */
	public String getLabelWidth() {
		return labelWidth;
	}

	/**
	 * ���� labelWidth ֵΪ <code>labelWidth</code>.
	 * @param labelWidth Ҫ���õ� labelWidth ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setLabelWidth(String labelWidth) {
		this.labelWidth = labelWidth;
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
	 * <code>clazz</code> FormPanel������ʵ�������
	 */
	private Class entity = null;
	
	/**
	 * <code>tbarScriptList</code> FormPanel�й�����tbar����ű����ɲ����ã��ɱ���ǩ������ExtBarButtonTag��ǩ������д����
	 */
	private List<String> tbarScriptList = new ArrayList<String>();
	
	/**
	 * <code>bbarScriptList</code> FormPanel�й�����bbar����ű����ɲ����ã��ɱ���ǩ������ExtBarButtonTag��ǩ������д����
	 */
	private List<String> bbarScriptList = new ArrayList<String>();

	/**
	 * <code>itemsScript</code> items�ű��ַ���
	 */
	private String itemsScript = null;

	/**
	 * ���� entity.
	 * @return entity
	 */
	public Class getEntity() {
		return entity;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.webapp.taglib.ext.ExtPanelInterface#getBbarScriptList()
	 */
	public List<String> getBbarScriptList() {
		return bbarScriptList;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.webapp.taglib.ext.ExtPanelInterface#getTbarScriptList()
	 */
	public List<String> getTbarScriptList() {
		return tbarScriptList;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.webapp.taglib.ext.data.ExtDataTargetInterface#setDataScript(java.lang.String)
	 */
	public void setDataScript(String dataScript) {
		reader = dataScript;
	}
	
	/**
	 * ���� itemsScript ֵΪ <code>itemsScript</code>.
	 * @param itemsScript Ҫ���õ� itemsScript ֵ
	 */
	public void setItemsScript(String itemsScript) {
		this.itemsScript = itemsScript;
	}

	/* ���� Javadoc��
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			entity = BaseEntityHelper.parseEntity(model);
		} catch (Exception e) {
			throw new JspException(e);
		}
		
		return EVAL_BODY_INCLUDE;
	}

	/* ���� Javadoc��
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		StringBuffer buf = new StringBuffer();
		
		buf.append("var " + name + " = new " + clazz + "({");
		
    	if (id != null)
    		buf.append("id: '" + id + "', ");
		
    	if (labelAlign != null)
    		buf.append("labelAlign: '" + labelAlign + "', ");
		
    	if (labelWidth != null)
    		buf.append("labelWidth: " + labelWidth + ", ");
		
    	if (url != null)
    		buf.append("url: '" + url + "', ");
		
    	if (method != null)
    		buf.append("method: '" + method + "', ");
		
    	if (title != null) {
    		String str;
        	try {
				str = TagUtils.getMessage(pageContext, bundle, title);
			} catch (Exception e) {
				str = title;
			}
			
       		buf.append("title: '" + str + "', ");
    	}
		
    	if (frame != null)
    		buf.append("frame: " + frame + ", ");
		
    	if (propertys != null)
    		buf.append(propertys + ", ");

    	if (reader != null) 
    		buf.append("reader: ").append(reader).append(", ");
		
    	//����Ƿ����ö��㹤������������
    	if (tbarScriptList.size() > 0){
    		buf.append("tbar: [");
    		
    		boolean first = true;
    		for (String s : tbarScriptList) {
    			if (first)
    				first = false;
    			else
    				buf.append(", ");
    				
    			buf.append(s);
    		}
    		
    		buf.append("], ");
    	}
    	
    	//����Ƿ����õײ㹤������������
    	if (bbarScriptList.size() > 0){
    		buf.append("bbar: [");
    		
    		boolean first = true;
    		for (String s : bbarScriptList) {
    			if (first)
    				first = false;
    			else
    				buf.append(", ");
    				
    			buf.append(s);
    		}
    		
    		buf.append("], ");
    	}
    	
    	buf.append("items: ").append(itemsScript).append("});");
		
		try {
			pageContext.getOut().print(buf.toString());
		} catch (IOException ioe) {
			throw new JspTagException(ioe.toString(), ioe);
		}
    	
    	release();
		return EVAL_PAGE;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.BodyTagSupport#release()
	 */
	@Override
	public void release() {
		for (int i = tbarScriptList.size() - 1; i>= 0; i--)
			tbarScriptList.remove(tbarScriptList.get(i));
		
		for (int i = bbarScriptList.size() - 1; i>= 0; i--)
			bbarScriptList.remove(bbarScriptList.get(i));

		entity = null;
		itemsScript = null;
		
		super.release();
	}
}
