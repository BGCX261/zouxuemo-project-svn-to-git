package com.lily.dap.service.report2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.lily.dap.dao.Condition;
import com.lily.dap.test.ServiceTest;

@ContextConfiguration
public class ReportManagerTest extends ServiceTest {
	@Autowired
	private ReportManager reportManager;
	
	@Test
	public void testOutReportStringStringConditionMapOfStringObjectOutputStream() {
		FileOutputStream fo = null;

		String reportConfigName = "roster";
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("unit", "直属征收局");
		
		Condition condition = Condition.create();
		condition.llike("name", "王");
				
		try {
			fo = new FileOutputStream("./export/" + reportConfigName + ".html");
			reportManager.outReport(reportConfigName, ReportManager.REPORT_TYPE_HTML, condition, param, fo);
			
			fo = new FileOutputStream("./export/" + reportConfigName + ".xls");
			reportManager.outReport(reportConfigName, ReportManager.REPORT_TYPE_EXCEL, condition, param, fo);
			
//			fo = new FileOutputStream("./export/" + reportConfigName + ".doc");
//			reportManager.outReport(reportConfigName, ReportManager.REPORT_TYPE_WORD, null, paramMap, fo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				fo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}