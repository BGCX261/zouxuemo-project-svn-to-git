/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
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
 * <p>生成Ext的Ext.grid.GridPanel js代码的JSP Tag</p>
 *
 * @author 邹学模
 * @date 2008-3-20
 *
 * @jsp.tag name="extGridPanel"
 */
@SuppressWarnings("unchecked")
public class ExtGridPanelTag extends BodyTagSupport implements ExtPanelInterface, ExtDataTargetInterface {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = 4818408163449394773L;

	public static String DEFAULT_GRID_PANEL = "Ext.grid.GridPanel";
	
	/**
	 * <code>id</code> 列的ID值，可选
	 */
	private String id = null;
	
	/**
	 * <code>name</code> 列对象的名称，必需
	 */
	private String name = null;
	
	/**
	 * <code>store</code> 关联Store的变量名，如果本字段未设置，则要求本标签必须包含一个extJsonStore标签
	 */
	private String store;
	
	/**
	 * <code>clazz</code> 生成类名称，可选，如果不设置，则默认使用DEFAULT_GRID_PANEL来生成
	 */
	private String clazz = DEFAULT_GRID_PANEL;
	
	/**
	 * <code>bundle</code> 使用的资源类名
	 */
	private String bundle = null;
	
	/**
	 * <code>title</code> Grid标题
	 */
	private String title = null;
	
	/**
	 * <code>model</code> Store数据结构对应的实体对象全路径名，类似于"com.lily.dap.model.right.Role"，必需字段
	 */
	private String model = null;
	
	/**
	 * <code>autoExpandColumn</code> 自动扩展列属性，可选
	 */
	private String autoExpandColumn = null;
	
	/**
	 * <code>propertys</code> 附加属性设置，可选，标签会把附加属性加入到属性中去。要求格式为"xxx: 'xxx', xxx:xxx,..."形式
	 */
	private String propertys = null;

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
	 * 设置 name 值为 <code>name</code>.
	 * @param name 要设置的 name 值
	 * 
     * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setName(String name) {
		this.name = name;
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
	 * 设置 model 值为 <code>model</code>.
	 * @param model 要设置的 model 值
	 * 
     * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setModel(String model) {
		this.model = model;
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
	 * 返回 bundle.
	 * @return the bundle
	 */
	public String getBundle() {
		return bundle;
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
	 * 设置 store 值为 <code>store</code>.
	 * @param store 要设置的 store 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setStore(String store) {
		this.store = store;
	}

	/**
	 * 返回 store.
	 * @return store
	 */
	public String getStore() {
		return store;
	}

	/**
	 * 设置 autoExpandColumn 值为 <code>autoExpandColumn</code>.
	 * @param autoExpandColumn 要设置的 autoExpandColumn 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setAutoExpandColumn(String autoExpandColumn) {
		this.autoExpandColumn = autoExpandColumn;
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
	 * <code>clazz</code> Grid操作的实体对象类
	 */
	private Class entity = null;
	
	/**
	 * <code>varScriptList</code> Grid中需要生成的变量脚本列表，由本标签包含的ExtColumnModelTag标签和ExtColumnTag标签负责填写
	 */
	private List<String> varScriptList = new ArrayList<String>();

	/**
	 * <code>cmScript</code> Grid中关联的ColumnModel对象脚本，由本标签包含的ExtColumnModelTag标签负责填写
	 */
	private String cmScript = null;
	
	/**
	 * <code>smScript</code> Grid中关联的SelectionModel对象脚本，可不设置，由本标签包含的ExtSelectionModelTag标签负责填写
	 */
	private String smScript = null;
	
	/**
	 * <code>tbarScriptList</code> Grid中关联的tbar对象脚本，可不设置，由本标签包含的ExtBarButtonTag标签负责填写插入
	 */
	private List<String> tbarScriptList = new ArrayList<String>();
	
	/**
	 * <code>bbarScriptList</code> Grid中关联的bbar对象脚本，可不设置，由本标签包含的ExtBarButtonTag标签负责填写插入
	 */
	private List<String> bbarScriptList = new ArrayList<String>();
	
	/**
	 * <code>pageingToolbarScript</code> Grid中关联的PagingToolbar对象脚本，可不设置，由本标签包含的ExtPagingToolbarTag标签负责填写
	 */
	private String pageingToolbarScript = null;

	/**
	 * 返回 entity.
	 * @return entity
	 */
	public Class getEntity() {
		return entity;
	}

	/**
	 * 返回 varScriptList.
	 * @return varScriptList
	 */
	public List<String> getVarScriptList() {
		return varScriptList;
	}

	/**
	 * 设置 cmScript 值为 <code>cmScript</code>.
	 * @param cmScript 要设置的 cmScript 值
	 */
	public void setCmScript(String cmScript) {
		this.cmScript = cmScript;
	}

	/**
	 * 设置 smScript 值为 <code>smScript</code>.
	 * @param smScript 要设置的 smScript 值
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
	 * 设置 pageingToolbarScript 值为 <code>pageingToolbarScript</code>.
	 * @param pageingToolbarScript 要设置的 pageingToolbarScript 值
	 */
	public void setPageingToolbarScript(String pageingToolbarScript) {
		this.pageingToolbarScript = pageingToolbarScript;
	}

	public void setDataScript(String dataScript) {
		store = dataScript;
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
    		
    		buf.append("],");
    	}
    	
    	//如果已经设置了翻页工具条，则底层工具栏设置为翻页工具栏，否则检查是否设置底层工具栏，并设置
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
