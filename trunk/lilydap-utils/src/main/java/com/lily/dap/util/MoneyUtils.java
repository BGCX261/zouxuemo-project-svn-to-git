package com.lily.dap.util;

public class MoneyUtils {
	private static String[] upChinese = { "��", "Ҽ", "��", "��", "��", "��", "½", "��", "��", "��", };
	private static String[] upChinese2 = { "��", "��", "Բ", "ʰ", "��", "Ǫ", "�f", "ʰ", "��", "Ǫ", "��", "ʰ", "��", "Ǫ", "��" };
	/**
	 * ��������numת��Ϊ���ִ�д��ʽ
	 * 
	 * @param num
	 *            �����С��10000000��
	 * @return ���Ĵ�д��ʽ
	 */
	public static String translate(double num) {
		StringBuffer result = new StringBuffer();
		int count = 0;
		int zeroflag = 0;
		boolean mantissa = false;
		
		if (num < 0) // ����ֵС����
			throw new IllegalArgumentException("�������Ϊ������");
		
		if (num == 0)  // ����ֵ������
			return "��";
		
		long tem = (long) (num * 100);
		if (tem % 100 == 0) { // ���Ϊ��ʱ
			if (tem == 0)
				return "��"; // �����Ϊe:0.0012С�ڷּ�����λʱ
			
			tem = tem / 100;
			count = 2;
			mantissa = true;
		}
		
		while (tem > 0) {
			int t = (int)(tem % 10); // ȡ�����һλ
			if (t != 0) { // ���һλ��Ϊ��ʱ
				if (zeroflag >= 1) { // �Ը�λǰ�ĵ���������λ���д���
					if (((!mantissa) && count == 1)) { // ������������ҷ�ΪΪ��

					} else if (count > 2 && count - zeroflag < 2) { // ������Ϊ400.04С����ǰ������

						result.insert(1, "��");

					} else if (count > 6 && count - zeroflag < 6 && count < 10) { // ��λ��Ϊ������λΪ��
						if (count - zeroflag == 2) { // ����ֵ��400000
							result.insert(0, "�f");
						} else {
							result.insert(0, "�f��"); // ����ֵ��400101
						}
					} else if (count > 10 && count - zeroflag < 10) {
						if (count - zeroflag == 2) {
							result.insert(0, "��");
						} else {
							result.insert(0, "����");
						}

					} else if (((count - zeroflag) == 2)) { // ��λΪ��

					} else if (count > 6 && count - zeroflag == 6 && count < 10) { // ����λ��ʼ��������4001000
						result.insert(0, "�f");
					} else if (count == 11 && zeroflag == 1) {
						result.insert(0, "��");
					} else {
						result.insert(0, "��");
					}

				}
				
				result.insert(0, upChinese[t] + upChinese2[count]);
				zeroflag = 0;
			} else {
				if (count == 2)
					result.insert(0, upChinese2[count]); // ��λΪ�㲹��"Բ"��
				
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
