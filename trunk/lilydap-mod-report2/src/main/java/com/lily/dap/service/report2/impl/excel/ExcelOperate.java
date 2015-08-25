/**
 * 
 */
package com.lily.dap.service.report2.impl.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;

import com.lily.dap.service.report2.impl.model.Cell;
import com.lily.dap.service.report2.impl.model.Chart;
import com.lily.dap.service.report2.impl.model.CssClass;
import com.lily.dap.service.report2.impl.model.Row;
import com.lily.dap.service.report2.impl.model.Table;
import com.lily.dap.service.report2.util.StyleUtils;

/**
 * @author xuemozou
 *
 * 生成Excel数据
 */
public class ExcelOperate {
	private Map<String, HSSFCellStyle> styleMap = new HashMap<String, HSSFCellStyle>();
	
	public void generate(OutputStream result, String fileName, Table table, Map<String, CssClass> cssClassMap, Map<String, String> config) throws IOException {
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		HSSFSheet hssfSheet = hssfWorkbook.createSheet();
		HSSFPatriarch hssfPatriarch = null;
		
		hssfWorkbook.setSheetName(0, fileName);
		
		int index = 0;
		for (Integer width : table.getWidths()) 
			hssfSheet.setColumnWidth(index++, width * 36);
		
		int currRow = 0, rows = table.getData().size();
		int orgCol, currCol = 0, cols = table.getWidths().size();
		
    	//占位符，作为判断每行每列是否需要添加单元格的依据
		HSSFCellStyle[][] placeholder = new HSSFCellStyle[rows][cols];
		
		HSSFRow hssfRow;
		HSSFCell hssfCell;
		for (Row row : table.getData()) {
			hssfRow = hssfSheet.createRow(currRow);
			hssfRow.setHeight((short)(row.getHeight() * 16));
			
			currCol = 0;
			for (Cell cell : row.getData()) {
				HSSFCellStyle cellStyle = getHSSFCellStyle(hssfWorkbook, cssClassMap, table, row, cell);
				
				orgCol = currCol;
		    	while (placeholder[currRow][currCol] != null)
		    		currCol++;
				
				for (;orgCol < currCol; orgCol++) {
					hssfCell = hssfRow.createCell(orgCol);
					hssfCell.setCellStyle(placeholder[currRow][orgCol]);
				}
				
				int rowSpan = cell.getRowSpan();
				int colSpan = cell.getColSpan();
				
				if (rowSpan > 1 || colSpan > 1)
					hssfSheet.addMergedRegion(new CellRangeAddress(currRow, currRow + rowSpan - 1, currCol, currCol + colSpan - 1));
				
				hssfCell = hssfRow.createCell(currCol);
				hssfCell.setCellStyle(cellStyle);
				
				Object context = cell.getContext();
				if (context instanceof Double)
					hssfCell.setCellValue((Double)context);
				else if (context instanceof Float)
					hssfCell.setCellValue((Float)context);
				else if (context instanceof Integer)
					hssfCell.setCellValue((Integer)context);
				else if (context instanceof Date)
					hssfCell.setCellValue((Date)context);
				else if (context instanceof Boolean)
					hssfCell.setCellValue((Boolean)context);
				else if (context instanceof byte[]) {	//这是图表数据
					if (hssfPatriarch == null)
						hssfPatriarch = hssfSheet.createDrawingPatriarch();
					
					int picIndex = hssfWorkbook.addPicture((byte[])context, HSSFWorkbook.PICTURE_TYPE_JPEG);
//					HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) currCol, currRow, (short) (currCol + colSpan), currRow + rowSpan);// 控制图片的左上角,右下角的位置
					Chart chartConfig = cell.getChart();
					HSSFClientAnchor anchor = createClientAnchor((short) currCol, currRow, (short) (currCol + colSpan), currRow + rowSpan, chartConfig.getWidth(), chartConfig.getHeight(), HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER);// 控制图片的左上角,右下角的位置
					
					hssfPatriarch.createPicture(anchor, picIndex);
				} else {
					if (context == null)
						context = "";
					
					String text = processText(context.toString());
					
					hssfCell.setCellValue(new HSSFRichTextString(text));
				}
				
		    	for (int i = rowSpan - 1; i >= 0; i--)
		    		for (int j = colSpan - 1; j >= 0; j--)
		    			placeholder[currRow + i][currCol + j] = cellStyle;
		    	
		    	orgCol = currCol + 1;
		    	currCol = currCol + colSpan;
				for (;orgCol < currCol; orgCol++) {
					hssfCell = hssfRow.createCell(orgCol);
					hssfCell.setCellStyle(placeholder[currRow][orgCol]);
				}
			}
			
			for (;currCol < cols; currCol++) {
				HSSFCell cell = hssfRow.createCell(currCol);
				cell.setCellStyle(placeholder[currRow][currCol] == null ? getHSSFCellStyle(hssfWorkbook, cssClassMap, table, null, null) : placeholder[currRow][currCol]);
			}
			
			currRow++;
		}
		
		hssfWorkbook.write(result);
	}
	
	private HSSFClientAnchor createClientAnchor(short col1, int row1, short col2, int row2, float width, float height, short align, short verticalAlign) {
		HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) col1, row1, (short) col2, row2);
		return anchor;
	}
	
	/**
	 * 返回或者创建指定样式类的Excel单元格样式，其中样式类可以从表体、当前行、当前单元格中得到，他们之间实现继承关系
	 * 
	 * @param hssfWorkbook
	 * @param cssClassMap
	 * @param table
	 * @param row
	 * @param cell
	 * @return
	 */
	private HSSFCellStyle getHSSFCellStyle(HSSFWorkbook hssfWorkbook, Map<String, CssClass> cssClassMap, Table table, Row row, Cell cell) {
		String cssClass = StyleUtils.mergeCssClass(
				table == null ? null : table.getCssClass(), 
				row == null ? null : row.getCssClass(), 
				cell == null ? null : cell.getCssClass());
		
		HSSFCellStyle hssfCellStyle;
		if (styleMap.containsKey(cssClass))
			hssfCellStyle = styleMap.get(cssClass);
		else {
			hssfCellStyle = StyleOperate.buildHSSFCellStyle(hssfWorkbook, cssClassMap, cssClass);
			
			styleMap.put(cssClass, hssfCellStyle);
		}
		
		return hssfCellStyle;
	}
	
	private String processText(String text) {
		text = text.replaceAll("<BR>", "\n");
		text = text.replaceAll("<br>", "\n");
		text = text.replaceAll("<[/]*[^>]*>","");
		text = text.replaceAll("&nbsp;", " ");
		
		return text;
	}
	
	/*public static void main(String[] args) throws IOException {
		List<Integer> widths = new ArrayList<Integer>();
		widths.add(100);
		widths.add(200);
		widths.add(300);
		widths.add(400);
		
		Table table = new Table(widths, "report", null, null);
		
		Row row = new Row(50, null, null, null);
		table.addData(row);
		
		Cell cell = new Cell(1, 4, "title", null, null, null, null, null, -1, null, "我的报表");
		row.addData(cell);
		
		row = new Row(25, "header", null, null);
		table.addData(row);
		
		cell = new Cell(1, 2, null, null, null, null, null, null, -1, null, "蔬菜");
		row.addData(cell);
		cell = new Cell(1, 2, null, null, null, null, null, null, -1, null, "水果");
		row.addData(cell);
		
		row = new Row(25, null, null, null);
		table.addData(row);
		
		cell = new Cell(1, 1, null, null, null, null, null, null, -1, null, "白菜");
		row.addData(cell);
		cell = new Cell(1, 1, null, null, null, null, null, null, -1, null, "萝卜");
		row.addData(cell);
		cell = new Cell(1, 1, null, null, null, null, null, null, -1, null, "苹果");
		row.addData(cell);
		cell = new Cell(1, 1, null, null, null, null, null, null, -1, null, "香蕉");
		row.addData(cell);
		
		//-----------------------------------------------------------------
		Map<String, CssClass> cssClassMap = new HashMap<String, CssClass>();
		CssClass cssClass = new CssClass("report", "");
		cssClass.addStyle("borderTop", null, "short", "1");
		cssClass.addStyle("borderBottom", null, "short", "1");
		cssClass.addStyle("borderLeft", null, "short", "1");
		cssClass.addStyle("borderRight", null, "short", "1");
		cssClassMap.put("report", cssClass);
		
		cssClass = new CssClass("title", "");
		cssClass.addStyle("borderTop", null, "short", "0");
		cssClass.addStyle("borderBottom", null, "short", "0");
		cssClass.addStyle("borderLeft", null, "short", "0");
		cssClass.addStyle("borderRight", null, "short", "0");
		cssClass.addStyle("fontHeight", null, "short", "12");
		cssClassMap.put("title", cssClass);
		
		cssClass = new CssClass("header", "");
		cssClass.addStyle("boldweight", null, "short", "BOLDWEIGHT_BOLD");
		cssClass.addStyle("backgroundColor", null, "short", "COLOR_GRAY");
		cssClassMap.put("header", cssClass);
		
		//-----------------------------------------------------------------
    	FileOutputStream fo = new FileOutputStream("./example.xls");
    	
    	ExcelOperate operate = new ExcelOperate();
    	operate.generate(fo, "我的报表", table, cssClassMap, null);
    	fo.close();
    	
    	System.out.println("build ok!!!");
	}*/
}
