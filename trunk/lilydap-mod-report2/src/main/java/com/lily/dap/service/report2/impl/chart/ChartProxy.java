/**
 * 
 */
package com.lily.dap.service.report2.impl.chart;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuemozou
 * 
 * ͼ�����ɴ���
 */
public class ChartProxy {
	private Map<String, ChartStrategy> chartStrategyMap = new HashMap<String, ChartStrategy>();
	
	public void addChartStrategy(ChartStrategy chartStrategy) {
		String[] chartTypes = chartStrategy.support();
		
		for (String key : chartTypes) {
			if (chartStrategyMap.containsKey(key))
				chartStrategyMap.remove(key);
			
			chartStrategyMap.put(key, chartStrategy);
		}
	}
	
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
	public void outputChart(String type, 
				int height, 
				int width, 
				Map<String, String> paramMap, 
				Map<String, Object> variableMap,
				List<Map<String, Object>> dataList, 
				OutputStream os) throws IOException {
		if (!chartStrategyMap.containsKey(type))
			throw new RuntimeException("δ�ҵ�����Ϊ " + type + " ��ͼ��������������ͼ�������Ƿ���ȷ��");
		
		ChartStrategy chartStrategy = chartStrategyMap.get(type);
		chartStrategy.outputChart(type, height, width, paramMap, variableMap, dataList, os);
	}
}
