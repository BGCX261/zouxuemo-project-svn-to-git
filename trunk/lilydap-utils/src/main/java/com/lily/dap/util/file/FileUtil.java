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
 *         文件操作工具类 创建、删除目录；读、写文件；目录间拷贝文件；遍历目录下文件；判断文件是否存在
 */
public class FileUtil {
	private final static int MAX_BUFFER_LENGTH = 64 * 1024; // 文件处理的缓冲区最大值

	/**
	 * 组合目录和文件名字符串为一个文件全路径名字符串 如： combinDirectoryAndFile("D:\JAVA_HOME\tomcat
	 * 6.0.14\webapps\energyAnalysisReport", "scripts") 返回 D:\JAVA_HOME\tomcat
	 * 6.0.14\webapps\energyAnalysisReport\scripts
	 * 
	 * @param dir1
	 *            根目录字符串
	 * @param dir2
	 *            子目录字符串
	 * @return 返回组合的文件全路径字符串
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
	 * 判断给定目录下的给定文件是否存在
	 * 
	 * @param directory
	 *            要判断的文件所在目录
	 * @param filename
	 *            要判断文件名
	 * @return
	 */
	public static boolean isFileExists(String directory, String filename) {
		String filepath = combinDirectoryAndFile(directory, filename);

		return isFileExists(filepath);
	}

	/**
	 * 判断给定全路径名的文件是否存在
	 * 
	 * @param filepath
	 *            要判断文件的全路径名
	 * @return
	 */
	public static boolean isFileExists(String filepath) {
		File file = new File(filepath);

		return file.exists();
	}

	/**
	 * 从源目录复制文件到目标目录，如果目标目录下文件已经存在，则覆盖已存在文件
	 * 复制的文件如果有路径格式，则会检查目标目录下是否有对应的路径，如果没有，则自动创建目录
	 * 
	 * @param srcdir
	 *            要复制的源目录，例如：D:\\casemanage 或者 D:/casemanage
	 * @param tgtdir
	 *            要复制到的目标目录，例如：D:\\casemanage_copy 或者 D:/casemanage_copy
	 * @param filename
	 *            要复制的文件名，允许名中带路径格式，例如：j2sdk1.4.2_09\\include\\win32\\jni_md.h
	 * @throws RuntimeException
	 *             发生IO操作错误（如：目标目录空间不足）时抛出异常
	 */
	public static void copyFile(String srcDir, String tgtDir, String fileName) {
		InputStream is = null;
		try {
			String srcFilePath = combinDirectoryAndFile(srcDir, fileName);
			String tgtFilePath = combinDirectoryAndFile(tgtDir, fileName);

			is = new FileInputStream(srcFilePath);

			writeFile(is, tgtFilePath);
		} catch (IOException e) {
			throw new RuntimeException("从" + srcDir + "复制文件" + fileName + "至"
					+ tgtDir + "失败 - " + e.getMessage());
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 从源目录复制文件列表到目标目录，通过过滤器控制要复制的文件
	 * 
	 * @param srcDir
	 *            要复制的源目录，例如：D:\\casemanage 或者 D:/casemanage
	 * @param tgtDir
	 *            要复制到的目标目录，例如：D:\\casemanage_copy 或者 D:/casemanage_copy
	 * @param filter
	 *            限制复制的文件过滤器
	 * @return 返回拷贝文件和目录的数量
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
	 * 从输入流写入数据至指定目录下的文件，写入前会自动检查文件所在目录是否存在，不存在则自动创建目录
	 * 
	 * @param is
	 *            文件输入流
	 * @param filepath
	 *            输出的文件全路径名
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
			throw new RuntimeException(file_path + " 文件未找到,存储文件失败!");
		} catch (IOException e) {
			throw new RuntimeException("存储文件失败 - " + e.getMessage());
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
	 * 创建指定目录，检索整个目录链，会查看每级目录是否创建，没有创建则自动创建
	 * 
	 * @param directory
	 *            要创建的目录，例如：D:\\casemanage\\j2sdk1.4.2_09\\include\\win32
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
	 * 从指定目录下创建子目录
	 * 
	 * @param directory
	 *            需创建文件目录的根路径,要求是现实存在目录，例如：D:\\casemanage 或者 D:/casemanage
	 * @param subDirectory
	 *            要创建的目录，例如：j2sdk1.4.2_09\\include\\win32
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
	 * 删除指定目录下全部文件和目录
	 * 
	 * @param directory
	 *            需删除的文件目录路径，例如：D:\\casemanage 或者 D:/casemanage
	 * @param deleteOwner
	 *            是否同时删除自身目录，是：则删除目录本身，否则不删除目录本身
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
	 * 删除指定目录下全部文件和目录，支持通过过滤器控制需要删除的目录和文件
	 * 
	 * @param directory
	 *            需删除的文件目录路径，例如：D:\\casemanage 或者 D:/casemanage
	 * @param filter
	 *            控制是否删除的过滤器
	 * @return 返回删除文件和目录的数量
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
	 * 删除指定目录下的指定文件
	 * 
	 * @param directory
	 *            要删除文件所在目录，例如：D:\\casemanage 或者 D:/casemanage
	 * @param filename
	 *            要删除的文件名，允许名中带路径格式，例如：j2sdk1.4.2_09\\include\\win32\\jni_md.h
	 */
	public static void removeFile(String directory, String filename) {
		String filepath = combinDirectoryAndFile(directory, filename);
		File file = new File(filepath);
		if (!file.exists())
			return;

		file.delete();
	}

	/**
	 * 通过遍历器遍历指定目录及其子目录下所有文件，并返回遍历结果
	 * 
	 * @param directory
	 *            要遍历的目录
	 * @param iterator
	 *            文件遍历器，遍历的文件将依次调用遍历器接口
	 * @return 返回遍历的文件名集合
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
