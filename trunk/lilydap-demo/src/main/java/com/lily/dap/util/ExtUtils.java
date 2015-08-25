/**
 * 
 */
package com.lily.dap.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lily.dap.entity.ExtTreeData;

/**
 * EXT 操作工具类
 * 
 * @author 邹学模
 *
 */
public class ExtUtils {
	/**
	 * 建立ExtTreeData列表集合的JSON数据
	 * 
	 * @param dataList
	 * @return
	 */
	public static String buildJSONString(List<ExtTreeData> dataList) {
		StringBuffer buf = new StringBuffer();
		buf.append("[");
		
		boolean flag = false;
		for(ExtTreeData data : dataList) {
			if (flag)
				buf.append(", ");
			else
				flag = true;

			buf.append(buildJSONString(data));
		}
		
		buf.append("]");
		return buf.toString();
	}

	/**
	 * 建立ExtTreeData的JSON数据
	 * 
	 * @param data
	 * @return
	 */
	public static String buildJSONString(ExtTreeData data) {
		StringBuffer buf = new StringBuffer();
		buf.append("{");

		boolean flag = false;
		for(Map.Entry<String, Object> entry : data.getDataMap().entrySet()) {
			if (entry.getValue() == null)
				continue;
			
			if (flag)
				buf.append(", ");
			else
				flag = true;
			
			buf.append("\"").append(entry.getKey()).append("\" : ");
			
			String quotationMark = needQuotation(entry.getValue()) ? "\"" : "";
			buf.append(quotationMark).append(tranObject(entry.getValue())).append(quotationMark);
		}
		
		List<ExtTreeData> list = data.getChildren();
		if (list != null) {
			if (flag)
				buf.append(", ");
			
			buf.append("\"children\" : ");
			buf.append(buildJSONString(list));
		}
		
		buf.append("}");
		return buf.toString();
	}
	
	@SuppressWarnings("deprecation")
	private static String tranObject(Object obj) {
		if (obj instanceof Date) {
			SimpleDateFormat sdf;
			
			Date date = (Date)obj;
			if (date.getHours() > 0 || date.getMinutes() > 0 || date.getSeconds() > 0)
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 else
				 sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			return sdf.format(date);
		} else
			return obj.toString();
	}
	
	private static boolean needQuotation(Object obj) {
		if (obj instanceof String || obj instanceof Date)
			return true;
		else
			return false;
	}
}
