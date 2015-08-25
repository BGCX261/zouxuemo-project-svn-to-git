/**
 * 
 */
package com.lily.dap.service.report2.impl.excel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.lily.dap.service.report2.ReportManager;
import com.lily.dap.service.report2.impl.model.CssClass;
import com.lily.dap.service.report2.impl.model.CssClass.Style;
import com.lily.dap.service.report2.util.StyleUtils;

/**
 * @author xuemozou
 *
 */
public class StyleOperate {
    private static final Log logger = LogFactory.getLog(StyleOperate.class);
    
/*
			fontName = 宋体
			fontHeight = 9
			boldweight = BOLDWEIGHT_NORMAL
			italic = false
			color = COLOR_NORMAL
			strikeout = false
			underline = U_NONE
			borderTop = 0
			borderBottom = 0
			borderLeft = 0
			borderRight = 0
			topBorderColor = COLOR_BLACK
			bottomBorderColor = COLOR_BLACK
			leftBorderColor = COLOR_BLACK
			rightBorderColor = COLOR_BLACK
			backgroundColor = COLOR_WHITE
			alignment = ALIGN_CENTER
			verticalAlignment = VERTICAL_CENTER
			indention = 0
			wrapText = true
*/	
	private static Map<String, ExcelStyle> styleMapping = new HashMap<String, ExcelStyle>();
	static {
		ExcelStyle excelStyle;
		
		excelStyle = new ExcelStyle("fontName");
		styleMapping.put("fontName", excelStyle);
		
		excelStyle = new ExcelStyle("fontHeightInPoints");
		styleMapping.put("fontHeight", excelStyle);
		
		excelStyle = new ExcelStyle("boldweight");
		excelStyle.addOption("BOLDWEIGHT_NORMAL", HSSFFont.BOLDWEIGHT_NORMAL);
		excelStyle.addOption("BOLDWEIGHT_BOLD", HSSFFont.BOLDWEIGHT_BOLD);
		styleMapping.put("boldweight", excelStyle);
		
		excelStyle = new ExcelStyle("italic");
		styleMapping.put("italic", excelStyle);

		Map<String, Object> colorMap = new HashMap<String, Object>();
		colorMap.put("COLOR_NORMAL", HSSFFont.COLOR_NORMAL);
		colorMap.put("COLOR_BLACK", HSSFColor.BLACK.index);
		colorMap.put("COLOR_WHITE", HSSFColor.WHITE.index);
		colorMap.put("COLOR_RED", HSSFColor.RED.index);
		colorMap.put("COLOR_BLUE", HSSFColor.BLUE.index);
		colorMap.put("COLOR_GRAY", HSSFColor.GREY_25_PERCENT.index);
		
		excelStyle = new ExcelStyle("color");
		excelStyle.setOptions(colorMap);
		styleMapping.put("color", excelStyle);
		
		excelStyle = new ExcelStyle("strikeout");
		styleMapping.put("strikeout", excelStyle);
		
		excelStyle = new ExcelStyle("underline");
		excelStyle.addOption("U_NONE", HSSFFont.U_NONE);
		excelStyle.addOption("U_SINGLE", HSSFFont.U_SINGLE);
		styleMapping.put("underline", excelStyle);
		
		excelStyle = new ExcelStyle("borderTop");
		styleMapping.put("borderTop", excelStyle);
		
		excelStyle = new ExcelStyle("borderBottom");
		styleMapping.put("borderBottom", excelStyle);
		
		excelStyle = new ExcelStyle("borderLeft");
		styleMapping.put("borderLeft", excelStyle);
		
		excelStyle = new ExcelStyle("borderRight");
		styleMapping.put("borderRight", excelStyle);
		
		excelStyle = new ExcelStyle("topBorderColor");
		excelStyle.setOptions(colorMap);
		styleMapping.put("topBorderColor", excelStyle);
		
		excelStyle = new ExcelStyle("bottomBorderColor");
		excelStyle.setOptions(colorMap);
		styleMapping.put("bottomBorderColor", excelStyle);
		
		excelStyle = new ExcelStyle("leftBorderColor");
		excelStyle.setOptions(colorMap);
		styleMapping.put("leftBorderColor", excelStyle);
		
		excelStyle = new ExcelStyle("rightBorderColor");
		excelStyle.setOptions(colorMap);
		styleMapping.put("rightBorderColor", excelStyle);
		
		excelStyle = new ExcelStyle("backgroundColor");
		excelStyle.setOptions(colorMap);
		styleMapping.put("backgroundColor", excelStyle);
		
		excelStyle = new ExcelStyle("alignment");
		excelStyle.addOption("ALIGN_CENTER", HSSFCellStyle.ALIGN_CENTER);
		excelStyle.addOption("ALIGN_LEFT", HSSFCellStyle.ALIGN_LEFT);
		excelStyle.addOption("ALIGN_RIGHT", HSSFCellStyle.ALIGN_RIGHT);
		styleMapping.put("alignment", excelStyle);
		
		excelStyle = new ExcelStyle("verticalAlignment");
		excelStyle.addOption("VERTICAL_CENTER", HSSFCellStyle.VERTICAL_CENTER);
		excelStyle.addOption("VERTICAL_TOP", HSSFCellStyle.VERTICAL_TOP);
		excelStyle.addOption("VERTICAL_BOTTOM", HSSFCellStyle.VERTICAL_BOTTOM);
		styleMapping.put("verticalAlignment", excelStyle);
		
		excelStyle = new ExcelStyle("indention");
		styleMapping.put("indention", excelStyle);
		
		excelStyle = new ExcelStyle("wrapText");
		styleMapping.put("wrapText", excelStyle);
	}
	
	public static HSSFCellStyle buildHSSFCellStyle(HSSFWorkbook hssfWorkbook, Map<String, CssClass> cssClassMap, String cssClass) {
		ExcelCss css = new ExcelCss();
		
		Collection<Style> collection = StyleUtils.getReportStyle(cssClassMap, ReportManager.REPORT_TYPE_EXCEL, cssClass);
		for (Style style : collection) {
			String styleName = style.getName();
			Object value = style.getValue();
			
			ExcelStyle excelStyle = styleMapping.get(styleName);
			if (excelStyle == null)
				continue;
			
			String mappingName = excelStyle.getMappingName();
			value = excelStyle.getValue(value);
			
			try {
				PropertyUtils.setProperty(css, mappingName, value);
			} catch (Exception e) {
				logger.warn("读入样式[" + styleName + "]值[" + value.toString() + "]失败，忽略该样式－" + e);
			}
		}
		
		return buildHSSFCellStyle(hssfWorkbook, css);
	}
	
	private static HSSFCellStyle buildHSSFCellStyle(HSSFWorkbook hssfWorkbook, ExcelCss css) {
		HSSFCellStyle cellStyle;
		
		cellStyle = hssfWorkbook.createCellStyle();
		cellStyle.setAlignment(css.getAlignment());
		cellStyle.setVerticalAlignment(css.getVerticalAlignment());
		cellStyle.setBorderTop(css.getBorderTop());
		cellStyle.setBorderBottom(css.getBorderBottom());
		cellStyle.setBorderLeft(css.getBorderLeft());
		cellStyle.setBorderRight(css.getBorderRight());
		cellStyle.setTopBorderColor(css.getTopBorderColor());
		cellStyle.setBottomBorderColor(css.getBottomBorderColor());
		cellStyle.setLeftBorderColor(css.getLeftBorderColor());
		cellStyle.setRightBorderColor(css.getRightBorderColor());
		cellStyle.setFillForegroundColor(css.getBackgroundColor());
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setWrapText(css.isWrapText());
		cellStyle.setIndention(css.getIndention());
		
		HSSFFont font = hssfWorkbook.createFont();
		font.setFontName(css.getFontName());
		font.setFontHeightInPoints(css.getFontHeightInPoints());
		font.setBoldweight(css.getBoldweight());
		font.setItalic(css.isItalic());
		font.setStrikeout(css.isStrikeout());
		font.setUnderline(css.getUnderline());
		font.setColor(css.getColor());
		cellStyle.setFont(font);
		
		return cellStyle;
	}
}

class ExcelStyle {
	private String mappingName;
	private Map<String, Object> options = new HashMap<String, Object>();

	public ExcelStyle(String mappingName) {
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

