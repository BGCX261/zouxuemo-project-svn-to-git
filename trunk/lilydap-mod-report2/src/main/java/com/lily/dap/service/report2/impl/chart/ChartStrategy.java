/**
 * 
 */
package com.lily.dap.service.report2.impl.chart;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author xuemozou
 *
 * ͼ�����ɲ��Խӿڣ��ṩ��ʽͼ�����ɵĲ��Խӿ�
 */
public interface ChartStrategy {
	/**
	 * ����֧�ֵ�ͼ����������
	 * 
	 * @return
	 */
	public String[] support();
	
	/**
	 * ���һ���������͵�ͼ���������
	 * 
	 * @param type ͼ������
	 * @param height ͼ�����ͼ��ĸ߶�
	 * @param width ͼ�����ͼ��Ŀ��
	 * @param paramMap ͼ�����
	 * @param variableMap �ⲿ����ı���ֵ����
	 * @param dataList ��������ݼ���
	 * @param os ������������
	 */
	public void outputChart(String type, int height, int width, Map<String, String> paramMap, Map<String, Object> variableMap, List<Map<String, Object>> dataList, OutputStream os) throws IOException;
}
