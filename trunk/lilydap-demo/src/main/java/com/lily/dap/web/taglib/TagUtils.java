package com.lily.dap.web.taglib;

import java.util.ResourceBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;

import org.apache.taglibs.standard.tag.common.fmt.BundleSupport;

public class TagUtils {
	/**
	 * ����bundle�ͼ�ֵָ�����Ϣ��Դ�����δ�ҵ����Ѽ�ֵ��Ϊ����ֵ
	 *
	 * @param pageContext
	 * @param bundle
	 * @param key
	 * @return
	 * @throws JspException
	 */
	public static String getMessage(PageContext pageContext, String bundle, String key) throws JspException {
		LocalizationContext locCtxt;
		if (bundle != null && !"".equals(bundle))
			locCtxt = BundleSupport.getLocalizationContext(pageContext, bundle);
		else
			locCtxt = BundleSupport.getLocalizationContext(pageContext);
		
		if (locCtxt == null)
			return key;
			
		ResourceBundle resourceBundle = locCtxt.getResourceBundle();
		String message = resourceBundle.getString(key);
		if (message == null)
			message = key;
		
		return message;
	}
}
