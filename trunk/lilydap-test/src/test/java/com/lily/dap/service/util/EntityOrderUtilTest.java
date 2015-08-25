package com.lily.dap.service.util;

import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lily.dap.dao.Condition;
import com.lily.dap.dao.Dao;
import com.lily.dap.entity.demo.Module;

@ContextConfiguration(locations = { 
		"/applicationContext-resources.xml", 
		"/applicationContext-dao.xml" })
public class EntityOrderUtilTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private Dao dao;
	
	private EntityOrderUtil entityOrderUtil;
	
	/**
	 * 方法注释
	 *
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		executeSqlScript("classpath:/com/lily/dap/service/util/EntityOrderUtilTest-data.sql", false);
		
		entityOrderUtil = new EntityOrderUtil(dao);
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
	public void testGetEntityMaxOrder() {
		Assert.assertEquals(0, entityOrderUtil.getEntityMaxOrder(Module.class, "cnt", new Condition().eq("code", "code0")));
		Assert.assertEquals(2, entityOrderUtil.getEntityMaxOrder(Module.class, "cnt", new Condition().eq("code", "code1")));
		Assert.assertEquals(5, entityOrderUtil.getEntityMaxOrder(Module.class, "cnt", new Condition().eq("code", "code2")));
		Assert.assertEquals(9, entityOrderUtil.getEntityMaxOrder(Module.class, "cnt", new Condition().eq("code", "code3")));
		Assert.assertEquals(10, entityOrderUtil.getEntityMaxOrder(Module.class, "cnt", new Condition().eq("code", "code4")));
		Assert.assertEquals(10, entityOrderUtil.getEntityMaxOrder(Module.class, "cnt", null));
		
		try {
			entityOrderUtil.getEntityMaxOrder(Module.class, "", new Condition().eq("code", "code0"));
			Assert.fail();
		} catch (Exception e) {}
	}	

	@Test
	public void testAdjustEntityOrder() {
		List<Module> modules = dao.query(Module.class, Condition.create().asc("code"));
		
		List<Module> adjustModules = entityOrderUtil.adjustEntityOrder(Module.class, modules.get(0).getId(), 1, "cnt", new Condition().eq("code", "code1"));
		Assert.assertEquals(2, adjustModules.size());
		
		adjustModules = entityOrderUtil.adjustEntityOrder(Module.class, modules.get(4).getId(), -5, "cnt", new Condition().eq("code", "code2"));
		Assert.assertEquals(3, adjustModules.size());
		
		adjustModules = entityOrderUtil.adjustEntityOrder(Module.class, modules.get(4).getId(), 0, "cnt", new Condition().eq("code", "code3"));
		Assert.assertNull(adjustModules);
		
		modules = dao.query(Module.class, Condition.create().asc("code"));
		Assert.assertEquals(2, modules.get(0).getCnt());
		Assert.assertEquals(1, modules.get(1).getCnt());
		Assert.assertEquals(4, modules.get(2).getCnt());
		Assert.assertEquals(5, modules.get(3).getCnt());
		Assert.assertEquals(3, modules.get(4).getCnt());
		
		//如果排序字段有重复的情况，结果不可预测
		adjustModules = entityOrderUtil.adjustEntityOrder(Module.class, modules.get(9).getId(), 4, "cnt", new Condition().eq("code", "code4"));
		Assert.assertEquals(4, adjustModules.size());
	}	

	@Test
	public void testOffsetOne() {
		List<Module> modules = dao.query(Module.class, Condition.create().asc("cnt"));
		Assert.assertEquals(2, entityOrderUtil.offsetOne(Module.class, modules.get(0).getId(), Condition.create().asc("cnt"), true).getCnt());
		Assert.assertEquals(1, entityOrderUtil.offsetOne(Module.class, modules.get(1).getId(), Condition.create().asc("cnt"), false).getCnt());
		try {
			entityOrderUtil.offsetOne(Module.class, modules.get(0).getId(), Condition.create().asc("cnt"), false);
			Assert.fail();
		} catch (Exception e) {}
		
		modules = dao.query(Module.class, Condition.create().desc("cnt"));
		Assert.assertEquals(2, entityOrderUtil.offsetOne(Module.class, modules.get(12).getId(), Condition.create().desc("cnt"), false).getCnt());
		Assert.assertEquals(1, entityOrderUtil.offsetOne(Module.class, modules.get(11).getId(), Condition.create().desc("cnt"), true).getCnt());
		try {
			entityOrderUtil.offsetOne(Module.class, modules.get(12).getId(), Condition.create().desc("cnt"), true);
			Assert.fail();
		} catch (Exception e) {}
		
		modules = dao.query(Module.class, Condition.create().eq("code", "code1").asc("cnt"));
		Assert.assertEquals(2, entityOrderUtil.offsetOne(Module.class, modules.get(0).getId(), Condition.create().eq("code", "code1").asc("cnt"), true).getCnt());
		try {
			entityOrderUtil.offsetOne(Module.class, modules.get(1).getId(), Condition.create().eq("code", "code1").asc("cnt"), true);
			Assert.fail();
		} catch (Exception e) {}	
	}	
}
