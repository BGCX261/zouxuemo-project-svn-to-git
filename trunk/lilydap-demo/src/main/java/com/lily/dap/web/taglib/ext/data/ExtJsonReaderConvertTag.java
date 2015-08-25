/*
 * LilyDAPƽ̨ϵͳԴ�ļ�.
 *
 * Copyright 2008 �ٺ�����������޹�˾
 */

package com.lily.dap.web.taglib.ext.data;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

/**
 * <code>ExtJsonReaderConvertTag</code>
 * <p>����Ext��Ext.grid.Json Reader��Convert js�����JSP Tag</p>
 *
 * @author ��ѧģ
 * @date 2008-3-20
 *
 * @jsp.tag name="extJsonReaderConvert"
 */
public class ExtJsonReaderConvertTag extends TagSupport {
	/**
	 * <code>serialVersionUID</code> ����ע��
	 */
	private static final long serialVersionUID = -7080064390033455015L;
	
	/**
	 * <code>fields</code> ��Ҫ����ת�����ֶΣ��м������","�ָ��������ֶ�
	 */
	private String fields = null;
	
	/**
	 * <code>func</code> ת������������������Ѿ�����õĺ����������ֶ�
	 */
	private String func = null;

	/**
	 * ���� fields ֵΪ <code>fields</code>.
	 * @param fields Ҫ���õ� fields ֵ
	 * 
     * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setFields(String fields) {
		this.fields = fields;
	}

	/**
	 * ���� func ֵΪ <code>func</code>.
	 * @param func Ҫ���õ� func ֵ
	 * 
     * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setFunc(String func) {
		this.func = func;
	}

	/* ���� Javadoc��
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		ExtJsonReaderInterface extJsonReaderInterface = (ExtJsonReaderInterface)getParent();
		if (extJsonReaderInterface == null)
			throw new JspException("�����ҵ�ʵ��extJsonReaderInterface�ӿڱ�ǩ����");
		
		Map<String, String> convertScriptMap = extJsonReaderInterface.getConvertScriptMap();
		
		String[] fs = StringUtils.split(fields, ',');
		for (String s : fs) {
			s = s.trim();
			
			if (convertScriptMap.containsKey(s))
				convertScriptMap.remove(s);
			
			convertScriptMap.put(s, func);
		}
		
		return SKIP_BODY;
	}
}
