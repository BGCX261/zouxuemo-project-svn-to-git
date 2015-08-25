package com.lily.dap.service.common;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.lily.dap.dao.Dao;
import com.lily.dap.entity.common.Menu;
import com.lily.dap.test.ServiceTest;

@ContextConfiguration
public class MenuManagerTest extends ServiceTest {
	@Autowired
	private MenuManager menuManager;
	
	@Autowired
	private Dao dao;
	
	@Before
	public void setUp() throws Exception {
		executeSqlScript("classpath:/com/lily/dap/service/common/MenuManagerTest-data.sql", false);
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testAddMenu() {
		Menu menu = new Menu();
		menu.setParentCode("main");
		menu = menuManager.addMenu(menu);
		Assert.assertEquals(1, menu.getSn());
		
		Menu child = new Menu();
		child.setParentCode(menu.getLevelCode());
		child = menuManager.addMenu(child);
		Assert.assertEquals(1, child.getSn());
		
		Menu child2 = new Menu();
		child2.setParentCode(child.getLevelCode());
		child2 = menuManager.addMenu(child2);
		Assert.assertEquals(1, child2.getSn());
		
		menu = new Menu();
		menu.setParentCode("main");
		menu = menuManager.addMenu(menu);
		Assert.assertEquals(2, menu.getSn());
		
		child = new Menu();
		child.setParentCode(menu.getLevelCode());
		child = menuManager.addMenu(child);
		Assert.assertEquals(1, child.getSn());
		
		child = new Menu();
		child.setParentCode(menu.getLevelCode());
		child = menuManager.addMenu(child);
		Assert.assertEquals(2, child.getSn());
	
		dao.getSession().flush();
		
		List<Menu> list = menuManager.loadMenuTree("main");
		Assert.assertEquals(2, list.size());
	
		System.out.println("************* menu begin *******************");
		printMenu(list);
		System.out.println("************* menu end *******************");

		dao.getSession().clear();

		menuManager.adjustMenuOrder(menu.getId(), -1);
		menuManager.adjustMenuOrder(child.getId(), -1);
		
		list = menuManager.loadMenuTree("main");
		Assert.assertEquals(2, list.size());
	
		System.out.println("************* menu begin *******************");
		printMenu(list);
		System.out.println("************* menu end *******************");

		dao.getSession().clear();

		menuManager.removeMenu(menu.getId());
		
		list = menuManager.loadMenuTree("main");
	
		System.out.println("************* menu begin *******************");
		printMenu(list);
		System.out.println("************* menu end *******************");
	}
	
	@Test
	public void testCopyMenu() {
		long id1, id2;
		Menu menu = new Menu();
		menu.setParentCode("main");
		menu.setText("菜单1");
		menu = menuManager.addMenu(menu);
		Assert.assertEquals(1, menu.getSn());
		
		id1 = menu.getId();
		
		Menu child = new Menu();
		child.setParentCode(menu.getLevelCode());
		child.setText("菜单2");
		child = menuManager.addMenu(child);
		Assert.assertEquals(1, child.getSn());
		
		Menu child2 = new Menu();
		child2.setParentCode(child.getLevelCode());
		child2.setText("菜单3");
		child2 = menuManager.addMenu(child2);
		Assert.assertEquals(1, child2.getSn());
		
		menu = new Menu();
		menu.setParentCode("main");
		menu.setText("菜单4");
		menu = menuManager.addMenu(menu);
		Assert.assertEquals(2, menu.getSn());
		
		id2 = menu.getId();
		
		child = new Menu();
		child.setParentCode(menu.getLevelCode());
		child.setText("菜单5");
		child = menuManager.addMenu(child);
		Assert.assertEquals(1, child.getSn());
		
		child = new Menu();
		child.setParentCode(menu.getLevelCode());
		child.setText("菜单6");
		child = menuManager.addMenu(child);
		Assert.assertEquals(2, child.getSn());
		
		menuManager.copyMenu(new long[]{id1}, id2, false);
		
		dao.getSession().clear();
		
		List<Menu> list = menuManager.loadMenuTree("main");
		
		System.out.println("************* menu begin *******************");
		printMenu(list);
		System.out.println("************* menu end *******************");
		
		dao.getSession().clear();
		
		menuManager.copyMenu(new long[]{id1}, id2, true);
		
		dao.getSession().flush();
		dao.getSession().clear();
		
		list = menuManager.loadMenuTree("main");
		
		System.out.println("************* menu begin *******************");
		printMenu(list);
		System.out.println("************* menu end *******************");
	}
	
	private void printMenu(List<Menu> list) {
		for (Menu m : list) {
			System.out.print(StringUtils.repeat(" ", m.getLevelCode().length() - 4));
			System.out.println(m.getParentCode() + "->" + m.getLevelCode() + " " + m.getText());
			
			if (!m.getChilds().isEmpty())
				printMenu(m.getChilds());
		}
	}
}
