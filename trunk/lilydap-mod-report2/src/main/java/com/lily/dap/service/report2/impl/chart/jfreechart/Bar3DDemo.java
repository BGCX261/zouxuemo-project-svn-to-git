package com.lily.dap.service.report2.impl.chart.jfreechart;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.io.FileOutputStream;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.TextAnchor;

public class Bar3DDemo {
	public static void main(String[] args) throws IOException {
		double[][] data = new double[][] { { 500 }, { 200 }, { 100 }, { 400 },
				{ 600 }, { 300 } };
		String[] rowKeys = { "苹果", "梨子", "葡萄", "桔子", "西瓜", "香蕉" };
		String[] columnKeys = { "" };
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				rowKeys, columnKeys, data);

		JFreeChart chart = ChartFactory.createBarChart3D("水果销量图统计", "水果", // 横坐标名称
				"销量", // 纵坐标名称
				dataset,// 数据集合
				PlotOrientation.VERTICAL,// 图形位置，水平还是垂直
				true, false, false);

		Font titleFont = new Font("黑体", Font.CENTER_BASELINE, 20);
		Font legendFont = new Font("宋体", Font.CENTER_BASELINE, 12);
		Font labelFont = new Font("宋体", Font.CENTER_BASELINE, 12);
		Font tickLabelFont = new Font("宋体", Font.CENTER_BASELINE, 10);

		//设置标题字体
		chart.getTitle().setFont(titleFont);

		//设置图例字体
		chart.getLegend().setItemFont(legendFont); 

		chart.setBackgroundPaint(Color.WHITE);
		// 设定背景色为白色

		CategoryPlot categoryPlot = chart.getCategoryPlot();
		// 获得 plot：3dBar为CategoryPlot
		
		//设置目录轴的标签字体和刻度字体
		categoryPlot.getDomainAxis().setLabelFont(labelFont);
		categoryPlot.getDomainAxis().setTickLabelFont(tickLabelFont);
		
		//设置数值轴的标签字体和刻度字体
		categoryPlot.getRangeAxis().setLabelFont(labelFont);
		categoryPlot.getRangeAxis().setTickLabelFont(tickLabelFont);

		categoryPlot.setBackgroundPaint(Color.lightGray);
		// 设定图表数据显示部分背景色

		categoryPlot.setDomainGridlinePaint(Color.white);
		// 横坐标网格线白色
		categoryPlot.setDomainGridlinesVisible(true);
		// 设置网格线可见

		categoryPlot.setRangeGridlinePaint(Color.white);
		// 纵坐标网格线白色

		// 获取横坐标
		CategoryAxis domainAxis = categoryPlot.getDomainAxis();

		// 设置 横坐标 垂直显示
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.4));

		// 获取纵坐标
		NumberAxis numberaxis = (NumberAxis) categoryPlot.getRangeAxis();
		// 将纵坐标间距设置为50
		numberaxis.setTickUnit(new NumberTickUnit(50));
		// 设置横坐标的标题字体和大小,此处是“宋体13号”
		domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 13));

		// 设置距离图片左端距离,参数为图片的百分比
		domainAxis.setLowerMargin(0.05);

		// 设置距离图片右端距离
		domainAxis.setUpperMargin(0.05);

		// 设置横坐标的坐标值的字体
		domainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));
		// 使横坐标设置生效
		categoryPlot.setDomainAxis(domainAxis);

		ValueAxis rangeAxis = categoryPlot.getRangeAxis();
		// 设置最高的一个柱与图片顶端的距离
		rangeAxis.setUpperMargin(0.05);

		// 设置最低的一个柱与图片底端的距离
		rangeAxis.setLowerMargin(0.05);
		categoryPlot.setRangeAxis(rangeAxis);

		// 设置竖坐标标签的旋转角度
		rangeAxis.setLabelAngle(0.05);

		// 重要的类，负责生成各种效果
		BarRenderer3D renderer = (BarRenderer3D) categoryPlot.getRenderer();

		// 设置 Wall 的颜色
		renderer.setWallPaint(Color.PINK);

		// 设置每个柱的颜色
		GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, Color.blue,
				0.0F, 0.0F, new Color(0, 0, 64)); // 设定特定颜色
		GradientPaint gradientpaint1 = new GradientPaint(0.0F, 0.0F,
				Color.green, 0.0F, 0.0F, new Color(0, 64, 0));

		renderer.setSeriesPaint(0, gradientpaint);
		renderer.setSeriesPaint(1, gradientpaint1);

		// 设置柱的 Outline 颜色
		renderer.setSeriesOutlinePaint(0, Color.BLACK);
		renderer.setSeriesOutlinePaint(1, Color.BLACK);
		// 设置每个category所包含的平行柱的之间距离
		renderer.setItemMargin(0.1);

		// 显示每个柱的数值，并修改该数值的字体属性
		renderer
				.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());

		// 以下设置，将按照指定格式，制定内容显示每个柱的数值。可以显示柱名称，所占百分比
		// renderer.setBaseItemLabelGenerator(new
		// StandardCategoryItemLabelGenerator("{2}",new DecimalFormat("0.0%")));

		// 设置柱子上数值的字体
		renderer.setItemLabelFont(new Font("宋体", Font.PLAIN, 13));
		renderer.setItemLabelsVisible(true);

		// 设置柱子上数据的颜色
		renderer.setItemLabelPaint(Color.RED);

		// 设置bar的最小宽度，以保证能显示数值
		renderer.setMinimumBarLength(0.02);

		// 最大宽度
		// renderer.setMaximumBarWidth(0.07);

		// 设置柱子上比例数值的显示，如果按照默认方式显示，数值为方向正常显示
		// 设置柱子上显示的数据旋转90度,最后一个参数为旋转的角度值/3.14
		ItemLabelPosition itemLabelPosition = new ItemLabelPosition(
				ItemLabelAnchor.INSIDE12, TextAnchor.CENTER_RIGHT,
				TextAnchor.CENTER_RIGHT, -1.57D);

		// 下面的设置是为了解决，当柱子的比例过小，而导致表示该柱子比例的数值无法显示的问题
		// 设置不能在柱子上正常显示的那些数值的显示方式，将这些数值显示在柱子外面
		ItemLabelPosition itemLabelPositionFallback = new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT,
				TextAnchor.HALF_ASCENT_LEFT, -1.57D);

		// 设置正常显示的柱子label的position
		renderer.setPositiveItemLabelPosition(itemLabelPosition);
		renderer.setNegativeItemLabelPosition(itemLabelPosition);

		// 设置不能正常显示的柱子label的position
		renderer
				.setPositiveItemLabelPositionFallback(itemLabelPositionFallback);
		renderer
				.setNegativeItemLabelPositionFallback(itemLabelPositionFallback);

		categoryPlot.setRenderer(renderer);
		// 设置柱子的透明度
		categoryPlot.setForegroundAlpha(0.8f);

		// 为柱图设置“数据挖陷”
		// 第一个参数是跳转的连接地址
		renderer.setBaseItemURLGenerator(new StandardCategoryURLGenerator(
				"detail.jsp", "fruit", ""));

		// 使设置生效
		renderer.setBaseItemLabelsVisible(true);
		// 设置柱的透明度
		categoryPlot.setForegroundAlpha(0.5f);
		// 设置地区、销量的显示位置
		// plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);

		FileOutputStream fos_jpg = null;
		try {
			fos_jpg = new FileOutputStream("D:\\bar.jpg");
			ChartUtilities.writeChartAsJPEG(fos_jpg, 1, chart, 600, 400, null);
		} finally {
			try {
				fos_jpg.close();
			} catch (Exception e) {
			}
		}
	}
}
