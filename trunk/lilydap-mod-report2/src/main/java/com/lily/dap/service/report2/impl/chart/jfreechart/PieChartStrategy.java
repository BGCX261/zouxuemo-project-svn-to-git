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
<chart type="pie/pie3d" datasource="XXX">				<!--ָ����ͼ�����ͣ���ά/��ά����ͼ������Դ-->
	<param name="title">��ͼ��ʾ</param>				<!--ָ��ͼ����⣬������ѡ-->
	<param name="backgroundcolor">0000ff</param>		<!--ָ��ͼ����ɫ������ɫΪRGB��ʽ�����磺a0a0a0��������ѡ-->
	<param name="color">0000ff,...,ff0000</param>		<!--ָ���������ɫ��������ɫ֮����","�ָ�����ɫΪRGB��ʽ�����磺a0a0a0��������ѡ-->
	<param name="namefield">field2</param>				<!--ָ����ͼ�ĸ������ƣ�����-->
	<param name="valuefield">field3</param>				<!--ָ����ͼ�ĸ���ֵ������-->
	<param name="foregroundAlpha">0.5</param>			<!--ָ����ͼ��ǰ��͸���ȣ�һ��������Ϊ��ά��ͼʱʹ�ã���ΧΪ0��1֮��ĸ�������Ϊ0ʱȫ͸����Ϊ1ʱ��͸����Ĭ��Ϊ��͸��-->
	<param name="circular">true</param>					<!--ָ����ʾ�ı�ͼ�Ƿ�ΪԲ�λ�����Բ�Σ�true��Բ�Σ�false����Բ�Σ�Ĭ����Բ��-->
	<param name="legend">true</param>					<!--�Ƿ���ʾ��Ŀ˵����true����ʾ˵����false������ʾ˵����Ĭ�ϲ���ʾ-->
	<param name="labelformat">{0}\n{1}\n{2}</param>		<!--ָ����ͼ�ı�ǩ��ʽ����ʽ������"{0}\n{1}\n{2}"�����У�0���������ƣ�1������ֵ��2������ռ�����ٷֱȣ�\n��ʾ����-->
	<param name="legendformat">{0}\n{1}\n{2}</param>	<!--ָ����Ŀ˵���ı�ǩ��ʽ����ʽ������"{0}\n{1}\n{2}"�����У�0���������ƣ�1������ֵ��2������ռ�����ٷֱȣ�\n��ʾ����-->
	<param name="maximumlabelwidth">0.3</param>			<!--ָ�������ǩlabel������ȣ������plot�İٷֱȣ���ΧΪ0��1֮��ĸ�����-->
	<param name="foregroundAlpha">0.5</param>			<!--ָ����ͼ��ǰ��͸���ȣ�һ��������Ϊ��ά��ͼʱʹ�ã���ΧΪ0��1֮��ĸ�������Ϊ0ʱȫ͸����Ϊ1ʱ��͸����Ĭ��Ϊ��͸��-->
</chart>
*/
public class PieChartStrategy implements ChartStrategy {
	private static final String[] supportTypes = new String[]{ChartConstants.TYPE_PIE, ChartConstants.TYPE_PIE3D};
	
	private static final Font titleFont = new Font("����", Font.CENTER_BASELINE, 20);    
	private static final Font legendFont = new Font("����", Font.CENTER_BASELINE, 12);    
	private static final Font labelFont = new Font("����", Font.CENTER_BASELINE, 12);    

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
	        		title, 			// ͼ�����
					dataset, 		// ���ݼ�
					legend, 		// �Ƿ���ʾͼ��
					false, 			// �Ƿ����ɹ���
					false			// �Ƿ�����URL����
					);
        else
        	chart = ChartFactory.createPieChart(
	        		title, 			// ͼ�����
					dataset, 		// ���ݼ�
					legend, 		// �Ƿ���ʾͼ��
					false, 			// �Ƿ����ɹ���
					false			// �Ƿ�����URL����
					);
		
		//���ñ�������
		if (chart.getTitle() != null)
			chart.getTitle().setFont(titleFont);

		//����ͼ������
		if (chart.getLegend() != null)
			chart.getLegend().setItemFont(legendFont);
		
		PiePlot pie = (PiePlot)chart.getPlot();
		
//		if (pie instanceof PiePlot3D) {
//			PiePlot3D pie3d = (PiePlot3D)pie;
//		}
		
		//����ǰ��͸����
		if (paramMap.get("foregroundAlpha") != null) {
			float f = Float.parseFloat(ChartUtils.evaluateParam(paramMap.get("foregroundAlpha"), variableMap));
			
			pie.setForegroundAlpha(f);
		}
		
		//���ñ�ǩ�������
		if (paramMap.get("maximumlabelwidth") != null) {
			float f = Float.parseFloat(ChartUtils.evaluateParam(paramMap.get("maximumlabelwidth"), variableMap));
			
			pie.setMaximumLabelWidth(f);
		}
		
		//���ñ���ϱ�ǩ�ı�ǩ����
		pie.setLabelFont(labelFont);

		String labelFormat = paramMap.get("labelformat");
		if (labelFormat != null) {
			/*
			 * ��Ϊlabelformat�������ܰ���{0}��{1}��{2}�������ַ���������Ҫͨ�����'{'��������ַ��ǲ������֣����ж�labelformat�����ǲ��Ǳ��ʽ
			 */
			int index = labelFormat.indexOf('{');
			
			if (index >= 0) {
				char ch = labelFormat.charAt(index + 1);
				
				if (!CharUtils.isAsciiNumeric(ch))
					labelFormat = ChartUtils.evaluateParam(labelFormat, variableMap);
			}
	 		
			//���ñ��ϱ�ǩ����ʾ��ʽ�����У�0���������ƣ�1������ֵ��2������ռ�����ٷֱ�
			StandardPieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator(labelFormat);
			pie.setLabelGenerator(generator);
		}

		String legendformat = paramMap.get("legendformat");
		if (legendformat != null) {
			/*
			 * ��Ϊlegendformat�������ܰ���{0}��{1}��{2}�������ַ���������Ҫͨ�����'{'��������ַ��ǲ������֣����ж�legendformat�����ǲ��Ǳ��ʽ
			 */
			int index = legendformat.indexOf('{');
			
			if (index >= 0) {
				char ch = legendformat.charAt(index + 1);
				
				if (!CharUtils.isAsciiNumeric(ch))
					legendformat = ChartUtils.evaluateParam(legendformat, variableMap);
			}
	 		
			//���ñ��ϱ�ǩ����ʾ��ʽ�����У�0���������ƣ�1������ֵ��2������ռ�����ٷֱ�
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
		
		//���ñ�ͼ��Բ�λ�����Բ��
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
			throw new RuntimeException("���õ������ֶλ���ֵ�ֶ�������Դ�в����ڣ��������õ��ֶ������Ƿ���ȷ����������Դ�����Ƿ���ȷ��");
		
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
