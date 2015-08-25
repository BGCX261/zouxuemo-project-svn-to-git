package com.lily.dap.service.report2.impl.chart.jfreechart;
import java.awt.Color;
import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;
/**
 * 该类用于演示最简单的折线图生成
 * @author Winter Lau
 */
public class LineChartDemo {
	public static void main(String[] args) throws IOException{
		CategoryDataset dataset = getDataSet2();
		JFreeChart chart = ChartFactory.createLineChart(
				"水果产量图", 				// 图表标题
				"水果", 					// 目录轴的显示标签
				"产量", 					// 数值轴的显示标签
				dataset, 					// 数据集
				PlotOrientation.VERTICAL, 	// 图表方向：水平、垂直
				true, 						// 是否显示图例
				false, 						// 是否生成工具
				false 						// 是否生成URL链接
				);
		
		Font titleFont = new Font("黑体", Font.CENTER_BASELINE, 20);    
		Font legendFont = new Font("宋体", Font.CENTER_BASELINE, 12);    
		Font labelFont = new Font("宋体", Font.CENTER_BASELINE, 12);    
		Font tickLabelFont = new Font("宋体", Font.CENTER_BASELINE, 10);    

		//设置标题字体
		chart.getTitle().setFont(titleFont);

		//设置图例字体
		chart.getLegend().setItemFont(legendFont); 

		CategoryPlot plot = (CategoryPlot)chart.getPlot();
	
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
	    renderer.setBaseShapesVisible(true);
	    renderer.setBaseShapesFilled(true);
        
		//设置目录轴的标签字体和刻度字体
		plot.getDomainAxis().setLabelFont(labelFont);
		plot.getDomainAxis().setTickLabelFont(tickLabelFont);
		
		//设置数值轴的标签字体和刻度字体
		plot.getRangeAxis().setLabelFont(labelFont);
		plot.getRangeAxis().setTickLabelFont(tickLabelFont);
		
		FileOutputStream fos_jpg = null;
		try {
			fos_jpg = new FileOutputStream("D:\\fruit1.jpg");
			ChartUtilities.writeChartAsJPEG(fos_jpg,1,chart,600,400,null);
		} finally {
			try {
				fos_jpg.close();
			} catch (Exception e) {}
		}
	}
	/**
	 * 获取一个演示用的简单数据集对象
	 * @return
	 */
	private static CategoryDataset getDataSet() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(100, null, "苹果");
		dataset.addValue(200, null, "梨子");
		dataset.addValue(300, null, "葡萄");
		dataset.addValue(400, null, "香蕉");
		dataset.addValue(500, null, "荔枝");
		return dataset;
	}
	/**
	 * 获取一个演示用的组合数据集对象
	 * @return
	 */
	private static CategoryDataset getDataSet2() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(100, "北京", "苹果");
		dataset.addValue(130, "上海", "苹果");
		dataset.addValue(180, "广州", "苹果");
		dataset.addValue(200, "北京", "梨子");
		dataset.addValue(250, "上海", "梨子");
		dataset.addValue(290, "广州", "梨子");
		dataset.addValue(300, "北京", "葡萄");
		dataset.addValue(340, "上海", "葡萄");
		dataset.addValue(360, "广州", "葡萄");
		dataset.addValue(400, "北京", "香蕉");
		dataset.addValue(430, "上海", "香蕉");
		dataset.addValue(480, "广州", "香蕉");
		dataset.addValue(500, "北京", "荔枝");
		dataset.addValue(590, "上海", "荔枝");
		dataset.addValue(550, "广州", "荔枝");
		return dataset;
	}
}
