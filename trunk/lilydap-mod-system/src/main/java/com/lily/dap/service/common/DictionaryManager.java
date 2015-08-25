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
 * <h1>�ֵ����ӿ�</h1>
 * 
 * @author ��ѧģ
 *
 */
public interface DictionaryManager {
	/**
	 * ��ȡָ�����ֵ����
	 * 
	 * @param id	-	�ֵ����id
	 * @return	�ֵ����	
	 * @throws DataNotExistException	-	�ֵ����δ�ҵ�
	 */
	public DictClassify getDictClassify(long id)  throws DataNotExistException;
	
	/**
	 * ��ȡ�����ֵ����
	 * 
	 * @return���ֵ�����б���δ�ҵ��κη��࣬���б�Ϊ�ա����б��Ѹ����ֵ�Ŀ¼���������
	 */
	public List<DictClassify> getDictClassifys();
	
	/**
	 * ��ȡָ���ֵ�Ŀ¼�����µ�ָ���ֵ�Ŀ¼
	 * 
	 * @param id	-	�ֵ�Ŀ¼id
	 * @return	��ȡ���ֵ�Ŀ¼
	 * @throws DataNotExistException	�ֵ�Ŀ¼δ�ҵ�
	 */
	public DictCatalog getDictCatalog(long id) throws DataNotExistException;
	
	/**
	 * ��ȡָ����ŵ��ֵ�Ŀ¼
	 * 
	 * @param code	-	�ֵ�Ŀ¼���
	 * @return	��ȡ���ֵ�Ŀ¼
	 * @throws DataNotExistException	�ֵ�Ŀ¼δ�ҵ�
	 */
	public DictCatalog getDictCatalog(String code) throws DataNotExistException;
	
	/**
	 * ��ȡָ���ֵ�Ŀ¼�����µ�ָ���༭״̬�����ֵ�Ŀ¼
	 * 
	 * @param classify - �ֵ�Ŀ¼������
	 * @param onlyEditFlag - �Ƿ�ֻ��������༭���ֵ�Ŀ¼
	 * @return	�����÷����������ֵ�Ŀ¼���б����˷�������Ŀ¼���򷵻ؿ��б�;���б��Ѹ����ֵ�Ŀ¼���������
	 */
	public List<DictCatalog> getDictCatalogs(String classify, boolean onlyEditFlag);
	
	/**
	 * ���ݸ���ID��ȡ�ֵ�
	 * 
	 * @param id
	 * @return
	 * @throws DataNotExistException ����Ҳ����ֵ䣬�׳��쳣
	 */
	public Dictionary getDictionary(long id) throws DataNotExistException;
	
	/**
	 * ����ָ���ֵ�Ŀ¼���ֵ�ɣĻ�ȡ�ֵ�
	 * 
	 * @param catalog  -	�ֵ�Ŀ¼���
	 * @param dictId	-	�ֵ�id
	 * @return Dictionary  �ֵ�
	 * @throws DataNotExistException	-	�Ҳ����ֵ�
	 */
	public Dictionary getDictionary(String catalog, int dictId) throws DataNotExistException;
	
	/**
	 * ����ָ���ֵ�Ŀ¼���ֵ�����ȡ�ֵ�
	 * 
	 * @param catalog �ֵ�Ŀ¼���
	 * @param dictCode	�ֵ���
	 * @return Dictionary	�ֵ�
	 * @throws DataNotExistException	�Ҳ����ֵ�
	 */
	public Dictionary getDictionary(String catalog, String dictCode) throws DataNotExistException;
	
	/**
	 * ��ȡ����������ֵ��б������б���
	 * 
	 * @param catalogCode Ҫ�������ֵ����
	 * @param onlyShow �Ƿ�ֻ��������ʾ���ֵ���Ϣ
	 * @param onlyEnable �Ƿ�ֻ����ʹ��״̬���ֵ���Ϣ
	 * @return �����ֵ伯��List{Dictionary}
	 * @throws DataNotExistException �����ֵ���಻�����쳣
	 */
	public List<Dictionary> getDictionaries(String catalogCode, boolean onlyShow);
	
	/**
	 * ����ָ���ֵ�Ŀ¼���ֵ�ֵ��ȡ�ֵ䣬����ж����ͬ���ֵ�ֵ�����ص�һ���ֵ��¼
	 * 
	 * @param catalog �ֵ�Ŀ¼���
	 * @param dictValue	�ֵ�ֵ
	 * @return Dictionary	�ֵ�
	 * @throws DataNotExistException	�Ҳ����ֵ�
	 */
	public Dictionary getDictionaryExt(String catalog, String dictValue) throws DataNotExistException;
	

	/**
	 * ��ȡָ��Ŀ¼���ֵ䣬Ҫ���ֵ������Code��Valueģʽ�ģ�����Code�����ڸ����ĳ��ȷ�Χ��
	 *
	 * @param catalog
	 * @param onlyShowFlag
	 * @param dictCodeMinLength Code��С���ȣ�������޶���������Ϊ-1
	 * @param dictCodeMaxLength Code��󳤶ȣ�������޶���������Ϊ-1
	 * @return
	 */
	public List<Dictionary> getDictionaries(String catalog, boolean onlyShowFlag, int dictCodeMinLength, int dictCodeMaxLength);
	
	/**
	 * ��ȡָ��Ŀ¼���ֵ䣬Ҫ���ֵ������Code��Valueģʽ�ģ�����Code�����Ը������ַ�����ͷ
	 *
	 * @param catalog
	 * @param onlyShowFlag
	 * @param dictCodeHead Ҫ�趨��Code��ͷ�ַ���
	 * @return
	 */
	public List<Dictionary> getDictionaries(String catalog, boolean onlyShowFlag, String dictCodeHead);
	
	/**
	 * ��ȡָ��Ŀ¼��ָ����ŵ��ֵ䣬Ҫ���ֵ������Code��Valueģʽ
	 *
	 * @param catalog
	 * @param onlyShowFlag
	 * @param filter Ҫ���ؼ�¼���ֵ�Code���飬ֻ���ֵ�Code���ڸ����������ֵ���ֵ��¼�ŷ���
	 * @return
	 */
	public List<Dictionary> getDictionaries(String catalog, boolean onlyShowFlag, String[] filter);
	
	/**
	 * ��ȡָ��Ŀ¼��ָ��ID���ֵ䣬Ҫ���ֵ������ID��Valueģʽ
	 *
	 * @param catalog
	 * @param onlyShowFlag
	 * @param filter Ҫ���ؼ�¼���ֵ�ID���飬ֻ���ֵ�ID���ڸ����������ֵ���ֵ��¼�ŷ���
	 * @return
	 */
	public List<Dictionary> getDictionaries(String catalog, boolean onlyShowFlag, int[] filter);
	
	/**
	 * ����ָ�������ԣ�����Ĭ���ֵ䣬Ȼ����ӣ��Զ��жϵ�ǰ�ֵ�ģʽ��
	 * 
	 * @param catalog���ֵ�Ŀ¼����
	 * @param dictId	�ֵ�id
	 * @param dictCode	�ֵ���
	 * @param dictValue �ֵ���
	 * @return �������ɵ��ֵ����
	 * @throws DataContentRepeatException	��������Ѵ���ID��ͬ������Code��ͬ���ֵ�ʱ�׳��쳣
	 */
	public Dictionary addDictionary(String catalog, int dictId, String dictCode, String dictValue) throws DataContentRepeatException;
	
	/**
	 * ����ָ�������ԣ�����Ĭ���ֵ䣬Ȼ����ӣ����ģʽΪID��Valueģʽ��
	 * 
	 * @param catalog���ֵ�Ŀ¼����
	 * @param dictId	�ֵ�id
	 * @param dictValue �ֵ���
	 * @return �������ɵ��ֵ����
	 * @throws DataContentRepeatException	��������Ѵ���ID��ͬ���ֵ�ʱ�׳��쳣
	 */
	public Dictionary addDictionary(String catalog, int dictId, String dictValue) throws DataContentRepeatException;

	/**
	 * ����ָ�������ԣ�����Ĭ���ֵ䣬Ȼ����ӣ����ģʽΪCode��Valueģʽ��
	 * 
	 * @param catalog���ֵ�Ŀ¼����
	 * @param dictCode	�ֵ���
	 * @param dictValue �ֵ���
	 * @return �������ɵ��ֵ����
	 * @throws DataContentRepeatException	��������Ѵ���Code��ͬ���ֵ�ʱ�׳��쳣
	 */
	public Dictionary addDictionary(String catalog, String dictCode, String dictValue) throws DataContentRepeatException;
	
	/**
	 * ����ָ�������ԣ�����Ĭ���ֵ䣬Ȼ����ӣ����ģʽΪValueģʽ��
	 * 
	 * @param catalog���ֵ�Ŀ¼����
	 * @param dictValue �ֵ���
	 * @return �������ɵ��ֵ����
	 * @throws DataContentRepeatException	��������Ѵ���Value��ͬ���ֵ�ʱ�׳��쳣
	 */
	public Dictionary addDictionary(String catalog, String dictValue) throws DataContentRepeatException;

	/**
	 * �޸ĸ���ID���ֵ������ֵ�ֵ
	 * 
	 * @param id 
	 * @param dictValue
	 * @throws DataNotExistException �������ID�����ڣ����׳��쳣
	 */
	public void modifyDictionary(long id, String dictValue) throws DataNotExistException;
	
	/**
	 * ɾ������ID���ֵ����
	 * 
	 * @param id
	 * @throws DataHaveUsedException ��ɾ����ǰ������鵱ǰ�ֵ������Ƿ�ʹ�ã�����Ѿ���ʹ�ã����׳��쳣
	 */
	public void removeDictionary(long id) throws DataHaveUsedException, DataNotExistException;
	
	/**
	 * �����ֵ�����˳��
	 * 
	 * @param id Ҫ������ֵ�ID
	 * @param order ���������������ֵ������ǰ���򣻷���������򡣸�������ֵ�����������Ĳ�ֵ��ֱ����ͷ���ߵ�β��
	 * @throws DataNotExistException �������ID�����ڣ����׳��쳣
	 */
	public void adjustDictionaryOrder(long id, int order) throws DataNotExistException;
}
