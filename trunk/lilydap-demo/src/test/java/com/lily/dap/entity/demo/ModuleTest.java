package com.lily.dap.entity.demo;

import org.junit.Before;
import org.junit.Test;

import com.lily.dap.entity.demo.Module;
import com.lily.dap.test.EntityTest;

public class ModuleTest extends EntityTest<Module> {
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
	protected boolean fillEntity(Module entity) {
		return super.fillEntity(entity);
	}

	@Override
	protected void setBatchInsertCount(int batchInsertCount) {
		super.setBatchInsertCount(batchInsertCount);
	}
}
