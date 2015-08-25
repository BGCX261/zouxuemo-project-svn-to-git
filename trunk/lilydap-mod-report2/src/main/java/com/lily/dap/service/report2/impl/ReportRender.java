/**
 * 
 */
package com.lily.dap.service.report2.impl;

import java.io.OutputStream;
import java.util.Map;

import com.lily.dap.service.report2.ReportManager;
import com.lily.dap.service.report2.impl.excel.ExcelOperate;
import com.lily.dap.service.report2.impl.html.HtmlOperate;
import com.lily.dap.service.report2.impl.model.CssClass;
import com.lily.dap.service.report2.impl.model.Table;
import com.lily.dap.service.report2.impl.pdf.PdfOperate;
import com.lily.dap.service.report2.impl.word.WordOperate;

/**
 * @author xuemozou
 *
 * ����ָ��Ҫ��HTML��EXCEL��WORD���ı���������
 */
public class ReportRender {
	public static void renderReport(String fileName, Table table, Map<String, CssClass> cssClassMap, String reportType, Map<String, Map<String, String>>configMap, OutputStream os) {
		Map<String, String> config = configMap.get(reportType);
		
		try {
			if (ReportManager.REPORT_TYPE_HTML.equals(reportType)) {
				HtmlOperate operate = new HtmlOperate();
				
				operate.generate(os, fileName, table, cssClassMap, config);
			} else if (ReportManager.REPORT_TYPE_EXCEL.equals(reportType)) {
				ExcelOperate operate = new ExcelOperate();
				
				operate.generate(os, fileName, table, cssClassMap, config);
			} else if (ReportManager.REPORT_TYPE_WORD.equals(reportType)) {
				WordOperate operate = new WordOperate();
				
				operate.generate(os, fileName, table, cssClassMap, config);
			} else if (ReportManager.REPORT_TYPE_PDF.equals(reportType)) {
				PdfOperate operate = new PdfOperate();
				
				operate.generate(os, fileName, table, cssClassMap, config);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new RuntimeException("��Ⱦ����[" + fileName + "]����ʱ����" + e.getMessage());
		}
	}
}
