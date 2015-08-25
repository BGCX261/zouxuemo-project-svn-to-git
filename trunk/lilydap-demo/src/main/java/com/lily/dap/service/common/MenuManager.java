package com.lily.dap.service.common;

import java.util.List;

import com.lily.dap.dao.Condition;
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
	 * ��������UID��Menu
	 * 
	 * @param uid
	 * @return
	 * @throws DataNotExistException ���δ�ҵ����׳��쳣
	 */
	public Menu get(String uid) throws DataNotExistException;

	/**
	 * ��������������Menu�б�
	 * 
	 * @param cond
	 * @return
	 */
	public List<Menu> gets(Condition cond);
	
	/**
	 * ���������˵��µ��Ӳ˵��б�
	 *
	 * @param loadCode
	 * @param parentUid
	 * @return
	 */
	public List<Menu> gets(String loadCode, String parentUid);
	
	/**
	 * ���ظ���װ���µ��ֵ����б�
	 *
	 * @param loadCode
	 * @param parentUid
	 * @return
	 */
	public List<Menu> loadMenuTree(String loadCode, String parentUid);
	
	/**
	 * ����һ������loadCode������parentID�µ��Ӳ˵������parentIDΪ0���򴴽���һ���˵�
	 * 
	 * @param loadCode
	 * @param parentId
	 * @return
	 */
	public Menu createMenu(String loadCode, long parentId);
	
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
}
