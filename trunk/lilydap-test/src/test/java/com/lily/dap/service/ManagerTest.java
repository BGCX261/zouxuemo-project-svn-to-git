package com.lily.dap.service;

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
public class ManagerTest extends ServiceTest {
	@Autowired
	private Manager manager;
	
	@Autowired
	private Dao dao;
	
	/**
	 * ����ע��
	 *
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		deleteFromTables("module");
	}
	
	/**
	 * ����ע��
	 *
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testCrud() {
		try {
			manager.get(Module.class, 999999999);
			
			fail("�ڴ��׳�DataNotExistException�쳣ʧ�ܣ�");
		} catch (DataNotExistException e) {}
		
		Module module = new Module();
		module.setCode("code");
		module.setCode("name");
		module.setNum(1001.1);
		module.setCnt(123);
		
		manager.saveOrUpdate(module);
		Assert.assertEquals(1, countRowsInTable("module"));
		
		module.setCnt(100);
		manager.saveOrUpdate(module);
		dao.flush();
		Assert.assertEquals(1, manager.count(Module.class, Condition.create().eq("cnt", 100)));
		
		manager.remove(Module.class, module.getId());
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
		manager.saveOrUpdate(module);
		
		module = new Module();
		module.setCode("code2");
		module.setName("name2");
		module.setNum(2002.2);
		module.setCnt(321);
		manager.saveOrUpdate(module);
		dao.flush();
		
		Assert.assertEquals(2, countRowsInTable("module"));
		
		Assert.assertEquals(3003.3d, manager.sum(Module.class, "num", null));
		Assert.assertEquals(444l, manager.sum(Module.class, "cnt", null));

		Assert.assertEquals(1, manager.count(Module.class, Condition.create().eq("code", "code")));
		
		Assert.assertEquals(1001.1d, manager.sum(Module.class, "num", Condition.create().eq("code", "code")));
		Assert.assertEquals(321l, manager.sum(Module.class, "cnt", Condition.create().eq("code", "code2")));
		
		Assert.assertEquals(2002.2d, manager.max(Module.class, "num", null));
		Assert.assertEquals(1001.1d, manager.min(Module.class, "num", null));
		
		Assert.assertEquals(321, manager.max(Module.class, "cnt", null));
		Assert.assertEquals(123, manager.min(Module.class, "cnt", null));

		Assert.assertEquals(222d, manager.avg(Module.class, "cnt", null));
		Assert.assertEquals(321d, manager.avg(Module.class, "cnt", Condition.create().eq("code", "code2")));
		
		Assert.assertEquals("code2", manager.max(Module.class, "code", null));
		Assert.assertEquals("code", manager.min(Module.class, "code", null));
		
		try {
			manager.sum(Module.class, "code", null);
			
			fail("�ڴ�manager.sum�����׳��쳣");
		} catch (Exception e) {}
	}
}
