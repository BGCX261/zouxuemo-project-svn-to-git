/**
 * 
 */
package com.lily.dap.service.common;

import java.util.List;

import com.lily.dap.entity.dictionary.DictCatalog;
import com.lily.dap.entity.dictionary.DictClassify;
import com.lily.dap.entity.dictionary.Dictionary;
import com.lily.dap.service.exception.DataContentRepeatException;
import com.lily.dap.service.exception.DataHaveUsedException;
import com.lily.dap.service.exception.DataNotExistException;

/**
 * <h1>字典管理接口</h1>
 * 
 * @author 邹学模
 *
 */
public interface DictionaryManager {
	/**
	 * 获取指定的字典分类
	 * 
	 * @param id	-	字典分类id
	 * @return	字典分类	
	 * @throws DataNotExistException	-	字典分类未找到
	 */
	public DictClassify getDictClassify(long id)  throws DataNotExistException;
	
	/**
	 * 获取所有字典分类
	 * 
	 * @return　字典分类列表，若未找到任何分类，则列表为空。该列表已根据字典目录排序号排序。
	 */
	public List<DictClassify> getDictClassifys();
	
	/**
	 * 获取指定字典目录分类下的指定字典目录
	 * 
	 * @param id	-	字典目录id
	 * @return	获取的字典目录
	 * @throws DataNotExistException	字典目录未找到
	 */
	public DictCatalog getDictCatalog(long id) throws DataNotExistException;
	
	/**
	 * 获取指定编号的字典目录
	 * 
	 * @param code	-	字典目录编号
	 * @return	获取的字典目录
	 * @throws DataNotExistException	字典目录未找到
	 */
	public DictCatalog getDictCatalog(String code) throws DataNotExistException;
	
	/**
	 * 获取指定字典目录分类下的指定编辑状态所有字典目录
	 * 
	 * @param classify - 字典目录分类编号
	 * @param onlyEditFlag - 是否只检索允许编辑的字典目录
	 * @return	包含该分类下所有字典目录的列表，若此分类下无目录，则返回空列表;该列表已根据字典目录排序号排序。
	 */
	public List<DictCatalog> getDictCatalogs(String classify, boolean onlyEditFlag);
	
	/**
	 * 根据给定ID获取字典
	 * 
	 * @param id
	 * @return
	 * @throws DataNotExistException 如果找不到字典，抛出异常
	 */
	public Dictionary getDictionary(long id) throws DataNotExistException;
	
	/**
	 * 根据指定字典目录与字典ＩＤ获取字典
	 * 
	 * @param catalog  -	字典目录编号
	 * @param dictId	-	字典id
	 * @return Dictionary  字典
	 * @throws DataNotExistException	-	找不到字典
	 */
	public Dictionary getDictionary(String catalog, int dictId) throws DataNotExistException;
	
	/**
	 * 根据指定字典目录与字典编码获取字典
	 * 
	 * @param catalog 字典目录编号
	 * @param dictCode	字典编号
	 * @return Dictionary	字典
	 * @throws DataNotExistException	找不到字典
	 */
	public Dictionary getDictionary(String catalog, String dictCode) throws DataNotExistException;
	
	/**
	 * 获取给定分类的字典列表，返回列表集合
	 * 
	 * @param catalogCode 要检索的字典分类
	 * @param onlyShow 是否只检索可显示的字典信息
	 * @param onlyEnable 是否只检索使能状态的字典信息
	 * @return 返回字典集合List{Dictionary}
	 * @throws DataNotExistException 给定字典分类不存在异常
	 */
	public List<Dictionary> getDictionaries(String catalogCode, boolean onlyShow);
	
	/**
	 * 根据指定字典目录与字典值获取字典，如果有多个相同的字典值，返回第一个字典记录
	 * 
	 * @param catalog 字典目录编号
	 * @param dictValue	字典值
	 * @return Dictionary	字典
	 * @throws DataNotExistException	找不到字典
	 */
	public Dictionary getDictionaryExt(String catalog, String dictValue) throws DataNotExistException;
	

	/**
	 * 获取指定目录的字典，要求字典必须是Code－Value模式的，并且Code长度在给定的长度范围内
	 *
	 * @param catalog
	 * @param onlyShowFlag
	 * @param dictCodeMinLength Code最小长度，如果不限定，则设置为-1
	 * @param dictCodeMaxLength Code最大长度，如果不限定，则设置为-1
	 * @return
	 */
	public List<Dictionary> getDictionaries(String catalog, boolean onlyShowFlag, int dictCodeMinLength, int dictCodeMaxLength);
	
	/**
	 * 获取指定目录的字典，要求字典必须是Code－Value模式的，并且Code必须以给定的字符串开头
	 *
	 * @param catalog
	 * @param onlyShowFlag
	 * @param dictCodeHead 要设定的Code开头字符串
	 * @return
	 */
	public List<Dictionary> getDictionaries(String catalog, boolean onlyShowFlag, String dictCodeHead);
	
	/**
	 * 获取指定目录下指定编号的字典，要求字典必须是Code－Value模式
	 *
	 * @param catalog
	 * @param onlyShowFlag
	 * @param filter 要返回记录的字典Code数组，只有字典Code是在给定数组里的值的字典记录才返回
	 * @return
	 */
	public List<Dictionary> getDictionaries(String catalog, boolean onlyShowFlag, String[] filter);
	
	/**
	 * 获取指定目录下指定ID的字典，要求字典必须是ID－Value模式
	 *
	 * @param catalog
	 * @param onlyShowFlag
	 * @param filter 要返回记录的字典ID数组，只有字典ID是在给定数组里的值的字典记录才返回
	 * @return
	 */
	public List<Dictionary> getDictionaries(String catalog, boolean onlyShowFlag, int[] filter);
	
	/**
	 * 根据指定的属性，生成默认字典，然后添加（自动判断当前字典模式）
	 * 
	 * @param catalog　字典目录编码
	 * @param dictId	字典id
	 * @param dictCode	字典编号
	 * @param dictValue 字典名
	 * @return 返回生成的字典对象
	 * @throws DataContentRepeatException	该类别下已存在ID相同、或者Code相同的字典时抛出异常
	 */
	public Dictionary addDictionary(String catalog, int dictId, String dictCode, String dictValue) throws DataContentRepeatException;
	
	/**
	 * 根据指定的属性，生成默认字典，然后添加（针对模式为ID－Value模式）
	 * 
	 * @param catalog　字典目录编码
	 * @param dictId	字典id
	 * @param dictValue 字典名
	 * @return 返回生成的字典对象
	 * @throws DataContentRepeatException	该类别下已存在ID相同的字典时抛出异常
	 */
	public Dictionary addDictionary(String catalog, int dictId, String dictValue) throws DataContentRepeatException;

	/**
	 * 根据指定的属性，生成默认字典，然后添加（针对模式为Code－Value模式）
	 * 
	 * @param catalog　字典目录编码
	 * @param dictCode	字典编号
	 * @param dictValue 字典名
	 * @return 返回生成的字典对象
	 * @throws DataContentRepeatException	该类别下已存在Code相同的字典时抛出异常
	 */
	public Dictionary addDictionary(String catalog, String dictCode, String dictValue) throws DataContentRepeatException;
	
	/**
	 * 根据指定的属性，生成默认字典，然后添加（针对模式为Value模式）
	 * 
	 * @param catalog　字典目录编码
	 * @param dictValue 字典名
	 * @return 返回生成的字典对象
	 * @throws DataContentRepeatException	该类别下已存在Value相同的字典时抛出异常
	 */
	public Dictionary addDictionary(String catalog, String dictValue) throws DataContentRepeatException;

	/**
	 * 修改给定ID的字典对象的字典值
	 * 
	 * @param id 
	 * @param dictValue
	 * @throws DataNotExistException 如果给定ID不存在，则抛出异常
	 */
	public void modifyDictionary(long id, String dictValue) throws DataNotExistException;
	
	/**
	 * 删除给定ID的字典对象
	 * 
	 * @param id
	 * @throws DataHaveUsedException 在删除以前，将检查当前字典数据是否被使用，如果已经被使用，则抛出异常
	 */
	public void removeDictionary(long id) throws DataHaveUsedException, DataNotExistException;
	
	/**
	 * 调整字典排序顺序
	 * 
	 * @param id 要排序的字典ID
	 * @param order 排序方向（如果给定负值，则向前排序；否则向后排序。根据数字值，调整调整的步值。直至到头或者到尾）
	 * @throws DataNotExistException 如果给定ID不存在，则抛出异常
	 */
	public void adjustDictionaryOrder(long id, int order) throws DataNotExistException;
}
