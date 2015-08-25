/**
 * 
 */
package com.lily.dap.service.report2.impl.chart.jfreechart;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.CharUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import com.lily.dap.service.report2.impl.chart.ChartConstants;
import com.lily.dap.service.report2.impl.chart.ChartStrategy;
import com.lily.dap.service.report2.impl.chart.ChartUtils;

/**
 * @author xuemozou
 *
 */
/*
<chart type="pie/pie3d" datasource="XXX">				<!--指定饼图的类型（二维/三维）和图表数据源-->
	<param name="title">饼图演示</param>				<!--指定图表标题，参数可选-->
	<param name="backgroundcolor">0000ff</param>		<!--指定图表背景色，背景色为RGB格式，例如：a0a0a0，参数可选-->
	<param name="color">0000ff,...,ff0000</param>		<!--指定各块饼颜色，多块饼颜色之间用","分隔，颜色为RGB格式，例如：a0a0a0，参数可选-->
	<param name="namefield">field2</param>				<!--指定饼图的各项名称，必须-->
	<param name="valuefield">field3</param>				<!--指定饼图的各项值，必须-->
	<param name="foregroundAlpha">0.5</param>			<!--指定饼图的前景透明度，一般在类型为三维饼图时使用，范围为0到1之间的浮点数，为0时全透明，为1时不透明，默认为不透明-->
	<param name="circular">true</param>					<!--指定显示的饼图是否为圆形还是椭圆形，true：圆形，false：椭圆形，默认椭圆形-->
	<param name="legend">true</param>					<!--是否显示项目说明，true：显示说明，false：不显示说明，默认不显示-->
	<param name="labelformat">{0}\n{1}\n{2}</param>		<!--指定饼图的标签格式，格式类似于"{0}\n{1}\n{2}"，其中，0：数据名称，1：数据值，2：数据占整个百分比，\n表示换行-->
	<param name="legendformat">{0}\n{1}\n{2}</param>	<!--指定项目说明的标签格式，格式类似于"{0}\n{1}\n{2}"，其中，0：数据名称，1：数据值，2：数据占整个百分比，\n表示换行-->
	<param name="maximumlabelwidth">0.3</param>			<!--指定分类标签label的最大宽度，相对与plot的百分比，范围为0到1之间的浮点数-->
	<param name="foregroundAlpha">0.5</param>			<!--指定饼图的前景透明度，一般在类型为三维饼图时使用，范围为0到1之间的浮点数，为0时全透明，为1时不透明，默认为不透明-->
</chart>
*/
public class PieChartStrategy implements ChartStrategy {
	private static final String[] supportTypes = new String[]{ChartConstants.TYPE_PIE, ChartConstants.TYPE_PIE3D};
	
	private static final Font titleFont = new Font("黑体", Font.CENTER_BASELINE, 20);    
	private static final Font legendFont = new Font("宋体", Font.CENTER_BASELINE, 12);    
	private static final Font labelFont = new Font("宋体", Font.CENTER_BASELINE, 12);    

	/* (non-Javadoc)
	 * @see com.lily.dap.service.report2.impl.chart.ChartStrategy#support()
	 */
	public String[] support() {
		return supportTypes;
	}

	/* (non-Javadoc)
	 * @see com.lily.dap.service.report2.impl.chart.ChartStrategy#outputChart(java.lang.String, int, int, java.util.Map, java.util.List, java.io.OutputStream)
	 */
	public void outputChart(String type, int height, int width,
			Map<String, String> paramMap, Map<String, Object> variableMap, List<Map<String, Object>> dataList,
			OutputStream os) throws IOException {
		String nameField = ChartUtils.evaluateParam(paramMap.get("namefield"), variableMap);
		String valueField = ChartUtils.evaluateParam(paramMap.get("valuefield"), variableMap);
		DefaultPieDataset dataset = createDataset(dataList, nameField, valueField);

		String title = ChartUtils.evaluateParam(paramMap.get("title"), variableMap);
        
        boolean legend = paramMap.get("legend") == null ? true : "true".equals(ChartUtils.evaluateParam(paramMap.get("legend"), variableMap).toLowerCase());
		
        JFreeChart chart;
        if (ChartConstants.TYPE_PIE3D.equals(type))
        	chart = ChartFactory.createPieChart3D(
	        		title, 			// 图表标题
					dataset, 		// 数据集
					legend, 		// 是否显示图例
					false, 			// 是否生成工具
					false			// 是否生成URL链接
					);
        else
        	chart = ChartFactory.createPieChart(
	        		title, 			// 图表标题
					dataset, 		// 数据集
					legend, 		// 是否显示图例
					false, 			// 是否生成工具
					false			// 是否生成URL链接
					);
		
		//设置标题字体
		if (chart.getTitle() != null)
			chart.getTitle().setFont(titleFont);

		//设置图例字体
		if (chart.getLegend() != null)
			chart.getLegend().setItemFont(legendFont);
		
		PiePlot pie = (PiePlot)chart.getPlot();
		
//		if (pie instanceof PiePlot3D) {
//			PiePlot3D pie3d = (PiePlot3D)pie;
//		}
		
		//设置前景透明度
		if (paramMap.get("foregroundAlpha") != null) {
			float f = Float.parseFloat(ChartUtils.evaluateParam(paramMap.get("foregroundAlpha"), variableMap));
			
			pie.setForegroundAlpha(f);
		}
		
		//设置标签的最大宽度
		if (paramMap.get("maximumlabelwidth") != null) {
			float f = Float.parseFloat(ChartUtils.evaluateParam(paramMap.get("maximumlabelwidth"), variableMap));
			
			pie.setMaximumLabelWidth(f);
		}
		
		//设置标饼上标签的标签字体
		pie.setLabelFont(labelFont);

		String labelFormat = paramMap.get("labelformat");
		if (labelFormat != null) {
			/*
			 * 因为labelformat参数可能包含{0}、{1}、{2}这样的字符串，所以要通过检查'{'后面跟的字符是不是数字，来判断labelformat参数是不是表达式
			 */
			int index = labelFormat.indexOf('{');
			
			if (index >= 0) {
				char ch = labelFormat.charAt(index + 1);
				
				if (!CharUtils.isAsciiNumeric(ch))
					labelFormat = ChartUtils.evaluateParam(labelFormat, variableMap);
			}
	 		
			//设置饼上标签的显示格式，其中，0：数据名称，1：数据值，2：数据占整个百分比
			StandardPieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator(labelFormat);
			pie.setLabelGenerator(generator);
		}

		String legendformat = paramMap.get("legendformat");
		if (legendformat != null) {
			/*
			 * 因为legendformat参数可能包含{0}、{1}、{2}这样的字符串，所以要通过检查'{'后面跟的字符是不是数字，来判断legendformat参数是不是表达式
			 */
			int index = legendformat.indexOf('{');
			
			if (index >= 0) {
				char ch = legendformat.charAt(index + 1);
				
				if (!CharUtils.isAsciiNumeric(ch))
					legendformat = ChartUtils.evaluateParam(legendformat, variableMap);
			}
	 		
			//设置饼上标签的显示格式，其中，0：数据名称，1：数据值，2：数据占整个百分比
			StandardPieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator(legendformat);
			pie.setLegendLabelGenerator(generator);
		}
		
		if (paramMap.get("color") != null) {
			int index = 0;
			
			String[] colGroups = ChartUtils.evaluateParam(paramMap.get("color"), variableMap).split(",");
			for (String colVal : colGroups) {
				Color color = ChartUtils.createColor(colVal.trim());
				
				pie.setSectionPaint(index++, color);
			}
		}
		
		//设置饼图是圆形还是椭圆形
		if (paramMap.get("circular") != null) {
			boolean isCircular = "true".equals(ChartUtils.evaluateParam(paramMap.get("circular"), variableMap).toLowerCase());
			
			pie.setCircular(isCircular);
		}
		
		ChartUtilities.writeChartAsJPEG(os, 1, chart, width, height, null);
	}
	
	private DefaultPieDataset createDataset(List<Map<String, Object>> dataList, String nameField, String valueField) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		if (dataList.size() == 0)
			return dataset;
		
		Map<String, Object> firstMap = dataList.get(0);
		if (!firstMap.containsKey(nameField) || !firstMap.containsKey(valueField))
			throw new RuntimeException("配置的名称字段或者值字段在数据源中不存在，请检查配置的字段名称是否正确，或者数据源配置是否正确！");
		
		String nameKey = null;
		Number value = 0;
		for (Map<String, Object> data : dataList) {
			nameKey = (String)data.get(nameField);
			value = (Number)data.get(valueField);
			
			dataset.setValue(nameKey, value);
		}
		
		return dataset;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
