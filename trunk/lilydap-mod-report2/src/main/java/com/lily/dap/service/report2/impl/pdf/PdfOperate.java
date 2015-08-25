/**
 * 
 */
package com.lily.dap.service.report2.impl.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lily.dap.service.report2.impl.model.Cell;
import com.lily.dap.service.report2.impl.model.CssClass;
import com.lily.dap.service.report2.impl.model.Row;
import com.lily.dap.service.report2.impl.model.Table;
import com.lily.dap.service.report2.util.StyleUtils;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.RectangleReadOnly;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author xuemozou
 *
 * ����Word����
 */
public class PdfOperate {
	private Map<String, Font> fontMap = new HashMap<String, Font>();
	
	public void generate(OutputStream result, String fileName, Table table, Map<String, CssClass> cssClassMap, Map<String, String> config) throws IOException, DocumentException {
		Rectangle page = PageSize.A4;
		
		if (config != null) {
			String pageSize = config.get("pageSize");
			if (pageSize != null) {
				try {
					String[] tmp = pageSize.split(",");
					int width = Integer.parseInt(tmp[0]);
					int height = Integer.parseInt(tmp[1]);
					
					page = new RectangleReadOnly(width, height);
				} catch (NumberFormatException e) {}
			}
			
			String rotate = config.get("rotate");
			if (rotate != null && "true".equals(rotate))
				page = page.rotate();
		}
		
		// ����ֽ�Ŵ�С
		Document document = new Document(page);
		// ����һ����д��(Writer)��document���������ͨ����д��(Writer)���Խ��ĵ�д�뵽������
		PdfWriter.getInstance(document, result);
		document.open();
		
		int[] widths = new int[table.getWidths().size()];
		int index = 0;
		for (Integer width : table.getWidths())
			widths[index++] = width;

		com.lowagie.text.Table textTable = new com.lowagie.text.Table(widths.length);
		textTable.setWidths(widths);
		textTable.setWidth(100);
		textTable.setBorderWidth(0); // �߿���
		
		if (table.getCssClass() != null)
			StyleOperate.setTableStyle(textTable, cssClassMap, table.getCssClass());
		
		for (Row row : table.getData()) {
			for (Cell cell : row.getData()) {
				com.lowagie.text.Cell textCell = createCell(cssClassMap, table, row, cell, row.getHeight());
				
				textTable.addCell(textCell);
			}
		}
		
		document.add(textTable);
		document.close();
	}
	
	private com.lowagie.text.Cell createCell(Map<String, CssClass> cssClassMap, Table table, Row row, Cell cell, int height) throws DocumentException, IOException {
		String cssClass = StyleUtils.mergeCssClass(
				table == null ? null : table.getCssClass(), 
				row == null ? null : row.getCssClass(), 
				cell == null ? null : cell.getCssClass());

		Font font;
		if (fontMap.containsKey(cssClass))
			font = fontMap.get(cssClass);
		else {
			font = StyleOperate.createFont(cssClassMap, cssClass);
			
			fontMap.put(cssClass, font);
		}
		
		com.lowagie.text.Cell textCell;
		if (cell.getContext() instanceof byte[]) {
			Image img = Image.getInstance((byte[])cell.getContext());
			img.setAbsolutePosition(0, 0);
//			img.setAlignment(Image.RIGHT);// ����ͼƬ��ʾλ��
//			img.scaleAbsolute(12, 35);// ֱ���趨��ʾ�ߴ�
//			img.scalePercent(50);// ��ʾ��ʾ�Ĵ�СΪԭ�ߴ��50%
//			img.scalePercent(25, 12);// ͼ��߿����ʾ����
			
			textCell = new com.lowagie.text.Cell(img);
		} else {
			String context = processText(cell.getContext().toString());
			Phrase phrase = new Phrase(context, font);
			textCell = new com.lowagie.text.Cell(phrase);
		}
		
		StyleOperate.setCellStyle(textCell, cssClassMap, cssClass);
		
		textCell.setRowspan(cell.getRowSpan());
		textCell.setColspan(cell.getColSpan());
		
		return textCell;
	}
	
	private String processText(String text) {
		text = text.replaceAll("<BR>", "\n");
		text = text.replaceAll("<br>", "\n");
		text = text.replaceAll("<[/]*[^>]*>","");
		text = text.replaceAll("&nbsp;", " ");
		
		return text;
	}
	
	/*public static void main(String[] args) throws IOException, DocumentException {
		List<Integer> widths = new ArrayList<Integer>();
		widths.add(100);
		widths.add(200);
		widths.add(300);
		widths.add(400);
		
		Table table = new Table(widths, "report", null, null);
		
		Row row = new Row(50, null, null, null);
		table.addData(row);
		
		Cell cell = new Cell(1, 4, "title", null, null, null, null, null, -1, null, "�ҵı���");
		row.addData(cell);
		
		row = new Row(25, "header", null, null);
		table.addData(row);
		
		cell = new Cell(1, 2, null, null, null, null, null, null, -1, null, "�߲�");
		row.addData(cell);
		cell = new Cell(1, 2, null, null, null, null, null, null, -1, null, "ˮ��");
		row.addData(cell);
		
		row = new Row(25, null, null, null);
		table.addData(row);
		
		cell = new Cell(1, 1, null, null, null, null, null, null, -1, null, "�ײ�");
		row.addData(cell);
		cell = new Cell(1, 1, null, null, null, null, null, null, -1, null, "�ܲ�");
		row.addData(cell);
		cell = new Cell(1, 1, null, null, null, null, null, null, -1, null, "ƻ��");
		row.addData(cell);
		cell = new Cell(1, 1, null, null, null, null, null, null, -1, null, "�㽶");
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
    	FileOutputStream fo = new FileOutputStream("./example.pdf");
    	
    	PdfOperate operate = new PdfOperate();
    	operate.generate(fo, "�ҵı���", table, cssClassMap, null);
    	fo.close();
    	
    	System.out.println("build ok!!!");
	}*/
}
