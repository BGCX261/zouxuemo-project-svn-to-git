package com.lily.dap.web.action.demo;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;

import com.lily.dap.test.Struts2ActionTest;

public class ModuleCrudActionTest extends Struts2ActionTest {
    @Override
	protected void setUp() throws Exception {
		super.setUp();
		
		this.setDefaultRollback(false);
	}

	public void testSave() throws UnsupportedEncodingException, ServletException {
		String result;
		
    	request.setParameter("code", "test");
    	request.setParameter("name", "test");
    	result = executeAction("/demo/module-crud!save.action");
    	System.out.println("/demo/module-crud!save.action result: " + result);
    	
//    	Map<String, Object> map = JsonHelper.parseJsonStringToMap(String.class, Object.class, result);
    }   

	public void testGet() throws UnsupportedEncodingException, ServletException {
		String result;
		
    	request.setParameter("id", "0");
    	result = executeAction("/demo/module-crud!get.action");
    	System.out.println("/demo/module-crud!get.action result: " + result);
    	
//    	Map<String, Object> map = JsonHelper.parseJsonStringToMap(String.class, Object.class, result);
    }   
}
