package com.lily.dap.util;

import java.util.HashSet;
import java.util.Set;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtils {
	// ����ƴ����ʽ�����
	private static HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
	static {
		// ������ã���Сд�����귽ʽ��
		hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE); // Сд
		hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE); // ������
		hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V); // "v"
	}
	
	public static Set<String> getPinyin(String src) {
		if (src != null && !src.trim().equalsIgnoreCase("")) {
			char[] srcChar = src.toCharArray();
			
			String[][] temp = new String[src.length()][];
			for (int i = 0; i < srcChar.length; i++) {
				char c = srcChar[i];
				// �����Ļ���a-z����A-Zת��ƴ��(�ҵ������Ǳ������Ļ���a-z����A-Z)
				if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) { // �����ַ�
					try {
						temp[i] = PinyinHelper.toHanyuPinyinStringArray(
								srcChar[i], hanYuPinOutputFormat);
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
					}
				} else if (((int) c >= 65 && (int) c <= 90)
						|| ((int) c >= 97 && (int) c <= 122)) { // Ӣ����ĸ
					temp[i] = new String[] { String.valueOf(srcChar[i]) };
				} else { // �����ַ�
					temp[i] = new String[] { "" };
				}
			}
			
			String[] pinyinArray = ExChange(temp);
			Set<String> pinyinSet = new HashSet<String>();
			for (int i = 0; i < pinyinArray.length; i++) 
				pinyinSet.add(pinyinArray[i]);
			
			return pinyinSet;
		}
		
		return null;
	}

	/**
	 * �ַ�������ת���ַ��������ŷָ���
	 * 
	 * @param stringSet
	 * @return
	 */
	public static String makeStringByStringSet(Set<String> stringSet,
			String separator) {
		StringBuilder str = new StringBuilder();
		int i = 0;
		for (String s : stringSet) {
			if (i == stringSet.size() - 1) {
				str.append(s);
			} else {
				str.append(s + separator);
			}
			i++;
		}
		return str.toString().toLowerCase();
	}

	private static String[] ExChange(String[][] strJaggedArray) {
		String[][] temp = DoExchange(strJaggedArray);
		return temp[0];
	}

	private static String[][] DoExchange(String[][] strJaggedArray) {
		int len = strJaggedArray.length;
		if (len >= 2) {
			int len1 = strJaggedArray[0].length;
			int len2 = strJaggedArray[1].length;
			int newlen = len1 * len2;
			String[] temp = new String[newlen];
			int index = 0;
			for (int i = 0; i < len1; i++) {
				for (int j = 0; j < len2; j++) {
					temp[index] = strJaggedArray[0][i] + strJaggedArray[1][j];
					index++;
				}
			}
			String[][] newArray = new String[len - 1][];
			for (int i = 2; i < len; i++) {
				newArray[i - 1] = strJaggedArray[i];
			}
			newArray[0] = temp;
			return DoExchange(newArray);
		} else {
			return strJaggedArray;
		}
	}

	/**
	 * ֻת������Ϊƴ���������ַ�����
	 * 
	 * @param src
	 * @return
	 */
	public static String getPinyinWithMark(String src, boolean addSpaceInPinyin) {
		if (src != null && !src.trim().equalsIgnoreCase("")) {
			char[] srcChar = src.toCharArray();
			
			StringBuffer output = new StringBuffer();
			for (int i = 0; i < srcChar.length; i++) {
				char c = srcChar[i];
				// ������ת��ƴ��(�ҵ������Ǳ�������)
				if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) { // �����ַ�
					try {
						String[] temp = PinyinHelper.toHanyuPinyinStringArray(srcChar[i], hanYuPinOutputFormat);
						output.append(temp[0]);
						
						if (addSpaceInPinyin)
							output.append(" ");
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
					}
				} else { // �����ַ�
					output.append(String.valueOf(srcChar[i]));
				}
			}
			return output.toString();
		}
		
		return "";
	}
	
	/**
	 * ���غ���ƴ������ĸ��ֻת������Ϊƴ���������ַ�����
	 * 
	 * @param src
	 * @return
	 */
	public static String getPinyinFirstLetter(String src) {
		if (src != null && !src.trim().equalsIgnoreCase("")) {
			char[] srcChar = src.toCharArray();
			
			StringBuffer output = new StringBuffer();
			for (int i = 0; i < srcChar.length; i++) {
				char c = srcChar[i];
				// ������ת��ƴ��(�ҵ������Ǳ�������)
				if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) { // �����ַ�
					try {
						String[] temp = PinyinHelper.toHanyuPinyinStringArray(srcChar[i], hanYuPinOutputFormat);
						output.append(temp[0].charAt(0));
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						e.printStackTrace();
					}
				}
			}
			
			return output.toString();
		}
		
		return "";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getPinyinWithMark("�����й���! I'm Chinese!", true));
		
		System.out.println(getPinyinWithMark("�˲� �μ� ����", false));
		
		System.out.println(getPinyinWithMark("��ѧģ", false));
		System.out.println(getPinyinFirstLetter("��ѧģ"));
	}
}