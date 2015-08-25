/**
 * 
 */
package com.lily.dap.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuemozou
 * 
 *         �ļ����������� ������ɾ��Ŀ¼������д�ļ���Ŀ¼�俽���ļ�������Ŀ¼���ļ����ж��ļ��Ƿ����
 */
public class FileUtil {
	private final static int MAX_BUFFER_LENGTH = 64 * 1024; // �ļ�����Ļ��������ֵ

	/**
	 * ���Ŀ¼���ļ����ַ���Ϊһ���ļ�ȫ·�����ַ��� �磺 combinDirectoryAndFile("D:\JAVA_HOME\tomcat
	 * 6.0.14\webapps\energyAnalysisReport", "scripts") ���� D:\JAVA_HOME\tomcat
	 * 6.0.14\webapps\energyAnalysisReport\scripts
	 * 
	 * @param dir1
	 *            ��Ŀ¼�ַ���
	 * @param dir2
	 *            ��Ŀ¼�ַ���
	 * @return ������ϵ��ļ�ȫ·���ַ���
	 */
	public static String combinDirectoryAndFile(String dir, String filename) {
		if (dir == null || "".equals(dir))
			return filename;
		else if (filename == null || "".equals(filename))
			return dir;

		String d = dir.replace('\\', '/');
		if (d.charAt(d.length() - 1) != '/')
			d += '/';

		String f = filename.replace('\\', '/');
		if (f.charAt(0) == '/')
			f = f.substring(1);

		return d + f;
	}

	/**
	 * �жϸ���Ŀ¼�µĸ����ļ��Ƿ����
	 * 
	 * @param directory
	 *            Ҫ�жϵ��ļ�����Ŀ¼
	 * @param filename
	 *            Ҫ�ж��ļ���
	 * @return
	 */
	public static boolean isFileExists(String directory, String filename) {
		String filepath = combinDirectoryAndFile(directory, filename);

		return isFileExists(filepath);
	}

	/**
	 * �жϸ���ȫ·�������ļ��Ƿ����
	 * 
	 * @param filepath
	 *            Ҫ�ж��ļ���ȫ·����
	 * @return
	 */
	public static boolean isFileExists(String filepath) {
		File file = new File(filepath);

		return file.exists();
	}

	/**
	 * ��ԴĿ¼�����ļ���Ŀ��Ŀ¼�����Ŀ��Ŀ¼���ļ��Ѿ����ڣ��򸲸��Ѵ����ļ�
	 * ���Ƶ��ļ������·����ʽ�������Ŀ��Ŀ¼���Ƿ��ж�Ӧ��·�������û�У����Զ�����Ŀ¼
	 * 
	 * @param srcdir
	 *            Ҫ���Ƶ�ԴĿ¼�����磺D:\\casemanage ���� D:/casemanage
	 * @param tgtdir
	 *            Ҫ���Ƶ���Ŀ��Ŀ¼�����磺D:\\casemanage_copy ���� D:/casemanage_copy
	 * @param filename
	 *            Ҫ���Ƶ��ļ������������д�·����ʽ�����磺j2sdk1.4.2_09\\include\\win32\\jni_md.h
	 * @throws RuntimeException
	 *             ����IO���������磺Ŀ��Ŀ¼�ռ䲻�㣩ʱ�׳��쳣
	 */
	public static void copyFile(String srcDir, String tgtDir, String fileName) {
		InputStream is = null;
		try {
			String srcFilePath = combinDirectoryAndFile(srcDir, fileName);
			String tgtFilePath = combinDirectoryAndFile(tgtDir, fileName);

			is = new FileInputStream(srcFilePath);

			writeFile(is, tgtFilePath);
		} catch (IOException e) {
			throw new RuntimeException("��" + srcDir + "�����ļ�" + fileName + "��"
					+ tgtDir + "ʧ�� - " + e.getMessage());
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * ��ԴĿ¼�����ļ��б�Ŀ��Ŀ¼��ͨ������������Ҫ���Ƶ��ļ�
	 * 
	 * @param srcDir
	 *            Ҫ���Ƶ�ԴĿ¼�����磺D:\\casemanage ���� D:/casemanage
	 * @param tgtDir
	 *            Ҫ���Ƶ���Ŀ��Ŀ¼�����磺D:\\casemanage_copy ���� D:/casemanage_copy
	 * @param filter
	 *            ���Ƹ��Ƶ��ļ�������
	 * @return ���ؿ����ļ���Ŀ¼������
	 */
	public static int copyFiles(String srcDir, String tgtDir, CopyFilter filter) {
		String src_dir = srcDir.replace('\\', '/');
		if (src_dir.charAt(src_dir.length() - 1) != '/')
			src_dir += '/';

		String tgt_dir = tgtDir.replace('\\', '/');
		if (tgt_dir.charAt(tgt_dir.length() - 1) != '/')
			tgt_dir += '/';

		File dir = new File(src_dir);
		if (!dir.exists())
			return 0;
		else if (!dir.isDirectory())
			return 0;

		return copyFiles(1, src_dir, tgt_dir, filter);
	}

	/**
	 * ��������д��������ָ��Ŀ¼�µ��ļ���д��ǰ���Զ�����ļ�����Ŀ¼�Ƿ���ڣ����������Զ�����Ŀ¼
	 * 
	 * @param is
	 *            �ļ�������
	 * @param filepath
	 *            ������ļ�ȫ·����
	 */
	public static void writeFile(InputStream is, String filepath) {
		String file_path = filepath.replace('\\', '/');
		String directory = file_path.substring(0, file_path.lastIndexOf('/'));

		createDirectory(directory);

		OutputStream os = null;
		byte[] buffer = new byte[MAX_BUFFER_LENGTH];
		try {
			os = new FileOutputStream(file_path);
			int bytesRead = 0;

			while ((bytesRead = is.read(buffer, 0, MAX_BUFFER_LENGTH)) != -1) {
				os.write(buffer, 0, bytesRead);
				os.flush();
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(file_path + " �ļ�δ�ҵ�,�洢�ļ�ʧ��!");
		} catch (IOException e) {
			throw new RuntimeException("�洢�ļ�ʧ�� - " + e.getMessage());
		} finally {
			buffer = null;

			try {
				if (os != null)
					os.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * ����ָ��Ŀ¼����������Ŀ¼������鿴ÿ��Ŀ¼�Ƿ񴴽���û�д������Զ�����
	 * 
	 * @param directory
	 *            Ҫ������Ŀ¼�����磺D:\\casemanage\\j2sdk1.4.2_09\\include\\win32
	 */
	public static boolean createDirectory(String directory) {
		try {
			String dir[] = directory.replace('\\', '/').split("/");

			StringBuffer buf = new StringBuffer(dir[0]);
			for (int i = 1; i < dir.length; i++) {
				buf.append(File.separator).append(dir[i]);

				File subFile = new File(buf.toString());
				if (subFile.exists() == false)
					if (!subFile.mkdir())
						return false;
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());

			return false;
		}

		return true;
	}

	/**
	 * ��ָ��Ŀ¼�´�����Ŀ¼
	 * 
	 * @param directory
	 *            �贴���ļ�Ŀ¼�ĸ�·��,Ҫ������ʵ����Ŀ¼�����磺D:\\casemanage ���� D:/casemanage
	 * @param subDirectory
	 *            Ҫ������Ŀ¼�����磺j2sdk1.4.2_09\\include\\win32
	 */
	public static boolean createDirectory(String directory, String subDirectory) {
		String dir[];
		File fl = new File(directory);
		try {
			if ("".equals(subDirectory) && fl.exists() != true)
				if (!fl.mkdir())
					return false;
				else if (!"".equals(subDirectory)) {
					dir = subDirectory.replace('\\', '/').split("/");

					StringBuffer buf = new StringBuffer(directory);
					for (int i = 0; i < dir.length; i++) {
						buf.append(File.separator).append(dir[i]);

						File subFile = new File(buf.toString());
						if (subFile.exists() == false)
							if (!subFile.mkdir())
								return false;
					}
				}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());

			return false;
		}

		return true;
	}

	/**
	 * ɾ��ָ��Ŀ¼��ȫ���ļ���Ŀ¼
	 * 
	 * @param directory
	 *            ��ɾ�����ļ�Ŀ¼·�������磺D:\\casemanage ���� D:/casemanage
	 * @param deleteOwner
	 *            �Ƿ�ͬʱɾ������Ŀ¼���ǣ���ɾ��Ŀ¼��������ɾ��Ŀ¼����
	 */
	public static void removeDirectory(String directory, boolean deleteOwner) {
		File file = new File(directory);
		if (!file.exists())
			return;
		else if (!file.isDirectory())
			return;

		File[] fileList = file.listFiles();
		if (fileList != null) {
			for (int i = 0; i < fileList.length; i++) {
				if (fileList[i].isFile())
					fileList[i].delete();
				else if (fileList[i].isDirectory())
					removeDirectory(fileList[i].getPath(), true);
			}
		}

		if (deleteOwner)
			file.delete();
	}

	/**
	 * ɾ��ָ��Ŀ¼��ȫ���ļ���Ŀ¼��֧��ͨ��������������Ҫɾ����Ŀ¼���ļ�
	 * 
	 * @param directory
	 *            ��ɾ�����ļ�Ŀ¼·�������磺D:\\casemanage ���� D:/casemanage
	 * @param filter
	 *            �����Ƿ�ɾ���Ĺ�����
	 * @return ����ɾ���ļ���Ŀ¼������
	 */
	public static int removeDirectory(String directory, RemoveFilter filter) {
		File file = new File(directory);
		if (!file.exists())
			return 0;
		else if (!file.isDirectory())
			return 0;

		int count = removeDirectory(1, directory, filter);

		if (filter == null
				|| filter.filter(0, file.getParent(), file.getName(), file
						.isDirectory())) {
			file.delete();

			count++;
		}

		return count;
	}

	/**
	 * ɾ��ָ��Ŀ¼�µ�ָ���ļ�
	 * 
	 * @param directory
	 *            Ҫɾ���ļ�����Ŀ¼�����磺D:\\casemanage ���� D:/casemanage
	 * @param filename
	 *            Ҫɾ�����ļ������������д�·����ʽ�����磺j2sdk1.4.2_09\\include\\win32\\jni_md.h
	 */
	public static void removeFile(String directory, String filename) {
		String filepath = combinDirectoryAndFile(directory, filename);
		File file = new File(filepath);
		if (!file.exists())
			return;

		file.delete();
	}

	/**
	 * ͨ������������ָ��Ŀ¼������Ŀ¼�������ļ��������ر������
	 * 
	 * @param directory
	 *            Ҫ������Ŀ¼
	 * @param iterator
	 *            �ļ����������������ļ������ε��ñ������ӿ�
	 * @return ���ر������ļ�������
	 */
	public static List<String> iterateFiles(String directory,
			FileIterator iterator) {
		File dir = new File(directory);
		if (!dir.exists())
			return null;
		else if (!dir.isDirectory())
			return null;

		List<String> result = new ArrayList<String>();
		iterateFiles(result, 1, directory, iterator);

		return result;
	}

	private static int copyFiles(int level, String srcDir, String tgtDir,
			CopyFilter filter) {
		int count = 0;

		File dir = new File(srcDir);
		File[] files = dir.listFiles();
		for (File file : files) {
			String fileName = file.getName();
			boolean isDirectory = file.isDirectory();

			boolean copyFlag = true;
			if (filter != null)
				copyFlag = filter.filter(level, srcDir, tgtDir, fileName,
						isDirectory);

			if (copyFlag) {
				if (isDirectory)
					createDirectory(tgtDir + fileName);
				else
					copyFile(srcDir, tgtDir, fileName);

				count++;
			}

			if (isDirectory)
				count += copyFiles(level + 1, srcDir + fileName + '/', tgtDir
						+ fileName + '/', filter);
		}

		return count;
	}

	private static int removeDirectory(int level, String directory,
			RemoveFilter filter) {
		int count = 0;

		File file = new File(directory);
		File[] fileList = file.listFiles();
		if (fileList != null) {
			for (int i = 0; i < fileList.length; i++) {
				boolean removeFlag = true;
				if (filter != null)
					removeFlag = filter.filter(level, fileList[i].getParent(),
							fileList[i].getName(), fileList[i].isDirectory());

				if (fileList[i].isDirectory())
					count += removeDirectory(level + 1, fileList[i].getPath(),
							filter);

				if (removeFlag) {
					fileList[i].delete();

					count++;
				}
			}
		}

		return count;
	}

	private static boolean iterateFiles(List<String> result, int level,
			String directory, FileIterator iterator) {
		File file = new File(directory);

		File[] fileList = file.listFiles();
		if (fileList != null) {
			for (int i = 0; i < fileList.length; i++) {
				int iterateFlag = FileIterator.ITERATE_OK;
				if (iterator != null)
					iterateFlag = iterator.iterate(level, fileList[i]
							.getParent(), fileList[i].getName(), fileList[i]
							.isDirectory());

				if (iterateFlag == FileIterator.ITERATE_STOP)
					return false;

				if (iterateFlag == FileIterator.ITERATE_OK)
					result.add(fileList[i].getPath());

				if (fileList[i].isDirectory())
					if (!iterateFiles(result, level + 1, fileList[i].getPath(),
							iterator))
						return false;
			}
		}

		return true;
	}
}
