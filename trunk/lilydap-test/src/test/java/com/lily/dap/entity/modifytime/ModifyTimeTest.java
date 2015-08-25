package com.lily.dap.entity.modifytime;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lily.dap.dao.Dao;

@ContextConfiguration(locations = { 
		"/applicationContext-resources.xml", 
		"/applicationContext-dao.xml" })
public class ModifyTimeTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	protected Dao dao;
	
	@Test
	public void testModifyTimeRecord() {
		ModifyTimeEntity entity = new ModifyTimeEntity();
		entity.setValue("first");
		
		dao.saveOrUpdate(entity);
		Assert.assertNotNull(entity.getModifyTime());
		System.out.println(entity);
		
		Date preDate = entity.getModifyTime();
		
		entity.setValue("second");
		dao.saveOrUpdate(entity);
		Assert.assertNotSame(preDate, entity.getModifyTime());
		System.out.println(entity);
		
		ModifyTimeEntity[] entitys = new ModifyTimeEntity[3];
		entitys[0] = new ModifyTimeEntity();
		entitys[0].setValue("one");
		entitys[1] = new ModifyTimeEntity();
		entitys[1].setValue("two");
		entitys[2] = new ModifyTimeEntity();
		entitys[2].setValue("three");
		
		dao.batchSaveOrUpdate(entitys);
		
		for (ModifyTimeEntity mte : entitys) {
			Assert.assertNotNull(mte.getModifyTime());
			System.out.println(mte);
		}
	}
}
