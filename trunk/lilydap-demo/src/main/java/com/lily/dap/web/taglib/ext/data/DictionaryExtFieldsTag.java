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
 * �����ֵ�����Դ�ֶα�ǩ
 * �÷�1����name����
 * <lilydap:dictionaryExtFields name="enabledFields" catalog="yes_no" selectText="-�Ƿ񼤻�-"/>
 * ִ�к��������������´��룺
 * 	var enabledFields = [['', '-�Ƿ񼤻�-'],['true', '��'],['false', '��']];
 * 
 * �÷�2������name����
 * <lilydap:dictionaryExtFields catalog="yes_no" selectText="-�Ƿ񼤻�-"/>
 * ִ�к��������������´��룺
 * [['', '-�Ƿ񼤻�-'],['true', '��'],['false', '��']];
 * 
 * @author ��ѧģ
 *
 * @jsp.tag name="dictionaryExtFields"
 */
public class DictionaryExtFieldsTag extends TagSupport {
	private static final long serialVersionUID = -6134750115202726684L;

	/**
	 * ����field�ı������ƣ���ѡ����������ã�����
	 */
	private String name = null;
	
	/**
	 * Ҫ�������ֵ�Ŀ¼����������
	 */
	private Object catalog = null;
	
	/**
	 * �Ƿ�ֻ����showFlagΪtrue���ֵ䣬��ѡ��Ĭ��Ϊtrue
	 */
	private String onlyShow = "true";
	
	/**
	 * <code>dictCodeHead</code> �ֵ�Code�޶���ͷ�ַ�������ѡ
	 */
	private String dictCodeHead = null;
	
	/**
	 * <code>dictCodeMinLength</code> �ֵ�Code�޶���С�ַ������ȣ���ѡ
	 */
	private String dictCodeMinLength = null;
	
	/**
	 * <code>dictCodeMaxLength</code> �ֵ�Code�޶�����ַ������ȣ���ѡ
	 */
	private String dictCodeMaxLength = null;
	
	/**
	 * <code>filter</code> �ֵ�����ַ����������ֶ�֮����","�ָ�
	 */
	private String filter = null;
	
	/**
	 * <code>bundle</code> ʹ�õ���Դ����
	 */
	private String bundle = null;

	/**
	 * ��������ǰ��������һ�������Ϊѡ�����л��߲�ѡ����ı��ַ�������ѡ��Ĭ��Ϊ"-����-"��
	 */
	private String selectText = "";
	
	/**
	 * <code>emptyText</code> �����ݵ����������һ�������Ϊѡ����ַ�������0����ѡ��Ĭ��û���ı��ַ�����������ֻ��CODE��VALUE���ֵ���Ч
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
	 * ���� id ֵΪ <code>id</code>.
	 * @param id Ҫ���õ� id ֵ
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
	 * ���� dictCodeHead ֵΪ <code>dictCodeHead</code>.
	 * @param dictCodeHead Ҫ���õ� dictCodeHead ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setDictCodeHead(String dictCodeHead) {
		this.dictCodeHead = dictCodeHead;
	}

	/**
	 * ���� dictCodeMinLength ֵΪ <code>dictCodeMinLength</code>.
	 * @param dictCodeMinLength Ҫ���õ� dictCodeMinLength ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setDictCodeMinLength(String dictCodeMinLength) {
		this.dictCodeMinLength = dictCodeMinLength;
	}

	/**
	 * ���� dictCodeMaxLength ֵΪ <code>dictCodeMaxLength</code>.
	 * @param dictCodeMaxLength Ҫ���õ� dictCodeMaxLength ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setDictCodeMaxLength(String dictCodeMaxLength) {
		this.dictCodeMaxLength = dictCodeMaxLength;
	}

	/**
	 * ���� filter ֵΪ <code>filter</code>.
	 * @param filter Ҫ���õ� filter ֵ
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setFilter(String filter) {
		this.filter = filter;
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
	 * @param selectText the selectText to set
	 * 
     * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setSelectText(String selectText) throws JspException {
		this.selectText = selectText;
	}

	/**
	 * ���� emptyText ֵΪ <code>emptyText</code>.
	 * @param emptyText Ҫ���õ� emptyText ֵ
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
			throw new JspException("�ֵ�Ŀ¼[" + catalog + "]δ���壡");
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
