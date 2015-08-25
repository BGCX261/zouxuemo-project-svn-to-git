package com.lily.dap.web.action.common;

import org.springframework.beans.factory.annotation.Autowired;

import com.lily.dap.service.common.DictionaryManager;
import com.lily.dap.web.action.BaseAction;

/**
 * �ֵ����Action
 *
 * @author zouxuemo
 */
public class DictionaryAction extends BaseAction {
	private static final long serialVersionUID = -5144161041038549808L;

	@Autowired
	private DictionaryManager dictionaryManager;
	
	 /**
     * Ĭ�ϣ��������������ֵ����ҳ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String execute() throws Exception {
		return SUCCESS;
    } 
	
//	/**
//     * �����ֵ�Ŀ¼��JSON����
//     * 
//     * @param mapping
//     * @param form
//     * @param request
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    public String listCatalog(){
//    	
//		List<DictClassify> classifyList = dictionaryManager.getDictClassifys();//��ȡ�ֵ����
//		List<ExtTreeData> treeList = new ArrayList<ExtTreeData>();
//		for (DictClassify classify : classifyList) {
//    		List<DictCatalog> catalogList = dictionaryManager.getDictCatalogs(classify.getCode(), true);
//    		
//    		if (catalogList.size() == 0)
//    			continue;
//    		
//    		ExtTreeData classifyData = new ExtTreeData();
//    		treeList.add(classifyData);
//    		
//    		classifyData.setData("type", "classify");
//    		classifyData.setData("id", "classify"+classify.getId());
//    		classifyData.setData("code", classify.getCode());
//    		classifyData.setData("text", classify.getName());
//    		classifyData.setData("qtip", classify.getDes());
//			classifyData.setData("leaf", false);
//    		classifyData.setData("expanded", true);
//    		classifyData.setData("iconCls", "tree-classify");
//    		
//    		classifyData.setData("cls", "folder");
//    		
//    		for (DictCatalog catalog : catalogList) {
//	    		ExtTreeData catalogData = new ExtTreeData();
//	    		classifyData.addChildren(catalogData);
//	    		
//	    		catalogData.setData("type", "catalog");
//	    		catalogData.setData("id", "catalog"+catalog.getId());
//	    		catalogData.setData("code", catalog.getCode());
//	    		catalogData.setData("text", catalog.getName());
//	    		catalogData.setData("qtip", catalog.getDes());
//	    		catalogData.setData("mode", catalog.getOpMode());
//	    		catalogData.setData("leaf", true);
//	    		catalogData.setData("cls", "file");
//	    		catalogData.setData("iconCls", "tree-catalog");
//    		}
//		}
//    	
//		String jsonStr = ExtUtils.buildJSONString(treeList);
//		Struts2Utils.renderJson(jsonStr);
//		return null;
//    }
//    
//    /**
//     * �����ֵ�����JSON����
//     * 
//     * @param mapping
//     * @param form
//     * @param request
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    public String listDictionary(){
//		String catalog = Struts2Utils.getParameter("catalog");
//		
//		List<Dictionary> dictionaryList = dictionaryManager.getDictionaries(catalog, false);
//		String jsonStr = JsonHelper.formatObjectToJsonString(dictionaryList);
//		
//		Struts2Utils.renderJson(jsonStr);
//		return null;
//    }
//    
//    /**
//     * ������ֵ�����
//     * 
//     * @param mapping
//     * @param form
//     * @param request
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    public String addDictionary() {
//    	Dictionary dictionary = new Dictionary();
//    	String[] changedFields = BaseEntityHelper.getEntityAllowCreateFields(Dictionary.class);
//    	Struts2Utils.writeModel(dictionary, changedFields);
//    	String jsonStr = JsonHelper.combinSuccessJsonString();
//    	try {
//			dictionary = dictionaryManager.addDictionary(dictionary.getCatalogCode(), dictionary.getDictId(), dictionary.getDictCode(), dictionary.getDictValue());
//
//			jsonStr = JsonHelper.combinSuccessJsonString("data",dictionary);
//		} catch (DataContentRepeatException e) {
//			jsonStr = JsonHelper.combinFailJsonString("msg:" + e.getMessage());
//		}
//		
//		Struts2Utils.renderJson(jsonStr);
//    	return null;
//    }
//    
//    /**
//     * �޸��ֵ����ݵ�����ֵ
//     * 
//     * @param mapping
//     * @param form
//     * @param request
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    public String modifyDictionary(){
//		long id = Struts2Utils.getLongParameter("id", 0);
//		String dictValue = Struts2Utils.getParameter("dictValue");
//		dictValue = EscapeUtils.unescape(dictValue);
//	
//		dictionaryManager.modifyDictionary(id, dictValue);
//		
//   		return null;
//    }
//    
//    /**
//     * ɾ���ֵ�����
//     * 
//     * @param mapping
//     * @param form
//     * @param request
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    public String removeDictionary(){
//		long id = Struts2Utils.getLongParameter("id", 0);
//	
//    	String jsonStr = JsonHelper.combinSuccessJsonString();
//    	
//		try {
//			dictionaryManager.removeDictionary(id);
//		} catch (DataHaveUsedException e) {
//			jsonStr = JsonHelper.combinFailJsonString(e.getMessage());
//		}
//		jsonStr = JsonHelper.combinSuccessJsonString("id",id);
//		
//		Struts2Utils.renderJson(jsonStr);
//   		return null;
//    }
//    /**
//     * �����ֵ���������˳��
//     * 
//     * @param mapping
//     * @param form
//     * @param request
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    public String adjustDictionaryOrder(){
//		long id = Struts2Utils.getLongParameter("id", 0);
//		int order = Struts2Utils.getIntParameter("order", 0);
//		
//		dictionaryManager.adjustDictionaryOrder(id, order);
//		
//		return null;
//    }
}
