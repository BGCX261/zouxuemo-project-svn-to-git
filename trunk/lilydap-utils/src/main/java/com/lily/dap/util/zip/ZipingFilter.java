/**
 * 
 */
package com.lily.dap.util.zip;

/**
 * @author xuemozou
 * 
 *         ѹ���ͽ�ѹ���������������ļ��Ƿ���Ҫѹ��/��ѹ��
 */
public interface ZipingFilter {
	/**
	 * �Ƿ������ļ�ѹ��/��ѹ��,true������ѹ��/��ѹ����false��������ѹ��/��ѹ��
	 * 
	 * @param pathname
	 *            �ļ���ѹ���ļ������·����
	 * @param isDirectory
	 *            �ļ��Ƿ�ΪĿ¼
	 * @return
	 */
	public boolean filter(String pathname, boolean isDirectory);
}
