package com.lily.dap.util;

import java.math.BigDecimal;

/**
 * 
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精
 * 确的浮点数运算，包括加减乘除、相等比较和四舍五入。
 */
public class ArithUtils {
	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 4;

	// 这个类不能实例化
	private ArithUtils() {

	}

	/**
	 * 
	 * 提供精确的加法运算。
	 * 
	 * @param v1 被加数
	 * @param v2 加数
	 * 
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.add(b2).doubleValue();
	}

	/**
	 * 
	 * 提供精确的减法运算。
	 * 
	 * @param v1 被减数
	 * @param v2 减数
	 * 
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 
	 * 提供精确的乘法运算。
	 * 
	 * @param v1 被乘数
	 * @param v2 乘数
	 * 
	 * @return 两个参数的积
	 */

	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到小数点以后4位，以后的数字四舍五入。
	 * 
	 * @param v1 被除数
	 * @param v2 除数
	 * 
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
	 * 
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * 
	 * @return 两个参数的商
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
	 * 判断两个双精度型的数值是否相等
	 * 
	 * @param value1	数值1
	 * @param value2	数值2
	 * @param precision		精度，即几位小数；进行四舍五入
	 * @return 如果相等返回true,否则返回false;
	 */
	public static boolean eq(double value1, double value2, int scale){
		if(scale < 0) throw new IllegalArgumentException("精度不允许小于零!");
		
		double tmpDoubleValue1 = value1 * Math.pow(10, scale);
		long tmpLongValue1 = Math.round(tmpDoubleValue1);
		
		double tmpDoubleValue2 = value2 * Math.pow(10, scale);
		long tmpLongValue2 = Math.round(tmpDoubleValue2);
		
		return tmpLongValue1 == tmpLongValue2 ? true:false;
	}
	

	/**
	 * 
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * 
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if(scale < 0) throw new IllegalArgumentException("精度不允许小于零!");
		
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
