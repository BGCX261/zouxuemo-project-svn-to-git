/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext.grid;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.lily.dap.util.BaseEntityHelper;
import com.lily.dap.web.taglib.TagUtils;

/**
 * <code>ExtColumnTag</code>
 * <p>生成Ext的Ext.grid.ColumnModel 的Column js代码的JSP Tag</p>
 *
 * @author 邹学模
 * @date 2008-3-20
 *
 * @jsp.tag name="extColumn"
 */
public class ExtColumnTag extends TagSupport {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = 8659418009595302033L;

	/**
	 * <code>id</code> 列的ID值，可选
	 */
	protected String id = null;
	
	/**
	 * <code>name</code> 列对象的名称，可选，如果设置，则生成一个列对象变量，并且要求设置clazz属性
	 */
	protected String name = null;
	
	/**
	 * <code>bundle</code> 使用的资源类名
	 */
	private String bundle = null;
	
	/**
	 * <code>header</code> 列头标题，可选，如果不设置，则取实体对象中的label注解
	 */
	protected String header = null;
	
	/**
	 * <code>field</code> 关联的实体对象属性名，可选，如果field属性不设置，则clazz属性必需设置
	 */
	protected String field = null;
	
	/**
	 * <code>clazz</code> 生成类名称，可选，如果设置，则会使用该类名new一个新对象，并把当前属性作为参数传递给该对象
	 */
	protected String clazz = null;
	
	/**
	 * <code>width</code> 列宽度
	 */
	protected String width = null;
	
	/**
	 * <code>renderer</code> 渲染函数
	 */
	protected String renderer = null;
	
	/**
	 * <code>propertys</code> 附加属性设置，可选，标签会把附加属性加入到属性中去。要求格式为"xxx: 'xxx', xxx:xxx,..."形式
	 */
	protected String propertys = null;
	
	/**
	 * <code>editor</code> 编辑属性设置，可选，如果设置编辑属性，会把本属集性作为参数传递给editor属性。要求格式为"xxx: 'xxx', xxx:xxx,..."形式
	 */
	protected String editor = null;

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
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 设置 field 值为 <code>field</code>.
	 * @param field 要设置的 field 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setField(String field) {
		this.field = field;
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
	 * 设置 header 值为 <code>header</code>.
	 * @param header 要设置的 header 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setHeader(String header) {
		this.header = header;
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
	 * 设置 width 值为 <code>width</code>.
	 * @param width 要设置的 width 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * 设置 renderer 值为 <code>renderer</code>.
	 * @param renderer 要设置的 renderer 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setRenderer(String renderer) {
		this.renderer = renderer;
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

	/**
	 * 设置 editor 值为 <code>editor</code>.
	 * @param editor 要设置的 editor 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setEditor(String editor) {
		this.editor = editor;
	}
	
	// -------------------------------- 以下为供子类实现 ----------------------------------
	/**
	 * 由继承的子类负责修改现有的属性默认值
	 *
	 */
	protected void modifyProperty() {
		return;
	}
	
	/**
	 * 由继承的子类负责添加额外的属性脚本代码
	 *
	 * @param buf
	 * @throws JspException
	 */
	protected void addPropertyScript(StringBuffer buf) throws JspException {
		return;
	}
	
	/**
	 * 由继承的子类负责释放额外的属性
	 *
	 */
	protected void releaseProperty() {
		return;
	}
	
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		ExtGridPanelTag extGridPanelTag = (ExtGridPanelTag)findAncestorWithClass(this, ExtGridPanelTag.class);
		if (extGridPanelTag == null && name != null)
			throw new JspException("不能找到extGridPanel标签定义");
		
		ExtColumnModelTag extColumnModelTag = (ExtColumnModelTag)getParent();
		if (extColumnModelTag == null)
			throw new JspException("不能找到extColumnModel标签定义");

		//可能在别处已经定义了name指定的对象
//		if (name != null && clazz == null)
//			throw new JspException("定义了name属性，但是没有定义clazz属性");
		
//		if (field == null && clazz == null)
//			throw new JspException("field属性和clazz属性必需至少定义一个");

		if (name != null && clazz == null) {
			extColumnModelTag.getColumnScriptList().add(name);
			return SKIP_BODY;
		}
		
		//调用子类的修改属性方法
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

    	//调用子类的添加额外属性脚本方法
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
		
		//调用子类的释放额外属性方法
		releaseProperty();
		
		return SKIP_BODY;
	}
}
