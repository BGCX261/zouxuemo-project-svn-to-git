package com.lily.dap.web.action.common;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.UnexpectedRollbackException;

import com.lily.dap.dao.Condition;
import com.lily.dap.entity.organize.Department;
import com.lily.dap.service.report2.ReportManager;
import com.lily.dap.web.action.BaseAction;
import com.lily.dap.web.security.RightUtils;
import com.lily.dap.web.util.Struts2Utils;

@Results( {@Result(name = "success", location = "/WEB-INF/page/system/reportMgr.jsp")})
public class ReportAction extends BaseAction {
	private static final long serialVersionUID = 3948105094172037361L;
	
	@Autowired
	private ReportManager reportManager;
	
	/**
	 * 默认转向报表的页面
	 */
    public String execute() throws Exception {
		return SUCCESS;
    }    
	
	public String html() throws Exception {
		String actionType = Struts2Utils.getParameter("actionType");
		
		HttpServletResponse response = Struts2Utils.getResponse();
		HttpServletRequest request = Struts2Utils.getRequest();
		
		// 生成HTML格式报表
		response.setContentType("text/html; charset=GBK");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		Map<String, Object> params = constructParams();
		String title = report("html", request, params, baos);
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("	<head>");
		out.println("		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gbk\">");
		out.println("	<title>" + title + "</title>");
		out.println("	</head>");
		out.println("	<body>");
		out.println(new String(baos.toByteArray(), "GBK"));
		out.println("	</body>");
		
		//如果actionType为print，则进行打印。
		if(actionType.equals("print")){
			out.println("<OBJECT classid='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2' height=0 id=factory name=factory width=0></OBJECT>");
			out.println("<script language='javascript'>");
			out.println("	self.moveTo(0,0)");
			out.println("	self.resizeTo(screen.availWidth,screen.availHeight)");
			out.println("	factory.execwb(7, 1);");
			out.println("	window.opener=null;");
			out.println("	window.close();");
			out.println("</script>");		
		}
		
		out.println("</html>");
		baos.close();
		
		return null;
	}
	
	public String excel() throws Exception {
		HttpServletResponse response = Struts2Utils.getResponse();
		HttpServletRequest request = Struts2Utils.getRequest();
		
		// 生成Excel格式报表
		OutputStream os = response.getOutputStream();
		response.setBufferSize(1000000);
		
		String reportTemplateName = request.getParameter("reportTemplateName");
		
		Map<String, Object> params = constructParams();
		String fileName = reportManager.getReportExportFileName(reportTemplateName, params);
		if (fileName != null) {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes(), "ISO8859-1") + ".xls");
			
			try {
				report("excel", request, params, os);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}	
	
	private String report(String reportType, HttpServletRequest request, Map<String, Object> params, OutputStream os)
			throws Exception {
		String reportTemplateName = request.getParameter("reportTemplateName");
		
		Condition condition = Struts2Utils.buildCondition(null);
		
		try {
			reportManager.outReport(reportTemplateName, reportType, condition, params, os);
		} catch (UnexpectedRollbackException e) {
			e.printStackTrace();
		}
		
		return reportManager.getReportConfigDes(reportTemplateName);
	}
	
	private Map<String, Object> constructParams() {
		Map<String, Object> params = new HashMap<String, Object>();
		
		List<Department> departments = RightUtils.getCurrPersonsDepartments();
		Department department = null;
		if (departments != null && departments.size() > 0) {
			department = departments.get(0);
			params.put("currDepartName", department.getName());
			params.put("currDepartLevel", department.getLevel());
		}
		
		Date currDate = new Date(System.currentTimeMillis());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");		

		// 在参数Map中加入当前操作人、操作人所在单位、所在层、以及当前日期
		params.put("currPersonName", RightUtils.getCurrPerson().getName());
		params.put("currPersonUsername", RightUtils.getCurrPerson().getUsername());
		params.put("currPersonId", RightUtils.getCurrPerson().getId());	
		
		params.put("reportManId", RightUtils.getCurrPerson().getId());		
		params.put("currDate", df.format(currDate));
		
		return params;
	}
}
