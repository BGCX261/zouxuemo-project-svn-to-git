/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.web.taglib.ext.grid;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

/**
 * <code>ExtPageingToolbarTag</code>
 * <p>����Ext��Ext.grid.PageingToolbar js�����JSP Tag</p>
 *
 * @author ��ѧģ
 * @date 2008-3-20
 *
 * @jsp.tag name="extPageingToolbar"
 */
public class ExtPageingToolbarTag extends TagSupport {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = 4561631361399576059L;

	public static String DEFAULT_PAGING_TOORBAR = "Ext.PagingToolbar";
	
	public static String DEFAULT_PAGE_SIZE = "18";
	
	/**
	 * <code>name</code> ��������ƣ���ѡ��������ã�������һ���������
	 */
	private String name = null;
	
	/**
	 * <code>clazz</code> ���������ƣ���ѡ����������ã���Ĭ��ʹ��DEFAULT_PAGING_TOORBAR������
	 */
	private String clazz = DEFAULT_PAGING_TOORBAR;
	
	/**
	 * <code>pageSize</code> ��ҳ��ÿҳ��¼��������ѡ����������ã���Ĭ��ʹ��DEFAULT_PAGE_SIZE
	 */
	private Object pageSize = DEFAULT_PAGE_SIZE;
	
	/**
	 * <code>displayInfo</code> �Ƿ���ʾ��Ϣ
	 */
	private String displayInfo = "true";
	
	/**
	 * <code>displayMsg</code> ��ʾ��Ϣ��ʽ
	 */
	private String displayMsg = "��ʾ�� {0} ���� {1} ����¼���� {2}  ��";
	
	/**
	 * <code>emptyMsg</code> ������ʱ����ʾ��Ϣ
	 */
	private String emptyMsg = "δ�ҵ���¼����";
	
	/**
	 * <code>propertys</code> �����������ã���ѡ����ǩ��Ѹ������Լ��뵽������ȥ��Ҫ���ʽΪ"xxx: 'xxx', xxx:xxx,..."��ʽ
	 */
	private String propertys = null;

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
	 * ���� clazz ֵΪ <code>clazz</code>.
	 * @param clazz Ҫ���õ� clazz ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	/**
	 * ���� pageSize ֵΪ <code>pageSize</code>.
	 * @param pageSize Ҫ���õ� pageSize ֵ
	 * @throws JspException 
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setPageSize(Object pageSize) throws JspException {
		this.pageSize = ExpressionEvaluatorManager.evaluate("pageSize", pageSize.toString(), Object.class, this, pageContext);
	}

	/**
	 * ���� displayInfo ֵΪ <code>displayInfo</code>.
	 * @param displayInfo Ҫ���õ� displayInfo ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setDisplayInfo(String displayInfo) {
		this.displayInfo = displayInfo;
	}

	/**
	 * ���� displayMsg ֵΪ <code>displayMsg</code>.
	 * @param displayMsg Ҫ���õ� displayMsg ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setDisplayMsg(String displayMsg) {
		this.displayMsg = displayMsg;
	}

	/**
	 * ���� emptyMsg ֵΪ <code>emptyMsg</code>.
	 * @param emptyMsg Ҫ���õ� emptyMsg ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setEmptyMsg(String emptyMsg) {
		this.emptyMsg = emptyMsg;
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

	/* ���� Javadoc��
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		ExtGridPanelTag extGridPanelTag = (ExtGridPanelTag)getParent();
		if (extGridPanelTag == null)
			throw new JspException("�����ҵ�extGridPanel��ǩ����");
		
		StringBuffer buf = new StringBuffer();
		
		buf.append("new ").append(clazz).append("({");
		buf.append("pageSize: ").append(pageSize).append(", ");
		
		if (extGridPanelTag.getStore() != null)
			buf.append("store: ").append(extGridPanelTag.getStore()).append(", ");
		
		buf.append("displayInfo: ").append(displayInfo).append(", ");
		buf.append("displayMsg: '").append(displayMsg).append("', ");
		buf.append("emptyMsg: '").append(emptyMsg).append("'");
		
		if (propertys != null)
			buf.append(", ").append(propertys);
		
		buf.append("});");
		
		if (name != null) {
			buf.insert(0, "var " + name + " = ").append(";");
			extGridPanelTag.getVarScriptList().add(buf.toString());
			extGridPanelTag.setPageingToolbarScript(name);
		} else {
			extGridPanelTag.setPageingToolbarScript(buf.toString());
		}
		
		return EVAL_BODY_INCLUDE;
	}
}
