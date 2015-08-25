/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
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
 * <p>生成Ext的FormPanel js代码的JSP Tag表单字段</p>
 *
 * @author 邹学模
 * @date 2008-4-17
 *
 * @jsp.tag name="extFormPanel"
 */
@SuppressWarnings("unchecked")
public class ExtFormPanelTag extends BodyTagSupport implements ExtPanelInterface, ExtDataTargetInterface {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = 6186861768453286440L;

	public static String DEFAULT_FORM_PANEL_PANEL = "Ext.FormPanel";
	
	/**
	 * <code>id</code> FORM的ID值，可选
	 */
	private String id = null;
	
	/**
	 * <code>name</code> FORM对象的名称，必需
	 */
	private String name = null;
	
	/**
	 * <code>clazz</code> 生成类名称，可选，如果不设置，则默认使用DEFAULT_FORM_PANEL_PANEL来生成
	 */
	private String clazz = DEFAULT_FORM_PANEL_PANEL;
	
	/**
	 * <code>bundle</code> 使用的资源类名
	 */
	private String bundle = null;
	
	/**
	 * <code>title</code> 窗口标题
	 */
	private String title = null;
	
	/**
	 * <code>model</code> Reader数据结构对应的实体对象全路径名，类似于"com.lily.dap.model.right.Role"，必需字段
	 */
	private String model = null;

	/**
	 * <code>url</code> FORM装载的URL路径
	 */
	private String url = null;
	
	/**
	 * <code>method</code> FORM提交方式，默认为"POST"
	 */
	protected String method = "POST";
	
	/**
	 * <code>frame</code> 是否包含框架
	 */
	protected String frame = "true";
	
	/**
	 * <code>labelAlign</code> 字段标签对齐方式，默认为"right"
	 */
	protected String labelAlign = "right";
	
	/**
	 * <code>labelWidth</code> 字段标签宽度，默认为70
	 */
	protected String labelWidth = "70";
	
	/**
	 * <code>reader</code> reader属性，要求设置
	 */
	protected String reader = null;
	
	/**
	 * <code>propertys</code> 附加属性设置，可选，标签会把附加属性加入到属性中去。要求格式为"xxx: 'xxx', xxx:xxx,..."形式
	 */
	private String propertys = null;
	
	/**
	 * 返回 id.
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置 id 值为 <code>id</code>.
	 * @param id 要设置的 id 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 返回 name.
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置 name 值为 <code>name</code>.
	 * @param name 要设置的 name 值
	 * 
	 * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 clazz.
	 * @return clazz
	 */
	public String getClazz() {
		return clazz;
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
	 * 返回 bundle.
	 * @return bundle
	 */
	public String getBundle() {
		return bundle;
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
	 * 返回 title.
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置 title 值为 <code>title</code>.
	 * @param title 要设置的 title 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 返回 model.
	 * @return model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * 设置 model 值为 <code>model</code>.
	 * @param model 要设置的 model 值
	 * 
	 * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * 返回 url.
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置 url 值为 <code>url</code>.
	 * @param url 要设置的 url 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 返回 method.
	 * @return method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * 设置 method 值为 <code>method</code>.
	 * @param method 要设置的 method 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 返回 frame.
	 * @return frame
	 */
	public String getFrame() {
		return frame;
	}

	/**
	 * 设置 frame 值为 <code>frame</code>.
	 * @param frame 要设置的 frame 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setFrame(String frame) {
		this.frame = frame;
	}

	/**
	 * 返回 reader.
	 * @return reader
	 */
	public String getReader() {
		return reader;
	}

	/**
	 * 设置 reader 值为 <code>reader</code>.
	 * @param reader 要设置的 reader 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setReader(String reader) {
		this.reader = reader;
	}

	/**
	 * 返回 labelAlign.
	 * @return labelAlign
	 */
	public String getLabelAlign() {
		return labelAlign;
	}

	/**
	 * 设置 labelAlign 值为 <code>labelAlign</code>.
	 * @param labelAlign 要设置的 labelAlign 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setLabelAlign(String labelAlign) {
		this.labelAlign = labelAlign;
	}

	/**
	 * 返回 labelWidth.
	 * @return labelWidth
	 */
	public String getLabelWidth() {
		return labelWidth;
	}

	/**
	 * 设置 labelWidth 值为 <code>labelWidth</code>.
	 * @param labelWidth 要设置的 labelWidth 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setLabelWidth(String labelWidth) {
		this.labelWidth = labelWidth;
	}

	/**
	 * 返回 propertys.
	 * @return propertys
	 */
	public String getPropertys() {
		return propertys;
	}

	/**
	 * 设置 propertys 值为 <code>propertys</code>.
	 * @param propertys 要设置的 propertys 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setPropertys(String propertys) {
		this.propertys = propertys;
	}
	
	// -------------------------------- 以下为私有变量 ----------------------------------
	/**
	 * <code>clazz</code> FormPanel操作的实体对象类
	 */
	private Class entity = null;
	
	/**
	 * <code>tbarScriptList</code> FormPanel中关联的tbar对象脚本，可不设置，由本标签包含的ExtBarButtonTag标签负责填写插入
	 */
	private List<String> tbarScriptList = new ArrayList<String>();
	
	/**
	 * <code>bbarScriptList</code> FormPanel中关联的bbar对象脚本，可不设置，由本标签包含的ExtBarButtonTag标签负责填写插入
	 */
	private List<String> bbarScriptList = new ArrayList<String>();

	/**
	 * <code>itemsScript</code> items脚本字符串
	 */
	private String itemsScript = null;

	/**
	 * 返回 entity.
	 * @return entity
	 */
	public Class getEntity() {
		return entity;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.webapp.taglib.ext.ExtPanelInterface#getBbarScriptList()
	 */
	public List<String> getBbarScriptList() {
		return bbarScriptList;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.webapp.taglib.ext.ExtPanelInterface#getTbarScriptList()
	 */
	public List<String> getTbarScriptList() {
		return tbarScriptList;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.webapp.taglib.ext.data.ExtDataTargetInterface#setDataScript(java.lang.String)
	 */
	public void setDataScript(String dataScript) {
		reader = dataScript;
	}
	
	/**
	 * 设置 itemsScript 值为 <code>itemsScript</code>.
	 * @param itemsScript 要设置的 itemsScript 值
	 */
	public void setItemsScript(String itemsScript) {
		this.itemsScript = itemsScript;
	}

	/* （非 Javadoc）
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

	/* （非 Javadoc）
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
		
    	//检查是否设置顶层工具栏，并设置
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
    	
    	//检查是否设置底层工具栏，并设置
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
