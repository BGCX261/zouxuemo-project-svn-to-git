/**
 * 
 */
package com.lily.dap.service.report2.impl.html;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;

import com.lily.dap.service.report2.ReportManager;
import com.lily.dap.service.report2.impl.model.CssClass;
import com.lily.dap.service.report2.impl.model.CssClass.Style;

/**
 * @author xuemozou
 *
 */
public class StyleOperate {
//    private static final Log logger = LogFactory.getLog(StyleOperate.class);
    
	private static Map<String, HtmlStyle> styleMapping = new HashMap<String, HtmlStyle>();
	static {
		HtmlStyle htmlStyle;
		
		htmlStyle = new HtmlStyle("FONT-FAMILY");
		styleMapping.put("fontName", htmlStyle);
		
		htmlStyle = new HtmlStyle("FONT-SIZE", "pt");
		styleMapping.put("fontHeight", htmlStyle);
		
		htmlStyle = new HtmlStyle("FONT-WEIGHT");
		htmlStyle.addOption("BOLDWEIGHT_NORMAL", "normal");
		htmlStyle.addOption("BOLDWEIGHT_BOLD", "bold");
		styleMapping.put("boldweight", htmlStyle);
		
		htmlStyle = new HtmlStyle("FONT-STYLE");
		htmlStyle.addOption("true", "italic");
		htmlStyle.addOption("false", "normal");
		styleMapping.put("italic", htmlStyle);

		Map<String, Object> colorMap = new HashMap<String, Object>();
		colorMap.put("COLOR_NORMAL", "black");
		colorMap.put("COLOR_BLACK", "black");
		colorMap.put("COLOR_WHITE", "white");
		colorMap.put("COLOR_RED", "red");
		colorMap.put("COLOR_BLUE", "blue");
		colorMap.put("COLOR_GRAY", "gray");
		
		htmlStyle = new HtmlStyle("COLOR");
		htmlStyle.setOptions(colorMap);
		styleMapping.put("color", htmlStyle);
		
//		htmlStyle = new HtmlStyle("strikeout");
//		styleMapping.put("strikeout", htmlStyle);
		
		htmlStyle = new HtmlStyle("TEXT-DECORATION");
		htmlStyle.addOption("U_NONE", "none");
		htmlStyle.addOption("U_SINGLE", "underline");
		styleMapping.put("underline", htmlStyle);
		
		htmlStyle = new HtmlStyle("border-top-width");
		styleMapping.put("borderTop", htmlStyle);
		
		htmlStyle = new HtmlStyle("border-bottom-width");
		styleMapping.put("borderBottom", htmlStyle);
		
		htmlStyle = new HtmlStyle("border-left-width");
		styleMapping.put("borderLeft", htmlStyle);
		
		htmlStyle = new HtmlStyle("border-right-width");
		styleMapping.put("borderRight", htmlStyle);
		
		htmlStyle = new HtmlStyle("border-top-color");
		htmlStyle.setOptions(colorMap);
		styleMapping.put("topBorderColor", htmlStyle);
		
		htmlStyle = new HtmlStyle("border-bottom-color");
		htmlStyle.setOptions(colorMap);
		styleMapping.put("bottomBorderColor", htmlStyle);
		
		htmlStyle = new HtmlStyle("border-left-color");
		htmlStyle.setOptions(colorMap);
		styleMapping.put("leftBorderColor", htmlStyle);
		
		htmlStyle = new HtmlStyle("border-right-color");
		htmlStyle.setOptions(colorMap);
		styleMapping.put("rightBorderColor", htmlStyle);
		
		htmlStyle = new HtmlStyle("background-color");
		htmlStyle.setOptions(colorMap);
		styleMapping.put("backgroundColor", htmlStyle);
		
		htmlStyle = new HtmlStyle("TEXT-ALIGN");
		htmlStyle.addOption("ALIGN_CENTER", "center");
		htmlStyle.addOption("ALIGN_LEFT", "left");
		htmlStyle.addOption("ALIGN_RIGHT", "right");
		styleMapping.put("alignment", htmlStyle);
		
		htmlStyle = new HtmlStyle("VERTICAL-ALIGN");
		htmlStyle.addOption("VERTICAL_CENTER", "middle");
		htmlStyle.addOption("VERTICAL_TOP", "top");
		htmlStyle.addOption("VERTICAL_BOTTOM", "bottom");
		styleMapping.put("verticalAlignment", htmlStyle);
		
		htmlStyle = new HtmlStyle("padding-left");
		htmlStyle.addOption("1", "1cm");
		styleMapping.put("indention", htmlStyle);
		
		htmlStyle = new HtmlStyle("word-wrap");
		htmlStyle.addOption("true", "break-word");
		htmlStyle.addOption("false", "keep-all");
		styleMapping.put("wrapText", htmlStyle);
	}
	
	public static String buildHtmlStyle(Map<String, CssClass> cssClassMap) {
		StringBuffer buf = new StringBuffer();
		
		for (Map.Entry<String, CssClass> entry : cssClassMap.entrySet()) {
		//for (CssClass cssClass : cssClassMap.values()) {
			String s = buildHtmlClass(entry.getValue());
			
			buf.append(s).append("\r\n");
		}
		
		return buf.toString();
	}
	
	private static String buildHtmlClass(CssClass cssClass) {
		StringBuffer buf = new StringBuffer();
		buf.append(".").append(cssClass.getClassName()).append(" {\r\n");
		
		Map<String, BorderStyle> borderStyleMap = new HashMap<String, BorderStyle>();
		
		Collection<Style> collection = cssClass.getStyles(ReportManager.REPORT_TYPE_HTML);
		for (Style style : collection) {
			HtmlStyle htmlStyle = styleMapping.get(style.getName());
			if (htmlStyle == null) {
				buf.append("  ").append(style.getName()).append(": ").append(style.getValue().toString()).append(";\r\n");
				
				continue;
			}
			
			String mappingName = htmlStyle.getMappingName();
			Object value = htmlStyle.getValue(style.getValue());
			String postfix = htmlStyle.getPostfix();
			
			// 如果mappingName是边框信息，则单独处理
			if (mappingName.startsWith("border-"))
				parseBorderStyle(borderStyleMap, mappingName, value);
			else {
				buf.append("  ").append(mappingName).append(": ").append(value);
				
				if (postfix != null)
					buf.append(postfix);
				
				buf.append(";\r\n");
			}
		}
		
		for (BorderStyle borderStyle : borderStyleMap.values())
			buf.append("  ").append(borderStyle.name).append(": ").append(borderStyle.color).append(" ").append(borderStyle.width).append(" solid;\r\n");
		
		buf.append("}");
		return buf.toString();
	}
	
	private static void parseBorderStyle(Map<String, BorderStyle> borderStyleMap, String mappingName, Object value) {
		BorderStyle borderStyle = null;
		
		if (mappingName.indexOf("left") >= 0) {
			if (borderStyleMap.containsKey("BORDER-LEFT"))
				borderStyle = borderStyleMap.get("BORDER-LEFT");
			else {
				borderStyle = new BorderStyle("BORDER-LEFT");
				borderStyleMap.put("BORDER-LEFT", borderStyle);
			}
		} else if (mappingName.indexOf("top") >= 0) {
			if (borderStyleMap.containsKey("BORDER-TOP"))
				borderStyle = borderStyleMap.get("BORDER-TOP");
			else {
				borderStyle = new BorderStyle("BORDER-TOP");
				borderStyleMap.put("BORDER-TOP", borderStyle);
			}
		} else if (mappingName.indexOf("right") >= 0) {
			if (borderStyleMap.containsKey("BORDER-RIGHT"))
				borderStyle = borderStyleMap.get("BORDER-RIGHT");
			else {
				borderStyle = new BorderStyle("BORDER-RIGHT");
				borderStyleMap.put("BORDER-RIGHT", borderStyle);
			}
		} else {
			if (borderStyleMap.containsKey("BORDER-BOTTOM"))
				borderStyle = borderStyleMap.get("BORDER-BOTTOM");
			else {
				borderStyle = new BorderStyle("BORDER-BOTTOM");
				borderStyleMap.put("BORDER-BOTTOM", borderStyle);
			}
		}
		
		if (mappingName.indexOf("width") >= 0) {
			borderStyle.width = value;
		} else {
			borderStyle.color = value;
		}
	}

	private static class BorderStyle {
		public String name = null;

		public Object width = "1";
		
		public Object color = "#666666";
		
		public BorderStyle(String name) {
			this.name = name;
		}
	}
}

class HtmlStyle {
	private String mappingName;
	private Map<String, Object> options = new HashMap<String, Object>();
	private String postfix = null;

	public HtmlStyle(String mappingName) {
		this.mappingName = mappingName;
	}

	public HtmlStyle(String mappingName, String postfix) {
		this.mappingName = mappingName;
		this.postfix = postfix;
	}

	public void setOptions(Map<String, Object> options) {
		this.options = options;
	}

	public void addOption(String name, Object value) {
		options.put(name, value);
	}
	
	public String getMappingName() {
		return mappingName;
	}

	public Object getValue(Object value) {
		String key = value.toString();
		if (options.containsKey(key))
			return options.get(key);
		
		return value;
	}

	public String getPostfix() {
		return postfix;
	}
}

