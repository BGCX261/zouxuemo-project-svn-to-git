/**
 * 
 */
package com.lily.dap.util.file;

/**
 * @author xuemozou
 * 
 *         �ļ����ƹ�����
 */
public interface CopyFilter {
	/**
	 * ���������������Ƶ�ǰ�ļ��Ƿ񿽱�
	 * 
	 * @param level
	 *            Ŀ¼������Ȳ㼶����Ŀ¼���ļ����Ϊ1������һ��Ŀ¼���ļ����Ϊ2��������������
	 * @param srcDirectory
	 *            ���Ƶ�ԴĿ¼
	 * @param tgtDirectory
	 *            ���Ƶ�Ŀ��Ŀ¼
	 * @param filename
	 *            ���Ƶ��ļ���
	 * @param isDirectory
	 *            Ҫ���Ƶ��ļ��Ƿ���Ŀ¼
	 * @return ����true��������������false����������
	 */
	public boolean filter(int level, String srcDirectory, String tgtDirectory,
			String filename, boolean isDirectory);
}
