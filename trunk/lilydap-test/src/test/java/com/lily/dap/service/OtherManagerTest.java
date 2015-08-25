package com.lily.dap.service;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

import com.lily.dap.dao.Condition;
import com.lily.dap.dao.Dao;
import com.lily.dap.entity.demo.Module;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.test.ServiceTest;

@ContextConfiguration(locations = { 
		"/applicationContext-resources-other.xml", 
		"/applicationContext-dao-other.xml",
		"/com/lily/dap/service/OtherManagerTest-context.xml"})
public class OtherManagerTest extends ServiceTest {
	@Autowired
	private OtherManagerImpl manager;
	
	@Autowired
	@Qualifier("dao")
	private Dao dao;
	
	@Autowired
	@Qualifier("otherDao")
	private Dao otherDao;
	
	/**
	 * 方法注释
	 *
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		dao.batchRemove(Module.class, Condition.create());
		otherDao.batchRemove(Module.class, Condition.create());
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
		manager.switchThisDataSource();
		
		try {
			manager.get(Module.class, 999999999);
			
			fail("期待抛出DataNotExistException异常失败！");
		} catch (DataNotExistException e) {}
		
		Module module = new Module();
		module.setCode("code");
		module.setCode("name");
		module.setNum(1001.1);
		module.setCnt(123);
		
		manager.saveOrUpdate(module);
		Assert.assertEquals(1, dao.count(Module.class, Condition.create()));
		
		module.setCnt(100);
		manager.saveOrUpdate(module);
		dao.flush();
		Assert.assertEquals(1, manager.count(Module.class, Condition.create().eq("cnt", 100)));
		
		manager.remove(Module.class, module.getId());
		dao.flush();
		Assert.assertEquals(0, dao.count(Module.class, Condition.create()));
	}
	
	@Test
	public void testOtherCrud() {
		manager.switchOtherDataSource();
		
		try {
			manager.get(Module.class, 999999999);
			
			fail("期待抛出DataNotExistException异常失败！");
		} catch (DataNotExistException e) {}
		
		Module module = new Module();
		module.setCode("code");
		module.setCode("name");
		module.setNum(1001.1);
		module.setCnt(123);
		
		manager.saveOrUpdate(module);
		Assert.assertEquals(1, otherDao.count(Module.class, Condition.create()));
		
		module.setCnt(100);
		manager.saveOrUpdate(module);
		dao.flush();
		Assert.assertEquals(1, manager.count(Module.class, Condition.create().eq("cnt", 100)));
		
		manager.remove(Module.class, module.getId());
		dao.flush();
		Assert.assertEquals(0, otherDao.count(Module.class, Condition.create()));
	}

	@Test
	public void testStat() {
		manager.switchThisDataSource();
		
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
		
		Assert.assertEquals(2, dao.count(Module.class, Condition.create()));
		
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
			
			fail("期待manager.sum方法抛出异常");
		} catch (Exception e) {}
	}

	@Test
	public void testOtherStat() {
		manager.switchOtherDataSource();
		
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
		
		Assert.assertEquals(2, otherDao.count(Module.class, Condition.create()));
		
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
			
			fail("期待manager.sum方法抛出异常");
		} catch (Exception e) {}
	}
}
