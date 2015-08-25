package com.lily.dap.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;


public class FieldTokenProcessorTest {
	@Test
	public void test() throws ParseException {
		FieldTokenProcessor processor = new FieldTokenProcessor();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("a", 123);
		params.put("b", 123.456);
		params.put("c", true);
		params.put("d", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2012-12-21 12:24:42"));
		
		Assert.assertEquals(5, processor.searchFieldToken("{a},{b},{c},{d},{e}").size());
		System.out.println(processor.searchFieldToken("{a},{b},{c},{d},{e}"));
		
		Assert.assertEquals(6, FieldTokenProcessor.searchAvailableVariableToken("a1+$$*10 b.e-_c/d_d").size());
		System.out.println(FieldTokenProcessor.searchAvailableVariableToken("a1+$$*10 b.e-_c/d_d"));
		
		Assert.assertEquals(2, FieldTokenProcessor.searchNestedVariableToken("a.b+c.d").size());
		System.out.println(FieldTokenProcessor.searchNestedVariableToken("a.b+c.d"));
		
		Assert.assertEquals("123,123.456,true,2012-12-21 12:24:42,", processor.replaceFieldToken("{a},{b},{c},{d},{e}", params, false));
		System.out.println(processor.replaceFieldToken("{a},{b},{c},{d},{e}", params, false));
		
		try {
			System.out.println(processor.replaceFieldToken("{a},{b},{c},{d},{e", params, true));
			Assert.fail("应该抛出异常");
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		try {
			System.out.println(processor.replaceFieldToken("{a},{b},{c},{d},}", params, true));
			Assert.fail("应该抛出异常");
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		try {
			System.out.println(processor.replaceFieldToken("{a},{b},{c},{d},{{e}", params, true));
			Assert.fail("应该抛出异常");
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		try {
			System.out.println(processor.replaceFieldToken("{a},{b},{c},{d},{e}", params, true));
			Assert.fail("应该抛出异常");
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		TestBean testBean = new TestBean("abc", 123, 111.1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2012-12-21 12:24:42"));
		System.out.println(processor.evaluateFieldToken("field1+field2+field3+field4, result: {field1+field2+field3+field4}", testBean));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("field1", "abc");
		map.put("field2", 123);
		map.put("field3", 111.1);
		map.put("field4", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2012-12-21 12:24:42"));
		Assert.assertEquals("field1+field2+field3+field4, result: abc123111.1(2012-12-21 12:24:42)", 
				processor.evaluateFieldToken("field1+field2+field3+field4, result: {field1+field2+field3+'('+date_to_string(field4,'yyyy-MM-dd HH:mm:ss')+')'}", map));
		System.out.println(processor.evaluateFieldToken("field1+field2+field3+field4, result: {rand()* 10000 + field1+field2+field3+'('+date_to_string(field4,'yyyy-MM-dd HH:mm:ss')+')'}", map));

		map.remove("field4");
		System.out.println(processor.evaluateFieldToken("field1+field2+field3+field4, result: {rand()* 10000 + field1+field2+field3+field4}", map));
	}
}
