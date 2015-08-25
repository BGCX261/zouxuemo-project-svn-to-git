/**
 * 
 */
package com.lily.dap.service.report2.impl.html;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lily.dap.service.report2.impl.model.Cell;
import com.lily.dap.service.report2.impl.model.CssClass;
import com.lily.dap.service.report2.impl.model.Row;
import com.lily.dap.service.report2.impl.model.Table;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author xuemozou
 *
 * 生成HTML数据
 */
public class HtmlOperate {
	private static Template htmlTemplate;
	static {
		try {
			Configuration cfg = new Configuration();
			
//			File dir = new File(HtmlOperate.class.getResource("").getPath());
//			cfg.setDirectoryForTemplateLoading(dir);
//			System.out.println("dir=" + dir);
			
			cfg.setClassForTemplateLoading(HtmlOperate.class, "/com/lily/dap/service/report2/impl/html/");
//			cfg.setClassForTemplateLoading(HtmlOperate.class, "c:\\");
			htmlTemplate = cfg.getTemplate("html.ftl");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void generate(OutputStream result, String fileName, Table table, Map<String, CssClass> cssClassMap, Map<String, String> config) throws IOException {
		SimpleHash root = new SimpleHash();
		root.put("table", table);
		String htmlStyle = StyleOperate.buildHtmlStyle(cssClassMap);
		root.put("style", htmlStyle);
		
		OutputStreamWriter writer = new OutputStreamWriter(result);
		
		try {
			htmlTemplate.process(root, writer);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		List<Integer> widths = new ArrayList<Integer>();
		widths.add(100);
		widths.add(200);
		widths.add(300);
		widths.add(400);
		
		Table table = new Table(widths, "report", null, null, null);
		
		Row row = new Row(50, null, null, null);
		table.addData(row);
		
		Cell cell = new Cell(1, 4, "title", null, null, null, null, null, -1, null, "我的报表", "<a href=\"javascript:window.print()\">打印</a>");
		row.addData(cell);
		
		row = new Row(25, "header", null, null);
		table.addData(row);
		
		cell = new Cell(1, 2, null, null, null, null, null, null, -1, null, "蔬菜","return \"<a href=javascript:window.print()>打印</a>\"");
		row.addData(cell);
		cell = new Cell(1, 2, null, null, null, null, null, null, -1, null, "<a href=\"javascript:window.print()\">打印</a>","");
		row.addData(cell);
		
		row = new Row(25, null, null, null);
		table.addData(row);
		
		cell = new Cell(1, 1, null, null, null, null, null, null, -1, null, "白菜","<a href=\"javascript:window.print()\">打印</a>");
		row.addData(cell);
		cell = new Cell(1, 1, null, null, null, null, null, null, -1, null, "萝卜","<a href=\"javascript:window.print()\">打印</a>");
		row.addData(cell);
		cell = new Cell(1, 1, null, null, null, null, null, null, -1, null, "苹果","<a href=\"javascript:window.print()\">打印</a>");
		row.addData(cell);
		cell = new Cell(1, 1, null, null, null, null, null, null, -1, null, "香蕉","<a href=\"javascript:window.print()\">打印</a>");
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
    	FileOutputStream fo = new FileOutputStream("c:/example.html");
    	
    	HtmlOperate operate = new HtmlOperate();
    	operate.generate(fo, "我的报表", table, cssClassMap, null);
    	fo.close();
    	
    	System.out.println("build ok!!!");
	}
}
