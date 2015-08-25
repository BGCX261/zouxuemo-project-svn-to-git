package com.lily.dap.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration(locations = { 
		"/applicationContext-resources.xml", 
		"/applicationContext-dao.xml"})
public class ServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

}
