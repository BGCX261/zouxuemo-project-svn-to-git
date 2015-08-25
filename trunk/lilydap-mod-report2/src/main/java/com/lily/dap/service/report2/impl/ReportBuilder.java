/**
 * 
 */
package com.lily.dap.service.report2.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import bsh.EvalError;
import bsh.Interpreter;

import com.lily.dap.service.report2.impl.chart.ChartProxy;
import com.lily.dap.service.report2.impl.model.Cell;
import com.lily.dap.service.report2.impl.model.Chart;
import com.lily.dap.service.report2.impl.model.Row;
import com.lily.dap.service.report2.impl.model.Table;
import com.lily.dap.service.report2.util.VariableLengthTwoDimensionalArray;

/**
 * @author xuemozou
 * 
 * ���ɱ�������
 */
public class ReportBuilder {
	private static ReportFieldTokenProcessor fieldTokenProcessor = new ReportFieldTokenProcessor('{', '}');
	
	public static final String ROW_INDEX_PROPERTY= "$ROW_INDEX$";
	
//	private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * �ж�ֵ�Ƿ�Ϊ0��������ʽ��Ҫ���ַ�����ֻ�ܰ���1�����"0"����0��1��"."�������ַ�����
	 */
	private static Pattern matchZeroPattern = Pattern.compile("0{1,}.{0,1}0{0,}");
	
	//��Ϊ��ӳ��͵�Ԫ��ӳ������ݻ���
	private Map<String, Map<String, Object>> dataCacheMap = new HashMap<String, Map<String, Object>>();
	
	/**
	 * ͼ������������
	 */
	private ChartProxy chartProxy;
	
	public ReportBuilder(ChartProxy chartProxy) {
		this.chartProxy = chartProxy;
	}

	/**
	 * �ӻ����л�ȡ��ӳ������Map�����δ�ҵ�����Ӵ���Ķ��������ҵ���Ӧ����List��ȡ��һ��������Ϊ��ӳ������
	 * 
	 * @param dataSourceName
	 * @param dataMap
	 * @return
	 */
	private Map<String, Object> getTableMappingData(String dataSourceName, Map<String, List<Map<String, Object>>> dataMap) {
		String key = "table_" + dataSourceName;
		
		Map<String, Object> map = dataCacheMap.get(key);
		if (map == null) {
			List<Map<String, Object>> dataList = dataMap.get(dataSourceName);
			if (dataList == null || dataList.size() == 0)
				return null;
			
			map = dataList.get(0);
			dataCacheMap.put(key, map);
		}
		
		return map;
	}
	
	/**
	 * �ӻ����л�ȡ��Ԫ��ӳ������Map�����δ�ҵ�����Ӵ�������ݺͲ�����Ϣ�з�����������uniqueProperty��property���ԣ�����һ����uniqueProperty��ӦֵΪkey��property��ӦֵΪvalue��Map���󣬲����뻺����
	 * 
	 * @param dataSourceName
	 * @param dataMap
	 * @param configMap
	 * @return
	 */
	private Map<String, Object> getCellMappingData(String dataSourceName, Map<String, List<Map<String, Object>>> dataMap, Map<String, Map<String, String>>configMap) {
		String key = "cell_" + dataSourceName;
		
		Map<String, Object> map = dataCacheMap.get(key);
		if (map == null) {
			List<Map<String, Object>> dataList = dataMap.get(dataSourceName);
			if (dataList == null || dataList.size() == 0)
				return null;
			
			Map<String, String> config = configMap.get(key);
			if (config == null)
				return null;
			
			String uniqueProperty = config.get("uniqueProperty");
			String property = config.get("property");
			if (uniqueProperty == null || property == null)
				return null;
			
			String[] uniquePropertys = uniqueProperty.split(" ");
			
			map = new HashMap<String, Object>();
			for (Map<String, Object> record : dataList) {
				String name = "";
				for (String unique : uniquePropertys)
					name += record.get(unique) + ".";
				
				name = name.substring(0, name.length() - 1);
				
				Object value = record.get(property);
				
				map.put(name, value);
			}
			
			dataCacheMap.put(key, map);
		}
		
		return map;
	}
	
	public Table generateTableData(Table template, Map<String, List<Map<String, Object>>> dataMap, Map<String, Map<String, String>>configMap, Map<String, Object> paramMap) {
		Table table = new Table(template.getWidths(), template.getCssClass(), template.getCssStyle(), template.getDatasources(), template.getDisplayZero());
		Integer[] widths = template.getWidths().toArray(new Integer[template.getWidths().size()]);
		
		//����Ĭ�ϵı�ӳ������Դ��
		String defaultTableMappingDataSource = "";
		if (template.getDatasources().size() > 0)
			defaultTableMappingDataSource = template.getDatasources().get(0);
		
		VariableLengthTwoDimensionalArray<Integer> placeHold = new VariableLengthTwoDimensionalArray<Integer>(widths.length, 0);
		int placeHoldRowIndex = 0, placeHoldCellIndex = 0;
		
		//�Ա��ϵ���ѭ������
		for (int rowIndex = 0, rowCount = template.getData().size(); rowIndex <  rowCount; rowIndex++) {
			Row row = template.getData().get(rowIndex);
			String rowMappingDataSource = row.getDatasource();
			
			//�������������ӳ��ģʽ����������й��������ݣ���ӵ�����
			if (rowMappingDataSource != null && !"".equals(rowMappingDataSource)) {
				List<Row> list = loadRowMappingData(table, row, widths, dataMap.get(rowMappingDataSource), configMap.get("table_" + rowMappingDataSource));
				
				for (Row r : list)
					table.addData(r);
				
				//�������ӳ��ģʽ���������Ҫ��λռλ��������ʼ��ռλ��������Ϊ0
				placeHold.reset();
				placeHoldRowIndex = 0;
			//�������
			} else {
				//��¡һ��������
				Row clone = null;
				try {
					clone = row.clone();
					clone.setParent(table);
					
					for (Cell cell : clone.getData())
						cell.setParent(clone);
				} catch (CloneNotSupportedException e) {}
				
				//��չռλ��ά�����ά����ֹ��������ʱ��������Խ���쳣
				placeHold.extendArray(placeHoldRowIndex + 1);
				//���õ�Ԫ��������������0��ʼ
				placeHoldCellIndex = 0;
				
				//�����ϵĵ�Ԫ��ѭ������
				for (Cell cell : clone.getData()) {
					//���ҵ�ǰ��Ԫ�����ڵ���������
					while(placeHold.get(placeHoldRowIndex, placeHoldCellIndex) == 1)
						placeHoldCellIndex++;
					
					//���õ�ǰ��Ԫ���ռλ���Ǻţ�����Ϊ�´β��ҵ�Ԫ��������ʱ�Ĳο�
					for (int r = cell.getRowSpan() - 1; r >= 0; r--)
						for (int c = cell.getColSpan() - 1; c >= 0; c--)
							placeHold.set(placeHoldRowIndex + r, placeHoldCellIndex + c, 1);
					
					//���㵱ǰ��Ԫ��Ŀ�ȣ������ÿ������
					int width = 0;
					for (int c = cell.getColSpan() - 1; c >= 0; c--)
						width += widths[placeHoldCellIndex + c];
					cell.setWidth(width);
					
					//���㵱ǰ��Ԫ��ĸ߶ȣ������ø߶�����
					int height = 0;
					for (int r = cell.getRowSpan() - 1; r >= 0; r--)
						height += template.getData().get(rowIndex + r).getHeight();
					cell.setHeight(height);
					
					String cellMappingDataSource = cell.getDatasource();
					
					//�����ǰ��Ԫ�����ڵ�Ԫ��ӳ��ģʽ
					if (cellMappingDataSource != null && !"".equals(cellMappingDataSource)) {
						Map<String, Object> map = getCellMappingData(cellMappingDataSource, dataMap, configMap);
						
						writeCellMappingData(cell, map, configMap.get("cell_" + cellMappingDataSource));
					//���������property���ԣ���ǰ��Ԫ�����ڱ��ӳ��ģʽ
					} else if (cell.getProperty() != null){
						//���ҵ�ǰ��Ԫ��ӳ�������Դ
						String tableMappingDataSource = cell.getOwner();
						if (tableMappingDataSource == null || "".equals(tableMappingDataSource))
							tableMappingDataSource = defaultTableMappingDataSource;
						
						Map<String, Object> map = getTableMappingData(tableMappingDataSource, dataMap);
						
						writeTableMappingData(cell, map, configMap.get("table_" + tableMappingDataSource));
					//���������chart���ԣ���ǰ��Ԫ������ͼ����Ϣ
					} else if (cell.getChart() != null) {
						Chart chartConfig = cell.getChart();
						String chartDatasource = chartConfig.getDatasource();
						List<Map<String, Object>> dataList = dataMap.get(chartDatasource);
						if (dataList != null) {
							ByteArrayOutputStream os = new ByteArrayOutputStream();
							
							try {
								int chartWidth = (int)(cell.getWidth() * chartConfig.getWidth() / 100);
								int chartHeight = (int)(cell.getHeight() * chartConfig.getHeight() / 100);
								chartProxy.outputChart(chartConfig.getType(), chartHeight, chartWidth, chartConfig.getParams(), paramMap, dataList, os);
								
								cell.setContext(os.toByteArray().clone());
							} catch (IOException e) {
								throw new RuntimeException("����ͼ�����ݴ���" + e.getMessage());
							} finally {
								try {
									os.close();
								} catch (IOException e) {}
							}
						}
						
						table.addChartCellList(cell);
					} else {
						try {						
							cell.setContext(fieldTokenProcessor.evaluateFieldToken(cell.getContext().toString(), paramMap));
						} catch (Exception e) {						
							e.printStackTrace();
						}
					}
				}
				
				placeHoldRowIndex++;
				table.addData(clone);
			}
		}
		
		return table;
	}
	
	private void writeTableMappingData(Cell cell, Map<String, Object> map, Map<String, String>config) {
		if (map == null)
			return;
		
		String property = cell.getProperty();
		Object value = map.get(property);
		if (value == null)
			return;
		
		cell.setContext(processValue(cell, value, map));
	}
	
	private List<Row> loadRowMappingData(Table table, Row template, Integer[] widths, List<Map<String, Object>> data, Map<String, String>config) {
		List<Row> rowList = new ArrayList<Row>();
		
		if (data == null)
			return rowList;
		
		int index, len = template.getData().size();
		int height = template.getHeight();
		
		//������ڵ���Ϣ����װ������ʱ����ʹ��
		GroupNode[] groupNodes = new GroupNode[template.getGroupIds().size()];
		int groupNodeLength = 0;
		
		for (String groupId : template.getGroupIds())
			groupNodes[groupNodeLength++] = new GroupNode(groupId);
		
		//�ںϲ���ʱ���ÿ���ϲ����е�һ���ж�Ӧ�ĵ�Ԫ��
		Cell[] groupFirstCells = new Cell[len];
		
		//��ѭ���������ʱ����Ϊ������еĸ���Ԫ������飬���кϲ����кϲ�ʱ�������Ӧλ�õĵ�Ԫ������Ϊnull���������в�Ϊnull�ĵ�Ԫ�����õ��еĵ�Ԫ���б���
		Cell[] currRowCells = new Cell[len];

		//ÿһ����Ԫ��ĺϲ����ʶ������0���������Ա��1����Ҫ��ǰ�浥Ԫ��ϲ���-1������Ҫ��ǰ�浥Ԫ��ϲ�
		int[] mergeGroupFlags = new int[len];
		
		//��ÿ�������ƥ����ĵ�Ԫ��ʱ����ƥ��ĵ�Ԫ������λ����ӵ�����
		int matchCellIndexLength;
		int[] matchCellIndexs = new int[len];
		
		int rowSn = 0;
		for (Map<String, Object> record : data) {
			Row row = null;
			rowSn++;
			
			try {
				row = template.clone();
				row.setParent(table);
				
				for (Cell cell : row.getData())
					cell.setParent(row);
			} catch (CloneNotSupportedException e) {}
			
			for (index = 0; index < len; index++) {
				Cell cell = row.getData().get(index);
				cell.setHeight(height);
				
				int width = 0;
				for (int c = cell.getColSpan() - 1; c >= 0; c--)
					width += widths[index + c];
				cell.setWidth(width);
				
				currRowCells[index] = cell;
				
			}
			
			//��ȡ���ݲ�д�뵥Ԫ���ֵ�У����ж�����Ҫ�յ�Ԫ��ϲ��������ö�Ӧ�����ĵ�Ԫ��Ϊnull
			for (index = 0; index < len; index++) {
				Cell cell = currRowCells[index];
				String property = cell.getProperty();
				if (property == null)
					continue;
				
				Object value;
				if (ROW_INDEX_PROPERTY.equals(property))
					value = rowSn;
				else
					value = record.get(property);
				
				if (value == null || value.toString().length() == 0) {
					if (cell.isMergeblank()) {
						mergeBlankCell(currRowCells, index);
						
						currRowCells[index] = null;
					}
				} else
					cell.setContext(processValue(cell, value,record));
			}
			
			//��������÷��飬��ʼ����������
			if (groupNodeLength > 0) {
				/*
				 * ѭ�����ÿ���飬�Ƚ���Key����ǰ��Key�Ƿ���ͬ
				 * �����ĳ������ͬ�������ڸ����������Ҫ���кϲ�
				 * ������������ж���ͬ����ͬ������Ϊ�µ�����ʼ��
				 * */	
				
				//���������Ԫ��ϲ����ʶΪ0
				for (index = 0; index < len; index++)
					mergeGroupFlags[index] = 0;
				
				//ѭ�����ÿ������Ϣ�������ø���Ԫ��ϲ����ʶֵ
				for (int pos = 0; pos < groupNodeLength; pos++) {
					GroupNode groupNode = groupNodes[pos];
					String match = groupNode.match;
					
					//���ҵ�ǰ�������Keyֵ���������ڵ�ǰ����ĵ�Ԫ��������¼����
					matchCellIndexLength = 0;
					String groupKey = "";
					for (index = 0; index < len; index++) {
						Cell cell = currRowCells[index];
						if (cell == null || cell.getGroupIds() == null)
							continue;
						
						if (cell.getGroupIds().indexOf(match) >= 0) {
							groupKey += cell.getContext() + "|";
							
							matchCellIndexs[matchCellIndexLength++] = index;
						}
					}
					
					//�����ǰ�еĵ�ǰ��Key����ǰ����Key��ͬ������Ҫ����ǰ���Ӧ��Ԫ��ϲ������õ�Ԫ��ϲ����ʶֵΪ1
					if (groupNode.preGroupKey != null && groupKey.equals(groupNode.preGroupKey)) {
						for (int i = matchCellIndexLength - 1; i >= 0; i--)
							mergeGroupFlags[matchCellIndexs[i]] = 1;
						//�����ǰ�еĵ�ǰ��Key����ǰ����Key��ͬ������Ҫ����ǰ���Ӧ��Ԫ��ϲ������õ�Ԫ��ϲ����ʶֵΪ-1
					} else {
						groupNode.preGroupKey = groupKey;
	
						//ֻ�ж���δ����1�ĵ�Ԫ��ĺϲ���ʶ�Ż�����Ϊ-1
						for (int i = matchCellIndexLength - 1; i >= 0; i--)
							if (mergeGroupFlags[matchCellIndexs[i]] == 0)
								mergeGroupFlags[matchCellIndexs[i]] = -1;
					}
				}
				
				//ѭ����ÿ����Ԫ��
				for (index = 0; index < len; index++) {
					//�����ǰ��Ԫ����Ҫ�ϲ��飬��ɾ����ǰ��Ԫ�񣬲������һ����Ԫ��rowSpan���ϵ�ǰ��Ԫ���rowSpan��height���ϵ�ǰ��Ԫ���height
					if (mergeGroupFlags[index] == 1) {
						groupFirstCells[index].setRowSpan(groupFirstCells[index].getRowSpan() + currRowCells[index].getRowSpan());
						groupFirstCells[index].setHeight(groupFirstCells[index].getHeight() + currRowCells[index].getHeight());
						
						currRowCells[index] = null;
					//�����ǰ��Ԫ����Ҫ�ϲ��飬���õ�ǰ��Ԫ���Ϊ���һ����Ԫ��
					} else if (mergeGroupFlags[index] == -1) {
						groupFirstCells[index] = currRowCells[index];
					}
				}
			}
			
			//����װ��û��ɾ���ĵ�Ԫ��
			row.getData().clear();
			for (index = 0; index < len; index++)
				if (currRowCells[index] != null)
					row.getData().add(currRowCells[index]);
			
			rowList.add(row);
		}
		
		return rowList;
	}
	
	private void mergeBlankCell(Cell[] cells, int mergeCellIndex) {
		//����ϲ���Ԫ���ǵ�һ����Ԫ�����ǰһ����Ԫ��ϲ�������ͺ�һ����Ԫ��ϲ�
		int pos = -1;
		if (mergeCellIndex > 0) {
			pos = mergeCellIndex - 1;
			while (pos >= 0 && cells[pos] == null)
				pos--;
		}
		
		if (pos == -1)
			pos = mergeCellIndex + 1;
		
		//�ҵ��ĵ�Ԫ���colSpan����Ҫ�ϲ��ĵ�Ԫ���colSpanֵ
		cells[pos].setColSpan(cells[pos].getColSpan() + cells[mergeCellIndex].getColSpan());
		
		//�ҵ��ĵ�Ԫ���width����Ҫ�ϲ��ĵ�Ԫ���widthֵ
		cells[pos].setWidth(cells[pos].getWidth() + cells[mergeCellIndex].getWidth());
	}
	
	private void writeCellMappingData(Cell cell, Map<String, Object> map, Map<String, String>config) {
		if (map == null)
			return;
		
		String uniqueValue = cell.getUniqueValue();
		Object value = map.get(uniqueValue);
		if (value == null)
			return;
		
		cell.setContext(processValue(cell, value,map));
	}
	
	private String processValue(Cell cell, Object value, Map<String, Object> record) {
		try {
			if (cell.getRenderer() != null && !"".equals(cell.getRenderer())) {
				Interpreter interpreter = new Interpreter();
				interpreter.set("_this", value);
				for (Map.Entry<String, Object> entry : record.entrySet())
					interpreter.set(entry.getKey(), entry.getValue());
				
				value = interpreter.eval(cell.getRenderer());
			}
		} catch (EvalError e) {
			throw new RuntimeException("ִ����Ⱦ�ű�����/r/n" + cell.getRenderer(), e);
		}
		
		if (value instanceof Number) {
			if (matchZeroPattern.matcher(value.toString()).matches()) {
				String displayZero = cell.getDisplayZero();
				if (displayZero == null)
					displayZero = cell.getParent().getParent().getDisplayZero();
					
				if (displayZero != null)
					return displayZero;
			}
				
			DecimalFormat df = new DecimalFormat();
			df.setGroupingUsed(false);
			
			int scale = cell.getScale();
			if (scale >= 0) {
				df.setMaximumFractionDigits(scale);
				df.setMinimumFractionDigits(scale);
			}
			
			return df.format(value);
		} else if (value instanceof Date) {
			String result = sdfTime.format((Date)value);
			if (result.endsWith(" 00:00:00"));
				result = result.substring(0, result.length() - 9);
			
			return result;
		} else {
			return value.toString();
		}
	}
	
	private static class GroupNode {
		public String groupId;
		
		public String match;
		
		public String preGroupKey = null;

		public GroupNode(String groupId) {
			this.groupId = groupId;
			this.match = " " + this.groupId + " ";
		}
	}
	
	/**
	 * @param args
	 * @throws CloneNotSupportedException 
	 */
	public static void main(String[] args) throws CloneNotSupportedException {
		Object d = new Double(0.00);
		Object f = new Float(00.000);
		Object i = new Integer(0);
		Object l = new Long(0);
		
		System.out.println(d.toString());
		System.out.println(f.toString());
		System.out.println(l.toString());
		System.out.println(i.toString());
		
		System.out.println(Float.parseFloat("0.0"));
		
		DecimalFormat df = new DecimalFormat("##.000");
		System.out.println(df.format(1234.56));
		System.out.println(df.format(new Long(67)));
		
		Row row = new Row();
		row.addData(new Cell());
		row.addData(new Cell());
		
		Row clone = row.clone();
		clone.addData(new Cell());
		for (Cell cell : clone.getData())
			cell.setContext("test");
		
		for (Cell cell : row.getData())
			System.out.print(cell.getContext() + " ");
		System.out.println();
		for (Cell cell : clone.getData())
			System.out.print(cell.getContext() + " ");
		
		System.out.println("/r--------------------------------------------");
		Pattern p = Pattern.compile("0{0,}.{0,1}0{0,}");
		
		System.out.println(p.matcher("0.0000").matches());
		System.out.println(p.matcher("0").matches());
		System.out.println(p.matcher("0..").matches());
		System.out.println(p.matcher("3.0000").matches());
		System.out.println(p.matcher(".04").matches());
		System.out.println(p.matcher(".").matches());
		
//		System.out.println("--------------------------------------------");
//		
//		System.out.println(new Double(0) instanceof Number);
//		System.out.println(new Float(0) instanceof Number);
//		System.out.println(new Integer(0) instanceof Number);
//		System.out.println(new Long(0) instanceof Number);
//		Object o = new Date();
//		System.out.println(o instanceof Number);
//		o = new BigDecimal(5);
//		System.out.println(o instanceof Number);
	}
}
