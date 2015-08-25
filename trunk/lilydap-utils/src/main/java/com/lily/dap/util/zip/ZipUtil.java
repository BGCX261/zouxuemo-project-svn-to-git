/**
 * 
 */
package com.lily.dap.util.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * @author xuemozou
 * 
 *         ZIP��ʽ�ļ�ѹ������ѹ��������
 */
public class ZipUtil {
	private static Log log = LogFactory.getLog(ZipUtil.class);

	private final static int MAX_BUFFER_LENGTH = 64 * 1024; // �ļ�����Ļ��������ֵ

	/**
	 * ѹ��ָ��·���µ��ļ�����Ŀ¼��ָ��Ŀ¼�µĸ����ļ�
	 * 
	 * @param path
	 *            Ҫѹ����·��, ������Ŀ¼, Ҳ�������ļ�.
	 * @param zipFileDir
	 *            ѹ�����ļ����Ŀ¼����Ŀ¼�������
	 * @param zipFileName
	 *            ѹ���ļ���
	 * @param isRecursive
	 *            �Ƿ�ݹ�ѹ����Ŀ¼���������ֻѹ����ǰĿ¼���ļ�������ѹ������Ŀ¼�������ļ�
	 * @param isOutBlankDir
	 *            �Ƿ������Ŀ¼
	 * @param filter
	 *            ѹ����������������Ҫѹ�����ļ������Ϊnull���������ļ���ѹ��
	 * @throws IOException
	 */
	public static void zip(String path, String zipFileDir, String zipFileName,
			boolean isRecursive, boolean isOutBlankDir, ZipingFilter filter)
			throws IOException {
		File dir = new File(zipFileDir);
		if (!dir.exists())
			throw new RuntimeException("ѹ���ļ����·��[" + zipFileDir + "]�����ڣ�");
		else if (!dir.isDirectory())
			throw new RuntimeException("ѹ���ļ����·��[" + zipFileDir + "]����Ŀ¼��");

		String zip_file_dir = zipFileDir.replace('\\', '/');
		if (zip_file_dir.charAt(zip_file_dir.length() - 1) != '/')
			zip_file_dir += '/';

		FileOutputStream fos = new FileOutputStream(zip_file_dir + zipFileName);

		zip(path, fos, isRecursive, isOutBlankDir, filter);

		fos.close();
	}

	/**
	 * ѹ��ָ��·���µ��ļ�����Ŀ¼�����������
	 * 
	 * @param path
	 *            Ҫѹ����·��, ������Ŀ¼, Ҳ�������ļ�.
	 * @param os
	 *            ѹ�������
	 * @param isRecursive
	 *            �Ƿ�ݹ�ѹ����Ŀ¼���������ֻѹ����ǰĿ¼���ļ�������ѹ������Ŀ¼�������ļ�
	 * @param isOutBlankDir
	 *            �Ƿ������Ŀ¼
	 * @param filter
	 *            ѹ����������������Ҫѹ�����ļ������Ϊnull���������ļ���ѹ��
	 * @throws IOException
	 */
	public static void zip(String path, OutputStream os, boolean isRecursive,
			boolean isOutBlankDir, ZipingFilter filter) throws IOException {
		ZipOutputStream zo = new ZipOutputStream(os);

		zip(path, new File(path), zo, isRecursive, isOutBlankDir, filter);

		zo.closeEntry();
		zo.close();
	}

	/**
	 * ��ѹ��ZIP�ļ����������ݵ�ָ��Ŀ¼�£�ѹ���ļ����ڸ�Ŀ¼�½⿪
	 * 
	 * @param is
	 *            ҪZIPѹ���ļ�������
	 * @param outputDirectory
	 *            Ҫ��ѹ����Ŀ���ļ�Ŀ¼����ѹ�ļ�����ѹ����Ŀ¼�£�Ŀ¼�������
	 * @param filter
	 *            ��ѹ��������������Ҫ��ѹ���ļ������Ϊnull���������ļ�����ѹ
	 * @throws IOException
	 */
	public static void unZip(InputStream is, String outputDirectory,
			ZipingFilter filter) throws IOException {
		String parent = System.getProperty("java.io.tmpdir");
		File file = File.createTempFile("lilydap", ".zip", new File(parent));

		FileOutputStream os = new FileOutputStream(file);
		writeFile(is, os);
		os.close();

		ZipFile zipFile = new ZipFile(file);
		unZip(zipFile, outputDirectory, filter);

		file.delete();
	}

	/**
	 * ��ѹ��ZIP�ļ����ݵ�ָ��Ŀ¼�£�ѹ���ļ����ڸ�Ŀ¼�½⿪
	 * 
	 * @param zipFilePath
	 *            Ҫ��ѹ����ZIP�ļ�ȫ·��
	 * @param outputDirectory
	 *            Ҫ��ѹ����Ŀ���ļ�Ŀ¼����ѹ�ļ�����ѹ����Ŀ¼�£�Ŀ¼�������
	 * @param filter
	 *            ��ѹ��������������Ҫ��ѹ���ļ������Ϊnull���������ļ�����ѹ
	 * @throws IOException
	 */
	public static void unZip(String zipFilePath, String outputDirectory,
			ZipingFilter filter) throws IOException {
		ZipFile zipFile = new ZipFile(zipFilePath);

		unZip(zipFile, outputDirectory, filter);
	}

	/**
	 * ѹ��ָ��·���µ��ļ�����Ŀ¼��ZIP�����
	 * 
	 * @param path
	 *            Ҫѹ����·��, ������Ŀ¼, Ҳ�������ļ�.
	 * @param basePath
	 *            ���path��Ŀ¼,��һ��Ϊnew File(path),
	 *            ������:ʹ�����zip�ļ��Դ�Ŀ¼Ϊ��Ŀ¼,���Ϊnull��ֻѹ���ļ�, ����ѹĿ¼.
	 * @param zo
	 *            ѹ�������
	 * @param isRecursive
	 *            �Ƿ�ݹ�ѹ����Ŀ¼���������ֻѹ����ǰĿ¼���ļ�������ѹ������Ŀ¼�������ļ�
	 * @param isOutBlankDir
	 *            �Ƿ������Ŀ¼
	 * @param filter
	 *            ѹ����������������Ҫѹ�����ļ������Ϊnull���������ļ���ѹ��
	 * @throws IOException
	 */
	private static void zip(String path, File basePath, ZipOutputStream zo,
			boolean isRecursive, boolean isOutBlankDir, ZipingFilter filter)
			throws IOException {
		File inFile = new File(path);

		File[] files;
		if (inFile.isDirectory()) { // ��Ŀ¼
			files = inFile.listFiles();
		} else if (inFile.isFile()) { // ���ļ�
			files = new File[1];
			files[0] = inFile;
		} else
			files = new File[0];

		byte[] buf = new byte[1024];
		int len;
		for (int i = 0; i < files.length; i++) {
			String pathName = "";
			boolean isDircetory = files[i].isDirectory();

			if (basePath != null) {
				if (basePath.isDirectory()) {
					pathName = files[i].getPath().substring(
							basePath.getPath().length() + 1);
				} else {// �ļ�
					pathName = files[i].getPath().substring(
							basePath.getParent().length() + 1);
				}
			} else {
				pathName = files[i].getName();
			}

			if (filter != null && !filter.filter(pathName, isDircetory)) {
				if (log.isDebugEnabled())
					log.debug("ignore " + pathName);

				continue;
			}

			if (log.isDebugEnabled())
				log.debug("ziping " + pathName);

			if (isDircetory) {
				if (isOutBlankDir && basePath != null)
					zo.putNextEntry(new ZipEntry(pathName + "/")); // ����ʹ��Ŀ¼Ҳ�Ž�ȥ

				if (isRecursive) { // �ݹ�
					zip(files[i].getPath(), basePath, zo, isRecursive,
							isOutBlankDir, filter);
				}
			} else {
				FileInputStream fin = new FileInputStream(files[i]);
				zo.putNextEntry(new ZipEntry(pathName));

				while ((len = fin.read(buf)) > 0) {
					zo.write(buf, 0, len);
				}

				fin.close();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static void unZip(ZipFile zipFile, String outputDirectory,
			ZipingFilter filter) throws IOException {
		File dir = new File(outputDirectory);
		if (!dir.exists())
			throw new RuntimeException("��ѹ��Ŀ¼[" + outputDirectory + "]�����ڣ�");
		else if (!dir.isDirectory())
			throw new RuntimeException("[" + outputDirectory + "]����Ŀ¼��");

		Enumeration e = zipFile.getEntries();
		ZipEntry zipEntry = null;

		while (e.hasMoreElements()) {
			zipEntry = (ZipEntry) e.nextElement();

			String filename = zipEntry.getName();
			boolean isDirectory = zipEntry.isDirectory();
			if (filter != null && !filter.filter(filename, isDirectory)) {
				if (log.isDebugEnabled())
					log.debug("ignore " + zipEntry.getName());

				continue;
			}

			if (log.isDebugEnabled())
				log.debug("unziping " + zipEntry.getName());

			if (zipEntry.isDirectory()) {
				String name = zipEntry.getName();
				name = name.substring(0, name.length() - 1);

				File f = new File(outputDirectory + File.separator + name);
				f.mkdir();

				if (log.isDebugEnabled())
					log.debug("create directory: " + outputDirectory
							+ File.separator + name);
			} else {
				String fileName = zipEntry.getName();
				fileName = fileName.replace('\\', '/');

				if (fileName.indexOf("/") != -1) {
					createDirectory(outputDirectory, fileName.substring(0,
							fileName.lastIndexOf("/")));

					fileName = fileName.substring(
							fileName.lastIndexOf("/") + 1, fileName.length());
				}

				File f = new File(outputDirectory + File.separator
						+ zipEntry.getName());
				f.createNewFile();

				InputStream in = zipFile.getInputStream(zipEntry);
				FileOutputStream out = new FileOutputStream(f);

				byte[] by = new byte[1024];
				int c;
				while ((c = in.read(by)) != -1)
					out.write(by, 0, c);

				out.close();
				in.close();
			}
		}

		zipFile.close();
	}

	private static void createDirectory(String directory, String subDirectory) {
		String dir[];
		File fl = new File(directory);

		String full_dir = directory;
		try {
			if (subDirectory == "" && fl.exists() != true)
				fl.mkdir();
			else if (subDirectory != "") {
				dir = subDirectory.replace('\\', '/').split("/");
				for (int i = 0; i < dir.length; i++) {
					File subFile = new File(full_dir + File.separator + dir[i]);
					if (subFile.exists() == false)
						subFile.mkdir();
					full_dir += File.separator + dir[i];
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private static void writeFile(InputStream is, OutputStream os)
			throws IOException {
		byte[] buffer = new byte[MAX_BUFFER_LENGTH];

		int bytesRead = 0;

		while ((bytesRead = is.read(buffer, 0, MAX_BUFFER_LENGTH)) != -1) {
			os.write(buffer, 0, bytesRead);
			os.flush();
		}
	}
}
