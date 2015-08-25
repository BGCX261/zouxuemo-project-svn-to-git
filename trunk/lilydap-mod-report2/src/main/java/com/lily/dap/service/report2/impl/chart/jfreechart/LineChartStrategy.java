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
 * ����ͼ���ɲ���
 */
/*
<chart type="line" datasource="XXX">						<!--ָ������ͼ�����ͺ�ͼ������Դ-->
	<param name="title">����ͼ��ʾ</param>					<!--ָ��ͼ����⣬������ѡ-->
	<param name="backgroundcolor">0000ff</param>			<!--ָ��ͼ����ɫ������ɫΪRGB��ʽ�����磺a0a0a0��������ѡ-->
	<param name="color">0000ff,...,ff0000</param>			<!--ָ������������ɫ������������ɫ֮����","�ָ�����ɫΪRGB��ʽ�����磺a0a0a0��������ѡ-->
	<param name="namefield">field2</param>					<!--ָ������ͼ�ĸ������ƣ�����-->
	<param name="categoryfield">field1</param>				<!--ָ������ͼ�ĸ�����࣬������ѡ-->
	<param name="valuefield">field3</param>					<!--ָ������ͼ�ĸ���ֵ�����ָ����categoryfield�������򱾲�������-->
	<param name="categoryfields">field1,..,fieldn</param>	<!--ָ������ͼ�ķ���ֵ�ֶΣ�����ֶ�֮����","�ָ���������ѡ-->
	<param name="categorynames">name1,...namen</param>		<!--ָ������ͼ�ķ���ֵ��Ӧ�ķ������ƣ�����ֶ�֮����","�ָ������ָ����categoryfields�������򱾲���Ϊcategoryfields�ж�Ӧ���������ֶεķ������ƣ�������ѡ����������ã����������ȡ�����ֶ���-->
	<param name="categoryaxislabel">Ŀ¼���ǩ</param>		<!--ָ��Ŀ¼���ǩ����������ѡ-->
	<param name="valueaxislabel">��ֵ���ǩ</param>			<!--ָ����ֵ���ǩ����������ѡ-->
	<param name="valueaxisarrow">true</param>				<!--��ֵ���Ƿ���ʾ��ͷ��Ĭ�ϲ���ʾ-->
	<param name="showbaseshape">true</param>				<!--�Ƿ������߼���ʾ�㣬Ĭ�ϲ���ʾ-->
	<param name="legend">true</param>						<!--�Ƿ���ʾ��Ŀ˵����Ĭ�ϲ���ʾ-->
	<param name="horizontal">false</param>					<!--����ͼ�Ƿ�ˮƽ��ʾ��Ĭ�ϴ�ֱ��ʾ-->
</chart>
*/
public class LineChartStrategy implements ChartStrategy {
	private static final String[] supportTypes = new String[]{ChartConstants.TYPE_LINE};
	
	private static final Font titleFont = new Font("����", Font.CENTER_BASELINE, 20);    
	private static final Font legendFont = new Font("����", Font.CENTER_BASELINE, 12);    
	private static final Font labelFont = new Font("����", Font.CENTER_BASELINE, 12);    
	private static final Font tickLabelFont = new Font("����", Font.CENTER_BASELINE, 10);    

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
        	title,      // ͼ�����
        	categoryAxisLabel, // Ŀ¼�����ʾ��ǩ
        	valueAxisLabel, // ��ֵ�����ʾ��ǩ
            dataset,    // ���ݼ�
            orientation,// ͼ����ˮƽ����ֱ
            legend,     // �Ƿ���ʾͼ��
            false,		// �Ƿ����ɹ���
            false      // �Ƿ�����URL����
        );
		
        if (paramMap.get("backgroundcolor") != null) {
        	Color color = ChartUtils.createColor(ChartUtils.evaluateParam(paramMap.get("backgroundcolor"), variableMap));
        	
        	chart.setBackgroundPaint(color);
        }
        	
		//���ñ�������
		if (chart.getTitle() != null)
			chart.getTitle().setFont(titleFont);

		//����ͼ������
		if (chart.getLegend() != null)
			chart.getLegend().setItemFont(legendFont);
        
		CategoryPlot plot = (CategoryPlot) chart.getPlot();

		if (paramMap.get("showbaseshape") != null && "true".equals(ChartUtils.evaluateParam(paramMap.get("showbaseshape"), variableMap).toLowerCase())) {
			LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}
		
		//����Ŀ¼��ı�ǩ����Ϳ̶�����
		if (plot.getDomainAxis() != null) {
			CategoryAxis categoryAxis = plot.getDomainAxis();
			
			categoryAxis.setLabelFont(labelFont);
			categoryAxis.setTickLabelFont(tickLabelFont);
		}
		
		//������ֵ��ı�ǩ����Ϳ̶�����
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
	 * װ�����������µ�����Դ����
	 * <p>	value	category	name
	 * <p>----------------------------
	 * <p>	100, 	"����", 	"ƻ��"
	 * <p>	100, 	"�Ϻ�", 	"ƻ��"
	 * <p>	100, 	"����", 	"ƻ��"
	 * <p>	200, 	"����", 	"����"
	 * <p>	200, 	"�Ϻ�", 	"����"
	 * <p>	200, 	"����", 	"����"
	 * <p>	300, 	"����", 	"����"
	 * <p>	300, 	"�Ϻ�", 	"����"
	 * <p>	300, 	"����", 	"����"
	 * <p>	400, 	"����", 	"�㽶"
	 * <p>	400, 	"�Ϻ�", 	"�㽶"
	 * <p>	400, 	"����", 	"�㽶"
	 * <p>	500, 	"����", 	"��֦"
	 * <p>	500, 	"�Ϻ�", 	"��֦"
	 * <p>	500, 	"����", 	"��֦"
	 * ����
	 * <p>	value	name
	 * <p>----------------
	 * <p>	100, 	"ƻ��"
	 * <p>	200, 	"����"
	 * <p>	300, 	"����"
	 * <p>	400, 	"�㽶"
	 * <p>	500, 	"��֦"
	 
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
			throw new RuntimeException("���õķ����ֶλ��������ֶλ���ֵ�ֶ�������Դ�в����ڣ��������õ��ֶ������Ƿ���ȷ����������Դ�����Ƿ���ȷ��");
		
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
	 * װ�����������µ�����Դ����
	 * <p>	2007		2008		name
	 * <p>--------------------------------
	 * <p>	20, 		10,		 	"ƻ��"
	 * <p>	30, 		20, 		"����"
	 * <p>	40, 		30, 		"����"
	 * <p>	50, 		40, 		"�㽶"
	 * <p>	60, 		50, 		"��֦"
	 * 
	 * @param dataList
	 * @param nameField
	 * @param categoryFields
	 * @param categoryNames �����������飬���Ϊnull����ȡ�����ֶ�����Ϊ��������
	 * @return
	 */
	private CategoryDataset createDataset(List<Map<String, Object>> dataList, String nameField, String[] categoryFields, String[] categoryNames) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		if (dataList.size() == 0)
			return dataset;
		
		int index, categoryLength = categoryFields.length;
		
		Map<String, Object> firstMap = dataList.get(0);
		if (!firstMap.containsKey(nameField))
			throw new RuntimeException("���õ������ֶ�������Դ�в����ڣ��������õ��ֶ������Ƿ���ȷ����������Դ�����Ƿ���ȷ��");
		
		for (index = 0; index < categoryLength; index++)
			if (!firstMap.containsKey(categoryFields[index]))
			throw new RuntimeException("���õķ����ֶ�������Դ�в����ڣ��������õ��ֶ������Ƿ���ȷ����������Դ�����Ƿ���ȷ��");
		
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
