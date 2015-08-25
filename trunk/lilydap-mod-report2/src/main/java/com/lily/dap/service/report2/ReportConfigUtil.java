/**
 * 
 */
package com.lily.dap.service.report2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lily.dap.service.report2.impl.model.Cell;
import com.lily.dap.service.report2.impl.model.Row;
import com.lily.dap.service.report2.impl.model.Table;

/**
 * @author xuemozou
 *
 * 报表模板工具类
 */
public class ReportConfigUtil {
	/**
	 * 向表中指定位置插入新行
	 * 
	 * @param table
	 * @param pos
	 * @param row
	 */
	public static void insertRow(Table table, int pos, Row row) {
		if (pos < 0 || pos > table.getData().size())
			throw new RuntimeException("要添加的行超出行范围！");
		
		table.getData().add(pos, row);
	}
	
	/**
	 * 向表中指定位置插入多个新行
	 * 
	 * @param table
	 * @param pos
	 * @param rows
	 */
	public static void insertRow(Table table, int pos, Row[] rows) {
		if (pos < 0 || pos > table.getData().size())
			throw new RuntimeException("要添加的行超出行范围！");
		
		for (int i = rows.length - 1; i >= 0; i--)
			table.getData().add(pos, rows[i]);
	}
	
	/**
	 * 向表中追加新行
	 * 
	 * @param table
	 * @param row
	 */
	public static void appendRow(Table table, Row row) {
		table.getData().add(row);
	}
	
	/**
	 * 向表中追加多个新行
	 * 
	 * @param table
	 * @param rows
	 */
	public static void appendRow(Table table, Row[] rows) {
		for (Row row : rows)
			table.getData().add(row);
	}
	
	/**
	 * 向表中的给定行和列位置前插入新单元格
	 * 
	 * @param table 要插入的表
	 * @param row 要插入的行号，从0开始
	 * @param col 要插入的列号，从0开始（注意：这里的列号是绝对列号，如果本行当前列前面有因为上面行跨行而占的位子，这些跨行而占的列数也会计算在列数中）
	 * @param cell 要插入的单元格
	 */
	public static void insertCell(Table table, int row, int col, Cell cell) {
		insertCell(table, row, col, new Cell[]{cell});
	}

	/**
	 * 向表中的给定行和列位置前插入多个单元格
	 * 
	 * @param table 要插入的表
	 * @param row 要插入的行号，从0开始
	 * @param col 要插入的列号，从0开始（注意：这里的列号是绝对列号，如果本行当前列前面有因为上面行跨行而占的位子，这些跨行而占的列数也会计算在列数中）
	 * @param cells 要插入的单元格数组
	 */
	public static void insertCell(Table table, int row, int col, Cell[] cells) {
		if (cells == null || cells.length == 0)
			return;
		
		int pos = calcColPos(table, row, col) - 1;
		
		Row rowData = table.getRow(row);
		List<Cell> list = rowData.getData();
		for (int i = cells.length - 1; i >= 0; i--)
			list.add(pos, cells[i]);
	}
	
	/**
	 * 向表的给定行最后面追加新单元格
	 * 
	 * @param table 要追加的表
	 * @param row 要追加的行号，从0开始
	 * @param cells 要追加的单元格
	 */
	public static void appendCell(Table table, int row, Cell cell) {
		appendCell(table, row, new Cell[]{cell});
	}

	/**
	 * 向表的给定行最后面追加多个单元格
	 * 
	 * @param table 要追加的表
	 * @param row 要追加的行号，从0开始
	 * @param cells 要追加的单元格数组
	 */
	public static void appendCell(Table table, int row, Cell[] cells) {
		if (cells == null || cells.length == 0)
			return;
		
		Row rowData = table.getRow(row);
		
		if (rowData == null)
			throw new RuntimeException("要添加的单元格超出行范围！");
		
		List<Cell> list = rowData.getData();
		
		for (int i = 0, len = cells.length; i < len; i++)
			list.add(cells[i]);
	}
	/**
	 * 向行中指定位置插入新单元格
	 * 
	 * @param row
	 * @param pos
	 * @param cell
	 */
	public static void insertCell(Row row, int pos, Cell cell) {
		if (pos < 0 || pos > row.getData().size())
			throw new RuntimeException("要添加的单元格超出范围！");
		
		row.getData().add(pos, cell);
	}
	
	/**
	 * 向行中指定位置插入多个新单元格
	 * 
	 * @param row
	 * @param pos
	 * @param cells
	 */
	public static void insertCell(Row row, int pos, Cell[] cells) {
		if (pos < 0 || pos > row.getData().size())
			throw new RuntimeException("要添加的单元格超出范围！");
		
		for (int i = cells.length - 1; i >= 0; i--)
			row.getData().add(pos, cells[i]);
	}
	
	/**
	 * 向给定行中追加新单元格
	 * 
	 * @param row
	 * @param cell
	 */
	public static void appendCell(Row row, Cell cell) {
		row.getData().add(cell);
	}
	
	/**
	 * 向给定行中追加多个新单元格
	 * 
	 * @param row
	 * @param cells
	 */
	public static void appendCell(Row row, Cell[] cells) {
		for (Cell cell : cells)
			row.getData().add(cell);
	}
	
	/**
	 * 向表的列宽数据中插入新的列宽
	 * 
	 * @param table 要插入的表
	 * @param pos 要插入的位置，从0开始
	 * @param width 要插入的列宽
	 */
	public static void insertWidth(Table table, int pos, int width) {
		insertWidth(table, pos, width, 1);
	}
	
	/**
	 * 向表的列宽数据中插入多个新的列宽
	 * 
	 * @param table 要插入的表
	 * @param pos 要插入的位置，从0开始
	 * @param widths 要插入的列宽数组
	 */
	public static void insertWidth(Table table, int pos, int[] widths) {
		if (widths == null || widths.length == 0)
			return;
		
		List<Integer> list = table.getWidths();
		if (pos < 0 || pos > list.size())
			throw new RuntimeException("要添加的宽度超出范围！");
		
		for (int i = widths.length - 1; i >= 0; i--)
			list.add(pos, widths[i]);
	}
	
	/**
	 * 向表的列宽数据中插入同样宽度的多个列宽
	 * 
	 * @param table 要插入的表
	 * @param pos 要插入的位置，从0开始
	 * @param width 要插入的列宽
	 * @param count 要插入列宽的数量
	 */
	public static void insertWidth(Table table, int pos, int width, int count) {
		if (count <= 0)
			return;
		
		List<Integer> list = table.getWidths();
		if (pos < 0 || pos > list.size())
			throw new RuntimeException("要添加的宽度超出范围！");
		
		while (count-- > 0)
			list.add(pos, width);
	}
	
	/**
	 * 调整表中各行单元格的colSpan属性，解决各行的单元格数量因为插入的新单元格而导致的表中各行单元格数量不一致问题
	 * <p>对于少于新列数的行，通过对最后一列，加大colSpan值实现所有行列数相等
	 * 
	 * @param table 要调整的表
	 */
	public static void adjustTable(Table table) {
		adjustTable(table, 0, null);
	}
	
	/**
	 * 调整表中各行单元格的colSpan属性，解决各行的单元格数量因为插入的新单元格而导致的表中各行单元格数量不一致问题
	 * <p>对于少于新列数的行，通过对设置了colgroup的列、或者本行最后一列，加大colSpan值实现所有行列数相等
	 * 
	 * @param table 要调整的表
	 * @param addCols 对于设置了colgroup的列，指定对应colgroup的列需要扩展的colSpan值，如果没有设置colgroup列，本参数可以为null
	 */
	public static void adjustTable(Table table, Map<String, Integer> addCols) {
		adjustTable(table, 0, addCols);
	}
	
	/**
	 * 调整表中各行单元格的colSpan属性，解决各行的单元格数量因为插入的新单元格而导致的表中各行单元格数量不一致问题
	 * <p>对于少于新列数的行，通过对设置了colgroup的列、或者本行最后一列，加大colSpan值实现所有行列数相等
	 * 
	 * @param table 要调整的表
	 * @param newColCount 调整后各行列数，如果设置为0，则系统自动计算最大列数作为新列数（效率比较低）
	 * @param addCols 对于设置了colgroup的列，指定对应colgroup的列需要扩展的colSpan值，如果没有设置colgroup列，本参数可以为null
	 */
	public static void adjustTable(Table table, int newColCount, Map<String, Integer> addCols) {
		int rowCount = table.rowCount();
		int colCount = newColCount;
		
		if (colCount <= 0)
			colCount = getMaxColCount(table);
		
		int[][] placeHold = new int[rowCount][colCount];
		
		Map<String, Cell> colGroupCellMap = new HashMap<String, Cell>();
		
		int currRow = 0, currCol;
		for (Row r : table.getData()) {
			colGroupCellMap.clear();
			
			currCol = 0;
			Cell lastCell = null;
			for (Cell c : r.getData()) {
				lastCell = c;
				
				if (c.getColgroup() != null)
					colGroupCellMap.put(c.getColgroup(), c);
				
				while(placeHold[currRow][currCol] != 0)
					currCol++;
				
				int rowSpan = c.getRowSpan();
				int colSpan = c.getColSpan();
				
				for (int i = 0; i < rowSpan; i++) {
					for (int j = 0; j < colSpan; j++) {
						placeHold[currRow + i][currCol + j] = 1;
					}
				}
				
				currCol += colSpan;
			}
			
			//检查当前行最后一个单元格右边是否有因为前面行设置了rowSpan扩展而占用的单元格，如果有，需要从总列数中删除这些占用的单元格
			int deductCol = 0;
			for (int col = currCol; col < colCount; col++)
				if (placeHold[currRow][col] != 0)
					deductCol++;
			
			if (currCol < colCount) {
				int addCount = colCount - currCol - deductCol;
				
				if (addCols != null) {
					for (String group1 : addCols.keySet()) {
						for (String group2 : colGroupCellMap.keySet()) {
							if (group2.indexOf(group1) >= 0) {
								int inc = addCols.get(group1);
								Cell cell = colGroupCellMap.get(group2);
								
								cell.setColSpan(cell.getColSpan() + inc);
								addCount -= inc;
								break;
							}
						}
					}
				}
				
				if (addCount > 0)
					lastCell.setColSpan(lastCell.getColSpan() + addCount);
			}
			
			currRow++;
		}
	}
	
	/**
	 * 返回最大列宽度值
	 * 
	 * @param table
	 * @return
	 */
	public static int getMaxColCount(Table table) {
		int rowCount = table.rowCount();
		int[][] placeHold = new int[rowCount][2000];
		
		int maxColCount = 0;
		int currRow = 0, currCol;
		for (Row r : table.getData()) {
			currCol = 0;
			for (Cell c : r.getData()) {
				while(placeHold[currRow][currCol] != 0)
					currCol++;
				
				int rowSpan = c.getRowSpan();
				int colSpan = c.getColSpan();
				
				for (int i = 0; i < rowSpan; i++) {
					for (int j = 0; j < colSpan; j++) {
						placeHold[currRow + i][currCol + j] = 1;
					}
				}
				
				currCol += colSpan;
			}
			
			if (currCol > maxColCount)
				maxColCount = currCol;
			
			currRow++;
		}
		
		return maxColCount;
	}
	
	/**
	 * 计算并返回表中给定行和列的绝对位置在该行的Row列表中的相对位置
	 * 
	 * @param table
	 * @param row
	 * @param col
	 * @return
	 */
	private static int calcColPos(Table table, int row, int col) {
		int[][] placeHold = new int[row+1][col+1];
		
		int currRow = 0, currCol;
		for (Row r : table.getData()) {
			currCol = 0;
			
			for (Cell c : r.getData()) {
				while(currCol <= col && placeHold[currRow][currCol] != 0)
					currCol++;
				
				if (currCol > col)
					break;
				
				int rowSpan = c.getRowSpan();
				int colSpan = c.getColSpan();
				
				for (int i = 0; i < rowSpan; i++) {
					if (currRow + i > row)
						break;
				
					for (int j = 0; j < colSpan; j++) {
						if (currCol + j > col)
							break;
					
						placeHold[currRow + i][currCol + j] = -1;
					}
				}
				
				placeHold[currRow][currCol] = 1;
				
				currCol += colSpan;
				if (currCol > col)
					break;
			}
			
			currRow++;
			if (currRow > row)
				break;
		}
		
		if (placeHold[row][col] != 1)
			throw new RuntimeException("要操作的位置[" + row + "," + col + "]已经被占用，不允许操作！");
		
		int pos = 0;
		for (int i = 0; i <= col; i++)
			if (placeHold[row][i] == 1)
				pos++;
		
		return pos;
	}
	
	public static void main(String[] args) {
		Table table = new Table();
		
		Row row = new Row();
		Cell cell = new Cell(2, 2, null, null, "0.0-1.1");
		row.addData(cell);
		cell = new Cell(1, 1, null, null, "0.2");
		row.addData(cell);
		cell = new Cell(2, 1, null, null, "0.3-1.3");
		row.addData(cell);
		cell = new Cell(1, 1, null, null, "0.4");
		row.addData(cell);
		table.addData(row);
		
		row = new Row();
		cell = new Cell(1, 1, null, null, "1.2");
		row.addData(cell);
		cell = new Cell(1, 1, "g1", null, "1.4");
		row.addData(cell);
		table.addData(row);
		
		row = new Row();
		cell = new Cell(1, 3, "g1 g2", null, "2.0-2.2");
		row.addData(cell);
		cell = new Cell(1, 2, null, null, "2.3-2.4");
		row.addData(cell);
		table.addData(row);
		
		row = new Row();
		cell = new Cell(1, 1, null, null, "3.0");
		row.addData(cell);
		cell = new Cell(1, 1, null, null, "3.1");
		row.addData(cell);
		cell = new Cell(1, 1, null, null, "3.2");
		row.addData(cell);
		cell = new Cell(1, 2, null, null, "3.3-3.4");
		row.addData(cell);
		table.addData(row);

		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 6; j++)
				try {
					System.out.println("cell[" + i + "," + j + "]'s pos is:" + ReportConfigUtil.calcColPos(table, i, j));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
		cell = new Cell(1, 1, null, null, "3.5");
		row.addData(cell);
		cell = new Cell(1, 1, null, null, "3.6");
		row.addData(cell);
		
		Map<String, Integer> addCols = new HashMap<String, Integer>();
//		addCols.put("g1", 1);
//		addCols.put("g2", 1);
		
		ReportConfigUtil.adjustTable(table, 7, addCols);
		for (Row r : table.getData()) {
			for (Cell c : r.getData()) {
				System.out.print(c.getRowSpan() + "." + c.getColSpan() + " ");
			}
			
			System.out.println();
		}
	}
}
