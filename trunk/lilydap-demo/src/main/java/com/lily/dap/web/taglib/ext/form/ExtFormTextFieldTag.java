/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.web.taglib.ext.form;

/**
 * <code>ExtFormTextFieldTag</code>
 * <p>�����ı��ֶνű���JSP��ǩ</p>
 *
 * @author ��ѧģ
 * @date 2008-4-18
 *
 * @jsp.tag name="extFormTextField"
 */
public class ExtFormTextFieldTag extends ExtFormFieldTag {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = -7117509309397732473L;

	/* ���� Javadoc��
	 * @see com.lily.dap.webapp.taglib.ext.form.ExtFormFieldTag#modifyProperty()
	 */
	@Override
	protected void modifyProperty() {
		xtype = "textfield";
	}
}
