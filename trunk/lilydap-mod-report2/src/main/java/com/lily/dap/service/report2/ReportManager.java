/**
 * 
 */
package com.lily.dap.service.report2;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.lily.dap.dao.Condition;
import com.lily.dap.service.report2.impl.ReportConfig;
import com.lily.dap.service.report2.impl.model.CssClass;

/**
 * 报表管理接口，实现报表环境的初始化、报表的装载和指定格式的输入
 * 
 * @author zouxuemo
 *
 */
public interface ReportManager {
	public static final String REPORT_TYPE_HTML = "html";
	public static final String REPORT_TYPE_EXCEL = "excel";
	public static final String REPORT_TYPE_WORD = "word";
	public static final String REPORT_TYPE_PDF = "pdf";
	
	/**
	 * 指定报表模板输出报表（Html格式、Excel格式、Word格式、Pdf格式）到指定的输出流
	 * <p>报表生成后会缓存报表中的图表数据，并在调用outChart或者1分钟后自动从缓存中删除
	 * 
	 * @param reportName 要输出的报表模版名称
	 * @param reportType 要输出的报表类型
	 * @param condition 输出报表所需的查询条件
	 * @param param 传入报表需要的参数Map（可能传入的参数包括：year-统计年份,month-统计月份,departCode-统计部门,currPersonName-当前登录用户名,currDepartName-当前用户所在部门, currDate-当前日期）
	 * @param os 要输出至的流对象
	 */
	public void outReport(String reportTemplateName, String reportType, Condition condition, Map<String, Object> param, OutputStream os);
	
	/**
	 * 指定报表模板输出报表（Html格式、Excel格式、Word格式、Pdf格式）到指定的输出流
	 * <p>报表生成后会缓存报表中的图表数据，并在调用outChart或者1分钟后自动从缓存中删除
	 * 
	 * @param reportConfig 要输出的报表模版
	 * @param reportType 要输出的报表类型
	 * @param condition 输出报表所需的查询条件
	 * @param param 传入报表需要的参数Map（可能传入的参数包括：year-统计年份,month-统计月份,departCode-统计部门,currPersonName-当前登录用户名,currDepartName-当前用户所在部门, currDate-当前日期）
	 * @param os 要输出至的流对象 传入报表需要的参数Map（可能传入的参数包括：year-统计年份,month-统计月份,departCode-统计部门,currPersonName-当前登录用户名,currDepartName-当前用户所在部门, currDate-当前日期）
	 */
	public void outReport(ReportConfig reportConfig, String reportType, Condition condition, Map<String, Object> param, OutputStream os);
	
	/**
	 * 指定报表模板和报表数据输出报表（Html格式、Excel格式、Word格式、Pdf格式）到指定的输出流
	 * <p>报表生成后会缓存报表中的图表数据，并在调用outChart或者1分钟后自动从缓存中删除
	 * 
	 * @param reportConfig 要输出的报表模版
	 * @param reportType 要输出的报表类型
	 * @param param 传入报表需要的参数Map（可能传入的参数包括：year-统计年份,month-统计月份,departCode-统计部门,currPersonName-当前登录用户名,currDepartName-当前用户所在部门, currDate-当前日期）
	 * @param datasourceMap 给定的数据源（数据源名称和数据源数据形成的键值对Map）
	 * @param os 要输出至的流对象
	 */
	
	public void outReportWithData(ReportConfig reportConfig, String reportType, Map<String, Object> param, Map<String, List<Map<String, Object>>> datasourceMap, OutputStream os);
	
	/**
	 * 输出先前生成报表中缓存的图表数据至指定的输出流，并删除图表数据
	 * 
	 * @param tableUID
	 * @param chartUID
	 * @param os
	 */
	public void outChart(String tableUID, int chartUID, OutputStream os);
	
	/**
	 * 返回所有报表模版名称
	 * 
	 * @return
	 */
	public String[] getReportConfigNames();
	
	/**
	 * 返回指定报表模版的说明
	 * 
	 * @param name
	 * @return
	 */
	public String getReportConfigDes(String name);
	
	/**
	 * 返回指定报表模版的导出文件的文件名
	 * 
	 * @param name
	 * @param param 传入报表需要的参数Map（可能传入的参数包括：year-统计年份,month-统计月份,departCode-统计部门,currPersonName-当前登录用户名,currDepartName-当前用户所在部门, currDate-当前日期）
	 * @return
	 */
	public String getReportExportFileName(String name, Map<String, Object> param);
	
	/**
	 * 返回指定报表模版
	 * 
	 * @param name
	 * @return
	 */
	public ReportConfig getReportConfig(String name);
	
	/**
	 * 新建一个从给定模板中克隆出来的新报表模板
	 * 
	 * @param name
	 * @return
	 */
	public ReportConfig cloneReportConfig(String name);
	
	/**
	 * 添加新的样式类
	 * 
	 * @param cssClass
	 */
	public void addCssClass(CssClass cssClass);
	
	/**
	 * 删除给定类名的样式类
	 * 
	 * @param className
	 */
	public CssClass removeCssClass(String className);
	
	/**
	 * 获取给定类名的样式类
	 * 
	 * @param className
	 * @return
	 */
	public CssClass getCssClass(String className);
	
	/**
	 * 返回一个扩展了给定类命名样式类的新样式类
	 * 
	 * @param className
	 * @param extendClassName
	 * @return
	 */
	public CssClass extendCssClass(String className, String extendClassName);
}
