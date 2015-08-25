package com.lily.dap.entity.dictionary;

import org.junit.Before;
import org.junit.Test;

import com.lily.dap.test.EntityTest;

public class DictClassifyTest extends EntityTest<DictClassify> {
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testCRUD() throws InstantiationException,
			IllegalAccessException {
		doAutoTestCRUD();
	}

	@Test
	public void testBatchInsert() throws InstantiationException,
			IllegalAccessException {
		doAutoTestBatchInsert();
	}

	@Override
	protected boolean fillEntity(DictClassify entity) {
		return super.fillEntity(entity);
	}

	@Override
	protected void setBatchInsertCount(int batchInsertCount) {
		super.setBatchInsertCount(batchInsertCount);
	}
}
