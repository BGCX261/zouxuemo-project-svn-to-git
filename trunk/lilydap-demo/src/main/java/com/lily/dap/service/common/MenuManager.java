package com.lily.dap.service.common;

import java.util.List;

import com.lily.dap.dao.Condition;
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
	 * 检索给定UID的Menu
	 * 
	 * @param uid
	 * @return
	 * @throws DataNotExistException 如果未找到，抛出异常
	 */
	public Menu get(String uid) throws DataNotExistException;

	/**
	 * 检索给定条件的Menu列表
	 * 
	 * @param cond
	 * @return
	 */
	public List<Menu> gets(Condition cond);
	
	/**
	 * 检索给定菜单下的子菜单列表
	 *
	 * @param loadCode
	 * @param parentUid
	 * @return
	 */
	public List<Menu> gets(String loadCode, String parentUid);
	
	/**
	 * 返回给定装载下的字典树列表
	 *
	 * @param loadCode
	 * @param parentUid
	 * @return
	 */
	public List<Menu> loadMenuTree(String loadCode, String parentUid);
	
	/**
	 * 创建一个给定loadCode的属于parentID下的子菜单，如果parentID为0，则创建第一级菜单
	 * 
	 * @param loadCode
	 * @param parentId
	 * @return
	 */
	public Menu createMenu(String loadCode, long parentId);
	
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
}
