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
 * 图表生成策略接口，提供样式图表生成的策略接口
 */
public interface ChartStrategy {
	/**
	 * 返回支持的图表类型数组
	 * 
	 * @return
	 */
	public String[] support();
	
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
	public void outputChart(String type, int height, int width, Map<String, String> paramMap, Map<String, Object> variableMap, List<Map<String, Object>> dataList, OutputStream os) throws IOException;
}
