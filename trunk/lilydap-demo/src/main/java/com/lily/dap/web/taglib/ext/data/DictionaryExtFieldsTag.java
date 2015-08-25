package com.lily.dap.web.taglib.ext.data;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.dictionary.DictCatalog;
import com.lily.dap.entity.dictionary.Dictionary;
import com.lily.dap.service.SpringContextHolder;
import com.lily.dap.service.common.DictionaryManager;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.web.taglib.TagUtils;

/**
 * 数据字典数据源字段标签
 * 用法1：带name属性
 * <lilydap:dictionaryExtFields name="enabledFields" catalog="yes_no" selectText="-是否激活-"/>
 * 执行后将生成类似于如下代码：
 * 	var enabledFields = [['', '-是否激活-'],['true', '是'],['false', '否']];
 * 
 * 用法2：不带name属性
 * <lilydap:dictionaryExtFields catalog="yes_no" selectText="-是否激活-"/>
 * 执行后将生成类似于如下代码：
 * [['', '-是否激活-'],['true', '是'],['false', '否']];
 * 
 * @author 邹学模
 *
 * @jsp.tag name="dictionaryExtFields"
 */
public class DictionaryExtFieldsTag extends TagSupport {
	private static final long serialVersionUID = -6134750115202726684L;

	/**
	 * 生成field的变量名称，可选；如果不设置，则无
	 */
	private String name = null;
	
	/**
	 * 要检索的字典目录，必须输入
	 */
	private Object catalog = null;
	
	/**
	 * 是否只检索showFlag为true的字典，可选，默认为true
	 */
	private String onlyShow = "true";
	
	/**
	 * <code>dictCodeHead</code> 字典Code限定开头字符串，可选
	 */
	private String dictCodeHead = null;
	
	/**
	 * <code>dictCodeMinLength</code> 字典Code限定最小字符串长度，可选
	 */
	private String dictCodeMinLength = null;
	
	/**
	 * <code>dictCodeMaxLength</code> 字典Code限定最大字符串长度，可选
	 */
	private String dictCodeMaxLength = null;
	
	/**
	 * <code>filter</code> 字典过滤字符串，过滤字段之间用","分隔
	 */
	private String filter = null;
	
	/**
	 * <code>bundle</code> 使用的资源类名
	 */
	private String bundle = null;

	/**
	 * 在数据最前面中增加一个数据项，为选择所有或者不选择的文本字符串。可选，默认为"-所有-"；
	 */
	private String selectText = "";
	
	/**
	 * <code>emptyText</code> 在数据的最后面增加一个数据项，为选择空字符串或者0。可选，默认没有文本字符串；本设置只对CODE－VALUE的字典有效
	 */
	private String emptyText = "";
	
	/**
	 * @param name
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @param catalog
	 * 
     * @jsp.attribute required="true" rtexprvalue="true"
	 */
	public void setCatalog(Object catalog) throws JspException {
		this.catalog = ExpressionEvaluatorManager.evaluate("catalog", catalog.toString(), Object.class, this, pageContext);
	}
	
	/**
	 * @param onlyShow
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setOnlyShow(String onlyShow) {
		this.onlyShow = onlyShow;
	}

	/**
	 * 设置 dictCodeHead 值为 <code>dictCodeHead</code>.
	 * @param dictCodeHead 要设置的 dictCodeHead 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setDictCodeHead(String dictCodeHead) {
		this.dictCodeHead = dictCodeHead;
	}

	/**
	 * 设置 dictCodeMinLength 值为 <code>dictCodeMinLength</code>.
	 * @param dictCodeMinLength 要设置的 dictCodeMinLength 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setDictCodeMinLength(String dictCodeMinLength) {
		this.dictCodeMinLength = dictCodeMinLength;
	}

	/**
	 * 设置 dictCodeMaxLength 值为 <code>dictCodeMaxLength</code>.
	 * @param dictCodeMaxLength 要设置的 dictCodeMaxLength 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setDictCodeMaxLength(String dictCodeMaxLength) {
		this.dictCodeMaxLength = dictCodeMaxLength;
	}

	/**
	 * 设置 filter 值为 <code>filter</code>.
	 * @param filter 要设置的 filter 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setFilter(String filter) {
		this.filter = filter;
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
	 * @param selectText the selectText to set
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setSelectText(String selectText) throws JspException {
		this.selectText = selectText;
	}

	/**
	 * 设置 emptyText 值为 <code>emptyText</code>.
	 * @param emptyText 要设置的 emptyText 值
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setEmptyText(String emptyText) {
		this.emptyText = emptyText;
	}

	public int doStartTag() throws JspException {
    	DictionaryManager dictionaryManager = (DictionaryManager)SpringContextHolder.getBean("dictionaryManager");
    	
    	DictCatalog dictCatalog = null;
		try {
			dictCatalog = dictionaryManager.getDictCatalog(catalog.toString());
		} catch (DataNotExistException e) {
			throw new JspException("字典目录[" + catalog + "]未定义！");
		}
		
    	int mode = dictCatalog.getOpMode();
    	
    	onlyShow = onlyShow.toLowerCase();
    	boolean onlyShowFlag = onlyShow.equals("true") || onlyShow.equals("yes") || onlyShow.equals("1");
    	
    	List<Dictionary> dictionarys;
    	if (dictCodeHead != null) {
    		dictionarys = dictionaryManager.getDictionaries(catalog.toString(), onlyShowFlag, dictCodeHead);
    	} else if (dictCodeMinLength != null || dictCodeMaxLength != null) {
    		int minLength = dictCodeMinLength == null ? -1 : Integer.parseInt(dictCodeMinLength);
    		int maxLength = dictCodeMaxLength == null ? -1 : Integer.parseInt(dictCodeMaxLength);
    		
    		dictionarys = dictionaryManager.getDictionaries(catalog.toString(), onlyShowFlag, minLength, maxLength);
    	} else if (filter != null && !"".equals(filter) && mode != DictCatalog.MODE_VALUE) {
    		String[] filters = filter.split(",");
    		
    		if (mode == DictCatalog.MODE_CODE_VALUE)
    			dictionarys = dictionaryManager.getDictionaries(catalog.toString(), onlyShowFlag, filters);
    		else {
    			int[] numberFilters = new int[filters.length];
    			
    			for (int index = 0; index < filters.length; index++)
    				numberFilters[index] = Integer.parseInt(filters[index]);
    			
    			dictionarys = dictionaryManager.getDictionaries(catalog.toString(), onlyShowFlag, numberFilters);
    		}
    	} else
    		dictionarys = dictionaryManager.getDictionaries(catalog.toString(), onlyShowFlag);
    	
    	StringBuffer buf = new StringBuffer();
    	
		if (name != null)
			buf.append("var ").append(name).append(" = ");
    	
    	buf.append("[");
    	
    	boolean first = true;
    	
    	if (!"".equals(selectText)) {
    		String text = TagUtils.getMessage(pageContext, bundle, selectText);
    			
        	if (mode == DictCatalog.MODE_ID_VALUE) {
        		buf.append("['" + Condition.IGNORE + "', '" + text + "']");
        	} else if (mode == DictCatalog.MODE_CODE_VALUE) {
        		buf.append("['" + Condition.IGNORE + "', '" + text + "']");
        	} else {
        		buf.append("['" + text + "']");
        	}
        	
    		first = false;
    	}
    	
    	for(Dictionary dictionary : dictionarys) {
        	if (first)
        		first = false;
        	else
        		buf.append(", ");
        	
        	if (mode == DictCatalog.MODE_ID_VALUE)
        		buf.append("[" + dictionary.getDictId() + ", '" + dictionary.getDictValue() + "']");
        	else if (mode == DictCatalog.MODE_CODE_VALUE)
        		buf.append("['" + dictionary.getDictCode() + "', '" + dictionary.getDictValue() + "']");
        	else
        		buf.append("['" + dictionary.getDictValue() + "']");
    	}
    	
    	if (!"".equals(emptyText)) {
        	if (!first)
        		buf.append(", ");
        	
    		String text = TagUtils.getMessage(pageContext, bundle, emptyText);
    			
        	if (mode == DictCatalog.MODE_ID_VALUE) {
        		buf.append("[" + 0 + ", '" + text + "']");
        	} else if (mode == DictCatalog.MODE_CODE_VALUE) {
        		buf.append("['', '" + text + "']");
        	} else {
        		buf.append("['']");
        	}
    	}
    	
    	buf.append("]");
    	
		if (name != null)
			buf.append(";");
    	
		try {
			pageContext.getOut().print(buf.toString());
		} catch (IOException ioe) {
			throw new JspTagException(ioe.toString(), ioe);
		}
		return SKIP_BODY;
	}
}
