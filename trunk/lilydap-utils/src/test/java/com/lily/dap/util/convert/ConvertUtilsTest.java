package com.lily.dap.util.convert;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class ConvertUtilsTest {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Test
	public void testDateConvert() {
		System.out.println(ConvertUtils.convert(new Date()));

		System.out.println(sdf.format(ConvertUtils.convert("2010-12-31", Date.class)));
		System.out.println(sdf.format(ConvertUtils.convert("2010-12-31 21:00", Date.class)));
		System.out.println(sdf.format(ConvertUtils.convert("2010-12-31 21:12", Date.class)));
		System.out.println(sdf.format(ConvertUtils.convert("2010-12-31 21:12:31", Date.class)));

//		System.out.println(sdf.format(ConvertUtils.convert(new Date().toString(), Date.class)));
//		System.out.println(sdf.format(ConvertUtils.convert(new Date().toGMTString(), Date.class)));

		Integer[] ary = ConvertUtils.convert2(StringUtils.split("1,2,3, 4, 5,6", ','), Integer.class);
		System.out.println(StringUtils.join(ary, '-'));
		
		ary = ConvertUtils.convert2(StringUtils.split("1", ','), Integer.class);
		System.out.println(StringUtils.join(ary, '-'));

		ary = ConvertUtils.convert2(StringUtils.split("", ','), Integer.class);
		System.out.println(StringUtils.join(ary, '-'));
	}
}
