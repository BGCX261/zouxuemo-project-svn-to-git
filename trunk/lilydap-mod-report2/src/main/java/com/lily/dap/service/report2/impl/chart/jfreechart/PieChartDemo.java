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
 * 用于演示饼图的生成
 * 
 * @author Winter Lau
 */
public class PieChartDemo {
	public static void main(String[] args) throws IOException {
		DefaultPieDataset data = getDataSet();
		JFreeChart chart = ChartFactory.createPieChart3D(
				"水果产量图", 	// 图表标题
				data, 			// 数据集
				true, 			// 是否显示图例
				false, 			// 是否生成工具
				false			// 是否生成URL链接
				);
		
		Font titleFont = new Font("黑体", Font.CENTER_BASELINE, 20);    
		Font legendFont = new Font("宋体", Font.CENTER_BASELINE, 12);    
		Font labelFont = new Font("宋体", Font.CENTER_BASELINE, 12);    
		
		//设置标题字体
		chart.getTitle().setFont(titleFont);

		//设置图例字体
		chart.getLegend().setItemFont(legendFont); 
		
		PiePlot pie = (PiePlot)chart.getPlot();
		
		//透明处理
//		pie.setDarkerSides(false);
		pie.setForegroundAlpha(Float.parseFloat("0.5"));
		
//		pie.setLabelGap(2);
		
		pie.setCircular(false);    
		
		//设置标饼上标签的标签字体
		pie.setLabelFont(labelFont);
		
		pie.setMaximumLabelWidth(0.35);    
		
//		pie.setExplodePercent("荔枝", 1.2);    

		//设置饼上标签的显示格式，其中，0：数据名称，1：数据值，2：数据占整个百分比
//		StandardPieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator("{0}\n{1}");
//		pie.setLabelGenerator(generator);
		
		pie.setLabelGenerator(new CustomLabelGenerator());
		
		pie.setLabelGenerator(new StandardPieSectionLabelGenerator(    
				StandardPieToolTipGenerator.DEFAULT_TOOLTIP_FORMAT));   
		
		// 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值，{2} 表示所占比例 ,小数点后两位    
		pie.setLabelGenerator(new StandardPieSectionLabelGenerator(    
				"{0}={1}({2})", NumberFormat.getNumberInstance(),    
				new DecimalFormat("0.00%")));    
		
		// 图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例    
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
	 * 获取一个演示用的简单数据集对象
	 * 
	 * @return
	 */
	private static DefaultPieDataset getDataSet() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("苹果苹果苹果苹果苹果苹果苹果", 100);
		dataset.setValue("梨子梨子梨子梨子梨子", 200);
		dataset.setValue("葡萄葡萄葡萄", 300);
		dataset.setValue("香蕉香蕉", 400);
		dataset.setValue("荔枝", 500);
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
