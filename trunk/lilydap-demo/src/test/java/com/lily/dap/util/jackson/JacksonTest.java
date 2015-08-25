package com.lily.dap.util.jackson;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.junit.Before;
import org.junit.Test;

public class JacksonTest {
	private ObjectMapper mapper = new ObjectMapper();
	private TestBean bean = new TestBean();
	
	@Before
	public void setUp() throws Exception {
		bean.setField1("张'三\"ABC");
		bean.setField2(1234);
		bean.setField3(1234.5678);
	}

	@Test
	public void testObjectMapper() throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println(mapper.writeValueAsString("abcd"));				//"abcd"
		System.out.println(mapper.writeValueAsString(1234.56));				//1234.56
		System.out.println(mapper.writeValueAsString(false));				//false
		System.out.println(mapper.writeValueAsString(new Date()));			//1292994112828
		System.out.println(mapper.writeValueAsString(new byte[]{1,2,3}));	//"AQID"
		
		 String s = mapper.writeValueAsString(bean);
		 System.out.println(s); //{"field1":"张'三\"ABC","field2":1234,"field3":1234.5678,"field4":1292935965187,"field5":false}
		 
		 TestBean bean2 = mapper.readValue(s, TestBean.class);
		 Assert.assertEquals(bean, bean2);
	}

	@Test
	public void testObjectMapperArray() throws JsonGenerationException, JsonMappingException, IOException {
		TestBean[] array = new TestBean[]{new TestBean(), new TestBean(), new TestBean()};
		
		String s = mapper.writeValueAsString(array);
		System.out.println(s);	//[{"field1":"","field2":0,"field3":0.0,"field4":1292936092656,"field5":false},{"field1":"","field2":0,"field3":0.0,"field4":1292936092656,"field5":false},{"field1":"","field2":0,"field3":0.0,"field4":1292936092656,"field5":false}]
		
		TestBean[] array2 = mapper.readValue(s, TypeFactory.arrayType(TestBean.class));
		for (TestBean bean : array2)
		System.out.println(bean);
	}

	@Test
	public void testObjectMapperList() throws JsonGenerationException, JsonMappingException, IOException {
		List<TestBean> list = new ArrayList<TestBean>();
		list.add(new TestBean());
		list.add(new TestBean());
		list.add(new TestBean());
		
		String s = mapper.writeValueAsString(list);
		System.out.println(s);	//[{"field1":"","field2":0,"field3":0.0,"field4":1292936092656,"field5":false},{"field1":"","field2":0,"field3":0.0,"field4":1292936092656,"field5":false},{"field1":"","field2":0,"field3":0.0,"field4":1292936092656,"field5":false}]
		
		List<TestBean> list2 = mapper.readValue(s, TypeFactory.collectionType(List.class, TestBean.class));
		System.out.println(list2);
	}

	@Test
	public void testObjectMapperMap() throws JsonGenerationException, JsonMappingException, IOException {
		Map<String, TestBean> map = new HashMap<String, TestBean>();
		map.put("a", new TestBean());
		map.put("b", new TestBean());
		map.put("c", new TestBean());
		
		String s = mapper.writeValueAsString(map);
		System.out.println(s);	//{"a":{"field1":"","field2":0,"field3":0.0,"field4":1292989500890},"c":{"field1":"","field2":0,"field3":0.0,"field4":1292989500890},"b":{"field1":"","field2":0,"field3":0.0,"field4":1292989500890}}
		
		Map<String, TestBean> map2 = mapper.readValue(s, TypeFactory.mapType(Map.class, String.class, TestBean.class));
		System.out.println(map2);
	}
}
