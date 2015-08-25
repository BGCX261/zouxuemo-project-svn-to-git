/**
 * 
 */
package com.lily.dap.service.report2.impl.word;

import java.awt.Color;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lily.dap.service.report2.ReportManager;
import com.lily.dap.service.report2.impl.model.CssClass;
import com.lily.dap.service.report2.impl.model.CssClass.Style;
import com.lily.dap.service.report2.util.StyleUtils;
import com.lowagie.text.Cell;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;

/**
 * @author xuemozou
 *
 */
public class StyleOperate {
    private static final Log logger = LogFactory.getLog(StyleOperate.class);
    
	private static Map<String, WordStyle> styleMapping = new HashMap<String, WordStyle>();
	static {
		WordStyle wordStyle;
		
		wordStyle = new WordStyle("fontName");
		styleMapping.put("fontName", wordStyle);
		
		wordStyle = new WordStyle("size");
		styleMapping.put("fontHeight", wordStyle);
		
		wordStyle = new WordStyle("style");
		wordStyle.addOption("BOLDWEIGHT_NORMAL", Font.NORMAL);
		wordStyle.addOption("BOLDWEIGHT_BOLD", Font.BOLD);
		styleMapping.put("boldweight", wordStyle);
		
		wordStyle = new WordStyle("italic");
		wordStyle.addOption("true", Font.ITALIC);
		wordStyle.addOption("false", Font.NORMAL);
		styleMapping.put("italic", wordStyle);

		Map<String, Object> colorMap = new HashMap<String, Object>();
		colorMap.put("COLOR_NORMAL", Color.BLACK);
		colorMap.put("COLOR_BLACK", Color.BLACK);
		colorMap.put("COLOR_WHITE", Color.WHITE);
		colorMap.put("COLOR_RED", Color.RED);
		colorMap.put("COLOR_BLUE", Color.BLUE);
		colorMap.put("COLOR_GRAY", Color.GRAY);
		
		wordStyle = new WordStyle("color");
		wordStyle.setOptions(colorMap);
		styleMapping.put("color", wordStyle);
		
		wordStyle = new WordStyle("strikeout");
		styleMapping.put("strikeout", wordStyle);
		
		wordStyle = new WordStyle("underline");
		wordStyle.addOption("U_NONE", Font.NORMAL);
		wordStyle.addOption("U_SINGLE", Font.UNDERLINE);
		styleMapping.put("underline", wordStyle);
		
		wordStyle = new WordStyle("borderWidthTop");
		styleMapping.put("borderTop", wordStyle);
		
		wordStyle = new WordStyle("borderWidthBottom");
		styleMapping.put("borderBottom", wordStyle);
		
		wordStyle = new WordStyle("borderWidthLeft");
		styleMapping.put("borderLeft", wordStyle);
		
		wordStyle = new WordStyle("borderWidthRight");
		styleMapping.put("borderRight", wordStyle);
		
		wordStyle = new WordStyle("borderColorTop");
		wordStyle.setOptions(colorMap);
		styleMapping.put("topBorderColor", wordStyle);
		
		wordStyle = new WordStyle("borderColorBottom");
		wordStyle.setOptions(colorMap);
		styleMapping.put("bottomBorderColor", wordStyle);
		
		wordStyle = new WordStyle("borderColorLeft");
		wordStyle.setOptions(colorMap);
		styleMapping.put("leftBorderColor", wordStyle);
		
		wordStyle = new WordStyle("borderColorRight");
		wordStyle.setOptions(colorMap);
		styleMapping.put("rightBorderColor", wordStyle);
		
		wordStyle = new WordStyle("backgroundColor");
		wordStyle.setOptions(colorMap);
		styleMapping.put("backgroundColor", wordStyle);
		
		wordStyle = new WordStyle("horizontalHorizontalAlignment");
		wordStyle.addOption("ALIGN_CENTER", Element.ALIGN_CENTER);
		wordStyle.addOption("ALIGN_LEFT", Element.ALIGN_LEFT);
		wordStyle.addOption("ALIGN_RIGHT", Element.ALIGN_RIGHT);
		styleMapping.put("alignment", wordStyle);
		
		wordStyle = new WordStyle("verticalHorizontalAlignment");
		wordStyle.addOption("VERTICAL_CENTER", Element.ALIGN_MIDDLE);
		wordStyle.addOption("VERTICAL_TOP", Element.ALIGN_TOP);
		wordStyle.addOption("VERTICAL_BOTTOM", Element.ALIGN_BOTTOM);
		styleMapping.put("verticalAlignment", wordStyle);
		
		wordStyle = new WordStyle("indention");
		styleMapping.put("indention", wordStyle);
		
		wordStyle = new WordStyle("wrapText");
		styleMapping.put("wrapText", wordStyle);
	}
	
	public static Font createFont(Map<String, CssClass> cssClassMap, String cssClass) throws DocumentException, IOException {
		WordCss css = buildWordCss(cssClassMap, cssClass);
		float size = css.getSize();
		int style = css.getStyle();
		
		BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font font = new Font(baseFont, size, style);
		font.setColor(css.getColor());
		
		return font;
	}
	
	public static void setTableStyle(Table table, Map<String, CssClass> cssClassMap, String cssClass) {
		WordCss css = buildWordCss(cssClassMap, cssClass);
		
		table.setAlignment(css.getHorizontalHorizontalAlignment());
		table.setBackgroundColor(css.getBackgroundColor());
		table.setBorderColorTop(css.getBorderColorTop());
		table.setBorderColorBottom(css.getBorderColorBottom());
		table.setBorderColorLeft(css.getBorderColorLeft());
		table.setBorderColorRight(css.getBorderColorRight());
		table.setBorderWidthTop(css.getBorderWidthTop());
		table.setBorderWidthBottom(css.getBorderWidthBottom());
		table.setBorderWidthLeft(css.getBorderWidthLeft());
		table.setBorderColorRight(css.getBorderColorRight());
	}
	
	public static void setCellStyle(Cell cell, Map<String, CssClass> cssClassMap, String cssClass) {
		WordCss css = buildWordCss(cssClassMap, cssClass);
		
		cell.setHorizontalAlignment(css.getHorizontalHorizontalAlignment());
		cell.setVerticalAlignment(css.getVerticalHorizontalAlignment());
		cell.setBackgroundColor(css.getBackgroundColor());
		cell.setBorderColorTop(css.getBorderColorTop());
		cell.setBorderColorBottom(css.getBorderColorBottom());
		cell.setBorderColorLeft(css.getBorderColorLeft());
		cell.setBorderColorRight(css.getBorderColorRight());
		cell.setBorderWidthTop(css.getBorderWidthTop());
		cell.setBorderWidthBottom(css.getBorderWidthBottom());
		cell.setBorderWidthLeft(css.getBorderWidthLeft());
		cell.setBorderWidthRight(css.getBorderWidthRight());
	}
	
	private static WordCss buildWordCss(Map<String, CssClass> cssClassMap, String cssClass) {
		WordCss css = new WordCss();
		
		Collection<Style> collection = StyleUtils.getReportStyle(cssClassMap, ReportManager.REPORT_TYPE_WORD, cssClass);
		for (Style style : collection) {
			String styleName = style.getName();
			Object value = style.getValue();
			
			WordStyle wordStyle = styleMapping.get(styleName);
			if (wordStyle == null)
				continue;
			
			String mappingName = wordStyle.getMappingName();
			value = wordStyle.getValue(value);
			
			try {
				PropertyUtils.setProperty(css, mappingName, value);
			} catch (Exception e) {
				logger.warn("读入样式[" + styleName + "]值[" + value.toString() + "]失败，忽略该样式－" + e);
			}
		}
		
		return css;
	}
}

class WordStyle {
	private String mappingName;
	private Map<String, Object> options = new HashMap<String, Object>();

	public WordStyle(String mappingName) {
		this.mappingName = mappingName;
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
}

