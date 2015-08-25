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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.lily.dap.service.report2.impl.chart.ChartConstants;
import com.lily.dap.service.report2.impl.chart.ChartStrategy;
import com.lily.dap.service.report2.impl.chart.ChartUtils;

/**
 * @author xuemozou
 *
 * 折线图生成策略
 */
/*
<chart type="line" datasource="XXX">						<!--指定折线图的类型和图表数据源-->
	<param name="title">折线图演示</param>					<!--指定图表标题，参数可选-->
	<param name="backgroundcolor">0000ff</param>			<!--指定图表背景色，背景色为RGB格式，例如：a0a0a0，参数可选-->
	<param name="color">0000ff,...,ff0000</param>			<!--指定各条曲线颜色，多条曲线颜色之间用","分隔，颜色为RGB格式，例如：a0a0a0，参数可选-->
	<param name="namefield">field2</param>					<!--指定折线图的各项名称，必须-->
	<param name="categoryfield">field1</param>				<!--指定折线图的各项分类，参数可选-->
	<param name="valuefield">field3</param>					<!--指定折线图的各项值，如果指定了categoryfield参数，则本参数必须-->
	<param name="categoryfields">field1,..,fieldn</param>	<!--指定折线图的分类值字段，多个字段之间以","分隔，参数可选-->
	<param name="categorynames">name1,...namen</param>		<!--指定折线图的分类值对应的分类名称，多个字段之间以","分隔，如果指定了categoryfields参数，则本参数为categoryfields中对应各个分类字段的分类名称，参数可选，如果不设置，则分类名称取分类字段名-->
	<param name="categoryaxislabel">目录轴标签</param>		<!--指定目录轴标签名，参数可选-->
	<param name="valueaxislabel">数值轴标签</param>			<!--指定数值轴标签名，参数可选-->
	<param name="valueaxisarrow">true</param>				<!--数值轴是否显示箭头，默认不显示-->
	<param name="showbaseshape">true</param>				<!--是否在折线间显示点，默认不显示-->
	<param name="legend">true</param>						<!--是否显示项目说明，默认不显示-->
	<param name="horizontal">false</param>					<!--折线图是否水平显示，默认垂直显示-->
</chart>
*/
public class LineChartStrategy implements ChartStrategy {
	private static final String[] supportTypes = new String[]{ChartConstants.TYPE_LINE};
	
	private static final Font titleFont = new Font("黑体", Font.CENTER_BASELINE, 20);    
	private static final Font legendFont = new Font("宋体", Font.CENTER_BASELINE, 12);    
	private static final Font labelFont = new Font("宋体", Font.CENTER_BASELINE, 12);    
	private static final Font tickLabelFont = new Font("宋体", Font.CENTER_BASELINE, 10);    

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
		String categoryField = ChartUtils.evaluateParam(paramMap.get("categoryfield"), variableMap);
		String valueField = ChartUtils.evaluateParam(paramMap.get("valuefield"), variableMap);
		
		String categoryfields = ChartUtils.evaluateParam(paramMap.get("categoryfields"), variableMap);
		String categorynames = ChartUtils.evaluateParam(paramMap.get("categorynames"), variableMap);
		
		CategoryDataset dataset;
		if (categoryfields != null) {
			String[] categoryFields = null, categoryNames = null;
			categoryFields = categoryfields.split(",");
			
			if (categorynames != null)
				categoryNames = categorynames.split(",");
			
			dataset = createDataset(dataList, nameField, categoryFields, categoryNames);
		} else {
			dataset = createDataset(dataList, nameField, categoryField, valueField);
		}
		
		String title = ChartUtils.evaluateParam(paramMap.get("title"), variableMap);
		String categoryAxisLabel = ChartUtils.evaluateParam(paramMap.get("categoryaxislabel"), variableMap);
        String valueAxisLabel = ChartUtils.evaluateParam(paramMap.get("valueaxislabel"), variableMap);
        
        PlotOrientation orientation = PlotOrientation.VERTICAL;
        if (paramMap.get("horizontal") != null && "true".equals(ChartUtils.evaluateParam(paramMap.get("horizontal"), variableMap).toLowerCase()))
        	orientation = PlotOrientation.HORIZONTAL;
        
        boolean legend = paramMap.get("legend") == null ? true : "true".equals(ChartUtils.evaluateParam(paramMap.get("legend"), variableMap).toLowerCase());
        
        JFreeChart chart = ChartFactory.createLineChart(
        	title,      // 图表标题
        	categoryAxisLabel, // 目录轴的显示标签
        	valueAxisLabel, // 数值轴的显示标签
            dataset,    // 数据集
            orientation,// 图表方向：水平、垂直
            legend,     // 是否显示图例
            false,		// 是否生成工具
            false      // 是否生成URL链接
        );
		
        if (paramMap.get("backgroundcolor") != null) {
        	Color color = ChartUtils.createColor(ChartUtils.evaluateParam(paramMap.get("backgroundcolor"), variableMap));
        	
        	chart.setBackgroundPaint(color);
        }
        	
		//设置标题字体
		if (chart.getTitle() != null)
			chart.getTitle().setFont(titleFont);

		//设置图例字体
		if (chart.getLegend() != null)
			chart.getLegend().setItemFont(legendFont);
        
		CategoryPlot plot = (CategoryPlot) chart.getPlot();

		if (paramMap.get("showbaseshape") != null && "true".equals(ChartUtils.evaluateParam(paramMap.get("showbaseshape"), variableMap).toLowerCase())) {
			LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}
		
		//设置目录轴的标签字体和刻度字体
		if (plot.getDomainAxis() != null) {
			CategoryAxis categoryAxis = plot.getDomainAxis();
			
			categoryAxis.setLabelFont(labelFont);
			categoryAxis.setTickLabelFont(tickLabelFont);
		}
		
		//设置数值轴的标签字体和刻度字体
		if (plot.getRangeAxis() != null) {
			NumberAxis numberAxis = (NumberAxis)plot.getRangeAxis();

			numberAxis.setLabelFont(labelFont);
			numberAxis.setTickLabelFont(tickLabelFont);
			
			if (paramMap.get("valueaxisarrow") != null && "true".equals(ChartUtils.evaluateParam(paramMap.get("valueaxisarrow"), variableMap).toLowerCase()))
				numberAxis.setPositiveArrowVisible(true);
			
//			numberAxis.setAutoTickUnitSelection(false);
//	        NumberTickUnit numberTickUnit = new NumberTickUnit(10d); 
//	        numberAxis.setTickUnit(numberTickUnit);
		}
		
		if (paramMap.get("color") != null) {
			LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
			
			int index = 0;
			
			String[] colGroups = ChartUtils.evaluateParam(paramMap.get("color"), variableMap).split(",");
			for (String colVal : colGroups) {
				Color color = ChartUtils.createColor(colVal.trim());
				
				renderer.setSeriesPaint(index++, color);
			}
		}
        
//        plot.setBackgroundPaint(Color.lightGray);
//        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
//        
//        plot.setDomainGridlinePaint(Color.white);
//        plot.setRangeGridlinePaint(Color.white);
//        
//        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
//        renderer.setBaseShapesVisible(true);
//        renderer.setBaseShapesFilled(true);
//
//        // change the auto tick unit selection to integer units only...
//        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		
		ChartUtilities.writeChartAsJPEG(os, 1, chart, width, height, null);
	}
	
	/**
	 * 装载类似于如下的数据源数据
	 * <p>	value	category	name
	 * <p>----------------------------
	 * <p>	100, 	"北京", 	"苹果"
	 * <p>	100, 	"上海", 	"苹果"
	 * <p>	100, 	"广州", 	"苹果"
	 * <p>	200, 	"北京", 	"梨子"
	 * <p>	200, 	"上海", 	"梨子"
	 * <p>	200, 	"广州", 	"梨子"
	 * <p>	300, 	"北京", 	"葡萄"
	 * <p>	300, 	"上海", 	"葡萄"
	 * <p>	300, 	"广州", 	"葡萄"
	 * <p>	400, 	"北京", 	"香蕉"
	 * <p>	400, 	"上海", 	"香蕉"
	 * <p>	400, 	"广州", 	"香蕉"
	 * <p>	500, 	"北京", 	"荔枝"
	 * <p>	500, 	"上海", 	"荔枝"
	 * <p>	500, 	"广州", 	"荔枝"
	 * 或者
	 * <p>	value	name
	 * <p>----------------
	 * <p>	100, 	"苹果"
	 * <p>	200, 	"梨子"
	 * <p>	300, 	"葡萄"
	 * <p>	400, 	"香蕉"
	 * <p>	500, 	"荔枝"
	 
	 * @param dataList
	 * @param nameField
	 * @param categoryField
	 * @param valueField
	 * @return
	 */
	private CategoryDataset createDataset(List<Map<String, Object>> dataList, String nameField, String categoryField, String valueField) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		if (dataList.size() == 0)
			return dataset;
		
		Map<String, Object> firstMap = dataList.get(0);
		if ((categoryField != null && !firstMap.containsKey(categoryField)) || 
				!firstMap.containsKey(nameField) ||
				!firstMap.containsKey(valueField))
			throw new RuntimeException("配置的分类字段或者名称字段或者值字段在数据源中不存在，请检查配置的字段名称是否正确，或者数据源配置是否正确！");
		
		String categoryKey = null, nameKey = null;
		Number value = 0;
		for (Map<String, Object> data : dataList) {
			if (categoryField != null)
				categoryKey = (String)data.get(categoryField);
			
			nameKey = (String)data.get(nameField);
			value = (Number)data.get(valueField);
			
			dataset.addValue(value, categoryKey, nameKey);
		}
		
		return dataset;
	}
	
	/**
	 * 装载类似于如下的数据源数据
	 * <p>	2007		2008		name
	 * <p>--------------------------------
	 * <p>	20, 		10,		 	"苹果"
	 * <p>	30, 		20, 		"梨子"
	 * <p>	40, 		30, 		"葡萄"
	 * <p>	50, 		40, 		"香蕉"
	 * <p>	60, 		50, 		"荔枝"
	 * 
	 * @param dataList
	 * @param nameField
	 * @param categoryFields
	 * @param categoryNames 分类名称数组，如果为null，则取分类字段名作为分类名称
	 * @return
	 */
	private CategoryDataset createDataset(List<Map<String, Object>> dataList, String nameField, String[] categoryFields, String[] categoryNames) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		if (dataList.size() == 0)
			return dataset;
		
		int index, categoryLength = categoryFields.length;
		
		Map<String, Object> firstMap = dataList.get(0);
		if (!firstMap.containsKey(nameField))
			throw new RuntimeException("配置的名称字段在数据源中不存在，请检查配置的字段名称是否正确，或者数据源配置是否正确！");
		
		for (index = 0; index < categoryLength; index++)
			if (!firstMap.containsKey(categoryFields[index]))
			throw new RuntimeException("配置的分类字段在数据源中不存在，请检查配置的字段名称是否正确，或者数据源配置是否正确！");
		
		String categoryKey = null, nameKey = null;
		Number value = 0;
		for (Map<String, Object> data : dataList) {
			nameKey = (String)data.get(nameField);
			
			for (index = 0; index < categoryLength; index++) {
				if (categoryNames == null)
					categoryKey = categoryFields[index];
				else
					categoryKey = categoryNames[index];
				
				value = (Number)data.get(categoryFields[index]);
				
				dataset.addValue(value, categoryKey, nameKey);
			}
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
