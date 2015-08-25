package com.lily.dap.service.common.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.common.Menu;
import com.lily.dap.service.common.MenuManager;
import com.lily.dap.service.core.BaseManager;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.util.EntityOrderUtil;

/**
 * MenuManagerImpl.java
 * 
 * <br>Menu服务实现类，实现Menu实体对象的CRUD操作
 *
 * <br>版权所有：东软百合软件开发有限公司
 */
@Service("menuManager")
public class MenuManagerImpl extends BaseManager implements MenuManager {
    private EntityOrderUtil entityOrderUtil;
    
    @PostConstruct
    public void init() {
    	entityOrderUtil = new EntityOrderUtil(dao);
    }
    
	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.MenuManager#get(long)
	 */
	public Menu get(long id) throws DataNotExistException {
		return get(Menu.class, id);
	}
	
	public Menu get(String uid) throws DataNotExistException {
		return get(Menu.class, "uid", uid);
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.MenuManager#gets(com.lily.dap.dao.Condition)
	 */
	public List<Menu> gets(Condition cond) {
		return query(Menu.class, cond);
	}
	
	public Menu createMenu(String loadCode, long parentId) {
		Menu menu = new Menu();
		
		if (parentId != 0) {
			Menu parent = get(parentId);
			
			menu.setLoadCode(parent.getLoadCode());
			menu.setParentUid(parent.getUid());
		} else
			menu.setLoadCode(loadCode);
		
		return menu;
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.common.MenuManager#addMenu(com.lily.dap.entity.common.Menu)
	 */
	public Menu addMenu(Menu menu) {
		if (menu.getParentUid() != null && !"".equals(menu.getParentUid()) && !"0".equals(menu.getParentUid())) {
			Menu parent = get(menu.getParentUid());
			menu.setLoadCode(parent.getLoadCode());
		}
			
		int sn = entityOrderUtil.getEntityMaxOrder(Menu.class, "sn", new Condition().eq("loadCode", menu.getLoadCode()).eq("parentUid", menu.getParentUid()));
		menu.setSn(sn + 1);
		
		String uid = RandomStringUtils.randomAlphabetic(20).toLowerCase();
		menu.setUid(uid);
		
		return saveOrUpdate(menu);
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.common.MenuManager#gets(java.lang.String, String)
	 */
	public List<Menu> gets(String loadCode, String parentUid) {
		Condition cond = new Condition();
		if (loadCode != null && !"".equals(loadCode))
			cond.eq("loadCode", loadCode);
		cond.eq("parentUid", parentUid);
		cond.asc("sn");
		
		return gets(cond);
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.common.MenuManager#loadMenuTree(java.lang.String, String)
	 */
	public List<Menu> loadMenuTree(String loadCode, String parentUid) {
		List<Menu> list = gets(loadCode, parentUid);
		for (Menu menu : list) {
			loadMenuTree(menu);
		}
		
		return list;
	}

	private void loadMenuTree(Menu menu) {
		List<Menu> childs = gets(menu.getLoadCode(), menu.getUid());
		if (childs.size() > 0) {
			for (Menu child : childs) {
				menu.addChildMenu(child);
				
				loadMenuTree(child);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.lily.dap.service.entityOrdermodel.FieldDefinitionManager#adjustFieldOrder(long, int)
	 */
	public void adjustMenuOrder(long id, int order)
			throws DataNotExistException {
		Menu menu = get(id);
		
		entityOrderUtil.adjustEntityOrder(Menu.class, id, order, "sn", new Condition().eq("loadCode", menu.getLoadCode()).eq("parentUid", menu.getParentUid()));
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.common.MenuManager#modifyMenu(com.lily.dap.entity.common.Menu)
	 */
	public void modifyMenu(Menu menu) throws DataNotExistException {
		saveOrUpdate(menu);
	}

	/* （非 Javadoc）
	 * @see com.lily.dap.service.common.MenuManager#removeMenu(long)
	 */
	synchronized public void removeMenu(long id) throws DataNotExistException {
		Menu menu = get(id);
		
		List<Long> idList = new ArrayList<Long>();
		idList.add(id);
		
		searchMenuTreeIds(idList, menu);

		batchRemove(Menu.class, idList.toArray(new Long[0]));
	}
	
	private void searchMenuTreeIds(List<Long> idList, Menu menu) {
		List<Menu> childs = gets(menu.getLoadCode(), menu.getUid());
		if (childs.size() > 0) {
			for (Menu child : childs) {
				idList.add(child.getId());
				
				searchMenuTreeIds(idList, child);
			}
		}
	}
}
