package com.lily.dap.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TransferUtilsTest {
	private TestBean testBean1, testBean2;
	private StringBean stringBean;
	
	@Before
	public void setUp() throws Exception {
		testBean1 = new TestBean("abc", 123, 111.1, new Date());
		testBean2 = new TestBean("def", 321, 222.2, new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2000-12-31 12:23"));
		stringBean = new StringBean();
	}
	
	@Test
	public void testCopyTTStringArrayStringArrayTransferCallback() {
		TestBean testBean = new TestBean();
		System.out.println(testBean);
		
		TransferUtils.copy(testBean1, testBean, null, null, new TransferCallback() {
			public boolean readyCopy(Object src, String srcFieldName,
					Object srcFieldValue, Object tgt, String tgtFieldName,
					Object tgtFieldValue) {
				if ("field1".equals(srcFieldName))
					return false;
				
				return true;
			}
			
		});
		System.out.println(testBean);
		
		testBean = new TestBean();
		TransferUtils.copy(testBean2, testBean, new String[]{"field1", "field3"}, null, null);
		System.out.println(testBean);
		
		testBean = new TestBean();
		TransferUtils.copy(testBean2, testBean, null, new String[]{"field1", "field3"}, null);
		System.out.println(testBean);
	}

	@Test
	public void testCopyStringArrayObjectObjectTransferCallback() {
		String[] rules = new String[]{"field1", "field2-field3", "field3-field2", "field4"};
		
		TransferUtils.copy(rules, testBean1, stringBean, true, null);
		System.out.println(stringBean);
		
		TestBean testBean = new TestBean();
		TransferUtils.copy(rules, testBean, stringBean, false, new TransferCallback() {
			public boolean readyCopy(Object src, String srcFieldName,
					Object srcFieldValue, Object tgt, String tgtFieldName,
					Object tgtFieldValue) {
				if ("field1".equals(srcFieldName))
					return false;
				
				return true;
			}
			
		});
		System.out.println(testBean);
	}
	
	@Test
	public void testCopyMapToBean() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("field1", "abcd");
		map.put("field2", "12345");
		map.put("field3", "12345.678");
		map.put("field4", "2012-12-31 21:12");
	
		String[] rules = map.keySet().toArray(new String[0]);
		TestBean bean = new TestBean();
		TransferUtils.copy(rules, map, bean, null);
		System.out.println(bean);
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		TransferUtils.copy(bean, map1, null);
		Assert.assertTrue(map1.size() == 4);
		Assert.assertEquals("abcd", map1.get("field1"));
		Assert.assertEquals(12345, map1.get("field2"));
		
		TestBean bean1 = new TestBean();
		TransferUtils.copy(map, bean1, null);
		Assert.assertEquals("abcd", bean1.getField1());
		Assert.assertEquals(12345, bean1.getField2());
	}

	@Test
	public void testTransfer() {
		String[] rule = new String[]{"field1=field1+'ºóÐø×Ö·û´®'", "field2=field2+123", "field3=field3+100", "field4='2012-12-31 12:12:12'"};
		
		TestBean testBean = new TestBean();
		TransferUtils.transfer(rule, testBean, testBean1);
		System.out.println(testBean);
		
		rule = new String[]{"field1=$source[0].field1+$source[1].field1", "field2=$source[0].field2+$source[1].field2", "field3=$source[0].field3+$source[1].field3", "field4='2012-12-31'"};
		
		TransferUtils.transfer(rule, testBean, testBean1, testBean2);
		System.out.println(testBean);
	}

}
