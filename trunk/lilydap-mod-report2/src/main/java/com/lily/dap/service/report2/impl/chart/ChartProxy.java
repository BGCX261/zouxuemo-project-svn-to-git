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
 * 图表生成代理
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
	 * 输出一个给定类型的图表至输出流
	 * 
	 * @param type 图表类型
	 * @param height 图表输出图像的高度
	 * @param width 图表输出图像的宽度
	 * @param paramMap 图表参数
	 * @param variableMap 外部传入的变量值集合
	 * @param dataList 传入的数据集合
	 * @param os 输出至的输出流
	 */
	public void outputChart(String type, 
				int height, 
				int width, 
				Map<String, String> paramMap, 
				Map<String, Object> variableMap,
				List<Map<String, Object>> dataList, 
				OutputStream os) throws IOException {
		if (!chartStrategyMap.containsKey(type))
			throw new RuntimeException("未找到类型为 " + type + " 的图表生成器，请检查图表类型是否正确！");
		
		ChartStrategy chartStrategy = chartStrategyMap.get(type);
		chartStrategy.outputChart(type, height, width, paramMap, variableMap, dataList, os);
	}
}
