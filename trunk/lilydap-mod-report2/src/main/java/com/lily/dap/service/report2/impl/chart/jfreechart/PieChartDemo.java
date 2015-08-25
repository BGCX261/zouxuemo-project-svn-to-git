package com.lily.dap.service.report2.impl.chart.jfreechart;

import java.awt.Font;
import java.awt.font.TextAttribute;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.AttributedString;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

/**
 * ������ʾ��ͼ������
 * 
 * @author Winter Lau
 */
public class PieChartDemo {
	public static void main(String[] args) throws IOException {
		DefaultPieDataset data = getDataSet();
		JFreeChart chart = ChartFactory.createPieChart3D(
				"ˮ������ͼ", 	// ͼ�����
				data, 			// ���ݼ�
				true, 			// �Ƿ���ʾͼ��
				false, 			// �Ƿ����ɹ���
				false			// �Ƿ�����URL����
				);
		
		Font titleFont = new Font("����", Font.CENTER_BASELINE, 20);    
		Font legendFont = new Font("����", Font.CENTER_BASELINE, 12);    
		Font labelFont = new Font("����", Font.CENTER_BASELINE, 12);    
		
		//���ñ�������
		chart.getTitle().setFont(titleFont);

		//����ͼ������
		chart.getLegend().setItemFont(legendFont); 
		
		PiePlot pie = (PiePlot)chart.getPlot();
		
		//͸������
//		pie.setDarkerSides(false);
		pie.setForegroundAlpha(Float.parseFloat("0.5"));
		
//		pie.setLabelGap(2);
		
		pie.setCircular(false);    
		
		//���ñ���ϱ�ǩ�ı�ǩ����
		pie.setLabelFont(labelFont);
		
		pie.setMaximumLabelWidth(0.35);    
		
//		pie.setExplodePercent("��֦", 1.2);    

		//���ñ��ϱ�ǩ����ʾ��ʽ�����У�0���������ƣ�1������ֵ��2������ռ�����ٷֱ�
//		StandardPieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{0}\n{1}");
//		pie.setLabelGenerator(generator);
		
		pie.setLabelGenerator(new CustomLabelGenerator());
		
		pie.setLabelGenerator(new StandardPieSectionLabelGenerator(    
				StandardPieToolTipGenerator.DEFAULT_TOOLTIP_FORMAT));   
		
		// ͼƬ����ʾ�ٷֱ�:�Զ��巽ʽ��{0} ��ʾѡ� {1} ��ʾ��ֵ��{2} ��ʾ��ռ���� ,С�������λ    
		pie.setLabelGenerator(new StandardPieSectionLabelGenerator(    
				"{0}={1}({2})", NumberFormat.getNumberInstance(),    
				new DecimalFormat("0.00%")));    
		
		// ͼ����ʾ�ٷֱ�:�Զ��巽ʽ�� {0} ��ʾѡ� {1} ��ʾ��ֵ�� {2} ��ʾ��ռ����    
		pie.setLegendLabelGenerator(new StandardPieSectionLabelGenerator( "{0},{1},{2}"));  	
		
		FileOutputStream fos_jpg = null;
		try {
			fos_jpg = new FileOutputStream("D:\\pie.jpg");
			ChartUtilities.writeChartAsJPEG(fos_jpg, 1, chart, 400, 400, null);
		} finally {
			try {
				fos_jpg.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * ��ȡһ����ʾ�õļ����ݼ�����
	 * 
	 * @return
	 */
	private static DefaultPieDataset getDataSet() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("ƻ��ƻ��ƻ��ƻ��ƻ��ƻ��ƻ��", 100);
		dataset.setValue("��������������������", 200);
		dataset.setValue("������������", 300);
		dataset.setValue("�㽶�㽶", 400);
		dataset.setValue("��֦", 500);
		return dataset;
	}

    static class CustomLabelGenerator implements PieSectionLabelGenerator {
        
        /**
         * Generates a label for a pie section.
         * 
         * @param dataset  the dataset (<code>null</code> not permitted).
         * @param key  the section key (<code>null</code> not permitted).
         * 
         * @return The label (possibly <code>null</code>).
         */
        public String generateSectionLabel(PieDataset dataset, Comparable key) {
            String result = null;    
            if (dataset != null) {
//                if (!key.equals("Two")) {
                    result = key.toString();   
//                }
            }
            return result;
        }
        
        public AttributedString generateAttributedSectionLabel(
                PieDataset dataset, Comparable key) {
            AttributedString result = null;
            String keyStr = key.toString();
            String text = keyStr + " : " + String.valueOf(dataset.getValue(key));
            result = new AttributedString(text);
            result.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD,
                    0, keyStr.length() - 1);
            return result;
        }
   
    }
}
