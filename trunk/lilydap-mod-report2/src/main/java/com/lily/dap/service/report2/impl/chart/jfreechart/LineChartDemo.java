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
 * ����������ʾ��򵥵�����ͼ����
 * @author Winter Lau
 */
public class LineChartDemo {
	public static void main(String[] args) throws IOException{
		CategoryDataset dataset = getDataSet2();
		JFreeChart chart = ChartFactory.createLineChart(
				"ˮ������ͼ", 				// ͼ�����
				"ˮ��", 					// Ŀ¼�����ʾ��ǩ
				"����", 					// ��ֵ�����ʾ��ǩ
				dataset, 					// ���ݼ�
				PlotOrientation.VERTICAL, 	// ͼ����ˮƽ����ֱ
				true, 						// �Ƿ���ʾͼ��
				false, 						// �Ƿ����ɹ���
				false 						// �Ƿ�����URL����
				);
		
		Font titleFont = new Font("����", Font.CENTER_BASELINE, 20);    
		Font legendFont = new Font("����", Font.CENTER_BASELINE, 12);    
		Font labelFont = new Font("����", Font.CENTER_BASELINE, 12);    
		Font tickLabelFont = new Font("����", Font.CENTER_BASELINE, 10);    

		//���ñ�������
		chart.getTitle().setFont(titleFont);

		//����ͼ������
		chart.getLegend().setItemFont(legendFont); 

		CategoryPlot plot = (CategoryPlot)chart.getPlot();
	
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
	    renderer.setBaseShapesVisible(true);
	    renderer.setBaseShapesFilled(true);
        
		//����Ŀ¼��ı�ǩ����Ϳ̶�����
		plot.getDomainAxis().setLabelFont(labelFont);
		plot.getDomainAxis().setTickLabelFont(tickLabelFont);
		
		//������ֵ��ı�ǩ����Ϳ̶�����
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
	 * ��ȡһ����ʾ�õļ����ݼ�����
	 * @return
	 */
	private static CategoryDataset getDataSet() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(100, null, "ƻ��");
		dataset.addValue(200, null, "����");
		dataset.addValue(300, null, "����");
		dataset.addValue(400, null, "�㽶");
		dataset.addValue(500, null, "��֦");
		return dataset;
	}
	/**
	 * ��ȡһ����ʾ�õ�������ݼ�����
	 * @return
	 */
	private static CategoryDataset getDataSet2() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(100, "����", "ƻ��");
		dataset.addValue(130, "�Ϻ�", "ƻ��");
		dataset.addValue(180, "����", "ƻ��");
		dataset.addValue(200, "����", "����");
		dataset.addValue(250, "�Ϻ�", "����");
		dataset.addValue(290, "����", "����");
		dataset.addValue(300, "����", "����");
		dataset.addValue(340, "�Ϻ�", "����");
		dataset.addValue(360, "����", "����");
		dataset.addValue(400, "����", "�㽶");
		dataset.addValue(430, "�Ϻ�", "�㽶");
		dataset.addValue(480, "����", "�㽶");
		dataset.addValue(500, "����", "��֦");
		dataset.addValue(590, "�Ϻ�", "��֦");
		dataset.addValue(550, "����", "��֦");
		return dataset;
	}
}
