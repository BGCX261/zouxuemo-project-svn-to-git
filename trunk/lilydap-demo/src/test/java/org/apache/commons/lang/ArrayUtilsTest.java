/**
 * 
 */
package org.apache.commons.lang;

import org.junit.Test;

/**
 * @author Administrator
 *
 */
public class ArrayUtilsTest {
	@Test
	public void testArray() {
		int[] array = new int[]{1, 2, 3, 4, 5};
		
		array = ArrayUtils.add(array, 6);
		System.out.println(ArrayUtils.toString(array));
		
		array = ArrayUtils.add(array, 1, 7);
		System.out.println(ArrayUtils.toString(array));
		
		array = ArrayUtils.addAll(array, new int[]{8, 9});
		System.out.println(ArrayUtils.toString(array));
		
		System.out.println(ArrayUtils.contains(array, 7));
		System.out.println(ArrayUtils.contains(array, 10));
		
		Object[] ss = new String[]{"abc", "def", "ghi"};
		
		System.out.println(ArrayUtils.indexOf(ss, "def"));
		System.out.println(ArrayUtils.indexOf(ss, "de"));
		
		ss = ArrayUtils.removeElement(ss, "def");
		System.out.println(ArrayUtils.toString(ss));
	}
	
	@Test
	public void testRandomString() {
		System.out.println(RandomStringUtils.random(100));

		System.out.println(RandomStringUtils.randomAlphabetic(100));
		
		System.out.println(RandomStringUtils.randomAlphanumeric(100));
		
		System.out.println(RandomStringUtils.randomAscii(100));
		
		System.out.println(RandomStringUtils.randomNumeric(100));
		
		System.out.println(RandomStringUtils.random(100, new char[]{'a', 'b', 'c', '1', '2', '3'}));
		
		System.out.println(RandomStringUtils.random(100, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890.-_"));
	}
	
	@Test
	public void testStringEscape() {
		System.out.println("<html><body>这是一个HTML<br></body></html>");
		System.out.println(StringEscapeUtils.escapeHtml("<html><body>这是一个HTML<br></body></html>"));
		System.out.println(StringEscapeUtils.unescapeHtml(StringEscapeUtils.escapeHtml("<html><body>这是一个HTML<br></body></html>")));
		
		System.out.println("\"这是一个HTML' Page\r\n\"");
		System.out.println(StringEscapeUtils.escapeJava("\"这是一个HTML' Page\r\n\""));
		System.out.println(StringEscapeUtils.escapeJavaScript("\"这是一个HTML' Page\r\n\""));

		System.out.println(StringEscapeUtils.unescapeJava(StringEscapeUtils.escapeJava("\"这是一个HTML' Page\r\n\"")));
		System.out.println(StringEscapeUtils.unescapeJavaScript(StringEscapeUtils.escapeJavaScript("\"这是一个HTML' Page\r\n\"")));

		System.out.println("\"这是一个HTML' Page\r\n\"");
		System.out.println(StringEscapeUtils.escapeSql("\"这是一个HTML' Page\r\n\""));
	}
}
