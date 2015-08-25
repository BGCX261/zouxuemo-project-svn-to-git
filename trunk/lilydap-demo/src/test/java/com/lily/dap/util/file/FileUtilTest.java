/**
 * FileUtilTest.java
 * Copyright (C), 2009 �ٺ����.
 * Author : ��ѧģ      
 * 2009-10-5 ����05:16:02
 */
package com.lily.dap.util.file;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @ClassName: FileUtilTest
 * @author ��ѧģ
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

		// ������������Ŀ¼��������չ��Ϊbmp���ļ�
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

		// ����������ӡ������չ��Ϊbmp���ļ�������Ļ
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

		// ɾ�����г���bmp��������ļ�
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

		// �����������˷������з�Ŀ¼���ļ���
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
	// //������������windowsϵͳĿ¼��������չ��Ϊexe���ļ�����ӡ�ļ�������Ļ
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
