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
		String[] rowKeys = { "ƻ��", "����", "����", "����", "����", "�㽶" };
		String[] columnKeys = { "" };
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
				rowKeys, columnKeys, data);

		JFreeChart chart = ChartFactory.createBarChart3D("ˮ������ͼͳ��", "ˮ��", // ����������
				"����", // ����������
				dataset,// ���ݼ���
				PlotOrientation.VERTICAL,// ͼ��λ�ã�ˮƽ���Ǵ�ֱ
				true, false, false);

		Font titleFont = new Font("����", Font.CENTER_BASELINE, 20);
		Font legendFont = new Font("����", Font.CENTER_BASELINE, 12);
		Font labelFont = new Font("����", Font.CENTER_BASELINE, 12);
		Font tickLabelFont = new Font("����", Font.CENTER_BASELINE, 10);

		//���ñ�������
		chart.getTitle().setFont(titleFont);

		//����ͼ������
		chart.getLegend().setItemFont(legendFont); 

		chart.setBackgroundPaint(Color.WHITE);
		// �趨����ɫΪ��ɫ

		CategoryPlot categoryPlot = chart.getCategoryPlot();
		// ��� plot��3dBarΪCategoryPlot
		
		//����Ŀ¼��ı�ǩ����Ϳ̶�����
		categoryPlot.getDomainAxis().setLabelFont(labelFont);
		categoryPlot.getDomainAxis().setTickLabelFont(tickLabelFont);
		
		//������ֵ��ı�ǩ����Ϳ̶�����
		categoryPlot.getRangeAxis().setLabelFont(labelFont);
		categoryPlot.getRangeAxis().setTickLabelFont(tickLabelFont);

		categoryPlot.setBackgroundPaint(Color.lightGray);
		// �趨ͼ��������ʾ���ֱ���ɫ

		categoryPlot.setDomainGridlinePaint(Color.white);
		// �����������߰�ɫ
		categoryPlot.setDomainGridlinesVisible(true);
		// ���������߿ɼ�

		categoryPlot.setRangeGridlinePaint(Color.white);
		// �����������߰�ɫ

		// ��ȡ������
		CategoryAxis domainAxis = categoryPlot.getDomainAxis();

		// ���� ������ ��ֱ��ʾ
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.4));

		// ��ȡ������
		NumberAxis numberaxis = (NumberAxis) categoryPlot.getRangeAxis();
		// ��������������Ϊ50
		numberaxis.setTickUnit(new NumberTickUnit(50));
		// ���ú�����ı�������ʹ�С,�˴��ǡ�����13�š�
		domainAxis.setLabelFont(new Font("����", Font.PLAIN, 13));

		// ���þ���ͼƬ��˾���,����ΪͼƬ�İٷֱ�
		domainAxis.setLowerMargin(0.05);

		// ���þ���ͼƬ�Ҷ˾���
		domainAxis.setUpperMargin(0.05);

		// ���ú����������ֵ������
		domainAxis.setTickLabelFont(new Font("����", Font.PLAIN, 12));
		// ʹ������������Ч
		categoryPlot.setDomainAxis(domainAxis);

		ValueAxis rangeAxis = categoryPlot.getRangeAxis();
		// ������ߵ�һ������ͼƬ���˵ľ���
		rangeAxis.setUpperMargin(0.05);

		// ������͵�һ������ͼƬ�׶˵ľ���
		rangeAxis.setLowerMargin(0.05);
		categoryPlot.setRangeAxis(rangeAxis);

		// �����������ǩ����ת�Ƕ�
		rangeAxis.setLabelAngle(0.05);

		// ��Ҫ���࣬�������ɸ���Ч��
		BarRenderer3D renderer = (BarRenderer3D) categoryPlot.getRenderer();

		// ���� Wall ����ɫ
		renderer.setWallPaint(Color.PINK);

		// ����ÿ��������ɫ
		GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, Color.blue,
				0.0F, 0.0F, new Color(0, 0, 64)); // �趨�ض���ɫ
		GradientPaint gradientpaint1 = new GradientPaint(0.0F, 0.0F,
				Color.green, 0.0F, 0.0F, new Color(0, 64, 0));

		renderer.setSeriesPaint(0, gradientpaint);
		renderer.setSeriesPaint(1, gradientpaint1);

		// �������� Outline ��ɫ
		renderer.setSeriesOutlinePaint(0, Color.BLACK);
		renderer.setSeriesOutlinePaint(1, Color.BLACK);
		// ����ÿ��category��������ƽ������֮�����
		renderer.setItemMargin(0.1);

		// ��ʾÿ��������ֵ�����޸ĸ���ֵ����������
		renderer
				.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());

		// �������ã�������ָ����ʽ���ƶ�������ʾÿ��������ֵ��������ʾ�����ƣ���ռ�ٷֱ�
		// renderer.setBaseItemLabelGenerator(new
		// StandardCategoryItemLabelGenerator("{2}",new DecimalFormat("0.0%")));

		// ������������ֵ������
		renderer.setItemLabelFont(new Font("����", Font.PLAIN, 13));
		renderer.setItemLabelsVisible(true);

		// �������������ݵ���ɫ
		renderer.setItemLabelPaint(Color.RED);

		// ����bar����С��ȣ��Ա�֤����ʾ��ֵ
		renderer.setMinimumBarLength(0.02);

		// �����
		// renderer.setMaximumBarWidth(0.07);

		// ���������ϱ�����ֵ����ʾ���������Ĭ�Ϸ�ʽ��ʾ����ֵΪ����������ʾ
		// ������������ʾ��������ת90��,���һ������Ϊ��ת�ĽǶ�ֵ/3.14
		ItemLabelPosition itemLabelPosition = new ItemLabelPosition(
				ItemLabelAnchor.INSIDE12, TextAnchor.CENTER_RIGHT,
				TextAnchor.CENTER_RIGHT, -1.57D);

		// �����������Ϊ�˽���������ӵı�����С�������±�ʾ�����ӱ�������ֵ�޷���ʾ������
		// ���ò�����������������ʾ����Щ��ֵ����ʾ��ʽ������Щ��ֵ��ʾ����������
		ItemLabelPosition itemLabelPositionFallback = new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT,
				TextAnchor.HALF_ASCENT_LEFT, -1.57D);

		// ����������ʾ������label��position
		renderer.setPositiveItemLabelPosition(itemLabelPosition);
		renderer.setNegativeItemLabelPosition(itemLabelPosition);

		// ���ò���������ʾ������label��position
		renderer
				.setPositiveItemLabelPositionFallback(itemLabelPositionFallback);
		renderer
				.setNegativeItemLabelPositionFallback(itemLabelPositionFallback);

		categoryPlot.setRenderer(renderer);
		// �������ӵ�͸����
		categoryPlot.setForegroundAlpha(0.8f);

		// Ϊ��ͼ���á��������ݡ�
		// ��һ����������ת�����ӵ�ַ
		renderer.setBaseItemURLGenerator(new StandardCategoryURLGenerator(
				"detail.jsp", "fruit", ""));

		// ʹ������Ч
		renderer.setBaseItemLabelsVisible(true);
		// ��������͸����
		categoryPlot.setForegroundAlpha(0.5f);
		// ���õ�������������ʾλ��
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
