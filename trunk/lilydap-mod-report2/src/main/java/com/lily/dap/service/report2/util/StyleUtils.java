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
 * �����ʽ������
 */
public class StyleUtils {
	/**
	 * �����ÿո�ָ��Ķ����ʽ������������Щ��ʽ������������ʽ����
	 * <p>���������ʽ��������ǰ����ʽ����ͬ����ʽ���򸲸�ǰ�����ʽ
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
	 * �ϲ������ʽ��������ʽ����֮���ÿո����ָ�
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
