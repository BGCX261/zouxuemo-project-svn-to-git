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
 * 生成报表数据
 */
public class ReportBuilder {
	private static ReportFieldTokenProcessor fieldTokenProcessor = new ReportFieldTokenProcessor('{', '}');
	
	public static final String ROW_INDEX_PROPERTY= "$ROW_INDEX$";
	
//	private static SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 判断值是否为0的正则表达式（要求字符串中只能包含1到多个"0"、和0到1个"."这样的字符串）
	 */
	private static Pattern matchZeroPattern = Pattern.compile("0{1,}.{0,1}0{0,}");
	
	//作为表映射和单元格映射的数据缓存
	private Map<String, Map<String, Object>> dataCacheMap = new HashMap<String, Map<String, Object>>();
	
	/**
	 * 图表生成器代理
	 */
	private ChartProxy chartProxy;
	
	public ReportBuilder(ChartProxy chartProxy) {
		this.chartProxy = chartProxy;
	}

	/**
	 * 从缓存中获取表映射数据Map，如果未找到，则从穿入的鹅数据中找到对应数据List，取第一条数据作为表映射数据
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
	 * 从缓存中获取单元格映射数据Map，如果未找到，则从传入的数据和参数信息中分析，并根据uniqueProperty和property属性，创建一个以uniqueProperty对应值为key，property对应值为value的Map对象，并存入缓存中
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
		
		//查找默认的表映射数据源名
		String defaultTableMappingDataSource = "";
		if (template.getDatasources().size() > 0)
			defaultTableMappingDataSource = template.getDatasources().get(0);
		
		VariableLengthTwoDimensionalArray<Integer> placeHold = new VariableLengthTwoDimensionalArray<Integer>(widths.length, 0);
		int placeHoldRowIndex = 0, placeHoldCellIndex = 0;
		
		//对表上的行循环处理
		for (int rowIndex = 0, rowCount = template.getData().size(); rowIndex <  rowCount; rowIndex++) {
			Row row = template.getData().get(rowIndex);
			String rowMappingDataSource = row.getDatasource();
			
			//如果本行属于行映射模式，则从数据中构建行数据，添加到表中
			if (rowMappingDataSource != null && !"".equals(rowMappingDataSource)) {
				List<Row> list = loadRowMappingData(table, row, widths, dataMap.get(rowMappingDataSource), configMap.get("table_" + rowMappingDataSource));
				
				for (Row r : list)
					table.addData(r);
				
				//如果是行映射模式，则处理完后要复位占位符，并初始化占位符行索引为0
				placeHold.reset();
				placeHoldRowIndex = 0;
			//如果不是
			} else {
				//克隆一份行数据
				Row clone = null;
				try {
					clone = row.clone();
					clone.setParent(table);
					
					for (Cell cell : clone.getData())
						cell.setParent(clone);
				} catch (CloneNotSupportedException e) {}
				
				//扩展占位二维数组的维数防止访问数据时发生数组越界异常
				placeHold.extendArray(placeHoldRowIndex + 1);
				//设置单元格列索引计数从0开始
				placeHoldCellIndex = 0;
				
				//对行上的单元格循环处理
				for (Cell cell : clone.getData()) {
					//查找当前单元格所在的列索引号
					while(placeHold.get(placeHoldRowIndex, placeHoldCellIndex) == 1)
						placeHoldCellIndex++;
					
					//设置当前单元格的占位符记号，以作为下次查找单元格列索引时的参考
					for (int r = cell.getRowSpan() - 1; r >= 0; r--)
						for (int c = cell.getColSpan() - 1; c >= 0; c--)
							placeHold.set(placeHoldRowIndex + r, placeHoldCellIndex + c, 1);
					
					//计算当前单元格的宽度，并设置宽度属性
					int width = 0;
					for (int c = cell.getColSpan() - 1; c >= 0; c--)
						width += widths[placeHoldCellIndex + c];
					cell.setWidth(width);
					
					//计算当前单元格的高度，并设置高度属性
					int height = 0;
					for (int r = cell.getRowSpan() - 1; r >= 0; r--)
						height += template.getData().get(rowIndex + r).getHeight();
					cell.setHeight(height);
					
					String cellMappingDataSource = cell.getDatasource();
					
					//如果当前单元格属于单元格映射模式
					if (cellMappingDataSource != null && !"".equals(cellMappingDataSource)) {
						Map<String, Object> map = getCellMappingData(cellMappingDataSource, dataMap, configMap);
						
						writeCellMappingData(cell, map, configMap.get("cell_" + cellMappingDataSource));
					//如果设置了property属性，则当前单元格属于表格映射模式
					} else if (cell.getProperty() != null){
						//查找当前单元格映射的数据源
						String tableMappingDataSource = cell.getOwner();
						if (tableMappingDataSource == null || "".equals(tableMappingDataSource))
							tableMappingDataSource = defaultTableMappingDataSource;
						
						Map<String, Object> map = getTableMappingData(tableMappingDataSource, dataMap);
						
						writeTableMappingData(cell, map, configMap.get("table_" + tableMappingDataSource));
					//如果设置了chart属性，则当前单元格生成图表信息
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
								throw new RuntimeException("生产图表数据错误：" + e.getMessage());
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
		
		//构建组节点信息，供装载数据时分析使用
		GroupNode[] groupNodes = new GroupNode[template.getGroupIds().size()];
		int groupNodeLength = 0;
		
		for (String groupId : template.getGroupIds())
			groupNodes[groupNodeLength++] = new GroupNode(groupId);
		
		//在合并组时存放每个合并行中第一个行对应的单元格
		Cell[] groupFirstCells = new Cell[len];
		
		//在循环添加新行时，作为存放新行的各单元格的数组，在行合并和列合并时，会把相应位置的单元格设置为null，最后把所有不为null的单元格设置到行的单元格列表中
		Cell[] currRowCells = new Cell[len];

		//每一个单元格的合并组标识，其中0：不是组成员，1：需要和前面单元格合并，-1：不需要和前面单元格合并
		int[] mergeGroupFlags = new int[len];
		
		//在每次组遍历匹配组的单元格时，把匹配的单元格索引位置添加到其中
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
			
			//读取数据并写入单元格的值中，其中对于需要空单元格合并的则设置对应索引的单元格为null
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
			
			//如果有设置分组，则开始处理分组情况
			if (groupNodeLength > 0) {
				/*
				 * 循环检查每个组，比较组Key与以前组Key是否相同
				 * 如果在某个组相同，则属于该组的所有列要进行合并
				 * 如果在所有组中都不同，则不同的列作为新的组起始列
				 * */	
				
				//清除各个单元格合并组标识为0
				for (index = 0; index < len; index++)
					mergeGroupFlags[index] = 0;
				
				//循环检查每个组信息，并设置各单元格合并组标识值
				for (int pos = 0; pos < groupNodeLength; pos++) {
					GroupNode groupNode = groupNodes[pos];
					String match = groupNode.match;
					
					//查找当前分组的组Key值，并把属于当前分组的单元格索引记录下来
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
					
					//如果当前行的当前组Key与以前的组Key相同，则需要与以前组对应单元格合并，设置单元格合并组标识值为1
					if (groupNode.preGroupKey != null && groupKey.equals(groupNode.preGroupKey)) {
						for (int i = matchCellIndexLength - 1; i >= 0; i--)
							mergeGroupFlags[matchCellIndexs[i]] = 1;
						//如果当前行的当前组Key与以前的组Key相同，则不需要与以前组对应单元格合并，设置单元格合并组标识值为-1
					} else {
						groupNode.preGroupKey = groupKey;
	
						//只有对于未设置1的单元格的合并标识才会设置为-1
						for (int i = matchCellIndexLength - 1; i >= 0; i--)
							if (mergeGroupFlags[matchCellIndexs[i]] == 0)
								mergeGroupFlags[matchCellIndexs[i]] = -1;
					}
				}
				
				//循环对每个单元格
				for (index = 0; index < len; index++) {
					//如果当前单元格需要合并组，则删除当前单元格，并让组第一个单元格rowSpan加上当前单元格的rowSpan，height加上当前单元格的height
					if (mergeGroupFlags[index] == 1) {
						groupFirstCells[index].setRowSpan(groupFirstCells[index].getRowSpan() + currRowCells[index].getRowSpan());
						groupFirstCells[index].setHeight(groupFirstCells[index].getHeight() + currRowCells[index].getHeight());
						
						currRowCells[index] = null;
					//如果当前单元格不需要合并组，则让当前单元格成为组第一个单元格
					} else if (mergeGroupFlags[index] == -1) {
						groupFirstCells[index] = currRowCells[index];
					}
				}
			}
			
			//重新装载没有删除的单元格
			row.getData().clear();
			for (index = 0; index < len; index++)
				if (currRowCells[index] != null)
					row.getData().add(currRowCells[index]);
			
			rowList.add(row);
		}
		
		return rowList;
	}
	
	private void mergeBlankCell(Cell[] cells, int mergeCellIndex) {
		//如果合并单元格不是第一个单元格，则和前一个单元格合并，否则和后一个单元格合并
		int pos = -1;
		if (mergeCellIndex > 0) {
			pos = mergeCellIndex - 1;
			while (pos >= 0 && cells[pos] == null)
				pos--;
		}
		
		if (pos == -1)
			pos = mergeCellIndex + 1;
		
		//找到的单元格的colSpan加上要合并的单元格的colSpan值
		cells[pos].setColSpan(cells[pos].getColSpan() + cells[mergeCellIndex].getColSpan());
		
		//找到的单元格的width加上要合并的单元格的width值
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
			throw new RuntimeException("执行渲染脚本错误/r/n" + cell.getRenderer(), e);
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
