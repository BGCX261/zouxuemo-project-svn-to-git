/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext.form;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.lily.dap.util.BaseEntityHelper;
import com.lily.dap.web.taglib.TagUtils;
import com.lily.dap.web.taglib.ext.ExtItemsInterface;

/**
 * <code>ExtFormFieldTag</code>
 * <p>生成Ext的表单字段 js代码的JSP Tag表单字段</p>
 *
 * @author 邹学模
 * @date 2008-4-17
 *
 * @jsp.tag name="extFormField"
 */
public class ExtFormFieldTag extends TagSupport {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = 2155996880178455720L;

	public static final String TEMPLATE_VALUE_STRING = "$$";
	
	/**
	 * <code>xtype</code> xtype属性，对于配置方式生成的代码，要求指定一个xtype，对于new生成的代码，不是必需的
	 */
	protected String xtype = null;
	
	/**
	 * <code>id</code> 字段的ID值，如果未给定，并且field指定，则默认使用field名称作为ID值
	 */
	protected String id = null;
	
	/**
	 * <code>name</code> 字段的Name值，如果未给定，并且field指定，则默认使用field名称作为Name值
	 */
	protected String name = null;
	
	/**
	 * <code>bundle</code> 使用的资源类名
	 */
	protected String bundle = null;
	
	/**
	 * <code>header</code> 列头标题，可选，如果不设置，则取实体对象中的label注解
	 */
	protected String label = null;
	
	/**
	 * <code>hideLabel</code> 是否隐藏字段标签，可选，如果不设置，则不隐藏
	 */
	protected String hideLabel = null;
	
	/**
	 * <code>labelTemplate</code> 列头标题模版，可选，如果设置标题模版，则可自定义模版内容
	 */
	protected String labelTemplate = null;
	
	/**
	 * <code>field</code> 关联的实体对象属性名，可选，如果设置了field属性，则ID,name,label可能会从该字段获取信息
	 */
	protected String field = null;
	
	/**
	 * <code>clazz</code> 生成类名称，可选，如果设置，则会使用该类名new一个新对象，并把当前属性作为参数传递给该对象
	 */
	protected String clazz = null;
	
	/**
	 * <code>readOnly</code> 是否只读属性
	 */
	protected String readOnly = null;
	
	/**
	 * <code>hidden</code> 是否隐藏属性
	 */
	protected String hidden = null;
	
	/**
	 * <code>value</code> 字段值属性
	 */
	protected String value = null;
	
	/**
	 * <code>tabIndex</code> tab索引属性
	 */
	protected String tabIndex = null;
	
	/**
	 * <code>anchor</code> anchor属性
	 */
	protected String anchor = "98%";
	
	/**
	 * <code>propertys</code> 附加属性设置，可选，标签会把附加属性加入到属性中去。要求格式为"xxx: 'xxx', xxx:xxx,..."形式
	 */
	protected String propertys = null;

	/**
	 * 返回 xtype.
	 * @return xtype
	 */
	public String getXtype() {
		return xtype;
	}

	/**
	 * 设置 xtype 值为 <code>xtype</code>.
	 * @param xtype 要设置的 xtype 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

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
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setName(String name) {
		this.name = name;
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
	 * 返回 label.
	 * @return label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 设置 label 值为 <code>label</code>.
	 * @param label 要设置的 label 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 返回 hideLabel.
	 * @return hideLabel
	 */
	public String getHideLabel() {
		return hideLabel;
	}

	/**
	 * 设置 hideLabel 值为 <code>hideLabel</code>.
	 * @param hideLabel 要设置的 hideLabel 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setHideLabel(String hideLabel) {
		this.hideLabel = hideLabel;
	}

	/**
	 * 返回 labelTemplate.
	 * @return labelTemplate
	 */
	public String getLabelTemplate() {
		return labelTemplate;
	}

	/**
	 * 设置 labelTemplate 值为 <code>labelTemplate</code>.
	 * @param labelTemplate 要设置的 labelTemplate 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setLabelTemplate(String labelTemplate) {
		this.labelTemplate = labelTemplate;
	}

	/**
	 * 返回 field.
	 * @return field
	 */
	public String getField() {
		return field;
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
	 * 返回 readOnly.
	 * @return readOnly
	 */
	public String getReadOnly() {
		return readOnly;
	}

	/**
	 * 设置 readOnly 值为 <code>readOnly</code>.
	 * @param readOnly 要设置的 readOnly 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * 返回 hidden.
	 * @return hidden
	 */
	public String getHidden() {
		return hidden;
	}

	/**
	 * 设置 hidden 值为 <code>hidden</code>.
	 * @param hidden 要设置的 hidden 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	/**
	 * 返回 value.
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 设置 value 值为 <code>value</code>.
	 * @param value 要设置的 value 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 返回 tabIndex.
	 * @return tabIndex
	 */
	public String getTabIndex() {
		return tabIndex;
	}

	/**
	 * 设置 tabIndex 值为 <code>tabIndex</code>.
	 * @param tabIndex 要设置的 tabIndex 值
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTabIndex(String tabIndex) {
		this.tabIndex = tabIndex;
	}

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
		ExtFormFieldsTag extFormFieldsTag = (ExtFormFieldsTag)findAncestorWithClass(this, ExtFormFieldsTag.class);
		if (extFormFieldsTag == null)
			throw new JspException("不能找到extFormFields标签定义");
		
		Object parent = getParent();
		if (!(parent instanceof ExtItemsInterface))
			throw new JspException("不能找到field容器标签定义");

		List<String> itemsScriptList = ((ExtItemsInterface)parent).getItemsScriptList();
		
		//调用子类的修改属性方法
		modifyProperty();
		
		StringBuffer buf = new StringBuffer();
		
		if (clazz != null)
			buf.append("new ").append(clazz).append("(");
		
		buf.append("{");
		
		if (xtype != null)
			buf.append("xtype: '").append(xtype).append("', ");
		
    	if (field != null) {
    		if (name == null)
    			name = field;
    		
    		if (id == null)
    			id = field;
    	}

    	if (id != null)
    		buf.append("id: '").append(id).append("', ");
    	
    	if (name != null)
    		buf.append("name: '").append(name).append("', ");
    	
    	if ("true".equals(this.hideLabel)) {
    		buf.append("hideLabel: ").append(hideLabel).append(", ");
    	} else {
	    	String fieldLabel = null;
			if (label != null) {
	    		if (bundle == null)
	    			bundle = extFormFieldsTag.getBundle();
	    		
	    		fieldLabel = TagUtils.getMessage(pageContext, bundle, label);
			} else if (field != null) {
				try {
					fieldLabel = BaseEntityHelper.getEntityFieldLabel(extFormFieldsTag.getEntity(), field, true);
				} catch (Exception e) {}
			}
			
	    	if (fieldLabel != null) {
	    		if (labelTemplate != null)
	    			fieldLabel = StringUtils.replace(labelTemplate, TEMPLATE_VALUE_STRING, fieldLabel);
	    		
	    		buf.append("fieldLabel: '" + fieldLabel + "', ");
	    	}
    	}
    	
    	if (readOnly != null)
    		buf.append("readOnly: ").append(readOnly).append(", ");
    	
    	if (hidden != null)
    		buf.append("hidden: ").append(hidden).append(", ");
    	
    	if (value != null)
    		buf.append("value: '").append(value).append("', ");
    	
    	if (tabIndex != null)
    		buf.append("tabIndex: ").append(tabIndex).append(", ");
    	else
    		buf.append("tabIndex: ").append(extFormFieldsTag.generateNewTabIndex()).append(", ");
    	
    	if (anchor != null)
    		buf.append("anchor: '").append(anchor).append("', ");

    	//调用子类的添加额外属性脚本方法
    	addPropertyScript(buf);
		
    	if (propertys != null)
    		buf.append(propertys + ", ");
		
    	if (buf.charAt(buf.length() - 1) != '{') {
    		buf.setLength(buf.length() - 2);
    		buf.append("}");
    	} else {
    		buf.setLength(buf.length() - 1);
    	}
    	
		if (clazz != null)
			buf.append(")");
		
		String itemScript = buf.toString();
		itemsScriptList.add(itemScript);
		
		//标签会作为单件存在，所以在这一次调用时设置了ID和Name值而下一次调用就会依然保留该值，所以要清除掉设置值
		id = null;
		name = null;
		bundle = null;
		
		//调用子类的释放额外属性方法
		releaseProperty();
		
		return SKIP_BODY;
	}
}
