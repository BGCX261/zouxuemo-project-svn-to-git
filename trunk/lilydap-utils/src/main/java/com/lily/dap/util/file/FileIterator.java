/**
 * FileIterator.java
 * Copyright (C), 2009 �ٺ����.
 * Author : ��ѧģ      
 * 2009-10-5 ����03:35:05
 */
package com.lily.dap.util.file;

/**
 * @ClassName: FileIterator
 * @author ��ѧģ
 * @Description : �ļ��������ӿڣ������ļ�������FileUtil��iterateFiles����ʱʹ��
 * 
 */
public interface FileIterator {
	// �������ƣ��ڵ��ñ������ӿڷ���ʱ������һ���ı�����Ϊ
	public static int ITERATE_OK = 1; // ��ǰ�ļ�·�����뷵���ļ�·���б�����������
	public static int ITERATE_IGNORE = 2; // ��ǰ�ļ�·�������뷵���ļ�·���б�����������
	public static int ITERATE_STOP = 0; // ��ǰ�ļ�·�������뷵���ļ�·���б���ֹͣ����

	/**
	 * �������ýӿڣ��ڱ����ļ�ʱ���ñ��ӿڷ����������ݷ���ֵ�����Ƿ�������±���
	 * 
	 * @param level
	 *            ��ǰ����Ŀ¼����Ȳ㼶����Ŀ¼���ļ����Ϊ1������һ��Ŀ¼���ļ����Ϊ2��������������
	 * @param dir
	 *            ���������ļ�����Ŀ¼
	 * @param file
	 *            ���������ļ���
	 * @param isDirectory
	 *            ���������ļ��Ƿ���Ŀ¼
	 * @return ����ֵ������������Ƴ���
	 */
	public int iterate(int level, String dir, String file, boolean isDirectory);
}
