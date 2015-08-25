package com.lily.dap.util;

import java.math.BigDecimal;

/**
 * 
 * ����Java�ļ����Ͳ��ܹ���ȷ�ĶԸ������������㣬����������ṩ��
 * ȷ�ĸ��������㣬�����Ӽ��˳�����ȱȽϺ��������롣
 */
public class ArithUtils {
	// Ĭ�ϳ������㾫��
	private static final int DEF_DIV_SCALE = 4;

	// ����಻��ʵ����
	private ArithUtils() {

	}

	/**
	 * 
	 * �ṩ��ȷ�ļӷ����㡣
	 * 
	 * @param v1 ������
	 * @param v2 ����
	 * 
	 * @return ���������ĺ�
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.add(b2).doubleValue();
	}

	/**
	 * 
	 * �ṩ��ȷ�ļ������㡣
	 * 
	 * @param v1 ������
	 * @param v2 ����
	 * 
	 * @return ���������Ĳ�
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 
	 * �ṩ��ȷ�ĳ˷����㡣
	 * 
	 * @param v1 ������
	 * @param v2 ����
	 * 
	 * @return ���������Ļ�
	 */

	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 
	 * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ����ȷ��С�����Ժ�4λ���Ժ�������������롣
	 * 
	 * @param v1 ������
	 * @param v2 ����
	 * 
	 * @return ������������
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 
	 * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ����scale����ָ�����ȣ��Ժ�������������롣
	 * 
	 * @param v1 ������
	 * @param v2 ����
	 * @param scale ��ʾ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
	 * 
	 * @return ������������
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	/**
	 * �ж�����˫�����͵���ֵ�Ƿ����
	 * 
	 * @param value1	��ֵ1
	 * @param value2	��ֵ2
	 * @param precision		���ȣ�����λС����������������
	 * @return �����ȷ���true,���򷵻�false;
	 */
	public static boolean eq(double value1, double value2, int scale){
		if(scale < 0) throw new IllegalArgumentException("���Ȳ�����С����!");
		
		double tmpDoubleValue1 = value1 * Math.pow(10, scale);
		long tmpLongValue1 = Math.round(tmpDoubleValue1);
		
		double tmpDoubleValue2 = value2 * Math.pow(10, scale);
		long tmpLongValue2 = Math.round(tmpDoubleValue2);
		
		return tmpLongValue1 == tmpLongValue2 ? true:false;
	}
	

	/**
	 * 
	 * �ṩ��ȷ��С��λ�������봦��
	 * 
	 * @param v ��Ҫ�������������
	 * @param scale С���������λ
	 * 
	 * @return ���������Ľ��
	 */
	public static double round(double v, int scale) {
		if(scale < 0) throw new IllegalArgumentException("���Ȳ�����С����!");
		
		double  tmpValue = Math.pow(10, scale);
		v = Math.round(v * tmpValue) / tmpValue;
		return v;
	}
	
	public static void main(String[] args) {
		System.out.println(ArithUtils.div(10, 3));		//3.3333
		System.out.println(ArithUtils.div(10, 3, 0));	//3.0
		
		double v1 = 123.123450;
		double v2 = 123.123455;
		System.out.println(ArithUtils.eq(v1, v2, 5));
		
		System.out.println(ArithUtils.round((418.670167 - 296.72) / 296.72 * 100, 2));
		
		System.out.println(ArithUtils.round(123.4456789d, 1));
		System.out.println(ArithUtils.round(123.4456789d, 2));
		System.out.println(ArithUtils.round(123.4456789d, 3));
		System.out.println(ArithUtils.round(123.4456789d, 4));
		System.out.println(ArithUtils.round(123.4456789d, 5));
		
		long b;
		
		b = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++)
			ArithUtils.round(123.4456789d, 3);
		System.out.println(System.currentTimeMillis() - b);
	}
}
