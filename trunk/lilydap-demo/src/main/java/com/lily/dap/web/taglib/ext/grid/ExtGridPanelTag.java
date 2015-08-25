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
import com.lily.dap.web.taglib.TagUtils;
import com.lily.dap.web.taglib.ext.ExtPanelInterface;
import com.lily.dap.web.taglib.ext.data.ExtDataTargetInterface;

/**
 * <code>ExtGridPanelTag</code>
 * <p>����Ext��Ext.grid.GridPanel js�����JSP Tag</p>
 *
 * @author ��ѧģ
 * @date 2008-3-20
 *
 * @jsp.tag name="extGridPanel"
 */
@SuppressWarnings("unchecked")
public class ExtGridPanelTag extends BodyTagSupport implements ExtPanelInterface, ExtDataTargetInterface {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = 4818408163449394773L;

	public static String DEFAULT_GRID_PANEL = "Ext.grid.GridPanel";
	
	/**
	 * <code>id</code> �е�IDֵ����ѡ
	 */
	private String id = null;
	
	/**
	 * <code>name</code> �ж�������ƣ�����
	 */
	private String name = null;
	
	/**
	 * <code>store</code> ����Store�ı�������������ֶ�δ���ã���Ҫ�󱾱�ǩ�������һ��extJsonStore��ǩ
	 */
	private String store;
	
	/**
	 * <code>clazz</code> ���������ƣ���ѡ����������ã���Ĭ��ʹ��DEFAULT_GRID_PANEL������
	 */
	private String clazz = DEFAULT_GRID_PANEL;
	
	/**
	 * <code>bundle</code> ʹ�õ���Դ����
	 */
	private String bundle = null;
	
	/**
	 * <code>title</code> Grid����
	 */
	private String title = null;
	
	/**
	 * <code>model</code> Store���ݽṹ��Ӧ��ʵ�����ȫ·������������"com.lily.dap.model.right.Role"�������ֶ�
	 */
	private String model = null;
	
	/**
	 * <code>autoExpandColumn</code> �Զ���չ�����ԣ���ѡ
	 */
	private String autoExpandColumn = null;
	
	/**
	 * <code>propertys</code> �����������ã���ѡ����ǩ��Ѹ������Լ��뵽������ȥ��Ҫ���ʽΪ"xxx: 'xxx', xxx:xxx,..."��ʽ
	 */
	private String propertys = null;

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
     * @jsp.attribute required="true" rtexprvalue="true"
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
	 * ���� model ֵΪ <code>model</code>.
	 * @param model Ҫ���õ� model ֵ
	 * 
     * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setModel(String model) {
		this.model = model;
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
	 * @return the bundle
	 */
	public String getBundle() {
		return bundle;
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
	 * ���� store ֵΪ <code>store</code>.
	 * @param store Ҫ���õ� store ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setStore(String store) {
		this.store = store;
	}

	/**
	 * ���� store.
	 * @return store
	 */
	public String getStore() {
		return store;
	}

	/**
	 * ���� autoExpandColumn ֵΪ <code>autoExpandColumn</code>.
	 * @param autoExpandColumn Ҫ���õ� autoExpandColumn ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setAutoExpandColumn(String autoExpandColumn) {
		this.autoExpandColumn = autoExpandColumn;
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
	 * <code>clazz</code> Grid������ʵ�������
	 */
	private Class entity = null;
	
	/**
	 * <code>varScriptList</code> Grid����Ҫ���ɵı����ű��б��ɱ���ǩ������ExtColumnModelTag��ǩ��ExtColumnTag��ǩ������д
	 */
	private List<String> varScriptList = new ArrayList<String>();

	/**
	 * <code>cmScript</code> Grid�й�����ColumnModel����ű����ɱ���ǩ������ExtColumnModelTag��ǩ������д
	 */
	private String cmScript = null;
	
	/**
	 * <code>smScript</code> Grid�й�����SelectionModel����ű����ɲ����ã��ɱ���ǩ������ExtSelectionModelTag��ǩ������д
	 */
	private String smScript = null;
	
	/**
	 * <code>tbarScriptList</code> Grid�й�����tbar����ű����ɲ����ã��ɱ���ǩ������ExtBarButtonTag��ǩ������д����
	 */
	private List<String> tbarScriptList = new ArrayList<String>();
	
	/**
	 * <code>bbarScriptList</code> Grid�й�����bbar����ű����ɲ����ã��ɱ���ǩ������ExtBarButtonTag��ǩ������д����
	 */
	private List<String> bbarScriptList = new ArrayList<String>();
	
	/**
	 * <code>pageingToolbarScript</code> Grid�й�����PagingToolbar����ű����ɲ����ã��ɱ���ǩ������ExtPagingToolbarTag��ǩ������д
	 */
	private String pageingToolbarScript = null;

	/**
	 * ���� entity.
	 * @return entity
	 */
	public Class getEntity() {
		return entity;
	}

	/**
	 * ���� varScriptList.
	 * @return varScriptList
	 */
	public List<String> getVarScriptList() {
		return varScriptList;
	}

	/**
	 * ���� cmScript ֵΪ <code>cmScript</code>.
	 * @param cmScript Ҫ���õ� cmScript ֵ
	 */
	public void setCmScript(String cmScript) {
		this.cmScript = cmScript;
	}

	/**
	 * ���� smScript ֵΪ <code>smScript</code>.
	 * @param smScript Ҫ���õ� smScript ֵ
	 */
	public void setSmScript(String smScript) {
		this.smScript = smScript;
	}

	/**
	 * @return the tbarScriptList
	 */
	public List<String> getTbarScriptList() {
		return tbarScriptList;
	}

	/**
	 * @return the bbarScriptList
	 */
	public List<String> getBbarScriptList() {
		return bbarScriptList;
	}

	/**
	 * ���� pageingToolbarScript ֵΪ <code>pageingToolbarScript</code>.
	 * @param pageingToolbarScript Ҫ���õ� pageingToolbarScript ֵ
	 */
	public void setPageingToolbarScript(String pageingToolbarScript) {
		this.pageingToolbarScript = pageingToolbarScript;
	}

	public void setDataScript(String dataScript) {
		store = dataScript;
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
		
		for (String s : varScriptList)
			buf.append(s).append("\r\n");
		
		buf.append("var " + name + " = new " + clazz + "({");
		
    	if (id != null)
    		buf.append("id: '" + id + "',");
    			
    	if (store != null)
    		buf.append("store: " + store + ",");
    			
    	if (title != null) {
    		String str;
        	try {
				str = TagUtils.getMessage(pageContext, bundle, title);
			} catch (Exception e) {
				str = title;
			}
			
       		buf.append("title: '" + str + "', ");
    	}
    	
    	if (autoExpandColumn != null && !"".equals(autoExpandColumn))
    		buf.append("autoExpandColumn: '" + autoExpandColumn + "',");
    			
    	if (cmScript != null)
    		buf.append("cm: " + cmScript + ",");
		
    	if (smScript != null)
    		buf.append("sm: " + smScript + ",");
		
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
    		
    		buf.append("],");
    	}
    	
    	//����Ѿ������˷�ҳ����������ײ㹤��������Ϊ��ҳ���������������Ƿ����õײ㹤������������
    	if (pageingToolbarScript != null)
    		buf.append("bbar: " + pageingToolbarScript + ",");
    	else if (bbarScriptList.size() > 0){
    		buf.append("bbar: [");
    		
    		boolean first = true;
    		for (String s : bbarScriptList) {
    			if (first)
    				first = false;
    			else
    				buf.append(", ");
    				
    			buf.append(s);
    		}
    		
    		buf.append("],");
    	}
		
    	if (propertys != null)
    		buf.append(propertys + ",");
    	
    	if (buf.charAt(buf.length() - 1) == ',')
    		buf.setLength(buf.length() - 1);
	
		buf.append("});\r\n");
		
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
		for (int i = varScriptList.size() - 1; i>= 0; i--)
			varScriptList.remove(varScriptList.get(i));
		
		for (int i = tbarScriptList.size() - 1; i>= 0; i--)
			tbarScriptList.remove(tbarScriptList.get(i));
		
		for (int i = bbarScriptList.size() - 1; i>= 0; i--)
			bbarScriptList.remove(bbarScriptList.get(i));

		entity = null;
		cmScript = null;
		smScript = null;
		pageingToolbarScript = null;
		
		super.release();
	}
}
