package com.lily.dap.util.dozer;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;

import com.lily.dap.util.StringBean;
import com.lily.dap.util.TestBean;

public class DozerTest {
	private Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();
	private TestBean testBean1, testBean2;
	private StringBean stringBean;
	
	@Before
	public void setUp() throws Exception {
		testBean1 = new TestBean("abc", 123, 111.1f, new Date());
		testBean2 = new TestBean("def", 321, 222.2f, new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2000-12-31 12:23"));
		stringBean = new StringBean();
	}

	@Test
	public void testDozer() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("field1", "×Ö·û´®");
		map.put("field2", new BigDecimal("12345"));
		map.put("field3", new BigDecimal("111.1"));
//		map.put("field4", "2012-12-31 12:12:12");
		map.put("field4", "2011-11-15T16:17:43.000+08:00");
		
		TestBean testBean = new TestBean();
		mapper.map(map, testBean);
		System.out.println(testBean);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(testBean.getField4()));

		Map<String, String> map1 = new HashMap<String, String>();
		mapper.map(testBean, map1);
		System.out.println(map1);
	}
}
