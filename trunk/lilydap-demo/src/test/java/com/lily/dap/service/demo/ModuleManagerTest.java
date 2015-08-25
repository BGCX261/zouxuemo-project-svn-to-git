package com.lily.dap.service.demo;


import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.lily.dap.dao.Condition;
import com.lily.dap.dao.Dao;
import com.lily.dap.entity.demo.Module;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.test.ServiceTest;

@ContextConfiguration
public class ModuleManagerTest extends ServiceTest {
	@Autowired
	private ModuleManager moduleManager;
	
	@Autowired
	private Dao dao;
	
	/**
	 * 方法注释
	 *
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		deleteFromTables("module");
	}
	
	/**
	 * 方法注释
	 *
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCrud() {
		try {
			moduleManager.get(999999999);
			
			fail("期待抛出DataNotExistException异常失败！");
		} catch (DataNotExistException e) {}
		
		Module module = new Module();
		module.setCode("code");
		module.setCode("name");
		module.setNum(1001.1);
		module.setCnt(123);
		
		moduleManager.saveOrUpdate(module);
		Assert.assertEquals(1, countRowsInTable("module"));
		
		module.setCnt(100);
		moduleManager.saveOrUpdate(module);
		dao.flush();
		Assert.assertEquals(1, moduleManager.count(Condition.create().eq("cnt", 100)));
		
		moduleManager.remove(module.getId());
		dao.flush();
		Assert.assertEquals(0, countRowsInTable("module"));
	}

//	@Test
//	public void testQuery() {
//		fail("Not yet implemented");
//	}

	@Test
	public void testStat() {
		Module module = new Module();
		module.setCode("code");
		module.setName("name");
		module.setNum(1001.1);
		module.setCnt(123);
		moduleManager.saveOrUpdate(module);
		
		module = new Module();
		module.setCode("code2");
		module.setName("name2");
		module.setNum(2002.2);
		module.setCnt(321);
		moduleManager.saveOrUpdate(module);
		dao.flush();
		
		Assert.assertEquals(2, countRowsInTable("module"));
		
		Assert.assertEquals(3003.3d, moduleManager.sum("num", null));
		Assert.assertEquals(444l, moduleManager.sum("cnt", null));

		Assert.assertEquals(1, moduleManager.count(Condition.create().eq("code", "code")));
		
		Assert.assertEquals(1001.1d, moduleManager.sum("num", Condition.create().eq("code", "code")));
		Assert.assertEquals(321l, moduleManager.sum("cnt", Condition.create().eq("code", "code2")));
		
		Assert.assertEquals(2002.2d, moduleManager.max("num", null));
		Assert.assertEquals(1001.1d, moduleManager.min("num", null));
		
		Assert.assertEquals(321, moduleManager.max("cnt", null));
		Assert.assertEquals(123, moduleManager.min("cnt", null));

		Assert.assertEquals(222d, moduleManager.avg("cnt", null));
		Assert.assertEquals(321d, moduleManager.avg("cnt", Condition.create().eq("code", "code2")));
		
		Assert.assertEquals("code2", moduleManager.max("code", null));
		Assert.assertEquals("code", moduleManager.min("code", null));
		
		try {
			moduleManager.sum("code", null);
			
			fail("期待moduleManager.sum方法抛出异常");
		} catch (Exception e) {}
	}
}
