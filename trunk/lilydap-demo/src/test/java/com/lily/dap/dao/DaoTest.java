package com.lily.dap.dao;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lily.dap.entity.demo.Module;
import com.lily.dap.util.convert.ConvertUtils;

/**
 * <Dao测试类
 * 
 *
 * date: 2010-12-2
 * @author zouxuemo
 */
@ContextConfiguration(locations = { 
		"/applicationContext-resources.xml", 
		"/applicationContext-dao.xml" })
public class DaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private Dao dao;
	
	/**
	 * 方法注释
	 *
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
//		simpleJdbcTemplate.update("drop all objects");
//
		executeSqlScript("classpath:/com/lily/dap/dao/DaoTest-data.sql", false);
	}
	
	/**
	 * 方法注释
	 *
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.lily.dap.dao.hibernate.HibernateDao#get(java.lang.Class, java.io.Serializable)}.
	 */
	@Test
//	@Rollback(false)
	public void testGet() {
		long id = (Long)dao.unique("select id from Module where code = 'code'");
		Module module = dao.get(Module.class, id);
		Assert.assertEquals("code", module.getCode());
		
		List<Map<String, Object>> idList = dao.sqlQuery("select id from module");
		Serializable[] ids = new Serializable[idList.size()];
		for (int i = 0; i < idList.size(); i++) {
			ids[i] = (Serializable)idList.get(i).get("id");
		}
		
		List<Module> list = dao.gets(Module.class, ids);
		Assert.assertEquals(13, list.size());
		
		Assert.assertNull(dao.get(Module.class, 9999999999l));
	
		List<Module> list2 = dao.gets(Module.class, new Serializable[]{9999999999l});
		Assert.assertNotNull(list2);
		Assert.assertEquals(0, list2.size());
	}

	/**
	 * Test method for {@link com.lily.dap.dao.hibernate.HibernateDao#query(java.lang.Class, com.lily.dap.dao.Condition)}.
	 * @throws ParseException 
	 */
	@Test
	public void testQueryClassOfTCondition() throws ParseException {
		List<Module> list = dao.query(Module.class, Condition.create().between("createDate", ConvertUtils.convert("2010-01-01", Date.class), ConvertUtils.convert("2010-10-31", Date.class)).desc("createDate"));
		Assert.assertEquals(10, list.size());
		Assert.assertEquals("code10", list.get(0).getCode());
		
		list = dao.query(Module.class, Condition.create().lp().isnull("des").or().eq("code", "code1").rp().in("name", new Object[]{"name", "name1", "name2", "name3", "name4", "name5"}).desc("id"));
		Assert.assertEquals(2, list.size());
		Assert.assertEquals("code1", list.get(0).getCode());
		
		list = dao.query(Module.class, Condition.create().inquery("id", "select id from Module where code like 'code1%'").page(2, 3));
		Assert.assertEquals(1, list.size());
	}

	/**
	 * Test method for {@link com.lily.dap.dao.hibernate.HibernateDao#query(org.hibernate.criterion.DetachedCriteria, int, int)}.
	 */
	@Test
	public void testQueryDetachedCriteria() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Module.class);
		detachedCriteria.add(Restrictions.in("code", new Object[]{"code1", "code2"}));
		detachedCriteria.add(Restrictions.eq("name", "name2"));
		
		List<Module> result = dao.query(detachedCriteria);
		Assert.assertEquals(1, result.size());
	}

	/**
	 * Test method for {@link com.lily.dap.dao.hibernate.HibernateDao#query(java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testQueryString() {
		List<Module> result = dao.query("from Module where code in (?, ?)", "code1", "code2");
		Assert.assertEquals(2, result.size());
	}

	/**
	 * Test method for {@link com.lily.dap.dao.hibernate.HibernateDao#count(java.lang.Class, com.lily.dap.dao.Condition)}.
	 */
	@Test
	public void testCount() {
		Assert.assertEquals(13, dao.count(Module.class, null));
		
		Assert.assertEquals(4, dao.count(Module.class, Condition.create().inquery("id", "select id from Module where code like 'code1%'")));
	}

	/**
	 * Test method for {@link com.lily.dap.dao.hibernate.HibernateDao#sum(java.lang.Class, java.lang.String, com.lily.dap.dao.Condition)}.
	 */
	@Test
	public void testStat() {
		Assert.assertEquals(333.33, dao.sum(Module.class, "num", Condition.create().in("code", new Object[]{"code1", "code2"})));
		Assert.assertEquals(123456d, dao.max(Module.class, "num", null));
		Assert.assertEquals(111.11d, dao.min(Module.class, "num", null));
		Assert.assertEquals(222.22d, dao.avg(Module.class, "num", Condition.create().in("code", new Object[]{"code1", "code3"})));

		
		Assert.assertEquals(555l, dao.sum(Module.class, "cnt", Condition.create().in("code", new Object[]{"code1", "code2"})));
		Assert.assertEquals(4444, dao.max(Module.class, "cnt", null));
		Assert.assertEquals(111, dao.min(Module.class, "cnt", null));
		Assert.assertEquals(333d, dao.avg(Module.class, "cnt", Condition.create().in("code", new Object[]{"code1", "code3"})));
	}

	/**
	 * Test method for {@link com.lily.dap.dao.hibernate.HibernateDao#unique(java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testUnique() {
		Assert.assertEquals(new Long(13), dao.unique("select count(*) from Module"));
		
		Assert.assertEquals(new Long(13), dao.unique("select count(*) from Module where code like ?", "code%"));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", "code2");
		Assert.assertEquals("name2", dao.unique("select name from Module where code = :code", map));
		
		map.put("num", 123456d);
		Assert.assertEquals("name", dao.unique("select name from Module where num = :num", map));
	}

	/**
	 * Test method for {@link com.lily.dap.dao.hibernate.HibernateDao#saveOrUpdate(com.lily.dap.entity.BaseEntity)}.
	 */
	@Test
	public void testSaveOrUpdate() {
		int count = countRowsInTable("module");
		
		Module module = new Module();
		module.setCode("code1");
		module.setName("name1");
		module.setDes("des1");
		
		dao.saveOrUpdate(module);
		Assert.assertTrue(module.getId() > 0);
		
		module.setCode("modify code");
		module.setName("修改的名称");
		dao.saveOrUpdate(module);
		
		dao.flush();
		Assert.assertEquals(count + 1, countRowsInTable("module"));
	}

	/**
	 * Test method for {@link com.lily.dap.dao.hibernate.HibernateDao#batchSaveOrUpdate(T[])}.
	 */
	@Test
	public void testBatchSaveOrUpdate() {
		int MAX_SIZE = 1000;
		Module[] modules = new Module[MAX_SIZE];
		for (int i = 0; i < MAX_SIZE; i++) {
			modules[i] = new Module();
			modules[i].setCode("code" + i);
			modules[i].setName("name" + i);
		}
		
		dao.batchSaveOrUpdate(modules);
		dao.flush();
		
		Assert.assertEquals(MAX_SIZE + 13, countRowsInTable("module"));
	}

	/**
	 * Test method for {@link com.lily.dap.dao.hibernate.HibernateDao#batchRemove(java.lang.Class, java.io.Serializable[])}.
	 */
	@Test
	public void testBatchRemove() {
//		Assert.assertEquals(5, dao.batchRemove(Module.class, new Long[]{3l, 4l, 5l, 6l, 7l}));
		
		Assert.assertEquals(4, dao.batchRemove(Module.class, Condition.create().llike("code", "code1")));
		
//		Assert.assertEquals(4, countRowsInTable("module"));
	}

	/**
	 * Test method for {@link com.lily.dap.dao.hibernate.HibernateDao#batchUpdate(java.lang.Class, java.lang.String[], java.lang.Object[], com.lily.dap.dao.Condition)}.
	 */
	@Test
	public void testBatchUpdate() {
		Assert.assertEquals(2, dao.batchUpdate(Module.class, new String[]{"name", "des"}, new Object[]{"新的编码", "新的说明"}, Condition.create().in("code", new Object[]{"code1", "code2"})));
		
		Assert.assertEquals(2, dao.count(Module.class, Condition.create().eq("name", "新的编码")));
	}
	
	@Test
	public void testExecute() {
		//这个语句不能执行，hql好像不支持insert...select...中的带参数查询
//		int count = dao.execute("insert into Module (code, name) select code, ? from Module where code in (?, ?)", "HQL", "code2", "code3");
		
		int count = dao.execute("insert into Module (code, name, num, cnt, createDate, defaultModule) select code, name, num, cnt, createDate, defaultModule from Module where code in (?, ?)", "code1", "code2");
		Assert.assertEquals(2, count);
		
		Assert.assertEquals(4, dao.execute("update Module set name = ? where code in(?, ?)", "修改的名称", "code1", "code2"));
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("name", "修改的名称");
		param.put("code1", "code3");
		param.put("code2", "code4");
		Assert.assertEquals(2, dao.execute("update Module set name = :name where code in(:code1, :code2)", param));
		
		Assert.assertEquals(new Long(6),  dao.unique("select count(*) from Module where name = '修改的名称'"));
	}

	/**
	 * Test method for {@link com.lily.dap.dao.hibernate.HibernateDao#sqlExecute(java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testSqlExecute() {
		dao.sqlExecute("delete from module where code like ?", "code1%");
		Assert.assertEquals(9, countRowsInTable("module"));

//		dao.sqlExecute("insert into module (id, code, name) select id + 100, code, ? from module where code in (?, ?)", "修改的名称", "code2", "code3");
//		Assert.assertEquals(new Long(2),  dao.unique("select count(*) from Module where name = '修改的名称'"));
	}

	/**
	 * Test method for {@link com.lily.dap.dao.hibernate.HibernateDao#sqlQuery(java.lang.String, int, int, java.lang.Object[])}.
	 */
	@Test
	public void testSqlQuery() {
		// TODO 在oracle中当des的值为null时读取会出错，这个问题建议自己改写spring的RowMapper
		
		List<Module> list = dao.sqlQuery(Module.class, "select id, code, name, num, create_date, default_module from module");
		Assert.assertEquals(13, list.size());
		
		List<Map<String, Object>> list2 = dao.sqlQuery("select id, code, name, num, create_date, default_module, des from module where code like 'code1%'");
		Assert.assertEquals(4, list2.size());
		
	}
}
