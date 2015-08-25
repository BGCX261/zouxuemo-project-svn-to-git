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
 *         ZIP格式文件压缩、解压缩工具类
 */
public class ZipUtil {
	private static Log log = LogFactory.getLog(ZipUtil.class);

	private final static int MAX_BUFFER_LENGTH = 64 * 1024; // 文件处理的缓冲区最大值

	/**
	 * 压缩指定路径下的文件及子目录至指定目录下的给定文件
	 * 
	 * @param path
	 *            要压缩的路径, 可以是目录, 也可以是文件.
	 * @param zipFileDir
	 *            压缩后文件存放目录，该目录必须存在
	 * @param zipFileName
	 *            压缩文件名
	 * @param isRecursive
	 *            是否递归压缩子目录，如果否，则只压缩当前目录下文件，否则压缩其子目录下所有文件
	 * @param isOutBlankDir
	 *            是否输出空目录
	 * @param filter
	 *            压缩过滤器，过滤需要压缩的文件，如果为null，则所有文件都压缩
	 * @throws IOException
	 */
	public static void zip(String path, String zipFileDir, String zipFileName,
			boolean isRecursive, boolean isOutBlankDir, ZipingFilter filter)
			throws IOException {
		File dir = new File(zipFileDir);
		if (!dir.exists())
			throw new RuntimeException("压缩文件存放路径[" + zipFileDir + "]不存在！");
		else if (!dir.isDirectory())
			throw new RuntimeException("压缩文件存放路径[" + zipFileDir + "]不是目录！");

		String zip_file_dir = zipFileDir.replace('\\', '/');
		if (zip_file_dir.charAt(zip_file_dir.length() - 1) != '/')
			zip_file_dir += '/';

		FileOutputStream fos = new FileOutputStream(zip_file_dir + zipFileName);

		zip(path, fos, isRecursive, isOutBlankDir, filter);

		fos.close();
	}

	/**
	 * 压缩指定路径下的文件及子目录至给定输出流
	 * 
	 * @param path
	 *            要压缩的路径, 可以是目录, 也可以是文件.
	 * @param os
	 *            压缩输出流
	 * @param isRecursive
	 *            是否递归压缩子目录，如果否，则只压缩当前目录下文件，否则压缩其子目录下所有文件
	 * @param isOutBlankDir
	 *            是否输出空目录
	 * @param filter
	 *            压缩过滤器，过滤需要压缩的文件，如果为null，则所有文件都压缩
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
	 * 解压缩ZIP文件输入流数据到指定目录下，压缩文件将在该目录下解开
	 * 
	 * @param is
	 *            要ZIP压缩文件输入流
	 * @param outputDirectory
	 *            要解压到的目标文件目录，解压文件将解压到本目录下，目录必须存在
	 * @param filter
	 *            解压过滤器，过滤需要解压的文件，如果为null，则所有文件都解压
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
	 * 解压缩ZIP文件数据到指定目录下，压缩文件将在该目录下解开
	 * 
	 * @param zipFilePath
	 *            要解压缩的ZIP文件全路径
	 * @param outputDirectory
	 *            要解压到的目标文件目录，解压文件将解压到本目录下，目录必须存在
	 * @param filter
	 *            解压过滤器，过滤需要解压的文件，如果为null，则所有文件都解压
	 * @throws IOException
	 */
	public static void unZip(String zipFilePath, String outputDirectory,
			ZipingFilter filter) throws IOException {
		ZipFile zipFile = new ZipFile(zipFilePath);

		unZip(zipFile, outputDirectory, filter);
	}

	/**
	 * 压缩指定路径下的文件及子目录至ZIP输出流
	 * 
	 * @param path
	 *            要压缩的路径, 可以是目录, 也可以是文件.
	 * @param basePath
	 *            如果path是目录,它一般为new File(path),
	 *            作用是:使输出的zip文件以此目录为根目录,如果为null它只压缩文件, 不解压目录.
	 * @param zo
	 *            压缩输出流
	 * @param isRecursive
	 *            是否递归压缩子目录，如果否，则只压缩当前目录下文件，否则压缩其子目录下所有文件
	 * @param isOutBlankDir
	 *            是否输出空目录
	 * @param filter
	 *            压缩过滤器，过滤需要压缩的文件，如果为null，则所有文件都压缩
	 * @throws IOException
	 */
	private static void zip(String path, File basePath, ZipOutputStream zo,
			boolean isRecursive, boolean isOutBlankDir, ZipingFilter filter)
			throws IOException {
		File inFile = new File(path);

		File[] files;
		if (inFile.isDirectory()) { // 是目录
			files = inFile.listFiles();
		} else if (inFile.isFile()) { // 是文件
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
				} else {// 文件
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
					zo.putNextEntry(new ZipEntry(pathName + "/")); // 可以使空目录也放进去

				if (isRecursive) { // 递归
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
			throw new RuntimeException("解压缩目录[" + outputDirectory + "]不存在！");
		else if (!dir.isDirectory())
			throw new RuntimeException("[" + outputDirectory + "]不是目录！");

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
