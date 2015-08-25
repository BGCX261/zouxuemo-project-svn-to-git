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
 * ����ģ�幤����
 */
public class ReportConfigUtil {
	/**
	 * �����ָ��λ�ò�������
	 * 
	 * @param table
	 * @param pos
	 * @param row
	 */
	public static void insertRow(Table table, int pos, Row row) {
		if (pos < 0 || pos > table.getData().size())
			throw new RuntimeException("Ҫ��ӵ��г����з�Χ��");
		
		table.getData().add(pos, row);
	}
	
	/**
	 * �����ָ��λ�ò���������
	 * 
	 * @param table
	 * @param pos
	 * @param rows
	 */
	public static void insertRow(Table table, int pos, Row[] rows) {
		if (pos < 0 || pos > table.getData().size())
			throw new RuntimeException("Ҫ��ӵ��г����з�Χ��");
		
		for (int i = rows.length - 1; i >= 0; i--)
			table.getData().add(pos, rows[i]);
	}
	
	/**
	 * �����׷������
	 * 
	 * @param table
	 * @param row
	 */
	public static void appendRow(Table table, Row row) {
		table.getData().add(row);
	}
	
	/**
	 * �����׷�Ӷ������
	 * 
	 * @param table
	 * @param rows
	 */
	public static void appendRow(Table table, Row[] rows) {
		for (Row row : rows)
			table.getData().add(row);
	}
	
	/**
	 * ����еĸ����к���λ��ǰ�����µ�Ԫ��
	 * 
	 * @param table Ҫ����ı�
	 * @param row Ҫ������кţ���0��ʼ
	 * @param col Ҫ������кţ���0��ʼ��ע�⣺������к��Ǿ����кţ�������е�ǰ��ǰ������Ϊ�����п��ж�ռ��λ�ӣ���Щ���ж�ռ������Ҳ������������У�
	 * @param cell Ҫ����ĵ�Ԫ��
	 */
	public static void insertCell(Table table, int row, int col, Cell cell) {
		insertCell(table, row, col, new Cell[]{cell});
	}

	/**
	 * ����еĸ����к���λ��ǰ��������Ԫ��
	 * 
	 * @param table Ҫ����ı�
	 * @param row Ҫ������кţ���0��ʼ
	 * @param col Ҫ������кţ���0��ʼ��ע�⣺������к��Ǿ����кţ�������е�ǰ��ǰ������Ϊ�����п��ж�ռ��λ�ӣ���Щ���ж�ռ������Ҳ������������У�
	 * @param cells Ҫ����ĵ�Ԫ������
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
	 * ���ĸ����������׷���µ�Ԫ��
	 * 
	 * @param table Ҫ׷�ӵı�
	 * @param row Ҫ׷�ӵ��кţ���0��ʼ
	 * @param cells Ҫ׷�ӵĵ�Ԫ��
	 */
	public static void appendCell(Table table, int row, Cell cell) {
		appendCell(table, row, new Cell[]{cell});
	}

	/**
	 * ���ĸ����������׷�Ӷ����Ԫ��
	 * 
	 * @param table Ҫ׷�ӵı�
	 * @param row Ҫ׷�ӵ��кţ���0��ʼ
	 * @param cells Ҫ׷�ӵĵ�Ԫ������
	 */
	public static void appendCell(Table table, int row, Cell[] cells) {
		if (cells == null || cells.length == 0)
			return;
		
		Row rowData = table.getRow(row);
		
		if (rowData == null)
			throw new RuntimeException("Ҫ��ӵĵ�Ԫ�񳬳��з�Χ��");
		
		List<Cell> list = rowData.getData();
		
		for (int i = 0, len = cells.length; i < len; i++)
			list.add(cells[i]);
	}
	/**
	 * ������ָ��λ�ò����µ�Ԫ��
	 * 
	 * @param row
	 * @param pos
	 * @param cell
	 */
	public static void insertCell(Row row, int pos, Cell cell) {
		if (pos < 0 || pos > row.getData().size())
			throw new RuntimeException("Ҫ��ӵĵ�Ԫ�񳬳���Χ��");
		
		row.getData().add(pos, cell);
	}
	
	/**
	 * ������ָ��λ�ò������µ�Ԫ��
	 * 
	 * @param row
	 * @param pos
	 * @param cells
	 */
	public static void insertCell(Row row, int pos, Cell[] cells) {
		if (pos < 0 || pos > row.getData().size())
			throw new RuntimeException("Ҫ��ӵĵ�Ԫ�񳬳���Χ��");
		
		for (int i = cells.length - 1; i >= 0; i--)
			row.getData().add(pos, cells[i]);
	}
	
	/**
	 * ���������׷���µ�Ԫ��
	 * 
	 * @param row
	 * @param cell
	 */
	public static void appendCell(Row row, Cell cell) {
		row.getData().add(cell);
	}
	
	/**
	 * ���������׷�Ӷ���µ�Ԫ��
	 * 
	 * @param row
	 * @param cells
	 */
	public static void appendCell(Row row, Cell[] cells) {
		for (Cell cell : cells)
			row.getData().add(cell);
	}
	
	/**
	 * �����п������в����µ��п�
	 * 
	 * @param table Ҫ����ı�
	 * @param pos Ҫ�����λ�ã���0��ʼ
	 * @param width Ҫ������п�
	 */
	public static void insertWidth(Table table, int pos, int width) {
		insertWidth(table, pos, width, 1);
	}
	
	/**
	 * �����п������в������µ��п�
	 * 
	 * @param table Ҫ����ı�
	 * @param pos Ҫ�����λ�ã���0��ʼ
	 * @param widths Ҫ������п�����
	 */
	public static void insertWidth(Table table, int pos, int[] widths) {
		if (widths == null || widths.length == 0)
			return;
		
		List<Integer> list = table.getWidths();
		if (pos < 0 || pos > list.size())
			throw new RuntimeException("Ҫ��ӵĿ�ȳ�����Χ��");
		
		for (int i = widths.length - 1; i >= 0; i--)
			list.add(pos, widths[i]);
	}
	
	/**
	 * �����п������в���ͬ����ȵĶ���п�
	 * 
	 * @param table Ҫ����ı�
	 * @param pos Ҫ�����λ�ã���0��ʼ
	 * @param width Ҫ������п�
	 * @param count Ҫ�����п������
	 */
	public static void insertWidth(Table table, int pos, int width, int count) {
		if (count <= 0)
			return;
		
		List<Integer> list = table.getWidths();
		if (pos < 0 || pos > list.size())
			throw new RuntimeException("Ҫ��ӵĿ�ȳ�����Χ��");
		
		while (count-- > 0)
			list.add(pos, width);
	}
	
	/**
	 * �������и��е�Ԫ���colSpan���ԣ�������еĵ�Ԫ��������Ϊ������µ�Ԫ������µı��и��е�Ԫ��������һ������
	 * <p>�����������������У�ͨ�������һ�У��Ӵ�colSpanֵʵ���������������
	 * 
	 * @param table Ҫ�����ı�
	 */
	public static void adjustTable(Table table) {
		adjustTable(table, 0, null);
	}
	
	/**
	 * �������и��е�Ԫ���colSpan���ԣ�������еĵ�Ԫ��������Ϊ������µ�Ԫ������µı��и��е�Ԫ��������һ������
	 * <p>�����������������У�ͨ����������colgroup���С����߱������һ�У��Ӵ�colSpanֵʵ���������������
	 * 
	 * @param table Ҫ�����ı�
	 * @param addCols ����������colgroup���У�ָ����Ӧcolgroup������Ҫ��չ��colSpanֵ�����û������colgroup�У�����������Ϊnull
	 */
	public static void adjustTable(Table table, Map<String, Integer> addCols) {
		adjustTable(table, 0, addCols);
	}
	
	/**
	 * �������и��е�Ԫ���colSpan���ԣ�������еĵ�Ԫ��������Ϊ������µ�Ԫ������µı��и��е�Ԫ��������һ������
	 * <p>�����������������У�ͨ����������colgroup���С����߱������һ�У��Ӵ�colSpanֵʵ���������������
	 * 
	 * @param table Ҫ�����ı�
	 * @param newColCount ����������������������Ϊ0����ϵͳ�Զ��������������Ϊ��������Ч�ʱȽϵͣ�
	 * @param addCols ����������colgroup���У�ָ����Ӧcolgroup������Ҫ��չ��colSpanֵ�����û������colgroup�У�����������Ϊnull
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
			
			//��鵱ǰ�����һ����Ԫ���ұ��Ƿ�����Ϊǰ����������rowSpan��չ��ռ�õĵ�Ԫ������У���Ҫ����������ɾ����Щռ�õĵ�Ԫ��
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
	 * ��������п��ֵ
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
	 * ���㲢���ر��и����к��еľ���λ���ڸ��е�Row�б��е����λ��
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
			throw new RuntimeException("Ҫ������λ��[" + row + "," + col + "]�Ѿ���ռ�ã������������");
		
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
