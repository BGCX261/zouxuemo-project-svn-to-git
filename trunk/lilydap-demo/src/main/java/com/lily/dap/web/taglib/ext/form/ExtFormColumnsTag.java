/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.lily.dap.web.taglib.ext.ExtItemsInterface;

/**
 * <code>ExtFormColumnsTag</code>
 * <p>生成Ext的Form的column布局 js代码的JSP Tag表单字段</p>
 *
 * @author 邹学模
 * @date 2008-4-17
 *
 * @jsp.tag name="extFormColumns"
 */
public class ExtFormColumnsTag extends BodyTagSupport implements ExtItemsInterface {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = 584406962131680016L;
	
	/**
	 * <code>layout</code> column的布局属性，不能修改
	 */
	protected String layout = "column";
	
	/**
	 * <code>anchor</code> anchor属性
	 */
	protected String anchor = "100%";
	
	/**
	 * <code>propertys</code> 附加属性设置，可选，标签会把附加属性加入到属性中去。要求格式为"xxx: 'xxx', xxx:xxx,..."形式
	 */
	protected String propertys = null;

	/**
	 * 返回 anchor.
	 * @return anchor
	 */
	public String getAnchor() {
		return anchor;
	}

	/**
	 * 设置 anchor 值为 <code>anchor</code>.
	 * @param anchor 要设置的 anchor 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setAnchor(String anchor) {
		this.anchor = anchor;
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
	 * <code>itemsScriptList</code> 需要生成的items脚本列表，由本标签包含的子标签负责填写
	 */
	private List<String> itemsScriptList = new ArrayList<String>();
	
	public List<String> getItemsScriptList() {
		return itemsScriptList;
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
	
	@Override
	public int doStartTag() throws JspException {
		Object parent = getParent();
		if (!(parent instanceof ExtItemsInterface))
			throw new JspException("不能找到field容器标签定义");
		
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException {
		ExtItemsInterface parent = (ExtItemsInterface)getParent();
		List<String> parentItemsScriptList = parent.getItemsScriptList();
		
		//调用子类的修改属性方法
		modifyProperty();
		
		StringBuffer buf = new StringBuffer();
		buf.append("{");
		
		if (layout != null)
			buf.append("layout: '").append(layout).append("', ");
		
		if (anchor != null)
			buf.append("anchor: '").append(anchor).append("', ");

    	//调用子类的添加额外属性脚本方法
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
		//调用子类的释放额外属性方法
		releaseProperty();
		
		for (int i = itemsScriptList.size() - 1; i>= 0; i--)
			itemsScriptList.remove(itemsScriptList.get(i));
	}
}
