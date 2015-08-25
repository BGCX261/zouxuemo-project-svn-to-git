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
 * �������ӿڣ�ʵ�ֱ������ĳ�ʼ���������װ�غ�ָ����ʽ������
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
	 * ָ������ģ���������Html��ʽ��Excel��ʽ��Word��ʽ��Pdf��ʽ����ָ���������
	 * <p>�������ɺ�Ỻ�汨���е�ͼ�����ݣ����ڵ���outChart����1���Ӻ��Զ��ӻ�����ɾ��
	 * 
	 * @param reportName Ҫ����ı���ģ������
	 * @param reportType Ҫ����ı�������
	 * @param condition �����������Ĳ�ѯ����
	 * @param param ���뱨����Ҫ�Ĳ���Map�����ܴ���Ĳ���������year-ͳ�����,month-ͳ���·�,departCode-ͳ�Ʋ���,currPersonName-��ǰ��¼�û���,currDepartName-��ǰ�û����ڲ���, currDate-��ǰ���ڣ�
	 * @param os Ҫ�������������
	 */
	public void outReport(String reportTemplateName, String reportType, Condition condition, Map<String, Object> param, OutputStream os);
	
	/**
	 * ָ������ģ���������Html��ʽ��Excel��ʽ��Word��ʽ��Pdf��ʽ����ָ���������
	 * <p>�������ɺ�Ỻ�汨���е�ͼ�����ݣ����ڵ���outChart����1���Ӻ��Զ��ӻ�����ɾ��
	 * 
	 * @param reportConfig Ҫ����ı���ģ��
	 * @param reportType Ҫ����ı�������
	 * @param condition �����������Ĳ�ѯ����
	 * @param param ���뱨����Ҫ�Ĳ���Map�����ܴ���Ĳ���������year-ͳ�����,month-ͳ���·�,departCode-ͳ�Ʋ���,currPersonName-��ǰ��¼�û���,currDepartName-��ǰ�û����ڲ���, currDate-��ǰ���ڣ�
	 * @param os Ҫ������������� ���뱨����Ҫ�Ĳ���Map�����ܴ���Ĳ���������year-ͳ�����,month-ͳ���·�,departCode-ͳ�Ʋ���,currPersonName-��ǰ��¼�û���,currDepartName-��ǰ�û����ڲ���, currDate-��ǰ���ڣ�
	 */
	public void outReport(ReportConfig reportConfig, String reportType, Condition condition, Map<String, Object> param, OutputStream os);
	
	/**
	 * ָ������ģ��ͱ��������������Html��ʽ��Excel��ʽ��Word��ʽ��Pdf��ʽ����ָ���������
	 * <p>�������ɺ�Ỻ�汨���е�ͼ�����ݣ����ڵ���outChart����1���Ӻ��Զ��ӻ�����ɾ��
	 * 
	 * @param reportConfig Ҫ����ı���ģ��
	 * @param reportType Ҫ����ı�������
	 * @param param ���뱨����Ҫ�Ĳ���Map�����ܴ���Ĳ���������year-ͳ�����,month-ͳ���·�,departCode-ͳ�Ʋ���,currPersonName-��ǰ��¼�û���,currDepartName-��ǰ�û����ڲ���, currDate-��ǰ���ڣ�
	 * @param datasourceMap ����������Դ������Դ���ƺ�����Դ�����γɵļ�ֵ��Map��
	 * @param os Ҫ�������������
	 */
	
	public void outReportWithData(ReportConfig reportConfig, String reportType, Map<String, Object> param, Map<String, List<Map<String, Object>>> datasourceMap, OutputStream os);
	
	/**
	 * �����ǰ���ɱ����л����ͼ��������ָ�������������ɾ��ͼ������
	 * 
	 * @param tableUID
	 * @param chartUID
	 * @param os
	 */
	public void outChart(String tableUID, int chartUID, OutputStream os);
	
	/**
	 * �������б���ģ������
	 * 
	 * @return
	 */
	public String[] getReportConfigNames();
	
	/**
	 * ����ָ������ģ���˵��
	 * 
	 * @param name
	 * @return
	 */
	public String getReportConfigDes(String name);
	
	/**
	 * ����ָ������ģ��ĵ����ļ����ļ���
	 * 
	 * @param name
	 * @param param ���뱨����Ҫ�Ĳ���Map�����ܴ���Ĳ���������year-ͳ�����,month-ͳ���·�,departCode-ͳ�Ʋ���,currPersonName-��ǰ��¼�û���,currDepartName-��ǰ�û����ڲ���, currDate-��ǰ���ڣ�
	 * @return
	 */
	public String getReportExportFileName(String name, Map<String, Object> param);
	
	/**
	 * ����ָ������ģ��
	 * 
	 * @param name
	 * @return
	 */
	public ReportConfig getReportConfig(String name);
	
	/**
	 * �½�һ���Ӹ���ģ���п�¡�������±���ģ��
	 * 
	 * @param name
	 * @return
	 */
	public ReportConfig cloneReportConfig(String name);
	
	/**
	 * ����µ���ʽ��
	 * 
	 * @param cssClass
	 */
	public void addCssClass(CssClass cssClass);
	
	/**
	 * ɾ��������������ʽ��
	 * 
	 * @param className
	 */
	public CssClass removeCssClass(String className);
	
	/**
	 * ��ȡ������������ʽ��
	 * 
	 * @param className
	 * @return
	 */
	public CssClass getCssClass(String className);
	
	/**
	 * ����һ����չ�˸�����������ʽ�������ʽ��
	 * 
	 * @param className
	 * @param extendClassName
	 * @return
	 */
	public CssClass extendCssClass(String className, String extendClassName);
}
