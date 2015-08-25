/**
 * 
 */
package com.lily.dap.service.report2.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.lily.dap.service.report2.impl.model.CssClass;
import com.lily.dap.service.report2.impl.model.CssClass.Style;

/**
 * @author xuemozou
 *
 * 表格样式工具类
 */
public class StyleUtils {
	/**
	 * 给定用空格分隔的多个样式类名，返回这些样式类所包含的样式集合
	 * <p>如果后面样式类中有与前面样式类相同的样式，则覆盖前面的样式
	 * 
	 * @param cssClassMap
	 * @param scope
	 * @param classs
	 * @return
	 */
	public static Collection<Style> getReportStyle(Map<String, CssClass> cssClassMap, String scope, String classs) {
		Map<String, Style> map = new HashMap<String, Style>();
		
		String[] tmp = classs.split(" ");
		for (String clazz : tmp) {
			if ("".equals(clazz))
				continue;
			
			CssClass cssClass = cssClassMap.get(clazz);
			if (cssClass == null)
				continue;
			
			Collection<Style> collection = cssClass.getStyles(scope);
			for (Style style : collection) {
				String name = style.getName();
				if (map.containsKey(name))
					map.remove(name);
				
				map.put(name, style);
			}
		}
		
		return map.values();
	}
	
	/**
	 * 合并多个样式类名，样式类名之间用空格来分隔
	 * 
	 * @param cssClasss
	 * @return
	 */
	public static String mergeCssClass(String... cssClasss) {
		StringBuffer buf = new StringBuffer();
		
		boolean first = true;
		for (String c : cssClasss) {
			if (c == null || "".equals(c))
				continue;
			
			if (first)
				first = false;
			else
				buf.append(" ");
			
			buf.append(c);
		}
		
		return buf.toString();
	}
}
