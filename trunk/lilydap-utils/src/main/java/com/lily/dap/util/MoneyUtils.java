package com.lily.dap.util;

public class MoneyUtils {
	private static String[] upChinese = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", };
	private static String[] upChinese2 = { "分", "角", "圆", "拾", "佰", "仟", "萬", "拾", "佰", "仟", "亿", "拾", "佰", "仟", "兆" };
	/**
	 * 将输入金额num转换为汉字大写格式
	 * 
	 * @param num
	 *            输入金额（小于10000000）
	 * @return 金额的大写格式
	 */
	public static String translate(double num) {
		StringBuffer result = new StringBuffer();
		int count = 0;
		int zeroflag = 0;
		boolean mantissa = false;
		
		if (num < 0) // 输入值小于零
			throw new IllegalArgumentException("输入金额不能为负数！");
		
		if (num == 0)  // 输入值等于零
			return "零";
		
		long tem = (long) (num * 100);
		if (tem % 100 == 0) { // 金额为整时
			if (tem == 0)
				return "零"; // 输入额为e:0.0012小于分计量单位时
			
			tem = tem / 100;
			count = 2;
			mantissa = true;
		}
		
		while (tem > 0) {
			int t = (int)(tem % 10); // 取得最后一位
			if (t != 0) { // 最后一位不为零时
				if (zeroflag >= 1) { // 对该位前的单个或多个零位进行处理
					if (((!mantissa) && count == 1)) { // 不是整数金额且分为为零

					} else if (count > 2 && count - zeroflag < 2) { // 输入金额为400.04小数点前后都有零

						result.insert(1, "零");

					} else if (count > 6 && count - zeroflag < 6 && count < 10) { // 万位后为零且万位为零
						if (count - zeroflag == 2) { // 输入值如400000
							result.insert(0, "萬");
						} else {
							result.insert(0, "萬零"); // 输入值如400101
						}
					} else if (count > 10 && count - zeroflag < 10) {
						if (count - zeroflag == 2) {
							result.insert(0, "亿");
						} else {
							result.insert(0, "亿零");
						}

					} else if (((count - zeroflag) == 2)) { // 个位为零

					} else if (count > 6 && count - zeroflag == 6 && count < 10) { // 以万位开始出现零如4001000
						result.insert(0, "萬");
					} else if (count == 11 && zeroflag == 1) {
						result.insert(0, "亿");
					} else {
						result.insert(0, "零");
					}

				}
				
				result.insert(0, upChinese[t] + upChinese2[count]);
				zeroflag = 0;
			} else {
				if (count == 2)
					result.insert(0, upChinese2[count]); // 个位为零补上"圆"字
				
				zeroflag++;
			}
			
			tem /= 10;
			count++;

			if (count > 20)
				break;
		}
		
		return result.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(MoneyUtils.translate(0));
		
		System.out.println(MoneyUtils.translate(0.0012));
		
		try {
			System.out.println(MoneyUtils.translate(-1));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println(MoneyUtils.translate(1.23));
		
		System.out.println(MoneyUtils.translate(1.23456));
		
		System.out.println(MoneyUtils.translate(1));
		
		System.out.println(MoneyUtils.translate(12));
		
		System.out.println(MoneyUtils.translate(123));
		
		System.out.println(MoneyUtils.translate(1234));
		
		System.out.println(MoneyUtils.translate(12345));
		
		System.out.println(MoneyUtils.translate(123456));
		
		System.out.println(MoneyUtils.translate(1234567));
		
		System.out.println(MoneyUtils.translate(12345678));
		
		System.out.println(MoneyUtils.translate(123456789));
		
		System.out.println(MoneyUtils.translate(1234567890));
		
		System.out.println(MoneyUtils.translate(1234567890123.456d));
	}
}
