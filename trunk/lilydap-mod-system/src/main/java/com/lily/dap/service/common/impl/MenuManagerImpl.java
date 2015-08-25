package com.lily.dap.service.common.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.ArrayStack;
import org.springframework.stereotype.Service;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.common.Menu;
import com.lily.dap.service.common.MenuManager;
import com.lily.dap.service.core.BaseManager;
import com.lily.dap.service.exception.DataContentInvalidateException;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.service.util.EntityOrderUtil;

/**
 * MenuManagerImpl.java
 * 
 * <br>Menu����ʵ���࣬ʵ��Menuʵ������CRUD����
 *
 * <br>��Ȩ���У�����ٺ�����������޹�˾
 */
@Service("menuManager")
public class MenuManagerImpl extends BaseManager implements MenuManager {
	private static int MENU_LEVEL_BIT = 3;
	private static String FORMAT_MENU_LEVEL_BIT = "%0" + MENU_LEVEL_BIT + "d";
	
	private MenuComparator menuComparator = new MenuComparator();
	
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
	
	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.MenuManager#get(java.lang.String)
	 */
	public Menu get(String levelCode) throws DataNotExistException {
		return get(Menu.class, "levelCode", levelCode);
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.common.MenuManager#gets(java.lang.String)
	 */
	public List<Menu> gets(String parentCode) {
		return query(Menu.class, Condition.create().eq("parentCode", parentCode).asc("sn"));
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.common.MenuManager#loadMenuTree(java.lang.String)
	 */
	public List<Menu> loadMenuTree(String parentCode) {
		List<Menu> result = new ArrayList<Menu>();
		
		List<Menu> list = query(Menu.class, Condition.create().llike("parentCode", parentCode).asc("levelCode"));
		
		int preLevelLength = 0;
		ArrayStack stack = new ArrayStack();
		
		for (Menu menu : list) {
			String levelCode = menu.getLevelCode();
			int levelLength = levelCode.length();
			
			if (levelLength > preLevelLength) {
				//ɶҲ����
			} else if (levelLength < preLevelLength) {
				//�˻�������ĸ��˵�
				int offset = (preLevelLength - levelLength) / MENU_LEVEL_BIT;
				while (offset-- >= 0) {
					Menu m = (Menu)stack.pop();

					if (!m.getChilds().isEmpty() && m.getChilds().size() >= 2)
						Collections.sort(m.getChilds(), menuComparator);
				}
			} else
				stack.pop();

			if (stack.isEmpty())
				result.add(menu);
			else
				((Menu)stack.peek()).addChildMenu(menu);
			
			preLevelLength = levelLength;
			stack.push(menu);
		}
		
		while(!stack.isEmpty()) {
			Menu m = (Menu)stack.pop();

			if (!m.getChilds().isEmpty() && m.getChilds().size() >= 2)
				Collections.sort(m.getChilds(), menuComparator);
		}
		
		Collections.sort(result, menuComparator);
		return result;
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.common.MenuManager#addMenu(com.lily.dap.entity.common.Menu)
	 */
	public Menu addMenu(Menu menu) {
		String parentCode = menu.getParentCode();
		if (parentCode == null || "".equals(parentCode)) {
			throw new DataContentInvalidateException("Ҫ��ӵĲ˵�δָ�����㼶���룬����������õĸ��㼶���룡");
		}
		
		int sn = entityOrderUtil.getEntityMaxOrder(Menu.class, "sn", new Condition().eq("parentCode", menu.getParentCode()));
		menu.setSn(sn + 1);
		
		int level = 1;
		
		String maxLevelCode = dao.max(Menu.class, "levelCode", Condition.create().eq("parentCode", menu.getParentCode()));
		if (maxLevelCode != null)
			level = Integer.parseInt(maxLevelCode.substring(maxLevelCode.length() - MENU_LEVEL_BIT)) + 1;
		
		menu.setLevelCode(parentCode + String.format(FORMAT_MENU_LEVEL_BIT, level));
			
		return saveOrUpdate(menu);
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.common.MenuManager#modifyMenu(com.lily.dap.entity.common.Menu)
	 */
	public void modifyMenu(Menu menu) throws DataNotExistException {
		saveOrUpdate(menu);
	}

	/* ���� Javadoc��
	 * @see com.lily.dap.service.common.MenuManager#removeMenu(long)
	 */
	public void removeMenu(long id) throws DataNotExistException {
		Menu menu = get(id);
		
		String levelCode = menu.getLevelCode();
		dao.batchRemove(Menu.class, new Condition().llike("levelCode", levelCode));
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.entityOrdermodel.FieldDefinitionManager#adjustFieldOrder(long, int)
	 */
	public void adjustMenuOrder(long id, int order)
			throws DataNotExistException {
		Menu menu = get(id);
		
		entityOrderUtil.adjustEntityOrder(Menu.class, id, order, "sn", new Condition().eq("parentCode", menu.getParentCode()));
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.MenuManager#copyMenu(long[], java.lang.String, boolean)
	 */
	public void copyMenu(long[] ids, String toParentCode, boolean deleteOrginMenu)
			throws DataNotExistException {
		if (ids == null || ids.length == 0)
			return;
		
		String parentCode = toParentCode;
		
		for (long id : ids) {
			//�����˵����ݺ��������Ӳ˵�����
			Menu menu = get(id);
			List<Menu> childs = query(Menu.class, Condition.create().llike("parentCode", menu.getLevelCode()).asc("levelCode"));

			//����Ǹ��Ʋ˵����ݣ���Ѽ����Ĳ˵����ݺ��������Ӳ˵�����cloneһ�ݣ�������idΪ0
			if (!deleteOrginMenu) {
				try {
					menu = menu.clone();
				} catch (CloneNotSupportedException e) {
					// ����ִ��
				}
				menu.setId(0);
				
				List<Menu> cloneChilds = new ArrayList<Menu>();
				for (Menu child : childs) {
					try {
						child = child.clone();
					} catch (CloneNotSupportedException e) {
						// ����ִ��
					}
					child.setId(0);
					
					cloneChilds.add(child);				}
					
				childs = cloneChilds;
			}
			
			int orgStrLen = menu.getLevelCode().length();

			//�޸Ĳ˵����ݵĸ��㼶���롢�㼶�����������Ϣ
			menu.setParentCode(parentCode);
			int sn = entityOrderUtil.getEntityMaxOrder(Menu.class, "sn", new Condition().eq("parentCode", parentCode));
			menu.setSn(sn + 1);
			
			int level = 1;
			
			String maxLevelCode = dao.max(Menu.class, "levelCode", Condition.create().eq("parentCode", parentCode));
			if (maxLevelCode != null)
				level = Integer.parseInt(maxLevelCode.substring(maxLevelCode.length() - MENU_LEVEL_BIT)) + 1;
			
			menu.setLevelCode(parentCode + String.format(FORMAT_MENU_LEVEL_BIT, level));
			
			String replaceStr = menu.getLevelCode();
			
			//�޸������Ӳ˵��ĸ��㼶����Ͳ㼶������Ϣ
			for (Menu child : childs) {
				child.setParentCode(replaceStr + child.getParentCode().substring(orgStrLen));
				child.setLevelCode(replaceStr + child.getLevelCode().substring(orgStrLen));
			}
			
			//���渴�ƻ��޸ĵĲ˵����ݺ��������Ӳ˵�����
			dao.saveOrUpdate(menu);
			for (Menu child : childs)
				dao.saveOrUpdate(child);
		}
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.common.MenuManager#copyMenu(long[], long, boolean)
	 */
	public void copyMenu(long[] ids, long toParentId, boolean deleteOrginMenu)
			throws DataNotExistException {
		Menu menu = get(toParentId);
		
		copyMenu(ids, menu.getLevelCode(), deleteOrginMenu);
	}
}

class MenuComparator implements Comparator<Menu> {
	public int compare(Menu o1, Menu o2) {
		return o1.getSn() - o2.getSn();
	}
}
