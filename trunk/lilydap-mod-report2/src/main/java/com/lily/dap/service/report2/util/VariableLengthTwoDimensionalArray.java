/**
 * 
 */
package com.lily.dap.service.report2.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuemozou
 *
 * ��һά�������ȵĶ�ά����
 */
public class VariableLengthTwoDimensionalArray <T> {
	private static final int DEFAULT_SEGMENT_LENGTH = 100;
		
	/**
	 * Ƭ�εĳ��ȣ�����ά�����е�һά���зֶεĶγ���ֵ
	 */
	private int segmentLength = DEFAULT_SEGMENT_LENGTH;
	
	/**
	 * ���ÿ���̶�һά����ֵ�Ķ�ά����Ƭ�μ���
	 */
	private List<Object[][]> segmentList = new ArrayList<Object[][]>();
	
	/**
	 * ��ǰ���õ����һάֵ
	 */
	private int validFirstDimensionalLength = 0;
	
	/**
	 * ��ǰͨ��set�������õ����һάֵ
	 */
	private int maxFirstDimensionalLength = 0;
	
	private T initValue;
	
	/**
	 * ��ά�����еĶ�άֵ
	 */
	private int secondDimensionalLength = 0;
	
	public VariableLengthTwoDimensionalArray(int segmentLength,
			int secondDimensionalLength, T initValue) {
		this.segmentLength = segmentLength;
		this.secondDimensionalLength = secondDimensionalLength;
		this.initValue = initValue;
	}

	public VariableLengthTwoDimensionalArray(int secondDimensionalLength, T initValue) {
		this.secondDimensionalLength = secondDimensionalLength;
		this.initValue = initValue;
	}
	
	public void extendArray(int newFirstDimensionalLength) {
		if (newFirstDimensionalLength > validFirstDimensionalLength)
			extendFirstDimensionalLength(newFirstDimensionalLength);
		
		if (newFirstDimensionalLength > maxFirstDimensionalLength)
			maxFirstDimensionalLength = newFirstDimensionalLength;
	}

	/**
	 * ����ָ��λ�õ�����ֵ
	 * 
	 * @param firstDimensionalPos
	 * @param secondDimensionalPos
	 * @param value
	 */
	public void set(int firstDimensionalPos, int secondDimensionalPos, T value) {
		if (secondDimensionalPos >= secondDimensionalLength)
			throw new ArrayIndexOutOfBoundsException("�±�Խ�磬����λ��[" + firstDimensionalPos + "," + secondDimensionalPos + "]�������÷�Χ[" + maxFirstDimensionalLength + "," + secondDimensionalLength + "]��");
		
		extendArray(firstDimensionalPos + 1);
		
		int segmentIndex = firstDimensionalPos / segmentLength;
		int firstDimensionalIndex = firstDimensionalPos % segmentLength;
		
		Object[][] segment = segmentList.get(segmentIndex);
		segment[firstDimensionalIndex][secondDimensionalPos] = value;
	}
	
	/**
	 * ��ȡָ��λ�õ�����ֵ
	 * 
	 * @param firstDimensionalPos
	 * @param secondDimensionalPos
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T get(int firstDimensionalPos, int secondDimensionalPos) {
		if (firstDimensionalPos >= maxFirstDimensionalLength || secondDimensionalPos >= secondDimensionalLength)
			throw new ArrayIndexOutOfBoundsException("�±�Խ�磬����λ��[" + firstDimensionalPos + "," + secondDimensionalPos + "]�������÷�Χ[" + maxFirstDimensionalLength + "," + secondDimensionalLength + "]��");
		
		int segmentIndex = firstDimensionalPos / segmentLength;
		int firstDimensionalIndex = firstDimensionalPos % segmentLength;
		
		Object[][] segment = segmentList.get(segmentIndex);
		return (T)segment[firstDimensionalIndex][secondDimensionalPos];
	}
	
	/**
	 * ���ص�һά�ĳ���ֵ
	 * 
	 * @return
	 */
	public int len1() {
		return maxFirstDimensionalLength;
	}
	
	/**
	 * ���صڶ�ά�ĳ���ֵ
	 * 
	 * @return
	 */
	public int len2() {
		return secondDimensionalLength;
	}
	
	/**
	 * ������ݣ���λΪ0ά������
	 */
	public void reset() {
		segmentList.clear();
		
		maxFirstDimensionalLength = 0;
		validFirstDimensionalLength = 0;
	}
	
	private void extendFirstDimensionalLength(int newDimensionalLength) {
		int newSegmentCount = (newDimensionalLength - validFirstDimensionalLength) / segmentLength + 1;
		
		for (int ptr = 0; ptr < newSegmentCount; ptr++) {
			Object[][] segment = new Object[segmentLength][secondDimensionalLength];
			for (int i = 0; i < segmentLength; i++)
				for (int j = 0; j < secondDimensionalLength; j++)
					segment[i][j] = initValue;
				
			segmentList.add(segment);
		}
		
		validFirstDimensionalLength += newSegmentCount * segmentLength;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		byte[] src = new byte[2];
		src[0] = 1;
		src[1] = 2;
		
		byte[] tgt = src.clone();
		tgt[0] = 3;
		tgt[1] = 4;
		
		VariableLengthTwoDimensionalArray<Integer> array = new VariableLengthTwoDimensionalArray<Integer>(3, 0);
		
		array.set(101, 1, 100);
		array.set(201, 1, 200);
		array.set(301, 1, 300);
		array.set(1000, 1, 1000);
		array.set(1001, 1, 1000);
		array.extendArray(2000);
		System.out.println(array.get(101, 1));
		System.out.println(array.get(201, 1));
		System.out.println(array.get(301, 1));
		System.out.println(array.get(1001, 1));
		System.out.println(array.get(1002, 1));
		System.out.println(array.get(1999, 1));
		System.out.println(array.get(2000, 1));
	}
}
