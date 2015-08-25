/**
 * 
 */
package com.lily.dap.service.report2.impl.pdf;

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
    
	private static Map<String, PdfStyle> styleMapping = new HashMap<String, PdfStyle>();
	static {
		PdfStyle pdfStyle;
		
		pdfStyle = new PdfStyle("fontName");
		styleMapping.put("fontName", pdfStyle);
		
		pdfStyle = new PdfStyle("size");
		styleMapping.put("fontHeight", pdfStyle);
		
		pdfStyle = new PdfStyle("style");
		pdfStyle.addOption("BOLDWEIGHT_NORMAL", Font.NORMAL);
		pdfStyle.addOption("BOLDWEIGHT_BOLD", Font.BOLD);
		styleMapping.put("boldweight", pdfStyle);
		
		pdfStyle = new PdfStyle("italic");
		pdfStyle.addOption("true", Font.ITALIC);
		pdfStyle.addOption("false", Font.NORMAL);
		styleMapping.put("italic", pdfStyle);

		Map<String, Object> colorMap = new HashMap<String, Object>();
		colorMap.put("COLOR_NORMAL", Color.BLACK);
		colorMap.put("COLOR_BLACK", Color.BLACK);
		colorMap.put("COLOR_WHITE", Color.WHITE);
		colorMap.put("COLOR_RED", Color.RED);
		colorMap.put("COLOR_BLUE", Color.BLUE);
		colorMap.put("COLOR_GRAY", Color.GRAY);
		
		pdfStyle = new PdfStyle("color");
		pdfStyle.setOptions(colorMap);
		styleMapping.put("color", pdfStyle);
		
		pdfStyle = new PdfStyle("strikeout");
		styleMapping.put("strikeout", pdfStyle);
		
		pdfStyle = new PdfStyle("underline");
		pdfStyle.addOption("U_NONE", Font.NORMAL);
		pdfStyle.addOption("U_SINGLE", Font.UNDERLINE);
		styleMapping.put("underline", pdfStyle);
		
		pdfStyle = new PdfStyle("borderWidthTop");
		styleMapping.put("borderTop", pdfStyle);
		
		pdfStyle = new PdfStyle("borderWidthBottom");
		styleMapping.put("borderBottom", pdfStyle);
		
		pdfStyle = new PdfStyle("borderWidthLeft");
		styleMapping.put("borderLeft", pdfStyle);
		
		pdfStyle = new PdfStyle("borderWidthRight");
		styleMapping.put("borderRight", pdfStyle);
		
		pdfStyle = new PdfStyle("borderColorTop");
		pdfStyle.setOptions(colorMap);
		styleMapping.put("topBorderColor", pdfStyle);
		
		pdfStyle = new PdfStyle("borderColorBottom");
		pdfStyle.setOptions(colorMap);
		styleMapping.put("bottomBorderColor", pdfStyle);
		
		pdfStyle = new PdfStyle("borderColorLeft");
		pdfStyle.setOptions(colorMap);
		styleMapping.put("leftBorderColor", pdfStyle);
		
		pdfStyle = new PdfStyle("borderColorRight");
		pdfStyle.setOptions(colorMap);
		styleMapping.put("rightBorderColor", pdfStyle);
		
		pdfStyle = new PdfStyle("backgroundColor");
		pdfStyle.setOptions(colorMap);
		styleMapping.put("backgroundColor", pdfStyle);
		
		pdfStyle = new PdfStyle("horizontalHorizontalAlignment");
		pdfStyle.addOption("ALIGN_CENTER", Element.ALIGN_CENTER);
		pdfStyle.addOption("ALIGN_LEFT", Element.ALIGN_LEFT);
		pdfStyle.addOption("ALIGN_RIGHT", Element.ALIGN_RIGHT);
		styleMapping.put("alignment", pdfStyle);
		
		pdfStyle = new PdfStyle("verticalHorizontalAlignment");
		pdfStyle.addOption("VERTICAL_CENTER", Element.ALIGN_MIDDLE);
		pdfStyle.addOption("VERTICAL_TOP", Element.ALIGN_TOP);
		pdfStyle.addOption("VERTICAL_BOTTOM", Element.ALIGN_BOTTOM);
		styleMapping.put("verticalAlignment", pdfStyle);
		
		pdfStyle = new PdfStyle("indention");
		styleMapping.put("indention", pdfStyle);
		
		pdfStyle = new PdfStyle("wrapText");
		styleMapping.put("wrapText", pdfStyle);
	}
	
	public static Font createFont(Map<String, CssClass> cssClassMap, String cssClass) throws DocumentException, IOException {
		PdfCss css = buildWordCss(cssClassMap, cssClass);
		float size = css.getSize();
		int style = css.getStyle();
		
		BaseFont baseFont = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font font = new Font(baseFont, size, style);
		font.setColor(css.getColor());
		
		return font;
	}
	
	public static void setTableStyle(Table table, Map<String, CssClass> cssClassMap, String cssClass) {
		PdfCss css = buildWordCss(cssClassMap, cssClass);
		
		table.setAlignment(css.getHorizontalHorizontalAlignment());
		table.setBackgroundColor(css.getBackgroundColor());
//		table.setBorderColorTop(css.getBorderColorTop());
//		table.setBorderColorBottom(css.getBorderColorBottom());
//		table.setBorderColorLeft(css.getBorderColorLeft());
//		table.setBorderColorRight(css.getBorderColorRight());
//		table.setBorderWidthTop(css.getBorderWidthTop());
//		table.setBorderWidthBottom(css.getBorderWidthBottom());
//		table.setBorderWidthLeft(css.getBorderWidthLeft());
//		table.setBorderColorRight(css.getBorderColorRight());
	}
	
	public static void setCellStyle(Cell cell, Map<String, CssClass> cssClassMap, String cssClass) {
		PdfCss css = buildWordCss(cssClassMap, cssClass);
		
		cell.setHorizontalAlignment(css.getHorizontalHorizontalAlignment());
		cell.setVerticalAlignment(css.getVerticalHorizontalAlignment());
		cell.setBackgroundColor(css.getBackgroundColor());
//		cell.setBorderColorTop(css.getBorderColorTop());
//		cell.setBorderColorBottom(css.getBorderColorBottom());
//		cell.setBorderColorLeft(css.getBorderColorLeft());
//		cell.setBorderColorRight(css.getBorderColorRight());
//		cell.setBorderWidthTop(css.getBorderWidthTop());
//		cell.setBorderWidthBottom(css.getBorderWidthBottom());
//		cell.setBorderWidthLeft(css.getBorderWidthLeft());
//		cell.setBorderWidthRight(css.getBorderWidthRight());
	}
	
	private static PdfCss buildWordCss(Map<String, CssClass> cssClassMap, String cssClass) {
		PdfCss css = new PdfCss();
		
		Collection<Style> collection = StyleUtils.getReportStyle(cssClassMap, ReportManager.REPORT_TYPE_WORD, cssClass);
		for (Style style : collection) {
			String styleName = style.getName();
			Object value = style.getValue();
			
			PdfStyle pdfStyle = styleMapping.get(styleName);
			if (pdfStyle == null)
				continue;
			
			String mappingName = pdfStyle.getMappingName();
			value = pdfStyle.getValue(value);
			
			try {
				PropertyUtils.setProperty(css, mappingName, value);
			} catch (Exception e) {
				logger.warn("读入样式[" + styleName + "]值[" + value.toString() + "]失败，忽略该样式－" + e);
			}
		}
		
		return css;
	}
}

class PdfStyle {
	private String mappingName;
	private Map<String, Object> options = new HashMap<String, Object>();

	public PdfStyle(String mappingName) {
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

