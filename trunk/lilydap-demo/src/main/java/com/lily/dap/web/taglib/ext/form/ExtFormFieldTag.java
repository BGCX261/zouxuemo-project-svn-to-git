/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
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
 * <p>����Ext�ı��ֶ� js�����JSP Tag���ֶ�</p>
 *
 * @author ��ѧģ
 * @date 2008-4-17
 *
 * @jsp.tag name="extFormField"
 */
public class ExtFormFieldTag extends TagSupport {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = 2155996880178455720L;

	public static final String TEMPLATE_VALUE_STRING = "$$";
	
	/**
	 * <code>xtype</code> xtype���ԣ��������÷�ʽ���ɵĴ��룬Ҫ��ָ��һ��xtype������new���ɵĴ��룬���Ǳ����
	 */
	protected String xtype = null;
	
	/**
	 * <code>id</code> �ֶε�IDֵ�����δ����������fieldָ������Ĭ��ʹ��field������ΪIDֵ
	 */
	protected String id = null;
	
	/**
	 * <code>name</code> �ֶε�Nameֵ�����δ����������fieldָ������Ĭ��ʹ��field������ΪNameֵ
	 */
	protected String name = null;
	
	/**
	 * <code>bundle</code> ʹ�õ���Դ����
	 */
	protected String bundle = null;
	
	/**
	 * <code>header</code> ��ͷ���⣬��ѡ����������ã���ȡʵ������е�labelע��
	 */
	protected String label = null;
	
	/**
	 * <code>hideLabel</code> �Ƿ������ֶα�ǩ����ѡ����������ã�������
	 */
	protected String hideLabel = null;
	
	/**
	 * <code>labelTemplate</code> ��ͷ����ģ�棬��ѡ��������ñ���ģ�棬����Զ���ģ������
	 */
	protected String labelTemplate = null;
	
	/**
	 * <code>field</code> ������ʵ���������������ѡ�����������field���ԣ���ID,name,label���ܻ�Ӹ��ֶλ�ȡ��Ϣ
	 */
	protected String field = null;
	
	/**
	 * <code>clazz</code> ���������ƣ���ѡ��������ã����ʹ�ø�����newһ���¶��󣬲��ѵ�ǰ������Ϊ�������ݸ��ö���
	 */
	protected String clazz = null;
	
	/**
	 * <code>readOnly</code> �Ƿ�ֻ������
	 */
	protected String readOnly = null;
	
	/**
	 * <code>hidden</code> �Ƿ���������
	 */
	protected String hidden = null;
	
	/**
	 * <code>value</code> �ֶ�ֵ����
	 */
	protected String value = null;
	
	/**
	 * <code>tabIndex</code> tab��������
	 */
	protected String tabIndex = null;
	
	/**
	 * <code>anchor</code> anchor����
	 */
	protected String anchor = "98%";
	
	/**
	 * <code>propertys</code> �����������ã���ѡ����ǩ��Ѹ������Լ��뵽������ȥ��Ҫ���ʽΪ"xxx: 'xxx', xxx:xxx,..."��ʽ
	 */
	protected String propertys = null;

	/**
	 * ���� xtype.
	 * @return xtype
	 */
	public String getXtype() {
		return xtype;
	}

	/**
	 * ���� xtype ֵΪ <code>xtype</code>.
	 * @param xtype Ҫ���õ� xtype ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

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
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setName(String name) {
		this.name = name;
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
	 * ���� label.
	 * @return label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * ���� label ֵΪ <code>label</code>.
	 * @param label Ҫ���õ� label ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * ���� hideLabel.
	 * @return hideLabel
	 */
	public String getHideLabel() {
		return hideLabel;
	}

	/**
	 * ���� hideLabel ֵΪ <code>hideLabel</code>.
	 * @param hideLabel Ҫ���õ� hideLabel ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setHideLabel(String hideLabel) {
		this.hideLabel = hideLabel;
	}

	/**
	 * ���� labelTemplate.
	 * @return labelTemplate
	 */
	public String getLabelTemplate() {
		return labelTemplate;
	}

	/**
	 * ���� labelTemplate ֵΪ <code>labelTemplate</code>.
	 * @param labelTemplate Ҫ���õ� labelTemplate ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setLabelTemplate(String labelTemplate) {
		this.labelTemplate = labelTemplate;
	}

	/**
	 * ���� field.
	 * @return field
	 */
	public String getField() {
		return field;
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
	 * ���� readOnly.
	 * @return readOnly
	 */
	public String getReadOnly() {
		return readOnly;
	}

	/**
	 * ���� readOnly ֵΪ <code>readOnly</code>.
	 * @param readOnly Ҫ���õ� readOnly ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * ���� hidden.
	 * @return hidden
	 */
	public String getHidden() {
		return hidden;
	}

	/**
	 * ���� hidden ֵΪ <code>hidden</code>.
	 * @param hidden Ҫ���õ� hidden ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setHidden(String hidden) {
		this.hidden = hidden;
	}

	/**
	 * ���� value.
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * ���� value ֵΪ <code>value</code>.
	 * @param value Ҫ���õ� value ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * ���� tabIndex.
	 * @return tabIndex
	 */
	public String getTabIndex() {
		return tabIndex;
	}

	/**
	 * ���� tabIndex ֵΪ <code>tabIndex</code>.
	 * @param tabIndex Ҫ���õ� tabIndex ֵ
	 * 
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTabIndex(String tabIndex) {
		this.tabIndex = tabIndex;
	}

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
		ExtFormFieldsTag extFormFieldsTag = (ExtFormFieldsTag)findAncestorWithClass(this, ExtFormFieldsTag.class);
		if (extFormFieldsTag == null)
			throw new JspException("�����ҵ�extFormFields��ǩ����");
		
		Object parent = getParent();
		if (!(parent instanceof ExtItemsInterface))
			throw new JspException("�����ҵ�field������ǩ����");

		List<String> itemsScriptList = ((ExtItemsInterface)parent).getItemsScriptList();
		
		//����������޸����Է���
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

    	//�����������Ӷ������Խű�����
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
		
		//��ǩ����Ϊ�������ڣ���������һ�ε���ʱ������ID��Nameֵ����һ�ε��þͻ���Ȼ������ֵ������Ҫ���������ֵ
		id = null;
		name = null;
		bundle = null;
		
		//����������ͷŶ������Է���
		releaseProperty();
		
		return SKIP_BODY;
	}
}
