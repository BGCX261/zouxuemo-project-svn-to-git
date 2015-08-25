package com.lily.dap.service.common;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.lily.dap.entity.dictionary.Dictionary;
import com.lily.dap.service.common.impl.DictionaryManagerImpl;
import com.lily.dap.service.exception.DataContentRepeatException;
import com.lily.dap.service.exception.DataHaveUsedException;
import com.lily.dap.service.exception.DataNotExistException;
import com.lily.dap.test.ServiceTest;

@ContextConfiguration
public class DictionaryManagerTest extends ServiceTest {
	@Autowired
	private DictionaryManager dictionaryManager;
	
	@Before
	public void setUp() throws Exception {
		executeSqlScript("classpath:/com/lily/dap/service/common/DictionaryManagerTest-data.sql", false);
		
		((DictionaryManagerImpl)dictionaryManager).init();
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testAddDictionary() {
		Dictionary dictionary = dictionaryManager.addDictionary("catalog1", 30, "ID值3");
		Assert.assertEquals(0, dictionary.getSystemFlag());
		Assert.assertEquals(1, dictionary.getShowFlag());
		Assert.assertEquals(1, dictionary.getEnableFlag());
		Assert.assertEquals(3, dictionary.getSn());
		
		dictionary = dictionaryManager.addDictionary("catalog2", "code3", "CODE值3");
		Assert.assertEquals(0, dictionary.getSystemFlag());
		Assert.assertEquals(1, dictionary.getShowFlag());
		Assert.assertEquals(1, dictionary.getEnableFlag());
		Assert.assertEquals(3, dictionary.getSn());
		
		dictionary = dictionaryManager.addDictionary("catalog3", "选项3");
		Assert.assertEquals(0, dictionary.getSystemFlag());
		Assert.assertEquals(1, dictionary.getShowFlag());
		Assert.assertEquals(1, dictionary.getEnableFlag());
		Assert.assertEquals(3, dictionary.getSn());
		
		try {
			dictionaryManager.addDictionary("catalog1", 30, "ID值3");
			Assert.fail();
		} catch (DataContentRepeatException e) {}
		
		try {
			dictionaryManager.addDictionary("catalog0", 1, "ID值3");
			Assert.fail();
		} catch (DataNotExistException e) {}
		
		try {
			dictionaryManager.addDictionary("catalog1", "code123", "ID值3");
			Assert.fail();
		} catch (Exception e) {}
	}

	@Test
	public void testAdjustDictionaryOrder() {
		Dictionary dictionary = dictionaryManager.getDictionary("catalog1", 10);
		
		dictionaryManager.adjustDictionaryOrder(dictionary.getId(), 1);
		dictionary = dictionaryManager.getDictionary("catalog1", 10);
		Assert.assertEquals(2, dictionary.getSn());
		
		dictionaryManager.adjustDictionaryOrder(dictionary.getId(), -1);
		dictionary = dictionaryManager.getDictionary("catalog1", 10);
		Assert.assertEquals(1, dictionary.getSn());
		
		dictionaryManager.adjustDictionaryOrder(dictionary.getId(), -1);
		dictionary = dictionaryManager.getDictionary("catalog1", 10);
		Assert.assertEquals(1, dictionary.getSn());
	}
//
//	@Test
//	public void testGetDictionariesStringBoolean() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testGetDictionariesStringBooleanIntInt() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testGetDictionariesStringBooleanString() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testGetDictionariesStringBooleanStringArray() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testGetDictionariesStringBooleanIntArray() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testGetDictionaryLong() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testGetDictionaryStringInt() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testGetDictionaryStringString() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testGetDictionaryExt() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testModifyDictionary() {
//		fail("Not yet implemented"); // TODO
//	}

	@Test
	public void testRemoveDictionary() {
		try {
			dictionaryManager.removeDictionary(dictionaryManager.getDictionary("catalog1", 10).getId());
			Assert.fail();
		} catch (DataHaveUsedException e) {}
		
		try {
			dictionaryManager.removeDictionary(dictionaryManager.getDictionary("catalog2", "code1").getId());
			Assert.fail();
		} catch (DataHaveUsedException e) {}
		
		dictionaryManager.removeDictionary(dictionaryManager.getDictionary("catalog2", "code2").getId());
		
		try {
			dictionaryManager.getDictionary("catalog2", "code2");
			Assert.fail();
		} catch (DataNotExistException e) {}
	}
}
