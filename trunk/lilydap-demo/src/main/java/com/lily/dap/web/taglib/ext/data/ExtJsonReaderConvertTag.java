/*
 * LilyDAP平台系统源文件.
 *
 * Copyright 2008 百合软件开发有限公司
 */

package com.lily.dap.web.taglib.ext.data;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

/**
 * <code>ExtJsonReaderConvertTag</code>
 * <p>生成Ext的Ext.grid.Json Reader的Convert js代码的JSP Tag</p>
 *
 * @author 邹学模
 * @date 2008-3-20
 *
 * @jsp.tag name="extJsonReaderConvert"
 */
public class ExtJsonReaderConvertTag extends TagSupport {
	/**
	 * <code>serialVersionUID</code> 属性注释
	 */
	private static final long serialVersionUID = -7080064390033455015L;
	
	/**
	 * <code>fields</code> 需要进行转换的字段，中间可以用","分隔，必需字段
	 */
	private String fields = null;
	
	/**
	 * <code>func</code> 转换函数，这个函数是已经定义好的函数，必需字段
	 */
	private String func = null;

	/**
	 * 设置 fields 值为 <code>fields</code>.
	 * @param fields 要设置的 fields 值
	 * 
     * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setFields(String fields) {
		this.fields = fields;
	}

	/**
	 * 设置 func 值为 <code>func</code>.
	 * @param func 要设置的 func 值
	 * 
     * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setFunc(String func) {
		this.func = func;
	}

	/* （非 Javadoc）
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		ExtJsonReaderInterface extJsonReaderInterface = (ExtJsonReaderInterface)getParent();
		if (extJsonReaderInterface == null)
			throw new JspException("不能找到实现extJsonReaderInterface接口标签定义");
		
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
