package com.lily.dap.web.taglib;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lily.dap.entity.dictionary.DictCatalog;
import com.lily.dap.entity.dictionary.Dictionary;
import com.lily.dap.service.common.DictionaryManager;

/**
 * �����ֵ���Ϣ�������ֵ����Ա�ǩ����ΪStruts�����б�ʹ�ã��ṩ�������ֵ��б���������.
 *
 * <p>
 * <b>NOTE</b> - This tag requires a Java2 (JDK 1.2 or later) platform.
 * </p>
 *
 * @author zouxuemo
 *
 * @jsp.tag name="dictionary"
 */
public class DictionaryTag extends IdObjectTag {
	/*
	 * �÷�1�����ø���Ŀ¼��catalog="sex"�����ֵ伯�������������ԣ�name="sex"��
	 * <LilyDAP:dictionary name="sex" toScope="page" catalog="sex" onlyShow="true"/>
	 * <html:select property="sex">	
	 * 	<html:options collection="sex" property="name" labelProperty="name"/>
	 * </html:select>
	 * 
	 * �÷�2���������Ŀ¼��catalog="sex"���ĸ����ֵ�ID��id="${partyMemberForm.branchId}"�����ֵ�����
	 * <LilyDAP:dictionary catalog="sex"/><c:out value="${partyMemberForm.sex}"/></LilyDAP:dictionary>
	 */
	public static final String SHOWALL_NAME = "-- ���� --";
	
	private static final long serialVersionUID = 3905528206810167095L;
    private String catalog;
    private String onlyShow = "false";
    private String onlyEnabled = "true";
    private String includeShowAll = "false";
    
    private static final int refreshPeriod = 60;

    /**
     * @param catalog �ֵ�����Ŀ¼CODE
     *
     * @jsp.attribute required="true" rtexprvalue="true"
     */
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

	/**
     * @param �Ƿ�ֻ������ʾ���ֵ���Ϣ��Ĭ����false
     * @jsp.attribute required="false" rtexprvalue="true"
     */
    public void setOnlyShow(String onlyShow) {
        this.onlyShow = onlyShow;
    }

    /**
     * @param �Ƿ�ֻ����ʹ�ܵ��ֵ���Ϣ��Ĭ����true
     * @jsp.attribute required="false" rtexprvalue="true"
     */
    public void setOnlyEnabled(String onlyEnabled) {
        this.onlyEnabled = onlyEnabled;
    }

    /**
     * @param includeShowAll �Ƿ����һ����ʶ���е��ֵ��¼
     * @jsp.attribute required="false" rtexprvalue="true"
     */
    public void setIncludeShowAll(String includeShowAll) {
		this.includeShowAll = includeShowAll;
	}

    /**
     * ��������������ֵ��б�.
     *
     * @return Ҫ�������ֵ伯���б�
     */
    protected List retrieveObjectList() {
    	boolean onlyShowFlag = false;
    	if ("true".equals(onlyShow) || "yes".equals(onlyShow))
    		onlyShowFlag = true;
    	boolean onlyEnabledFlag = true;
    	if ("false".equals(onlyEnabled) || "no".equals(onlyEnabled))
    		onlyEnabledFlag = false;
    		
     	DictionaryManager manager = getDictionaryManager();
     	
    	String catalogCode = getDictCatalog(manager);
    	List dictionaryList = manager.getDictionaries(catalogCode, onlyShowFlag);
    	
    	if ("true".equals(includeShowAll) || "yes".equals(includeShowAll)) {
    		Dictionary dictionary = new Dictionary();
    		dictionary.setId(0);
    		dictionary.setCatalogCode(catalogCode);
    		dictionary.setDictId(-1);
    		dictionary.setSn(0);
    		dictionary.setDictValue(SHOWALL_NAME);
    		dictionary.setEnableFlag(1);
    		dictionary.setShowFlag(1);
    		dictionary.setSystemFlag(1);
    		dictionaryList.add(0, dictionary);
    	}
	    	
        return dictionaryList;
    }
    
    /**
     * ���ظ���ID�Ĳ�������
     * 
     * @param id
     * @return
     */
    protected String getNameForId(long id) {
     	DictionaryManager manager = getDictionaryManager();
    	
    	String catalogCode = getDictCatalog(manager);
    	Dictionary dictionary = manager.getDictionary(catalogCode, (int)id);
    	
    	return dictionary.getDictValue();
    }
    
    protected DictionaryManager getDictionaryManager() {
    	ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
    	return (DictionaryManager)ctx.getBean("dictionaryManager");
    }
    
    protected String getDictCatalog(DictionaryManager manager) {
    	DictCatalog dictCatalog = manager.getDictCatalog(catalog);
    	return dictCatalog.getCode();
    }
}
