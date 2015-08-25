package com.lily.dap.web.taglib;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lily.dap.entity.dictionary.DictCatalog;
import com.lily.dap.entity.dictionary.Dictionary;
import com.lily.dap.service.common.DictionaryManager;

/**
 * 检索字典信息并设置字典属性标签，作为Struts下拉列表使用，提供给定的字典列表数据属性.
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
	 * 用法1：设置给定目录（catalog="sex"）的字典集合至给定的属性（name="sex"）
	 * <LilyDAP:dictionary name="sex" toScope="page" catalog="sex" onlyShow="true"/>
	 * <html:select property="sex">	
	 * 	<html:options collection="sex" property="name" labelProperty="name"/>
	 * </html:select>
	 * 
	 * 用法2：输出给定目录（catalog="sex"）的给定字典ID（id="${partyMemberForm.branchId}"）的字典名称
	 * <LilyDAP:dictionary catalog="sex"/><c:out value="${partyMemberForm.sex}"/></LilyDAP:dictionary>
	 */
	public static final String SHOWALL_NAME = "-- 所有 --";
	
	private static final long serialVersionUID = 3905528206810167095L;
    private String catalog;
    private String onlyShow = "false";
    private String onlyEnabled = "true";
    private String includeShowAll = "false";
    
    private static final int refreshPeriod = 60;

    /**
     * @param catalog 字典所属目录CODE
     *
     * @jsp.attribute required="true" rtexprvalue="true"
     */
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

	/**
     * @param 是否只检索显示的字典信息，默认是false
     * @jsp.attribute required="false" rtexprvalue="true"
     */
    public void setOnlyShow(String onlyShow) {
        this.onlyShow = onlyShow;
    }

    /**
     * @param 是否只检索使能的字典信息，默认是true
     * @jsp.attribute required="false" rtexprvalue="true"
     */
    public void setOnlyEnabled(String onlyEnabled) {
        this.onlyEnabled = onlyEnabled;
    }

    /**
     * @param includeShowAll 是否添加一条标识所有的字典记录
     * @jsp.attribute required="false" rtexprvalue="true"
     */
    public void setIncludeShowAll(String includeShowAll) {
		this.includeShowAll = includeShowAll;
	}

    /**
     * 检索给定分类的字典列表.
     *
     * @return 要检索的字典集合列表
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
     * 返回给定ID的部门名称
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
