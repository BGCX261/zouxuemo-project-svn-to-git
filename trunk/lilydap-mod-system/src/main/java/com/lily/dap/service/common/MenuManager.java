package com.lily.dap.service.common;

import java.util.List;

import com.lily.dap.entity.common.Menu;
import com.lily.dap.service.Manager;
import com.lily.dap.service.exception.DataNotExistException;

/**
 * MenuManager.java
 * 
 * <br>Menu服务类接口，定义Menu实体对象的CRUD操作
 * <br>在Manager接口中已经定义了基本的CRUD操作接口，在本服务类中定义其他的操作接口
 *
 * <br>版权所有：东软百合软件开发有限公司
 */
public interface MenuManager extends Manager {
	/**
	 * 检索给定ID的Menu
	 * 
	 * @param id
	 * @return
	 * @throws DataNotExistException 如果未找到，抛出异常
	 */
	public Menu get(long id) throws DataNotExistException;

	/**
	 * 检索给定层级编码的菜单
	 * 
	 * @param levelCode
	 * @return
	 * @throws DataNotExistException 如果未找到，抛出异常
	 */
	public Menu get(String levelCode) throws DataNotExistException;
	
	/**
	 * 检索给定父层级编码下的菜单列表
	 *
	 * @param parentCode
	 * @return
	 */
	public List<Menu> gets(String parentCode);
	
	/**
	 * 返回给定父层级编码下的菜单列表，并遍历检索每个菜单的子菜单，设置为菜单的childs值
	 *
	 * @param parentCode
	 * @return
	 */
	public List<Menu> loadMenuTree(String parentCode);
	
	/**
	 * 添加菜单
	 *
	 * @param menu
	 * @return
	 */
	public Menu addMenu(Menu menu);
	
	/**
	 * 调整菜单的排序顺序
	 * 
	 * @param id 要排序的菜单ID
	 * @param order 排序方向（如果给定负值，则向前排序；否则向后排序。根据数字值，调整调整的步值。直至到头或者到尾）
	 * @throws DataNotExistException 如果给定ID不存在，则抛出异常
	 */
	public void adjustMenuOrder(long id, int order) throws DataNotExistException;
	
	/**
	 * 修改菜单
	 *
	 * @param menu
	 * @throws DataNotExistException 如果修改菜单未找到，抛出异常
	 */
	public void modifyMenu(Menu menu) throws DataNotExistException;
	
	/**
	 * 删除菜单（如果有子菜单，同时删除子菜单）
	 *
	 * @param id
	 * @throws DataNotExistException 如果删除菜单未找到，抛出异常
	 */
	public void removeMenu(long id) throws DataNotExistException;
	
	/**
	 * 复制或者移动一批菜单（及其包含的子菜单）至给定菜单下作为其子菜单
	 * 
	 * @param ids 要复制或者移动的菜单ID数组
	 * @param toParentCode 要复制或者移动至的父菜单层级编码
	 * @param deleteOrginMenu 是否删除原有菜单
	 * @throws DataNotExistException
	 */
	public void copyMenu(long[] ids, String toParentCode, boolean deleteOrginMenu) throws DataNotExistException;
	
	/**
	 * 复制或者移动一批菜单（及其包含的子菜单）至给定菜单下作为其子菜单
	 * 
	 * @param ids 要复制或者移动的菜单ID数组
	 * @param toParentId 要复制或者移动至的父菜单ID
	 * @param deleteOrginMenu 是否删除原有菜单
	 * @throws DataNotExistException
	 */
	public void copyMenu(long[] ids, long toParentId, boolean deleteOrginMenu) throws DataNotExistException;
}
