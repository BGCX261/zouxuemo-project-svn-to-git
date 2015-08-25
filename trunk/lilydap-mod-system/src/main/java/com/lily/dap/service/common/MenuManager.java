package com.lily.dap.service.common;

import java.util.List;

import com.lily.dap.entity.common.Menu;
import com.lily.dap.service.Manager;
import com.lily.dap.service.exception.DataNotExistException;

/**
 * MenuManager.java
 * 
 * <br>Menu������ӿڣ�����Menuʵ������CRUD����
 * <br>��Manager�ӿ����Ѿ������˻�����CRUD�����ӿڣ��ڱ��������ж��������Ĳ����ӿ�
 *
 * <br>��Ȩ���У�����ٺ�����������޹�˾
 */
public interface MenuManager extends Manager {
	/**
	 * ��������ID��Menu
	 * 
	 * @param id
	 * @return
	 * @throws DataNotExistException ���δ�ҵ����׳��쳣
	 */
	public Menu get(long id) throws DataNotExistException;

	/**
	 * ���������㼶����Ĳ˵�
	 * 
	 * @param levelCode
	 * @return
	 * @throws DataNotExistException ���δ�ҵ����׳��쳣
	 */
	public Menu get(String levelCode) throws DataNotExistException;
	
	/**
	 * �����������㼶�����µĲ˵��б�
	 *
	 * @param parentCode
	 * @return
	 */
	public List<Menu> gets(String parentCode);
	
	/**
	 * ���ظ������㼶�����µĲ˵��б�����������ÿ���˵����Ӳ˵�������Ϊ�˵���childsֵ
	 *
	 * @param parentCode
	 * @return
	 */
	public List<Menu> loadMenuTree(String parentCode);
	
	/**
	 * ��Ӳ˵�
	 *
	 * @param menu
	 * @return
	 */
	public Menu addMenu(Menu menu);
	
	/**
	 * �����˵�������˳��
	 * 
	 * @param id Ҫ����Ĳ˵�ID
	 * @param order ���������������ֵ������ǰ���򣻷���������򡣸�������ֵ�����������Ĳ�ֵ��ֱ����ͷ���ߵ�β��
	 * @throws DataNotExistException �������ID�����ڣ����׳��쳣
	 */
	public void adjustMenuOrder(long id, int order) throws DataNotExistException;
	
	/**
	 * �޸Ĳ˵�
	 *
	 * @param menu
	 * @throws DataNotExistException ����޸Ĳ˵�δ�ҵ����׳��쳣
	 */
	public void modifyMenu(Menu menu) throws DataNotExistException;
	
	/**
	 * ɾ���˵���������Ӳ˵���ͬʱɾ���Ӳ˵���
	 *
	 * @param id
	 * @throws DataNotExistException ���ɾ���˵�δ�ҵ����׳��쳣
	 */
	public void removeMenu(long id) throws DataNotExistException;
	
	/**
	 * ���ƻ����ƶ�һ���˵�������������Ӳ˵����������˵�����Ϊ���Ӳ˵�
	 * 
	 * @param ids Ҫ���ƻ����ƶ��Ĳ˵�ID����
	 * @param toParentCode Ҫ���ƻ����ƶ����ĸ��˵��㼶����
	 * @param deleteOrginMenu �Ƿ�ɾ��ԭ�в˵�
	 * @throws DataNotExistException
	 */
	public void copyMenu(long[] ids, String toParentCode, boolean deleteOrginMenu) throws DataNotExistException;
	
	/**
	 * ���ƻ����ƶ�һ���˵�������������Ӳ˵����������˵�����Ϊ���Ӳ˵�
	 * 
	 * @param ids Ҫ���ƻ����ƶ��Ĳ˵�ID����
	 * @param toParentId Ҫ���ƻ����ƶ����ĸ��˵�ID
	 * @param deleteOrginMenu �Ƿ�ɾ��ԭ�в˵�
	 * @throws DataNotExistException
	 */
	public void copyMenu(long[] ids, long toParentId, boolean deleteOrginMenu) throws DataNotExistException;
}
