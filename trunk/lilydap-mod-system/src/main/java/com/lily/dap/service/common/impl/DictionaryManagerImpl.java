/**
 * 
 */
package com.lily.dap.service.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.BaseEntity;
import com.lily.dap.entity.dictionary.DictAccess;
import com.lily.dap.entity.dictionary.DictCatalog;
import com.lily.dap.entity.dictionary.DictClassify;
import com.lily.dap.entity.dictionary.Dictionary;
import com.lily.dap.service.common.DictionaryManager;
import com.lily.dap.service.core.BaseManager;
import com.lily.dap.service.exception.DataContentRepeatException;
import com.lily.dap.service.exception.DataHaveUsedException;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.exception.ServiceException;
import com.lily.dap.service.util.EntityOrderUtil;
import com.lily.dap.service.util.VerifyUtil;
import com.lily.dap.util.BaseEntityHelper;

import javax.annotation.PostConstruct;

/**
 * 数据字典管理实现类
 * 
 * @author A邹学模
 *
 */
@Service("dictionaryManager")
public class DictionaryManagerImpl extends BaseManager implements
		DictionaryManager {
    protected final Log logger = LogFactory.getLog(getClass());

    private EntityOrderUtil entityOrderUtil;
    
    private VerifyUtil verifyUtil;
    
    /**
     * <code>dictionaryCacheMap</code> 字典缓存Map
     */
    private Map<String, List<Dictionary>> dictionaryCacheMap = new HashMap<String, List<Dictionary>>();
    
    /**
     * 存放字典使用实体集合的Map，其中key对应那个字典目录，value为这个字典目录有那些实体对象的那个字段使用着
     */
    private Map<String, List<DictAccess>> dictionaryAccessMap = new HashMap<String, List<DictAccess>>();
    
    @PostConstruct
    public void init() {
    	entityOrderUtil = new EntityOrderUtil(dao);
    	verifyUtil = new VerifyUtil(dao);
    	
    	//在执行单元测试时的各个测试方法时，服务类会一直存在，因而缓存数据也会一直保持存在，但是每个方法可能会重新执行SQL语句而生成数据库数据库数据，
    	//因而导致缓存数据和数据库数据不一致，在这里进行数据清除工作，在正式运行的系统不会出现这种问题
    	dictionaryCacheMap.clear();
    	dictionaryAccessMap.clear();
    	
    	//读取字典使用实体数据至dictionaryAccessMap，以供在执行删除字典数据时判断数据是否被使用的依据
    	List<DictAccess> list = dao.query(DictAccess.class, null);
    	for (DictAccess dictAccess : list) {
    		String catalogCode = dictAccess.getCatalogCode();
    		
    		List<DictAccess> dictAccessList = dictionaryAccessMap.get(catalogCode);
    		if (dictAccessList == null) {
    			dictAccessList = new ArrayList<DictAccess>();
    			
    			dictionaryAccessMap.put(catalogCode, dictAccessList);
    		}
    		
    		dictAccessList.add(dictAccess);
    	}
    }
    
	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.DictionaryManager#addDictionary(java.lang.String, long, java.lang.String, java.lang.String)
	 */
	public Dictionary addDictionary(String catalog, int dictId, String dictCode, String dictValue)
			throws DataContentRepeatException {
		verifyUtil.assertDataExist(DictCatalog.class, "code", catalog, "字典目录" + catalog + "未定义，请检查要添加字典的字典目录名！");
		
		DictCatalog dictCatalog = getDictCatalog(catalog);
		int mode = dictCatalog.getOpMode();
		
		Dictionary dictionary;
		if (mode == DictCatalog.MODE_ID_VALUE)
			dictionary = addDictionary(catalog, dictId, dictValue);
		else if (mode == DictCatalog.MODE_CODE_VALUE)
			dictionary = addDictionary(catalog, dictCode, dictValue);
		else
			dictionary = addDictionary(catalog, dictValue);
		
		return dictionary;
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.DictionaryManager#addDictionary(java.lang.String, long, java.lang.String)
	 */
	public Dictionary addDictionary(String catalog, int dictId,
			String dictValue) throws DataContentRepeatException {
		verifyUtil.assertDataExist(DictCatalog.class, "code", catalog, "字典目录" + catalog + "未定义，请检查要添加字典的字典目录名！");
		verifyUtil.assertDataNotRepeat(Dictionary.class, new String[]{"catalogCode", "dictId"}, new Object[]{catalog, dictId}, "数据字典ID重复，请重新输入字典ID值！");
		
		DictCatalog dictCatalog = getDictCatalog(catalog);
		int mode = dictCatalog.getOpMode();
		if (mode != DictCatalog.MODE_ID_VALUE)
			throw new ServiceException("要添加字典的模式不是ID-VALUE模式！");
		
		Dictionary dictionary = new Dictionary();
		dictionary.setCatalogCode(catalog);
		dictionary.setDictId(dictId);
		dictionary.setDictValue(dictValue);
		
		return doAddDictionary(dictionary);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.DictionaryManager#addDictionary(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Dictionary addDictionary(String catalog, String dictCode,
			String dictValue) throws DataContentRepeatException {
		verifyUtil.assertDataExist(DictCatalog.class, "code", catalog, "字典目录" + catalog + "未定义，请检查要添加字典的字典目录名！");
		verifyUtil.assertDataNotRepeat(Dictionary.class, new String[]{"catalogCode", "dictCode"}, new Object[]{catalog, dictCode}, "数据字典编码重复，请重新输入字典编码值！");
		
		DictCatalog dictCatalog = getDictCatalog(catalog);
		int mode = dictCatalog.getOpMode();
		if (mode != DictCatalog.MODE_CODE_VALUE)
			throw new ServiceException("要添加字典的模式不是CODE-VALUE模式！");
		
		Dictionary dictionary = new Dictionary();
		dictionary.setCatalogCode(catalog);
		dictionary.setDictCode(dictCode);
		dictionary.setDictValue(dictValue);
		
		return doAddDictionary(dictionary);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.DictionaryManager#addDictionary(java.lang.String, java.lang.String)
	 */
	public Dictionary addDictionary(String catalog, String dictValue)
			throws DataContentRepeatException {
		verifyUtil.assertDataExist(DictCatalog.class, "code", catalog, "字典目录" + catalog + "未定义，请检查要添加字典的字典目录名！");
		verifyUtil.assertDataNotRepeat(Dictionary.class, new String[]{"catalogCode", "dictValue"}, new Object[]{catalog, dictValue}, "数据字典值重复，请重新输入字典值！");
		
		DictCatalog dictCatalog = getDictCatalog(catalog);
		int mode = dictCatalog.getOpMode();
		if (mode != DictCatalog.MODE_VALUE)
			throw new ServiceException("要添加字典的模式不是VALUE模式！");
		
		Dictionary dictionary = new Dictionary();
		dictionary.setCatalogCode(catalog);
		dictionary.setDictValue(dictValue);
		
		return doAddDictionary(dictionary);
	}
	
	private Dictionary doAddDictionary(Dictionary dictionary) {
		String catalog = dictionary.getCatalogCode();
		
		int sn = entityOrderUtil.getEntityMaxOrder(Dictionary.class, "sn", new Condition().eq("catalogCode", catalog));
		dictionary.setSn(sn + 1);
		
		dictionary.setSystemFlag(0);
		dictionary.setShowFlag(1);
		dictionary.setEnableFlag(1);
		
		dao.saveOrUpdate(dictionary);
		
		updateDictionaryCacheData(catalog, dictionary);
		return dictionary;
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.DictionaryManager#adjustDictionaryOrder(long, int)
	 */
	public void adjustDictionaryOrder(long id, int order)
			throws DataNotExistException {
		Dictionary dictionary = getDictionary(id);
		
		entityOrderUtil.adjustEntityOrder(Dictionary.class, id, order, "sn", new Condition().eq("catalogCode", dictionary.getCatalogCode()));
		
		updateDictionaryCacheDatas(dictionary.getCatalogCode());
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.DictionaryManager#getDictCatalog(long)
	 */
	public DictCatalog getDictCatalog(long id) throws DataNotExistException {
		return get(DictCatalog.class, id);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.DictionaryManager#getDictCatalog(java.lang.String)
	 */
	public DictCatalog getDictCatalog(String code) throws DataNotExistException {
		return get(DictCatalog.class, "code", code);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.DictionaryManager#getDictCatalogs(java.lang.String, boolean)
	 */
	public List<DictCatalog> getDictCatalogs(String classify, boolean onlyEditFlag) {
		Condition condition = Condition.create();
		condition.eq("classify", classify);
		if (onlyEditFlag)
			condition.eq("editFlag", DictCatalog.EDIT_ALLOW);
		condition.asc("sn");
		
		return dao.query(DictCatalog.class, condition);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.DictionaryManager#getDictClassify(int)
	 */
	public DictClassify getDictClassify(long id) throws DataNotExistException {
		return get(DictClassify.class, id);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.DictionaryManager#getDictClassifys()
	 */
	public List<DictClassify> getDictClassifys() {
		return dao.query(DictClassify.class, new Condition().asc("sn"));
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.DictionaryManager#getDictionaries(java.lang.String, boolean)
	 */
	public List<Dictionary> getDictionaries(String catalog, boolean onlyShowFlag) {
		List<Dictionary> dictionarys = getDictionaryCacheData(catalog);
		
		List<Dictionary> results = new ArrayList<Dictionary>();
		if (!onlyShowFlag)
			results.addAll(dictionarys);
		else {
			for (Dictionary dictionary : dictionarys) {
				if (dictionary.getShowFlag() == 1)
					results.add(dictionary);
			}
		}
		
		return results;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.common.DictionaryManager#getDictionaries(java.lang.String, boolean, int, int)
	 */
	public List<Dictionary> getDictionaries(String catalog,
			boolean onlyShowFlag, int dictCodeMinLength, int dictCodeMaxLength) {
		List<Dictionary> dictionarys = getDictionaryCacheData(catalog);

		List<Dictionary> results = new ArrayList<Dictionary>();
		for (Dictionary dictionary : dictionarys) {
			if (!onlyShowFlag || dictionary.getShowFlag() == 1) {
				int length = dictionary.getDictCode().length();
				
				if ((dictCodeMinLength < 0 || length >= dictCodeMinLength) &&
					(dictCodeMaxLength < 0 || length <= dictCodeMaxLength))
				results.add(dictionary);
			}
		}
		
		return results;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.common.DictionaryManager#getDictionaries(java.lang.String, boolean, java.lang.String)
	 */
	public List<Dictionary> getDictionaries(String catalog,
			boolean onlyShowFlag, String dictCodeHead) {
		List<Dictionary> dictionarys = getDictionaryCacheData(catalog);

		if (dictCodeHead == null)
			dictCodeHead = "";
		
		List<Dictionary> results = new ArrayList<Dictionary>();
		for (Dictionary dictionary : dictionarys) {
			if (!onlyShowFlag || dictionary.getShowFlag() == 1) {
				if (dictionary.getDictCode().startsWith(dictCodeHead))
					results.add(dictionary);
			}
		}
		
		return results;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.common.DictionaryManager#getDictionaries(java.lang.String, boolean, java.lang.String[])
	 */
	public List<Dictionary> getDictionaries(String catalog,
			boolean onlyShowFlag, String[] filter) {
		List<Dictionary> dictionarys = getDictionaryCacheData(catalog);
		
		List<Dictionary> results = new ArrayList<Dictionary>();
		for (Dictionary dictionary : dictionarys) {
			if (!onlyShowFlag || dictionary.getShowFlag() == 1) {
				for (String code : filter)
					if (code.equals(dictionary.getDictCode())) {
						results.add(dictionary);
						break;
					}
			}
		}
		
		return results;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.common.DictionaryManager#getDictionaries(java.lang.String, boolean, int[])
	 */
	public List<Dictionary> getDictionaries(String catalog,
			boolean onlyShowFlag, int[] filter) {
		List<Dictionary> dictionarys = getDictionaryCacheData(catalog);
		
		List<Dictionary> results = new ArrayList<Dictionary>();
		for (Dictionary dictionary : dictionarys) {
			if (!onlyShowFlag || dictionary.getShowFlag() == 1) {
				for (int id : filter)
					if (id == dictionary.getDictId()) {
						results.add(dictionary);
						break;
					}
			}
		}
		
		return results;
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.DictionaryManager#getDictionary(long)
	 */
	public Dictionary getDictionary(long id) throws DataNotExistException {
		return get(Dictionary.class, id);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.DictionaryManager#getDictionary(java.lang.String, long)
	 */
	public Dictionary getDictionary(String catalog, int dictId)
			throws DataNotExistException {
		List<Dictionary> dictionarys = getDictionaryCacheData(catalog);
		for (Dictionary dictionary : dictionarys) {
			if (dictionary.getDictId() == dictId)
				return dictionary;
		}
		
		throw new DataNotExistException("指定目录[" + catalog + "]和ID[" + dictId + "]的字典记录未找到");
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.DictionaryManager#getDictionary(java.lang.String, java.lang.String)
	 */
	public Dictionary getDictionary(String catalog, String dictCode)
			throws DataNotExistException {
		List<Dictionary> dictionarys = getDictionaryCacheData(catalog);
		for (Dictionary dictionary : dictionarys) {
			if (dictCode.equals(dictionary.getDictCode()))
				return dictionary;
		}
		
		throw new DataNotExistException("指定目录[" + catalog + "]和CODE[" + dictCode + "]的字典记录未找到");
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.common.DictionaryManager#getDictionaryExt(java.lang.String, java.lang.String)
	 */
	public Dictionary getDictionaryExt(String catalog, String dictValue)
			throws DataNotExistException {
		List<Dictionary> dictionarys = getDictionaryCacheData(catalog);
		for (Dictionary dictionary : dictionarys) {
			if (dictValue.equals(dictionary.getDictValue()))
				return dictionary;
		}
		
		throw new DataNotExistException("指定目录[" + catalog + "]和值[" + dictValue + "]的字典记录未找到");
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.DictionaryManager#modifyDictionary(long, java.lang.String)
	 */
	public void modifyDictionary(long id, String dictValue)
			throws DataNotExistException {
		Dictionary dictionary = getDictionary(id);
		if (dictionary.getSystemFlag() == 1)
			throw new DataHaveUsedException("系统编码字典，不允许修改！");
		
		dictionary.setDictValue(dictValue);
		dao.saveOrUpdate(dictionary);
		
		changeDictionaryCacheData(dictionary.getCatalogCode(), dictionary);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.DictionaryManager#removeDictionary(long)
	 */
	public void removeDictionary(long id) throws DataHaveUsedException, DataNotExistException {
		Dictionary dictionary = getDictionary(id);
		if (dictionary.getSystemFlag() == 1)
			throw new DataHaveUsedException("系统编码字典，不允许删除！");
		
		checkDictionaryIsUsing(dictionary);
		
		dao.remove(dictionary);
		
		removeDictionaryCacheData(dictionary.getCatalogCode(), dictionary);
	}
	
	private void checkDictionaryIsUsing(Dictionary dictionary) {
		String catalogCode = dictionary.getCatalogCode();

		DictCatalog dictCatalog = getDictCatalog(catalogCode);
		int mode = dictCatalog.getOpMode();
		
		if (mode != DictCatalog.MODE_VALUE) {	//对于字典模式不是Value模式的，需要检查字典使用情况表中字典数据是否已经使用
			List<DictAccess> dictAccessList = dictionaryAccessMap.get(catalogCode);
			if (dictAccessList != null) {
				for (DictAccess dictAccess : dictAccessList) {
					String modelName = dictAccess.getAccessModelName();
					String fieldName = dictAccess.getAccessFieldName();

					Class<? extends BaseEntity> modelClass;
					try {
						modelClass = BaseEntityHelper.parseEntity(modelName);
					} catch (Exception e) {
						logger.warn("字典[" + catalogCode + "]使用情况类名[" + modelName + "]对应类不存在，请检查数据库中数据!");
						continue;
					}
					
					Condition condition = Condition.create();
					if (mode == DictCatalog.MODE_ID_VALUE)
						condition.eq(fieldName, dictionary.getDictId());
					else
						condition.eq(fieldName, dictionary.getDictCode());
					
					long count = dao.count(modelClass, condition);
					if (count > 0)
						throw new DataHaveUsedException("字典数据已经被使用，不允许删除！");
				}
			}
		}
	}
	
	private List<Dictionary> updateDictionaryCacheDatas(String catalog) {
		List<Dictionary> dictionarys = dictionaryCacheMap.get(catalog);
		if (dictionarys != null) {
			dictionaryCacheMap.remove(dictionarys);
		
			dictionarys.clear();
		}
		
		Condition condition = Condition.create();
		condition.eq("catalogCode", catalog);
		condition.asc("sn");
		
		List<Dictionary> list = dao.query(Dictionary.class, condition);
		
		dictionarys = new ArrayList<Dictionary>();
		for (Dictionary dictionary : list) {
			try {
				dictionarys.add(dictionary.clone());
			} catch (CloneNotSupportedException e) { // 永远不会抛出的异常
				e.printStackTrace();
			}
		}
		
		dictionaryCacheMap.put(catalog, dictionarys);
		return list;
	}
	
	private void updateDictionaryCacheData(String catalog, Dictionary dictionary) {
		List<Dictionary> dictionarys = dictionaryCacheMap.get(catalog);
		if (dictionarys == null) {
			updateDictionaryCacheDatas(catalog);
			return;
		}
		
		try {
			int index = -1;
			for (int i = 0; i < dictionarys.size(); i++) {
				if (dictionarys.get(i).getSn() > dictionary.getSn()) {
					index = i;
					break;
				}
			}
			if (index >= 0)
				dictionarys.add(index, dictionary.clone());
			else
				dictionarys.add(dictionary.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	private void removeDictionaryCacheData(String catalog, Dictionary dictionary) {
		List<Dictionary> dictionarys = dictionaryCacheMap.get(catalog);
		if (dictionarys != null) {
			for (Dictionary d : dictionarys) {
				if (d.getId() == dictionary.getId()) {
					dictionarys.remove(d);
					return;
				}
			}
		}
	}
	
	private void changeDictionaryCacheData(String catalog, Dictionary dictionary) {
		removeDictionaryCacheData(catalog, dictionary);
		
		updateDictionaryCacheData(catalog, dictionary);
	}
	
	private List<Dictionary> getDictionaryCacheData(String catalog) {
		List<Dictionary> dictionarys = dictionaryCacheMap.get(catalog);
		if (dictionarys == null)
			dictionarys = updateDictionaryCacheDatas(catalog);
		
		return dictionarys;
	}



}
