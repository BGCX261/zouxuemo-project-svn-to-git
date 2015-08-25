/**
 * 
 */
package com.lily.dap.service.report2.util;

import java.math.BigDecimal;

/**
 * @author xuemozou
 *
 * 报表工具类
 */
public class ReportUtils {
	public static float scale(float val, int scale) {
		if (scale < 0)
			return val;
		
		BigDecimal b = new BigDecimal(val);
		b.setScale(scale, BigDecimal.ROUND_HALF_UP);
		return b.floatValue();
	}
	
	public static float scale(double val, int scale) {
		BigDecimal b = new BigDecimal(val);
		if (scale >= 0)
			b.setScale(scale, BigDecimal.ROUND_HALF_UP);
		
		return b.floatValue();
	}
	
	public static void main(String[] args) {
		float f = (float)5.0 / 3;
		System.out.println(new BigDecimal(f).setScale(0, BigDecimal.ROUND_HALF_UP).floatValue());
		System.out.println(new BigDecimal(f).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
	}

}
