/**
 * 
 */
package com.lily.dap.util.file;

/**
 * @author xuemozou
 * 
 *         �ļ�ɾ��������
 */
public interface RemoveFilter {
	/**
	 * �Ƿ������ļ�ɾ��,true������ɾ����false��������ɾ��
	 * 
	 * @param level
	 *            Ŀ¼��Ȳ㼶����Ŀ¼���Ϊ0����Ŀ¼���ļ����Ϊ1������һ��Ŀ¼���ļ����Ϊ2��������������
	 * @param directory
	 *            ɾ����ԴĿ¼
	 * @param filename
	 *            ɾ�����ļ���
	 * @param isDirectory
	 *            Ҫɾ�����ļ��Ƿ���Ŀ¼
	 * @return
	 */
	public boolean filter(int level, String directory, String filename,
			boolean isDirectory);
}
