/**
 * FileUtilTest.java
 * Copyright (C), 2009 百合软件.
 * Author : 邹学模      
 * 2009-10-5 下午05:16:02
 */
package com.lily.dap.util.file;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @ClassName: FileUtilTest
 * @author 邹学模
 * @Description :
 * 
 */
@Ignore
public class FileUtilTest {
	@Test
	public void testCombinDirectoryAndFile() {
		Assert
				.assertEquals(
						FileUtil
								.combinDirectoryAndFile(
										"D:/JAVA_HOME/tomcat 6.0.14/webapps/energyAnalysisReport",
										"scripts"),
						"D:/JAVA_HOME/tomcat 6.0.14/webapps/energyAnalysisReport/scripts");
		Assert
				.assertEquals(FileUtil.combinDirectoryAndFile(
						"D:/JAVA_HOME/tomcat 6.0.14/webapps/",
						"/energyAnalysisReport/scripts"),
						"D:/JAVA_HOME/tomcat 6.0.14/webapps/energyAnalysisReport/scripts");
	}

	@Test
	public void testIsFileExists() {
		Assert.assertTrue(FileUtil.isFileExists("C:/windows"));
		Assert.assertFalse(FileUtil.isFileExists("C:/none"));

		Assert.assertTrue(FileUtil.isFileExists("C:/windows", "explorer.exe"));
		Assert.assertTrue(FileUtil.isFileExists("C:/windows", "system"));
	}

	@Test
	public void testCopyFile() {
		String tgtDir = "C:/~tempTgtDir";
		String srcDir = "C:/windows";

		FileUtil.copyFile(srcDir, tgtDir, "explorer.exe");
		Assert.assertTrue(FileUtil.isFileExists(tgtDir, "explorer.exe"));

		FileUtil.copyFile(srcDir, tgtDir, "system32/drivers/acpi.sys");
		Assert.assertTrue(FileUtil.isFileExists(tgtDir,
				"system32/drivers/acpi.sys"));

		// 过滤器－拷贝目录下所有扩展名为bmp的文件
		CopyFilter filter = new CopyFilter() {
			public boolean filter(int level, String srcDirectory,
					String tgtDirectory, String filename, boolean isDirectory) {
				// if (level > 1)
				// return false;

				if (filename.endsWith(".bmp"))
					return true;

				return false;
			}
		};

		FileUtil.copyFiles(srcDir, tgtDir, filter);

		// 遍历器－打印所有扩展名为bmp的文件名到屏幕
		FileIterator iterator = new FileIterator() {
			public int iterate(int level, String dir, String file,
					boolean isDirectory) {
				if (file.endsWith(".bmp"))
					System.out.println(file);

				return ITERATE_IGNORE;
			}
		};

		FileUtil.iterateFiles(tgtDir, iterator);
	}

	@Test
	public void testRemoveDirectory() {
		String tgtDir = "C:/~tempTgtDir";

		// 删除所有除了bmp外的其他文件
		RemoveFilter filter = new RemoveFilter() {
			public boolean filter(int level, String directory, String filename,
					boolean isDirectory) {
				if (filename.endsWith(".bmp"))
					return false;
				else
					return true;
			}

		};

		FileUtil.removeDirectory(tgtDir, filter);

		// 遍历器－过滤返回所有非目录的文件名
		FileIterator iterator = new FileIterator() {
			public int iterate(int level, String dir, String file,
					boolean isDirectory) {
				while(level-- > 0)
					System.out.print('\t');
				
				if (isDirectory)
					System.out.print('[');
				
				System.out.print(file);
				
				if (isDirectory)
					System.out.print(']');
				
				System.out.println();
				
				if (isDirectory)
					return ITERATE_IGNORE;

				return ITERATE_OK;
			}
		};

		List<String> result = FileUtil.iterateFiles(tgtDir, iterator);
		// List<String> result = FileUtil.iterateFiles(tgtDir, null);
		for (String s : result)
			System.out.println(s);

		FileUtil.removeDirectory(tgtDir, null);
	}
	//
	// @Test
	// public void testIterateFiles() {
	// //遍历器－遍历windows系统目录下所有扩展名为exe的文件并打印文件名到屏幕
	// FileIterator iterator = new FileIterator() {
	// public int iterate(int level, String dir, String file,
	// boolean isDirectory) {
	// if (/*level == 1 && */file.endsWith(".exe"))
	// System.out.println(dir + File.separator + file);
	//				
	// return ITERATE_IGNORE;
	// }
	// };
	//		
	// FileUtil.iterateFiles("C:/windows", iterator);
	// }
}
